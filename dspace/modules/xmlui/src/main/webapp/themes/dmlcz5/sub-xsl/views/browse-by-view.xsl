<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : browse-by-view.xsl
    Created on : October 6, 2015, 4:55 PM
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
    
    <!-- priority here is really important !! -->
    <xsl:template
        match="/dri:document/dri:body/dri:div[contains(@n,'browse-by-') and @rend='primary']"
        priority="10000"
    >
        <div class="row">
            <div class="col-xs-12">
                <h1>
                    <i18n:text>
                        <xsl:value-of
                            select="./dri:head"
                        />
                    </i18n:text>
                </h1>
            </div>
        </div>
        <xsl:apply-templates
            select="./dri:div[contains(@rend,'browse-navigation-wrapper')]/dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-navigation']"
        />
        <div class="row">
            <div class="col-xs-10">
                <xsl:apply-templates
                    mode="pagination"
                    select="./dri:div[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]"
                />
            </div>
            <div class="col-xs-2">
                <xsl:apply-templates
                    select="./dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-controls']" 
                />               
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <xsl:apply-templates
                    select="./dri:div[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]"
                />
            </div>            
        </div>
    </xsl:template>
    
    
    <xsl:template
        match="dri:list[@n='jump-list']"
    >
        <nav>
            <ul class="pagination">
                <xsl:for-each
                    select="./dri:item/dri:xref"
                >
                    <li>
                        <a href="{./@target}">
                            <xsl:value-of
                                select="."
                            />
                        </a>
                    </li>
                </xsl:for-each>
            </ul>
        </nav>
    </xsl:template>
    
    <xsl:template
        match="dri:div[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]"
    >
        <xsl:if
            test="./dri:referenceSet[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.referenceSet.browse-by-')]/dri:reference"
        >
            <div class="table-responsive">
                <table class="table table-hover table-striped">
                    <xsl:for-each
                        select="./dri:referenceSet[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.referenceSet.browse-by-')]/dri:reference"
                    >
                        <tr>
                            <td>
                                <xsl:value-of
                                    select="./@url"
                                />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </div>
        </xsl:if>
        <xsl:if
            test="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]"
        >
            <div class="table-responsive">
                <table class="table table-hover table-striped">
                    <thead class="thead-default">
                        <tr>
                            <th>
                                <i18n:text>
                                    <xsl:value-of
                                        select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[@role='header']/dri:cell"
                                    />
                                </i18n:text>
                            </th>
                            <th>
                                <i18n:text>page.search.table.occurences</i18n:text>
                            </th>
                        </tr>
                    </thead>
                    <tfoot class="thead-default">
                        <tr>
                            <th>
                                <i18n:text>
                                    <xsl:value-of
                                        select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[@role='header']/dri:cell"
                                    />
                                </i18n:text>
                            </th>
                            <th>
                                <i18n:text>page.search.table.occurences</i18n:text>
                            </th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <xsl:for-each
                            select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[not(@role)]/dri:cell"
                        >
                            <tr>
                                <td class="disable-math">
                                    <a href="{./dri:xref/@target}">
                                        <xsl:value-of
                                            select="./dri:xref"
                                        />
                                    </a>
                                </td>
                                <td>                            
                                    <span class="label label-default label-pill pull-xs-left">
                                        <xsl:value-of
                                            select="./text()"
                                        />
                                    </span>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </div>
        </xsl:if>
        
        <nav>
            <ul class="pager">
                <li>
                    <xsl:attribute
                        name="class"
                    >
                        <xsl:choose>
                            <xsl:when
                                test="./@previousPage"
                            >
                                <xsl:text>pager-prev</xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>pager-prev disabled</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <a>
                        <xsl:attribute
                            name="href"
                        >
                            <xsl:choose>
                                <xsl:when
                                    test="./@previousPage"
                                >
                                    <xsl:value-of
                                        select="./@previousPage"
                                    />
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:text>#</xsl:text>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:attribute>                        
                        <i class="fa fa-arrow-left"></i>
                        <span class="hidden-sm-down">
                            <xsl:text> </xsl:text>
                            <i18n:text>navigation.previous</i18n:text>
                        </span>
                    </a>
                </li>
                <li>
                    <xsl:attribute
                        name="class"
                    >
                        <xsl:choose>
                            <xsl:when
                                test="./@nextPage"
                            >
                                <xsl:text>pager-next</xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>pager-next disabled</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <a>
                        <xsl:attribute
                            name="href"
                        >
                            <xsl:choose>
                                <xsl:when
                                    test="./@nextPage"
                                >
                                    <xsl:value-of
                                        select="./@nextPage"
                                    />
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:text>#</xsl:text>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:attribute>
                        <span class="hidden-sm-down">
                            <i18n:text>navigation.next</i18n:text>
                            <xsl:text> </xsl:text>
                        </span>                        
                        <i class="fa fa-arrow-right"></i>
                    </a>
                </li>
            </ul>
        </nav>
    </xsl:template>
    
    <xsl:template
        match="dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-navigation']"
    >
        <form method="{@method}" action="{@action}">
            <div class="row">
                <div class="col-xs-4 hidden-md-up">
                    <xsl:apply-templates
                        select="./dri:div[@rend='row']/dri:div/dri:field"
                    />
                </div>
                <div class="col-xs-8 col-md-12">
                    <div class="hidden-sm-down">
                        <xsl:apply-templates
                            select="./dri:div[@rend='row']/dri:div/dri:list"
                        />
                    </div>
                    <div>
                        <div class="input-group">
                            <xsl:for-each
                                select="./dri:div[@rend='row']/dri:div/dri:p[@rend='hidden']/dri:field"
                            >
                                <input type="hidden" name="{@n}" value="{./dri:value}" />
                            </xsl:for-each>
                            <!--<input type="text" name="starts_with" class="form-control" placeholder="xmlui.ArtifactBrowser.ConfigurableBrowse.general.starts_with_help" i18n:attribute="placeholder" />-->
                            <input type="text" name="starts_with" class="form-control">
                                <xsl:attribute name="placeholder">
                                    <xsl:text>xmlui.ArtifactBrowser.ConfigurableBrowse.general.starts_with_help</xsl:text>
                                </xsl:attribute>
                                <xsl:attribute name="i18n:attr">placeholder</xsl:attribute>
                            </input>
                            <span class="input-group-btn">
                                <button class="btn btn-secondary" type="submit">
                                    <i18n:text>xmlui.general.go</i18n:text>
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </xsl:template>
    
    <xsl:template 
        match="dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-controls']"
    >
        <form action="{@action}" method="{@method}" id="{translate(@id,'.','_')}">
            <div class="hidden">
                <xsl:for-each
                    select="./dri:p[@n='hidden-fields']/dri:field[@type='hidden']"
                >
                    <input type="hidden" name="{@n}" value="{./dri:value}" />
                </xsl:for-each>
                <xsl:for-each
                    select="./dri:p/dri:field"
                >
                    <xsl:apply-templates
                        select="."
                    />
                </xsl:for-each>
            </div>
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fa fa-cog"></i>
                </button>                
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">
                    <xsl:for-each
                        select="./dri:p[not(@n='hidden-fields')]"                    
                    >
                        <xsl:choose>
                            <xsl:when
                                test="./dri:field"
                            >
                                <xsl:for-each
                                    select="./dri:field/dri:option"
                                >                           
                                    <a class="dropdown-item" data-name="{parent::*/@n}" data-value="{./@returnValue}" href="#">   
                                        <span class="btn-xs invisible">
                                            <xsl:if
                                                test="parent::*/dri:value[@option=current()/@returnValue]"
                                            >
                                                <xsl:attribute
                                                    name="class"
                                                >
                                                    <xsl:text>btn-xs</xsl:text>
                                                </xsl:attribute>
                                            </xsl:if>
                                            <i class="fa fa-check"></i>
                                        </span>                                       
                                        <xsl:value-of
                                            select="."
                                        />
                                    </a>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <h6 class="dropdown-header">
                                    <xsl:value-of
                                        select="."
                                    />
                                </h6>
                            </xsl:otherwise>
                        </xsl:choose>                        
                    </xsl:for-each>
                </div>
            </div>
        </form>
    </xsl:template>   
    
    <xsl:template
        match="dri:div[@pagination='simple']"    
        mode="pagination"
    > 
        <i18n:translate>
            <xsl:choose>
                <xsl:when test="@itemsTotal = -1">
                    <i18n:text>xmlui.dri2xhtml.structural.pagination-info.nototal</i18n:text>
                </xsl:when>
                <xsl:otherwise>
                    <i18n:text>xmlui.dri2xhtml.structural.pagination-info</i18n:text>
                </xsl:otherwise>
            </xsl:choose>
            <i18n:param>
                <xsl:value-of select="@firstItemIndex"/>
            </i18n:param>
            <i18n:param>
                <xsl:value-of select="@lastItemIndex"/>
            </i18n:param>
            <i18n:param>
                <xsl:value-of select="@itemsTotal"/>
            </i18n:param>
        </i18n:translate>
    </xsl:template>
</xsl:stylesheet>
