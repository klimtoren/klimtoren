/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.location;

import be.wolkmaan.klimtoren.persistence.Repository;

/**
 *
 * @author karl
 */
public interface LocationRepository extends Repository<Location> {
    public Mailbox findByMailbox(Mailbox mailbox);
}
