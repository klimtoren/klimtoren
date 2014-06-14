/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application.builder;

import be.wolkmaan.klimtoren.heart.enums.Kind;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.Record;

/**
 *
 * @author karl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long id;
    private LocalDateTime occuredDatetime;
    private String title;
    private String message;
    private Party forParty;
    private Resource forResource;
    private Kind kind;

    public static class Builder {
        
        private Long id;
        private LocalDateTime occuredDatetime;
        private String title;
        private String message;
        private Party forParty;
        private Resource forResource;
        private Kind kind = Kind.INFO;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder occuredDatetime(LocalDateTime occuredDatetime) {
            this.occuredDatetime = occuredDatetime;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder forParty(Party forParty) {
            this.forParty = forParty;
            return this;
        }

        
        public Builder forResource(Resource forResource) {
            this.forResource = forResource;
            return this;
        }

        public Builder kind(Kind kind) {
            this.kind = kind;
            return this;
        }

        public Event build() {
            return new Event(id, occuredDatetime, title, message, forParty, forResource, kind);
        }
    }

    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(occuredDatetime);
        sb.append(" -> ");
        sb.append(title);
        sb.append("\n");
        sb.append(kind.name());
        sb.append(": ");
        sb.append(message);
        if (forParty != null) {
            sb.append(" [Party: ");
            sb.append(forParty.getDisplayName());
            sb.append("]");
        }
        if (forResource != null) {
            sb.append(" [Resource: ");
            sb.append(forResource.getDisplayName());
            sb.append("]");
        }
        
        return sb.toString();
    }

    public static Event map(Record record) {
        //TODO: check mandatory fields
        //TODO: same function on party and resource
        Event.Builder builder = new Event.Builder();

        Lists.newArrayList(record.fields())
                .stream()
                .forEach((field) -> {
                    try {
                        if("party.displayname".equals(field.getName())) {
                            System.out.println("ok");
                        } 
                       
                        else {
                        String methodName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field.getName());
                        builder.getClass().getMethod(methodName, field.getType())
                                .invoke(builder, record.getValue(field.getName(), field.getType()));
                        }
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        return builder.build();
    }
}
