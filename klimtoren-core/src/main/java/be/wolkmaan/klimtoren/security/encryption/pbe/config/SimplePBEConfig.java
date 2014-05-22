/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security.encryption.pbe.config;

import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import be.wolkmaan.klimtoren.security.exceptions.PasswordAlreadyCleanedException;
import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import java.security.Provider;

/**
 *
 * @author karl
 */
public class SimplePBEConfig implements PBEConfig, PBECleanablePasswordConfig {

    private String algorithm = null;
    private char[] password = null;
    private Integer keyObtentionIterations = null;
    private SaltGenerator saltGenerator = null;
    private String providerName = null;
    private Provider provider = null;
    
    private boolean passwordCleaned = false;

    public SimplePBEConfig() {
        super();
    }
    public void setAlgorithm(final String algorithm) {
        this.algorithm = algorithm;
    }
    public void setPassword(final String password) {
        if(this.password != null) {
            cleanPassword();
        }
        if(password == null)
            this.password = null;
        else
            this.password = password.toCharArray();
    }
    public void setPasswordCharArray(final char[] password) {
        if(this.password != null)
            cleanPassword();
        if(password == null) 
            this.password = null;
        else {
            this.password = new char[password.length];
            System.arraycopy(password, 0, this.password, 0, password.length);
        }
    }
    public void setKeyObtentionIterations(final Integer keyObtentionIterations) {
        this.keyObtentionIterations = keyObtentionIterations;
    }
    public void setKeyObtentionIterations(final String keyObtentionIterations) {
        if (keyObtentionIterations != null) {
            try {
                this.keyObtentionIterations = new Integer(keyObtentionIterations);
            } catch (NumberFormatException e) {
                throw new EncryptionInitializationException(e);
            }
        } else {
            this.keyObtentionIterations = null;
        }
    }
public void setSaltGenerator(final SaltGenerator saltGenerator) {
        this.saltGenerator = saltGenerator;
    }
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
    public void setProviderName(final String providerName) {
        this.providerName = providerName;
    }
    public void setProvider(final Provider provider) {
        this.provider = provider;
    }
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
    
    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public String getPassword() {
        if(this.passwordCleaned) 
            throw new PasswordAlreadyCleanedException();
        return new String(this.password);
    }
    @Override
    public char[] getPasswordCharArray() {
        if(this.passwordCleaned)
            throw new PasswordAlreadyCleanedException();
        final char[] result = new char[this.password.length];
        System.arraycopy(this.password, 0, result, 0, this.password.length);
        return result;
    }
    @Override
    public Integer getKeyObtentionIterations() {
        return this.keyObtentionIterations;
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
    public void cleanPassword() {
        if(this.password != null) {
            final int pwdLength = this.password.length;
            for(int i=0;i<pwdLength;i++) {
                this.password[i] = (char)0;
            }
            this.password = new char[0];
        }
        this.passwordCleaned = true;
    }

}
