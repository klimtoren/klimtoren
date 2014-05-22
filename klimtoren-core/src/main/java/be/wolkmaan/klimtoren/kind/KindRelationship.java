/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.kind;

import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="kind_relations")
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper=true)
public class KindRelationship extends EntitySupport<KindRelationship, Long> {
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date end;
    
    private Kind kind;
    private Kind source;
    private Kind target;
    
    @ManyToOne
    @JoinColumn(name="owner")
    private Party owner;
}
