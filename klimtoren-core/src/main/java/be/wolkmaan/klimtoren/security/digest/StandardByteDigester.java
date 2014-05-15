/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security.digest;

import be.wolkmaan.klimtoren.security.digest.config.DigesterConfig;
import be.wolkmaan.klimtoren.security.exceptions.AlreadyInitializedException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionOperationNotPossibleException;
import be.wolkmaan.klimtoren.security.salt.RandomSaltGenerator;
import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import be.wolkmaan.klimtoren.shared.CommonUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;

/**
 *
 * @author karl
 */
public class StandardByteDigester implements ByteDigester {

    public static final String DEFAULT_ALGORITHM = "MD5";
    public static final int DEFAULT_SALT_SIZE_BYTES = 8;
    public static final int DEFAULT_ITERATIONS = 1000;

    private String algorithm = DEFAULT_ALGORITHM;
    private int saltSizeBytes = DEFAULT_SALT_SIZE_BYTES;
    private int iterations = DEFAULT_ITERATIONS;
    private SaltGenerator saltGenerator = null;
    private String providerName = null;
    private Provider provider = null;
    private boolean invertPositionOfSaltInMessageBeforeDigesting = false;
    private boolean invertPositionOfPlainSaltInEncryptionResults = false;
    private boolean useLenientSaltSizeCheck = false;

    private DigesterConfig config = null;

    private boolean algorithmSet = false;
    private boolean saltSizeBytesSet = false;
    private boolean iterationsSet = false;
    private boolean saltGeneratorSet = false;
    private boolean providerNameSet = false;
    private boolean providerSet = false;
    private boolean invertPositionOfSaltInMessageBeforeDigestingSet = false;
    private boolean invertPositionOfPlainSaltInEncryptionResultsSet = false;
    private boolean useLenientSaltSizeCheckSet = false;

    private boolean initialized = false;

    private boolean useSalt = true;

    private MessageDigest md = null;

    private int digestLengthBytes = 0;

    public StandardByteDigester() {
        super();
    }

