/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.shared.view;

import be.wolkmaan.klimtoren.shared.view.BaseView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author karl
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseView {
    public Class<? extends BaseView> value();
}
