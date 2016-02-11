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
    
    <xsl:template 
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.CollectionAspect.div.collection-view' and @n='collection-view']"
    >
        <xsl:variable
            name="collectionMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of 
                select="./dri:referenceSet[@n='current-collection']/dri:reference[@type='DSpace Collection']/@url"
            />
            <xsl:text>?sections=dmdSec</xsl:text>
        </xsl:variable>        
        <xsl:choose>
            <xsl:when
                test="$communityType = 'monograph'"
            >
                <xsl:call-template
                    name="monograph"                    
                >
                    <xsl:with-param
                        name="collectionMetadata"
                        select="$collectionMetadata"
                    />
                </xsl:call-template>
            </xsl:when>
            <xsl:when
                test="$communityType = 'proceedings'"
            >
                <xsl:call-template
                    name="proceedings"                    
                >
                    <xsl:with-param
                        name="collectionMetadata"
                        select="$collectionMetadata"
                    />                   
                </xsl:call-template>
                
            </xsl:when>
            <xsl:when
                test="$communityType = 'serial'"
            >
                <xsl:call-template
                    name="serial"
                >
                    <xsl:with-param
                        name="collectionMetadata"
                        select="$collectionMetadata"
                    />
                    <xsl:with-param
                        name="issueSiblings"
                        select="./dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.CollectionAspect.referenceSet.issue-siblings']"
                    />
                    <xsl:with-param
                        name="currentIssueUrl"
                        select="./dri:referenceSet[@n='current-collection']/dri:reference[@type='DSpace Collection']/@url"
                    />
                </xsl:call-template>
            </xsl:when>
            <xsl:when
                test="$communityType = 'celebrity'"
            >
                <xsl:call-template
                    name="celebrity"
                >
                    <xsl:with-param
                        name="collectionMetadata"
                        select="$collectionMetadata"
                    />
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <div class="alert alert-danger" role="alert">
                    <strong>
                        <i18n:text>page.error.error</i18n:text>
                    </strong>
                    <xsl:text> </xsl:text>
                    <i18n:text>page.error.unknowntype</i18n:text>
                </div>
            </xsl:otherwise>
        </xsl:choose>        
    </xsl:template>
    
    <xsl:template
        name="monograph"
    >
        <xsl:param
            name="collectionMetadata"
        />
        <div class="row">
            <div class="col-xs-12">
                <h2>
                    <xsl:value-of
                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                    />
                </h2>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-2 hidden-xs-down">
                <img height="200" width="140" alt="page.collection.proceedings.thumbnail" i18n:attribute="alt">
                    <xsl:attribute name="src">
                        <xsl:value-of select="document(concat($collectionMetadata,',fileSec'))/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href" />
                    </xsl:attribute>
                </img>
            </div>
            <div class="col-sm-10">
                <dl class="dl-horizontal">
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.creator</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and not(@qualifier='author')]"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.contributor</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:for-each
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and not(@qualifier='author')]"
                            >
                                <xsl:value-of
                                    select="."
                                />
                                <xsl:text> (</xsl:text>
                                <xsl:value-of
                                    select="./@qualifier"
                                />
                                <xsl:text>)</xsl:text>
                                <xsl:if 
                                    test="position() != last()"
                                >
                                    <xsl:text>; </xsl:text>
                                </xsl:if>
                            </xsl:for-each>                            
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='language']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.language</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='language']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='issuer']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.issued</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='issuer']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.publisher</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='place']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.place</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='place']"
                            />
                            <xsl:if
                                test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
                            >
                                <xsl:text>, </xsl:text>
                                <xsl:value-of
                                    select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
                                />
                            </xsl:if>
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='printer']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.printer</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='printer']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.description</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and not(@qualifier)]"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.note</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and not(@qualifier)]"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and @qualifier='msc']"
                    >
                        <dt class="col-sm-3">
                            <i18n:text>page.collection.monograph.msc</i18n:text>
                        </dt>
                        <dd class="col-md-9">
                            <xsl:for-each
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and @qualifier='msc']"
                            >
                                <!-- href
                                        http://localhost:8080/dmlcz5/discover?filtertype=msc&filter_relational_operator=equals&filter=26A39
                                        -->
                                <a class="label label-info" href="#">
                                    <xsl:value-of
                                        select="."
                                    />
                                </a>
                                <xsl:text> </xsl:text>                              
                            </xsl:for-each>
                        </dd>
                    </xsl:if>
                </dl>
            </div>
        </div>
        <h3>
            <i18n:text>page.collection.monograph.toc</i18n:text>
        </h3>
        <div class="row">
            <div class="col-xs-11 table-responsive">
                <table class="table table-condensed table-toc">
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
                        <tr>
                            <td>
                                <xsl:value-of
                                    select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='format' and @qualifier='extent']"
                                />
                            </td>
                            <td>
                                <a href="{document($itemMetadata)/mets:METS/@OBJID}">
                                    <xsl:value-of
                                        select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                    />
                                </a>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template
        name="proceedings"
    >
        <xsl:param
            name="collectionMetadata"
        />
        <div class="row">
            <div class="col-xs-12">
                <h2>
                    <xsl:value-of
                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='acronym']"
                    />
                </h2>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <h4 class="text-muted">
                    <xsl:value-of
                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                    />
                </h4>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-2 hidden-xs-down">
                <img height="200" alt="page.collection.proceedings.thumbnail" i18n:attribute="alt" class="img-responsive">
                    <xsl:attribute name="src">
                        <xsl:value-of select="document(concat($collectionMetadata,',fileSec'))/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href" />
                    </xsl:attribute>
                </img>
            </div>
            <div class="col-sm-10">
                <dl class="dl-horizontal">                    
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='editor']"
                    >
                        <dt class="col-xs-12 col-md-3">
                            <i18n:text>page.collection.proceedings.editors</i18n:text>
                        </dt>
                        <dd class="col-xs-12 col-md-9">
                            <xsl:for-each
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='editor']"
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
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='conference' and @qualifier='organizer']"
                    >
                        <dt class="col-xs-12 col-md-3">
                            <i18n:text>page.collection.proceedings.organized</i18n:text>
                        </dt>
                        <dd class="col-xs-12 col-md-9">
                            <xsl:for-each
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='conference' and @qualifier='organizer']"
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
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='conference' and @qualifier='place']"
                    >
                        <dt class="col-xs-12 col-md-3">
                            <i18n:text>page.collection.proceedings.venue</i18n:text>
                        </dt>
                        <dd class="col-xs-12 col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='conference' and @qualifier='place']"
                            />
                            <xsl:text>, </xsl:text>
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date' and not(@qualifier)]"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher']"
                    >
                        <dt class="col-xs-12 col-md-3">
                            <i18n:text>page.collection.proceedings.publisher</i18n:text>
                        </dt>
                        <dd class="col-xs-12 col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and not(@qualifier)]"
                            />
                            <xsl:text>, </xsl:text>
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='publisher' and @qualifier='place']"
                            />
                            <xsl:text>, </xsl:text>
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date' and not(@qualifier)]"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='isbn']"
                    >
                        <dt class="col-xs-12 col-md-3">
                            <i18n:text>page.collection.proceedings.isbn</i18n:text>
                        </dt>
                        <dd class="col-xs-12 col-md-9">
                            <xsl:value-of
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='isbn']"
                            />
                        </dd>
                    </xsl:if>
                    <xsl:variable 
                        name="zblID" 
                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idzbl']" 
                    />
                    <xsl:if
                        test="$zblID"
                    >
                        <dt class="col-xs-12 col-md-3">
                            <i18n:text>page.collection.proceedings.idzbl</i18n:text>
                        </dt>
                        <dd class="col-xs-12 col-md-9">
                            <a target="_blank" href="{concat($zblUrl,substring-after($zblID,'Zbl '))}">
                                <xsl:value-of
                                    select="$zblID"
                                />
                            </a>     
                        </dd>
                    </xsl:if>                    
                    <xsl:if
                        test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and @qualifier='msc']"
                    >
                        <dt class="col-xs-12 col-md-3">
                            <i18n:text>page.collection.proceedings.msc</i18n:text>
                        </dt>
                        <dd class="col-xs-12 col-md-9">
                            <xsl:for-each
                                select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and @qualifier='msc']"
                            >
                                <a class="label label-info">
                                    <xsl:attribute
                                        name="href"
                                    >
                                        <!--
                                        http://localhost:8080/dmlcz5/discover?filtertype=msc&filter_relational_operator=equals&filter=26A39
                                        -->
                                        <xsl:text>#</xsl:text>
                                    </xsl:attribute>
                                    <xsl:value-of
                                        select="."
                                    />
                                </a>
                                <xsl:text> </xsl:text>                              
                            </xsl:for-each>
                        </dd>
                    </xsl:if>
                </dl>
            </div>
        </div>
        <h3>
            <i18n:text>page.collection.proceedings.toc</i18n:text>
        </h3>
        <div class="row">
            <div class="col-xs-11 table-responsive">
                <table class="table table-condensed table-toc">
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
                        <tr>
                            <td class="pull-sm-right hidden-xs-down">
                                <h5>
                                    <xsl:value-of
                                        select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='format' and @qualifier='extent']"
                                    />
                                </h5>
                            </td>
                            <td>
                                <h5>
                                    <a href="{document($itemMetadata)/mets:METS/@OBJID}">
                                        <xsl:value-of
                                            select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                        />
                                    </a>
                                </h5>
                                <xsl:if
                                    test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                                >
                                    <h6>
                                        <i class="fa fa-user" />
                                        <xsl:text> </xsl:text>
                                        <span class="text-muted">
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
                                    </h6>
                                </xsl:if>                                                               
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template
        name="celebrity"
    >
        <!-- 
        this level is same as item
        -->
        <xsl:apply-templates />
    </xsl:template>
    
    <xsl:template
        name="serial"
    >
        <xsl:param
            name="collectionMetadata"
        />
        <xsl:param
            name="issueSiblings"
        />
        <xsl:param
            name="currentIssueUrl"
        />
        <div class="row">
            <div class="col-xs-12 hidden-sm-down">             
                <ul class="nav nav-tabs">
                    <xsl:for-each 
                        select="$issueSiblings/dri:reference"
                    >
                        <xsl:variable
                            name="siblingMetadata"
                        >
                            <xsl:text>cocoon://</xsl:text>
                            <xsl:value-of select="./@url"/>
                            <xsl:text>?sections=dmdSec</xsl:text>
                        </xsl:variable>
                        <li class="nav-item">                            
                            <a href="{document($siblingMetadata)/mets:METS/@OBJID}" class="nav-link">
                                <xsl:if
                                    test="./@url = $currentIssueUrl"
                                >
                                    <xsl:attribute
                                        name="class"
                                    >
                                        <xsl:text>nav-link active</xsl:text>
                                    </xsl:attribute>
                                </xsl:if>
                                <i18n:text>page.community.serial.volume.issue</i18n:text>
                                <xsl:text> </xsl:text>
                                <xsl:value-of
                                    select="document($siblingMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='name']"
                                />
                            </a>
                        </li>
                    </xsl:for-each>
                </ul>
            </div>            
        </div>
        <div class="row">
            <div class="col-xs-12">
                <h2>
                    <i18n:text>page.community.serial.volume.issue</i18n:text> 
                    <xsl:value-of
                        select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and @qualifier='name']"
                    />
                    <xsl:text>, </xsl:text>
                    <small>
                        <i18n:text>page.community.serial.volume.volume</i18n:text> 
                        <xsl:value-of
                            select="/dri:document/dri:meta/dri:pageMeta/dri:trail[3]"
                        />
                        <xsl:text>, </xsl:text> 
                        <xsl:value-of
                            select="document(concat('cocoon:///metadata/handle',substring-after(/dri:document/dri:meta/dri:pageMeta/dri:trail[3]/@target,'handle'),'/mets.xml?sections=dmdSec'))/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='date']"
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
        <xsl:if
            test="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and not(@qualifier='name')]"
        >
            <div class="row">
                <div class="col-xs-12">
                    <h4 class="text-muted">
                        <xsl:value-of
                            select="document($collectionMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title' and not(@qualifier='name')]"
                        />
                    </h4>
                </div>
            </div>
        </xsl:if>       
        <div class="row offset-top-25">
            <div class="col-xs-12">
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
                                <div class="col-sm-2 hidden-xs-down text-sm-right">
                                    <h5>
                                        <xsl:value-of
                                            select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='format' and @qualifier='extent']"
                                        />
                                    </h5>
                                </div>
                                <div class="col-sm-10">
                                    <div class="disable-math">
                                        <h5>
                                            <a href="{document($itemMetadata)/mets:METS/@OBJID}">                                               
                                                <xsl:value-of
                                                    select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title'][1]"
                                                />
                                            </a>
                                        </h5>
                                    </div>
                                    
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                                    >                                    
                                        <div class="author-row">                                            
                                            <h6>
                                                <i class="fa fa-user" />
                                                <xsl:text> </xsl:text>
                                                <span class="text-muted">
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
                                            </h6>
                                        </div>
                                    </xsl:if>
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                    >
                                        <p class="issue-item-abstract">
                                            <xsl:value-of
                                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                            />
                                        </p>
                                    </xsl:if>
                                </div>                                
                            </div>
                        </li>                    
                    </xsl:for-each>
                </ul>
            </div>
        </div>
    </xsl:template>        
</xsl:stylesheet>
