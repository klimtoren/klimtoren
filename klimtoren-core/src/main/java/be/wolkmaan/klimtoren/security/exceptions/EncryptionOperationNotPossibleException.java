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
public final class EncryptionOperationNotPossibleException extends RuntimeException {

    private static final long serialVersionUID = 6304674109588715145L;

    public EncryptionOperationNotPossibleException() {
        super();
    }

    public EncryptionOperationNotPossibleException(final Throwable t) {
        super(t);
    }
    
    public EncryptionOperationNotPossibleException(final String message) {
        super(message);
    }
    
}