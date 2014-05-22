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
import be.wolkmaan.klimtoren.web.config.PersistenceConfig;
import be.wolkmaan.klimtoren.web.config.RootConfig;
import be.wolkmaan.klimtoren.web.config.WebMvcConfig;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author karl
 */
@Slf4j
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, PersistenceConfig.class, WebMvcConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SchoolServiceImplTest {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private PartyService partyService;
    @Autowired
    private PartyRepository partyRepository;

    @Transactional
    private Mailbox getKlimtorenMailbox() {
        Mailbox mb = new Mailbox();
        mb.setStreet("Kapellestraat 16");
        mb.setZipcode("8490");
        mb.setCity("Jabbeke");
        mb.setStateOrProvince("West-Vlaanderen");
        mb.setCountry(locationRepository.findCountryByCode("BE"));
        return mb;
    }

    private Organization school = null;

    @Transactional
    private Organization getKlimtoren() {
        if (school == null) {
            school = schoolService.registerNewSchool("VBS De Klimtoren", "Lagere school", getKlimtorenMailbox(), "klimtoren.be");
        }
        return school;
    }

    private Organization department = null;

    @Transactional
    private Organization getDepartment() {
        if (department == null) {
            department = schoolService.registerNewDepartment("VBS De Klimtoren", "Kleuterafdeling", getDepartmentMailbox(), getKlimtoren());
        }
        return department;
    }

    @Transactional
    private Mailbox getDepartmentMailbox() {
        Mailbox dmb = new Mailbox();
        dmb.setStreet("Kapellestraat 12");
        dmb.setZipcode("8490");
        dmb.setCity("Jabbeke");
        dmb.setStateOrProvince("West-Vlaanderen");
        dmb.setCountry(locationRepository.findCountryByCode("BE"));
        return dmb;
    }

    private Person student = null;

    @Transactional
    private Person getStudent() {
        String givenName = "Jurre";
        String surName = "Van Iseghem";
        String middleName = "";
        Person.Gender gender = Gender.MALE;
        PartyAttribute[] details = null;
        if (student == null) {
            try {
                student = schoolService.registerNewStudent(givenName, surName, middleName, getKlimtoren(), gender, details);
            } catch (NoDomainNameFoundException ex) {
                Logger.getLogger(SchoolServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return student;
    }

    @Transactional
    private Person getStudent2() {
        String givenName = "Jaan ";
        String surName = "Van Iseghem";
        String middleName = "";
        Person.Gender gender = Gender.FEMALE;
        PartyAttribute[] details = null;
        if (student == null) {
            try {
                student = schoolService.registerNewStudent(givenName, surName, middleName, getKlimtoren(), gender, details);
            } catch (NoDomainNameFoundException ex) {
                Logger.getLogger(SchoolServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return student;
    }

    private Person teacher;

    @Transactional
    private Person getTeacher() {
        String givenName = "Karl";
        String surName = "Van Iseghem";
        String middleName = "Flor";
        Person.Gender gender = Gender.MALE;
        PartyAttribute[] details = null;
        if (teacher == null) {

            try {
                teacher = schoolService.registerNewTeacher(givenName, surName, middleName, getKlimtoren(), gender, details);
            } catch (NoDomainNameFoundException ex) {
                Logger.getLogger(SchoolServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return teacher;
    }

    private Person mother = null;

    @Transactional
    private Person getParent() {
        String givenName = "Ulrike";
        String surName = "Drieskens";
        String middleName = "";
        Person.Gender gender = Gender.FEMALE;
        PartyAttribute[] details = null;
        if (mother == null) {
            try {
                mother = partyService.registerNewUser(getKlimtoren(), givenName, surName, middleName, gender, details);
            } catch (NoDomainNameFoundException ex) {
                Logger.getLogger(SchoolServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mother;
    }

    @Transactional
    private void resetStudent() {
        if (student != null && school != null) {
            List<PartyToPartyRelationship> p2ps = partyRepository.findRelation(student, school);
            p2ps.stream().forEach((p2p) -> {
                p2p.setEnd(null);
            });
        }
    }
    private Organization group = null;

    @Transactional
    private Organization getGroup() {
        if (group == null) {
            PartyAttribute[] details = {
                new PartyAttribute("level", "L4", Kind.STRING)
            };
            try {
                group = schoolService.registerNewGroup("4A", "", getKlimtoren(), details);
            } catch (PartyAlreadyExistsException ex) {
                Logger.getLogger(SchoolServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return group;
    }

    /**
     * Test of registerNewSchool method, of class SchoolServiceImpl.
     */
    @Test
    public void testRegisterNewSchool() {
        System.out.println("registerNewSchool");
        school = getKlimtoren();
        assertNotNull(school.getId());
    }

    /**
     * Test of registerNewStudent method, of class SchoolServiceImpl.
     */
    @Test
    public void testRegisterNewStudent() {
        System.out.println("registerNewStudent");
        student = getStudent();
        assertNotNull(student.getId());
        System.out.println(student.getAttribute("initialPwd"));
    }

    /**
     * Test of unSubscribeStudent method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testUnSubscribeStudent_Person_Organization() {
        System.out.println("unSubscribeStudent");
        student = getStudent();
        resetStudent();
        student = schoolService.unSubscribeStudent(student, school);
        List<PartyToPartyRelationship> result = partyRepository.findRelation(student, school, true);
        result.stream().forEach((p2p) -> {
            assertNotNull(p2p.getEnd());
        });
    }

    /**
     * Test of unSubscribeStudent method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testUnSubscribeStudent_3args() {
        System.out.println("unSubscribeStudent");
        student = getStudent();
        resetStudent();
        Date now = new Date();
        student = schoolService.unSubscribeStudent(student, school, now);
        List<PartyToPartyRelationship> result = partyRepository.findRelation(student, school, true);
        result.stream().forEach((p2p) -> {
            assertTrue(p2p.getEnd().equals(now));
        });
    }

    /**
     * Test of registerNewAddressForSchool method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testRegisterNewAddressForSchool() {
        System.out.println("registerNewAddressForSchool");
        school = getKlimtoren();
        Mailbox na = new Mailbox();
        na.setStreet("Kapellestraat 12");
        na.setZipcode("8490");
        na.setCity("Jabbeke");
        na.setStateOrProvince("West-Vlaanderen");
        na.setCountry(locationRepository.findCountryByCode("BE"));

        Organization result = (Organization) partyService.registerNewAddress(school, na, Kind.WORK, true);
        assertNotNull(result.getLocations());
        assertTrue(((Mailbox) result.getLocations().get(0).getAtLocation()).getStreet().equals("Kapellestraat 12"));
    }

    /**
     * Test of registerNewDepartment method, of class SchoolServiceImpl.
     */
    @Test
    public void testRegisterNewDepartment() {
        System.out.println("registerNewDepartment");
        department = getDepartment();
        assertNotNull(department.getId());
    }

    /**
     * Test of registerNewGroup method, of class SchoolServiceImpl.
     */
    @Test
    public void testRegisterNewGroup() {

        System.out.println("registerNewGroup");
        group = getGroup();
        assertNotNull(group.getId());
        assertTrue(group.getAttribute("level").getValue().equals("L4"));
    }

    /**
     * Test of addStudentToGroup method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testAddStudentToGroup_Person_Organization() {
        System.out.println("addStudentToGroup");
        resetStudent();
        schoolService.addStudentToGroup(this.getStudent(), this.getGroup());
        List<PartyToPartyRelationship> result = partyRepository.findRelation(student, group, true);
        result.stream().forEach((res) -> {
            System.out.println("Relation : " + res.getContext().getDisplayName() + " <->"
                    + res.getReference().getDisplayName() + " [" + res.getKind().name() + "] ");
        });
    }

    /**
     * Test of addStudentToGroup method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testAddStudentToGroup_3args() {
        System.out.println("addStudentToGroup");
        resetStudent();
        schoolService.addStudentToGroup(this.getStudent2(), this.getGroup(), new Date());
        List<PartyToPartyRelationship> result = partyRepository.findRelation(student, group, true);
        result.stream().forEach((res) -> {
            System.out.println("Relation : " + res.getContext().getDisplayName() + " <->"
                    + res.getReference().getDisplayName() + " [" + res.getKind().name() + "] ");
        });
    }

    /**
     * Test of registerNewTeacher method, of class SchoolServiceImpl.
     */
    @Test
    public void testRegisterNewTeacher() {
        System.out.println("registerNewTeacher");

        teacher = getTeacher();
        assertNotNull(teacher.getId());
    }

    /**
     * Test of addTeacherToGroup method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testAddTeacherToGroup_3args() {
        System.out.println("addTeacherToGroup");
        teacher = getTeacher();
        group = getGroup();
        schoolService.addTeacherToGroup(teacher, group, true);
        assertNotNull(partyRepository.findRelation(teacher, group, Kind.TITULAR));
    }

    /**
     * Test of addTeacherToGroup method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testAddTeacherToGroup_4args() {
        System.out.println("addTeacherToGroup");
        teacher = getTeacher();
        group = getGroup();
        schoolService.addTeacherToGroup(teacher, group, false, new Date());
        assertNull(partyRepository.findRelation(teacher, group, Kind.TITULAR));
    }

    /**
     * Test of connectParentToStudent method, of class SchoolServiceImpl.
     *
     * @throws be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException
     */
    @Test
    @Transactional
    public void testConnectParentToStudent_7args() throws NoDomainNameFoundException {
        System.out.println("connectParentToStudent");
        Person father = schoolService.connectParentToStudent(getKlimtoren(), "Karl", "Van Iseghem", "Flor", Gender.MALE, getStudent(), Kind.FATHER);
        assertNotNull(father.getId());

    }

    /**
     * Test of connectParentToStudent method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testConnectParentToStudent_4args() {
        System.out.println("connectParentToStudent");
        mother = getParent();
        assertNotNull(mother.getId());
        schoolService.connectParentToStudent(getKlimtoren(), getParent(), getStudent(), Kind.MOTHER);

        assertNotNull(partyRepository.findRelation(getParent(), getStudent(), Kind.MOTHER));
        assertNotNull(partyRepository.findRelation(getParent(), getKlimtoren(), Kind.PARENT, true));
    }

    /**
     * Test of connectStudentToParent method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testConnectStudentToParent() {
        System.out.println("connectStudentToParent");

        schoolService.connectStudentToParent(getKlimtoren(), getStudent(), getTeacher());
        assertNotNull(partyRepository.findRelation(getTeacher(), getStudent(), Kind.FATHER));
    }

    /**
     * Test of connectParentsToStudent method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testConnectParentsToStudent_4args() {
        System.out.println("connectParentsToStudent");

        schoolService.connectParentsToStudent(getKlimtoren(), getParent(), getTeacher(), getStudent2());

        assertNotNull(partyRepository.findRelation(getParent(), getStudent(), Kind.MOTHER));
        assertNotNull(partyRepository.findRelation(getTeacher(), getStudent(), Kind.FATHER));
        assertNotNull(partyRepository.findRelation(getParent(), getKlimtoren(), Kind.PARENT, true));
    }

    /**
     * Test of connectParentsToStudent method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testConnectParentsToStudent_5args() {
        System.out.println("connectParentsToStudent");
        schoolService.connectParentsToStudent(getKlimtoren(), getParent(), getTeacher(), getStudent2(), Kind.DIVORCED);

        assertNotNull(partyRepository.findRelation(getParent(), getStudent(), Kind.MOTHER));
        assertNotNull(partyRepository.findRelation(getTeacher(), getStudent(), Kind.FATHER));
        assertNotNull(partyRepository.findRelation(getTeacher(), getKlimtoren(), Kind.PARENT, true));
        assertNotNull(partyRepository.findRelation(getParent(), getTeacher(), Kind.DIVORCED));
        assertNull(partyRepository.findRelation(getParent(), getTeacher(), Kind.MARRIED));
    }

    /**
     * Test of registerParentsRelation method, of class SchoolServiceImpl.
     */
    @Test
    @Transactional
    public void testRegisterParentsRelation() {
        System.out.println("registerParentsRelation");
        List<PartyToPartyRelationship> div = partyRepository.findRelation(getParent(), getTeacher(), Kind.MARRIED, true);
        div.stream().forEach((divorce) -> {
            partyService.stopRelation(divorce);
        });
        schoolService.registerParentsRelation(getParent(), getTeacher(), Kind.MARRIED);

        assertNull(partyRepository.findRelation(getParent(), getTeacher(), Kind.DIVORCED));
        assertNotNull(partyRepository.findRelation(getParent(), getTeacher(), Kind.MARRIED));
    }

}
