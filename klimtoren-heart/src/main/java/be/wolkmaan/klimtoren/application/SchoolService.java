/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.application.builder.Account;
import be.wolkmaan.klimtoren.application.builder.Organization;
import be.wolkmaan.klimtoren.application.builder.Person;

/**
 *
 * @author karl
 */
public interface SchoolService extends PartyService {
    public Organization createClassgroup(Organization classGroup);
    public Account createTeacher(Account teacher, Organization forSchool);
    public Account createStudent(Account student, Organization forClassGroup);
    
    public void addTeacherToClassGroup(Person teacher, Organization classGroup, boolean isTitular);
    public void addStudentToClassGroup(Person student, Organization classGroup);
    
    public void unsubscribeStudent(Person student);
}
