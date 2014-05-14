/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.application;

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
    
   
    @Test
    public void testRegisterPerson() {
        Person p = partyService.registerNewPerson("Karl", "Van Iseghem", "Flor");
        assertNotNull(p.getFullName());
        assertTrue(p.getId() != null && p.getId() > 0);
        assertTrue(p.getFullName().getStartDate().compareTo(new Date()) == -1);
    }
    @Test
    public void testRegisterUser() {
        try {
            Person p = partyService.registerNewUser("Karl", "Van Iseghem", "Flor", "karl.vaniseghem@klimtoren.be", "ofosok");
        } catch (UserAlreadyExistsException ex) {
            Logger.getLogger(PartyServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
