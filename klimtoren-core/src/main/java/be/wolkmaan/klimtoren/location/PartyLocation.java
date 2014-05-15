/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.location;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.role.Role;
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
 *
 * @author karl
 */
@Table(name="partylocations")
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper=true)
public class PartyLocation extends EntitySupport<PartyLocation, Long> {
    private Date start;
    private Date end;
    private boolean isDefault;
    private boolean isContactPoint;
    
    @ManyToOne
    @JoinColumn(name="atLocation")
    private Location atLocation;
    
    @ManyToOne
    @JoinColumn(name="party")
    private Party party;
    
    private Kind kind;
    
    @ManyToOne
    @JoinColumn(name="role")
    private Role role;
    
    private Kind partyRoleKind;
}
