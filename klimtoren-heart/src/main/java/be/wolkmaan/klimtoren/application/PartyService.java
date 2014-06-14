/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.application.builder.Account;
import be.wolkmaan.klimtoren.application.builder.Organization;
import be.wolkmaan.klimtoren.application.builder.Party;
import be.wolkmaan.klimtoren.application.builder.Person;
import be.wolkmaan.klimtoren.heart.enums.Kind;
import be.wolkmaan.klimtoren.heart.model.tables.records.PartiesRecord;
import java.util.stream.Stream;

/**
 *
 * @author karl
 */
public interface PartyService {

    /* SELECTS */
    public Stream<Organization> getOrganizations();
    public Stream<Organization> getOrganizations(Kind kind);
    
    public Stream<Person> getPersons(Organization forOrganization);
    public Stream<Person> getPersons(Organization forOrganization, Kind kind);
    public Stream<Account> getAccounts(Organization forOrganization);
    
    public Stream<Person> getEmployees(Organization ofOrganization);
    public Stream<Person> getParents(Organization ofOrganization);
    
    /* CRUD */
    public Organization getOrganizationById(Integer id);
    public Stream<Organization> getOrganizationsByName(String name);
    public Stream<Organization> getOrganizationsLikeName(String likeName);
    
    public Person getPersonById(Integer id);
    public Person getPersonByUsername(String username);
    public Account getAccountByUsername(String username);
    public boolean login(String username, String password);
    
    public PartiesRecord createPerson(Person person);
    public PartiesRecord createAccount(Account account, Organization forOrganization);
    public Organization createOrganization(Organization organization);
    
    /* RELATIONS */
    public void createRelation(Party context, Party reference, Kind kind);
    public void createRelation(Party context, Party reference, Kind kind, Kind reverseKind);
}
