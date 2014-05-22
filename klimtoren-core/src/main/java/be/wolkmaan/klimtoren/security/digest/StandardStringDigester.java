/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security.digest;

import be.wolkmaan.klimtoren.security.digest.config.DigesterConfig;
import be.wolkmaan.klimtoren.security.digest.config.StringDigesterConfig;
import be.wolkmaan.klimtoren.security.exceptions.AlreadyInitializedException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionOperationNotPossibleException;
import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import be.wolkmaan.klimtoren.shared.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.security.Provider;
import java.text.Normalizer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author karl I. Encrypt passwords using one-way techniques, this is, digests.
 *
 * II. Match input and stored passwords by comparing digests, not unencrypted
 * strings.
 *
 * III. Use a salt containing at least 8 random bytes, and attach these random
 * bytes, undigested, to the result.
 *
 * IV. Iterate the hash function at least 1,000 times.
 *
 * V. Prior to digesting, perform string-to-byte sequence translation using a
 * fixed encoding, preferably UTF-8.
 *
 * VI. Finally, apply BASE64 encoding and store the digest as an US-ASCII
 * character string.
 */
@Slf4j
public class StandardStringDigester implements StringDigester {

    public static final String MESSAGE_CHARSET = "UTF-8";
    public static final String DIGEST_CHARSET = "US-ASCII";
    public static final boolean DEFAULT_UNICODE_NORMALIZATION_IGNORED = false;
    public static final String DEFAULT_STRING_OUTPUT_TYPE = CommonUtils.STRING_OUTPUT_TYPE_BASE64;

    private final StandardByteDigester byteDigester;
    private StringDigesterConfig stringDigesterConfig = null;

    private boolean unicodeNormalizationIgnored = DEFAULT_UNICODE_NORMALIZATION_IGNORED;
    private String stringOutputType = DEFAULT_STRING_OUTPUT_TYPE;
    private boolean stringOutputTypeBase64 = true;

    private String prefix = null;
    private String suffix = null;

    private boolean unicodeNormalizationIgnoredSet = false;
    private boolean stringOutputTypeSet = false;
    private boolean prefixSet = false;
    private boolean suffixSet = false;
    
    private final Base64 base64;
    
    public StandardStringDigester() {
        super();
        this.byteDigester = new StandardByteDigester();
        this.base64 = new Base64();
    }
    
    private StandardStringDigester(final StandardByteDigester standardByteDigester) {
        super();
        this.byteDigester = standardByteDigester;
        this.base64 = new Base64();
    }
    
    public synchronized void setConfig(final DigesterConfig config) {
        this.byteDigester.setConfig(config);
        if((config != null) && (config instanceof StringDigesterConfig)) {
            this.stringDigesterConfig = (StringDigesterConfig)config;
        }
    }
    public void setAlgorithm(final String algoritm) {
        this.byteDigester.setAlgorithm(algoritm);
    }
    public void setSaltSizeBytes(final int saltSizeBytes) {
        this.byteDigester.setSaltSizeBytes(saltSizeBytes);
    }
    public void setIterations(final int iterations) {
        this.byteDigester.setIterations(iterations);
    }
    public void setSaltGenerator(final SaltGenerator saltGenerator) {
        this.byteDigester.setSaltGenerator(saltGenerator);
    }
    public void setProviderName(final String providerName) {
        this.byteDigester.setProviderName(providerName);
    }
    public void setProvider(final Provider provider) {
        this.byteDigester.setProvider(provider);
    }
    public synchronized void setInvertPositionOfSaltInMessageBeforeDigesting(
            final boolean invertPositionOfSaltInMessageBeforeDigesting) {
        this.byteDigester.setInvertPositionOfSaltInMessageBeforeDigesting(invertPositionOfSaltInMessageBeforeDigesting);
    }
    public synchronized void setInvertPositionOfPlainSaltInEncryptionResults(
            final boolean invertPositionOfPlainSaltInEncryptionResults) {
        this.byteDigester.setInvertPositionOfPlainSaltInEncryptionResults(invertPositionOfPlainSaltInEncryptionResults);
    }
    public synchronized void setUseLenientSaltSizeCheck(final boolean useLenientSaltSizeCheck) {
        this.byteDigester.setUseLenientSaltSizeCheck(useLenientSaltSizeCheck);
    }
    public synchronized void setUnicodeNormalizationIgnored(final boolean unicodeNormalizationIgnored) {
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.unicodeNormalizationIgnored = unicodeNormalizationIgnored;
        this.unicodeNormalizationIgnoredSet = true;
    }
    public synchronized void setStringOutputType(final String stringOutputType) {
        CommonUtils.validateNotEmpty(stringOutputType, "String output type cannot be set empty");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.stringOutputType = 
            CommonUtils.getStandardStringOutputType(stringOutputType);
        this.stringOutputTypeSet = true;
    }
    public synchronized void setPrefix(final String prefix) {
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.prefix = prefix;
        this.prefixSet = true;
    }
    public synchronized void setSuffix(final String suffix) {
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.suffix = suffix;
        this.suffixSet = true;
    }
    
    /* CLONE */
    StandardStringDigester cloneDigester() {
        
        // Check initialization
        if (!isInitialized()) {
            initialize();
        }
        
        final StandardStringDigester cloned = 
            new StandardStringDigester(this.byteDigester.cloneDigester());
        cloned.setPrefix(this.prefix);
        cloned.setSuffix(this.suffix);
        if (CommonUtils.isNotEmpty(this.stringOutputType)) {
            cloned.setStringOutputType(this.stringOutputType);
        }
        cloned.setUnicodeNormalizationIgnored(this.unicodeNormalizationIgnored);
        
        return cloned;
        
    }
    
