/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.application.builder;

import be.wolkmaan.klimtoren.heart.enums.Kind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Party {
    protected Integer id;
    protected String displayName;
    protected String descriptiveInformation;
    protected Kind kind;
   
    public static class Builder {
        private Integer id;
        private String displayName;
        private String descriptiveInformation;
        private Kind kind;
        

        public Builder() {

        }
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }
        public Builder displayName(String displayName) {
            this.displayName = displayName;
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

        public Party build() {
            Party party = new Party(id, displayName, descriptiveInformation, kind);
            return party;
        }
    }

   
}