    public void setConfig(DigesterConfig config) {
        CommonUtils.validateNotNull(config, "Config cannot be set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.config = config;
    }

    public synchronized void setAlgorithm(final String algorithm) {
        CommonUtils.validateNotEmpty(algorithm, "Algorithm cannot be empty");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.algorithm = algorithm;
        this.algorithmSet = true;
    }

    public synchronized void setSaltSizeBytes(final int saltSizeBytes) {
        CommonUtils.validateIsTrue(saltSizeBytes >= 0, "Salt size in bytes must be non-negative.");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.saltSizeBytes = saltSizeBytes;
        this.useSalt = (saltSizeBytes > 0);
        this.saltSizeBytesSet = true;
    }

    public synchronized void setIterations(final int iterations) {
        CommonUtils.validateIsTrue(iterations > 0, "Number of iterations must be greater than zero");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.iterations = iterations;
        this.iterationsSet = true;
    }

    public synchronized void setSaltGenerator(final SaltGenerator saltGenerator) {
        CommonUtils.validateNotNull(saltGenerator, "Salt generator cannot be set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.saltGenerator = saltGenerator;
        this.saltGeneratorSet = true;
    }

    public synchronized void setProviderName(final String providerName) {
        CommonUtils.validateNotNull(providerName, "Provider name cannot be set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.providerName = providerName;
        this.providerNameSet = true;
    }

    public synchronized void setProvider(final Provider provider) {
        CommonUtils.validateNotNull(provider, "Provider cannot be set null");
        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.provider = provider;
        this.providerSet = true;
    }

    public synchronized void setInvertPositionOfSaltInMessageBeforeDigesting(
            final boolean invertPositionOfSaltInMessageBeforeDigesting) {

        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.invertPositionOfSaltInMessageBeforeDigesting = invertPositionOfSaltInMessageBeforeDigesting;
        this.invertPositionOfSaltInMessageBeforeDigestingSet = true;
    }

    public synchronized void setInvertPositionOfPlainSaltInEncryptionResults(
            final boolean invertPositionOfPlainSaltInEncryptionResults) {

        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.invertPositionOfPlainSaltInEncryptionResults = invertPositionOfPlainSaltInEncryptionResults;
        this.invertPositionOfPlainSaltInEncryptionResultsSet = true;

    }

    public synchronized void setUseLenientSaltSizeCheck(final boolean useLenientSaltSizeCheck) {

        if (isInitialized()) {
            throw new AlreadyInitializedException();
        }
        this.useLenientSaltSizeCheck = useLenientSaltSizeCheck;
        this.useLenientSaltSizeCheckSet = true;

    }

    /* CLONE */
    StandardByteDigester cloneDigester() {

        // Check initialization
        if (!isInitialized()) {
            initialize();
        }

        final StandardByteDigester cloned = new StandardByteDigester();
        if (CommonUtils.isNotEmpty(this.algorithm)) {
            cloned.setAlgorithm(this.algorithm);
        }
        cloned.setInvertPositionOfPlainSaltInEncryptionResults(this.invertPositionOfPlainSaltInEncryptionResults);
        cloned.setInvertPositionOfSaltInMessageBeforeDigesting(this.invertPositionOfSaltInMessageBeforeDigesting);
        cloned.setIterations(this.iterations);
        if (this.provider != null) {
            cloned.setProvider(this.provider);
        }
        if (this.providerName != null) {
            cloned.setProviderName(this.providerName);
        }
        if (this.saltGenerator != null) {
            cloned.setSaltGenerator(this.saltGenerator);
        }
        cloned.setSaltSizeBytes(this.saltSizeBytes);
        cloned.setUseLenientSaltSizeCheck(this.useLenientSaltSizeCheck);

        return cloned;

    }

    public boolean isInitialized() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void initialize() {
        if (!this.initialized) {

            if (this.config != null) {

                final String configAlgorithm = this.config.getAlgorithm();
                if (configAlgorithm != null) {
                    CommonUtils.validateNotEmpty(configAlgorithm, "Algorithm cannot be empty");
                }

                final Integer configSaltSizeBytes = this.config.getSaltSizeBytes();
                if (configSaltSizeBytes != null) {
                    CommonUtils.validateIsTrue(configSaltSizeBytes.intValue() >= 0,
                            "Salt size in bytes must be non-negative");
                }

                final Integer configIterations = this.config.getIterations();
                if (configIterations != null) {
                    CommonUtils.validateIsTrue(configIterations.intValue() > 0,
                            "Number of iterations must be greater than zero");
                }

                final SaltGenerator configSaltGenerator = this.config.getSaltGenerator();

                final String configProviderName = this.config.getProviderName();
                if (configProviderName != null) {
                    CommonUtils.validateNotEmpty(configProviderName, "Provider name cannot be empty");
                }

                final Provider configProvider = this.config.getProvider();

                final Boolean configInvertPositionOfSaltInMessageBeforeDigesting
                        = this.config.getInvertPositionOfSaltInMessageBeforeDigesting();

                final Boolean configInvertPositionOfPlainSaltInEncryptionResults
                        = this.config.getInvertPositionOfPlainSaltInEncryptionResults();

                final Boolean configUseLenientSaltSizeCheck
                        = this.config.getUseLenientSaltSizeCheck();

                this.algorithm
                        = ((this.algorithmSet) || (configAlgorithm == null))
                        ? this.algorithm : configAlgorithm;
                this.saltSizeBytes
                        = ((this.saltSizeBytesSet) || (configSaltSizeBytes == null))
                        ? this.saltSizeBytes : configSaltSizeBytes.intValue();
                this.iterations
                        = ((this.iterationsSet) || (configIterations == null))
                        ? this.iterations : configIterations.intValue();
                this.saltGenerator
                        = ((this.saltGeneratorSet) || (configSaltGenerator == null))
                        ? this.saltGenerator : configSaltGenerator;
                this.providerName
                        = ((this.providerNameSet) || (configProviderName == null))
                        ? this.providerName : configProviderName;
                this.provider
                        = ((this.providerSet) || (configProvider == null))
                        ? this.provider : configProvider;
                this.invertPositionOfSaltInMessageBeforeDigesting
                        = ((this.invertPositionOfSaltInMessageBeforeDigestingSet) || (configInvertPositionOfSaltInMessageBeforeDigesting == null))
                        ? this.invertPositionOfSaltInMessageBeforeDigesting : configInvertPositionOfSaltInMessageBeforeDigesting.booleanValue();
                this.invertPositionOfPlainSaltInEncryptionResults
                        = ((this.invertPositionOfPlainSaltInEncryptionResultsSet) || (configInvertPositionOfPlainSaltInEncryptionResults == null))
                        ? this.invertPositionOfPlainSaltInEncryptionResults : configInvertPositionOfPlainSaltInEncryptionResults.booleanValue();
                this.useLenientSaltSizeCheck
                        = ((this.useLenientSaltSizeCheckSet) || (configUseLenientSaltSizeCheck == null))
                        ? this.useLenientSaltSizeCheck : configUseLenientSaltSizeCheck.booleanValue();

            }

            if (this.saltGenerator == null) {
                this.saltGenerator = new RandomSaltGenerator();
            }

            if (this.useLenientSaltSizeCheck) {
                if (!this.saltGenerator.includePlainSaltInEncryptionResults()) {
                    throw new EncryptionInitializationException(
                            "The configured Salt Generator ("
                            + this.saltGenerator.getClass().getName()
                            + ") does not include plain salt "
                            + "in encryption results, which is not compatible"
                            + "with setting the salt size checking behaviour to \"lenient\".");
                }
            }

            try {
                if (this.provider != null) {
                    this.md
                            = MessageDigest.getInstance(
                                    this.algorithm,
                                    this.provider);
                } else if (this.providerName != null) {
                    this.md
                            = MessageDigest.getInstance(
                                    this.algorithm,
                                    this.providerName);
                } else {
                    this.md = MessageDigest.getInstance(this.algorithm);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new EncryptionInitializationException(e);
            } catch (NoSuchProviderException e) {
                throw new EncryptionInitializationException(e);
            }

            this.digestLengthBytes = this.md.getDigestLength();
            if (this.digestLengthBytes <= 0) {
                throw new EncryptionInitializationException(
                        "The configured algorithm ("
                        + this.algorithm + ") or its provider do  "
                        + "not allow knowing the digest length beforehand "
                        + "(getDigestLength() operation), which is not compatible"
                        + "with setting the salt size checking behaviour to \"lenient\".");
            }
            this.initialized = true;
        }
    }

    public byte[] digest(byte[] message) {
        if (message == null) {
            return null;
        }
        if (!isInitialized()) {
            initialize();
        }
        byte[] salt = null;
        if (this.useSalt) {
            salt = this.saltGenerator.generateSalt(this.saltSizeBytes);
        }
        return digest(message, salt);
    }

    private byte[] digest(final byte[] message, final byte[] salt) {
        try {
            byte[] digest = null;
            synchronized (this.md) {
                this.md.reset();
                if (salt != null) {
                    if (!this.invertPositionOfSaltInMessageBeforeDigesting) {
                        this.md.update(salt);
                        this.md.update(message);
                    } else {
                        this.md.update(message);
                        this.md.update(salt);
                    }
                } else {
                    this.md.update(message);
                }
                digest = this.md.digest();
                for (int i = 0; i < (this.iterations - 1); i++) {
                    this.md.reset();
                    digest = this.md.digest(digest);
                }
            }
            if (this.saltGenerator.includePlainSaltInEncryptionResults() && salt != null) {
                if (!this.invertPositionOfPlainSaltInEncryptionResults) {
                    return CommonUtils.appendArrays(salt, digest);
                }
                return CommonUtils.appendArrays(digest, salt);
            }
            return digest;
        } catch (Exception e) {
            throw new EncryptionOperationNotPossibleException();
        }

    }
    
    public boolean matches(final byte[] message, final byte[] digest) {
        if (message == null) {
            return (digest == null);
        } else if (digest == null) {
            return false;
        }
        
        if (!isInitialized()) {
            initialize();
        }           
        try {
            byte[] salt = null;
            if (this.useSalt) {
                if (this.saltGenerator.includePlainSaltInEncryptionResults()) {
                    int digestSaltSize = this.saltSizeBytes;
                    if (this.digestLengthBytes > 0) {
                        if (this.useLenientSaltSizeCheck) {
                            if (digest.length < this.digestLengthBytes) {
                                throw new EncryptionOperationNotPossibleException();
                            }
                            digestSaltSize = digest.length - this.digestLengthBytes;
                        } else {
                            if (digest.length != (this.digestLengthBytes + this.saltSizeBytes)) {
                                throw new EncryptionOperationNotPossibleException();
                            }
                        }
                    } else {
                        if (digest.length < this.saltSizeBytes) {
                            throw new EncryptionOperationNotPossibleException();
                        }
                    }
                    
                    if (!this.invertPositionOfPlainSaltInEncryptionResults) {
                        salt = new byte[digestSaltSize];
                        System.arraycopy(digest, 0, salt, 0, digestSaltSize);
                    } else {
                        salt = new byte[digestSaltSize];
                        System.arraycopy(digest, digest.length - digestSaltSize, salt, 0, digestSaltSize);
                    }
                    
                } else {
                    salt = this.saltGenerator.generateSalt(this.saltSizeBytes);
                }
            }
            final byte[] encryptedMessage = digest(message, salt);
            return (digestsAreEqual(encryptedMessage, digest));
        
        } catch (Exception e) {
            throw new EncryptionOperationNotPossibleException();
        }
        
    }

    
    // Time-constant comparison of byte arrays
    private static boolean digestsAreEqual(byte[] a, byte[] b) {
        if (a == null || b == null) {
            return false;
        }
        final int aLen = a.length;
        if (b.length != aLen) {
            return false;
        }
        int match = 0;
        for (int i = 0; i < aLen; i++) {
            match |= a[i] ^ b[i];
        }
        return (match == 0);
        
    }
}
