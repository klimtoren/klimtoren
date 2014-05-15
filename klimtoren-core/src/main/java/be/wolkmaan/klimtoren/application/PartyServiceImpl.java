/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.kind.KindRepository;
import be.wolkmaan.klimtoren.party.Authentication;
import be.wolkmaan.klimtoren.party.FullName;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import com.google.common.base.Strings;
import java.util.Date;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author karl
 */
@Service("partyService")
@Slf4j
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private KindRepository kindRepository;

    /* -----------------------------------------
    |   Transactions
    ----------------------------------------- */
    @Override
    @Transactional
    public Person registerNewPerson(String givenName, String surName, String middleName) {

        Person person = createPerson(givenName, surName, middleName);
        partyRepository.store(person);
        //TODO: p.setKind(Kinds.PERSON);
        return person;
    }

    @Transactional
    @Override
    public Person registerNewUser(String givenName, String surName, String middleName,
            String userName, String password) throws UserAlreadyExistsException {
        Person person = createPerson(givenName, surName, middleName);

        Person p = partyRepository.findByUsername(userName);
        if (p != null) {
            throw new UserAlreadyExistsException();
        }

        Authentication auth = new Authentication();
        auth.setForPerson(person);
        auth.setPassword(encryptPassword(password));
        auth.setUsername(userName);
        auth.setGranted(true);
        auth.setLastAttemptFailureTime(new Date());
        auth.setLastLogin(new Date());
        auth.setEnableAutoUnlock(true);
        auth.setLocked(false);
        person.setAuthentication(auth);

        partyRepository.store(person);
        //TODO: p.setKind(Kinds.PERSON);
        log.debug(person.getAuthentication().toString());
        return person;
    }

    @Transactional
    @Override
    public Organization registerNewOrganization(String name) {
        Organization org = createOrganization(name);
        partyRepository.store(org);
        return org;
    }

    @Transactional
    @Override
    public Organization registerNewOrganization(String name, Party parent, Kind kindOfParent) {
        Organization org = createOrganization(name);
        partyRepository.store(org);
        registerRelation(org, parent, kindOfParent);
        return org;
    }
    
    @Transactional
    @Override
    public PartyToPartyRelationship registerRelation(Party context, Party reference, Kind kind) {
        PartyToPartyRelationship p2p = new PartyToPartyRelationship();
        p2p.setStart(new Date());
        p2p.setKind(kind);
        p2p.setReferencedParty(reference);
        p2p.setContextParty(context);
        
        partyRepository.store(p2p);
        return p2p;
    }
    
    @Transactional
    @Override
    public PartyToPartyRelationship stopRelation(PartyToPartyRelationship p2p) {
        p2p.setEnd(new Date());
        partyRepository.store(p2p);
        return p2p;
    }
    
    @Transactional
    @Override
    public PartyToPartyRelationship stopRelation(Long id) {
        PartyToPartyRelationship p2p = partyRepository.get(id);
        if(p2p!=null) 
            return this.stopRelation(p2p);
        else
            return null;
    }

    
    /* -----------------------------------------
    |   Private methods
    ----------------------------------------- */
    private Person createPerson(String givenName, String surName, String middleName) {
        Date registerDate = new Date();
        String displayName = givenName
                + (Strings.isNullOrEmpty(middleName) ? " " : " " + middleName + " ")
                + surName;
        Person p = new Person();
        p.setDisplayName(displayName);
        p.setPrimaryKind(Kind.PERSON);

        FullName fn = new FullName();
        fn.setGivenName(givenName);
        fn.setSurName(surName);
        fn.setMiddleName(middleName);
        fn.setStartDate(registerDate);

        p.addFullName(fn);
        return p;
    }

    private Organization createOrganization(String name) {
        Organization org = new Organization();
        org.setDisplayName(name);
        org.setPrimaryKind(Kind.ORGANIZATION);

        return org;
    }

    private String encryptPassword(String password) {
        return encryptPassword(password, null);
    }

    private String encryptPassword(String password, String salt) {
        return password + "-" + salt; //TODO create algorithm
    }
}
