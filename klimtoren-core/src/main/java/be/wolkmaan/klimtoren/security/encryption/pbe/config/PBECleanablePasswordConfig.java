/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.encryption.pbe.config;

/**
 *
 * @author karl
 */
public interface PBECleanablePasswordConfig {
    public char[] getPasswordCharArray();
    public void cleanPassword();
}
