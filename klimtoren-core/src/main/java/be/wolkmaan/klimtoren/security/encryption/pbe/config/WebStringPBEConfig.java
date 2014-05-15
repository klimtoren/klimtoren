/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.security.encryption.pbe.config;

import be.wolkmaan.klimtoren.shared.CommonUtils;

/**
 *
 * @author karl
 */
public class WebStringPBEConfig
    extends WebPBEConfig
implements StringPBEConfig {
    private String stringOutputType = null;
    
    public WebStringPBEConfig() {
        super();
    }
    public void setStringOutputType(final String stringOutputType) {
        this.stringOutputType =
                CommonUtils.getStandardStringOutputType(stringOutputType);
    }
    @Override
    public String getStringOutputType() {
        return this.stringOutputType;
    }
}
