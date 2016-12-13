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
                <xsl:call-template name="buildHTMLHead"/>
            </head>
            <body>
                <xsl:call-template name="navbar"/>
                <div class="container-fluid mt-4">
                    <div class="row">
                        <aside class="col-md-3 col-lg-2">
                            <xsl:call-template name="sidebar"/>
                        </aside>
                        <main class="col-md-9 col-lg-10">
                            <xsl:call-template name="breadcrumb" />
                            <xsl:apply-templates/>
                        </main>
                    </div>
                </div>
                <xsl:call-template name="footer"/>
                <xsl:if test="./dri:body/dri:div[@id='aspect.discovery.SimpleSearch.div.search']">
                    <xsl:call-template name="math-help"/>
                </xsl:if>
                <xsl:call-template name="javascript-footer"/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>