    public boolean isInitialized() {
        return this.byteDigester.isInitialized();
    }
    public synchronized void initialize() {
        if (!this.isInitialized()) {
            if (this.stringDigesterConfig != null) {              
                final Boolean configUnicodeNormalizationIgnored = 
                    this.stringDigesterConfig.isUnicodeNormalizationIgnored();
                final String configStringOutputType = 
                    this.stringDigesterConfig.getStringOutputType();
                final String configPrefix = 
                    this.stringDigesterConfig.getPrefix();
                final String configSuffix =
                    this.stringDigesterConfig.getSuffix();
                this.unicodeNormalizationIgnored = 
                    ((this.unicodeNormalizationIgnoredSet) || (configUnicodeNormalizationIgnored == null))?
                            this.unicodeNormalizationIgnored : configUnicodeNormalizationIgnored;
                this.stringOutputType = 
                    ((this.stringOutputTypeSet) || (configStringOutputType == null))?
                            this.stringOutputType : configStringOutputType;
                this.prefix =
                    ((this.prefixSet) || (configPrefix == null))?
                            this.prefix : configPrefix;
                this.suffix =
                    ((this.suffixSet) || (configSuffix == null))?
                            this.suffix : configSuffix;                
            }           
            this.stringOutputTypeBase64 =
                (CommonUtils.STRING_OUTPUT_TYPE_BASE64.
                    equalsIgnoreCase(this.stringOutputType));
            this.byteDigester.initialize();
        }
    }
    @Override
    public String digest(final String message) {

        if (message == null) {
            return null;
        }

        // Check initialization
        if (!isInitialized()) {
            initialize();
        }
        
        try {

            // Normalize Unicode message to NFC form
            String normalizedMessage;
            if (! this.unicodeNormalizationIgnored) {
                normalizedMessage = Normalizer.normalize(message, Normalizer.Form.NFC);
            } else {
                normalizedMessage = message;
            }
            
            // The input String is converted into bytes using MESSAGE_CHARSET
            // as a fixed charset to avoid problems with different platforms
            // having different default charsets (see MESSAGE_CHARSET doc).
            final byte[] messageBytes = normalizedMessage.getBytes(MESSAGE_CHARSET);

            // The StandardByteDigester does its job.
            byte[] digest = this.byteDigester.digest(messageBytes);

            // We build the result variable
            final StringBuffer result = new StringBuffer();
            
            if (this.prefix != null) {
                // Prefix is added
                result.append(this.prefix);
            }
            
            // We encode the result in BASE64 or HEXADECIMAL so that we obtain
            // the safest result String possible.
            if (this.stringOutputTypeBase64) {
                digest = this.base64.encode(digest);
                result.append(new String(digest, DIGEST_CHARSET)); 
            } else {
                result.append(CommonUtils.toHexadecimal(digest));
            }
            
            if (this.suffix != null) {
                // Suffix is added
                result.append(this.suffix);
            }
            
            return result.toString(); 

        } catch (EncryptionInitializationException | EncryptionOperationNotPossibleException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            // If digest fails, it is more secure not to return any information
            // about the cause in nested exceptions. Simply fail.
            throw new EncryptionOperationNotPossibleException();
        } 
    }
    
    
    @Override
    public boolean matches(final String message, final String digest) {

        String processedDigest = digest;
        
        if (processedDigest != null) {
            if (this.prefix != null) {
                if (!processedDigest.startsWith(this.prefix)) {
                    throw new EncryptionOperationNotPossibleException(
                            "Digest does not start with required prefix \"" + this.prefix + "\"");
                }
                processedDigest = processedDigest.substring(this.prefix.length()); 
            }
            if (this.suffix != null) {
                if (!processedDigest.endsWith(this.suffix)) {
                    throw new EncryptionOperationNotPossibleException(
                            "Digest does not end with required suffix \"" + this.suffix + "\"");
                }
                processedDigest = processedDigest.substring(0, processedDigest.length() - this.suffix.length());
            }
        }
        

        if (message == null) {
            return (processedDigest == null);
        } else if (processedDigest == null) {
            return false;
        }

        
        // Check initialization
        if (!isInitialized()) {
            initialize();
        }
        
        try {

            // Normalize Unicode message to NFC form
            String normalizedMessage;
            if (! this.unicodeNormalizationIgnored) {
                normalizedMessage = Normalizer.normalize(message, Normalizer.Form.NFC);
            } else {
                normalizedMessage = message;
            }
            
            // We get a valid byte array from the message, in the 
            // fixed MESSAGE_CHARSET that the digest operations use.
            final byte[] messageBytes = normalizedMessage.getBytes(MESSAGE_CHARSET);
            

            // The BASE64 or HEXADECIMAL encoding is reversed and the digest
            // is converted into a byte array.
            byte[] digestBytes;
            if (this.stringOutputTypeBase64) {
                // The digest must be a US-ASCII String BASE64-encoded
                digestBytes = processedDigest.getBytes(DIGEST_CHARSET);
                digestBytes = this.base64.decode(digestBytes);
            } else {
                digestBytes = CommonUtils.fromHexadecimal(processedDigest);
            }
            
            // The StandardByteDigester is asked to match message to digest.
            return this.byteDigester.matches(messageBytes, digestBytes); 
        
        } catch (EncryptionInitializationException | EncryptionOperationNotPossibleException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            // If digest fails, it is more secure not to return any information
            // about the cause in nested exceptions. Simply fail.
            throw new EncryptionOperationNotPossibleException();
        }

    }
}
