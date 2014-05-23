/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.sample;

import be.wolkmaan.klimtoren.application.PartyService;
import be.wolkmaan.klimtoren.application.SchoolService;
import be.wolkmaan.klimtoren.exceptions.NoDomainNameFoundException;
import be.wolkmaan.klimtoren.exceptions.PartyAlreadyExistsException;
import be.wolkmaan.klimtoren.kind.Kind;
import be.wolkmaan.klimtoren.location.Country;
import be.wolkmaan.klimtoren.location.Email;
import be.wolkmaan.klimtoren.location.LocationRepository;
import be.wolkmaan.klimtoren.location.Mailbox;
import be.wolkmaan.klimtoren.location.Telephone;
import be.wolkmaan.klimtoren.party.Organization;
import be.wolkmaan.klimtoren.party.PartyAttribute;
import be.wolkmaan.klimtoren.party.Person;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author karl
 */
@Slf4j
@Data
@Component
public class SampleData {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private LocationRepository locationRepository;

    /* SCHOOL STRUCTURE */
    private Organization lager;
    private Organization kleuter;
    private Organization klimtoren;
    private Organization k1A;
    private Organization k1B;
    private Organization k1C;
    private Organization k1D;
    private Organization k2A;
    private Organization k2B;
    private Organization k3A;
    private Organization k3B;
    private Organization l1A;
    private Organization l1B;
    private Organization l1C;
    private Organization l2A;
    private Organization l2B;
    private Organization l3A;
    private Organization l3B;
    private Organization l4A;
    private Organization l4B;
    private Organization l5A;
    private Organization l5B;
    private Organization l6A;
    private Organization l6B;

    /* TEACHERS */
    private Person evaC;
    private Person carlaD;
    private Person veerleG;
    private Person deborahD;
    private Person sofieW;
    private Person pascaleM;
    private Person liesA;
    private Person marijkeC;

    private Person evelineC;
    private Person wimA;
    private Person fraukeT;
    private Person elsD;
    private Person dorineD;
    private Person anneliesK;
    private Person veerleP;
    private Person kelly;
    private Person annickV;
    private Person evaT;
    private Person veerleV;
    private Person ingridV;
    private Person peterD;
    private Person sophieH;

    /* TEAM */
    private Person rebekkaB;
    private Person annH;
    private Person anB;

    /* CHILDREN */
    private Person jurre;
    private Person jaan;

    /* PARENTS */
    private Person karl;
    private Person ulrike;

    private boolean init = false;

    private Country belgium;
    private Mailbox klimtorenMB;
    private Mailbox kleuterMB;
    private Telephone klimtorenTelephone;
    private Email klimtorenEmail;

