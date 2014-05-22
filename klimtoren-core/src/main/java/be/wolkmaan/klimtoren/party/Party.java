/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.PartyLocation;
import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @Enumerated(EnumType.STRING)
    private Kind kind;
    @Enumerated(EnumType.STRING)
    private Kind primaryKind;

    @OneToMany(mappedBy = "forParty")
    @Cascade(CascadeType.ALL)
    private List<PartyAttribute> attributes;

    @OneToMany(mappedBy = "forParty")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<FullName> names;
    
    @OneToMany(mappedBy="forParty")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<PartyLocation> locations;

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
    public void addLocation(PartyLocation location) {
        if(locations == null) {
            locations = new ArrayList<>();
        }
        location.setForParty(this);
        locations.add(location);
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

    public PartyAttribute getAttribute(String key) {
        if(this.getAttributes() == null)
            return null;
        for (PartyAttribute pa : this.getAttributes()) {
            if (pa.getName().toUpperCase().equals(key.toUpperCase())) {
                return pa;
            }
        }
        return null;
    }
    public void setAttribute(String key, String value) {
        PartyAttribute pa = getAttribute(key);
        if(pa == null) {
            pa = new PartyAttribute();
            pa.setName(key);
            pa.setValue(value);
            pa.setStart(new Date());
            pa.setForParty(this);
        } else {
            pa.setValue(value);
        }
        if(this.attributes == null) {
            this.attributes = new ArrayList<>();
        }
        this.attributes.add(pa);
    }
}
