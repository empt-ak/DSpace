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
    
    <xsl:template 
        name="buildBodyFooter"
    >
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">&#160;</script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script>
            <xsl:attribute 
                name="src"
            >
                <xsl:value-of 
                    select="$contextPath" 
                />
                <xsl:text>/themes/</xsl:text>
                <xsl:value-of 
                    select="$theme" 
                />
                <xsl:text>/js/bootstrap.min.js</xsl:text>
            </xsl:attribute>
                    &#160;
        </script>
        <script>
            <xsl:attribute 
                name="src"
            >
                <xsl:value-of 
                    select="$contextPath" 
                />
                <xsl:text>/themes/</xsl:text>
                <xsl:value-of 
                    select="$theme" 
                />
                <xsl:text>/js/digilaw.min.js</xsl:text>
            </xsl:attribute>
                    &#160;
        </script>
        <script>
            <xsl:attribute 
                name="src"
            >
                <xsl:value-of 
                    select="$contextPath" 
                />
                <xsl:text>/themes/</xsl:text>
                <xsl:value-of 
                    select="$theme" 
                />
                <xsl:text>/js/holder.min.js</xsl:text>
            </xsl:attribute>
                    &#160;
        </script>          
    </xsl:template>

</xsl:stylesheet>
