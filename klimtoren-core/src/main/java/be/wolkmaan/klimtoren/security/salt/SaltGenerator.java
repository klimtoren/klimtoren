/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.salt;

/**
 *
 * @author karl
 */
public interface SaltGenerator {
    public byte[] generateSalt(int lengthBytes);
    public boolean includePlainSaltInEncryptionResults();
}
