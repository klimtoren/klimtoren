/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.encryption;

/**
 *
 * @author karl
 */
public interface StringEncryptor {
    public String encrypt(String message);
    public String decrypt(String encryptedMessage);
}
