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
                    <div class="row digilib-content-body">
                        <div class="col-md-2 left-sidebar">
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
                    <footer class="row" id="footer">
                        <xsl:call-template name="buildFooter" />
                    </footer>
                </div>
                <xsl:call-template name="buildBodyFooter" />
            </body>
        </html>        
    </xsl:template>
</xsl:stylesheet>