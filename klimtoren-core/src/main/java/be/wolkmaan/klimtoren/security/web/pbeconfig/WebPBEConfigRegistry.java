/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.web.pbeconfig;

import be.wolkmaan.klimtoren.security.encryption.pbe.config.WebPBEConfig;
import be.wolkmaan.klimtoren.security.exceptions.EncryptionInitializationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author karl
 */
public final class WebPBEConfigRegistry {
    private final Set names = new HashSet();
    private final List configs = new ArrayList();
    private boolean webConfigurationDone = false;
    
    private static final WebPBEConfigRegistry instance = 
        new WebPBEConfigRegistry();
    
    
    public static WebPBEConfigRegistry getInstance() {
        return instance;
    }
    
    private WebPBEConfigRegistry() {
        super();
    }
    
    
    public synchronized void registerConfig(final WebPBEConfig config) {
        if (this.webConfigurationDone) {
            throw new EncryptionInitializationException(
                    "Cannot register: Web configuration is already done");
        }
        // Avoid duplication of encryptors because of the initialization
        // class being called more than once.
        if (!this.names.contains(config.getName())) {
            this.configs.add(config);
            this.names.add(config);
        }
    }
    
    public synchronized List getConfigs() {
        return Collections.unmodifiableList(this.configs);
    }

    public boolean isWebConfigurationDone() {
        return (this.webConfigurationDone || (this.configs.size() == 0));
    }

    public void setWebConfigurationDone(final boolean configurationDone) {
        this.webConfigurationDone = configurationDone;
    }
    
}
