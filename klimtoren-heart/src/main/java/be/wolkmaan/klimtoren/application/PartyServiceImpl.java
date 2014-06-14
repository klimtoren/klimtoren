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
import be.wolkmaan.klimtoren.common.Utils;
import be.wolkmaan.klimtoren.heart.enums.Gender;
import be.wolkmaan.klimtoren.heart.enums.Kind;
import static be.wolkmaan.klimtoren.heart.model.tables.Authentication.AUTHENTICATION;
import static be.wolkmaan.klimtoren.heart.model.tables.Fullnames.FULLNAMES;
import static be.wolkmaan.klimtoren.heart.model.tables.Organizations.ORGANIZATIONS;
import static be.wolkmaan.klimtoren.heart.model.tables.P2pRelations.P2P_RELATIONS;
import static be.wolkmaan.klimtoren.heart.model.tables.Parties.PARTIES;
import static be.wolkmaan.klimtoren.heart.model.tables.Persons.PERSONS;
import be.wolkmaan.klimtoren.heart.model.tables.records.AuthenticationRecord;
import be.wolkmaan.klimtoren.heart.model.tables.records.PartiesRecord;
import be.wolkmaan.klimtoren.heart.model.tables.records.PersonsRecord;
import be.wolkmaan.klimtoren.mapper.Mapper;
import com.google.common.base.Strings;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Record6;
import org.jooq.Record8;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.exception.DataTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author karl
 */
@Service("partyService")
public class PartyServiceImpl implements PartyService {

    @Autowired
    DSLContext create;
    @Autowired
    private EventService eventService;

    /* ++++++++++++++++++++++++++++++++++++++++
     + SELECT OPERATIONS 
     +++++++++++++++++++++++++++++++++++++++ */
    /*----------------------------------------
     | ORGANIZATION 
     ----------------------------------------*/
    @Override
    public Stream<Organization> getOrganizations() {
        return selectOrganziationFields()
                .from(PARTIES)
                .join(ORGANIZATIONS)
                .on(ORGANIZATIONS.ID.equal(PARTIES.ID))
                .fetch()
                .stream()
                .map(o -> Mapper.getInstance().map(o, Organization.class));
    }

    @Override
    public Stream<Organization> getOrganizations(Kind kind) {
        return selectOrganziationFields()
                .from(PARTIES)
                .join(ORGANIZATIONS)
                .on(ORGANIZATIONS.ID.equal(PARTIES.ID))
                .where(PARTIES.KIND.equal(kind))
                .fetch()
                .stream()
                .map(o -> Mapper.getInstance().map(o, Organization.class));
    }

    @Override
    public Organization getOrganizationById(Integer id) {
        return selectOrganziationFields()
                .from(PARTIES)
                .join(ORGANIZATIONS)
                .on(ORGANIZATIONS.ID.equal(PARTIES.ID))
                .where(PARTIES.ID.equal(id))
                .fetchOne()
                .map(o -> Mapper.getInstance().map(o, Organization.class));
    }

    @Override
    public Stream<Organization> getOrganizationsByName(String name) {
        return selectOrganziationFields()
                .from(PARTIES)
                .join(ORGANIZATIONS)
                .on(ORGANIZATIONS.ID.equal(PARTIES.ID))
                .where(PARTIES.DISPLAY_NAME.equal(name))
                .fetch()
                .stream()
                .map(o -> Mapper.getInstance().map(o, Organization.class));
    }

    @Override
    public Stream<Organization> getOrganizationsLikeName(String likeName) {
        return selectOrganziationFields()
                .from(PARTIES)
                .join(ORGANIZATIONS)
                .on(ORGANIZATIONS.ID.equal(PARTIES.ID))
                .where(PARTIES.DISPLAY_NAME.like("%" + likeName + "%"))
                .fetch()
                .stream()
                .map(o -> Mapper.getInstance().map(o, Organization.class));
    }

    private SelectSelectStep<Record4<Integer, String, String, Kind>> selectOrganziationFields() {
        return create.select(PARTIES.ID, PARTIES.DISPLAY_NAME, PARTIES.DESCRIPTIVE_INFORMATION, PARTIES.KIND);
    }


