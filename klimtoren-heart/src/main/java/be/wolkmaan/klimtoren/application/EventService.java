/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application;

import be.wolkmaan.klimtoren.application.builder.Event;
import be.wolkmaan.klimtoren.application.builder.Party;
import be.wolkmaan.klimtoren.application.builder.Resource;
import be.wolkmaan.klimtoren.heart.enums.Kind;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 *
 * @author karl
 */
public interface EventService {

    /* ++++++++++++++++++++++++++++++++++++++++
     + REGISTER EVENTS 
     +++++++++++++++++++++++++++++++++++++++ */
    public void onEvent(String title, String message);

    public void onEvent(String title, String message, Kind kind);

    public void onEvent(String title, String message, Party forParty);

    public void onEvent(String title, String message, Resource forResource);

    public void onEvent(String title, String message, Party forParty, Resource forResource);

    public void onEvent(String title, String message, Party forParty, Kind kind);

    public void onEvent(String title, String message, Resource forResource, Kind kind);

    public void onEvent(String title, String message, Party forParty, Resource forResource, Kind kind);

    /* ++++++++++++++++++++++++++++++++++++++++
     + SELECT EVENTS 
     +++++++++++++++++++++++++++++++++++++++ */
    public Stream<Event> getEventsByKind(Kind kind);

    public Stream<Event> getEventsForParty(Party forParty);

    public Stream<Event> getEventsByKind(Kind kind, Party forParty);

    public Stream<Event> getEventsForResource(Resource forResource);

    public Stream<Event> getEventsByKind(Kind kind, Resource forResource);

    public Stream<Event> getEventsByKind(Kind kind, LocalDateTime seekAfter, Integer limit);

    public Stream<Event> getEventsForParty(Party forParty, LocalDateTime seekAfter, Integer limit);

    public Stream<Event> getEventsByKind(Kind kind, Party forParty, LocalDateTime seekAfter);

    public Stream<Event> getEventsForResource(Resource forResource, LocalDateTime seekAfter);

    public Stream<Event> getEventsByKind(Kind kind, Resource forResource, LocalDateTime seekAfter);
}
