/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.LocationRepository;
import be.wolkmaan.klimtoren.location.Mailbox;
import be.wolkmaan.klimtoren.location.PartyLocation;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.security.encryption.pbe.StandardPBEStringEncryptor;
import be.wolkmaan.klimtoren.shared.CommonUtils;
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
@Slf4j
@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Override
    @Transactional
    public Person registerNewStudent(String givenName, String surName, String middleName,
            Organization school, PartyAttribute... details) throws NoDomainNameFoundException {
        
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("wolkmaan");
        
        PartyAttribute domainName = school.getAttribute("domainName");
        if (domainName == null) {
            throw new NoDomainNameFoundException();
        }
        String autoPassword = generatePassword(givenName, surName);
        String username = generateUsername(givenName, surName, domainName.getValue());
        
        Person student = null;
        try {
            student = partyService.registerNewUser(givenName, surName, middleName, username, autoPassword);
            
            List<PartyAttribute> attributes = Lists.newArrayList(details);
            
            PartyAttribute initialPwd = new PartyAttribute("initialPwd", encryptor.encrypt(autoPassword), Kind.ENCRYPTED_TEXT);
            attributes.add(initialPwd);
            
            partyService.addPartyDetails(student, attributes);
            partyService.registerRelation(student, school, Kind.STUDENT);
            partyService.registerRelation(school, student, Kind.SCHOOL);
            
        } catch(UserAlreadyExistsException ex) {
            //this error can't be called, 
            //the function generateUsername auto-generates an unique username.
            //therefor this dump to log (you never know)
            log.error(ex.getMessage());   
        }
        
        //AUTO-GENERATE: password how to return to provide this information
        //TRANSIENT FIELD IN PERSON OR IN AUTHENTICATION?
        return student;
    }
    @Override
    public Person unSubscribeStudent(Person student, Organization school) {
        return unSubscribeStudent(student, school, new Date());
    }
    @Override
    public Person unSubscribeStudent(Person student, Organization school, Date end) {
        List<PartyToPartyRelationship> relations = partyRepository.findRelation(student, school, true);
        if(relations != null) {
            relations.stream().map((relation) -> {
                relation.setEnd(end);
                return relation;
            }).forEach((relation) -> {
                partyRepository.store(relation);
            });
        }
        return student;
    }

    @Override
    public Organization registerNewSchool(String schoolName, String descriptiveInformation, Mailbox address) {
        Organization school = new Organization();
        school.setDisplayName(schoolName);
        school.setPrimaryKind(Kind.SCHOOL);
        school.setDescriptiveInformation(descriptiveInformation);
        
        Mailbox inDB = locationRepository.findByMailbox(address);
        if(inDB != null) {
            address = inDB;
        }
        PartyLocation location = new PartyLocation();
        location.setAtLocation(address);
        location.setParty(school);
        location.setStart(new Date());
        location.setContactPoint(true);
        //TODO: check the other setters role partyrole , ...
        partyRepository.store(school);
        return school;
    }

    @Override
    public Organization registerNewGroup(String groupName, String descriptiveInformation, Organization parent, PartyAttribute... details) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addStudentToGroup(Person student, Organization group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addStudentToGroup(Person student, Organization group, Date start) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person registerNewTeacher(String givenName, String surName, String middleName, Organization school, PartyAttribute... details) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addTeacherToGroup(Person teacher, Organization group, boolean isTitular) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addTeacherToGroup(Person teacher, Organization group, boolean isTitular, Date start) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean connectParentToStudent(String givenName, String surName, String middleName, Person student, Kind relation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean connectParentToStudent(Person parent, Person student, Kind relation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean connectStudentToParent(Person student, Person parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean connectParentsToStudent(Person mother, Person father, Person student) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean connectParentsToStudent(Person mother, Person father, Person student, Kind parentsRelation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setParentsRelation(Person mother, Person father, Kind parentsRelation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        sb.append(givenName.substring(0, 1).toLowerCase());
        sb.append(surName.substring(0, 1).toLowerCase());
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
        int i=1;
        while (!userNameFound) {
            p = partyRepository.findByUsername(username);
            if(p!=null) {
                //username already exists
                username =(gn + "." + sn + (++i) + "@" + domainName).toLowerCase();
            } else {
                userNameFound = true;
            }
        }
        return username;
    }

    
}
