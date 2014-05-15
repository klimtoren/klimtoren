/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
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
     * @param givenName
     * @param surName
     * @param middleName
     * @param userName
     * @param password
     * @return 
     * @throws be.wolkmaan.klimtoren.application.UserAlreadyExistsException 
     */
    public Person registerNewUser(String givenName, String surName, 
            String middleName, String userName, String password) throws UserAlreadyExistsException;
    
    
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
    public Organization registerNewOrganization(String name, Party parent, Kind kindOfParent);
    
    /**
     * Adds new relation between two parties
     * @param context
     * @param reference
     * @param kind
     * @return 
     */
    public PartyToPartyRelationship registerRelation(Party context, Party reference, Kind kind);
    
    /**
     * Stops a relation between two parties.
     * @param p2p
     * @return 
     */
    public PartyToPartyRelationship stopRelation(PartyToPartyRelationship p2p);
    /**
     * Stops a relation between two parties. The relation is found by the identifier.
     * @param id
     * @return 
     */
    public PartyToPartyRelationship stopRelation(Long id);
}
