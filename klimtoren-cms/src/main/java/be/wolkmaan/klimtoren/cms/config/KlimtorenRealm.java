/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.cms.config;

import be.wolkmaan.klimtoren.application.EventService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author karl
 */
public class KlimtorenRealm extends AuthorizingRealm {

//    @Autowired
//    private DSLContext create;
//
//    @Autowired
//    private EventService eventService;

    /*
     When User LOGS IN !
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token, token, getName());
        return info;
//        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//        String provided_un = upToken.getUsername();
//        String provided_pwd = String.valueOf(upToken.getPassword());
//
//        AuthenticationRecord auth = (AuthenticationRecord) create.select()
//                .from(AUTHENTICATION)
//                .where(AUTHENTICATION.USERNAME.lower().equal(provided_un.toLowerCase()))
//                .fetchOne();
//
//        Account account = Mapper.getInstance().map(auth, Account.class);
//
//        if (auth != null) {
//            final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
//            boolean granted = encryptor.checkPassword(provided_pwd, auth.getPassword()) && !auth.getIslocked();
//            if (granted) {
//                auth.setLastloginDatetime(LocalDateTime.now());
//                auth.store();
//
//                Record11<Integer, String, Kind, String, String, Gender, String, String, String, String, Kind> record
//                        = create.select(PARTIES.ID, PARTIES.DISPLAY_NAME, PARTIES.KIND,
//                                AUTHENTICATION.USERNAME, AUTHENTICATION.PASSWORD,
//                                PERSONS.GENDER, FULLNAMES.GIVENNAME, FULLNAMES.SURNAME, FULLNAMES.MIDDLENAME,
//                                FULLNAMES.TITLE, FULLNAMES.KIND)
//                        .from(AUTHENTICATION)
//                        .join(PARTIES)
//                        .on(AUTHENTICATION.ID.equal(PARTIES.ID))
//                        .join(PERSONS)
//                        .on(PERSONS.ID.equal(PARTIES.ID))
//                        .leftOuterJoin(FULLNAMES)
//                        .on(FULLNAMES.FORPARTY.equal(PARTIES.ID))
//                        .fetchOne();
//                account = Mapper.getInstance().map(record, Account.class);
//                //TODO locking features
//
//            } else {
//                throw new AuthenticationException("Wrong credentials");
//            }
//        } else {
//            throw new AuthenticationException(String.format("Username %s not found.", provided_un));
//        }
//
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(account, account.getPassword(), getName());
//        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
     UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        

     Account user = Mapper.getInstance().map(auth, Account.class);

     final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();

     if (auth != null) {
     boolean granted = encryptor.checkPassword(password, auth.getPassword()) && !auth.getIslocked();
     if (granted) {
     auth.setLastloginDatetime(LocalDateTime.now());
     auth.store();
     

     user = Mapper.getInstance().map(acc, Account.class);
     auth.store();
     eventService.onEvent("login", String.format("Successfull login for %s.", username),
     user, Kind.SUCCESS);
     } else {
     eventService.onEvent("login", String.format("Login failed for %s.", username),
     user, Kind.WARNING);
     if (auth.getIslocked()) {
     LocalDateTime prev = auth.getLastloginattemptfailureDatetime();
     LocalDateTime now = LocalDateTime.now();
     Duration between = Duration.between(prev, now);

     if (between.toMinutes() > 30 && auth.getEnableautounlock()) {
     auth.setIslocked(false);
     auth.setLastloginattemptfailureDatetime(null);
     auth.setLoginattemptfailurecount(0);
     auth.store();
     } else {
     String msg = "User locked.";
     if (auth.getEnableautounlock()) {
     msg += " Please wait " + between.toMinutes() + " minutes before trying again.";
     }
     throw new AuthenticationException(msg);
     }
     }
     if (!auth.getIslocked()) {
     auth.setLastloginattemptfailureDatetime(LocalDateTime.now());
     auth.setLoginattemptfailurecount(auth.getLoginattemptfailurecount() + 1);
     if (auth.getLoginattemptfailurecount() == 3) {
     auth.setIslocked(true);
     eventService.onEvent("login",
     String.format("%s was locked. Auto-unlock is set to %s", username, auth.getEnableautounlock()),
     user, Kind.SEVERE);
     }
     auth.store();
     throw new AuthenticationException("Credentials wrong");
     }
     }
     } else {
     throw new AuthenticationException("Username " + username + " not found");
     }

     
     */
}