    /*----------------------------------------
     | PERSON 
     ----------------------------------------*/
    @Override
    public Stream<Person> getPersons(Organization forOrganization) {
        return selectDistinctPersons()
                .join(P2P_RELATIONS)
                .on(P2P_RELATIONS.CONTEXT.equal(PARTIES.ID))
                .where(P2P_RELATIONS.REFERENCE.equal(forOrganization.getId()))
                .fetch()
                .stream()
                .map(per -> Mapper.getInstance().map(per, Person.class));
    }

    @Override
    public Stream<Person> getPersons(Organization forOrganization, Kind relationKind) {
        return selectPersons()
                .join(P2P_RELATIONS)
                .on(P2P_RELATIONS.CONTEXT.equal(PARTIES.ID))
                .where(P2P_RELATIONS.REFERENCE.equal(forOrganization.getId()))
                .and(P2P_RELATIONS.KIND.equal(relationKind))
                .fetch()
                .stream()
                .map(per -> Mapper.getInstance().map(per, Person.class));
    }

    @Override
    public Stream<Account> getAccounts(Organization forOrganization) {
        return selectAccounts()
                .join(P2P_RELATIONS)
                .on(P2P_RELATIONS.CONTEXT.equal(PARTIES.ID))
                .where(P2P_RELATIONS.REFERENCE.equal(forOrganization.getId()))
                .and(AUTHENTICATION.ISGRANTED.equal(true))
                .fetch()
                .stream()
                .map(per -> Mapper.getInstance().map(per, Account.class));
    }

    @Override
    public Person getPersonById(Integer id) {
        return selectPersons()
                .where(PARTIES.ID.equal(id))
                .fetchOne()
                .map(per -> Mapper.getInstance().map(per, Person.class));
    }

    @Override
    public Person getPersonByUsername(String username) {
        return selectPersons()
                .join(AUTHENTICATION)
                .on(AUTHENTICATION.ID.equal(PARTIES.ID))
                . where(AUTHENTICATION.USERNAME.lower().equal(username.toLowerCase()))
                .fetchOne()
                .map(per -> Mapper.getInstance().map(per, Person.class));
    }

    /*----------------------------------------
     | ACCOUNTS 
     ----------------------------------------*/
    @Override
    public Account getAccountByUsername(String username) {
        return selectPersons()
                .join(AUTHENTICATION)
                .on(AUTHENTICATION.ID.equal(PARTIES.ID))
                .where(AUTHENTICATION.USERNAME.lower().equal(username.toLowerCase()))
                .fetchOne()
                .map(p -> mapAccount(p));
        //TODO !!!!!! map Person::forParty.givenname -> Person as Party 
    }

    /*----------------------------------------
     | TYPE SPECIFIC PERSONS 
     ----------------------------------------*/
    @Override
    public Stream<Person> getParents(Organization ofOrganization) {
        return getPersons(ofOrganization, Kind.PARENT);
    }

    @Override
    public Stream<Person> getEmployees(Organization ofOrganization) {
        return getPersons(ofOrganization, Kind.EMPLOYEE);
    }

    /*----------------------------------------
     | AUTHENTICATION 
     ----------------------------------------*/
    @Override
    public boolean login(String username, String password) {
        boolean granted = false;
        final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();

        AuthenticationRecord auth = (AuthenticationRecord) create.select()
                .from(AUTHENTICATION)
                .where(AUTHENTICATION.USERNAME.lower().equal(username.toLowerCase()))
                .fetchOne();

        Party authParty = mapParty(auth);

        if (auth != null) {
            granted = encryptor.checkPassword(password, auth.getPassword()) && !auth.getIslocked();
            if (granted) {
                auth.setLastloginDatetime(LocalDateTime.now());
                eventService.onEvent("login", String.format("Successfull login for %s.", username),
                        authParty, Kind.SUCCESS);
            } else {
                eventService.onEvent("login", String.format("Login failed for %s.", username),
                        authParty, Kind.WARNING);
                if (auth.getIslocked()) {
                    LocalDateTime prev = auth.getLastloginattemptfailureDatetime();
                    LocalDateTime now = LocalDateTime.now();
                    Duration between = Duration.between(prev, now);
                    if (between.toMinutes() > 30 && auth.getEnableautounlock()) {
                        auth.setIslocked(false);
                        auth.setLastloginattemptfailureDatetime(null);
                        auth.setLoginattemptfailurecount(0);
                    }
                }
                if (!auth.getIslocked()) {
                    auth.setLastloginattemptfailureDatetime(LocalDateTime.now());
                    auth.setLoginattemptfailurecount(auth.getLoginattemptfailurecount() + 1);
                    if (auth.getLoginattemptfailurecount() == 3) {
                        auth.setIslocked(true);
                        eventService.onEvent("login",
                                String.format("%s was locked. Auto-unlock is set to %s", username, auth.getEnableautounlock()),
                                authParty, Kind.SEVERE);
                    }
                }
            }
            auth.store();
        }
        return granted;
    }

