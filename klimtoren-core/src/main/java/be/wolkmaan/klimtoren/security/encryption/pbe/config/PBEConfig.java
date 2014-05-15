/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.encryption.pbe.config;

import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import java.security.Provider;

/**
 *
 * @author karl
 */
public interface PBEConfig {
    public String getAlgorithm();
    public String getPassword();
    public Integer getKeyObtentionIterations();
    public SaltGenerator getSaltGenerator();
    public String getProviderName();
    public Provider getProvider();
    
    
}
