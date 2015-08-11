<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-variables.xsl
    Created on : July 24, 2015, 9:49 AM
    Author     : emptak
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                xmlns:dri="http://di.tamu.edu/DRI/1.0/"
                xmlns:mets="http://www.loc.gov/METS/"
                xmlns:xlink="http://www.w3.org/TR/xlink/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:dim="http://www.dspace.org/xmlns/dspace/dim"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:mods="http://www.loc.gov/mods/v3"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns="http://www.w3.org/1999/xhtml"
                exclude-result-prefixes="i18n dri mets xlink xsl dim xhtml mods dc">

    <xsl:variable 
        name="theme"
    >
        <xsl:text>dmlcz5</xsl:text>
    </xsl:variable>
    
    <xsl:variable 
        name="contextPath" 
        select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='contextPath']" 
    />
    
    <xsl:variable
        name="resourcePath"
    >
        <xsl:value-of select="$contextPath" />
        <xsl:text>/themes/</xsl:text>
        <xsl:value-of select="$theme" />
    </xsl:variable>
    
    <xsl:variable 
        name="solrServer" 
        select="'http://localhost:8080/solr5dml/search/'" 
    />
    
    <xsl:variable
        name="debug"
        select="'true'"
    />
    
    <xsl:variable
        name="currentViewHandle"
        select="substring-after(/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='focus' and @qualifier='container'],'hdl:')"
    />
        
</xsl:stylesheet>