    /* ++++++++++++++++++++++++++++++++++++++++
     + CRUD OPERATIONS 
     +++++++++++++++++++++++++++++++++++++++ */
    /*----------------------------------------
     | PERSONS 
     ----------------------------------------*/
    @Override
    public PartiesRecord createPerson(Person person) {
        PartiesRecord party = create.insertInto(PARTIES, PARTIES.KIND, PARTIES.DISPLAY_NAME)
                .values(person.getKind(), getDisplayname(person))
                .returning(PARTIES.ID, PARTIES.DISPLAY_NAME)
                .fetchOne();

        PersonsRecord p = create.newRecord(PERSONS);
        p.setGender(person.getGender())
                .setId(party.getId())
                .store();

        return party;
    }

    /*----------------------------------------
     | ACCOUNTS 
     ----------------------------------------*/
    @Override
    public PartiesRecord createAccount(Account account, Organization forOrganization) {

        String displayname = getDisplayname(account);
        PartiesRecord party = create.insertInto(PARTIES, PARTIES.KIND, PARTIES.DISPLAY_NAME)
                .values(account.getKind(), displayname)
                .returning(PARTIES.ID, PARTIES.DISPLAY_NAME)
                .fetchOne();
        PersonsRecord person = create.newRecord(PERSONS);
        AuthenticationRecord authentication = create.newRecord(AUTHENTICATION);

        if (!account.isEncrypted()) {
            //encrypt password
            account.setPassword(this.encryptPassword(account.getPassword()));
        }
        if (Strings.isNullOrEmpty(account.getUsername()) && !Strings.isNullOrEmpty(account.getDomain())) {
            final String domain = account.getDomain();
            final String given = Utils.normalizeAndTrim(account.getGivenname()).toLowerCase();
            final String sur = Utils.normalizeAndTrim(account.getSurname()).toLowerCase();
            account.setUsername(String.format("%s.%s@%s", given, sur, domain));
        }

        person.setId(party.getId())
                .setGender(account.getGender())
                .store();

        authentication.setId(party.getId())
                .setPassword(account.getPassword())
                .setUsername(account.getUsername())
                .setEnableautounlock(true)
                .setIsgranted(true)
                .setIslocked(false)
                .setLastloginDatetime(null)
                .setLastloginattemptfailureDatetime(null)
                .setLoginattemptfailurecount(0)
                .store();

        //TODO: create relation here ? kind and reversekind
        return party;
    }

    /*----------------------------------------
     | ORGANIZATIONS 
     ----------------------------------------*/
    @Override
    public Organization createOrganization(Organization organization) {
        PartiesRecord party = create.insertInto(PARTIES, PARTIES.KIND, PARTIES.DISPLAY_NAME)
                .values(organization.getKind(), organization.getDisplayName())
                .returning(PARTIES.ID, PARTIES.DISPLAY_NAME)
                .fetchOne();

        create.newRecord(ORGANIZATIONS)
                .setId(party.getId())
                .store();

        return mapOrg(party);
    }

    /*----------------------------------------
     | RELATIONS 
     ----------------------------------------*/
    @Override
    public void createRelation(Party context, Party reference, Kind kind) {
        create.newRecord(P2P_RELATIONS)
                .setContext(context.getId())
                .setReference(reference.getId())
                .setStartDatetime(LocalDateTime.now())
                .setKind(kind)
                .store();
    }

    @Override
    public void createRelation(Party context, Party reference, Kind kind, Kind reverseKind) {
        createRelation(context, reference, kind);
        createRelation(reference, context, reverseKind);
    }

