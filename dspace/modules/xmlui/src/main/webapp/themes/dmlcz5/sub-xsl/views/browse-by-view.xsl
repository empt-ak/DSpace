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
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    
    <xsl:template
        match="/dri:document/dri:body/dri:div[contains(@n,'browse-by-') and @rend='primary']"
    >
        <div class="row">
            <div class="col-xs-12">
                !header
            </div>
        </div>
        <div class="row">
            <div class="col-xs-4 hidden-md-up">
                <xsl:apply-templates
                    select="./dri:div/dri:div[@n='browse-navigation']/dri:div[@rend='row']/dri:div/dri:field"
                />
            </div>
            <div class="col-xs-8 col-md-12">
                <div class="hidden-sm-down">
                    <xsl:apply-templates
                        select="./dri:div/dri:div[@n='browse-navigation']/dri:div[@rend='row']/dri:div/dri:list"
                    />
                </div>
                <div>
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for..." />
                        <span class="input-group-btn">
                            <button class="btn btn-secondary" type="button">Go!</button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-10">
                !showing xyz of abc
            </div>
            <div class="col-xs-2">
                **
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <!-- tu chyba matchovania -->
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
        asf
        <xsl:if
            test="./dri:referenceSet[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]/dri:reference"
        >
            <div class="table-responsive">
                <table class="table table-hover table-striped">
                    <xsl:for-each
                        select="./dri:referenceSet[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]/dri:reference"
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
                                <xsl:value-of
                                    select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[@role='header']/dri:cell"
                                />
                            </th>
                            <th>
                                !pocet vyskytov
                            </th>
                        </tr>
                    </thead>
                    <tfoot class="thead-default">
                        <tr>
                            <th>
                                <xsl:value-of
                                    select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[@role='header']/dri:cell"
                                />
                            </th>
                            <th>
                                !pocet vyskytov
                            </th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <xsl:for-each
                            select="./dri:table[@id='aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-subject-results']/dri:row[not(@role)]/dri:cell"
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
    
</xsl:stylesheet>
