/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.shared.view;

import be.wolkmaan.klimtoren.shared.view.BaseView;

/**
 *
 * @author karl
 */
public interface DataView {
	boolean hasView();
	Class<? extends BaseView> getView();
	Object getData();
}
