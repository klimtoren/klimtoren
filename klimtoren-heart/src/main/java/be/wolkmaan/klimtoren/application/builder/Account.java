/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.application.builder;

import be.wolkmaan.klimtoren.heart.enums.Gender;
import be.wolkmaan.klimtoren.heart.enums.Kind;
import static com.google.common.base.Preconditions.*;
import com.google.common.base.Strings;
import java.time.LocalDateTime;
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
public class Account extends Person {
    private String username;
    private String password;

    private String domain;
    private boolean isEncrypted;
   
    public static class Builder  {
        private Integer id;
        private String givenname;
        private String surname;
        private String middlename;

        private String username;
        private String password;
        private String email;

        private Kind kind = Kind.PERSON;
        private Gender gender;

        private String domain = "klimtoren.be";
        private boolean isEncrypted;

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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            return password(password, false);
        }

        public Builder password(String password, boolean isEncrypted) {
            this.password = password;
            this.isEncrypted = isEncrypted;
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

        public Builder domain(String domain) {
            this.domain = domain;
            return this;
        }

        public Account build() {
            checkNotNull(givenname, "Given name can not be null.");
            checkNotNull(surname, "Surname can not be null.");
            checkNotNull(kind, "Kind can not be null.");
            checkNotNull(gender, "Gender can not be null.");
            if (Strings.isNullOrEmpty(username) && Strings.isNullOrEmpty(domain)) {
                throw new NullPointerException("Username or domain must be set.");
            }
            if (isEncrypted && Strings.isNullOrEmpty(password)) {
                throw new NullPointerException("Password can not be null and ecnrypted.");
            }
            Account acc = new Account(id, givenname, surname, middlename, 
                    username, password, kind, gender);

            acc.setDomain(domain);
            acc.setEncrypted(isEncrypted);
            return acc;
        }
    }

    private Account(Integer id, String givenname, String surname, String middlename, String username,
            String password, Kind kind, Gender gender) {
        super(id, givenname, surname, middlename, kind, gender);
        this.password = password;
        this.username = username;
    }
}
