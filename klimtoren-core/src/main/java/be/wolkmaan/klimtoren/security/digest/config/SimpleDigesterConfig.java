/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.digest.config;

import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import java.security.Provider;

/**
 *
 * @author karl
 */
public class SimpleDigesterConfig implements DigesterConfig {
    
    private String algorithm = null;
    private Integer iterations = null;
    private Integer saltSizeBytes = null;
    private SaltGenerator saltGenerator = null;
    private String providerName = null;
    private Provider provider = null;
    private Boolean invertPositionOfSaltInMessageBeforeDigesting = null;
    private Boolean invertPositionOfPlainSaltInEncryptionResults = null;
    private Boolean useLenientSaltSizeCheck = null;
    private Integer poolSize = null;
    

    /**
     * <p>
     * Creates a new <tt>SimpleDigesterConfig</tt> instance.
     * </p>
     */
    public SimpleDigesterConfig() {
        super();
    }
    
    /**
     * <p>
     * Sets the name of the algorithm.
     * </p>
     * <p>
     * This algorithm has to be supported by your security infrastructure, and
     * it should be allowed as an algorithm for creating
     * java.security.MessageDigest instances.
     * </p>
     * <p>
     * If you are specifying a security provider with {@link #setProvider(Provider)} or
     * {@link #setProviderName(String)}, this algorithm should be
     * supported by your specified provider.
     * </p>
     * <p>
     * If you are not specifying a provider, you will be able to use those
     * algorithms provided by the default security provider of your JVM vendor.
     * For valid names in the Sun JVM, see <a target="_blank" 
     *         href="http://java.sun.com/j2se/1.5.0/docs/guide/security/CryptoSpec.html#AppA">Java 
     *         Cryptography Architecture API Specification & 
     *         Reference</a>.
     * </p>
     * <p>
     * Determines the result of: {@link #getAlgorithm()}
     * </p>
     * 
     * @param algorithm the name of the algorithm.
     */
    public void setAlgorithm(final String algorithm) {
        this.algorithm = algorithm;
    }

    
    /**
     * <p>
     * Sets the number of hashing iterations.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getIterations()}
     * </p>
     * 
     * @param iterations the number of iterations.
     */
    public void setIterations(final Integer iterations) {
        this.iterations = iterations;
    }

    
    /**
     * <p>
     * Sets the number of hashing iterations.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getIterations()}
     * </p>
     * 
     * @since 1.4
     * 
     * @param iterations the number of iterations.
     */
    public void setIterations(final String iterations) {
        if (iterations != null) {
            try {
                this.iterations = new Integer(iterations);
            } catch (NumberFormatException e) {
                throw new EncryptionInitializationException(e);
            }
        } else {
            this.iterations = null;
        }
    }

    
    /**
     * <p>
     * Size in bytes of the salt to be used.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getSaltSizeBytes()}
     * </p>
     * 
     * @param saltSizeBytes the size of the salt, in bytes.
     */
    public void setSaltSizeBytes(final Integer saltSizeBytes) {
        this.saltSizeBytes = saltSizeBytes;
    }

    
    /**
     * <p>
     * Size in bytes of the salt to be used.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getSaltSizeBytes()}
     * </p>
     *
     * @since 1.4
     * 
     * @param saltSizeBytes the size of the salt, in bytes.
     */
    public void setSaltSizeBytes(final String saltSizeBytes) {
        if (saltSizeBytes != null) {
            try {
                this.saltSizeBytes = new Integer(saltSizeBytes);
            } catch (NumberFormatException e) {
                throw new EncryptionInitializationException(e);
            }
        } else {
            this.saltSizeBytes = null;
        }
    }

    
    /**
     * <p>
     * Sets the salt generator.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getSaltGenerator()}
     * </p>
     * 
     * @since 1.2
     * 
     * @param saltGenerator the salt generator.
     */
    public void setSaltGenerator(final SaltGenerator saltGenerator) {
        this.saltGenerator = saltGenerator;
    }

    
    /**
     * <p>
     * Sets the class name of the salt generator.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getSaltGenerator()}
     * </p>
     * 
     * @since 1.4
     * 
     * @param saltGeneratorClassName the name of the salt generator class.
     */
    public void setSaltGeneratorClassName(final String saltGeneratorClassName) {
        if (saltGeneratorClassName != null) {
            try {
                final Class saltGeneratorClass = 
                    Thread.currentThread().getContextClassLoader().loadClass(saltGeneratorClassName);
                this.saltGenerator = 
                    (SaltGenerator) saltGeneratorClass.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new EncryptionInitializationException(e);
            }
        } else {
            this.saltGenerator = null;
        }
    }

    
    /**
     * <p>
     * Sets the name of the security provider to be asked for the digest
     * algorithm. This provider should be already registered.
     * </p>
     * <p>
     * If both the <tt>providerName</tt> and <tt>provider</tt> properties
     * are set, only <tt>provider</tt> will be used, and <tt>providerName</tt>
     * will have no meaning for the digester object.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getProviderName()}
     * </p>
     * 
     * @since 1.3
     * 
     * @param providerName the name of the security provider.
     */
    public void setProviderName(final String providerName) {
        this.providerName = providerName;
    }
    
    
    /**
     * <p>
     * Sets the security provider to be used for obtaining the digest 
     * algorithm. This method is an alternative to 
     * both {@link #setProviderName(String)} and 
     * {@link #setProviderClassName(String)} and they should not be used 
     * altogether.
     * The provider specified with {@link #setProvider(Provider)} does not
     * have to be registered beforehand, and its use will not result in its
     * being registered.
     * </p>
     * <p>
     * If both the <tt>providerName</tt> and <tt>provider</tt> properties
     * are set, only <tt>provider</tt> will be used, and <tt>providerName</tt>
     * will have no meaning for the digester object.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getProvider()}
     * </p>
     * 
     * @since 1.3
     * 
     * @param provider the security provider object.
     */
    public void setProvider(final Provider provider) {
        this.provider = provider;
    }
    
    
    /**
     * <p>
     * Sets the class name for the security provider to be used for 
     * obtaining the digest algorithm. This method is an alternative to 
     * both {@link #setProviderName(String)} {@link #setProvider(Provider)} 
     * and they should not be used altogether.
     * The provider specified with {@link #setProviderClassName(String)} does not
     * have to be registered beforehand, and its use will not result in its
     * being registered.
     * </p>
     * <p>
     * If both the <tt>providerName</tt> and <tt>provider</tt> properties
     * are set, only <tt>provider</tt> will be used, and <tt>providerName</tt>
     * will have no meaning for the digester object.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getProvider()}
     * </p>
     * 
     * @since 1.4
     * 
     * @param providerClassName the name of the security provider class.
     */
    public void setProviderClassName(final String providerClassName) {
        if (providerClassName != null) {
            try {
                final Class providerClass = 
                    Thread.currentThread().getContextClassLoader().loadClass(providerClassName);
                this.provider = (Provider) providerClass.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new EncryptionInitializationException(e);
            }
        } else {
            this.provider = null;
        }
    }
    
    
    /**
     * <p>
     * Whether the salt bytes are to be appended after the 
     * message ones before performing the digest operation on the whole. The 
     * default behaviour is to insert those bytes before the message bytes, but 
     * setting this configuration item to <tt>true</tt> allows compatibility 
     * with some external systems and specifications (e.g. LDAP {SSHA}).
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getInvertPositionOfSaltInMessageBeforeDigesting()}
     * </p>
     * 
     * @since 1.7
     * 
     * @param invertPositionOfSaltInMessageBeforeDigesting
     *        whether salt will be appended after the message before applying 
     *        the digest operation on the whole, instead of inserted before it
     *        (which is the default).
     */
    public void setInvertPositionOfSaltInMessageBeforeDigesting(
            final Boolean invertPositionOfSaltInMessageBeforeDigesting) {
        this.invertPositionOfSaltInMessageBeforeDigesting = invertPositionOfSaltInMessageBeforeDigesting;
    }
    
    
    /**
     * <p>
     * Whether the plain (not hashed) salt bytes are to 
     * be appended after the digest operation result bytes. The default behaviour is 
     * to insert them before the digest result, but setting this configuration 
     * item to <tt>true</tt> allows compatibility with some external systems
     * and specifications (e.g. LDAP {SSHA}).
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getInvertPositionOfPlainSaltInEncryptionResults()}
     * </p>
     * 
     * @since 1.7
     * 
     * @param invertPositionOfPlainSaltInEncryptionResults
     *        whether plain salt will be appended after the digest operation 
     *        result instead of inserted before it (which is the 
     *        default).
     */
    public void setInvertPositionOfPlainSaltInEncryptionResults(
            final Boolean invertPositionOfPlainSaltInEncryptionResults) {
        this.invertPositionOfPlainSaltInEncryptionResults = invertPositionOfPlainSaltInEncryptionResults;
    }
    

