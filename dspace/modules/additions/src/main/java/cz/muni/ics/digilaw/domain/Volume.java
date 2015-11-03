//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.26 at 01:49:42 PM CET 
//


package cz.muni.ics.digilaw.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{}VolumeName" minOccurs="0"/>
 *         &lt;element ref="{}Note" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}LinkToReal" minOccurs="0"/>
 *         &lt;element ref="{}LinkToRealDesc" minOccurs="0"/>
 *         &lt;element ref="{}LinkToVirtual" minOccurs="0"/>
 *         &lt;element ref="{}LinkToVirtualDesc" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "volumeName",
    "title",
    "note",
    "linkToReal",
    "linkToRealDesc",
    "linkToVirtual",
    "linkToVirtualDesc"
})
@XmlRootElement(name = "Volume")
public class Volume {

    @XmlElement(name = "VolumeName")
    protected String volumeName;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "Note")
    protected List<Note> note;
    @XmlElement(name = "LinkToReal")
    protected String linkToReal;
    @XmlElement(name = "LinkToRealDesc")
    protected String linkToRealDesc;
    @XmlElement(name = "LinkToVirtual")
    protected String linkToVirtual;
    @XmlElement(name = "LinkToVirtualDesc")
    protected String linkToVirtualDesc;

    /**
     * Gets the value of the volumeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVolumeName() {
        return volumeName;
    }

    /**
     * Sets the value of the volumeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVolumeName(String value) {
        this.volumeName = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the note property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNote().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Note }
     * 
     * 
     */
    public List<Note> getNote() {
        if (note == null) {
            note = new ArrayList<Note>();
        }
        return this.note;
    }

    /**
     * Gets the value of the linkToReal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToReal() {
        return linkToReal;
    }

    /**
     * Sets the value of the linkToReal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToReal(String value) {
        this.linkToReal = value;
    }

    /**
     * Gets the value of the linkToRealDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToRealDesc() {
        return linkToRealDesc;
    }

    /**
     * Sets the value of the linkToRealDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToRealDesc(String value) {
        this.linkToRealDesc = value;
    }

    /**
     * Gets the value of the linkToVirtual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToVirtual() {
        return linkToVirtual;
    }

    /**
     * Sets the value of the linkToVirtual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToVirtual(String value) {
        this.linkToVirtual = value;
    }

    /**
     * Gets the value of the linkToVirtualDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToVirtualDesc() {
        return linkToVirtualDesc;
    }

    /**
     * Sets the value of the linkToVirtualDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToVirtualDesc(String value) {
        this.linkToVirtualDesc = value;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}