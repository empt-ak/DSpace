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
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:encoder="xalan://java.net.URLEncoder"
                xmlns:util="org.dspace.app.xmlui.utils.XSLUtils"
                xmlns:confman="org.dspace.core.ConfigurationManager"
                exclude-result-prefixes="xalan encoder i18n dri mets dim xlink xsl util confman">
    <xsl:output method="xml" encoding="UTF-8" indent="no"/>

    <xsl:template
            match="dri:body[dri:div[starts-with(@id,'aspect.discovery.SearchFacetFilter.div.browse-by-') or starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]]"
            priority="100000"
    >
        <h1>
            <i18n:text>
                <xsl:value-of select="dri:div/dri:head"/>
            </i18n:text>
        </h1>
        <xsl:apply-templates
                select="dri:div/dri:div[@id='aspect.discovery.SearchFacetFilter.div.filter-navigation' or @id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-navigation']"/>
        <xsl:apply-templates
            select="./dri:div/dri:div[@n='browse-controls']" />
        <xsl:apply-templates
                select="./dri:div[starts-with(@id,'aspect.discovery.SearchFacetFilter.div.browse-by-') and @pagination='simple'] | ./dri:div/dri:div[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-') and @pagination='simple']"/>
    </xsl:template>


    <xsl:template
        match="dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-controls']"
    >
        <!-- TODO -->
        <!--<div class="dropdown" id="{translate(@id,'.','_')}">-->
            <!--<button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true"-->
                    <!--aria-expanded="false">-->
                <!--<i class="fa fa-cog"></i>-->
            <!--</button>-->
            <!--<div class="dropdown-menu">-->
                <!--<xsl:for-each-->
                        <!--select="./dri:list/dri:item"-->
                <!--&gt;-->
                    <!--<xsl:choose>-->
                        <!--<xsl:when-->
                                <!--test="./i18n:text"-->
                        <!--&gt;-->
                            <!--<h6 class="dropdown-header">-->
                                <!--<i18n:text>-->
                                    <!--<xsl:value-of-->
                                            <!--select="."-->
                                    <!--/>-->
                                <!--</i18n:text>-->
                            <!--</h6>-->
                        <!--</xsl:when>-->
                        <!--<xsl:otherwise>-->
                            <!--<a class="dropdown-item {@rend}" href="{./dri:xref/@target}">-->
                                <!--<span class="btn-xs invisible">-->
                                    <!--<xsl:if-->
                                            <!--test="@rend='gear-option gear-option-selected'"-->
                                    <!--&gt;-->
                                        <!--<xsl:attribute-->
                                                <!--name="class"-->
                                        <!--&gt;-->
                                            <!--<xsl:text>btn-xs</xsl:text>-->
                                        <!--</xsl:attribute>-->
                                    <!--</xsl:if>-->
                                    <!--<i class="fa fa-check"></i>-->
                                <!--</span>-->
                                <!--<i18n:text>-->
                                    <!--<xsl:value-of-->
                                            <!--select="./dri:xref"-->
                                    <!--/>-->
                                <!--</i18n:text>-->
                            <!--</a>-->
                        <!--</xsl:otherwise>-->
                    <!--</xsl:choose>-->
                <!--</xsl:for-each>-->
            <!--</div>-->
        <!--</div>-->
    </xsl:template>

    <xsl:template
            match="dri:div[@id='aspect.discovery.SearchFacetFilter.div.filter-navigation' or @id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-navigation']"
    >
        <form method="{@method}" action="{@action}">
            <xsl:for-each select="./dri:p/dri:field[@type='hidden']">
                <input type="hidden" name="{@n}" value="{./dri:value}"/>
            </xsl:for-each>
            <div class="row">
                <div class="col-4 col-lg-12">
                    <xsl:apply-templates select="./dri:list[@n='jump-list']" mode="select"/>
                    <xsl:apply-templates select="./dri:list[@n='jump-list']" mode="paginated"/>
                </div>
                <div class="col-8 col-lg-12">
                    <xsl:apply-templates
                            select="./dri:p[dri:field[@id='aspect.discovery.SearchFacetFilter.field.starts_with' or @id='aspect.artifactbrowser.ConfigurableBrowse.field.starts_with']]"/>
                </div>
            </div>
        </form>
    </xsl:template>

    <xsl:template
            match="dri:list[@n='jump-list']"
            mode="select"
    >
        <select class="form-control hidden-md-up" name="{@n}">
            <xsl:for-each select="./dri:item/dri:xref">
                <option value="{@target}">
                    <xsl:value-of select="."/>
                </option>
            </xsl:for-each>
        </select>
    </xsl:template>

    <xsl:template
            match="dri:list[@n='jump-list']"
            mode="paginated"
    >
        <ul class="pagination hidden-sm-down">
            <xsl:for-each
                    select="./dri:item/dri:xref"
            >
                <li class="page-item">
                    <a href="{./@target}" class="page-link">
                        <xsl:value-of
                                select="."
                        />
                    </a>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:template>

    <xsl:template
            match="dri:p[dri:field[@id='aspect.discovery.SearchFacetFilter.field.starts_with' or @id='aspect.artifactbrowser.ConfigurableBrowse.field.starts_with']]"
    >
        <div class="input-group">
            <span class="input-group-addon" id="basic-addon3">
                <i18n:text>
                    <xsl:value-of select="./i18n:text"/>
                </i18n:text>
            </span>
            <input type="text" class="form-control"
                   placeholder="{./dri:field[@n='starts_with']/dri:help/i18n:text}"
                   name="{./dri:field/@n}"
                   i18n:attr="placeholder"
            />
            <span class="input-group-btn">
                <xsl:apply-templates select="./dri:field[@type='button']"/>
            </span>
        </div>
    </xsl:template>


    <xsl:template
            match="dri:div[@pagination='simple']"
    >
        <xsl:apply-templates
                select="./dri:table[starts-with(@id,'aspect.discovery.SearchFacetFilter.table.browse-by-') or starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')] | ./dri:referenceSet[@id='aspect.artifactbrowser.ConfigurableBrowse.referenceSet.browse-by-title']"
        />
        <div class="row">
            <div class="col-12">
                <a>
                    <xsl:attribute name="class">
                        <xsl:text>btn btn-secondary</xsl:text>
                        <xsl:if
                                test="not(@previousPage)"
                        >
                            <xsl:text> disabled</xsl:text>
                        </xsl:if>
                    </xsl:attribute>

                    <xsl:attribute
                            name="href"
                    >
                        <xsl:choose>
                            <xsl:when
                                    test="@previousPage"
                            >
                                <xsl:value-of
                                        select="@previousPage"
                                />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>#</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <i class="fa fa-arrow-left"></i>
                    <xsl:text> </xsl:text>
                    <span class="hidden-sm-down">
                        <xsl:text> </xsl:text>
                        <i18n:text>navigation.previous</i18n:text>
                    </span>
                </a>

                <a>
                    <xsl:attribute name="class">
                        <xsl:text>btn btn-secondary float-right</xsl:text>
                        <xsl:if
                                test="not(@nextPage)"
                        >
                            <xsl:text> disabled</xsl:text>
                        </xsl:if>
                    </xsl:attribute>

                    <xsl:attribute
                            name="href"
                    >
                        <xsl:choose>
                            <xsl:when
                                    test="@nextPage"
                            >
                                <xsl:value-of
                                        select="@nextPage"
                                />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>#</xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <span class="hidden-sm-down">
                        <xsl:text> </xsl:text>
                        <i18n:text>navigation.next</i18n:text>
                    </span>
                    <xsl:text> </xsl:text>
                    <i class="fa fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </xsl:template>


    <!-- browse by title is unique -->
    <xsl:template
            match="dri:referenceSet[@id='aspect.artifactbrowser.ConfigurableBrowse.referenceSet.browse-by-title']">
        <table class="table table-striped table-hover mt-5">
            <thead class="thead-default">
                <tr>
                    <th>
                        <i18n:text>xmlui.Discovery.AbstractSearch.type_title</i18n:text>
                    </th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each
                        select="./dri:reference"
                >
                    <xsl:variable
                            name="entry"
                    >
                        <xsl:text>cocoon://</xsl:text>
                        <xsl:value-of select="./@url"/>
                        <xsl:text>?sections=dmdSec,fileSec</xsl:text>
                    </xsl:variable>
                    <tr>
                        <td>
                            <a href="{document($entry)/mets:METS/@OBJID}">
                                <xsl:choose>
                                    <xsl:when
                                            test="document($entry)//mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title'][1]">
                                        <xsl:value-of select="document($entry)//mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title'][1]"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <i18n:text>page.item.missing.title</i18n:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </a>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template
            match="dri:table[starts-with(@id,'aspect.discovery.SearchFacetFilter.table.browse-by-') or starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]"
    >
        <table class="table table-striped table-hover mt-5">
            <thead class="thead-default">
                <tr>
                    <th>
                        <i18n:text>
                            <xsl:value-of select="./preceding-sibling::dri:head/i18n:text"/>
                        </i18n:text>
                    </th>
                    <th>
                        <i18n:text>xmlui.Discovery.AbstractSearch.occurences</i18n:text>
                    </th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each
                        select="./dri:row[not(@role)]/dri:cell[not(@role)]"
                >
                    <tr>
                        <xsl:if test="not(./dri:xref)">
                            <xsl:attribute name="class">
                                <xsl:text>table-info</xsl:text> <!-- selected browse by has no xref so we highlight it -->
                            </xsl:attribute>
                        </xsl:if>
                        <td>
                            <xsl:choose>
                                <xsl:when test="not(./dri:xref)">
                                    <xsl:value-of select="."/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <a href="{./dri:xref/@target}">
                                        <xsl:value-of select="./dri:xref"/>
                                    </a>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                        <td class="text-left">
                            <xsl:call-template name="browse-by-row">
                                <xsl:with-param name="text" select="."/>
                            </xsl:call-template>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template
            name="browse-by-row"
    >
        <xsl:param name="text"/>
        <span class="badge badge-info">
            <xsl:value-of select="substring-before(substring-after(.,'['),']')"/>
        </span>
    </xsl:template>
</xsl:stylesheet>
