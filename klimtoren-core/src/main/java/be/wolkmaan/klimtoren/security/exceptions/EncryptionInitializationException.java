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
public class EncryptionInitializationException extends RuntimeException {

    private static final long serialVersionUID = 8929638240023639778L;

    public EncryptionInitializationException() {
        super();
    }

    public EncryptionInitializationException(final Throwable t) {
        super(t);
    }
    
    public EncryptionInitializationException(final String msg, final Throwable t) {
        super(msg, t);
    }
    
    public EncryptionInitializationException(final String msg) {
        super(msg);
    }
}
