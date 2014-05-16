/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.util;

import be.wolkmaan.klimtoren.security.digest.StandardStringDigester;

/**
 *
 * @author karl
 */
public class StrongPasswordEncryptor implements PasswordEncryptor {
    private final StandardStringDigester digester;
    
    public StrongPasswordEncryptor() {
        super();
        this.digester = new StandardStringDigester();
        this.digester.setAlgorithm("SHA-256");
        this.digester.setIterations(100000);
        this.digester.setSaltSizeBytes(16);
        this.digester.initialize();
    }

    @Override
    public String encryptPassword(final String password) {
        return this.digester.digest(password);
    }

    @Override
    public boolean checkPassword(final String plainPassword, final String encryptedPassword) {
        return this.digester.matches(plainPassword, encryptedPassword);
    }
    
    
}