    /**
     * <p>
     * Whether digest matching operations will allow matching
     * digests with a salt size different to the one configured in the "saltSizeBytes"
     * property. This is possible because digest algorithms will produce a fixed-size 
     * result, so the remaining bytes from the hashed input will be considered salt.
     * </p>
     * <p>
     * This will allow the digester to match digests produced in environments which do not
     * establish a fixed salt size as standard (for example, SSHA password encryption
     * in LDAP systems).  
     * </p>
     * <p>
     * The value of this property will <b>not</b> affect the creation of digests, 
     * which will always have a salt of the size established by the "saltSizeBytes" 
     * property. It will only affect digest matching.  
     * </p>
     * <p>
     * Setting this property to <tt>true</tt> is not compatible with {@link SaltGenerator}
     * implementations which return false for their 
     * {@link SaltGenerator#includePlainSaltInEncryptionResults()} property. 
     * </p>
     * <p>
     * Also, be aware that some algorithms or algorithm providers might not support
     * knowing the size of the digests beforehand, which is also incompatible with
     * a lenient behaviour.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getUseLenientSaltSizeCheck()}
     * </p>
     *
     * @since 1.7
     * 
     * @param useLenientSaltSizeCheck whether the digester will allow matching of 
     *        digests with different salt sizes than established or not (default 
     *        is false).
     */
    public void setUseLenientSaltSizeCheck(final Boolean useLenientSaltSizeCheck) {
        this.useLenientSaltSizeCheck = useLenientSaltSizeCheck;
    }
    

