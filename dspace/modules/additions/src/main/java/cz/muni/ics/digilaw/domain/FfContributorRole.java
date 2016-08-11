//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.09 at 03:34:01 PM CEST 
//


package cz.muni.ics.digilaw.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ffContributorRole.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ffContributorRole">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="editor"/>
 *     &lt;enumeration value="translator"/>
 *     &lt;enumeration value="illustrator"/>
 *     &lt;enumeration value="photographer"/>
 *     &lt;enumeration value="author-of-introduction"/>
 *     &lt;enumeration value="author-of-afterword"/>
 *     &lt;enumeration value="author-of-bibliography"/>
 *     &lt;enumeration value="translator-of-summary"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ffContributorRole")
@XmlEnum
public enum FfContributorRole {

    @XmlEnumValue("editor")
    EDITOR("editor"),
    @XmlEnumValue("translator")
    TRANSLATOR("translator"),
    @XmlEnumValue("illustrator")
    ILLUSTRATOR("illustrator"),
    @XmlEnumValue("photographer")
    PHOTOGRAPHER("photographer"),
    @XmlEnumValue("author-of-introduction")
    AUTHOR_OF_INTRODUCTION("author-of-introduction"),
    @XmlEnumValue("author-of-afterword")
    AUTHOR_OF_AFTERWORD("author-of-afterword"),
    @XmlEnumValue("author-of-bibliography")
    AUTHOR_OF_BIBLIOGRAPHY("author-of-bibliography"),
    @XmlEnumValue("translator-of-summary")
    TRANSLATOR_OF_SUMMARY("translator-of-summary"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    FfContributorRole(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FfContributorRole fromValue(String v) {
        for (FfContributorRole c: FfContributorRole.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
