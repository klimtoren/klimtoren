/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.party;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author karl
 */
@Table(name = "organizations")
@Entity
@PrimaryKeyJoinColumn(name = "party_id")
public class Organization extends Party {

}
