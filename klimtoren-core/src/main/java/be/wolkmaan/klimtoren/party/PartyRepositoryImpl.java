/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.persistence.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository("partyRepository")
public class PartyRepositoryImpl extends HibernateRepository<Party> implements PartyRepository {
    
}
