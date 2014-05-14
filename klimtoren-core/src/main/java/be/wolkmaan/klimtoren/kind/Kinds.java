/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.kind;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author karl
 */
public class Kinds implements Map<Kind, List<Kind>> {

    private Map<Kind, List<Kind>> _map;

    @Override
    public int size() {
        return _map.size();
    }

    @Override
    public boolean isEmpty() {
        return _map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return _map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean containsValueInKey(Kind key, Kind value) {
        List<Kind> list = _map.get(key);
        if (list == null) {
            return false;
        } else {
            return list.contains(value);
        }

    }

    @Override
    public List<Kind> get(Object key) {
        return _map.get(key);
    }

    @Override
    public List<Kind> put(Kind key, List<Kind> value) {
        return _map.put(key, value);
    }

    @Override
    public List<Kind> remove(Object key) {
        return _map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Kind, ? extends List<Kind>> m) {
        _map.putAll(m);
    }

    @Override
    public void clear() {
        _map.clear();
    }

    @Override
    public Set<Kind> keySet() {
        return _map.keySet();
    }

    @Override
    public Collection<List<Kind>> values() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Entry<Kind, List<Kind>>> entrySet() {
        return _map.entrySet();
    }

}
