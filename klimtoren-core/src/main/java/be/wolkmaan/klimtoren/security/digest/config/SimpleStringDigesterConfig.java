/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wolkmaan.klimtoren.security.digest.config;

import be.wolkmaan.klimtoren.shared.CommonUtils;

/**
 *
 * @author karl
 */
public class SimpleStringDigesterConfig
        extends SimpleDigesterConfig
        implements StringDigesterConfig {

    private Boolean unicodeNormalizationIgnored = null;
    private String stringOutputType = null;
    private String prefix = null;
    private String suffix = null;

    /**
     * <p>
     * Creates a new <tt>SimpleStringDigesterConfig</tt> instance.
     * </p>
     */
    public SimpleStringDigesterConfig() {
        super();
    }

    /**
     * <p>
     * Sets whether the unicode text normalization step should be ignored.
     * </p>
     * <p>
     * The Java Virtual Machine internally handles all Strings as UNICODE. When
     * digesting or matching digests in jasypt, these Strings are first
     * <b>normalized to its NFC form</b> so that digest matching is not affected
     * by the specific form in which the messages where input.
     * </p>
     * <p>
     * <b>It is normally safe (and recommended) to leave this parameter set to
     * its default FALSE value (and thus DO perform normalization
     * operations)</b>. But in some specific cases in which issues with legacy
     * software could arise, it might be useful to set this to TRUE.
     * </p>
     * <p>
     * For more information on unicode text normalization, see this issue of
     * <a
     * href="http://java.sun.com/mailers/techtips/corejava/2007/tt0207.html">Core
     * Java Technologies Tech Tips</a>.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #isUnicodeNormalizationIgnored()}
     * </p>
     *
     * @param unicodeNormalizationIgnored whether the unicode text normalization
     * step should be ignored or not.
     */
    public void setUnicodeNormalizationIgnored(final Boolean unicodeNormalizationIgnored) {
        this.unicodeNormalizationIgnored = unicodeNormalizationIgnored;
    }

    /**
     * <p>
     * Sets whether the unicode text normalization step should be ignored.
     * </p>
     * <p>
     * The Java Virtual Machine internally handles all Strings as UNICODE. When
     * digesting or matching digests in jasypt, these Strings are first
     * <b>normalized to its NFC form</b> so that digest matching is not affected
     * by the specific form in which the messages where input.
     * </p>
     * <p>
     * <b>It is normally safe (and recommended) to leave this parameter set to
     * its default FALSE value (and thus DO perform normalization
     * operations)</b>. But in some specific cases in which issues with legacy
     * software could arise, it might be useful to set this to TRUE.
     * </p>
     * <p>
     * For more information on unicode text normalization, see this issue of
     * <a
     * href="http://java.sun.com/mailers/techtips/corejava/2007/tt0207.html">Core
     * Java Technologies Tech Tips</a>.
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #isUnicodeNormalizationIgnored()}
     * </p>
     *
     * @since 1.4
     *
     * @param unicodeNormalizationIgnored whether the unicode text normalization
     * step should be ignored or not.
     */
    public void setUnicodeNormalizationIgnored(final String unicodeNormalizationIgnored) {
        if (unicodeNormalizationIgnored != null) {
            this.unicodeNormalizationIgnored
                    = CommonUtils.getStandardBooleanValue(unicodeNormalizationIgnored);
        } else {
            this.unicodeNormalizationIgnored = null;
        }
    }

    /**
     * <p>
     * Sets the the form in which String output will be encoded. Available
     * encoding types are:
     * </p>
     * <ul>
     * <li><tt><b>base64</b></tt> (default)</li>
     * <li><tt><b>hexadecimal</b></tt></li>
     * </ul>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getStringOutputType()}
     * </p>
     *
     * @param stringOutputType the string output type.
     */
    public void setStringOutputType(final String stringOutputType) {
        this.stringOutputType
                = CommonUtils.
                getStandardStringOutputType(stringOutputType);
    }

    /**
     * <p>
     * Sets the prefix to be added at the beginning of encryption results, and
     * also to be expected at the beginning of plain messages provided for
     * matching operations (raising an
     * {@link EncryptionOperationNotPossibleException} if not).
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getPrefix()}
     * </p>
     *
     * @since 1.7
     *
     * @param prefix
     */
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    /**
     * <p>
     * Sets the suffix to be added at the end of encryption results, and also to
     * be expected at the end of plain messages provided for matching operations
     * (raising an {@link EncryptionOperationNotPossibleException} if not).
     * </p>
     * <p>
     * If not set, null will be returned.
     * </p>
     * <p>
     * Determines the result of: {@link #getSuffix()}
     * </p>
     *
     * @since 1.7
     *
     * @param suffix
     */
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public Boolean isUnicodeNormalizationIgnored() {
        return this.unicodeNormalizationIgnored;
    }

    @Override
    public String getStringOutputType() {
        return this.stringOutputType;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

}
