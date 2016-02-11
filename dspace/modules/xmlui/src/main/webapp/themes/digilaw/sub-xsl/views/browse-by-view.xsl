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
        <div class="form-group row">
            <div class="col-xs-10">
                <p class="form-control-static">
                    <xsl:apply-templates
                        mode="pagination"
                        select="./dri:div[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]"
                    />
                </p>
            </div>
            <div class="col-xs-2">
                <xsl:apply-templates
                    select="./dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-controls']" 
                />
            </div>
        </div>
        <div class="row offset-top-25">
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
            <xsl:for-each
                select="./dri:referenceSet[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.referenceSet.browse-by-')]/dri:reference"
            >
                <xsl:variable
                    name="extMets"
                >
                    <xsl:text>cocoon://</xsl:text>
                    <xsl:value-of select="./@url"/>
                    <xsl:text>?sections=dmdSec,fileSec</xsl:text>
                </xsl:variable>
                <div class="media disable-math">
                    <div class="media-left hidden-sm-down">
                        <img alt="page.general.thumbnail" class="img-responsive" i18n:attribute="alt">
                            <xsl:attribute name="data-src">
                                <xsl:text>holder.js/100x100</xsl:text>
                                <xsl:text>?text=No Thumbnail</xsl:text>
                            </xsl:attribute>
                        </img>
                    </div>
                    <div class="media-body">
                        <h5 class="media-heading">
                            <a href="{document($extMets)/mets:METS/@OBJID}">
                                <xsl:choose>
                                    <xsl:when
                                        test="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                    >
                                        <xsl:value-of
                                            select="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                        />
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <i18n:text>page.item.missing.title</i18n:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </a>
                        </h5>
                        <div>
                            <i class="fa fa-user"></i>
                            <span class="text-muted">
                                <xsl:text> </xsl:text>
                                <xsl:choose>
                                    <xsl:when
                                        test="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor']"
                                    >
                                        <xsl:for-each
                                            select="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor']"
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
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <i18n:text>page.item.missing.author</i18n:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </span>
                        </div>
                        <div>
                            <xsl:choose>
                                <xsl:when test="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']">
                                    <xsl:choose>
                                        <xsl:when
                                            test="string-length(document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']) &gt; 410"
                                        >
                                            <xsl:value-of select="substring(document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract'],0,410)" />
                                            <i18n:text>page.landing.ellipsis</i18n:text>
                                            <xsl:text> </xsl:text>
                                            <a href="{document($extMets)/mets:METS/@OBJID}">
                                                <i18n:text>page.landing.more</i18n:text>
                                                <xsl:text> </xsl:text>
                                                <i class="fa fa-arrow-circle-right"></i>
                                            </a>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']" />
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:when>
                                <xsl:otherwise>
                                    <i18n:text>page.item.missing.abstract</i18n:text>
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </div>
                </div>
            </xsl:for-each>
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
            <div class="form-group row">
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
        <form action="{@action}" method="{@method}" id="{translate(@id,'.','_')}" class="pull-xs-right">
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
                                        <i18n:text>
                                            <xsl:value-of
                                                select="."
                                            />
                                        </i18n:text>
                                    </a>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <h6 class="dropdown-header">
                                    <i18n:text>
                                        <xsl:value-of
                                            select="."
                                        />
                                    </i18n:text>
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
