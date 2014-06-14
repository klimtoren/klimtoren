/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.heart.enums;

import org.jooq.impl.EnumConverter;

/**
 *
 * @author karl
 */
public class GenderConverter extends EnumConverter<String, Gender> {

    public GenderConverter() {
        super(String.class, Gender.class);
    }
    
}
