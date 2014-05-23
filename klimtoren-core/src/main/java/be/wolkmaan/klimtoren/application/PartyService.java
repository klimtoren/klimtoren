/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.exceptions.UserDoesNotExistException;
import be.wolkmaan.klimtoren.exceptions.UserLockedException;
import be.wolkmaan.klimtoren.exceptions.UserNotAllowedException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.Location;
import be.wolkmaan.klimtoren.location.Mailbox;
import be.wolkmaan.klimtoren.location.PartyLocation;
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
     * @param gender
     * @return
     */
    public Person registerNewPerson(String givenName, String surName, String middleName, Gender gender);

    /**
     * Adds a new person and registers an initial password.
     *
     * @param givenName
     * @param surName
     * @param middleName
     * @param gender
     * @param userName
     * @param password
     * @return
     * @throws be.wolkmaan.klimtoren.application.UserAlreadyExistsException
     */
    public Person registerNewUser(String givenName, String surName,
            String middleName, Gender gender, String userName, String password) throws UserAlreadyExistsException;

    /**
     * Adds a new person and autocreates an initial password and an username.
     * The username is based on the givenname, surname and the domainname of an
     * organization that has the PartyAttribute "domainName".
     *
     * @param organiation
     * @param givenName
     * @param surName
     * @param middleName
     * @param gender
     * @param details
     * @return
     * @throws NoDomainNameFoundException
     */
    public Person registerNewUser(Organization organiation, String givenName, String surName,
            String middleName, Gender gender, PartyAttribute[] details)
            throws NoDomainNameFoundException;

    /**
     * Set the attributes for a person.
     *
     * @param party
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
     * Adds a new mailbox for a party.
     *
     * @param party
     * @param newAddress
     * @param kind
     * @return
     */
    public Party registerNewAddress(Party party, Mailbox newAddress, Kind kind);

    /**
     * Creates a relation between party and a location
     *
     * @param location
     * @param party
     * @param kind
     * @param isDefault
     * @param isContactPoint
     * @return
     */
    public PartyLocation createPartyLocation(Location location, Party party, Kind kind, boolean isDefault, boolean isContactPoint);

    /**
     * Creates and stores a relation between party and a location.
     *
     * @param location
     * @param party
     * @param kind
     * @param isDefault
     * @param isContactPoint
     * @return
     */
    public PartyLocation addPartyLocation(Location location, Party party, Kind kind, boolean isDefault, boolean isContactPoint);

    /**
     * Creates and stores all locations for a party.
     *
     * @param locations
     * @param party
     * @param kind
     * @param isDefault
     * @param isContactPoint
     * @return
     */
    public Party addPartyLocations(List<Location> locations, Party party, Kind kind, boolean isDefault, boolean isContactPoint);

    /**
     * Adds a new mailbox for a party. This methods ends all the previous live
     * mailboxes.
     *
     * @param party
     * @param newAddress
     * @param kind
     * @param stopOldMailboxes
     * @return
     */
    public Party registerNewAddress(Party party, Mailbox newAddress, Kind kind, boolean stopOldMailboxes);

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
