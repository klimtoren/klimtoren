/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.client;

import be.wolkmaan.klimtoren.application.EventService;
import be.wolkmaan.klimtoren.application.PartyService;
import be.wolkmaan.klimtoren.application.builder.Event;
import be.wolkmaan.klimtoren.application.builder.Organization;
import be.wolkmaan.klimtoren.application.builder.Party;
import be.wolkmaan.klimtoren.application.builder.Person;
import be.wolkmaan.klimtoren.heart.enums.Kind;
import be.wolkmaan.klimtoren.mapper.Mapper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author karl
 */
@Service
public class Run {

    @Autowired
    private PartyService partyService;
    @Autowired
    private EventService eventService;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
        Run run = (Run) context.getBean(Run.class);
        run.mapTest();
    }

    private void mapTest() {
        LocalDateTime n1 = LocalDateTime.now();
//        partyService.getOrganizations()
//                .forEach(System.out::println);
//        
        List<Event> list = eventService.getEventsByKind(Kind.WARNING, n1, 20)
                        .collect(Collectors.toList());
        LocalDateTime lpeek = list.get(list.size() - 1).getOccuredDatetime();
        
//        Organization school = partyService.getOrganizationById(1);
//        
//        partyService.getParents(school)
//                .forEach(acc -> System.out.println(acc.getGivenname()));
        
        LocalDateTime n2 = LocalDateTime.now();
        list = eventService.getEventsByKind(Kind.WARNING, lpeek, 10)
                .collect(Collectors.toList());
        
        LocalDateTime n3 = LocalDateTime.now();
        
        Person karl = partyService.getPersonById(2);
        list = eventService.getEventsForParty(karl)
                .collect(Collectors.toList());
        
        LocalDateTime n4 = LocalDateTime.now();
        
        System.out.println((Duration.between(n1, n2).getNano() / 1000000) + " ms");
        System.out.println((Duration.between(n2, n3).getNano() / 1000000) + " ms");
        System.out.println((Duration.between(n3, n4).getNano() / 1000000) + " ms");
    }

   

    @SuppressWarnings("empty-statement")
    private void test() {
        LocalDateTime n1 = LocalDateTime.now();

//        Organization school = partyService.getOrganizationById(1);
//        Person karl = partyService.getPersonById(2);
//        Person ulrike = partyService.getPersonById(3);
//        System.out.println("LEERKRACHTEN: ");
//        partyService.getEmployees(school)
//                .forEach(System.out::println);
//        System.out.println("OUDERS: ");
//        partyService.getParents(school)
//                .forEach(System.out::println);
//        
//        System.out.println("PERSONS: ");
//        partyService.getPersons(school)
//                .forEach(System.out::println);
//        
//        System.out.println("ACCOUNTS: ");
//        partyService.getAccounts(school)
//                .forEach(System.out::println);
//        
//        System.out.println("LOGIN 0: " + partyService.login("karl.vaniseghem@klimtoren.be", "password"));
//        System.out.println("LOGIN 1: " + partyService.login("karl.vaniseghem@klimtoren.be", "password1"));
//        System.out.println("LOGIN 2: " + partyService.login("karl.vaniseghem@klimtoren.be", "password2"));
//        System.out.println("LOGIN 3: " + partyService.login("karl.vaniseghem@klimtoren.be", "password3"));
//        System.out.println("LOGIN 4: " + partyService.login("karl.vaniseghem@klimtoren.be", "password"));
        LocalDateTime n2 = LocalDateTime.now();
        System.out.println((Duration.between(n1, n2).getNano() / 1000000) + " ms");

        final int limit = 10;

//       eventService
//                .getEventsByKind(Kind.WARNING, LocalDateTime.now(), limit)
//                .forEach((ev)-> {
//                    System.out.println(modelMapper.map(ev, Event.class));
//                });
//        int i = 0;
//        boolean flag = true;
//        do {
//            if(list.size() < 10) 
//                flag = false;
//            System.out.println((++i) + " --------------------------- ");
//            list.forEach((e)->System.out.println(e.getTitle()));
//
//            LocalDateTime last = list.get(list.size() - 1).getOccuredDatetime();
//
//            list = eventService
//                    .getEventsByKind(Kind.WARNING, last, limit)
//                    .collect(Collectors.toList());
//        } while (flag);
        LocalDateTime n3 = LocalDateTime.now();
        System.out.println((Duration.between(n2, n3).getNano() / 1000000) + " ms");

    }
}
