/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.controller;

import be.wolkmaan.klimtoren.application.PartyService;
import be.wolkmaan.klimtoren.application.builder.Organization;
import be.wolkmaan.klimtoren.application.builder.Person;
import be.wolkmaan.klimtoren.mapper.TableResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author karl
 */
@Controller
public class ManageController {
    @Autowired
    public PartyService partyService;
    
    @RequestMapping("/api/persons")
    @ResponseBody
    public TableResponse<List<Person>> getPersons() {
        Organization school = partyService.getOrganizationById(1);
        
        final List<Person> persons = partyService.getPersons(school).collect(Collectors.toList());
        
        return new TableResponse.Builder<>()
                .data(persons)
                .recordsFiltered(persons.size())
                .recordsTotal(persons.size())
                .build();
    }
}
