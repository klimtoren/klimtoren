/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.exceptions;

/**
 *
 * @author karl
 */
public class UserLockedException extends Exception {

    public UserLockedException() {
        super("User is locked.");
    }
    
}
