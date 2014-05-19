/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.exceptions.PartyAlreadyExistsException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.Mailbox;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.Person;
import be.wolkmaan.klimtoren.party.Person.Gender;
import java.util.Date;

/**
 *
 * @author karl
 */
public interface SchoolService {

    /**
     * Adds a new student to a given school. This methods generates a standard
     * username (givenname.surname@school-domainname). If this username already
     * exists it will be extended with an integer after the surname. An initial
     * password is generated too. This password is stored in the attributes
     * property.
     *
     * @param givenName
     * @param surName
     * @param middleName
     * @param school
     * @param details
     * @return
     * @throws NoDomainNameFoundException
     */
    public Person registerNewStudent(String givenName, String surName, String middleName,
            Organization school, Gender gender, PartyAttribute... details)
            throws NoDomainNameFoundException;

    /**
     * Stops all running relations between student and school. The end date of
     * the relation is set to today.
     *
     * @param student
     * @param school
     * @return
     */
    public Person unSubscribeStudent(Person student, Organization school);

    /**
     * Stops all running relations between student and school at a given date.
     *
     * @param student
     * @param school
     * @param end
     * @return
     */
    public Person unSubscribeStudent(Person student, Organization school, Date end);

    /**
     * Registers a new school.
     *
     * @param schoolName
     * @param descriptiveInformation
     * @param address
     * @return
     */
    public Organization registerNewSchool(String schoolName, String descriptiveInformation, Mailbox address);

    /**
     * Registers a new address for a school.
     *
     * @param school
     * @param newAddress
     * @return
     */
    public Organization registerNewAddressForSchool(Organization school, Mailbox newAddress);

    /**
     * Registers a new department for a school.
     *
     * @param department
     * @param address
     * @param forSchool
     * @return
     */
    public Organization registerNewDepartment(String departmentName, String descriptiveInformation, Mailbox address, Organization forSchool);

    /**
     * Registers a new classgroup in a school.
     *
     * @param groupName
     * @param descriptiveInformation
     * @param parent
     * @param details
     * @return
     */
    public Organization registerNewGroup(String groupName, String descriptiveInformation, Organization parent, PartyAttribute... details)
            throws PartyAlreadyExistsException ;

    public void addStudentToGroup(Person student, Organization group);

    public void addStudentToGroup(Person student, Organization group, Date start);

    public Person registerNewTeacher(String givenName, String surName, String middleName, Organization school, Gender gender, PartyAttribute... details)
            throws NoDomainNameFoundException;

    public void addTeacherToGroup(Person teacher, Organization group, boolean isTitular);

    public void addTeacherToGroup(Person teacher, Organization group, boolean isTitular, Date start);

    public Person connectParentToStudent(Organization school, String givenName, String surName, String middleName, Gender gender, Person student, Kind relation)
            throws NoDomainNameFoundException;

    public void connectParentToStudent(Organization school, Person parent, Person student, Kind relation);

    public void connectStudentToParent(Organization school, Person student, Person parent);

    public void connectParentsToStudent(Organization school, Person mother, Person father, Person student);

    public void connectParentsToStudent(Organization school, Person mother, Person father, Person student, Kind parentsRelation);

    public void setParentsRelation(Person mother, Person father, Kind parentsRelation);

}
