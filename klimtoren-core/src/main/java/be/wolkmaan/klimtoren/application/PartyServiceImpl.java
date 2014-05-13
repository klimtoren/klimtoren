/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.kind.KindRepository;
import be.wolkmaan.klimtoren.party.FullName;
import be.wolkmaan.klimtoren.party.Membership;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.Person;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author karl
 */
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private KindRepository kindRepository;

    @Override
    public Person registerNewPerson(String givenName, String surName, String middleName) {
        Date registerDate = new Date();
        String displayName = givenName + 
                (Strings.isNullOrEmpty(middleName) ? " " : " " + middleName + " ") + 
                surName;
        Person p = new Person();
        p.setDisplayName(displayName);
        
        FullName fn = new FullName();
        fn.setGivenName(givenName);
        fn.setSurName(surName);
        fn.setMiddleName(middleName);
        fn.setStartDate(registerDate);
        
        p.addFullName(fn);
        
        fn.setForParty(p);
        partyRepository.store(p);
        //TODO: p.setKind(Kinds.PERSON);
        return null;
    }

    @Override
    public Membership registerNewUser(String givenName, String surName, String middleName, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organization registerNewOrganization(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Organization registerNewOrganization(String name, Party parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
