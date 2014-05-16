/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.util;

/**
 *
 * @author karl
 */
public interface PasswordEncryptor {
    public String encryptPassword(String password);
    public boolean checkPassword(String plainPassword, String encryptedPassword);
}
