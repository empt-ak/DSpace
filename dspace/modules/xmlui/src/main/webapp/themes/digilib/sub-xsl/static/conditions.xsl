<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : landing-page.xsl
    Created on : July 24, 2015, 10:44 AM
    Author     : emptak
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                xmlns:dri="http://di.tamu.edu/DRI/1.0/"
                xmlns:mets="http://www.loc.gov/METS/"
                xmlns:dim="http://www.dspace.org/xmlns/dspace/dim"
                xmlns:xlink="http://www.w3.org/TR/xlink/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:atom="http://www.w3.org/2005/Atom"
                xmlns:ore="http://www.openarchives.org/ore/terms/"
                xmlns:oreatom="http://www.openarchives.org/ore/atom/"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:encoder="xalan://java.net.URLEncoder"
                xmlns:util="org.dspace.app.xmlui.utils.XSLUtils"
                xmlns:confman="org.dspace.core.ConfigurationManager"
                exclude-result-prefixes="xalan encoder i18n dri mets dim xlink xsl util confman">
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    
    <xsl:template 
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.statik.ConditionsAspect.div.conditions-section']"
    >
        <div class="row">
            <div class="col-xs-12">
                <h1>Conditions of Use</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <p>The <a href="http://www.math.cas.cz/">Institute of Mathematics CAS</a> (IM in the following) provides access to digitized documents strictly for personal use. Using the library's online system to access or download a digitized document you accept these Conditions of Use.</p>

                <p>The database itself is the property of the IM and contains elements covered by copyright. Access to the database containing the bibliographical references of all journal and conference proceedings articles and of monograph chapters is provided for free via the "search" and "browse" functions.</p>

                <p>The digitized journal articles are displayed with the agreement of the corresponding publisher or his successor and remain his property.</p>

                <p>The digitized monograph chapters are displayed with the agreement of the author and remain his/her property. In the case of elapsed author's rights the digital data of monographs are owned by the IM.</p>

                <p>Any copy or reconstruction of a significant part of the database (for instance data about a whole journal issue or volume, a whole proceedings or book volume) using data from the DML-CZ site is a counterfeit breaking the law.</p>

                <p>It is forbidden to modify the displayed documents. In particular, the copyright and Terms of use stated at the front page of every document must not be detached from any print or electronic copy.</p>

                <p>Commercial exploitation of the contents of the DML-CZ without explicit authorization of the IM is forbidden.</p>

                <h4>Copyright and author's rights for journal and proceedings papers</h4>

                <p>The DML-CZ respects author's rights. Generally, the authors of old journal and proceedings articles ceded the right of publication to the publisher. They could not waive inexistent rights such as providing electronic copies and displaying them on the Internet. The digitized articles in the DML-CZ remain property of the publisher or his successor who have got authors' authorization to digitize their articles and display them on the Internet.</p>

                <p>All texts have been published in the DML-CZ with the aim to promote publication of science and to maintain coherence of the collections. We invite the authors and copyright holders to send us their suggestions or possible objections.</p>

                <p>
                    <a href="">Complete text of Act No. 121/2000 on Copyright and Rights Related to Copyright and on Amendment to Certain Acts (the Copyright Act), as amended by Act No. 81/2005, Act No. 61/2006 and Act No. 216/2006</a>
                </p>
            </div>
        </div>
    </xsl:template>
            
</xsl:stylesheet>
