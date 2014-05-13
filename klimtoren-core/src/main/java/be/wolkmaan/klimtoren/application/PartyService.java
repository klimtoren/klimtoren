/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.party.Membership;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.party.Person;

/**
 *
 * @author karl
 */
public interface PartyService {
    /**
     * Adds a new person
     * @param givenName
     * @param surName
     * @param middleName
     * @return 
     */
    public Person registerNewPerson(String givenName, String surName, String middleName);
    
    /**
     * Adds a new person and registers an initial password.
     * A default profile is set.
     * @param givenName
     * @param surName
     * @param middleName
     * @param password
     * @return 
     */
    public Membership registerNewUser(String givenName, String surName, String middleName, String password);
    
    /**
     * Adds a new organization.
     * @param name
     * @return 
     */
    public Organization registerNewOrganization(String name);
    /**
     * Adds a new organization.
     * @param name
     * @param parent
     * @return 
     */
    public Organization registerNewOrganization(String name, Party parent);
}
