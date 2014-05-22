/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.location;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.persistence.HibernateRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("locationRepository")
public class LocationRepositoryImpl extends HibernateRepository<Location> implements LocationRepository {
    @Override
    public Mailbox findByMailbox(Mailbox mailbox) {
        Criteria crit = getSession().createCriteria(Mailbox.class)
                .add(Restrictions.and(
                        Restrictions.eq("street", mailbox.getStreet()),
                        Restrictions.eq("city", mailbox.getCity()),
                        Restrictions.eq("stateOrProvince", mailbox.getStateOrProvince()),
                        Restrictions.eq("country", mailbox.getCountry())
                ));
        return (Mailbox)crit.uniqueResult();
    }
    @Transactional
    @Override
    public Country findCountryByCode(String code) {
        Criteria crit = getSession().createCriteria(Country.class)
                .add(Restrictions.eq("countryCode", code));
        return (Country)crit.uniqueResult();
    }
    
    @Override
    public List<PartyLocation> findPartyLocations(Party party) {
        Criteria crit = getSession().createCriteria(PartyLocation.class)
                .add(Restrictions.eq("forParty", party));
        return (List<PartyLocation>)crit.list();
    }
    @Override
    public List<PartyLocation> findPartyLocations(Party party, Kind kind) {
        Criteria crit = getSession().createCriteria(PartyLocation.class)
                .add(Restrictions.eq("forParty", party))
                .add(Restrictions.eq("kind", kind));
        return (List<PartyLocation>)crit.list();
    }
    
    @Transactional
    @Override
    public Mailbox saveOrGetMailbox(Mailbox mailbox) {
        //find the new address in database
        //if it already exists, use this one
        //else store a new mailbox
        Mailbox inDB = findByMailbox(mailbox);
        if (inDB != null) {
            return inDB;
        } else {
            //not saved yet
            store(mailbox);
            return mailbox;
        }
    }
}
