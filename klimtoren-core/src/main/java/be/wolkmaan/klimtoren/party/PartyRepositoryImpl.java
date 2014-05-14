/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.persistence.HibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("partyRepository")
public class PartyRepositoryImpl extends HibernateRepository<Party> implements PartyRepository {

    @Override
    public Person findByUsername(String username) {
        String qs = "select p.displayName, a.username from Person as p join p.authentication as a where a.username=:username";
        Query query = getSession().createQuery(qs)
                        .setParameter("username", username);

        return (Person)query.uniqueResult();
    }
    
}
