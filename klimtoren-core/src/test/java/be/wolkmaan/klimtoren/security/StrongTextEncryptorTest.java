/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security;

import be.wolkmaan.klimtoren.security.encryption.pbe.StandardPBEStringEncryptor;
import lombok.extern.slf4j.Slf4j;
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

    private StandardPBEStringEncryptor encryptor;

    @Before
    public void setUp() {
        this.encryptor = new StandardPBEStringEncryptor();
        this.encryptor.setAlgorithm("PBEWithMD5AndDES");
    }

    @Test
    public void testEncrypt() {
        String pwd1 = "pass";
        String pwd2 = "pass";
        String pwd3 = "other";

        this.encryptor.setPassword("password");
        String enc1 = this.encryptor.encrypt(pwd1);
        log.debug(enc1);
        String enc2 = this.encryptor.encrypt(pwd2); //TODO 
        log.debug(enc2);
        String enc3 = this.encryptor.encrypt(pwd2);
        log.debug(enc3);
        
        String p1 = this.encryptor.decrypt(enc1);
        log.debug(p1);
    }
}
