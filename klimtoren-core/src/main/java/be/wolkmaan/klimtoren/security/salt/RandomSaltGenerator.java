/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security.salt;

import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author karl
 */
public class RandomSaltGenerator implements SaltGenerator {

    public static final String DEFAULT_SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private final SecureRandom random;

    public RandomSaltGenerator() {
        this(DEFAULT_SECURE_RANDOM_ALGORITHM);
    }

    public RandomSaltGenerator(final String secureRandomAlgorithm) {
        super();
        try {
            this.random = SecureRandom.getInstance(secureRandomAlgorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new EncryptionInitializationException(ex);
        }
    }

    @Override
    public byte[] generateSalt(int lengthBytes) {
        final byte[] salt = new byte[lengthBytes];
        synchronized(this.random) {
            this.random.nextBytes(salt);
        }
        return salt;
    }

    @Override
    public boolean includePlainSaltInEncryptionResults() {
       return true;
    }

}
