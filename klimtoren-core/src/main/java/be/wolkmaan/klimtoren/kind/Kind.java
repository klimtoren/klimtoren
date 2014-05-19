/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.kind;

/**
 *
 * @author karl
 */
public enum Kind {
    //HUMAN
    PERSON,
    
    //FAMILY & RELATIVES
    WIFE,
    HUSBAND,
    FATHER,
    MOTHER,
    SON,
    DAUGHTER,
    PARENT,
    
    //ORANIZATIONS
    COMPANY,
    ORGANIZATION,
    EMPLOYEE,
    INTERIM,
    DEPARTMENT,
    
    //SCHOOL SPECIFIC
    SCHOOL,
    PRINCIPAL_SCHOOL,
    CLASSGROUP,        
    DIRECTOR,
    TEACHER,
    TITULAR,
    STUDENT,
    
    //RESOURCES
    COMPUTER,
    PC,
    MAC,
    LINUX,
    SWITCH,
    ROUTER,
    AIRPORT,
    AIRPORT_EXPRESS,
    BEAMER,
    TV,
    DIGIBOARD,
    
    //DATATYPES
    STRING,
    ENCRYPTED_TEXT, 
    
    //CONTACT TYPES
    HOME,
    WORK,
    MOBILE,
    GENERAL,
    FAX_WORK,
    FAX_PRIVATE,
    CUSTOM;
}
