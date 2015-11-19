<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : browse-by-view.xsl
    Created on : October 5, 2015, 2:56 PM
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
        match="dri:body/dri:div[@id='aspect.discovery.SimpleSearch.div.search']"
    >
        <div id="testElement">test</div>
        <h2>
            <xsl:value-of
                select="./dri:head"
            />                
        </h2>
        <hr />
        <!--
        <div id="aspect.discovery.SimpleSearch.div.discovery-search-box" rend="discoverySearchBox" n="discovery-search-box"></div>
        <div id="aspect.discovery.SimpleSearch.div.main-form" interactive="yes" rend="" action="/dmlcz5/discover" n="main-form" method="post"></div>
        <div id="aspect.discovery.SimpleSearch.div.search-results" pagesTotal="4" lastItemIndex="10" itemsTotal="34" rend="primary" pageURLMask="discover?rpp=10&page={pageNum}&query=almost&group_by=none&etal=0" n="search-results" currentPage="1" pagination="masked" firstItemIndex="1"></div>
        -->
        <!-- /document/body/div/div/div/@id-->
        <xsl:apply-templates />       
    </xsl:template>
    
    <xsl:template match="dri:list[@id='aspect.discovery.SimpleSearch.list.primary-search']//dri:item[dri:field[@id='aspect.discovery.SimpleSearch.field.query']]" priority="3">
        <div>
            <xsl:call-template name="standardAttributes">
                <xsl:with-param name="class">
                    <xsl:text>row</xsl:text>
                </xsl:with-param>
            </xsl:call-template>

            <div class="col-sm-3">
                <xsl:apply-templates select="dri:field[@id='aspect.discovery.SimpleSearch.field.scope']"/>
            </div>

            <div class="col-sm-9">
                <div class="input-group">
                    <xsl:apply-templates select="dri:field[@id='aspect.discovery.SimpleSearch.field.query']"/>
                    <span class="input-group-btn">
                        <xsl:apply-templates select="dri:field[@id='aspect.discovery.SimpleSearch.field.submit']"/>
                    </span>  
                </div>
            </div>
        </div>

        <xsl:if test="dri:item[@id='aspect.discovery.SimpleSearch.item.did-you-mean']">
            <div class="row">
                <div class="col-sm-offset-3 col-sm-9">
                    DID YO MEAN TODO
                    <xsl:apply-templates select="dri:item[@id='aspect.discovery.SimpleSearch.item.did-you-mean']"/>
                </div>
            </div>
        </xsl:if>

        <div class="row">
            <div class="col-sm-offset-3 col-sm-9" id="filters-overview-wrapper-squared"/>
        </div>
    </xsl:template>

    
    
<!--    <xsl:template
        match="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-search-box']"
    >
        <form>
            <div class="form-group row">
                <div class="col-md-4">
                    <xsl:apply-templates />
                </div>
                <div class="col-md-8">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for..." name="query"/>
                        <span class="input-group-btn">
                            <button class="btn btn-secondary" type="button">Go!</button>
                        </span>                            
                    </div>
                </div>
            </div>
        </form>        
    </xsl:template>-->
    
<!--    <xsl:template
        match="dri:div[@id='aspect.discovery.SimpleSearch.div.masked-page-control']"
    >
        <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fa fa-cog"></i>
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                <xsl:apply-templates />
            </div>
        </div>
    </xsl:template>
    
    <xsl:template
        match="dri:list[@rend='gear-selection' and @n='sort-options']/dri:item"
    >
        <h6 class="dropdown-header">
            <xsl:value-of
                select="."
            />
        </h6>
        <xsl:apply-templates select="./dri:list"/>
    </xsl:template>
    
    <xsl:template
        match="dri:list[@rend='gear-selection' and @n='sort-options']/dri:list/dri:item/dri:xref"
    >
        <a href="{@target}" class="{@rend} dropdown-item">
             todo fajka pre selected entry
                        <span>
                <xsl:attribute name="class">
                    <xsl:text>glyphicon glyphicon-ok btn-xs</xsl:text>
                    <xsl:choose>
                        <xsl:when test="contains(../@rend, 'gear-option-selected')">
                            <xsl:text> active</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:text> invisible</xsl:text>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
            </span>
            <xsl:apply-templates/>
        </a>
    </xsl:template>-->
    
    
<!--    <xsl:template
        match="dri:field[@type='select']"
    >
        <select class="form-control" name="{@n}">
            <xsl:for-each
                select="./dri:option"
            >
                <option>
                    <xsl:attribute
                        name="value"
                    >
                        <xsl:value-of
                            select="./@returnValue"
                        />
                    </xsl:attribute>
                    <xsl:value-of
                        select="."
                    />
                </option>
            </xsl:for-each>
        </select>              
    </xsl:template>-->
    
<!--     <xsl:template match="dri:field" mode="normalField">
        <xsl:variable name="confidenceIndicatorID" select="concat(translate(@id,'.','_'),'_confidence_indicator')"/>
        <xsl:choose>
             TODO: this has changed dramatically (see form3.xml) 
            <xsl:when test="@type= 'select'">
                    <select>
                        <xsl:call-template name="fieldAttributes"/>
                        <xsl:apply-templates/>
                    </select>
            </xsl:when>

    
    <xsl:template
        match="dri:field[@type='select']/dri:option"
    >
        <option>
            <xsl:attribute 
                name="value"
            >
                <xsl:value-of 
                    select="@returnValue"
                />
            </xsl:attribute>
            <xsl:if 
                test="../dri:value[@type='option'][@option = current()/@returnValue]"
            >
                <xsl:attribute name="selected">selected</xsl:attribute>
            </xsl:if>
            <xsl:apply-templates />
        </option>
    </xsl:template>-->
    
    
    <xsl:template match="dri:list[@id='aspect.discovery.SimpleSearch.list.search-results-repository']">
        results
    </xsl:template>
</xsl:stylesheet>
