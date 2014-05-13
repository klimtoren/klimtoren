/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.shared.view;

import be.wolkmaan.klimtoren.shared.view.BaseView;
import lombok.Data;

/**
 *
 * @author karl
 */
@Data
public class PojoView implements DataView {
    private final Object data;
    private final Class<? extends BaseView> view;

    @Override
    public boolean hasView() {
        return true;
    }
}
