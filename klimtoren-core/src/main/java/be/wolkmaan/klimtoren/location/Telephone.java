/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.location;

import be.wolkmaan.klimtoren.shared.EntitySupport;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Table(name="telephones")
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper=true)
public class Telephone extends EntitySupport<Telephone, Long> {
    public enum Type {
        MOBILE,
        WORK,
        PRIVATE,
        GENERAL,
        FAX_WORK,
        FAX_PRIVATE,
        CUSTOM
    }
    private String number;
    
    private Type type;
    private String customType;
}
