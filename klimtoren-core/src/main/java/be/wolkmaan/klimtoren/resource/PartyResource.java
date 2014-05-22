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
@Table(name="party_resources")
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(callSuper=true)
public class PartyResource extends EntitySupport<PartyResource, Long> {

        @Temporal(javax.persistence.TemporalType.DATE)
	private Date start;
        @Temporal(javax.persistence.TemporalType.DATE)
	private Date end;

	@ManyToOne(optional=false)
	@JoinColumn(name="forParty")
	private Party forParty;

	@ManyToOne(optional=false)
	@JoinColumn(name="useResource")
	private Resource useResource;

	@Enumerated(EnumType.STRING)
	private Kind kind;
}
