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
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.statik.FAQAspect.div.faq-section']"
    >
        <div class="row">
            <div class="col-12">
                <h1>Frequently Asked Questions (FAQ)</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <ul>
                    <li>
                        <a href="#faq1">What is the DML-CZ?</a>
                    </li>
                    <li>
                        <a href="#faq2">Which documents are available in the DML-CZ?</a>
                    </li>
                    <li>
                        <a href="#faq3">Under what conditions can I see the full texts?</a>
                    </li>
                    <li>
                        <a href="#faq4">Who created the DML-CZ?</a>
                    </li>
                    <li>
                        <a href="#faq5">Who operates the DML-CZ?</a>
                    </li>
                    <li>
                        <a href="#faq6">Plans for further DML-CZ developments?</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="media" id="faq1">
                    <!--            <a class="media-left" href="#">
                        <img class="media-object" data-src="holder.js/64x64" alt="Generic placeholder image">
                    </a>-->
                    <div class="media-body">
                        <h4 class="media-heading">What is DML-CZ?</h4>
                        DML-CZ is the Czech Digital Mathematics Library created in order to digitize, preserve and make easily accessible the major part of mathematical literature that has been published in the Czech lands since the 19<sup>th</sup> century. See the <a href="http://dml.cz/about">About DML-CZ</a> for more details.
                    </div>
                </div>
                <div class="media" id="faq2">
                    <!--            <a class="media-left" href="#">
                        <img class="media-object" data-src="holder.js/64x64" alt="Generic placeholder image">
                    </a>-->
                    <div class="media-body">
                        <h4 class="media-heading">Which documents are available in the DML-CZ?</h4>
                        DML-CZ presents papers from selected mathematics journals, proceedings and monographs published by Czech institutions converted to the digital form. The number of documents available in the DML-CZ is gradually increasing. Click on the Browse-Collections link on the DML-CZ home page to check currently available collections.
                    </div>
                </div>
                <div class="media" id="faq3">
                    <!--            <a class="media-left" href="#">
                        <img class="media-object" data-src="holder.js/64x64" alt="Generic placeholder image">
                    </a>-->
                    <div class="media-body">
                        <h4 class="media-heading">Under what conditions can I see the full texts?</h4>
                        Full texts as well as metadata of all objects presented in the DML-CZ are freely available supposing the Condition of Use are respected.
                    </div>
                </div>
                <div class="media" id="faq4">
                    <!--            <a class="media-left" href="#">
                        <img class="media-object" data-src="holder.js/64x64" alt="Generic placeholder image">
                    </a>-->
                    <div class="media-body">
                        <h4 class="media-heading">Who created the DML-CZ?</h4>
                        The DML-CZ resulted from the project no. <strong>1ET200190513</strong> (2005-2009) supported by the Czech Academy of Sciences in the R&amp;D programme Information Society. The project has been accomplished from 1<sup>st</sup> January 2005 to 31<sup>st</sup> December 2009 by five <a href="http://project.dml.cz/partners.html">teams</a> from academic institutions. A number of Charles University and Masaryk University undergraduate and graduate students were involved as well.
                    </div>
                </div>
                <div class="media" id="faq5">
                    <!--            <a class="media-left" href="#">
                        <img class="media-object" data-src="holder.js/64x64" alt="Generic placeholder image">
                    </a>-->
                    <div class="media-body">
                        <h4 class="media-heading">Who operates the DML-CZ?</h4>
                        The DML-CZ is operated by the <a href="http://www.math.cas.cz/">Institute of Mathematics of the Czech Academy of Sciences</a> with the technical assistance of the Institute of Computer Science of the Masaryk University.
                    </div>
                </div>
                <div class="media" id="faq6">
                    <!--            <a class="media-left" href="#">
                        <img class="media-object" data-src="holder.js/64x64" alt="Generic placeholder image">
                    </a>-->
                    <div class="media-body">
                        <h4 class="media-heading">Plans for further DML-CZ developments?</h4>
                        Though the <a href="http://project.dml.cz/">DML-CZ research project</a> itself has been accomplished (see the <a href="http://project.dml.cz/docs/dmlcz_finalreport_2010.pdf">final report</a>) the development and completion of the DML-CZ continues using the created tools and processes. New documents are being continuously supplied such as new journal issues, digitized books, proceedings and other material (e.g. life-work archives of eminent Czech mathematicians). Content of the DML-CZ is harvested by the European Digital Mathematics Library <a href="https://eudml.org/">EuDML</a>.
                    </div>
                </div>
            </div>
        </div>        
    </xsl:template>
            
</xsl:stylesheet>
