/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Table(name = "parties")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Party extends EntitySupport<Party, Long> {

    private String descriptiveInformation;
    private String name;
    private String displayName;

    @ManyToOne
    private Kind kind;

    @ManyToOne
    private Kind primaryKind;

    @OneToMany(mappedBy = "forParty")
    private List<FullName> names;

    /*------------------------------
     | Methods
     -----------------------------*/
    public void addFullName(FullName fullName) {
        if (names == null) {
            names = new ArrayList<>();
        }
        fullName.setForParty(this);
        names.add(fullName);
    }
    
    /*------------------------------
     | Properties
     -----------------------------*/
    public FullName getFullName() {
        if (names != null && names.size() > 0) {
            return names.get(0);
        } else {
            return null;
        }
    }
}
