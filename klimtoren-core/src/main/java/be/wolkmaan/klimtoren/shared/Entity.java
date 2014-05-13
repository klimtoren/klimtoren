/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.shared;

import java.io.Serializable;

/**
 *
 * @author karl
 * @param <T> Entity type
 * @param <ID> Entity ID type (extending Serializable)
 */

public interface Entity<T, ID extends Serializable> extends Serializable {
	boolean sameIdentityAs(T other);
	
	ID getId();
	T clone();
}
