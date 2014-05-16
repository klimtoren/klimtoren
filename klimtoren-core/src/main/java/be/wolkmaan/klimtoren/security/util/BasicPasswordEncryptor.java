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
public class BasicPasswordEncryptor implements PasswordEncryptor {
    private final StandardStringDigester digester;
    
    public BasicPasswordEncryptor() {
        this.digester = new StandardStringDigester();
        this.digester.initialize();
    }
    @Override
    public String encryptPassword(String password) {
        return this.digester.digest(password);
    }

    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return this.digester.matches(plainPassword, encryptedPassword);
    }
    
}
