//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.20 at 03:29:38 PM CET 
//


package cz.muni.ics.digilaw.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ffLinkInMUCatalog.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ffLinkInMUCatalog">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="add"/>
 *     &lt;enumeration value="added"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ffLinkInMUCatalog")
@XmlEnum
public enum FfLinkInMUCatalog {

    @XmlEnumValue("add")
    ADD("add"),
    @XmlEnumValue("added")
    ADDED("added");
    private final String value;

    FfLinkInMUCatalog(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FfLinkInMUCatalog fromValue(String v) {
        for (FfLinkInMUCatalog c: FfLinkInMUCatalog.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
