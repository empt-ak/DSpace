<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-layout.xsl
    Created on : July 24, 2015, 9:32 AM
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

    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
   
    <xsl:template match="dri:document">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;</xsl:text>
        <html>
            <head>
                <!-- in page-html-head.xsl -->
                <xsl:call-template name="buildHTMLHead" />
            </head>
            <body>
                <div class="container-fluid">
                    <!-- in page-body-head.xsl -->
                    <xsl:call-template name="buildBodyHead" />
                    <!-- in page-body-navigation.xsl -->
                    <xsl:call-template name="buildNavigation" />
                    <div class="row">
                        <div class="col-md-2">
                            <xsl:call-template name="buildLeftSidebar" />
                        </div>
                        <div class="col-md-8">
                            <xsl:call-template name="buildBreadcrumb" />
                            <xsl:apply-templates />
                        </div>
                        <div class="col-md-2">
                            <xsl:call-template name="buildRightSidebar" />
                        </div>
                    </div>
                    <footer class="row">
                        <xsl:call-template name="buildFooter" />
                    </footer>
                </div>
                <xsl:call-template name="buildBodyFooter" />
            </body>
        </html>        
    </xsl:template>
</xsl:stylesheet>