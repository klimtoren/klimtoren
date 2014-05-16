/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security;

import be.wolkmaan.klimtoren.security.digest.StandardStringDigester;
import be.wolkmaan.klimtoren.security.encryption.pbe.StandardPBEStringEncryptor;
import be.wolkmaan.klimtoren.security.util.StrongPasswordEncryptor;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author karl
 */
@Slf4j
public class StrongTextEncryptorTest {

    

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
}
