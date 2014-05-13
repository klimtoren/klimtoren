/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.resource;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Table(name="resources")
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper=true)
public class Resource extends EntitySupport<Resource, Long> {
    private Date start;
    private Date end;
    private String displayName;
    private String descriptiveInformation;
    
    @ManyToOne
    @JoinColumn(name="forParty")
    private Party forParty;
    
    @ManyToOne
    @JoinColumn(name="kind")
    private Kind kind;
    
    @ManyToOne
    @JoinColumn(name="parent")
    private Resource parent;
}
