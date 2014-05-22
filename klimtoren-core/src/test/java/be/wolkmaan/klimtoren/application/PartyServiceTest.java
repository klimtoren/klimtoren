/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.party.Person.Gender;
import be.wolkmaan.klimtoren.security.encryption.pbe.StandardPBEStringEncryptor;
import be.wolkmaan.klimtoren.web.config.PersistenceConfig;
import be.wolkmaan.klimtoren.web.config.RootConfig;
import be.wolkmaan.klimtoren.web.config.WebMvcConfig;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author karl
 */
@Slf4j
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, PersistenceConfig.class, WebMvcConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PartyServiceTest {

    @Autowired
    private PartyService partyService;
    @Autowired
    private SchoolService schoolService;

    public Person testRegisterPerson() {
        Person p = partyService.registerNewPerson("Ulrike", "Drieskens", "", Gender.FEMALE);
        assertNotNull(p.getFullName());
        assertTrue(p.getId() != null && p.getId() > 0);
        assertTrue(p.getFullName().getStartDate().compareTo(new Date()) == -1);
        return p;
    }

    public Person testRegisterUser() {
        Person p = new Person();
        try {
            p = partyService.registerNewUser("Karl", "Van Iseghem", "Flor", Gender.MALE, "karl.vaniseghem@klimtoren.be", "ofosok");
        } catch (UserAlreadyExistsException ex) {
            Logger.getLogger(PartyServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public Organization testRegisterOrganization() {
        Organization org = partyService.registerNewOrganization("VBS De Klimtoren");
        assertNotNull(org.getId());
        assertTrue(org.getId() != null && org.getId() > 0);
        return org;
    }

    public void testRelations() {
        Person ulrike = testRegisterPerson();
        Person karl = testRegisterUser();
        Organization org = testRegisterOrganization();

        PartyToPartyRelationship husband = partyService.registerRelation(karl, ulrike, Kind.HUSBAND);
        PartyToPartyRelationship wife = partyService.registerRelation(ulrike, karl, Kind.WIFE);
        PartyToPartyRelationship employee = partyService.registerRelation(karl, org, Kind.EMPLOYEE);
        PartyToPartyRelationship interim = partyService.registerRelation(karl, org, Kind.INTERIM);

        partyService.stopRelation(interim);

        assertNull(husband.getEnd());
        assertNotNull(husband.getStart());
        assertNotNull(interim.getEnd());
    }

    @Test
    public void testNewStudent() {
        Organization org = partyService.registerNewOrganization("VBS De Klimtoren");
        partyService.addPartyDetails(org, Lists.newArrayList(new PartyAttribute("domainName", "klimtoren.be", Kind.STRING)));
        try {
            Person stud = schoolService.registerNewStudent("Karl", "Van Iseghem", "Flor", org, Gender.MALE);
            log.debug(stud.getAttribute("initialPwd").getValue());

            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword("wolkmaan");
            
            log.debug(encryptor.decrypt(stud.getAttribute("initialPwd").getValue()));
            
            
        } catch (NoDomainNameFoundException ex) {
            log.error(ex.getMessage());
        }

    }
}
