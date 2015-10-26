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
 * <p>Java class for lawSubject.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="lawSubject">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="pravni-teorie"/>
 *     &lt;enumeration value="rimske-pravo"/>
 *     &lt;enumeration value="pravni-dejiny"/>
 *     &lt;enumeration value="obcanske-pravo"/>
 *     &lt;enumeration value="trestni-pravo"/>
 *     &lt;enumeration value="obchodni-pravo"/>
 *     &lt;enumeration value="ustavni-pravo"/>
 *     &lt;enumeration value="spravni-pravo"/>
 *     &lt;enumeration value="mezinarodni-pravo"/>
 *     &lt;enumeration value="narodni-hospodarstvi-financni-pravo"/>
 *     &lt;enumeration value="ostatni-pravni-fond"/>
 *     &lt;enumeration value="ostatni-nepravni-fond"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "lawSubject")
@XmlEnum
public enum LawSubject {

    @XmlEnumValue("pravni-teorie")
    PRAVNI_TEORIE("pravni-teorie"),
    @XmlEnumValue("rimske-pravo")
    RIMSKE_PRAVO("rimske-pravo"),
    @XmlEnumValue("pravni-dejiny")
    PRAVNI_DEJINY("pravni-dejiny"),
    @XmlEnumValue("obcanske-pravo")
    OBCANSKE_PRAVO("obcanske-pravo"),
    @XmlEnumValue("trestni-pravo")
    TRESTNI_PRAVO("trestni-pravo"),
    @XmlEnumValue("obchodni-pravo")
    OBCHODNI_PRAVO("obchodni-pravo"),
    @XmlEnumValue("ustavni-pravo")
    USTAVNI_PRAVO("ustavni-pravo"),
    @XmlEnumValue("spravni-pravo")
    SPRAVNI_PRAVO("spravni-pravo"),
    @XmlEnumValue("mezinarodni-pravo")
    MEZINARODNI_PRAVO("mezinarodni-pravo"),
    @XmlEnumValue("narodni-hospodarstvi-financni-pravo")
    NARODNI_HOSPODARSTVI_FINANCNI_PRAVO("narodni-hospodarstvi-financni-pravo"),
    @XmlEnumValue("ostatni-pravni-fond")
    OSTATNI_PRAVNI_FOND("ostatni-pravni-fond"),
    @XmlEnumValue("ostatni-nepravni-fond")
    OSTATNI_NEPRAVNI_FOND("ostatni-nepravni-fond");
    private final String value;

    LawSubject(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LawSubject fromValue(String v) {
        for (LawSubject c: LawSubject.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
