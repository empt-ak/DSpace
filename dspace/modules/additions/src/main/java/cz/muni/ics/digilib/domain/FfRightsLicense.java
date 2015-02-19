//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.23 at 09:17:16 AM CET 
//


package cz.muni.ics.digilib.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ffRightsLicense.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ffRightsLicense">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cc-by-3.0-cz"/>
 *     &lt;enumeration value="cc-by-sa-3.0-cz"/>
 *     &lt;enumeration value="cc-by-nc-3.0-cz"/>
 *     &lt;enumeration value="cc-by-nd-3.0-cz"/>
 *     &lt;enumeration value="cc-by-nc-sa-3.0-cz"/>
 *     &lt;enumeration value="cc-by-nc-nd-3.0-cz"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ffRightsLicense")
@XmlEnum
public enum FfRightsLicense {

    @XmlEnumValue("cc-by-3.0-cz")
    CC_BY_3_0_CZ("cc-by-3.0-cz"),
    @XmlEnumValue("cc-by-sa-3.0-cz")
    CC_BY_SA_3_0_CZ("cc-by-sa-3.0-cz"),
    @XmlEnumValue("cc-by-nc-3.0-cz")
    CC_BY_NC_3_0_CZ("cc-by-nc-3.0-cz"),
    @XmlEnumValue("cc-by-nd-3.0-cz")
    CC_BY_ND_3_0_CZ("cc-by-nd-3.0-cz"),
    @XmlEnumValue("cc-by-nc-sa-3.0-cz")
    CC_BY_NC_SA_3_0_CZ("cc-by-nc-sa-3.0-cz"),
    @XmlEnumValue("cc-by-nc-nd-3.0-cz")
    CC_BY_NC_ND_3_0_CZ("cc-by-nc-nd-3.0-cz");
    private final String value;

    FfRightsLicense(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FfRightsLicense fromValue(String v) {
        for (FfRightsLicense c: FfRightsLicense.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