    public void init() {
        if (!init) {
            klimtoren = schoolService.registerNewSchool("VBS De Klimtoren", "", getKlimtorenMailbox(), "klimtoren.be");
            partyService.addPartyLocation(getKlimtorenTelephone(), klimtoren, Kind.WORK, true, true);
            partyService.addPartyLocation(getKlimtorenEmail(), klimtoren, Kind.WORK, true, true);

            kleuter = schoolService.registerNewDepartment("Kleuterafdeling", "", getKleuterMailbox(), klimtoren);
            lager = schoolService.registerNewDepartment("Lagere afdeling", "", getKlimtorenMailbox(), klimtoren);

            try {
                k1A = schoolService.registerNewGroup("1KA", "Eerste kleuter A", klimtoren, new PartyAttribute("level", "K1", Kind.STRING));
                k1B = schoolService.registerNewGroup("1KB", "Eerste kleuter B", klimtoren, new PartyAttribute("level", "K1", Kind.STRING));
                k1C = schoolService.registerNewGroup("1KC", "Eerste kleuter C", klimtoren, new PartyAttribute("level", "K1", Kind.STRING));
                k1D = schoolService.registerNewGroup("1KD", "Eerste kleuter D", klimtoren, new PartyAttribute("level", "K1", Kind.STRING));
                k2A = schoolService.registerNewGroup("2KA", "Tweede kleuter A", klimtoren, new PartyAttribute("level", "K2", Kind.STRING));
                k2B = schoolService.registerNewGroup("2KB", "Tweede kleuter B", klimtoren, new PartyAttribute("level", "K2", Kind.STRING));
                k3A = schoolService.registerNewGroup("3KA", "Derde kleuter A", klimtoren, new PartyAttribute("level", "K3", Kind.STRING));
                k3B = schoolService.registerNewGroup("3KB", "Derde kleuter B", klimtoren, new PartyAttribute("level", "K3", Kind.STRING));

                l1A = schoolService.registerNewGroup("1A", "Eerste leerjaar A", klimtoren, new PartyAttribute("level", "L1", Kind.STRING));
                l1B = schoolService.registerNewGroup("1B", "Eerste leerjaar B", klimtoren, new PartyAttribute("level", "L1", Kind.STRING));
                l1C = schoolService.registerNewGroup("1C", "Eerste leerjaar C", klimtoren, new PartyAttribute("level", "L1", Kind.STRING));
                l2A = schoolService.registerNewGroup("2A", "Tweede leerjaar A", klimtoren, new PartyAttribute("level", "L2", Kind.STRING));
                l2B = schoolService.registerNewGroup("2B", "Tweede leerjaar B", klimtoren, new PartyAttribute("level", "L2", Kind.STRING));
                l3A = schoolService.registerNewGroup("3A", "Derde leerjaar A", klimtoren, new PartyAttribute("level", "L3", Kind.STRING));
                l3B = schoolService.registerNewGroup("3B", "Derde leerjaar B", klimtoren, new PartyAttribute("level", "L3", Kind.STRING));
                l4A = schoolService.registerNewGroup("4A", "Vierde leerjaar A", klimtoren, new PartyAttribute("level", "L4", Kind.STRING));
                l4B = schoolService.registerNewGroup("4B", "Vierde leerjaar B", klimtoren, new PartyAttribute("level", "L4", Kind.STRING));
                l5A = schoolService.registerNewGroup("5A", "Vijfde leerjaar A", klimtoren, new PartyAttribute("level", "L5", Kind.STRING));
                l5B = schoolService.registerNewGroup("5B", "Vijfde leerjaar B", klimtoren, new PartyAttribute("level", "L5", Kind.STRING));
                l6A = schoolService.registerNewGroup("6A", "Zesde leerjaar A", klimtoren, new PartyAttribute("level", "L6", Kind.STRING));
                l6B = schoolService.registerNewGroup("6B", "Zesde leerjaar B", klimtoren, new PartyAttribute("level", "L6", Kind.STRING));

            } catch (PartyAlreadyExistsException ex) {
                log.error(ex.getMessage());
            }
            try {
                evaC = schoolService.registerNewTeacher("Eva", "Claeys", null, klimtoren, Person.Gender.FEMALE);
                carlaD = schoolService.registerNewTeacher("Carla", "Depré", null, klimtoren, Person.Gender.FEMALE);
                veerleG = schoolService.registerNewTeacher("Veerle", "Ghyssaert", null, klimtoren, Person.Gender.FEMALE);
                deborahD = schoolService.registerNewTeacher("Deborah", "Deschacht", null, klimtoren, Person.Gender.FEMALE);
                sofieW = schoolService.registerNewTeacher("Sofie", "Willaert", null, klimtoren, Person.Gender.FEMALE);
                pascaleM = schoolService.registerNewTeacher("Pascale", "Muylle", null, klimtoren, Person.Gender.FEMALE);
                liesA = schoolService.registerNewTeacher("Lies", "Accou", null, klimtoren, Person.Gender.FEMALE);
                marijkeC = schoolService.registerNewTeacher("Marijke", "Claeys", null, klimtoren, Person.Gender.FEMALE);

                evelineC = schoolService.registerNewTeacher("Eveline", "Coussens", null, klimtoren, Person.Gender.FEMALE);
                wimA = schoolService.registerNewTeacher("Wim", "Ameloot", null, klimtoren, Person.Gender.MALE);
                fraukeT = schoolService.registerNewTeacher("Frauke", "Taecke", null, klimtoren, Person.Gender.FEMALE);
                elsD = schoolService.registerNewTeacher("Els", "De Graeve", null, klimtoren, Person.Gender.FEMALE);
                dorineD = schoolService.registerNewTeacher("Dorine", "Dedeyne", null, klimtoren, Person.Gender.FEMALE);
                anneliesK = schoolService.registerNewTeacher("Annelies", "Keirsebilk", null, klimtoren, Person.Gender.FEMALE);
                veerleP = schoolService.registerNewTeacher("Veerle", "Poppe", null, klimtoren, Person.Gender.FEMALE);
                kelly = schoolService.registerNewTeacher("Kelly", "Nog iets", null, klimtoren, Person.Gender.FEMALE);
                veerleV = schoolService.registerNewTeacher("Veerle", "Vanacker", null, klimtoren, Person.Gender.FEMALE);
                ingridV = schoolService.registerNewTeacher("Ingrid", "van der Biest", null, klimtoren, Person.Gender.FEMALE);
                peterD = schoolService.registerNewTeacher("Peter", "De Craemer", null, klimtoren, Person.Gender.FEMALE);
                sophieH = schoolService.registerNewTeacher("Sophie", "Haelemeersch", null, klimtoren, Person.Gender.FEMALE);

            } catch (NoDomainNameFoundException ex) {
                log.error(ex.getMessage());
            }
            try {

                jurre = schoolService.registerNewStudent("Jurre", "Van Iseghem", null, klimtoren, Person.Gender.MALE);
                jaan = schoolService.registerNewStudent("Jaan", "Van Iseghem", null, klimtoren, Person.Gender.FEMALE);
            } catch (NoDomainNameFoundException ex) {
                log.error(ex.getMessage());
            }
            schoolService.addStudentToGroup(getJurre(), getK1A());
            schoolService.addStudentToGroup(getJaan(), getK1A());
            this.init = true;
        }
    }

    /* GETTERS */
    @Transactional
    public Country getBelgium() {
        if (belgium == null) {
            belgium = locationRepository.findCountryByCode("BE");
        }
        return belgium;
    }

    public Mailbox getKlimtorenMailbox() {
        if (klimtorenMB == null) {
            klimtorenMB = new Mailbox();
            klimtorenMB.setStreet("Kapellestraat 16");
            klimtorenMB.setZipcode("8490");
            klimtorenMB.setCity("Jabbeke");
            klimtorenMB.setCountry(getBelgium());
        }
        return klimtorenMB;
    }

    public Mailbox getKleuterMailbox() {
        if (kleuterMB == null) {
            kleuterMB = new Mailbox();
            kleuterMB.setStreet("Kapellestraat 12");
            kleuterMB.setZipcode("8490");
            kleuterMB.setCity("Jabbeke");
            kleuterMB.setCountry(getBelgium());
        }
        return kleuterMB;
    }

    public Email getKlimtorenEmail() {
        if (klimtorenEmail == null) {
            klimtorenEmail = new Email();
            klimtorenEmail.setUrl("info@klimtoren.be");
        }
        return klimtorenEmail;
    }

    public Telephone getKlimtorenTelephone() {
        if (klimtorenTelephone == null) {
            klimtorenTelephone = new Telephone();
            klimtorenTelephone.setNumber("050 81 27 14");
        }
        return klimtorenTelephone;
    }
}
