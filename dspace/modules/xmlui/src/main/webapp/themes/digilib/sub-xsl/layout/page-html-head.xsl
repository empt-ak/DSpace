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
    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    
    <xsl:template name="html-head">
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="Digital library offering an open access to the metadata and fulltext of mathematical texts published throughout history in the Czech lands" />
        <xsl:if test="//dri:metadata[@element='xhtml_head_item']">
            <xsl:value-of select="//dri:metadata[@element='xhtml_head_item']" disable-output-escaping="yes"/>
        </xsl:if>
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>
            <xsl:call-template
                name="buildTitle"
            />
        </title>

        <!-- Bootstrap -->
        <link rel="stylesheet">
            <xsl:attribute name="href">
                <xsl:value-of
                    select="$resourcePath"
                />
                <xsl:text>/css/bootstrap.min.css</xsl:text>
            </xsl:attribute>
        </link>
        <link rel="stylesheet">
            <xsl:attribute name="href">
                <xsl:value-of
                    select="$resourcePath"
                />
                <xsl:text>/css/font-awesome.min.css</xsl:text>
            </xsl:attribute>
        </link>
        <link rel="stylesheet">
            <xsl:attribute name="href">
                <xsl:value-of
                    select="$resourcePath"
                />
                <xsl:text>/css/digilibstyle.min.css</xsl:text>
            </xsl:attribute>
        </link>
        
        <xsl:for-each
            select="./dri:meta/dri:pageMeta/dri:metadata[@element='feed']"
        >
            <link href="{.}" rel="aternate" type="application/rss+xml">
                
            </link>
        </xsl:for-each>
<!--        <link rel="stylesheet">
            <xsl:attribute name="href">
                <xsl:value-of
                    select="$resourcePath"
                />
                <xsl:text>/css/jquery.bxslider.min.css</xsl:text>
            </xsl:attribute>
        </link>-->

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </xsl:template>
</xsl:stylesheet>
