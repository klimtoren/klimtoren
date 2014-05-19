/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Authentication;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.shared.CommonUtils;
import com.google.common.collect.Lists;
import java.text.Normalizer;
import java.util.Date;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author karl
 */
@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyRepository partyRepository;

    @Override
    public Person registerNewStudent(String givenName, String surName, String middleName,
            Organization school, PartyAttribute... details) throws NoDomainNameFoundException {
        String domainName = school.getAttribute("domainName").getValue();
        if (domainName == null) {
            throw new NoDomainNameFoundException();
        }
        String autoPassword = generatePassword(givenName, surName);
        Person student = partyService.registerNewPerson(givenName, surName, middleName);
        student = partyService.addPersonDetails(student, Lists.newArrayList(details));

        //AUTO-GENERATE: password how to return to provide this information
        //TRANSIENT FIELD IN PERSON OR IN AUTHENTICATION?
        return null;

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
