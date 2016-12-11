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
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.statik.NewsAspect.div.news-section']"
    >
        <div class="row">
            <div class="col-12">
                <h1>News</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="media">
                    <div class="media-body">
                        <h4 class="media-heading">09.09.2015</h4>
                        Four new monographic collections of mid-20th century added to DML-CZ: Škola mladých matematiků (School of Young Mathematicians), Cesta k vědění (Path to Knowledge), Brána k vědění (Gate to Knowledge), Kruh (The Ring). The new conference series PANM added.
                    </div>
                </div>
                <div class="media">
                    <div class="media-body">
                        <h4 class="media-heading">01.06.2015</h4>
                        Digital Archive of prominent Czech mathematician Eduard Čech (1893–1960).
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
            
</xsl:stylesheet>
