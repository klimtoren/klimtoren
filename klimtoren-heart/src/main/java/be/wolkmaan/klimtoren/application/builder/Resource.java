/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application.builder;

import be.wolkmaan.klimtoren.heart.enums.Kind;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Data
@NoArgsConstructor
public class Resource {

    private Integer id;
    private Party owner;
    private Resource parent;
    private LocalDateTime start;
    private LocalDateTime end;
    private Kind kind;
    private String displayName;

    public static class Builder {

        private Integer id;
        private Party owner;
        private Resource parent;
        private LocalDateTime start;
        private LocalDateTime end;
        private Kind kind;
        private String displayName;
        
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }
        public Builder owner(Party owner) {
            this.owner = owner;
            return this;
        }
        public Builder parent(Resource parent) {
            this.parent = parent;
            return this;
        }
        public Builder start(LocalDateTime start) {
            this.start = start;
            return this;
        }
        public Builder end(LocalDateTime end) {
            this.end = end;
            return this;
        }
        public Builder kind(Kind kind) {
            this.kind = kind;
            return this;
        }
        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }
        public Resource build() {
            return new Resource(id, owner, parent, start, end, kind, displayName);
        }
    }

    private Resource(Integer id, Party owner, Resource parent, LocalDateTime start, LocalDateTime end, Kind kind, String displayName) {
        this.id = id;
        this.owner = owner;
        this.parent = parent;
        this.start = start;
        this.end = end;
        this.kind = kind;
        this.displayName = displayName;
    }
    
}
