/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.Person;
import java.util.Date;

/**
 *
 * @author karl
 */
public interface SchoolService {
    public Person registerNewStudent(String givenName, String surName, String middleName, 
                                        Organization school, PartyAttribute... details)
                                            throws NoDomainNameFoundException;
    public Person unSubscribeStudent(Person student, Organization school);
    public Person unSubscribeStudent(Person student, Organization school, Date end);
    
    public boolean addStudentToGroup(Person student, Organization group);
    public boolean addStudentToGroup(Person student, Organization group, Date start);
    
    public Person registerNewTeacher(String givenName, String surName, String middleName, Organization school, PartyAttribute... details);
    public boolean addTeacherToGroup(Person teacher, Organization group, boolean isTitular);
    public boolean addTeacherToGroup(Person teacher, Organization group, boolean isTitular, Date start);
    
    public boolean connectParentToStudent(String givenName, String surName, String middleName, Person student, Kind relation);
    public boolean connectParentToStudent(Person parent, Person student, Kind relation);
    
    public boolean connectStudentToParent(Person student, Person parent);
    
    public boolean connectParentsToStudent(Person mother, Person father, Person student);
    public boolean connectParentsToStudent(Person mother, Person father, Person student, Kind parentsRelation);
    public boolean setParentsRelation(Person mother, Person father, Kind parentsRelation);
    
    
}
