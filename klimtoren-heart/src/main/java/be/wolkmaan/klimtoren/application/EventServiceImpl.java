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
import static be.wolkmaan.klimtoren.heart.model.tables.Eventlog.EVENTLOG;
import static be.wolkmaan.klimtoren.heart.model.tables.Parties.PARTIES;
import static be.wolkmaan.klimtoren.heart.model.tables.Resources.RESOURCES;
import be.wolkmaan.klimtoren.heart.model.tables.records.EventlogRecord;
import be.wolkmaan.klimtoren.mapper.Mapper;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.jooq.DSLContext;
import org.jooq.Record9;
import org.jooq.SelectJoinStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author karl
 */
@Service("eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    DSLContext create;

    /* ++++++++++++++++++++++++++++++++++++++++
     + REGISTER EVENTS 
     +++++++++++++++++++++++++++++++++++++++ */
    @Override
    public void onEvent(String title, String message) {
        onEvent(title, message, Kind.INFO);
    }

    @Override
    public void onEvent(String title, String message, Kind kind) {
        baseEvent(title, message, kind)
                .store();
    }

    @Override
    public void onEvent(String title, String message, Party forParty) {
        onEvent(title, message, forParty, Kind.INFO);
    }

    @Override
    public void onEvent(String title, String message, Resource forResource) {
        onEvent(title, message, forResource, Kind.INFO);
    }

    @Override
    public void onEvent(String title, String message, Party forParty, Resource forResource) {
        onEvent(title, message, forParty, forResource, Kind.INFO);
    }

    @Override
    public void onEvent(String title, String message, Party forParty, Kind kind) {
        baseEvent(title, message, kind)
                .setForparty(forParty.getId())
                .store();
    }

    @Override
    public void onEvent(String title, String message, Resource forResource, Kind kind) {
        baseEvent(title, message, kind)
                .setForresource(forResource.getId())
                .store();
    }

    @Override
    public void onEvent(String title, String message, Party forParty, Resource forResource, Kind kind) {
        baseEvent(title, message, kind)
                .setForparty(forParty.getId())
                .setForresource(forResource.getId())
                .store();
    }

    /* ++++++++++++++++++++++++++++++++++++++++
     + BASE EVENT LOG RECORD 
     +++++++++++++++++++++++++++++++++++++++ */
    private EventlogRecord baseEvent(String title, String message, Kind kind) {
        return create.newRecord(EVENTLOG)
                .setTitle(title)
                .setMessage(message)
                .setKind(kind)
                .setOccuredDatetime(LocalDateTime.now());
    }
    /* ++++++++++++++++++++++++++++++++++++++++
     + SELECT OPERATIONS 
     +++++++++++++++++++++++++++++++++++++++ */

    @Override
    public Stream<Event> getEventsByKind(Kind kind) {
        return selectEvent()
                .where(EVENTLOG.KIND.equal(kind))
                .orderBy(EVENTLOG.OCCURED_DATETIME.desc())
                .fetch()
                .stream()
                .map(e -> Mapper.getInstance().map(e, Event.class));
    }

    @Override
    public Stream<Event> getEventsForParty(Party forParty) {
        return selectEvent()
                .where(EVENTLOG.FORPARTY.equal(forParty.getId()))
                .fetch()
                .stream()
                .map(e -> Mapper.getInstance().map(e, Event.class));
    }

    @Override
    public Stream<Event> getEventsByKind(Kind kind, Party forParty) {
        return selectEvent()
                .where(EVENTLOG.KIND.equal(kind))
                .and(EVENTLOG.FORPARTY.equal(forParty.getId()))
                .orderBy(EVENTLOG.OCCURED_DATETIME.desc())
                .fetch()
                .stream()
                .map(e -> Mapper.getInstance().map(e, Event.class));
    }

    @Override
    public Stream<Event> getEventsForResource(Resource forResource) {
        return selectEvent()
                .where(EVENTLOG.FORRESOURCE.equal(forResource.getId()))
                .orderBy(EVENTLOG.OCCURED_DATETIME.desc())
                .fetch()
                .stream()
                .map(e -> Mapper.getInstance().map(e, Event.class));
    }

    @Override
    public Stream<Event> getEventsByKind(Kind kind, Resource forResource) {
        return selectEvent()
                .where(EVENTLOG.KIND.equal(kind))
                .and(EVENTLOG.FORRESOURCE.equal(forResource.getId()))
                .orderBy(EVENTLOG.OCCURED_DATETIME.desc())
                .fetch()
                .stream()
                .map(e -> Mapper.getInstance().map(e, Event.class));
    }

    @Override
    public Stream<Event> 
            getEventsByKind(Kind kind, LocalDateTime seekAfter, Integer limit) {
        return selectEvent()
                .where(EVENTLOG.KIND.equal(kind))
                .orderBy(EVENTLOG.OCCURED_DATETIME.desc())
                .seek(seekAfter)
                .limit(limit)
                .fetch()
                .stream()
                .map(e -> Mapper.getInstance().map(e, Event.class));
    }

    

    @Override
    public Stream<Event> getEventsForParty(Party forParty, LocalDateTime seekAfter, Integer limit) {
        return selectEvent()
                .where(EVENTLOG.FORPARTY.equal(forParty.getId()))
                .orderBy(EVENTLOG.OCCURED_DATETIME.desc())
                .seek(seekAfter)
                .limit(limit)
                .fetch()
                .into(EventlogRecord.class)
                .stream()
                .map(e -> Mapper.getInstance().map(e, Event.class));
    }

    @Override
    public Stream<Event> getEventsByKind(Kind kind, Party forParty, LocalDateTime seekAfter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<Event> getEventsForResource(Resource forResource, LocalDateTime seekAfter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<Event> getEventsByKind(Kind kind, Resource forResource, LocalDateTime seekAfter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /* ++++++++++++++++++++++++++++++++++++++++
     + PRIVATE HELPERS 
     +++++++++++++++++++++++++++++++++++++++ */
    private SelectJoinStep<Record9<Long, Kind, String, LocalDateTime, String, Integer, String, Integer, String>> selectEvent() {
        return create.select(
                EVENTLOG.ID, EVENTLOG.KIND, EVENTLOG.MESSAGE,
                EVENTLOG.OCCURED_DATETIME, EVENTLOG.TITLE,
                PARTIES.ID.as("forParty.id"), PARTIES.DISPLAY_NAME.as("forParty.display_name"),
                RESOURCES.ID.as("forResource.id"), RESOURCES.DISPLAYNAME.as("forResource.display_name"))
                .from(EVENTLOG).leftOuterJoin(PARTIES)
                .on(PARTIES.ID.equal(EVENTLOG.FORPARTY))
                .leftOuterJoin(RESOURCES)
                .on(RESOURCES.ID.equal(EVENTLOG.FORRESOURCE));
    }
}
