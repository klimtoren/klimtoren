/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.encryption.pbe.config;

import be.wolkmaan.klimtoren.security.web.pbeconfig.WebPBEConfigRegistry;
import be.wolkmaan.klimtoren.shared.CommonUtils;

/**
 *
 * @author karl
 */
public class WebPBEConfig extends SimplePBEConfig {
    private String name = null;
    private String validationWord = null;
    
    public WebPBEConfig() {
        super();
        final WebPBEConfigRegistry registry =
                WebPBEConfigRegistry.getInstance();
        registry.registerConfig(this);
    }
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        CommonUtils.validateNotEmpty(name, "Name cannot be set empty");
        this.name = name;
    }
    public String getValidationWord() {
        return this.validationWord;
    }
    public void setValidationWord(final String validation) {
        CommonUtils.validateNotEmpty(validation, "Validation word cannot be set empty");
        this.validationWord = validation;
    }
    public boolean isComplete() {
        return ((CommonUtils.isNotEmpty(this.name)) &&
                (CommonUtils.isNotEmpty(this.validationWord)));
    }
}
