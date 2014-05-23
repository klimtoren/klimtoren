/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.exceptions.PartyAlreadyExistsException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.LocationRepository;
import be.wolkmaan.klimtoren.location.Mailbox;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.PartyRepository;
import be.wolkmaan.klimtoren.party.PartyToPartyRelationship;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.party.Person.Gender;
import be.wolkmaan.klimtoren.shared.tuples.Pair;
import be.wolkmaan.klimtoren.shared.tuples.PairList;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author karl
 */
@Slf4j
@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Override
    @Transactional
    public Person registerNewStudent(String givenName, String surName, String middleName,
            Organization school, Gender gender, PartyAttribute... details) throws NoDomainNameFoundException {

        Person student = createUser(school, givenName, surName, middleName, gender, details);

        partyService.registerRelation(student, school, Kind.STUDENT, Kind.SCHOOL);

        return student;
    }

    @Override
    public Person unSubscribeStudent(Person student, Organization school) {
        return unSubscribeStudent(student, school, new Date());
    }

    @Override
    @Transactional
    public Person unSubscribeStudent(Person student, Organization school, Date end) {
        List<PartyToPartyRelationship> relations = partyRepository.findRelation(student, school, true);
        if (relations != null) {
            relations.stream().map((relation) -> {
                relation.setEnd(end);
                return relation;
            }).forEach((relation) -> {
                partyRepository.store(relation);
            });
        }
        return student;
    }

    @Override
    @Transactional
    public Organization registerNewSchool(String schoolName, String descriptiveInformation, Mailbox address, String domainName) {
        Organization school = createSchool(schoolName, descriptiveInformation);

        partyRepository.store(school);

        address = locationRepository.saveOrGetMailbox(address);
        partyService.createPartyLocation(address, school, Kind.WORK, true, true);
        partyService.addPartyDetails(school,
                Lists.newArrayList(new PartyAttribute("domainName", domainName, Kind.STRING)));
        partyRepository.store(school);
        return school;
    }

    @Override
    @Transactional
    public Organization registerNewDepartment(String departmentName, String descriptiveInformation, Mailbox address, Organization forSchool) {
        Organization department = createSchool(departmentName, descriptiveInformation);

        address = locationRepository.saveOrGetMailbox(address);
        partyService.createPartyLocation(address, department, Kind.WORK, true, false);
        partyRepository.store(department);

        partyService.registerRelation(department, forSchool, Kind.DEPARTMENT, Kind.PRINCIPAL_SCHOOL);

        return department;
    }

    @Override
    @Transactional
    public Organization registerNewGroup(String groupName, String descriptiveInformation, Organization school, PartyAttribute... details)
            throws PartyAlreadyExistsException {
        Organization classgroup = partyRepository.findOrganization(groupName, school, Kind.CLASSGROUP);
        if (classgroup == null) {
            classgroup = new Organization();
            classgroup.setDisplayName(groupName);
            classgroup.setDescriptiveInformation(descriptiveInformation);
            classgroup.setPrimaryKind(Kind.CLASSGROUP);
            classgroup.setAttributes(Lists.newArrayList(details));

            partyRepository.store(classgroup);

            partyService.registerRelation(classgroup, school, Kind.CLASSGROUP, Kind.SCHOOL);
        } else {
            throw new PartyAlreadyExistsException("The classgroup already exists.");
        }
        return classgroup;
    }

    @Override
    public Organization registerNewGroup(String groupName, String descriptiveInformation, PairList<Organization, Kind> parents, PartyAttribute... details) throws PartyAlreadyExistsException {
        Optional<Organization> school = parents.stream()
                .filter((parent) -> parent.getValue1().equals(Kind.SCHOOL))
                .map((parent) -> parent.getValue0())
                .findFirst();
        if (!school.isPresent()) {
            throw new IllegalArgumentException("There must be at least one Kind.SCHOOL in the parents list.");
        }
        Organization classgroup = partyRepository.findOrganization(groupName, school.get(), Kind.CLASSGROUP);
        if (classgroup == null) {
            classgroup = new Organization();
            classgroup.setDisplayName(groupName);
            classgroup.setDescriptiveInformation(descriptiveInformation);
            classgroup.setPrimaryKind(Kind.CLASSGROUP);
            classgroup.setAttributes(Lists.newArrayList(details));

            partyRepository.store(classgroup);
            for (Pair<Organization, Kind> parent : parents) {
                //register classgroup as Kind.CLASSGROUP, parent of kind specified in the PairList.
                partyService.registerRelation(classgroup, parent.getValue0(), Kind.CLASSGROUP, parent.getValue1());
            }
        } else {
            throw new PartyAlreadyExistsException("The classgroup already exists.");
        }
        return classgroup;

    }

    @Override
    @Transactional
    public void addStudentToGroup(Person student, Organization group) {
        partyService.registerRelation(student, group, Kind.STUDENT, Kind.CLASSGROUP);
    }

    @Override
    @Transactional
    public void addStudentToGroup(Person student, Organization group, Date start) {
        partyService.registerRelation(student, group, Kind.STUDENT, Kind.CLASSGROUP, start);
    }

    @Override
    @Transactional
    public Person registerNewTeacher(String givenName, String surName, String middleName, Organization school, Gender gender, PartyAttribute... details)
            throws NoDomainNameFoundException {
        Person teacher = createUser(school, givenName, surName, middleName, gender, details);

        partyService.registerRelation(teacher, school, Kind.TEACHER, Kind.SCHOOL);
        return teacher;
    }

    @Override
    public void addTeacherToGroup(Person teacher, Organization group, boolean isTitular) {
        partyService.registerRelation(teacher, group, isTitular ? Kind.TITULAR : Kind.TEACHER, Kind.CLASSGROUP);
    }

    @Override
    public void addTeacherToGroup(Person teacher, Organization group, boolean isTitular, Date start) {
        partyService.registerRelation(teacher, group, isTitular ? Kind.TITULAR : Kind.TEACHER, Kind.CLASSGROUP, start);
    }

    @Override
    public Person connectParentToStudent(Organization school, String givenName, String surName, String middleName, Gender gender, Person student, Kind relation)
            throws NoDomainNameFoundException {
        Person parent = createUser(school, givenName, surName, middleName, gender);
        partyService.registerRelation(parent, school, Kind.PARENT, Kind.SCHOOL);
        partyService.registerRelation(parent, student, relation, student.getGender().equals(Gender.MALE) ? Kind.SON : Kind.DAUGHTER);
        return parent;
    }

    @Override
    public void connectParentToStudent(Organization school, Person parent, Person student, Kind relation) {
        partyService.registerRelation(parent, school, Kind.PARENT, Kind.SCHOOL); //if parent is not yet added to the school
        partyService.registerRelation(parent, student, relation, student.getGender().equals(Gender.MALE) ? Kind.SON : Kind.DAUGHTER);
    }

    @Override
    public void connectStudentToParent(Organization school, Person student, Person parent) {
        partyService.registerRelation(parent, school, Kind.PARENT, Kind.SCHOOL);
        partyService.registerRelation(student, parent,
                student.getGender().equals(Gender.MALE) ? Kind.SON : Kind.DAUGHTER,
                parent.getGender().equals(Gender.MALE) ? Kind.FATHER : Kind.MOTHER);

    }

    @Override
    public void connectParentsToStudent(Organization school, Person partner1, Person partner2, Person student) {
        connectParentToStudent(school, partner1, student, partner1.getGender().equals(Gender.MALE) ? Kind.FATHER : Kind.MOTHER);
        connectParentToStudent(school, partner2, student, partner2.getGender().equals(Gender.MALE) ? Kind.FATHER : Kind.MOTHER);
    }

    @Override
    public void connectParentsToStudent(Organization school, Person partner1, Person partner2, Person student, Kind parentsRelation) {
        connectParentsToStudent(school, partner1, partner2, student);
        registerParentsRelation(partner1, partner2, parentsRelation);
    }

    @Override
    @Transactional
    public void registerParentsRelation(Person partner1, Person partner2, Kind parentsRelation) {
        partyService.registerRelation(partner1, partner2, parentsRelation);
        if (parentsRelation.equals(Kind.MARRIED)) {
            partyService.registerRelation(partner1, partner2,
                    (partner1.getGender().equals(Gender.MALE) ? Kind.HUSBAND : Kind.WIFE),
                    (partner2.getGender().equals(Gender.MALE) ? Kind.HUSBAND : Kind.WIFE));
        } else if (parentsRelation.equals(Kind.DIVORCED)) {
            List<PartyToPartyRelationship> relations = partyRepository.findRelation(partner1, partner2, true);
            relations.stream()
                    .filter((relation) -> (relation.getKind().equals(Kind.WIFE)
                            || relation.getKind().equals(Kind.HUSBAND)
                            || relation.getKind().equals(Kind.MARRIED)))
                    .map((relation) -> {
                        relation.setEnd(new Date()); //when 
                        return relation;
                    }).forEach((relation) -> {
                        partyRepository.store(relation);
                    });
        }
    }

    /* ----------------------------------------
     |  PRIVATE METHODS 
     ---------------------------------------- */
    /**
     * Generates an user with default username and auto-generated password. The
     * user relates to a given school.
     *
     * @param school
     * @param givenName
     * @param surName
     * @param middleName
     * @param details
     * @return
     * @throws NoDomainNameFoundException
     */
    @Transactional
    private Person createUser(Organization school, String givenName, String surName, String middleName, Gender gender, PartyAttribute... details) throws NoDomainNameFoundException {
        return partyService.registerNewUser(school, givenName, surName, middleName, gender, details);
    }

    private Organization createSchool(String departmentName, String descriptiveInformation) {
        Organization org = new Organization();
        org.setDisplayName(departmentName);
        org.setDescriptiveInformation(descriptiveInformation);
        org.setPrimaryKind(Kind.SCHOOL);
        return org;
    }
}
