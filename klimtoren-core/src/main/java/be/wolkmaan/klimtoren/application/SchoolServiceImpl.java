/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.exceptions.PartyAlreadyExistsException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.LocationRepository;
import be.wolkmaan.klimtoren.location.Mailbox;
import be.wolkmaan.klimtoren.location.PartyLocation;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.party.Person.Gender;
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
            Organization school, Gender gender, PartyAttribute... details) throws NoDomainNameFoundException {

        Person student = createUser(school, givenName, surName, middleName, gender, details);

        partyService.registerRelation(student, school, Kind.STUDENT, Kind.SCHOOL);
            
        return student;
    }

    @Override
    public Person unSubscribeStudent(Person student, Organization school) {
        return unSubscribeStudent(student, school, new Date());
    }

    @Override
    public Person unSubscribeStudent(Person student, Organization school, Date end) {
        List<PartyToPartyRelationship> relations = partyRepository.findRelation(student, school, true);
        if (relations != null) {
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
        Organization school = createSchool(schoolName, descriptiveInformation);

        address = saveOrGetMailbox(address);
        createPartyLocation(address, school, true, true);
        partyRepository.store(school);
        return school;
    }

    @Override
    public Organization registerNewAddressForSchool(Organization school, Mailbox newAddress) {
        newAddress = saveOrGetMailbox(newAddress);
        createPartyLocation(newAddress, school, true, true);

        partyRepository.store(school);
        return school;
    }

    @Override
    public Organization registerNewDepartment(String departmentName, String descriptiveInformation, Mailbox address, Organization forSchool) {
        Organization department = createSchool(departmentName, descriptiveInformation);

        address = saveOrGetMailbox(address);
        createPartyLocation(address, department, true, false);
        partyRepository.store(department);

        partyService.registerRelation(department, forSchool, Kind.DEPARTMENT);
        partyService.registerRelation(forSchool, department, Kind.PRINCIPAL_SCHOOL);

        return department;
    }

    @Override
    public Organization registerNewGroup(String groupName, String descriptiveInformation, Organization school, PartyAttribute... details)
        throws PartyAlreadyExistsException {
        Organization classgroup = partyRepository.findOrganization(groupName, school, Kind.CLASSGROUP);
        if (classgroup == null) {
            classgroup = new Organization();
            classgroup.setDisplayName(groupName);
            classgroup.setDescriptiveInformation(descriptiveInformation);
            classgroup.setPrimaryKind(Kind.CLASSGROUP);
            classgroup.setAttributes(Lists.newArrayList(details));
            
            partyRepository.store(classgroup);
            
            partyService.registerRelation(classgroup, school, Kind.CLASSGROUP, Kind.SCHOOL);
        } else {
            throw new PartyAlreadyExistsException("The classgroup already exists.");
        }
        return classgroup;
    }

    @Override
    public void addStudentToGroup(Person student, Organization group) {
        partyService.registerRelation(student, group, Kind.STUDENT, Kind.CLASSGROUP);
    }

    @Override
    public void addStudentToGroup(Person student, Organization group, Date start) {
        partyService.registerRelation(student, group, Kind.STUDENT, Kind.CLASSGROUP, start);
    }

    @Override
    public Person registerNewTeacher(String givenName, String surName, String middleName, Organization school, Gender gender, PartyAttribute... details) 
    throws NoDomainNameFoundException {
        Person teacher = createUser(school, givenName, surName, middleName, gender, details);

        partyService.registerRelation(teacher, school, Kind.TEACHER, Kind.SCHOOL);
        return teacher;
    }

    @Override
    public void addTeacherToGroup(Person teacher, Organization group, boolean isTitular) {
        partyService.registerRelation(teacher, group, isTitular ? Kind.TITULAR : Kind.TEACHER, Kind.CLASSGROUP);
    }

    @Override
    public void addTeacherToGroup(Person teacher, Organization group, boolean isTitular, Date start) {
        partyService.registerRelation(teacher, group, isTitular ? Kind.TITULAR : Kind.TEACHER, Kind.CLASSGROUP, start);
    }

    @Override
    public Person connectParentToStudent(Organization school, String givenName, String surName, String middleName, Gender gender, Person student, Kind relation) 
        throws NoDomainNameFoundException {
        Person parent = createUser(school, givenName, surName, middleName, gender);
        partyService.registerRelation(parent, school, Kind.PARENT, Kind.SCHOOL);
        partyService.registerRelation(parent, student, relation, student.getGender().equals(Gender.MALE) ? Kind.SON : Kind.DAUGHTER);
        return parent;
    }

    @Override
    public void connectParentToStudent(Organization school, Person parent, Person student, Kind relation) {
        partyService.registerRelation(student, parent, relation, student.getGender().equals(Gender.MALE) ? Kind.SON : Kind.DAUGHTER);
    }

    @Override
    public void connectStudentToParent(Organization school, Person student, Person parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connectParentsToStudent(Organization school, Person mother, Person father, Person student) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connectParentsToStudent(Organization school, Person mother, Person father, Person student, Kind parentsRelation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParentsRelation(Person mother, Person father, Kind parentsRelation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /* ----------------------------------------
     |  PRIVATE METHODS 
     ---------------------------------------- */
    /**
     * Generates an user with default username and auto-generated password.
     * The user relates to a given school.
     * @param school
     * @param givenName
     * @param surName
     * @param middleName
     * @param details
     * @return
     * @throws NoDomainNameFoundException 
     */
    @Transactional
    private Person createUser(Organization school, String givenName, String surName, String middleName, Gender gender, PartyAttribute... details) throws NoDomainNameFoundException {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("wolkmaan");
       
        PartyAttribute domainName = school.getAttribute("domainName");
        if (domainName == null) {
            throw new NoDomainNameFoundException();
        }
        String autoPassword = generatePassword(givenName, surName);
        String username = generateUsername(givenName, surName, domainName.getValue());
        Person user = null;
        try {
            user = partyService.registerNewUser(givenName, surName, middleName, gender, username, autoPassword);

            List<PartyAttribute> attributes = Lists.newArrayList(details);

            PartyAttribute initialPwd = new PartyAttribute("initialPwd", encryptor.encrypt(autoPassword), Kind.ENCRYPTED_TEXT);
            attributes.add(initialPwd);

            partyService.addPartyDetails(user, attributes);
            

        } catch (UserAlreadyExistsException ex) {
            //this error can't be called, 
            //the function generateUsername auto-generates an unique username.
            //therefor this dump to log (you never know)
            log.error(ex.getMessage());
        }
        return user;
    }
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

    private Organization createSchool(String departmentName, String descriptiveInformation) {
        Organization org = new Organization();
        org.setDisplayName(departmentName);
        org.setDescriptiveInformation(descriptiveInformation);
        org.setPrimaryKind(Kind.SCHOOL);
        return org;
    }

    @Transactional
    private Mailbox saveOrGetMailbox(Mailbox newAddress) {
        //find the new address in database
        //if it already exists, use this one
        //else store a new mailbox
        Mailbox inDB = locationRepository.findByMailbox(newAddress);
        if (inDB != null) {
            return inDB;
        } else {
            //not saved yet
            locationRepository.store(newAddress);
            return newAddress;
        }
    }

    private PartyLocation createPartyLocation(Mailbox address, Organization school, boolean isDefault, boolean isContactPoint) {
        PartyLocation location = new PartyLocation();
        location.setAtLocation(address);
        location.setParty(school);
        location.setStart(new Date());
        location.setContactPoint(isContactPoint);
        location.setDefault(isDefault);
        location.setPartyRoleKind(Kind.WORK);
        return location;
    }
    
    
}
