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
    
    
    <xsl:template match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.div.community-view' and @rend='top-comm']">
        <xsl:variable
            name="externalMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of select="./dri:referenceSet[@n='current-community']/dri:reference/@url"/>
            <xsl:text>?sections=dmdSec,fileSec</xsl:text>
        </xsl:variable>
        <div class="row">
            <div class="col-md-12">
                <h2 class="journal-title">
                    <xsl:value-of
                        select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
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
            <div class="col-md-2">
                <img height="200" width="140" alt="page.community.serial.thumbnail" i18n:attr="alt">
                    <xsl:attribute name="src">
                        <xsl:value-of select="document($externalMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href" />
                    </xsl:attribute>
                </img>
            </div>
            <div class="col-md-10">
                <dl class="dl-horizontal">
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='issn']"
                    >
                        <xsl:for-each
                            select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='issn']"
                        >
                            <dt>
                                <i18n:text>page.community.serial.issn</i18n:text>
                            </dt>
                            <dd>
                                <xsl:value-of
                                    select="./text()"
                                />
                            </dd>
                        </xsl:for-each>                        
                    </xsl:if>
                    <!-- TODO naozaj sa zobrazuje iba jeden ? [prvy]-->
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher']"
                    >        
                        <dt>
                            <i18n:text>page.community.serial.publisher</i18n:text>
                        </dt>
                        <dd>
                            <xsl:value-of
                                select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher'][1]"
                            />
                        </dd>
                    </xsl:if>
                    <dt>
                        <i18n:text>page.community.serial.publicationplace</i18n:text>
                    </dt>
                    <dd>!Praha, Czech Republic</dd>
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='uri']"
                    >
                        <dt>
                            <i18n:text>page.community.serial.website</i18n:text>
                        </dt>
                        <dd>
                            <a target="_blank">
                                <xsl:attribute
                                    name="href"
                                >
                                    <xsl:value-of
                                        select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='uri']"
                                    />
                                </xsl:attribute>
                                <xsl:value-of
                                    select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='uri']"
                                />
                            </a>
                            <xsl:text> </xsl:text>
                            <span class="glyphicon glyphicon-new-window">&#160;</span>
                        </dd>
                    </xsl:if>                     
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                    >
                        <dt>
                            <i18n:text>page.community.serial.description</i18n:text>
                        </dt>
                        <dd>
                            <xsl:value-of
                                select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                            />
                        </dd>
                    </xsl:if>                    
                    <dt>
                        <i18n:text>page.community.serial.published</i18n:text>
                    </dt>
                    <dd>1965 - now</dd>
                </dl>
            </div>
        </div>
        <xsl:choose>
            <!--
            this is little bit tricky. if there is any volume (for serials) then it has to be in lef
            table. if there is none this node branch does not exist and it may be
            the mono/cele/proc type of community which has collections as children
            -->
            <xsl:when
                test="./dri:div[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.div.sub-table' and @rend='left']"
            >
                <h3>
                    <i18n:text>page.community.serial.label.volumes</i18n:text>
                </h3>
                <div class="row">
                    <div class="col-md-6">
                        <table class="table table-hover table-condensed">
                            <xsl:for-each
                                select="./dri:div[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.div.sub-table' and @rend='left']/dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.volume']"
                            >
                                <xsl:variable
                                    name="volumeMetadata"
                                >
                                    <xsl:text>cocoon://</xsl:text>
                                    <xsl:value-of select="./dri:reference[@type='DSpace Community']/@url"/>
                                    <xsl:text>?sections=dmdSec</xsl:text>
                                </xsl:variable>
                                <tr>
                                    <td>
                                 &#160;
                                    </td>
                                    <td>
                                        <a>
                                            <xsl:attribute 
                                                name="href"
                                            >
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/@OBJID"
                                                />
                                            </xsl:attribute>
                                            <i18n:text>page.community.serial.label.volume</i18n:text>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of
                                                select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                            />
                                        </a> 
                                        <xsl:text> ( </xsl:text>
                                        <xsl:value-of
                                            select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
                                        />
                                        <xsl:text> )</xsl:text>
                                    </td>
                                    <td>
                                        <xsl:for-each
                                            select="./dri:reference[@type='DSpace Collection']"
                                        >
                                            <xsl:variable
                                                name="issueMetadata"
                                            >
                                                <xsl:text>cocoon://</xsl:text>
                                                <xsl:value-of select="./@url"/>
                                                <xsl:text>?sections=dmdSec</xsl:text>
                                            </xsl:variable>
                                            <a>
                                                <xsl:attribute
                                                    name="href"
                                                >
                                                    <xsl:value-of
                                                        select="document($issueMetadata)/mets:METS/@OBJID"
                                                    />
                                                </xsl:attribute>  
                                                <xsl:value-of
                                                    select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                                />
                                                <xsl:text> </xsl:text>                                      
                                            </a>
                                        </xsl:for-each>
                                    </td>
                                </tr>
                            </xsl:for-each>                    
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table table-hover table-condensed">
                            <xsl:for-each
                                select="./dri:div[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.div.sub-table' and @rend='right']/dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.volume']"
                            >
                                <xsl:variable
                                    name="volumeMetadata"
                                >
                                    <xsl:text>cocoon://</xsl:text>
                                    <xsl:value-of select="./dri:reference[@type='DSpace Community']/@url"/>
                                    <xsl:text>?sections=dmdSec</xsl:text>
                                </xsl:variable>
                                <tr>
                                    <td>
                                 &#160;
                                    </td>
                                    <td>
                                        <a>
                                            <xsl:attribute 
                                                name="href"
                                            >
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/@OBJID"
                                                />
                                            </xsl:attribute>
                                            <i18n:text>page.community.serial.label.volume</i18n:text>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of
                                                select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                            />
                                        </a> 
                                        <xsl:text> ( </xsl:text>
                                        <xsl:value-of
                                            select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
                                        />
                                        <xsl:text> )</xsl:text>
                                    </td>
                                    <td>
                                        <xsl:for-each
                                            select="./dri:reference[@type='DSpace Collection']"
                                        >
                                            <xsl:variable
                                                name="issueMetadata"
                                            >
                                                <xsl:text>cocoon://</xsl:text>
                                                <xsl:value-of select="./@url"/>
                                                <xsl:text>?sections=dmdSec</xsl:text>
                                            </xsl:variable>
                                            <a>
                                                <xsl:attribute
                                                    name="href"
                                                >
                                                    <xsl:value-of
                                                        select="document($issueMetadata)/mets:METS/@OBJID"
                                                    />
                                                </xsl:attribute>  
                                                <xsl:value-of
                                                    select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                                />
                                                <xsl:text> </xsl:text>                                      
                                            </a>
                                        </xsl:for-each>
                                    </td>
                                </tr>
                            </xsl:for-each>                    
                        </table>
                    </div>
                </div>
            </xsl:when><!-- serial list of volume end -->
            <xsl:when
                test="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.collection-list']"
            >
                <xsl:variable
                    name="comunityType"
                >
                    <xsl:value-of
                        select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='type']"
                    />
                </xsl:variable>
                <xsl:choose>
                    <xsl:when
                        test="$comunityType='monograph' or $comunityType='proceedings'"
                    >
                        <h3>
                            Archive
                        </h3>
                        <div class="row">
                            <div class="col-md-12">
                                <table class="table table-condensed">
                                    <xsl:for-each
                                        select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.collection-list']/dri:reference"
                                    >
                                        <xsl:variable
                                            name="collectionMetadata"
                                        >
                                            <xsl:text>cocoon://</xsl:text>
                                            <xsl:value-of select="./@url"/>
                                            <xsl:text>?sections=dmdSec</xsl:text>
                                        </xsl:variable>
                                        <tr>
                                            <td>
                                                <a>
                                                    <xsl:attribute
                                                        name="href"
                                                    >
                                                        <xsl:value-of
                                                            select="document($collectionMetadata)/mets:METS/@OBJID"
                                                        />
                                                    </xsl:attribute>
                                                    <xsl:value-of
                                                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @language]"
                                                    />
                                                    <xsl:value-of
                                                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='alternative' and @language]"
                                                    />
                                                </a>
                                                
                                            </td>                                            
                                        </tr>
                                    </xsl:for-each>
                                </table>
                            </div>
                        </div>
                    </xsl:when>
                    <xsl:when
                        test="$comunityType='celebrity'"
                    >
                        <h3>
                            Colllections
                        </h3>
                        <div class="row">
                            <div class="col-md-12">
                                <table class="table table-condensed">
                                    <xsl:for-each
                                        select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.collection-list']/dri:reference"
                                    >
                                        <tr>
                                            <td>x</td>
                                            <td>a</td>
                                        </tr>
                                    </xsl:for-each>
                                </table>
                            </div>
                        </div>
                    </xsl:when>
                    <xsl:otherwise>
                        <div class="alert alert-danger no-entry-alert">
                            <h2>Unknown community type</h2>
                            <pre>
                                <xsl:value-of
                                    select="$comunityType"
                                />
                            </pre>
                        </div>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <div class="row">
                    <div class="col-md-12 alert alert-info no-entry-alert">
                        <h2>no entries</h2>
                        this hue hue has noe hoe hoe
                    </div>
                </div>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.div.community-view' and @rend='volume']">
        <xsl:variable
            name="volumeMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of select="./dri:referenceSet/dri:reference[@type='DSpace Community']/@url"/>
            <xsl:text>?sections=dmdSec,fileSec</xsl:text>
        </xsl:variable>
        <div class="row">
            <div class="col-md-12">
                <h2 class="volume-title">
                    <xsl:value-of
                        select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
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
            <div class="col-md-2">
                <img height="200" width="140" alt="page.community.serial.volume.thumbnail" i18n:attr="alt">
                    <xsl:attribute name="src">
                        <xsl:value-of select="document($volumeMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href" />
                    </xsl:attribute>
                </img>
            </div>
            <div class="col-md-3">
                <div class="list-group">
                    <a class="list-group-item active">
                        <i18n:text>page.community.serial.volume.issues</i18n:text>
                    </a>
                    <xsl:for-each
                        select="./dri:referenceSet/dri:reference[@type='DSpace Collection']"
                    >
                        <xsl:variable 
                            name="issueMetadata"
                        >
                            <xsl:text>cocoon://</xsl:text>
                            <xsl:value-of select="./@url"/>
                            <xsl:text>?sections=dmdSec</xsl:text>
                        </xsl:variable>
                        <a class="list-group-item">
                            <xsl:attribute
                                name="href">
                                <xsl:value-of
                                    select="document($issueMetadata)/mets:METS/@OBJID"
                                />
                            </xsl:attribute>
                            <xsl:value-of
                                select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                            />
                        </a>
                    </xsl:for-each>
                </div>                
                <!--                <div class="col-md-5" id="myPie">
                    &#160;
                     http://stackoverflow.com/questions/17626555/responsive-d3-chart
                </div>-->
            </div>
            <div class="col-md-7">
                <canvas id="myChart">&#160;</canvas>
            </div>            
        </div>
    </xsl:template>
</xsl:stylesheet>
