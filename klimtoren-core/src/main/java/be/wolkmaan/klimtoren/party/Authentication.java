/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.shared.EntitySupport;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
@Table(name = "authentications")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Authentication extends EntitySupport<Authentication, Long> {
    private String username;
    private String password;
    private String salt;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastLogin;
    private int loginAttemptFailureCount;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastAttemptFailureTime;
    private boolean isGranted;
    private boolean isLocked;
    private boolean enableAutoUnlock;
    
    
    @OneToOne
    @JoinColumn(name="forPerson")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Person forPerson;
    
    @Override
    public String toString() {
        return getUsername();
    }
}
