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
                xmlns:confman="org.dspace.core.ConfigurationManager"
                exclude-result-prefixes="i18n dri mets xlink xsl dim xhtml mods dc confman">
    
    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    
    <xsl:variable 
        name="theme"
    >
        <xsl:text>dmlcz5</xsl:text>
    </xsl:variable>
     
    <xsl:variable 
        name="contextPath" 
        select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='contextPath'][not(@qualifier)]" 
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
    >
        <xsl:value-of
            select="confman:getProperty('solr.server')"
        />
        <xsl:text>/search/</xsl:text>
    </xsl:variable>
    
    <xsl:variable
        name="debug"
        select="'false'"
    />
    
    <xsl:variable
        name="currentViewHandle"
        select="substring-after(/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='focus' and @qualifier='container'],'hdl:')"
    />
    
    <xsl:variable
        name="communityType"        
    >
        <!-- if we are looking at community trail position = 2
        then @target is not set (is null) therefore we select community type out of
        current object which at position 2 is always a community
        -->
        <xsl:choose>
            <xsl:when
                test="/dri:document/dri:meta/dri:pageMeta/dri:trail[2][not(@target)]"
            >
                <xsl:value-of
                    select="document(concat('cocoon:///metadata/handle/',$currentViewHandle,'/mets.xml?sections=dmdSec'))/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='type']"       
                />
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of
                    select="document(concat('cocoon:///metadata',substring-after(/dri:document/dri:meta/dri:pageMeta/dri:trail[2]/@target,$contextPath),'/mets.xml?sections=dmdSec'))/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='type']"       
                />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
    
    <xsl:variable
        name="level"
    >
        <xsl:value-of select="count(/dri:document/dri:meta/dri:pageMeta/dri:trail)" />
    </xsl:variable>
    
    <xsl:variable
        name="zblUrl"
    >
        <xsl:text>https://zbmath.org/?q=an:</xsl:text>
    </xsl:variable>
    
    <xsl:variable
        name="amsUrl"
    >
        <xsl:text>http://www.ams.org/mathscinet-getitem?mr=</xsl:text>
    </xsl:variable>
        
    <xsl:variable 
        name="numdamUrl"
        select="'http://www.numdam.org/item?id='" 
    />
</xsl:stylesheet>