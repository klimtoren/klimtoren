/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.shared;

import be.wolkmaan.klimtoren.shared.view.BaseView;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonView;

/**
 *
 * @author karl
 * @param <T>
 * @param <ID>
 */
@MappedSuperclass
public class EntitySupport<T extends Entity, ID extends Serializable> implements Entity<T, ID> {
    private static final Logger LOG = Logger.getLogger(EntitySupport.class.getName());

    @Id
    @Column(name="id", unique=true, nullable=false)
    @GeneratedValue
    @Getter @Setter
    @JsonView(BaseView.class)
    protected ID id;
    
    @Override
    public boolean sameIdentityAs(T other) {
        return other != null && this.getId().equals(other.getId());
    }
    
    @Override
    public T clone() {
        T clone = null;
        
        try {
            clone = (T) this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        //Walk up the superclass hierarchy
        for(Class<?> obj = this.getClass(); 
                !obj.equals(Object.class);
                obj = obj.getSuperclass()) {
            Field[] fields = obj.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    field.set(clone, field.get(this));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    LOG.log(Level.WARNING, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        
        return this.sameIdentityAs((T) obj);
    }
}
