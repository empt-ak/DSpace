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
 * <p>Java class for ffSerialCatetory.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ffSerialCatetory">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="spffbu"/>
 *     &lt;enumeration value="bse"/>
 *     &lt;enumeration value="bbgn"/>
 *     &lt;enumeration value="erb"/>
 *     &lt;enumeration value="journal"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ffSerialCatetory")
@XmlEnum
public enum FfSerialCatetory {

    @XmlEnumValue("spffbu")
    SPFFBU("spffbu"),
    @XmlEnumValue("bse")
    BSE("bse"),
    @XmlEnumValue("bbgn")
    BBGN("bbgn"),
    @XmlEnumValue("erb")
    ERB("erb"),
    @XmlEnumValue("journal")
    JOURNAL("journal");
    private final String value;

    FfSerialCatetory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FfSerialCatetory fromValue(String v) {
        for (FfSerialCatetory c: FfSerialCatetory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
