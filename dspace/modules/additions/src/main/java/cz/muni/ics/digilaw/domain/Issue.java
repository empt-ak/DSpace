//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.23 at 09:17:16 AM CET 
//


package cz.muni.ics.digilaw.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element ref="{}IssueName" minOccurs="0"/>
 *         &lt;element ref="{}IssueNameVariant" minOccurs="0"/>
 *         &lt;element ref="{}IssueTitle" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ISBN" minOccurs="0"/>
 *         &lt;element ref="{}ISSN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Note" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}PageSize" minOccurs="0"/>
 *         &lt;element ref="{}Years" minOccurs="0"/>
 *         &lt;element ref="{}PublYear" minOccurs="0"/>
 *         &lt;element ref="{}PublicationDate" minOccurs="0"/>
 *         &lt;element ref="{}EmbargoEndDate" minOccurs="0"/>
 *         &lt;element ref="{}RealIssue" minOccurs="0"/>
 *         &lt;element ref="{}PDFOrigin" minOccurs="0"/>
 *         &lt;element ref="{}LinkToReal" minOccurs="0"/>
 *         &lt;element ref="{}LinkToRealDesc" minOccurs="0"/>
 *         &lt;element ref="{}LinkToVirtualData" minOccurs="0"/>
 *         &lt;element ref="{}LinkToVirtual" minOccurs="0"/>
 *         &lt;element ref="{}LinkToVirtualDesc" minOccurs="0"/>
 *         &lt;element ref="{}LinkToVirtualDescNum" minOccurs="0"/>
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
    "issueName",
    "issueNameVariant",
    "issueTitle",
    "isbn",
    "issn",
    "note",
    "pageSize",
    "years",
    "publYear",
    "publicationDate",
    "embargoEndDate",
    "realIssue",
    "pdfOrigin",
    "linkToReal",
    "linkToRealDesc",
    "linkToVirtualData",
    "linkToVirtual",
    "linkToVirtualDesc",
    "linkToVirtualDescNum"
})
@XmlRootElement(name = "Issue")
public class Issue {

    @XmlElement(name = "IssueName")
    protected String issueName;
    @XmlElement(name = "IssueNameVariant")
    protected String issueNameVariant;
    @XmlElement(name = "IssueTitle")
    protected List<String> issueTitle;
    @XmlElement(name = "ISBN")
    protected String isbn;
    @XmlElement(name = "ISSN")
    protected List<String> issn;
    @XmlElement(name = "Note")
    protected List<Note> note;
    @XmlElement(name = "PageSize")
    protected PageSize pageSize;
    @XmlElement(name = "Years")
    protected String years;
    @XmlElement(name = "PublYear")
    protected String publYear;
    @XmlElement(name = "PublicationDate")
    protected String publicationDate;
    @XmlElement(name = "EmbargoEndDate")
    protected String embargoEndDate;
    @XmlElement(name = "RealIssue")
    protected String realIssue;
    @XmlElement(name = "PDFOrigin")
    @XmlSchemaType(name = "NMTOKEN")
    protected FfPDFOrigin pdfOrigin;
    @XmlElement(name = "LinkToReal")
    protected String linkToReal;
    @XmlElement(name = "LinkToRealDesc")
    protected String linkToRealDesc;
    @XmlElement(name = "LinkToVirtualData")
    protected String linkToVirtualData;
    @XmlElement(name = "LinkToVirtual")
    protected String linkToVirtual;
    @XmlElement(name = "LinkToVirtualDesc")
    protected String linkToVirtualDesc;
    @XmlElement(name = "LinkToVirtualDescNum")
    protected String linkToVirtualDescNum;

    /**
     * Gets the value of the issueName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueName() {
        return issueName;
    }

    /**
     * Sets the value of the issueName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueName(String value) {
        this.issueName = value;
    }

    /**
     * Gets the value of the issueNameVariant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueNameVariant() {
        return issueNameVariant;
    }

    /**
     * Sets the value of the issueNameVariant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueNameVariant(String value) {
        this.issueNameVariant = value;
    }

    /**
     * Gets the value of the issueTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the issueTitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIssueTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIssueTitle() {
        if (issueTitle == null) {
            issueTitle = new ArrayList<String>();
        }
        return this.issueTitle;
    }

    /**
     * Gets the value of the isbn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * Sets the value of the isbn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISBN(String value) {
        this.isbn = value;
    }

    /**
     * Gets the value of the issn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the issn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getISSN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getISSN() {
        if (issn == null) {
            issn = new ArrayList<String>();
        }
        return this.issn;
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
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link PageSize }
     *     
     */
    public PageSize getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageSize }
     *     
     */
    public void setPageSize(PageSize value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the years property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYears() {
        return years;
    }

    /**
     * Sets the value of the years property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYears(String value) {
        this.years = value;
    }

    /**
     * Gets the value of the publYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublYear() {
        return publYear;
    }

    /**
     * Sets the value of the publYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublYear(String value) {
        this.publYear = value;
    }

    /**
     * Gets the value of the publicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the value of the publicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicationDate(String value) {
        this.publicationDate = value;
    }

    /**
     * Gets the value of the embargoEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmbargoEndDate() {
        return embargoEndDate;
    }

    /**
     * Sets the value of the embargoEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmbargoEndDate(String value) {
        this.embargoEndDate = value;
    }

    /**
     * Gets the value of the realIssue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealIssue() {
        return realIssue;
    }

    /**
     * Sets the value of the realIssue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealIssue(String value) {
        this.realIssue = value;
    }

    /**
     * Gets the value of the pdfOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link FfPDFOrigin }
     *     
     */
    public FfPDFOrigin getPDFOrigin() {
        return pdfOrigin;
    }

    /**
     * Sets the value of the pdfOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link FfPDFOrigin }
     *     
     */
    public void setPDFOrigin(FfPDFOrigin value) {
        this.pdfOrigin = value;
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
     * Gets the value of the linkToVirtualData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToVirtualData() {
        return linkToVirtualData;
    }

    /**
     * Sets the value of the linkToVirtualData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToVirtualData(String value) {
        this.linkToVirtualData = value;
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

    /**
     * Gets the value of the linkToVirtualDescNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToVirtualDescNum() {
        return linkToVirtualDescNum;
    }

    /**
     * Sets the value of the linkToVirtualDescNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToVirtualDescNum(String value) {
        this.linkToVirtualDescNum = value;
    }

}
