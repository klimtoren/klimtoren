/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.web.config.PersistenceConfig;
import be.wolkmaan.klimtoren.web.config.RootConfig;
import be.wolkmaan.klimtoren.web.config.WebMvcConfig;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
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
@WebAppConfiguration
@ContextConfiguration(classes={RootConfig.class, PersistenceConfig.class, WebMvcConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PartyServiceTest {
    
    @Autowired
    private PartyService partyService;
    
   
    public Person testRegisterPerson() {
        Person p = partyService.registerNewPerson("Ulrike", "Drieskens", "");
        assertNotNull(p.getFullName());
        assertTrue(p.getId() != null && p.getId() > 0);
        assertTrue(p.getFullName().getStartDate().compareTo(new Date()) == -1);
        return p;
    }
    public Person testRegisterUser() {
        Person p = new Person();
        try {
            p = partyService.registerNewUser("Karl", "Van Iseghem", "Flor", "karl.vaniseghem@klimtoren.be", "ofosok");
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
    @Test
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
}
