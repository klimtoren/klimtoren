/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.kind;

import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author karl
 */
 @Table(name="kinds")
 @Entity
public class Kind extends EntitySupport<Kind, Long> {
     @NaturalId
     @Getter @Setter
     private String name;
     
     @Getter @Setter
     private String descriptiveInformation;
     
     @NaturalId
     @ManyToOne
     @Getter @Setter
     private Party owner;

    /* ---------------------------
     | Contructors 
     * --------------------------- */
     public Kind() {
         
     }
     public Kind(String name, String descriptiveInformation, Party owner) {
         Validate.notNull(name, "Name is required.");
         
         setName(name);
         setDescriptiveInformation(descriptiveInformation);
         setOwner(owner);
     }
     public Kind(Long id, String name) {
         Validate.notNull(id, "Id is required");
         Validate.notNull(name, "Name is required.");
         
         setId(id);
         setName(name);
     }
}
