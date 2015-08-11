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
        name="buildBreadcrumb"
    >
        <xsl:if 
            test="count(/dri:document/dri:meta/dri:pageMeta/dri:trail) > 1"
        >
            <nav class="row">
                <div class="col-md-12">
                    <ol class="breadcrumb">
                        <xsl:for-each
                            select="/dri:document/dri:meta/dri:pageMeta/dri:trail"
                        >
                            <li>
                                <xsl:if
                                    test="position() = 1">
                                    <i class="glyphicon glyphicon-home" />&#160;
                                </xsl:if>
                                <xsl:if
                                    test="position() = last()"
                                >
                                    <xsl:attribute
                                        name="class"
                                    >
                                        <xsl:text>active</xsl:text>
                                    </xsl:attribute>
                                </xsl:if>
                                <xsl:choose>
                                    <xsl:when
                                        test="position() != last()"
                                    >
                                        <a>
                                            <xsl:attribute
                                                name="href"
                                            >
                                                <xsl:value-of
                                                    select="./@target"
                                                />
                                            </xsl:attribute>                                                                       
                                            <xsl:value-of
                                                select="."
                                            />
                                        </a>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of
                                            select="."
                                        />
                                    </xsl:otherwise>
                                </xsl:choose>                                 
                            </li>
                        </xsl:for-each>
                    </ol>
                </div>            
            </nav>
        </xsl:if>        
    </xsl:template>
    
    <xsl:template 
        name="buildPUN"
    > 
        <div class="pun">      
            <ul class="pager">
                <li class="previous disabled">
                    <a href="#">
                        <i18n:text>navigation.pun.previous</i18n:text>
                    </a>
                </li>
                <!--            <li class="disabled">
                    <a href="#">
                        <i18n:text>navigation.pun.up</i18n:text>
                    </a>
                </li>-->
                <li class="next disabled">
                    <a href="#">
                        <i18n:text>navigation.pun.next</i18n:text>
                    </a>
                </li>
            </ul>
        </div>
    </xsl:template>
    
    <xsl:template 
        name="buildFooter"
    >
        <div class="col-md-8 col-md-offset-2">
            <p class="footer-text">
                &#169; 2015 <a href="#">Institute of Mathematics ASCR</a>
            </p>                    
        </div>
    </xsl:template>
</xsl:stylesheet>
