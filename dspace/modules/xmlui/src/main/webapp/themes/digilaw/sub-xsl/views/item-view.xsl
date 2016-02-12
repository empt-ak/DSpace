<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : landing-page.xsl
    Created on : July 24, 2015, 10:44 AM
    Author     : emptak
    Description:
        This template is a little bit tricky,
        first we match ItemView and then recursively apply
        template for matching referenceSets. second step is important
        for matching items at collection level for celebrities.
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
        match="dri:body/dri:div[@id='cz.muni.ics.digilaw.aspects.ItemAspect.div.item-view' and @n='item-view']"
    >
        <xsl:apply-templates />
    </xsl:template>
    
    
    <xsl:template 
        match="dri:referenceSet[@id='cz.muni.ics.digilaw.aspects.ItemAspect.referenceSet.current-item' or @id='cz.muni.ics.digilaw.aspects.CollectionAspect.referenceSet.item-list']/dri:reference"
    >
        <xsl:variable
            name="itemMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of select="./@url"/>
            <xsl:text>?sections=dmdSec,fileSec</xsl:text>
        </xsl:variable>
        <div class="col-xs-12">
            <ul class="nav nav-tabs pull-sm-left pull-md-right" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" href="#entryTab" role="tab" data-toggle="tab">
                        <i18n:text>page.item.entry</i18n:text>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#fullentryTab" role="tab" data-toggle="tab">
                        <i18n:text>page.item.fullentry</i18n:text>
                    </a>
                </li>
                <!--                <li class="nav-item">
                    <a class="nav-link" href="#referencesTab" role="tab" data-toggle="tab">
                        <i18n:text>page.item.references</i18n:text>
                        <xsl:text> (</xsl:text>
                        <xsl:value-of
                            select="count(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='relation' and @qualifier='isbasedon'])"
                        />
                        <xsl:text>)</xsl:text>
                    </a>
                </li>-->
            </ul>
        </div>        
        <div class="row">
            <div class="col-xs-12">
                <div class="row">
                    <div class="col-md-12">
                        <h3>
                            <xsl:value-of
                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title'][1]"
                            />
                        </h3>
                        <xsl:for-each
                            select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                        >
                            <xsl:if 
                                test="position() &gt; 1"
                            >
                                <h4 class="text-muted">
                                    <xsl:value-of
                                        select="."
                                    />
                                </h4>
                            </xsl:if>
                        </xsl:for-each>
                    </div>
                </div>
                <div class="tab-content">            
                    <div role="tabpanel" class="tab-pane active" id="entryTab">
                        <div class="row">
                            <div class="col-xs-12 col-md-6 col-xl-3">
                                <div class="card">
                                    <xsl:choose>
                                        <xsl:when test="document($itemMetadata)/mets:fileSec/mets:fileGrp[@USE='THUMBNAIL']/
                        mets:file[@GROUPID=current()/@GROUPID]">
                                            <img alt="page.general.thumbnail" i18n:attribute="alt" class="card-img-top hidden-xs-down">
                                                <xsl:attribute name="src">
                                                    <xsl:value-of select="document($itemMetadata)/mets:fileSec/mets:fileGrp[@USE='THUMBNAIL']/
                                    mets:file[@GROUPID=current()/@GROUPID]/mets:FLocat[@LOCTYPE='URL']/@xlink:href"/>
                                                </xsl:attribute>
                                            </img>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <img alt="page.general.thumbnail" i18n:attribute="alt" class="card-img-top hidden-xs-down">
                                                <xsl:attribute name="data-src">
                                                    <xsl:text>holder.js/100px200</xsl:text>
                                                    <xsl:text>?text=No Thumbnail</xsl:text>
                                                </xsl:attribute>
                                            </img>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <div class="card-block card-block-reduced">
                                        <!--<h5>-->
                                        <i class="fa fa-file" />
                                        <xsl:text> </xsl:text>
                                        <b>
                                            <i18n:text>page.item.view</i18n:text>
                                        </b>
                                        <!--</h5>-->
                                        <ul class="list-unstyled">
                                            <li>
                                                <a>
                                                    <xsl:attribute 
                                                        name="href"
                                                    >
                                                        <xsl:value-of
                                                            select="document($itemMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href"
                                                        />
                                                    </xsl:attribute>
                                                    <xsl:variable
                                                        name="size"
                                                    >
                                                        <xsl:value-of
                                                            select="document($itemMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/@SIZE"
                                                        />
                                                    </xsl:variable>
                                                    <i18n:text>page.item.fulltext</i18n:text> 
                                                    <xsl:text> (</xsl:text>
                                                    <xsl:choose>
                                                        <xsl:when test="$size &lt; 1024">
                                                            <xsl:value-of select="$size"/>
                                                            <xsl:text> </xsl:text>
                                                            <i18n:text>file.extension.size.bytes</i18n:text>
                                                        </xsl:when>
                                                        <xsl:when test="$size &lt; 1024 * 1024">
                                                            <xsl:value-of select="substring(string($size div 1024),1,5)"/>
                                                            <xsl:text> </xsl:text>
                                                            <i18n:text>file.extension.size.kbytes</i18n:text>
                                                        </xsl:when>
                                                        <xsl:when test="$size &lt; 1024 * 1024 * 1024">
                                                            <xsl:value-of select="substring(string($size div (1024 * 1024)),1,5)"/>
                                                            <xsl:text> </xsl:text>
                                                            <i18n:text>file.extension.size.mbytes</i18n:text>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:value-of select="substring(string($size div (1024 * 1024 * 1024)),1,5)"/>
                                                            <xsl:text> </xsl:text>
                                                            <i18n:text>file.extension.size.gbytes</i18n:text>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                    <xsl:text>)</xsl:text>
                                                </a>
                                            </li>
                                        </ul>                                        
                                    </div>
                                    
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                                    >
                                        <div class="card-block card-block-reduced">
                                            <!--<h5>-->
                                            <i class="fa fa-user" />
                                            <xsl:text> </xsl:text>
                                            <b>
                                                <i18n:text>page.item.authors</i18n:text>
                                            </b>
                                            <!--</h5>-->
                                            <ul class="list-unstyled">
                                                <xsl:for-each
                                                    select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                                                >
                                                    <li>
                                                        <a>                                        
                                                            <xsl:value-of
                                                                select="." 
                                                            />
                                                        </a>
                                                    </li>
                                                </xsl:for-each>
                                            </ul>
                                        </div>
                                    </xsl:if>                                    
                                </div>                             
                            </div>
                            <div class="col-xs-12 col-md-6 col-xl-9">
                                <div class="card">
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                    >
                                        <div class="card-block">
                                            <h5 class="card-title">
                                                <i class="fa fa-file-text-o"></i>
                                                <xsl:text> </xsl:text>
                                                <i18n:text>page.item.abstract</i18n:text>
                                            </h5>                                    
                                            <p class="card-text text-justify">
                                                <xsl:for-each
                                                    select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                                >
                                                    <xsl:value-of
                                                        select="./text()"
                                                    />
                                                    <xsl:if 
                                                        test="position() != last()"
                                                    >
                                                        <br />
                                                    </xsl:if>
                                                </xsl:for-each>
                                            </p>
                                        </div>
                                    </xsl:if>
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and not(@qualifier)]"
                                    >
                                        <div class="card-block">
                                            <h5 class="card-title">
                                                <i class="fa fa-tags"></i>
                                                <xsl:text> </xsl:text>
                                                <i18n:text>page.item.keywords</i18n:text>
                                            </h5>
                                            <ul class="list-inline">
                                                <xsl:for-each
                                                    select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and not(@qualifier)]"
                                                >
                                                    <li>
                                                        <a class="label label-primary">
                                                            <xsl:value-of
                                                                select="."
                                                            />
                                                        </a>
                                                    </li>
                                                </xsl:for-each>          
                                            </ul> 
                                        </div>
                                    </xsl:if>
                                    <div class="card-block">
                                        <h5 class="card-title">
                                            <i18n:text>page.item.citation</i18n:text>
                                        </h5>
                                        [mono]<b>Item#title</b>. (Item#language). In: Collection#editors: Collection#title. Collection#publisher, Collection#publisher#place, Collection#date. pp. Item#extent
                                    </div>
                                </div>
                            </div>                                          
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="fullentryTab">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="thead-default">
                                    <tr>
                                        <th>
                                            <i18n:text>page.item.fullentry.schema</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.element</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.qualifier</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.language</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.value</i18n:text>
                                        </th>
                                    </tr>
                                </thead>
                                <tfoot class="thead-default">
                                    <tr>
                                        <th>
                                            <i18n:text>page.item.fullentry.schema</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.element</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.qualifier</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.language</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.value</i18n:text>
                                        </th>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <xsl:for-each
                                        select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[not(contains(./@mdschema,'dmlcz'))]"
                                    >
                                        <tr>
                                            <td>
                                                <xsl:value-of
                                                    select="./@mdschema"
                                                />
                                            </td>
                                            <td>
                                                <xsl:value-of
                                                    select="./@element"
                                                />
                                            </td>
                                            <td>
                                                <xsl:value-of
                                                    select="./@qualifier"
                                                />
                                            </td>
                                            <td>
                                                <xsl:value-of
                                                    select="./@language"
                                                />
                                            </td>
                                            <td class="disable-math">
                                                <xsl:value-of
                                                    select="."
                                                />
                                            </td>
                                        </tr>
                                    </xsl:for-each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!--                    <div role="tabpanel" class="tab-pane" id="referencesTab">
                        <h2>!References</h2>
                        <small>
                            <ul class="list-unstyled">
                                <xsl:for-each
                                    select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='relation' and @qualifier='isbasedon']"
                                >
                                    <li>
                                        <xsl:call-template
                                            name="replacehtml-ent"
                                        >
                                            <xsl:with-param
                                                name="fragment"
                                                select="."
                                            />
                                        </xsl:call-template>                          
                                    </li>
                                </xsl:for-each>
                            </ul>
                        </small>
                    </div>-->
                </div>
            </div>
        </div>       
    </xsl:template>               
</xsl:stylesheet>
