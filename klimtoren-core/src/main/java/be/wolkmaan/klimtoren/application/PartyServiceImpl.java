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
import be.wolkmaan.klimtoren.kind.KindRepository;
import be.wolkmaan.klimtoren.party.Authentication;
import be.wolkmaan.klimtoren.party.FullName;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.security.util.StrongPasswordEncryptor;
import be.wolkmaan.klimtoren.shared.CommonDateUtils;
import com.google.common.base.Strings;
import java.util.Date;
import java.util.List;
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
        if (p2p != null) {
            return this.stopRelation(p2p);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean login(String username, String password)
            throws UserDoesNotExistException, UserNotAllowedException, UserLockedException {
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        Person person = partyRepository.findByUsername(username);
        if (person == null) {
            throw new UserDoesNotExistException();
        }

        final Authentication auth = person.getAuthentication();
        if (auth == null) {
            throw new UserDoesNotExistException();
        }
        if (!auth.isGranted()) {
            throw new UserNotAllowedException();
        }
        if (auth.isLocked()) {
            throw new UserLockedException();
        }

        String encPassword = auth.getPassword();

        boolean allowed = encryptor.checkPassword(password, encPassword);
        if (allowed) {
            auth.setLastLogin(new Date());
            auth.setLastAttemptFailureTime(CommonDateUtils.getSQLMinimum());
            auth.setLoginAttemptFailureCount(0);
            partyRepository.store(auth);
            return true;
        } else {
            auth.setLastAttemptFailureTime(new Date());
            int failure = auth.getLoginAttemptFailureCount() + 1;
            auth.setLoginAttemptFailureCount(failure);
            if (failure == 3) {//TODO set failureCount in configuration file somewhere
                auth.setLocked(true);
                partyRepository.store(auth);
                throw new UserLockedException();
            }
            partyRepository.store(auth);
            return false;
        }
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
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor.encryptPassword(password);
    }

    @Override
    @Transactional
    public Party addPartyDetails(Party party, List<PartyAttribute> details) {
        details.stream().forEach((attribute) -> {
            party.setAttribute(attribute.getName(), attribute.getValue());
        });
        partyRepository.store(party);
        return party;
    }
}
