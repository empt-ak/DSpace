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
 * <p>Java class for lawFontType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="lawFontType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="modern-latin"/>
 *     &lt;enumeration value="historic-fraktur"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "lawFontType")
@XmlEnum
public enum LawFontType {

    @XmlEnumValue("modern-latin")
    MODERN_LATIN("modern-latin"),
    @XmlEnumValue("historic-fraktur")
    HISTORIC_FRAKTUR("historic-fraktur");
    private final String value;

    LawFontType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LawFontType fromValue(String v) {
        for (LawFontType c: LawFontType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
