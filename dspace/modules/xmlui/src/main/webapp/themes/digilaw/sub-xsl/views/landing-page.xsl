<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : landing-page.xsl
    Created on : July 24, 2015, 10:44 AM
    Author     : emptak
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                xmlns:dri="http://di.tamu.edu/DRI/1.0/"
                xmlns:mets="http://www.loc.gov/METS/"
                xmlns:dim="http://www.dspace.org/xmlns/dspace/dim"
                xmlns:xlink="http://www.w3.org/TR/xlink/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:atom="http://www.w3.org/2005/Atom"
                xmlns:ore="http://www.openarchives.org/ore/terms/"
                xmlns:oreatom="http://www.openarchives.org/ore/atom/"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:encoder="xalan://java.net.URLEncoder"
                xmlns:util="org.dspace.app.xmlui.utils.XSLUtils"
                xmlns:confman="org.dspace.core.ConfigurationManager"
                exclude-result-prefixes="xalan encoder i18n dri mets dim xlink xsl util confman">
    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    
    
    <xsl:template match="dri:body/dri:div[@id='cz.muni.ics.digilaw.aspects.MainAspect.div.landing-page']">
        <div class="jumbotron">
            <p class="lead">
                <i18n:text>page.landing.jubotron</i18n:text>
            </p>
        </div>
        
        <ul class="nav nav-tabs" role="tablist">
            <xsl:for-each select="./dri:div[@id='cz.muni.ics.digilaw.aspects.MainAspect.div.topcoms']">
                <li class="nav-item">
                    <a data-toggle="tab" role="tab" href="#{./@rend}">
                        <xsl:attribute
                            name="class"
                        >
                            <xsl:choose>
                                <xsl:when
                                    test="position() = 1"
                                >
                                    <xsl:text>nav-link active</xsl:text>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:text>nav-link</xsl:text>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:attribute>
                        <i18n:text>
                            <xsl:value-of
                                select="concat('page.landing.category.',./@rend)"
                            />
                        </i18n:text>
                    </a>
                </li>
            </xsl:for-each>
        </ul>
        <div class="tab-content">
            <xsl:for-each select="./dri:div[@id='cz.muni.ics.digilaw.aspects.MainAspect.div.topcoms']">
                <div role="tabpanel">
                    <xsl:attribute name="class">
                        <xsl:choose>
                            <xsl:when test="position() = 1">
                                <xsl:text>tab-pane active</xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>tab-pane</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <xsl:attribute name="id">
                        <xsl:value-of select="./@rend" />
                    </xsl:attribute>
					<h2>
						<!--
                        <i18n:text>
                            <xsl:value-of
                                select="concat('page.landing.category.',./@rend)"
                            />
						</i18n:text>
						-->
                    </h2>
                    <ul class="media-list section-list">
                        <xsl:for-each select="dri:referenceSet[@id='cz.muni.ics.digilaw.aspects.MainAspect.referenceSet.topcom-listing']/dri:reference">
                            <xsl:variable name="externalMetadataURL">
                                <xsl:text>cocoon://</xsl:text>
                                <xsl:value-of select="@url"/>
                                <xsl:text>?sections=dmdSec</xsl:text>
                            </xsl:variable>
                            <li class="media">
                                <div class="media-left hidden-sm-down">
                                    <xsl:choose>
                                        <xsl:when 
                                            test="document(concat('cocoon://',@url,'?sections=fileSec'))/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href"
                                        >
                                            <img height="100" alt="page.landing.thumbnail" i18n:attr="alt" class="img-responsive"><!-- width="70" -->       
                                                <xsl:attribute name="src">
                                                    <xsl:value-of 
                                                        select="document(concat('cocoon://',@url,'?sections=fileSec'))/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href" 
                                                    />
                                                </xsl:attribute>
                                            </img>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <!-- TODO -->
											<!--
                                            <img alt="page.general.thumbnail" class="img-responsive" i18n:attribute="alt">
                                                <xsl:attribute name="data-src">
                                                    <xsl:text>holder.js/100x100</xsl:text>
                                                    <xsl:text>?text=No Thumbnail</xsl:text>
                                                </xsl:attribute>
											</img>
											-->
                                        </xsl:otherwise>
                                    </xsl:choose> 
                                </div>
                                <div class="media-body">
                                    <h4 class="media-heading">
                                        <a href="{document($externalMetadataURL)/mets:METS/@OBJID}">
                                            <xsl:value-of select="document($externalMetadataURL)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']" />
                                        </a>
                                    </h4>
									<!--
                                    <xsl:choose>
                                        <xsl:when
                                            test="string-length(document($externalMetadataURL)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']) &gt; 410"
                                        >
                                            <xsl:value-of select="substring(document($externalMetadataURL)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract'],0,410)" />
                                            <i18n:text>page.landing.ellipsis</i18n:text>
                                            <xsl:text> </xsl:text>
                                            <a href="{document($externalMetadataURL)/mets:METS/@OBJID}">
                                                <i18n:text>page.landing.more</i18n:text>
                                                <xsl:text> </xsl:text>
                                                <i class="fa fa-arrow-circle-right"></i>
                                            </a>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="document($externalMetadataURL)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']" />
                                        </xsl:otherwise>
									</xsl:choose>
									-->
                                </div>                                
                            </li>
                        </xsl:for-each>
                    </ul>
                </div>
            </xsl:for-each>
        </div>
    </xsl:template>
</xsl:stylesheet>
