/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author karl
 */
@Table(name = "fullnames")
@Entity
public class FullName extends EntitySupport<FullName, Long> {

    @NaturalId
    @Getter
    @Setter
    private Date startDate;

    @Getter
    @Setter
    private Date endDate;

    @NaturalId
    @ManyToOne
    @Getter
    @Setter
    private Party forParty;

    @NaturalId
    @Getter
    @Setter
    private String givenName;

    @NaturalId
    @Getter
    @Setter
    private String middleName;

    @NaturalId
    @Getter
    @Setter
    private String surName;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String nickName;

    @Getter
    @Setter
    private String pronunciation;

    @Getter
    @Setter
    private Kind kind;

    /*------------------------------
     | Contructors
     -----------------------------*/
    public FullName() {

    }

    public FullName(String givenName, String surName, Date startDate, Party forParty) {
        Validate.notNull(surName, "Surname is required.");
        Validate.notNull(startDate, "Start date is required.");
        Validate.notNull(forParty, "For Party is required.");
        this.givenName = givenName;
        this.surName = surName;
        this.startDate = startDate;
        this.forParty = forParty;
    }
}
