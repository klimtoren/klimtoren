/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.persistence.HibernateRepository;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository("partyRepository")
public class PartyRepositoryImpl extends HibernateRepository<Party> implements PartyRepository {

    @Override
    public Person findByUsername(String username) {
        Criteria crit = getSession().createCriteria(Person.class)
                .createAlias("authentication", "auth", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("auth.username", username).ignoreCase());

        return (Person) crit.uniqueResult();
    }

    @Override
    public Organization findOrganization(String name, Organization parent, Kind relationKind) {
        Criteria crit = getSession().createCriteria(PartyToPartyRelationship.class)
                .add(Restrictions.eq("reference", parent))
                .add(Restrictions.eq("kind", relationKind))
                .createAlias("context", "context")
                .add(Restrictions.eq("context.displayName", name).ignoreCase());
        
        return (Organization) crit.uniqueResult();
    }

    @Override
    public PartyToPartyRelationship get(Long id) {
        return (PartyToPartyRelationship) getSession().createCriteria(PartyToPartyRelationship.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public Party remove(Serializable id) {
        throw new UnsupportedOperationException("You can't remove a party. Stop relations and end fullnames instead.");
    }

    @Override
    public PartyToPartyRelationship findRelation(Party context, Party reference, Kind kind) {
        Criteria crit = createUnidirectional(context, reference);
        crit.add(Restrictions.eq("kind", kind));
        return (PartyToPartyRelationship) crit.uniqueResult();
    }

    @Override
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference) {
        Criteria crit = createUnidirectional(context, reference);
        return (List<PartyToPartyRelationship>) crit.list();
    }

    @Override
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference, Kind kind, boolean bidirectional) {
        Criteria crit;
        if (bidirectional) {
            crit = createBidirectional(context, reference);
        } else {
            crit = createUnidirectional(context, reference);
        }

        crit.add(Restrictions.eq("kind", kind));

        return (List<PartyToPartyRelationship>) crit.list();
    }

    @Override
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference, boolean bidirectional) {
        Criteria crit;
        if (bidirectional) {
            crit = createBidirectional(context, reference);
        } else {
            crit = createUnidirectional(context, reference);
        }

        return (List<PartyToPartyRelationship>) crit.list();
    }

    /* ---------------------------------
     |  PRIVATE METHODS 
     --------------------------------- */
    private Criteria createBidirectional(Party context, Party reference) {
        Criteria crit = getSession().createCriteria(PartyToPartyRelationship.class)
                .add(Restrictions.or(
                                Restrictions.and(
                                        Restrictions.eq("context", context),
                                        Restrictions.eq("reference", reference)
                                ),
                                Restrictions.and(
                                        Restrictions.eq("context", reference),
                                        Restrictions.eq("reference", context)
                                )
                        ))
                .add(Restrictions.isNull("end")); //for now only search live relations
        return crit;
    }

    private Criteria createUnidirectional(Party context, Party reference) {
        Criteria crit = getSession().createCriteria(PartyToPartyRelationship.class)
                .add(Restrictions.and(
                                Restrictions.eq("context", context),
                                Restrictions.eq("reference", reference)
                        ))
                .add(Restrictions.isNull("end")); //for now only search live relations
        return crit;
    }
}
