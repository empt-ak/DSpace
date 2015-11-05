<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-html-head.xsl
    Created on : July 24, 2015, 10:02 AM
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
    
    <xsl:template 
        name="buildBodyHead"
    >
        <xsl:if
            test="contains($debug,'true')"
        >
            <div class="alert alert-danger">
                <xsl:value-of select="$solrServer" />
                <br />
                
                <xsl:value-of select="$communityType" />
                <br/>
                <xsl:variable name="solrQuery">
                    <xsl:text>select?q=*%3A*&amp;fq=location%3A</xsl:text>
                    <xsl:call-template
                    name="getSolrLocation"
                />
                <xsl:text>&amp;rows=0&amp;wt=xml&amp;facet=true&amp;facet.field=msc_keyword</xsl:text>
                </xsl:variable>
                <xsl:value-of
                    select="$solrQuery"
                    />
            </div>
        </xsl:if>
        <div class="row">
            <div class="col-md-2 hidden-sm hidden-xs">
                <a>
                    <xsl:attribute
                        name="href"
                    >
                        <xsl:value-of
                            select="/dri:document/dri:meta/dri:pageMeta/dri:trail[1]/@target"
                        />
                    </xsl:attribute>
                    <img alt="digilibcz banner" class="digilib-banner">
                        <xsl:attribute
                            name="src"
                        >
                            <xsl:value-of
                                select="$resourcePath"
                            />
                            <xsl:text>/img/digilib-logo.gif</xsl:text>
                        </xsl:attribute>
                    </img>
                </a>            
            </div>
            <div class="col-md-10">
                <div class="page-header">
                    <a class="digilib-link">
                        <xsl:attribute
                            name="href"
                        >
                            <xsl:value-of
                                select="$contextPath"
                            />
                        </xsl:attribute>
                        <h1>
                            <i18n:text>page.head.title</i18n:text>
                        </h1>
                    </a>                 
                </div>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
