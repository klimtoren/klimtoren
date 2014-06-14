/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.common;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.Date;

/**
 *
 * @author karl
 */
public class Utils {
    public static String normalizeAndTrim(String string) {
        return Normalizer
                .normalize(string, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replace(" ", "");
    }
    public static Timestamp currentTimestamp() {
        return new Timestamp((new Date()).getTime());
    }
}
