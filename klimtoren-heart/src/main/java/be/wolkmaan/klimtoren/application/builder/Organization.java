/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application.builder;

import be.wolkmaan.klimtoren.heart.enums.Kind;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Organization extends Party {
    
    public static class Builder {
        private Integer id;
        private String displayName;
        private String descriptiveInformation;
        private Kind kind = Kind.ORGANIZATION;
        
        public Builder displayName(String displayname) {
            this.displayName = displayname;
            return this;
        }
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }
        public Builder descriptiveInformation(String descriptiveInformation) {
            this.descriptiveInformation = descriptiveInformation;
            return this;
        }
        public Builder kind(Kind kind) {
            this.kind = kind;
            return this;
        }
        
        public Organization build() {
            return new Organization(id, displayName, descriptiveInformation, kind);
        }
    }
    private Organization(Integer id, String displayname, String descriptiveInformation, Kind kind) {
        super(id, displayname, descriptiveInformation, kind);
    }
}
