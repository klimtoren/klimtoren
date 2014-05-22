/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.location;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Party;
import be.wolkmaan.klimtoren.persistence.Repository;
import java.util.List;

/**
 *
 * @author karl
 */
public interface LocationRepository extends Repository<Location> {

    public Mailbox findByMailbox(Mailbox mailbox);

    public Mailbox saveOrGetMailbox(Mailbox mailbox);

    public Country findCountryByCode(String code);

    public List<PartyLocation> findPartyLocations(Party party);

    public List<PartyLocation> findPartyLocations(Party party, Kind kind);

}
