//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.23 at 09:17:16 AM CET 
//


package cz.muni.ics.digilaw.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PartAuthor"/>
 *         &lt;element ref="{}PartTitle"/>
 *       &lt;/sequence>
 *       &lt;attribute name="PartType" use="required" type="{}ffPartType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "partAuthor",
    "partTitle"
})
@XmlRootElement(name = "Part")
public class Part {

    @XmlElement(name = "PartAuthor", required = true)
    protected String partAuthor;
    @XmlElement(name = "PartTitle", required = true)
    protected String partTitle;
    @XmlAttribute(name = "PartType", required = true)
    protected FfPartType partType;

    /**
     * Gets the value of the partAuthor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartAuthor() {
        return partAuthor;
    }

    /**
     * Sets the value of the partAuthor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartAuthor(String value) {
        this.partAuthor = value;
    }

    /**
     * Gets the value of the partTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartTitle() {
        return partTitle;
    }

    /**
     * Sets the value of the partTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartTitle(String value) {
        this.partTitle = value;
    }

    /**
     * Gets the value of the partType property.
     * 
     * @return
     *     possible object is
     *     {@link FfPartType }
     *     
     */
    public FfPartType getPartType() {
        return partType;
    }

    /**
     * Sets the value of the partType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FfPartType }
     *     
     */
    public void setPartType(FfPartType value) {
        this.partType = value;
    }

}
