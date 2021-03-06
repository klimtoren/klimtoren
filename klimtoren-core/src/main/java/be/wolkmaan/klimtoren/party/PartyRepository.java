/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.persistence.Repository;
import java.util.List;

/**
 *
 * @author karl
 */
public interface PartyRepository extends Repository<Party> {

    public Person findByUsername(String username);

    /**
     * Finds an child organisation by name.
     *
     * @param name
     * @param parent
     * @param relationKind
     * @return
     */
    public Organization findOrganization(String name, Organization parent, Kind relationKind);

    /**
     * Find all relations to a reference for a given kind.
     *
     * @param reference
     * @param kind
     * @return
     */
    public List<PartyToPartyRelationship> findRelationsForReference(Party reference, Kind kind);
    
    /**
     * Find all relatinos from a context for a given kind.
     * @param context
     * @param kind
     * @return 
     */
    public List<PartyToPartyRelationship> findRelationsForContext(Party context, Kind kind);

    /**
     * Get a relation between two parties by its identifier.
     *
     * @param id
     * @return
     */
    public PartyToPartyRelationship get(Long id);

    /**
     * TODO: remove !!!
     * @return
     */
    public List<PartyToPartyRelationship> listRelations();

    /**
     * Find all relations for a given conxtext.
     *
     * @param context
     * @return
     */
    public List<PartyToPartyRelationship> listRelations(Party context);

    /**
     * Finds a relation between two parties, of a specific kind. The relation is
     * not ended at this point.
     *
     * @param context
     * @param reference
     * @param kind
     * @return
     */
    public PartyToPartyRelationship findRelation(Party context, Party reference, Kind kind);

    /**
     * Finds all relations between two parties. The relation is not ended at
     * this point.
     *
     * @param context
     * @param reference
     * @return
     */
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference);

    /**
     * Finds a relation between two parties, of a specific kind. The relation is
     * not ended at this point. This method searches bidirectional (context <>
     * reference and vice versa)
     *
     * @param context
     * @param reference
     * @param kind
     * @param bidirectional
     * @return
     */
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference, Kind kind, boolean bidirectional);

    /**
     * Finds all relations between two parties. The relation is not ended at
     * this point. This method searches bidirectional (context <> reference and
     * vice versa)
     *
     * @param context
     * @param reference
     * @param bidirectional
     * @return
     */
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference, boolean bidirectional);

}
