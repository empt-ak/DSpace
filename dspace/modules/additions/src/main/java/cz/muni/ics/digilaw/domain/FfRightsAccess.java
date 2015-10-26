//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.26 at 01:41:46 PM CET 
//


package cz.muni.ics.digilaw.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ffRightsAccess.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ffRightsAccess">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="openaccess"/>
 *     &lt;enumeration value="embargoedaccess"/>
 *     &lt;enumeration value="restrictedaccess-mu"/>
 *     &lt;enumeration value="closedaccess"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ffRightsAccess")
@XmlEnum
public enum FfRightsAccess {

    @XmlEnumValue("openaccess")
    OPENACCESS("openaccess"),
    @XmlEnumValue("embargoedaccess")
    EMBARGOEDACCESS("embargoedaccess"),
    @XmlEnumValue("restrictedaccess-mu")
    RESTRICTEDACCESS_MU("restrictedaccess-mu"),
    @XmlEnumValue("closedaccess")
    CLOSEDACCESS("closedaccess");
    private final String value;

    FfRightsAccess(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FfRightsAccess fromValue(String v) {
        for (FfRightsAccess c: FfRightsAccess.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
