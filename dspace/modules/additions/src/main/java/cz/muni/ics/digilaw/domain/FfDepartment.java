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
 * <p>Java class for ffDepartment.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ffDepartment">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACOR"/>
 *     &lt;enumeration value="ACPS"/>
 *     &lt;enumeration value="anglistika"/>
 *     &lt;enumeration value="archeologie-muzeologie"/>
 *     &lt;enumeration value="CARLA"/>
 *     &lt;enumeration value="ceska-literatura"/>
 *     &lt;enumeration value="cesky-jazyk"/>
 *     &lt;enumeration value="CIT"/>
 *     &lt;enumeration value="dejiny-umeni"/>
 *     &lt;enumeration value="divadelni-veda"/>
 *     &lt;enumeration value="estetika"/>
 *     &lt;enumeration value="etnologie"/>
 *     &lt;enumeration value="FIFA"/>
 *     &lt;enumeration value="filmova-veda"/>
 *     &lt;enumeration value="filozofie"/>
 *     &lt;enumeration value="germanistika"/>
 *     &lt;enumeration value="historie"/>
 *     &lt;enumeration value="hudebni-veda"/>
 *     &lt;enumeration value="jazykoveda"/>
 *     &lt;enumeration value="KISK"/>
 *     &lt;enumeration value="klasicka-studia"/>
 *     &lt;enumeration value="pedagogika"/>
 *     &lt;enumeration value="psychologie"/>
 *     &lt;enumeration value="PVHA"/>
 *     &lt;enumeration value="religionistika"/>
 *     &lt;enumeration value="romanistika"/>
 *     &lt;enumeration value="slavistika"/>
 *     &lt;enumeration value="sociologie"/>
 *     &lt;enumeration value="UK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ffDepartment")
@XmlEnum
public enum FfDepartment {

    ACOR("ACOR"),
    ACPS("ACPS"),
    @XmlEnumValue("anglistika")
    ANGLISTIKA("anglistika"),
    @XmlEnumValue("archeologie-muzeologie")
    ARCHEOLOGIE_MUZEOLOGIE("archeologie-muzeologie"),
    CARLA("CARLA"),
    @XmlEnumValue("ceska-literatura")
    CESKA_LITERATURA("ceska-literatura"),
    @XmlEnumValue("cesky-jazyk")
    CESKY_JAZYK("cesky-jazyk"),
    CIT("CIT"),
    @XmlEnumValue("dejiny-umeni")
    DEJINY_UMENI("dejiny-umeni"),
    @XmlEnumValue("divadelni-veda")
    DIVADELNI_VEDA("divadelni-veda"),
    @XmlEnumValue("estetika")
    ESTETIKA("estetika"),
    @XmlEnumValue("etnologie")
    ETNOLOGIE("etnologie"),
    FIFA("FIFA"),
    @XmlEnumValue("filmova-veda")
    FILMOVA_VEDA("filmova-veda"),
    @XmlEnumValue("filozofie")
    FILOZOFIE("filozofie"),
    @XmlEnumValue("germanistika")
    GERMANISTIKA("germanistika"),
    @XmlEnumValue("historie")
    HISTORIE("historie"),
    @XmlEnumValue("hudebni-veda")
    HUDEBNI_VEDA("hudebni-veda"),
    @XmlEnumValue("jazykoveda")
    JAZYKOVEDA("jazykoveda"),
    KISK("KISK"),
    @XmlEnumValue("klasicka-studia")
    KLASICKA_STUDIA("klasicka-studia"),
    @XmlEnumValue("pedagogika")
    PEDAGOGIKA("pedagogika"),
    @XmlEnumValue("psychologie")
    PSYCHOLOGIE("psychologie"),
    PVHA("PVHA"),
    @XmlEnumValue("religionistika")
    RELIGIONISTIKA("religionistika"),
    @XmlEnumValue("romanistika")
    ROMANISTIKA("romanistika"),
    @XmlEnumValue("slavistika")
    SLAVISTIKA("slavistika"),
    @XmlEnumValue("sociologie")
    SOCIOLOGIE("sociologie"),
    UK("UK");
    private final String value;

    FfDepartment(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FfDepartment fromValue(String v) {
        for (FfDepartment c: FfDepartment.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
