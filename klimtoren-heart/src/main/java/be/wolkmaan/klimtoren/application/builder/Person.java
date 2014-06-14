/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application.builder;

import be.wolkmaan.klimtoren.heart.enums.Gender;
import be.wolkmaan.klimtoren.heart.enums.Kind;
import static com.google.common.base.Preconditions.checkNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends Party {

    protected String givenname;
    protected String surname;
    protected String middlename;
    protected Gender gender;

    public static class Builder {

        protected Integer id;
        protected String givenname;
        protected String surname;
        protected String middlename;
        protected Kind kind = Kind.PERSON;
        protected Gender gender;

        public Builder() {

        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder givenname(String givenname) {
            this.givenname = givenname;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder middlename(String middlename) {
            this.middlename = middlename;
            return this;
        }

        public Builder kind(Kind kind) {
            this.kind = kind;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Person build() {
            checkNotNull(givenname, "Given name can not be null.");
            checkNotNull(surname, "Surname can not be null.");
            checkNotNull(kind, "Kind can not be null.");
            checkNotNull(gender, "Gender can not be null.");

            Person person = new Person(id, givenname, surname, middlename, kind, gender);
            return person;
        }
    }

    protected Person(Integer id, String givenname, String surname, String middlename, Kind kind, Gender gender) {
        super(id, String.format("%s %s", givenname, surname), null, kind);
        this.givenname = givenname;
        this.surname = surname;
        this.middlename = middlename;
        this.gender = gender;
    }

}
