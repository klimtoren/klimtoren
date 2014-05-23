/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.LocationRepository;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.sample.SampleData;
import be.wolkmaan.klimtoren.web.config.PersistenceConfig;
import be.wolkmaan.klimtoren.web.config.RootConfig;
import be.wolkmaan.klimtoren.web.config.WebMvcConfig;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author karl
 */
@Slf4j
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, PersistenceConfig.class, WebMvcConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=false)
public class SchoolServiceImplTest {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    SampleData sampleData;
    
    @Before
    public void init() {
        sampleData.init();
    }
    
    @Transactional
    private void resetStudent() {
        if (sampleData.getJurre() != null && sampleData.getK1A()!= null) {
            List<PartyToPartyRelationship> p2ps = partyRepository.findRelation(sampleData.getJurre(), sampleData.getK1A());
            p2ps.stream().forEach((p2p) -> {
                p2p.setEnd(null);
            });
        }
    }

    @Test
    @Transactional
    public void findGroupForJurre() {
        PartyToPartyRelationship p2p = partyRepository.findRelation(sampleData.getJurre(), sampleData.getK1A(), Kind.STUDENT);
        assertNotNull(p2p);
    }
    @Test
    @Transactional
    public void findStudentsOfK1A() {
        List<PartyToPartyRelationship> list = partyRepository.findRelationsForReference(sampleData.getK1A(), Kind.STUDENT);
        List<PartyToPartyRelationship> list2 = partyRepository.findRelationsForReference(sampleData.getJurre(), Kind.CLASSGROUP);
        assertTrue(list.size() == 2);
        System.out.println("LIST 1");
        list.stream().forEach((rel) -> {
            System.out.println(rel.getContext().getDisplayName() + "->" + rel.getReference().getDisplayName() + " [" + rel.getKind().name() + "]"); 
        });
        System.out.println("LIST 2");
        list2.stream().forEach((rel) -> {
            System.out.println(rel.getContext().getDisplayName() + "->" + rel.getReference().getDisplayName() + " [" + rel.getKind().name() + "]"); 
        });
        partyService.stopRelation(list2.get(0));
        List<PartyToPartyRelationship> list3 = partyRepository.findRelationsForReference(sampleData.getJurre(), Kind.CLASSGROUP);
        System.out.println("LIST 3");
        list3.stream().forEach((rel) -> {
            System.out.println(rel.getContext().getDisplayName() + "->" + rel.getReference().getDisplayName() + " [" + rel.getKind().name() + "]"); 
        });
        List<PartyToPartyRelationship> list4 = partyRepository.findRelationsForReference(sampleData.getKlimtoren(), Kind.CLASSGROUP);
        System.out.println("LIST 4");
        list4.stream().forEach((rel) -> {
            System.out.println(rel.getContext().getDisplayName() + "->" + rel.getReference().getDisplayName() + " [" + rel.getKind().name() + "]"); 
        });
    }
}
