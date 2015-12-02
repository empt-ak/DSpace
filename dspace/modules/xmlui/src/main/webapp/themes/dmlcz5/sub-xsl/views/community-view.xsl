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
        </div>        
        <div class="row">
            <div class="col-sm-2">
                <img height="200" alt="page.community.serial.thumbnail" i18n:attr="alt" class="img-responsive">
                    <xsl:attribute name="src">
                        <xsl:value-of select="document($externalMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href" />
                    </xsl:attribute>
                </img>
            </div>
            <div class="col-sm-10">
                <dl class="dl-horizontal">
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='issn']"
                    >
                        <xsl:for-each
                            select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='issn']"
                        >
                            <dt class="col-sm-3">
                                <i18n:text>page.community.serial.issn</i18n:text>
                            </dt>
                            <dd class="col-sm-9">
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
                        <dt class="col-sm-3">
                            <i18n:text>page.community.serial.publisher</i18n:text>
                        </dt>
                        <dd class="col-sm-9">
                            <xsl:value-of
                                select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher'][1]"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='place']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.community.serial.publicationplace</i18n:text>
                        </dt>
                        <dd class="col-sm-9">
                            <xsl:value-of
                                select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='place']"
                            />
                        </dd>
                    </xsl:if>                    
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='uri']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.community.serial.website</i18n:text>
                        </dt>
                        <dd class="col-sm-9">
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
                        </dd>
                    </xsl:if>                     
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.community.serial.description</i18n:text>
                        </dt>
                        <dd class="col-sm-9">
                            <xsl:value-of
                                select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date' and @qualifier='start']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.community.serial.published</i18n:text>
                        </dt>
                        <dd class="col-sm-9">
                            <xsl:value-of
                                select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date' and @qualifier='start']"
                            />
                            <xsl:text>–</xsl:text>
                            <xsl:choose>
                                <xsl:when test="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date' and @qualifier='end']">
                                    <xsl:value-of
                                        select="document($externalMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date' and @qualifier='end']"
                                    />
                                </xsl:when>
                                <xsl:otherwise>
                                    <i18n:text>page.community.published.untilnow</i18n:text>
                                </xsl:otherwise>
                            </xsl:choose>
                        </dd>
                    </xsl:if>  
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
                        <div class="table-responsive">
                            <table class="table table-hover">
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
                                            <!--                                            <a>
                                            <xsl:attribute 
                                                name="href"
                                            >
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/@OBJID"
                                                />
                                            </xsl:attribute>-->
                                            <i18n:text>page.community.serial.label.volume</i18n:text>
                                            <xsl:text> </xsl:text>
                                            <strong>
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                                />
                                            </strong>
                                            <!--                                            </a> -->
                                            <xsl:text> (</xsl:text>
                                            <xsl:value-of
                                                select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
                                            />
                                            <xsl:text>)</xsl:text>
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
                                                        select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='name']"
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
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-hover">
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
                                            <!--                                            <a>
                                            <xsl:attribute 
                                                name="href"
                                            >
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/@OBJID"
                                                />
                                            </xsl:attribute>-->
                                            <i18n:text>page.community.serial.label.volume</i18n:text>
                                            <xsl:text> </xsl:text>
                                            <strong>
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                                />
                                            </strong>
                                            <!--                                            </a> -->
                                            <xsl:text> (</xsl:text>
                                            <xsl:value-of
                                                select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
                                            />
                                            <xsl:text>)</xsl:text>
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
                                                        select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='name']"
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
                </div>
            </xsl:when><!-- serial list of volume end -->
            <xsl:when
                test="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.community-volumes']"
            >
                <!-- for celebrity -->
                <h3>
                    <i18n:text>page.community.celebrity.collections</i18n:text>
                </h3>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-condensed">
                            <xsl:for-each
                                select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.community-volumes']/dri:reference"
                            >
                                <xsl:variable
                                    name="volumeMetadata"
                                >
                                    <xsl:text>cocoon://</xsl:text>
                                    <xsl:value-of select="./@url"/>
                                    <xsl:text>?sections=dmdSec</xsl:text>
                                </xsl:variable>
                                <tr>
                                    <td class="col-md-1">
                                &#160;
                                    </td>
                                    <td class="col-md-11">
                                        <a>
                                            <xsl:attribute
                                                name="href"
                                            >
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/@OBJID"
                                                />
                                            </xsl:attribute>
                                            <xsl:value-of
                                                select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                            />
                                        </a>
                                        <div class="celebrity-collection-entry">                                                
                                            <span class="h5 celebrity-volume-abstract">
                                                <xsl:value-of
                                                    select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                                />
                                            </span>
                                        </div>                                                              
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </table>
                    </div>
                </div>
            </xsl:when>
            <xsl:when
                test="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.collection-list']"
            >
                <xsl:choose>
                    <xsl:when
                        test="$communityType='monograph'"
                    >
                        <h3>
                            <i18n:text>page.community.monograph.archive</i18n:text>
                        </h3>
                        <div class="row">
                            <div class="col-md-12">
                                <table class="table table-condensed monograph-table">
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
                        test="$communityType='proceedings'"
                    >
                        <h3>
                            <i18n:text>page.community.proceedings.archive</i18n:text>
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
                                                <xsl:value-of
                                                    select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='acronym']" 
                                                />
                                                <xsl:text>:</xsl:text>
                                            </td>
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
                                                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='formalized']"
                                                    />
                                                </a>                                                
                                            </td>                                            
                                        </tr>
                                    </xsl:for-each>
                                </table>
                            </div>
                        </div>
                    </xsl:when>
                    <xsl:otherwise>
                        <div class="alert alert-danger no-entry-alert">
                            <h2>
                                <i18n:text>page.community.unknown.head</i18n:text>
                            </h2>
                            <pre>
                                <xsl:value-of
                                    select="$communityType"
                                />
                            </pre>
                        </div>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <div class="row">
                    <div class="col-md-12 alert alert-info no-entry-alert">
                        <h2>
                            <i18n:text>page.community.noentry.head</i18n:text>
                        </h2>
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
            <xsl:value-of select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.volume']/dri:reference[@type='DSpace Community']/@url"/>
            <xsl:text>?sections=dmdSec</xsl:text>
        </xsl:variable>
        <xsl:variable
            name="parentVolumeMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.parent-community']/dri:reference[@type='DSpace Community']/@url"/>
            <xsl:text>?sections=dmdSec</xsl:text>
        </xsl:variable>
        <xsl:choose>
            <xsl:when
                test="$communityType = 'serial'"
            >
                <div class="row">
                    <div class="col-md-12">
                        <h2 class="volume-title">
                            <i18n:text>page.community.serial.volume.volume</i18n:text>
                            <xsl:value-of
                                select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                            />
                            <xsl:text>, </xsl:text>
                            <small> 
                                <xsl:value-of
                                    select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
                                />
                                <xsl:text> (</xsl:text>
                                <xsl:value-of
                                    select="/dri:document/dri:meta/dri:pageMeta/dri:trail[2]"
                                />
                                <xsl:text>)</xsl:text>
                            </small>
                        </h2>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                        <xsl:choose>
                            <xsl:when test="document($volumeMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href">
                                <img alt="Thumbnail">
                                    <xsl:attribute name="src">
                                        <xsl:value-of select="document($volumeMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href"/>
                                    </xsl:attribute>
                                </img>
                            </xsl:when>
                            <xsl:otherwise>
                                <img alt="Thumbnail" class="img-responsive">
                                    <xsl:attribute name="data-src">
                                        <xsl:text>holder.js/100px200</xsl:text>
                                        <xsl:text>?text=No Thumbnail</xsl:text>
                                    </xsl:attribute>
                                </img>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                    <div class="col-md-3">
                        <div class="list-group">
                            <a class="list-group-item active">
                                <i18n:text>page.community.serial.volume.issues</i18n:text>
                            </a>
                            <xsl:for-each
                                select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.issues']/dri:reference[@type='DSpace Collection']"
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
                                    <i18n:text>page.community.serial.volume.issue</i18n:text>
                                    <xsl:value-of
                                        select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='name']"
                                    />
                                </a>
                            </xsl:for-each>
                        </div>
                    </div>
                    <div class="col-md-7">
                        <canvas id="myChart">&#160;</canvas>
                    </div>            
                </div>
            </xsl:when>
            <xsl:when
                test="$communityType = 'celebrity'"
            >
                <div class="row">
                    <div class="col-md-2">
                        <xsl:choose>
                            <xsl:when test="document($volumeMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href">
                                <img alt="Thumbnail">
                                    <xsl:attribute name="src">
                                        <xsl:value-of select="document($volumeMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href"/>
                                    </xsl:attribute>
                                </img>
                            </xsl:when>
                            <xsl:otherwise>
                                <img alt="Thumbnail" class="img-responsive">
                                    <xsl:attribute name="data-src">
                                        <xsl:text>holder.js/100px200</xsl:text>
                                        <xsl:text>?text=No Thumbnail</xsl:text>
                                    </xsl:attribute>
                                </img>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                    <div class="col-md-10">
                        <div class="row">
                            <div class="col-md-12">
                                <h2 class="volume-title">
                                    <xsl:value-of
                                        select="document($parentVolumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                    />
                                </h2>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <h3 class="volume-title">
                                    <xsl:value-of
                                        select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                    />
                                </h3>
                            </div>
                        </div>                        
                        <xsl:if
                            test="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                        >
                            <div class="row">
                                <div class="col-md-12">
                                    <xsl:value-of
                                        select="document($volumeMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                    />                                  
                                </div>
                            </div>
                        </xsl:if>
                    </div>
                </div>
                <h3>
                    <i18n:text>page.community.celebrity.volume.archive</i18n:text>
                </h3>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-condensed">
                            <xsl:for-each
                                select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CommunityAspect.referenceSet.issues']/dri:reference[@type='DSpace Collection']"
                            >
                                <xsl:variable 
                                    name="issueMetadata"
                                >
                                    <xsl:text>cocoon://</xsl:text>
                                    <xsl:value-of select="./@url"/>
                                    <xsl:text>?sections=dmdSec</xsl:text>
                                </xsl:variable>
                                <tr>
                                    <td class="col-md-1">
                                        <xsl:value-of
                                            select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='position']"
                                        />
                                    </td>
                                    <td>
                                        <xsl:value-of
                                            select="document($issueMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date' and @qualifier='created']"
                                        />
                                    </td>
                                    <td>
                                        <a>
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
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </table>
                    </div>
                </div>
            </xsl:when>
            <xsl:otherwise>
                wrong type
            </xsl:otherwise>
        </xsl:choose>        
    </xsl:template>
</xsl:stylesheet>
