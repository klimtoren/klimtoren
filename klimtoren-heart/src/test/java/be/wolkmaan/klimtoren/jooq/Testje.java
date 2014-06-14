/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.jooq;

import be.wolkmaan.klimtoren.heart.enums.Kind;
import static be.wolkmaan.klimtoren.heart.model.Tables.PARTIES;
import be.wolkmaan.klimtoren.heart.model.tables.Parties;
import be.wolkmaan.klimtoren.heart.model.tables.records.PartiesRecord;
import be.wolkmaan.klimtoren.jooq.config.PersistenceContext;
import javax.transaction.Transactional;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author karl
 */
@ContextConfiguration(classes = {PersistenceContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class Testje {
    
    @Autowired
    DSLContext create;
    
    @Test
    @Transactional
    public void testNewRecord() {
        Parties t = PARTIES.as("t");
        PartiesRecord party = create.newRecord(t)
                .setDisplayName("VBS De Klimtoren")
                .setKind(Kind.WORK)
                .setDescriptiveInformation("Test");
        party.store();
        party.setDescriptiveInformation("Test 2")
                .store();
    }
}

