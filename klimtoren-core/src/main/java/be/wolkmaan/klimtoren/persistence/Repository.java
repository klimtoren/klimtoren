/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.persistence;

/**
 *
 * @author karl
 */
public interface Repository<T> {
    public void store(T entity);
}
