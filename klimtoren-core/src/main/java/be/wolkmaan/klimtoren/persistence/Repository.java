/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.persistence;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author karl
 */
public interface Repository<T> {
    public void store(Object entity);
    public T get(Serializable id);
    public List<T> list();
    public T remove(Serializable id);
}
