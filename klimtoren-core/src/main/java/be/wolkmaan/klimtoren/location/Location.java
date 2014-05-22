/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.location;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Table(name="locations")
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper=true)
public class Location extends EntitySupport<Location, Long> {
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updated;
    
    @Enumerated(EnumType.STRING)
    private Kind kind;
}
