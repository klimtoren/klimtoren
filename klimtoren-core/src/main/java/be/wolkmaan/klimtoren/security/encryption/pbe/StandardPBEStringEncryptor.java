/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security.encryption.pbe;

import be.wolkmaan.klimtoren.security.encryption.pbe.config.PBEConfig;
import be.wolkmaan.klimtoren.security.encryption.pbe.config.StringPBEConfig;
import be.wolkmaan.klimtoren.security.exceptions.AlreadyInitializedException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionOperationNotPossibleException;
import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import be.wolkmaan.klimtoren.shared.CommonUtils;
import java.security.Provider;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author karl
 */
public class StandardPBEStringEncryptor implements PBEStringCleanablePasswordEncryptor {

    private static final String MESSAGE_CHARSET = "UTF-8";
    private static final String ENCRYPTED_MESSAGE_CHARSET = "US-ASCII";
    public static final String DEFAULT_STRING_OUTPUT_TYPE = CommonUtils.STRING_OUTPUT_TYPE_BASE64;

    private StringPBEConfig stringPBEConfig = null;

    private String stringOutputType = DEFAULT_STRING_OUTPUT_TYPE;
    private boolean stringOutputTypeBase64 = true;

    private boolean stringOutputTypeSet = false;

    private final StandardPBEByteEncryptor byteEncryptor;
    private final Base64 base64;

    public StandardPBEStringEncryptor() {
        super();
        this.byteEncryptor = new StandardPBEByteEncryptor();
        this.base64 = new Base64();
    }

    private StandardPBEStringEncryptor(final StandardPBEByteEncryptor standardPBEByteEncryptor) {
        super();
        this.byteEncryptor = standardPBEByteEncryptor;
        this.base64 = new Base64();
    }

    public synchronized void setConfig(final PBEConfig config) {
        this.byteEncryptor.setConfig(config);
        if ((config != null) && (config instanceof StringPBEConfig)) {
            this.stringPBEConfig = (StringPBEConfig) config;
        }
    }

    public void setAlgorithm(final String algorithm) {
        this.byteEncryptor.setAlgorithm(algorithm);
    }

    public void setPassword(final String password) {
        this.byteEncryptor.setPassword(password);
    }

    public void setPasswordCharArray(char[] password) {
        this.byteEncryptor.setPasswordCharArray(password);
    }

    public void setKeyObtentionIterations(final int keyObtentionIterations) {
        this.byteEncryptor.setKeyObtentionIterations(keyObtentionIterations);
    }

    public void setSaltGenerator(final SaltGenerator saltGenerator) {
        this.byteEncryptor.setSaltGenerator(saltGenerator);
    }

    public void setProviderName(final String providerName) {
        this.byteEncryptor.setProviderName(providerName);
    }

    public void setProvider(final Provider provider) {
        this.byteEncryptor.setProvider(provider);
    }

    public synchronized void setStringOutputType(final String stringOutputType) {
        CommonUtils.validateNotEmpty(stringOutputType, "String output type cannot be set empty");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.stringOutputType = CommonUtils.getStandardStringOutputType(stringOutputType);
        this.stringOutputTypeSet = true;
    }

    synchronized StandardPBEStringEncryptor[] cloneAndInitializeEncryptor(final int size) {

        final StandardPBEByteEncryptor[] byteEncryptorClones
                = this.byteEncryptor.cloneAndInitializeEncryptor(size);

        initializeSpecifics();

        final StandardPBEStringEncryptor[] clones = new StandardPBEStringEncryptor[size];

        clones[0] = this;

        for (int i = 1; i < size; i++) {
            clones[i] = new StandardPBEStringEncryptor(byteEncryptorClones[i]);
            if (CommonUtils.isNotEmpty(this.stringOutputType)) {
                clones[i].setStringOutputType(this.stringOutputType);
            }
        }

        return clones;

    }

    public boolean isInitialized() {
        return this.byteEncryptor.isInitialized();
    }

    public synchronized void initialize() {
        if (!this.isInitialized()) {
            initializeSpecifics();
            this.byteEncryptor.initialize();
        }
    }

    private void initializeSpecifics() {
        if (this.stringPBEConfig != null) {
            final String configStringOutputType
                    = this.stringPBEConfig.getStringOutputType();
            this.stringOutputType
                    = ((this.stringOutputTypeSet) || (configStringOutputType == null))
                    ? this.stringOutputType : configStringOutputType;
        }
        this.stringOutputTypeBase64
                = (CommonUtils.STRING_OUTPUT_TYPE_BASE64.
                equalsIgnoreCase(this.stringOutputType));
    }

    @Override
    public String encrypt(final String message) {
        if (message == null) {
            return null;
        }

        // Check initialization
        if (!isInitialized()) {
            initialize();
        }

        try {

            // The input String is converted into bytes using MESSAGE_CHARSET
            // as a fixed charset to avoid problems with different platforms
            // having different default charsets (see MESSAGE_CHARSET doc).
            final byte[] messageBytes = message.getBytes(MESSAGE_CHARSET);

            // The StandardPBEByteEncryptor does its job.
            byte[] encryptedMessage = this.byteEncryptor.encrypt(messageBytes);

            // We encode the result in BASE64 or HEXADECIMAL so that we obtain
            // the safest result String possible.
            String result = null;
            if (this.stringOutputTypeBase64) {
                encryptedMessage = this.base64.encode(encryptedMessage);
                result = new String(encryptedMessage, ENCRYPTED_MESSAGE_CHARSET);
            } else {
                result = CommonUtils.toHexadecimal(encryptedMessage);
            }

            return result;

        } catch (EncryptionInitializationException e) {
            throw e;
        } catch (EncryptionOperationNotPossibleException e) {
            throw e;
        } catch (Exception e) {
            // If encryption fails, it is more secure not to return any 
            // information about the cause in nested exceptions. Simply fail.
            throw new EncryptionOperationNotPossibleException();
        }
    }

    @Override
    public String decrypt(String encryptedMessage) {
        if (encryptedMessage == null) {
            return null;
        }
        // Check initialization
        if (!isInitialized()) {
            initialize();
        }
        try {
            byte[] encryptedMessageBytes = null;
            // Decode input to bytes depending on whether it is a
            // BASE64-encoded or hexadecimal String
            if (this.stringOutputTypeBase64) {
                encryptedMessageBytes
                        = encryptedMessage.getBytes(ENCRYPTED_MESSAGE_CHARSET);
                encryptedMessageBytes
                        = this.base64.decode(encryptedMessageBytes);
            } else {
                encryptedMessageBytes
                        = CommonUtils.fromHexadecimal(encryptedMessage);
            }
            // Let the byte encyptor decrypt
            final byte[] message = this.byteEncryptor.decrypt(encryptedMessageBytes);

            // Return the resulting decrypted String, using MESSAGE_CHARSET
            // as charset to maintain between encryption and decyption
            // processes.
            return new String(message, MESSAGE_CHARSET);

        } catch (EncryptionInitializationException e) {
            throw e;
        } catch (EncryptionOperationNotPossibleException e) {
            throw e;
        } catch (Exception e) {
            // If decryption fails, it is more secure not to return any 
            // information about the cause in nested exceptions. Simply fail.
            throw new EncryptionOperationNotPossibleException();
        }

    }
}
