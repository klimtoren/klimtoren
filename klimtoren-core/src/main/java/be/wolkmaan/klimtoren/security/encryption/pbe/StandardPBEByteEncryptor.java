
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security.encryption.pbe;

import be.wolkmaan.klimtoren.security.encryption.pbe.config.PBECleanablePasswordConfig;
import be.wolkmaan.klimtoren.security.encryption.pbe.config.PBEConfig;
import be.wolkmaan.klimtoren.security.exceptions.AlreadyInitializedException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionOperationNotPossibleException;
import be.wolkmaan.klimtoren.security.salt.FixedSaltGenerator;
import be.wolkmaan.klimtoren.security.salt.RandomSaltGenerator;
import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import be.wolkmaan.klimtoren.shared.CommonUtils;
import java.security.InvalidKeyException;
import java.security.Provider;
import java.text.Normalizer;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author karl
 */
public class StandardPBEByteEncryptor implements PBEByteCleanablePasswordEncryptor {

    public static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDES";
    public static final int DEFAULT_KEY_OBTENTION_ITERATIONS = 1000;
    public static final int DEFAULT_SALT_SIZE_BYTES = 8;

    private String algorithm = DEFAULT_ALGORITHM;
    private String providerName = null;
    private Provider provider = null;

    private char[] password = null;

    private int keyObtentionIterations = DEFAULT_KEY_OBTENTION_ITERATIONS;
    private SaltGenerator saltGenerator = null;
    private int saltSizeBytes = DEFAULT_SALT_SIZE_BYTES;

    private PBEConfig config = null;

    private boolean algorithmSet = false;
    private boolean passwordSet = false;
    private boolean iterationsSet = false;
    private boolean saltGeneratorSet = false;
    private boolean providerNameSet = false;
    private boolean providerSet = false;

    private boolean initialized = false;

    private SecretKey key = null;

    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    private boolean usingFixedSalt = false;
    private byte[] fixedSaltInUse = null;

    public StandardPBEByteEncryptor() {
        super();
    }