    /* ++++++++++++++++++++++++++++++++++++++++
     + PRIVATE SELECT STEPS 
     +++++++++++++++++++++++++++++++++++++++ */
    private SelectOnConditionStep<Record6<Integer, String, String, String, Kind, Gender>> selectPersons() {
        return create.select(PARTIES.ID, FULLNAMES.GIVENNAME, FULLNAMES.SURNAME,
                FULLNAMES.MIDDLENAME, PARTIES.KIND, PERSONS.GENDER)
                .from(PARTIES)
                .join(PERSONS)
                .on(PERSONS.ID.equal(PARTIES.ID))
                .leftOuterJoin(FULLNAMES)
                .on(FULLNAMES.FORPARTY.equal(PARTIES.ID));
    }

    private SelectOnConditionStep<Record6<Integer, String, String, String, Kind, Gender>> selectDistinctPersons() {
        return create.selectDistinct(PARTIES.ID, FULLNAMES.GIVENNAME, FULLNAMES.SURNAME,
                FULLNAMES.MIDDLENAME, PARTIES.KIND, PERSONS.GENDER)
                .from(PARTIES)
                .join(PERSONS)
                .on(PERSONS.ID.equal(PARTIES.ID))
                .leftOuterJoin(FULLNAMES)
                .on(FULLNAMES.FORPARTY.equal(PARTIES.ID));
    }

    private SelectOnConditionStep<Record8<Integer, String, String, String, Kind, Gender, String, Boolean>> selectAccounts() {
        return create.selectDistinct(PARTIES.ID, FULLNAMES.GIVENNAME, FULLNAMES.SURNAME,
                FULLNAMES.MIDDLENAME, PARTIES.KIND, PERSONS.GENDER,
                AUTHENTICATION.USERNAME, AUTHENTICATION.ISLOCKED)
                .from(PARTIES)
                .join(PERSONS)
                .on(PERSONS.ID.equal(PARTIES.ID))
                .join(AUTHENTICATION)
                .on(AUTHENTICATION.ID.equal(PARTIES.ID))
                .leftOuterJoin(FULLNAMES)
                .on(FULLNAMES.FORPARTY.equal(PARTIES.ID));
    }

    /* ++++++++++++++++++++++++++++++++++++++++
     + MAPPINGS 
     +++++++++++++++++++++++++++++++++++++++ */
    private static Organization mapOrg(Record o) throws DataTypeException, IllegalArgumentException {
        return new Organization.Builder()
                .id(o.getValue("id", Integer.class))
                .displayName(o.getValue("displayname", String.class))
                .descriptiveInformation(o.getValue("descriptiveinformation", String.class))
                .kind(o.getValue("kind", Kind.class))
                .build();
    }

    private static Party mapParty(Record per) {
        return new Party.Builder()
                .id(per.getValue("id", Integer.class))
                .build();
    }

    private static Person mapPerson(Record per) throws DataTypeException, IllegalArgumentException {
        return new Person.Builder()
                .id(per.getValue("id", Integer.class))
                .gender(per.getValue("gender", Gender.class))
                .givenname(per.getValue("givenname", String.class))
                .surname(per.getValue("surname", String.class))
                .kind(per.getValue("kind", Kind.class))
                .build();
    }

    private static Account mapAccount(Record a) {
        Account.Builder builder = new Account.Builder()
                .id(a.getValue("id", Integer.class))
                .gender(a.getValue("gender", Gender.class))
                .givenname(a.getValue("givenname", String.class))
                .surname(a.getValue("surname", String.class))
                .kind(a.getValue("kind", Kind.class))
                .username(a.getValue("username", String.class));
        if (a.field("password") != null) {
            builder.password(a.getValue("password", String.class), true);
        }

        return builder.build();
    }

    /* ++++++++++++++++++++++++++++++++++++++++
     + PRIVATE HELPER METHODS 
     +++++++++++++++++++++++++++++++++++++++ */
    private String getDisplayname(Person person) {
        return String.format("%s %s", person.getGivenname(), person.getSurname());
    }

    private String encryptPassword(String password) {
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor.encryptPassword(password);
    }

}
