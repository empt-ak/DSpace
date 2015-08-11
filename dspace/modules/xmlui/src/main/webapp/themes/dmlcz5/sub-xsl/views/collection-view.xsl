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
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    
    <xsl:template 
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.CollectionAspect.div.collection-view' and @n='collection-view']"
    >
        <xsl:variable
            name="issueMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of 
                select="./dri:referenceSet[@n='current-collection']/dri:reference[@type='DSpace Collection']/@url"
            />
            <xsl:text>?sections=dmdSec,fileSec</xsl:text>
        </xsl:variable>
        <div class="row">
            <div class="col-md-12">
                <h2 class="volume-title">
                    <xsl:value-of
                        select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                    />
                </h2>
            </div>
<!--            <div class="col-md-4">
                <xsl:call-template
                    name="buildPUN"
                />
            </div>-->
        </div>
        <div class="row">
            <div class="col-md-12">
                <ul class="list-unstyled">
                    <xsl:for-each
                        select="./dri:referenceSet[@n='item-list']/dri:reference"
                    >
                        <xsl:variable
                            name="itemMetadata"
                        >
                            <xsl:text>cocoon://</xsl:text>
                            <xsl:value-of select="./@url"/>
                            <xsl:text>?sections=dmdSec</xsl:text>
                        </xsl:variable>
                        <li>
                            <div>
                                <xsl:attribute
                                    name="class"
                                >
                                    <xsl:choose>
                                        <xsl:when
                                            test="position() != last()"
                                        >
                                            <xsl:text>row issue-item-row</xsl:text>
                                        </xsl:when>
                                        <xsl:otherwise>
                                        <xsl:text>row</xsl:text>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:attribute>                                
                                <div class="col-md-2 col-md-offset-1 text-right item-extent">
                                    <xsl:value-of
                                        select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='format' and @qualifier='extent']"
                                    />
                                </div>
                                <div class="col-md-9">
                                    <a>
                                        <xsl:attribute 
                                            name="href"
                                        >
                                            <xsl:value-of
                                                select="document($itemMetadata)/mets:METS/@OBJID"
                                            />
                                        </xsl:attribute>
                                        <h4 class="issue-item-title">
                                            <xsl:value-of
                                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title'][1]"
                                            />
                                        </h4>
                                    </a>                                    
                                    <div class="author-row">
                                        <i class="glyphicon glyphicon-user" />&#160;
                                        <span class="h5 issue-item-authors">
                                            <xsl:for-each
                                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                                            >
                                                <xsl:value-of
                                                    select="."
                                                />
                                                <xsl:if
                                                    test="position() != last()"
                                                >
                                                    <xsl:text>; </xsl:text>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </span>
                                    </div>
                                    <p class="issue-item-abstract">
                                        <!--                                        <xsl:value-of
                                            select="substring(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract'],1,200)"
                                        />-->
                                        <xsl:value-of
                                            select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                        />
                                    </p>                                    
                                </div>                                
                            </div>
                        </li>                    
                    </xsl:for-each>
                </ul>
            </div>
        </div>
    </xsl:template>
            
</xsl:stylesheet>
