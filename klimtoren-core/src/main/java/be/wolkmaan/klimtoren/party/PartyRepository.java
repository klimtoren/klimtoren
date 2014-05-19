/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.party;

import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.persistence.Repository;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author karl
 */
public interface PartyRepository extends Repository<Party> {
    public Person findByUsername(String username);

    public PartyToPartyRelationship get(Long id);

    /**
     * Finds a relation between two parties, of a specific kind. The relation is not ended at this point.
     * @param context
     * @param reference
     * @param kind
     * @return 
     */
    public PartyToPartyRelationship findRelation(Party context, Party reference, Kind kind);
    /**
     * Finds all relations between two parties. The relation is not ended at this point.
     * @param context
     * @param reference
     * @return 
     */
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference);
    
    /**
     * Finds a relation between two parties, of a specific kind. The relation is not ended at this point.
     * This method searches bidirectional (context <> reference and vice versa)
     * @param context
     * @param reference
     * @param kind
     * @param bidirectional
     * @return 
     */
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference, Kind kind, boolean bidirectional);
    
    /**
     * Finds all relations between two parties. The relation is not ended at this point.
     * This method searches bidirectional (context <> reference and vice versa)
     * @param context
     * @param reference
     * @param bidirectional
     * @return 
     */
    public List<PartyToPartyRelationship> findRelation(Party context, Party reference, boolean bidirectional);
}
