/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.digest;

/**
 *
 * @author karl
 */
public interface StringDigester {
    public String digest(String message);
    public boolean matches(String message, String digest);
}

