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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author karl
 */
@Table(name = "fullnames")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FullName extends EntitySupport<FullName, Long> {

    @NaturalId
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;

    @NaturalId
    @ManyToOne
    private Party forParty;

    @NaturalId
    private String givenName;

    @NaturalId
    private String middleName;

    @NaturalId
    private String surName;

    private String title;

    private String nickName;

    private String pronunciation;

    @Enumerated(EnumType.STRING)
    private Kind kind;

 
}
