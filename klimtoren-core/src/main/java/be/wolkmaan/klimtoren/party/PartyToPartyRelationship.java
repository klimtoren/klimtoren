/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "p2p")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PartyToPartyRelationship extends EntitySupport<PartyToPartyRelationship, Long> {

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date end;

    @ManyToOne(optional = false)
    @JoinColumn(name = "context")
    private Party context;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reference")
    private Party reference;

    @Enumerated(EnumType.STRING)
    private Kind kind;
}
