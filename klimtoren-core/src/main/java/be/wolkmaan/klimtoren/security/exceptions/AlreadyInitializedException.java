/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.exceptions;

/**
 *
 * @author karl
 */
public class AlreadyInitializedException extends RuntimeException {

    public AlreadyInitializedException() {
        super("Encryption entity already initialized");
    }
    
}
