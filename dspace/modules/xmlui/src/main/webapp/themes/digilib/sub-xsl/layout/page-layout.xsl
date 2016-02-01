<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-layout.xsl
    Created on : July 24, 2015, 9:32 AM
    Author     : emptak
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:dri="http://di.tamu.edu/DRI/1.0/"                
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"               
                xmlns="http://www.w3.org/1999/xhtml"
                exclude-result-prefixes="dri xsl">

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
   
    <xsl:template match="dri:document">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;</xsl:text>
        <html>
            <head>
                <!-- in page-html-head.xsl -->
                <xsl:call-template name="html-head" />
            </head>
            <body>
                <xsl:call-template name="navigation" />
                <div class="container-fluid body-content">  
                    <xsl:if
                        test="count(/dri:document/dri:meta/dri:pageMeta/dri:trail) = 1"
                    >                   
                        <xsl:call-template name="body-head" />
                        <xsl:call-template name="search-panel" />
                    </xsl:if>
                    <div class="row content-body">
                        <div class="col-md-offset-3 col-xl-offset-2 col-md-6 col-xl-8">
                            <xsl:call-template name="breadcrumb" />
                            <xsl:apply-templates />
                        </div>
                    </div>
                    
                    <xsl:call-template name="discovery-panel" />
                    
                    <footer class="row">
                        <xsl:call-template name="footer" />
                    </footer>
                </div>
                <xsl:call-template name="buildBodyFooter" />
            </body>
        </html>        
    </xsl:template>
</xsl:stylesheet>