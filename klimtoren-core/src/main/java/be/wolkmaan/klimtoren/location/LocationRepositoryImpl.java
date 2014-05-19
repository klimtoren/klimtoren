/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.location;

import be.wolkmaan.klimtoren.persistence.HibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("locationRepository")
public class LocationRepositoryImpl extends HibernateRepository<Location> implements LocationRepository {
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
}
