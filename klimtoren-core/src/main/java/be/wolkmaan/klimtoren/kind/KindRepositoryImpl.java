/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.kind;

import be.wolkmaan.klimtoren.persistence.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author karl
 */
@Repository("kindRepository")
public class KindRepositoryImpl extends HibernateRepository<Kind> implements KindRepository {

}
