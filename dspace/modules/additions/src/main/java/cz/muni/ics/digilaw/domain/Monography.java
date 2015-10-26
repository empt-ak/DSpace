//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.26 at 01:41:46 PM CET 
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
 *         &lt;element ref="{}Title"/>
 *         &lt;element ref="{}TitleVariant" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Author" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Contributor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ISBN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}ISSN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Language" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Edition" minOccurs="0"/>
 *         &lt;element ref="{}Publisher" maxOccurs="unbounded"/>
 *         &lt;element ref="{}PublPlace" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}PublYear"/>
 *         &lt;element ref="{}FormatExtent"/>
 *         &lt;element ref="{}Series" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Abstract" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Keyword" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Subject" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}DescribedURL" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}AlephSysno" minOccurs="0"/>
 *         &lt;element ref="{}OpenAccess"/>
 *         &lt;element ref="{}RelatedDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Note" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Warning" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}PageSize" minOccurs="0"/>
 *         &lt;element ref="{}PDFOrigin" minOccurs="0"/>
 *         &lt;element ref="{}FontType" maxOccurs="unbounded" minOccurs="0"/>
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
    "title",
    "titleVariant",
    "author",
    "contributor",
    "isbn",
    "issn",
    "language",
    "edition",
    "publisher",
    "publPlace",
    "publYear",
    "formatExtent",
    "series",
    "_abstract",
    "keyword",
    "subject",
    "describedURL",
    "alephSysno",
    "openAccess",
    "relatedDocument",
    "note",
    "warning",
    "pageSize",
    "pdfOrigin",
    "fontType"
})
@XmlRootElement(name = "Monography")
public class Monography {

