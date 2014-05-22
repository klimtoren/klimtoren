/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security;

import be.wolkmaan.klimtoren.application.PartyService;
import be.wolkmaan.klimtoren.application.UserAlreadyExistsException;
import be.wolkmaan.klimtoren.exceptions.UserDoesNotExistException;
import be.wolkmaan.klimtoren.exceptions.UserLockedException;
import be.wolkmaan.klimtoren.exceptions.UserNotAllowedException;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.party.Person.Gender;
import be.wolkmaan.klimtoren.security.util.StrongPasswordEncryptor;
import be.wolkmaan.klimtoren.web.config.PersistenceConfig;
import be.wolkmaan.klimtoren.web.config.RootConfig;
import be.wolkmaan.klimtoren.web.config.WebMvcConfig;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
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
public class StrongTextEncryptorTest {

    @Autowired
    private PartyService partyService;

    @Before
    public void setUp() {

    }

    @Test
    public void testEncrypt() {
        String pass1 = "pass";
        String pass2 = "pass";
        String pass3 = "pass2";

        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        String enc1 = encryptor.encryptPassword(pass1);
        String enc2 = encryptor.encryptPassword(pass2);
        String enc3 = encryptor.encryptPassword(pass3);

        assertTrue(encryptor.checkPassword(pass1, enc2));
        assertFalse(encryptor.checkPassword(pass1, enc3));
    }

    @Test
    public void login() {
        try {
            Person p = partyService.registerNewUser("Karl", "Van Iseghem", "Flor", Gender.MALE, "karl.vaniseghem@klimtoren.be", "pass");

            try {
                boolean granted = partyService.login(p.getAuthentication().getUsername(), "pass");
                assertTrue(granted);
            } catch (UserDoesNotExistException ex) {
                assertTrue("User does not exists.".equals(ex.getMessage()));
            } catch (UserNotAllowedException ex) {
                assertTrue("User is not allowed.".equals(ex.getMessage()));
            } catch (UserLockedException ex) {
                assertTrue("User is locked.".equals(ex.getMessage()));
            }

            try {
                boolean granted = partyService.login("notexists", "pass");
                assertFalse(granted);
            } catch (UserDoesNotExistException ex) {
                assertTrue("User does not exists.".equals(ex.getMessage()));
            } catch (UserNotAllowedException ex) {
                assertTrue("User is not allowed.".equals(ex.getMessage()));
            } catch (UserLockedException ex) {
                assertTrue("User is locked.".equals(ex.getMessage()));
            }

            try {
                boolean granted = partyService.login(p.getAuthentication().getUsername(), "pass2");
                assertFalse(granted);
                granted = partyService.login(p.getAuthentication().getUsername(), "pass2");
                assertFalse(granted);
                granted = partyService.login(p.getAuthentication().getUsername(), "pass2");
                assertFalse(granted);
            } catch (UserDoesNotExistException ex) {
                assertTrue("User does not exists.".equals(ex.getMessage()));
            } catch (UserNotAllowedException ex) {
                assertTrue("User is not allowed.".equals(ex.getMessage()));
            } catch (UserLockedException ex) {
                assertTrue("User is locked.".equals(ex.getMessage()));
            }

        } catch (UserAlreadyExistsException ex) {
            assertTrue("User already exists.".equals(ex.getMessage()));
        }

    }
}
