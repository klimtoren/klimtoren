/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.digest.config;

import be.wolkmaan.klimtoren.security.salt.SaltGenerator;
import java.security.Provider;

/**
 *
 * @author karl
 */
public interface DigesterConfig {
    public String getAlgorithm();
    public Integer getSaltSizeBytes();
    public Integer getIterations();
    public SaltGenerator getSaltGenerator();
    public String getProviderName();
    public Provider getProvider();
    public Boolean getInvertPositionOfSaltInMessageBeforeDigesting();
    public Boolean getInvertPositionOfPlainSaltInEncryptionResults();
    public Boolean getUseLenientSaltSizeCheck();
    public Integer getPoolSize();
}