    @XmlElement(name = "Title", required = true)
    protected Title title;
    @XmlElement(name = "TitleVariant")
    protected List<TitleVariant> titleVariant;
    @XmlElement(name = "Author", required = true)
    protected List<Author> author;
    @XmlElement(name = "Contributor")
    protected List<Contributor> contributor;
    @XmlElement(name = "ISBN")
    protected List<String> isbn;
    @XmlElement(name = "ISSN")
    protected List<String> issn;
    @XmlElement(name = "Language", required = true)
    protected List<String> language;
    @XmlElement(name = "Edition")
    protected String edition;
    @XmlElement(name = "Publisher", required = true)
    protected List<String> publisher;
    @XmlElement(name = "PublPlace")
    protected List<String> publPlace;
    @XmlElement(name = "PublYear", required = true)
    protected String publYear;
    @XmlElement(name = "FormatExtent", required = true)
    protected String formatExtent;
    @XmlElement(name = "Series")
    protected List<Series> series;
    @XmlElement(name = "Abstract")
    protected List<Abstract> _abstract;
    @XmlElement(name = "Keyword")
    protected List<Keyword> keyword;
    @XmlElement(name = "Subject")
    protected List<LawSubject> subject;
    @XmlElement(name = "DescribedURL")
    protected List<DescribedURL> describedURL;
    @XmlElement(name = "AlephSysno")
    protected String alephSysno;
    @XmlElement(name = "OpenAccess", required = true)
    protected FfOpenAccess openAccess;
    @XmlElement(name = "RelatedDocument")
    protected List<RelatedDocument> relatedDocument;
    @XmlElement(name = "Note")
    protected List<Note> note;
    @XmlElement(name = "Warning")
    protected List<String> warning;
    @XmlElement(name = "PageSize")
    protected PageSize pageSize;
    @XmlElement(name = "PDFOrigin")
    protected FfPDFOrigin pdfOrigin;
    @XmlElement(name = "FontType")
    protected List<LawFontType> fontType;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link Title }
     *     
     */
    public Title getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link Title }
     *     
     */
    public void setTitle(Title value) {
        this.title = value;
    }

    /**
     * Gets the value of the titleVariant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the titleVariant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTitleVariant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TitleVariant }
     * 
     * 
     */
    public List<TitleVariant> getTitleVariant() {
        if (titleVariant == null) {
            titleVariant = new ArrayList<TitleVariant>();
        }
        return this.titleVariant;
    }

    /**
     * Gets the value of the author property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the author property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Author }
     * 
     * 
     */
    public List<Author> getAuthor() {
        if (author == null) {
            author = new ArrayList<Author>();
        }
        return this.author;
    }

    /**
     * Gets the value of the contributor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contributor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContributor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Contributor }
     * 
     * 
     */
    public List<Contributor> getContributor() {
        if (contributor == null) {
            contributor = new ArrayList<Contributor>();
        }
        return this.contributor;
    }

    /**
     * Gets the value of the isbn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isbn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getISBN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getISBN() {
        if (isbn == null) {
            isbn = new ArrayList<String>();
        }
        return this.isbn;
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
     * Gets the value of the language property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the language property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLanguage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLanguage() {
        if (language == null) {
            language = new ArrayList<String>();
        }
        return this.language;
    }

    /**
     * Gets the value of the edition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdition() {
        return edition;
    }

    /**
     * Sets the value of the edition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdition(String value) {
        this.edition = value;
    }

    /**
     * Gets the value of the publisher property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the publisher property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublisher().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPublisher() {
        if (publisher == null) {
            publisher = new ArrayList<String>();
        }
        return this.publisher;
    }

    /**
     * Gets the value of the publPlace property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the publPlace property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublPlace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPublPlace() {
        if (publPlace == null) {
            publPlace = new ArrayList<String>();
        }
        return this.publPlace;
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
     * Gets the value of the formatExtent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatExtent() {
        return formatExtent;
    }

    /**
     * Sets the value of the formatExtent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatExtent(String value) {
        this.formatExtent = value;
    }

    /**
     * Gets the value of the series property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the series property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSeries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Series }
     * 
     * 
     */
    public List<Series> getSeries() {
        if (series == null) {
            series = new ArrayList<Series>();
        }
        return this.series;
    }

    /**
     * Gets the value of the abstract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the abstract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbstract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Abstract }
     * 
     * 
     */
    public List<Abstract> getAbstract() {
        if (_abstract == null) {
            _abstract = new ArrayList<Abstract>();
        }
        return this._abstract;
    }

    /**
     * Gets the value of the keyword property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyword property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyword().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Keyword }
     * 
     * 
     */
    public List<Keyword> getKeyword() {
        if (keyword == null) {
            keyword = new ArrayList<Keyword>();
        }
        return this.keyword;
    }

    /**
     * Gets the value of the subject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LawSubject }
     * 
     * 
     */
    public List<LawSubject> getSubject() {
        if (subject == null) {
            subject = new ArrayList<LawSubject>();
        }
        return this.subject;
    }

    /**
     * Gets the value of the describedURL property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the describedURL property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescribedURL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DescribedURL }
     * 
     * 
     */
    public List<DescribedURL> getDescribedURL() {
        if (describedURL == null) {
            describedURL = new ArrayList<DescribedURL>();
        }
        return this.describedURL;
    }

    /**
     * Gets the value of the alephSysno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlephSysno() {
        return alephSysno;
    }

    /**
     * Sets the value of the alephSysno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlephSysno(String value) {
        this.alephSysno = value;
    }

    /**
     * Gets the value of the openAccess property.
     * 
     * @return
     *     possible object is
     *     {@link FfOpenAccess }
     *     
     */
    public FfOpenAccess getOpenAccess() {
        return openAccess;
    }

    /**
     * Sets the value of the openAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link FfOpenAccess }
     *     
     */
    public void setOpenAccess(FfOpenAccess value) {
        this.openAccess = value;
    }

    /**
     * Gets the value of the relatedDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedDocument }
     * 
     * 
     */
    public List<RelatedDocument> getRelatedDocument() {
        if (relatedDocument == null) {
            relatedDocument = new ArrayList<RelatedDocument>();
        }
        return this.relatedDocument;
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
     * Gets the value of the warning property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warning property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarning().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getWarning() {
        if (warning == null) {
            warning = new ArrayList<String>();
        }
        return this.warning;
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
     * Gets the value of the fontType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fontType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFontType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LawFontType }
     * 
     * 
     */
    public List<LawFontType> getFontType() {
        if (fontType == null) {
            fontType = new ArrayList<LawFontType>();
        }
        return this.fontType;
    }

}
