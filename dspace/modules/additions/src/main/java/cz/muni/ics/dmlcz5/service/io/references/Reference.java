//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.10 at 10:03:27 AM CET 
//
package cz.muni.ics.dmlcz5.service.io.references;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prefix" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authors">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="author" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="links">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element name="link">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>anyURI">
 *                           &lt;attribute name="source" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder =
{
    "prefix",
    "authors",
    "title",
    "suffix",
    "year",
    "links"
})
public class Reference
{

    @XmlElement(required = true)
    protected String prefix;
    @XmlElement(required = true)
    protected Authors authors;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected String suffix;
    protected Short year;
    @XmlElement(required = true)
    protected Links links;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the prefix property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPrefix(String value)
    {
        this.prefix = value;
    }

    /**
     * Gets the value of the authors property.
     *
     * @return possible object is {@link Authors }
     *
     */
    public Authors getAuthors()
    {
        return authors;
    }

    /**
     * Sets the value of the authors property.
     *
     * @param value allowed object is {@link Authors }
     *
     */
    public void setAuthors(Authors value)
    {
        this.authors = value;
    }

    /**
     * Gets the value of the title property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitle(String value)
    {
        this.title = value;
    }

    /**
     * Gets the value of the suffix property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSuffix()
    {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSuffix(String value)
    {
        this.suffix = value;
    }

    /**
     * Gets the value of the year property.
     *
     * @return possible object is {@link Short }
     *
     */
    public Short getYear()
    {
        return year;
    }

    /**
     * Sets the value of the year property.
     *
     * @param value allowed object is {@link Short }
     *
     */
    public void setYear(Short value)
    {
        this.year = value;
    }

    /**
     * Gets the value of the links property.
     *
     * @return possible object is {@link Links }
     *
     */
    public Links getLinks()
    {
        return links;
    }

    /**
     * Sets the value of the links property.
     *
     * @param value allowed object is {@link Links }
     *
     */
    public void setLinks(Links value)
    {
        this.links = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setId(String value)
    {
        this.id = value;
    }

}
