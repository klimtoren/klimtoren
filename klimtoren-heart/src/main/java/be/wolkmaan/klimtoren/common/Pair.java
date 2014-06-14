/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author karl
 * @param <A>
 * @param <B>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<A, B> {
    private A value1;
    private B value2;
}
