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
import be.wolkmaan.klimtoren.kind.KindRepository;
import be.wolkmaan.klimtoren.location.Location;
import be.wolkmaan.klimtoren.location.LocationRepository;
import be.wolkmaan.klimtoren.location.Mailbox;
import be.wolkmaan.klimtoren.location.PartyLocation;
import be.wolkmaan.klimtoren.party.Authentication;
import be.wolkmaan.klimtoren.party.FullName;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.party.Person.Gender;
import be.wolkmaan.klimtoren.security.encryption.pbe.StandardPBEStringEncryptor;
import be.wolkmaan.klimtoren.security.util.StrongPasswordEncryptor;
import be.wolkmaan.klimtoren.shared.CommonDateUtils;
import be.wolkmaan.klimtoren.shared.CommonUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    @Autowired
    private LocationRepository locationRepository;

    /* -----------------------------------------
     |   Transactions
     ----------------------------------------- */
    @Override
    @Transactional
    public Person registerNewPerson(String givenName, String surName, String middleName, Gender gender) {

        Person person = createPerson(givenName, surName, middleName, gender);
        partyRepository.store(person);
        //TODO: p.setKind(Kinds.PERSON);
        return person;
    }

    @Transactional
    @Override
    public Person registerNewUser(String givenName, String surName, String middleName, Gender gender,
            String userName, String password) throws UserAlreadyExistsException {
        Person person = createPerson(givenName, surName, middleName, gender);

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
    public Person registerNewUser(Organization organization, String givenName, String surName, 
            String middleName, Gender gender, PartyAttribute[] details) 
                    throws NoDomainNameFoundException {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("wolkmaan");

        PartyAttribute domainName = organization.getAttribute("domainName");
        if (domainName == null) {
            throw new NoDomainNameFoundException();
        }
        String autoPassword = generatePassword(givenName, surName);
        String username = generateUsername(givenName, surName, domainName.getValue());
        Person user = null;
        try {
            user = registerNewUser(givenName, surName, middleName, gender, username, autoPassword);

            
            List<PartyAttribute> attributes = Lists.newArrayList();
            if(details != null) attributes = Lists.newArrayList(details);

            PartyAttribute initialPwd = new PartyAttribute("initialPwd", encryptor.encrypt(autoPassword), Kind.ENCRYPTED_TEXT);
            attributes.add(initialPwd);

            addPartyDetails(user, attributes);

        } catch (UserAlreadyExistsException ex) {
            //this error can't be called, 
            //the function generateUsername auto-generates an unique username.
            //therefor this dump to log (you never know)
            log.error(ex.getMessage());
        }
        return user;
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
        return registerRelation(context, reference, kind, new Date());
    }

    @Transactional
    @Override
    public PartyToPartyRelationship registerRelation(Party context, Party reference, Kind kind, Date start) {
        //FIRST CHECK IF RELATION EXISTS
        PartyToPartyRelationship p2p = partyRepository.findRelation(context, reference, kind);

        if (p2p == null) {
            p2p = new PartyToPartyRelationship();
            p2p.setStart(start);
            p2p.setKind(kind);
            p2p.setReference(reference);
            p2p.setContext(context);

            partyRepository.store(p2p);
        }
        return p2p;
    }

    @Transactional
    @Override
    public void registerRelation(Party context, Party reference, Kind kind, Kind reverseKind) {
        registerRelation(context, reference, kind, reverseKind, new Date());
    }

    @Transactional
    @Override
    public void registerRelation(Party context, Party reference, Kind kind, Kind reverseKind, Date start) {
        registerRelation(context, reference, kind, start);
        registerRelation(reference, context, reverseKind, start);
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
    public Party registerNewAddress(Party party, Mailbox newAddress, Kind kind) {
        return registerNewAddress(party, newAddress, kind, false);
    }

    @Override
    public Party registerNewAddress(Party party, Mailbox newAddress, Kind kind, boolean stopOldMailboxes) {
        newAddress = locationRepository.saveOrGetMailbox(newAddress);
        if (stopOldMailboxes) {
            List<PartyLocation> locations = locationRepository.findPartyLocations(party, kind);
            locations.stream().map((partyLocation) -> {
                partyLocation.setEnd(new Date());
                partyLocation.getForParty().getLocations().remove(partyLocation);
                return partyLocation;
            })
            .forEach((partyLocation) -> {
                partyRepository.store(partyLocation); 
                //locationRepository.store(partyLocation);
            });
        }

        createPartyLocation(newAddress, party, kind,
                true, true);

        partyRepository.store(party);
        return party;
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
    private Person createPerson(String givenName, String surName, String middleName, Gender gender) {
        Date registerDate = new Date();
        String displayName = givenName
                + (Strings.isNullOrEmpty(middleName) ? " " : " " + middleName + " ")
                + surName;
        Person p = new Person();
        p.setDisplayName(displayName);
        p.setPrimaryKind(Kind.PERSON);
        p.setGender(gender);

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

    @Override
    public PartyLocation createPartyLocation(Location location, Party party, Kind kind, boolean isDefault, boolean isContactPoint) {
        PartyLocation partyLocation = new PartyLocation();
        partyLocation.setAtLocation(location);
        partyLocation.setForParty(party);
        partyLocation.setStart(new Date());
        partyLocation.setContactPoint(isContactPoint);
        partyLocation.setDefault(isDefault);
        partyLocation.setKind(kind);
        party.addLocation(partyLocation);
        return partyLocation;
    }

    
    
     /* ----------------------------------------
     |  PRIVATE METHODS 
     ---------------------------------------- */
     /**
     * Generates a random password, based on the name.
     *
     * @param givenName
     * @param surName
     * @return
     */
    private String generatePassword(String givenName, String surName) {
        final String AB = "0123456789";
        final String special = "*%?&!@#";
        Random rnd = new Random();
        int len = 8;
        StringBuilder sb = new StringBuilder(len);
        
        String firstChar = givenName.substring(0, 1).toLowerCase();
        firstChar = CommonUtils.normalizeAndTrim(firstChar);
        String secondChar = surName.substring(0, 1).toLowerCase();
        secondChar = CommonUtils.normalizeAndTrim(secondChar);
        sb.append(firstChar);
        sb.append(secondChar);
        
        
        for (int i = 2; i < len - 1; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        
        sb.append(special.charAt(rnd.nextInt(special.length())));
        return sb.toString();
    }

    /**
     * Generates a standard username, based on given, surname and the domainname
     * of the school.
     *
     * @param givenName
     * @param surName
     * @param domainName
     * @return
     */
    @Transactional
    private String generateUsername(String givenName, String surName, String domainName) {
        
        String gn = CommonUtils.normalizeAndTrim(givenName);
        String sn = CommonUtils.normalizeAndTrim(surName);
        
        String username = (gn + "." + sn + "@" + domainName).toLowerCase();
        
        boolean userNameFound = false;
        Person p;
        int i = 1;
        while (!userNameFound) {
            p = partyRepository.findByUsername(username);
            if (p != null) {
                //username already exists
                username = (gn + "." + sn + (++i) + "@" + domainName).toLowerCase();
            } else {
                userNameFound = true;
            }
        }
        return username;
    }

}
