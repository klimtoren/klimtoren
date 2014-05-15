/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.shared;

import be.wolkmaan.klimtoren.security.exceptions.EncryptionOperationNotPossibleException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author karl
 */
public class CommonUtils {

    public static final String STRING_OUTPUT_TYPE_BASE64 = "base64";
    public static final String STRING_OUTPUT_TYPE_HEXADECIMAL = "hexadecimal";

    private static final List STRING_OUTPUT_TYPE_HEXADECIMAL_NAMES
            = Arrays.asList(
                    new String[]{
                        "HEXADECIMAL", "HEXA", "0X", "HEX", "HEXADEC"
                    }
            );
    private static char[] hexDigits
            = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static Boolean getStandardBooleanValue(final String value) {
        if (value == null) {
            return null;
        }
        final String upperValue = value.toUpperCase();

        if ("TRUE".equals(upperValue) || "ON".equals(upperValue) || "YES".equals(upperValue)) {
            return Boolean.TRUE;
        }
        if ("FALSE".equals(upperValue) || "OFF".equals(upperValue) || "NO".equals(upperValue)) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static String getStandardStringOutputType(final String value) {
        if (value == null) {
            return null;
        }
        if (STRING_OUTPUT_TYPE_HEXADECIMAL_NAMES.contains(value.toUpperCase())) {
            return STRING_OUTPUT_TYPE_HEXADECIMAL;
        }
        return STRING_OUTPUT_TYPE_BASE64;
    }

    public static String toHexadecimal(final byte[] message) {
        if (message == null) {
            return null;
        }
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < message.length; i++) {
            int curByte = message[i] & 0xff;
            buffer.append(hexDigits[curByte >> 4]);
            buffer.append(hexDigits[curByte & 0xf]);
        }
        return buffer.toString();
    }

    public static byte[] fromHexadecimal(final String message) {
        if (message == null) {
            return null;
        }
        if ((message.length() % 2) != 0) {
            throw new EncryptionOperationNotPossibleException();
        }
        try {
            final byte[] result = new byte[message.length() / 2];
            for (int i = 0; i < message.length(); i = i + 2) {
                final int first = Integer.parseInt("" + message.charAt(i), 16);
                final int second = Integer.parseInt("" + message.charAt(i + 1), 16);
                result[i / 2] = (byte) (0x0 + ((first & 0xff) << 4) + (second & 0xff));
            }
            return result;
        } catch (Exception e) {
            throw new EncryptionOperationNotPossibleException();
        }
    }

    public static void validateNotNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateNotEmpty(final String string, final String message) {
        if (isEmpty(string)) {
            throw new IllegalArgumentException(message);
        }

    }

    public static void validateIsTrue(final boolean expression, final String message) {
        if (expression == false) {
            throw new IllegalArgumentException(message);
        }
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static byte[] appendArrays(final byte[] firstArray, final byte[] secondArray) {
    
        validateNotNull(firstArray, "Appended array cannot be null");
        validateNotNull(secondArray, "Appended array cannot be null");
        
        final byte[] result = new byte[firstArray.length + secondArray.length];
        
        System.arraycopy(firstArray, 0, result, 0, firstArray.length);
        System.arraycopy(secondArray, 0, result, firstArray.length, secondArray.length);
        
        return result;
        
    }

    
    //this class should only be called statically
    private CommonUtils() {
        super();
    }
}
