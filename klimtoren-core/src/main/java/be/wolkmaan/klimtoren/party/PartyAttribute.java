/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Attributes for a party. Designed from an EAV-databasemodel
 * @author karl
 */
@Table(name="party_attributes")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PartyAttribute extends EntitySupport<PartyAttribute, Long> {
    private Date start;
    private Date end;
    private String name;
    private String value;
    
    private Kind kind;
    @ManyToOne
    @JoinColumn(name="forParty")
    private Party forParty;
}
