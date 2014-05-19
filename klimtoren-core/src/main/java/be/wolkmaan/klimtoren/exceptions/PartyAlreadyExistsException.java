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
public class PartyAlreadyExistsException extends Exception {

    public PartyAlreadyExistsException() {
    }
    public PartyAlreadyExistsException(final Throwable t) {
        super(t);
    }
    public PartyAlreadyExistsException(final String message) {
        super(message);
    }
}
