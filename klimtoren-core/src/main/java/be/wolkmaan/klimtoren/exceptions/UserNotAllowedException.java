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
public class UserNotAllowedException extends Exception {

    public UserNotAllowedException() {
        super("User is not allowed.");
    }
    
}