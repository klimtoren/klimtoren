/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Table(name = "persons")
@Entity
@PrimaryKeyJoinColumn(name = "party_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends Party {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "forPerson", cascade = CascadeType.ALL)
    public Authentication authentication;
}
