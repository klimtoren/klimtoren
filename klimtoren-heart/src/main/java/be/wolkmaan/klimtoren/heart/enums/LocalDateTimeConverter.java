/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.heart.enums;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.jooq.Converter;

/**
 *
 * @author karl
 */
public class LocalDateTimeConverter implements Converter<Timestamp, LocalDateTime> {

    @Override
    public LocalDateTime from(Timestamp databaseObject) {
        return databaseObject.toLocalDateTime();
    }

    @Override
    public Timestamp to(LocalDateTime dt) {
        return Timestamp.valueOf(dt);
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDateTime> toType() {
        return LocalDateTime.class;
    }
}