    /**
     * <p>
     * Sets the size of the pool of digesters to be created.
     * </p>
     * <p>
     * <b>This parameter will be ignored if used with a non-pooled digester</b>.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getPoolSize()}
     * </p>
     *
     * @since 1.7
     * 
     * @param poolSize the size of the pool to be used if this configuration is used with a
     *         pooled digester
     */
    public void setPoolSize(final Integer poolSize) {
        this.poolSize = poolSize;
    }
    

    /**
     * <p>
     * Sets the size of the pool of digesters to be created.
     * </p>
     * <p>
     * <b>This parameter will be ignored if used with a non-pooled digester</b>.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getPoolSize()}
     * </p>
     *
     * @since 1.7
     * 
     * @param poolSize the size of the pool to be used if this configuration is used with a
     *         pooled digester
     */
    public void setPoolSize(final String poolSize) {
        if (poolSize != null) {
            try {
                this.poolSize = new Integer(poolSize);
            } catch (NumberFormatException e) {
                throw new EncryptionInitializationException(e);
            }
        } else {
            this.poolSize = null;
        }
    }


    
    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    
    @Override
    public Integer getIterations() {
        return this.iterations;
    }

    
    @Override
    public Integer getSaltSizeBytes() {
        return this.saltSizeBytes;
    }
    
    
    @Override
    public SaltGenerator getSaltGenerator() {
        return this.saltGenerator;
    }
    
    @Override
    public String getProviderName() {
        return this.providerName;
    }
    
    @Override
    public Provider getProvider() {
        return this.provider;
    }
    
    @Override
    public Boolean getInvertPositionOfSaltInMessageBeforeDigesting() {
        return this.invertPositionOfSaltInMessageBeforeDigesting;
    }
    
    @Override
    public Boolean getInvertPositionOfPlainSaltInEncryptionResults() {
        return this.invertPositionOfPlainSaltInEncryptionResults;
    }

    @Override
    public Boolean getUseLenientSaltSizeCheck() {
        return this.useLenientSaltSizeCheck;
    }

    @Override
    public Integer getPoolSize() {
        return this.poolSize;
    }

    
}
