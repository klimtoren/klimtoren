/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.UserDoesNotExistException;
import be.wolkmaan.klimtoren.exceptions.UserLockedException;
import be.wolkmaan.klimtoren.exceptions.UserNotAllowedException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.party.Person.Gender;
import java.util.Date;
import java.util.List;

/**
 *
 * @author karl
 */
public interface PartyService {

    /**
     * Adds a new person
     *
     * @param givenName
     * @param surName
     * @param middleName
     * @return
     */
    public Person registerNewPerson(String givenName, String surName, String middleName, Gender gender);

    /**
     * Adds a new person and registers an initial password.
     *
     * @param givenName
     * @param surName
     * @param middleName
     * @param userName
     * @param password
     * @return
     * @throws be.wolkmaan.klimtoren.application.UserAlreadyExistsException
     */
    public Person registerNewUser(String givenName, String surName,
            String middleName, Gender gender, String userName, String password) throws UserAlreadyExistsException;

    /**
     * Set the attributes for a person.
     *
     * @param person
     * @param details
     * @return
     */
    public Party addPartyDetails(Party party, List<PartyAttribute> details);

    /**
     * Adds a new organization.
     *
     * @param name
     * @return
     */
    public Organization registerNewOrganization(String name);

    /**
     * Adds a new organization.
     *
     * @param name
     * @param parent
     * @param kindOfParent
     * @return
     */
    public Organization registerNewOrganization(String name, Party parent, Kind kindOfParent);

    /**
     * Adds new relation between two parties
     *
     * @param context
     * @param reference
     * @param kind
     * @return
     */
    public PartyToPartyRelationship registerRelation(Party context, Party reference, Kind kind);

    /**
     * Adds a new relation between two parties. The reverse relation will be
     * added too.
     *
     * @param context
     * @param reference
     * @param kind
     * @param reverseKind
     */
    public void registerRelation(Party context, Party reference, Kind kind, Kind reverseKind);

    /**
     * Adds a new relation between two parties starting from the given date.
     *
     * @param context
     * @param reference
     * @param kind
     * @param start
     * @return 
     */
    public PartyToPartyRelationship registerRelation(Party context, Party reference, Kind kind, Date start);

    /**
     * Adds a new relation between two parties starting from the given date. The
     * reverse relation will added too.
     *
     * @param context
     * @param reference
     * @param kind
     * @param reverseKind
     * @param start
     */
    public void registerRelation(Party context, Party reference, Kind kind, Kind reverseKind, Date start);

    /**
     * Stops a relation between two parties.
     *
     * @param p2p
     * @return
     */
    public PartyToPartyRelationship stopRelation(PartyToPartyRelationship p2p);

    /**
     * Stops a relation between two parties. The relation is found by the
     * identifier.
     *
     * @param id
     * @return
     */
    public PartyToPartyRelationship stopRelation(Long id);

    /**
     * Checks is a person entered the correct password. This method verifies if
     * the user exists, if the user is granted and if the user isn't locked.
     *
     * @param username
     * @param password
     * @return
     * @throws UserDoesNotExistException
     * @throws UserNotAllowedException
     * @throws UserLockedException
     */
    public boolean login(String username, String password)
            throws UserDoesNotExistException, UserNotAllowedException, UserLockedException;

}