    public synchronized void setConfig(PBEConfig config) {
        CommonUtils.validateNotNull(config, "Config cannot be set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.config = config;
    }

    public synchronized void setAlgorithm(String algorithm) {
        CommonUtils.validateNotEmpty(algorithm, "Algorithm cannot be set empty");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.algorithm = algorithm;
        this.algorithmSet = true;
    }

    public synchronized void setPassword(String password) {
        CommonUtils.validateNotEmpty(password, "Password cannot be set empty");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        if (this.password != null) {
            cleanPassword(this.password);
        }
        this.password = password.toCharArray();
        this.passwordSet = true;
    }

    public synchronized void setPasswordCharArray(char[] password) {
        CommonUtils.validateNotNull(password, "Password cannot be set null");
        CommonUtils.validateIsTrue(password.length > 0, "Password cannot be set empty");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        if (this.password != null) {
            cleanPassword(this.password);
        }
        this.password = new char[password.length];
        System.arraycopy(password, 0, this.password, 0, password.length);
        this.passwordSet = true;
    }

    public synchronized void setKeyObtentionIterations(int keyObtentionIterations) {
        CommonUtils.validateIsTrue(keyObtentionIterations > 0,
                "Number of iterations for key obtention must be "
                + "greater than zero");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.keyObtentionIterations = keyObtentionIterations;
        this.iterationsSet = true;
    }

    public synchronized void setSaltGenerator(SaltGenerator saltGenerator) {
        CommonUtils.validateNotNull(saltGenerator, "Salt generator cannot be set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.saltGenerator = saltGenerator;
        this.saltGeneratorSet = true;
    }

    public synchronized void setProviderName(String providerName) {
        CommonUtils.validateNotNull(providerName, "Provider name cannot be set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.providerName = providerName;
        this.providerNameSet = true;
    }

    public synchronized void setProvider(Provider provider) {
        CommonUtils.validateNotNull(provider, "Provider canno tbe set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.provider = provider;
        this.providerSet = true;
    }

    synchronized StandardPBEByteEncryptor[] cloneAndInitializeEncryptor(final int size) {

        if (isInitialized()) {
            throw new EncryptionInitializationException(
                    "Cannot clone encryptor if it has been already initialized");
        }

        // If there is a config object, this forces the password configured value 
        // (if any) into the this.password property.
        resolveConfigurationPassword();

        final char[] copiedPassword = new char[this.password.length];
        System.arraycopy(this.password, 0, copiedPassword, 0, this.password.length);

        // Initialize the encryptor - note that this will clean the 
        // password (that's why copied it before)
        initialize();

        final StandardPBEByteEncryptor[] clones = new StandardPBEByteEncryptor[size];

        clones[0] = this;

        for (int i = 1; i < size; i++) {

            final StandardPBEByteEncryptor clone = new StandardPBEByteEncryptor();
            clone.setPasswordCharArray(copiedPassword);
            if (CommonUtils.isNotEmpty(this.algorithm)) {
                clone.setAlgorithm(this.algorithm);
            }
            clone.setKeyObtentionIterations(this.keyObtentionIterations);
            if (this.provider != null) {
                clone.setProvider(this.provider);
            }
            if (this.providerName != null) {
                clone.setProviderName(this.providerName);
            }
            if (this.saltGenerator != null) {
                clone.setSaltGenerator(this.saltGenerator);
            }

            clones[i] = clone;

        }

        cleanPassword(copiedPassword);

        return clones;

    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public synchronized void initialize() {

        // Double-check to avoid synchronization issues
        if (!this.initialized) {

            /*
             * If a PBEConfig object has been set, we need to 
             * consider the values it returns (if, for each value, the
             * corresponding "setX" method has not been called).
             */
            if (this.config != null) {

                resolveConfigurationPassword();

                final String configAlgorithm = this.config.getAlgorithm();
                if (configAlgorithm != null) {
                    CommonUtils.validateNotEmpty(configAlgorithm,
                            "Algorithm cannot be set empty");
                }

                final Integer configKeyObtentionIterations
                        = this.config.getKeyObtentionIterations();
                if (configKeyObtentionIterations != null) {
                    CommonUtils.validateIsTrue(configKeyObtentionIterations.intValue() > 0,
                            "Number of iterations for key obtention must be "
                            + "greater than zero");
                }

                final SaltGenerator configSaltGenerator = this.config.getSaltGenerator();

                final String configProviderName = this.config.getProviderName();
                if (configProviderName != null) {
                    CommonUtils.validateNotEmpty(configProviderName,
                            "Provider name cannot be empty");
                }

                final Provider configProvider = this.config.getProvider();

                this.algorithm
                        = ((this.algorithmSet) || (configAlgorithm == null))
                        ? this.algorithm : configAlgorithm;
                this.keyObtentionIterations
                        = ((this.iterationsSet)
                        || (configKeyObtentionIterations == null))
                        ? this.keyObtentionIterations
                        : configKeyObtentionIterations.intValue();
                this.saltGenerator
                        = ((this.saltGeneratorSet) || (configSaltGenerator == null))
                        ? this.saltGenerator : configSaltGenerator;
                this.providerName
                        = ((this.providerNameSet) || (configProviderName == null))
                        ? this.providerName : configProviderName;
                this.provider
                        = ((this.providerSet) || (configProvider == null))
                        ? this.provider : configProvider;

            }

            /*
             * If the encryptor was not set a salt generator in any way,
             * it is time to apply its default value.
             */
            if (this.saltGenerator == null) {
                this.saltGenerator = new RandomSaltGenerator();
            }

            try {

                // Password cannot be null.
                if (this.password == null) {
                    throw new EncryptionInitializationException(
                            "Password not set for Password Based Encryptor");
                }

                // Normalize password to NFC form
                final char[] normalizedPassword = Normalizer.normalize(new String(this.password), Normalizer.Form.NFC).toCharArray();

                /*
                 * Encryption and decryption Ciphers are created the usual way.
                 */
                final PBEKeySpec pbeKeySpec = new PBEKeySpec(normalizedPassword);

                // We don't need the char[] passwords anymore -> clean!
                cleanPassword(this.password);
                cleanPassword(normalizedPassword);

                if (this.provider != null) {

                    final SecretKeyFactory factory
                            = SecretKeyFactory.getInstance(
                                    this.algorithm,
                                    this.provider);

                    this.key = factory.generateSecret(pbeKeySpec);

                    this.encryptCipher
                            = Cipher.getInstance(this.algorithm, this.provider);
                    this.decryptCipher
                            = Cipher.getInstance(this.algorithm, this.provider);

                } else if (this.providerName != null) {

                    final SecretKeyFactory factory
                            = SecretKeyFactory.getInstance(
                                    this.algorithm,
                                    this.providerName);

                    this.key = factory.generateSecret(pbeKeySpec);

                    this.encryptCipher
                            = Cipher.getInstance(this.algorithm, this.providerName);
                    this.decryptCipher
                            = Cipher.getInstance(this.algorithm, this.providerName);

                } else {

                    final SecretKeyFactory factory
                            = SecretKeyFactory.getInstance(this.algorithm);

                    this.key = factory.generateSecret(pbeKeySpec);

                    this.encryptCipher = Cipher.getInstance(this.algorithm);
                    this.decryptCipher = Cipher.getInstance(this.algorithm);

                }

            } catch (EncryptionInitializationException e) {
                throw e;
            } catch (Throwable t) {
                throw new EncryptionInitializationException(t);
            }

            // The salt size for the chosen algorithm is set to be equal 
            // to the algorithm's block size (if it is a block algorithm).
            final int algorithmBlockSize = this.encryptCipher.getBlockSize();
            if (algorithmBlockSize > 0) {
                this.saltSizeBytes = algorithmBlockSize;
            }

            this.usingFixedSalt = (this.saltGenerator instanceof FixedSaltGenerator);

            if (this.usingFixedSalt) {

                // Create salt
                this.fixedSaltInUse
                        = this.saltGenerator.generateSalt(this.saltSizeBytes);

                /*
                 * Initialize the Cipher objects themselves. Due to the fact that
                 * we will be using a fixed salt, this can be done just once, which
                 * means a better performance at the encrypt/decrypt methods. 
                 */
                final PBEParameterSpec parameterSpec
                        = new PBEParameterSpec(this.fixedSaltInUse, this.keyObtentionIterations);

                try {

                    this.encryptCipher.init(
                            Cipher.ENCRYPT_MODE, this.key, parameterSpec);
                    this.decryptCipher.init(
                            Cipher.DECRYPT_MODE, this.key, parameterSpec);

                } catch (final Exception e) {
                    // If encryption fails, it is more secure not to return any 
                    // information about the cause in nested exceptions. Simply fail.
                    throw new EncryptionOperationNotPossibleException();
                }

            }

            this.initialized = true;

        }

    }

    private synchronized void resolveConfigurationPassword() {

        // Double-check to avoid synchronization issues
        if (!this.initialized) {

            if (this.config != null && !this.passwordSet) {

                // Get the configured password. If the config object implements
                // CleanablePassword, we get password directly as a char array
                // in order to avoid unnecessary creation of immutable Strings
                // containing such password.
                char[] configPassword = null;
                if (this.config instanceof PBECleanablePasswordConfig) {
                    configPassword = ((PBECleanablePasswordConfig) this.config).getPasswordCharArray();
                } else {
                    final String configPwd = this.config.getPassword();
                    if (configPwd != null) {
                        configPassword = configPwd.toCharArray();
                    }
                }

                if (configPassword != null) {
                    CommonUtils.validateIsTrue(configPassword.length > 0,
                            "Password cannot be set empty");
                }

                if (configPassword != null) {
                    this.password = new char[configPassword.length];
                    System.arraycopy(configPassword, 0, this.password, 0, configPassword.length);
                    this.passwordSet = true;
                    cleanPassword(configPassword);
                }

                // Finally, clean the password at the configuration object
                if (this.config instanceof PBECleanablePasswordConfig) {
                    ((PBECleanablePasswordConfig) this.config).cleanPassword();
                }

            }

        }

    }

    private static void cleanPassword(final char[] password) {
        if (password != null) {
            synchronized (password) {
                final int pwdLength = password.length;
                for (int i = 0; i < pwdLength; i++) {
                    password[i] = (char) 0;
                }
            }
        }
    }

    /**
     * <p>
     * Encrypts a message using the specified configuration.
     * </p>
     * <p>
     * The mechanisms applied to perform the encryption operation are described
     * in <a href="http://www.rsasecurity.com/rsalabs/node.asp?id=2127"
     * target="_blank">PKCS &#035;5: Password-Based Cryptography Standard</a>.
     * </p>
     * <p>
     * This encryptor uses a salt for each encryption operation. The size of the
     * salt depends on the algorithm being used. This salt is used for creating
     * the encryption key and, if generated by a random generator, it is also
     * appended unencrypted at the beginning of the results so that a decryption
     * operation can be performed.
     * </p>
     * <p>
     * <b>If a random salt generator is used, two encryption results for the
     * same message will always be different (except in the case of random salt
     * coincidence)</b>. This may enforce security by difficulting brute force
     * attacks on sets of data at a time and forcing attackers to perform a
     * brute force attack on each separate piece of encrypted data.
     * </p>
     *
     * @param message the byte array message to be encrypted
     * @return the result of encryption
     * @throws EncryptionOperationNotPossibleException if the encryption
     * operation fails, ommitting any further information about the cause for
     * security reasons.
     * @throws EncryptionInitializationException if initialization could not be
     * correctly done (for example, no password has been set).
     */
    public byte[] encrypt(final byte[] message)
            throws EncryptionOperationNotPossibleException {

        if (message == null) {
            return null;
        }

        // Check initialization
        if (!isInitialized()) {
            initialize();
        }

        try {

            final byte[] salt;
            final byte[] encryptedMessage;
            if (this.usingFixedSalt) {

                salt = this.fixedSaltInUse;

                synchronized (this.encryptCipher) {
                    encryptedMessage = this.encryptCipher.doFinal(message);
                }

            } else {

                // Create salt
                salt = this.saltGenerator.generateSalt(this.saltSizeBytes);

                /*
                 * Perform encryption using the Cipher
                 */
                final PBEParameterSpec parameterSpec
                        = new PBEParameterSpec(salt, this.keyObtentionIterations);

                synchronized (this.encryptCipher) {
                    this.encryptCipher.init(
                            Cipher.ENCRYPT_MODE, this.key, parameterSpec);
                    encryptedMessage = this.encryptCipher.doFinal(message);
                }

            }

            // Finally we build an array containing both the unencrypted salt
            // and the result of the encryption. This is done only
            // if the salt generator we are using specifies to do so.
            if (this.saltGenerator.includePlainSaltInEncryptionResults()) {

                // Insert unhashed salt before the encryption result
                return CommonUtils.appendArrays(salt, encryptedMessage);

            }

            return encryptedMessage;

        } catch (final InvalidKeyException e) {
            // The problem could be not having the unlimited strength policies
            // installed, so better give a usefull error message.
            handleInvalidKeyException(e);
            throw new EncryptionOperationNotPossibleException();
        } catch (final Exception e) {
            // If encryption fails, it is more secure not to return any 
            // information about the cause in nested exceptions. Simply fail.
            throw new EncryptionOperationNotPossibleException();
        }

    }

    /**
     * <p>
     * Decrypts a message using the specified configuration.
     * </p>
     * <p>
     * The mechanisms applied to perform the decryption operation are described
     * in <a href="http://www.rsasecurity.com/rsalabs/node.asp?id=2127"
     * target="_blank">PKCS &#035;5: Password-Based Cryptography Standard</a>.
     * </p>
     * <p>
     * If a random salt generator is used, this decryption operation will expect
     * to find an unencrypted salt at the beginning of the encrypted input, so
     * that the decryption operation can be correctly performed (there is no
     * other way of knowing it).
     * </p>
     *
     * @param encryptedMessage the byte array message to be decrypted
     * @return the result of decryption
     * @throws EncryptionOperationNotPossibleException if the decryption
     * operation fails, ommitting any further information about the cause for
     * security reasons.
     * @throws EncryptionInitializationException if initialization could not be
     * correctly done (for example, no password has been set).
     */
    public byte[] decrypt(final byte[] encryptedMessage)
            throws EncryptionOperationNotPossibleException {

        if (encryptedMessage == null) {
            return null;
        }

        // Check initialization
        if (!isInitialized()) {
            initialize();
        }

        if (this.saltGenerator.includePlainSaltInEncryptionResults()) {
            // Check that the received message is bigger than the salt
            if (encryptedMessage.length <= this.saltSizeBytes) {
                throw new EncryptionOperationNotPossibleException();
            }
        }

        try {

            // If we are using a salt generator which specifies the salt
            // to be included into the encrypted message itself, get it from 
            // there. If not, the salt is supposed to be fixed and thus the
            // salt generator can be safely asked for it again.
            byte[] salt = null;
            byte[] encryptedMessageKernel = null;
            if (this.saltGenerator.includePlainSaltInEncryptionResults()) {

                final int saltStart = 0;
                final int saltSize
                        = (this.saltSizeBytes < encryptedMessage.length ? this.saltSizeBytes : encryptedMessage.length);
                final int encMesKernelStart
                        = (this.saltSizeBytes < encryptedMessage.length ? this.saltSizeBytes : encryptedMessage.length);
                final int encMesKernelSize
                        = (this.saltSizeBytes < encryptedMessage.length ? (encryptedMessage.length - this.saltSizeBytes) : 0);

                salt = new byte[saltSize];
                encryptedMessageKernel = new byte[encMesKernelSize];

                System.arraycopy(encryptedMessage, saltStart, salt, 0, saltSize);
                System.arraycopy(encryptedMessage, encMesKernelStart, encryptedMessageKernel, 0, encMesKernelSize);

            } else if (!this.usingFixedSalt) {

                salt = this.saltGenerator.generateSalt(this.saltSizeBytes);
                encryptedMessageKernel = encryptedMessage;

            } else {
                // this.usingFixedSalt == true

                salt = this.fixedSaltInUse;
                encryptedMessageKernel = encryptedMessage;

            }

            final byte[] decryptedMessage;
            if (this.usingFixedSalt) {

                /*
                 * Fixed salt is being used, therefore no initialization supposedly needed
                 */
                synchronized (this.decryptCipher) {
                    decryptedMessage
                            = this.decryptCipher.doFinal(encryptedMessageKernel);
                }

            } else {

                /*
                 * Perform decryption using the Cipher
                 */
                final PBEParameterSpec parameterSpec
                        = new PBEParameterSpec(salt, this.keyObtentionIterations);

                synchronized (this.decryptCipher) {
                    this.decryptCipher.init(
                            Cipher.DECRYPT_MODE, this.key, parameterSpec);
                    decryptedMessage
                            = this.decryptCipher.doFinal(encryptedMessageKernel);
                }

            }

            // Return the results
            return decryptedMessage;

        } catch (final InvalidKeyException e) {
            // The problem could be not having the unlimited strength policies
            // installed, so better give a usefull error message.
            handleInvalidKeyException(e);
            throw new EncryptionOperationNotPossibleException();
        } catch (final Exception e) {
            // If decryption fails, it is more secure not to return any 
            // information about the cause in nested exceptions. Simply fail.
            throw new EncryptionOperationNotPossibleException();
        }

    }

    private void handleInvalidKeyException(final InvalidKeyException e) {

        if ((e.getMessage() != null)
                && ((e.getMessage().toUpperCase().indexOf("KEY SIZE") != -1))) {

            throw new EncryptionOperationNotPossibleException(
                    "Encryption raised an exception. A possible cause is "
                    + "you are using strong encryption algorithms and "
                    + "you have not installed the Java Cryptography "
                    + "Extension (JCE) Unlimited Strength Jurisdiction "
                    + "Policy Files in this Java Virtual Machine");

        }

    }
}
