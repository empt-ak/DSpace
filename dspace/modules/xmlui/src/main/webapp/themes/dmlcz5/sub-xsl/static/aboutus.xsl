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
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.statik.AboutUsAspect.div.news-section']"
    >
        <div class="page-header">
            <h1>About DML-CZ</h1>
        </div>
        
        The Czech Digital Mathematics Library (DML-CZ) has been developed in order to preserve in a digital form the content of major part of mathematical literature that has ever been published in the Czech lands, and to provide a free access to the digital content and bibliographical data.

        DML-CZ resulted from the project no. 1ET200190513 supported by the Academy of Sciences of the Czech Republic in the R&amp;D programme Information Society.

        DML-CZ is operated by the Institute of Mathematics AS CR.

        <h3>Project partners</h3>

        Institute of Mathematics AS CR, Praha
        Institute of Computer Science, Masaryk University, Brno
        Faculty of Informatics, Masaryk University, Brno
        Faculty of Mathematics and Physics, Charles University, Praha
        Library AS CR, Praha
        Details about the project teams can be found here.

        <h3>Ownership of the data</h3>

        The digitized journal and proceedings papers are displayed with the agreement of the publisher who owns the digital data. The digitized monographs are displayed with the agreement of the author and/or the publisher while the digital data are property of the Institute of Mathematics AS CR. The database itself, in particular the bibliographic data, are property of the Institute of Mathematics AS CR.

        <h3>Principles</h3>

        DML-CZ presents full texts articles and book chapters in PDF format, equipped with enhanced metadata including bibliographical references linked to Zentrablatt MATH and MathSciNet.

        Each retrodigitised page is scanned in a high quality (mostly 600 DPI bitonal) and adjusted with image processing software. The digital born documents are being obtained form the original sources provided by publishers. The presented page content and format corresponds to the original one.

        Journals are presented and accessed according to the terms of a contract with the publisher. The digital documents displayed in the DML-CZ are authorized with electronic stamps.
    </xsl:template>
            
</xsl:stylesheet>
