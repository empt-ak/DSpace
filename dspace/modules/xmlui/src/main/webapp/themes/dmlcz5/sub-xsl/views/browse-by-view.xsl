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
            match="dri:body[dri:div[starts-with(@id,'aspect.discovery.SearchFacetFilter.div.browse-by-')]]"
            priority="100000"
    >
        <h1>
            <i18n:text>
                <xsl:value-of select="dri:div/dri:head"/>
            </i18n:text>
        </h1>
        <xsl:apply-templates select="dri:div/dri:div[@id='aspect.discovery.SearchFacetFilter.div.filter-navigation']"/>
        <xsl:apply-templates
                select="./dri:div[starts-with(@id,'aspect.discovery.SearchFacetFilter.div.browse-by-') and @pagination='simple']"/>
    </xsl:template>

    <xsl:template
            match="dri:div[@id='aspect.discovery.SearchFacetFilter.div.filter-navigation']"
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
                    !TODO koliesko
                    <xsl:apply-templates
                            select="./dri:p[dri:field[@id='aspect.discovery.SearchFacetFilter.field.starts_with']]"/>
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
            match="dri:p[dri:field[@id='aspect.discovery.SearchFacetFilter.field.starts_with']]"
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
                select="./dri:table[starts-with(@id,'aspect.discovery.SearchFacetFilter.table.browse-by-')]"/>
    </xsl:template>


    <xsl:template
            match="dri:table[starts-with(@id,'aspect.discovery.SearchFacetFilter.table.browse-by-')]"
    >
        <table class="table mt-5">
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
                        select="./dri:row/dri:cell"
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
                                    <xsl:value-of select="substring-before(.,'(')"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <a href="{./dri:xref/@target}">
                                        <xsl:value-of select="substring-before(./dri:xref,'(')"/>
                                    </a>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                        <td>
                            <xsl:call-template name="browse-by-row">
                                <xsl:with-param name="text" select=". | ./dri:xref"/>
                            </xsl:call-template>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>

        <div class="row">
            <div class="col-12">
                <a>
                    <xsl:attribute name="class">
                        <xsl:text>btn btn-secondary</xsl:text>
                        <xsl:if
                                test="/@previousPage"
                        >
                            <xsl:text> disabled</xsl:text>
                        </xsl:if>
                    </xsl:attribute>

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

                <a>
                    <xsl:attribute name="class">
                        <xsl:text>btn btn-secondary float-right</xsl:text>
                        <xsl:if
                                test="/@nextPage"
                        >
                            <xsl:text> disabled</xsl:text>
                        </xsl:if>
                    </xsl:attribute>

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
                        <xsl:text> </xsl:text>
                        <i18n:text>navigation.next</i18n:text>
                    </span>
                    <i class="fa fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </xsl:template>

    <xsl:template
            name="browse-by-row"
    >
        <xsl:param name="text"/>
        <span class="badge badge-info float-right">
            <xsl:variable
                    name="badge"
            >
                <!-- if ./rend='selected' == true then this node has no xref children
                so we pass value of this node like | ./text()
                -->
                <xsl:call-template name="substring-after-last">
                    <xsl:with-param name="string" select="$text"/>
                    <xsl:with-param name="delimiter" select="'('"/>
                </xsl:call-template>
            </xsl:variable>

            <xsl:value-of
                    select="substring-before($badge,')')"
            />
        </span>
    </xsl:template>


    <!--<xsl:template-->
    <!--match="dri:p/dri:field[@id='aspect.discovery.SearchFacetFilter.field.starts_with' and @n='starts_with']"-->
    <!--priority="1000000"-->
    <!--&gt;-->
    <!--qwerty-->
    <!--&lt;!&ndash;<div class="input-group">&ndash;&gt;-->
    <!--&lt;!&ndash;<span class="input-group-addon" id="basic-addon3">https://example.com/users/</span>&ndash;&gt;-->
    <!--&lt;!&ndash;<input type="text" class="form-control" placeholder="Search for..." />&ndash;&gt;-->
    <!--&lt;!&ndash;<span class="input-group-btn">&ndash;&gt;-->
    <!--&lt;!&ndash;<button class="btn btn-secondary" type="button">Go!</button>&ndash;&gt;-->
    <!--&lt;!&ndash;</span>&ndash;&gt;-->
    <!--&lt;!&ndash;</div>&ndash;&gt;-->
    <!--</xsl:template>-->

    <!--<xsl:template-->
    <!--match="dri:div[@pagination='simple' and starts-with(@n,'browse-by-')]">-->
    <!--<xsl:apply-templates-->
    <!--select="./dri:table"-->
    <!--/>-->
    <!--<div class="row">-->
    <!--<div class="col-12">-->
    <!--<a>-->
    <!--<xsl:attribute name="class">-->
    <!--<xsl:text>btn btn-secondary</xsl:text>-->
    <!--<xsl:if-->
    <!--test="/@previousPage"-->
    <!--&gt;-->
    <!--<xsl:text> disabled</xsl:text>-->
    <!--</xsl:if>-->
    <!--</xsl:attribute>-->

    <!--<xsl:attribute-->
    <!--name="href"-->
    <!--&gt;-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="./@previousPage"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="./@previousPage"-->
    <!--/>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<xsl:text>#</xsl:text>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</xsl:attribute>-->
    <!--<i class="fa fa-arrow-left"></i>-->
    <!--<span class="hidden-sm-down">-->
    <!--<xsl:text> </xsl:text>-->
    <!--<i18n:text>navigation.previous</i18n:text>-->
    <!--</span>-->
    <!--</a>-->

    <!--<a>-->
    <!--<xsl:attribute name="class">-->
    <!--<xsl:text>btn btn-secondary float-right</xsl:text>-->
    <!--<xsl:if-->
    <!--test="/@nextPage"-->
    <!--&gt;-->
    <!--<xsl:text> disabled</xsl:text>-->
    <!--</xsl:if>-->
    <!--</xsl:attribute>-->

    <!--<xsl:attribute-->
    <!--name="href"-->
    <!--&gt;-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="./@nextPage"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="./@nextPage"-->
    <!--/>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<xsl:text>#</xsl:text>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</xsl:attribute>-->
    <!--<i class="fa fa-arrow-right"></i>-->
    <!--<span class="hidden-sm-down">-->
    <!--<xsl:text> </xsl:text>-->
    <!--<i18n:text>navigation.next</i18n:text>-->
    <!--</span>-->
    <!--</a>-->
    <!--</div>-->
    <!--</div>-->
    <!--</xsl:template>-->

    <!--<xsl:template match="dri:table[parent::dri:div[@pagination='simple']]" priority="10000">-->
    <!--<table class="table disable-math">-->
    <!--<xsl:for-each select="./dri:row/dri:cell/dri:xref">-->
    <!--<tr>-->
    <!--<td>-->
    <!--<a href="{@target}">-->
    <!--<span class="badge badge-info float-right">-->
    <!--<xsl:variable-->
    <!--name="badge"-->
    <!--&gt;-->
    <!--&lt;!&ndash; if ./rend='selected' == true then this node has no xref children-->
    <!--so we pass value of this node like | ./text()-->
    <!--&ndash;&gt;-->
    <!--<xsl:call-template name="substring-after-last">-->
    <!--<xsl:with-param name="string" select="."/>-->
    <!--<xsl:with-param name="delimiter" select="'('"/>-->
    <!--</xsl:call-template>-->
    <!--</xsl:variable>-->

    <!--<xsl:value-of-->
    <!--select="substring-before($badge,')')"-->
    <!--/>-->
    <!--</span>-->
    <!--<xsl:value-of-->
    <!--select="substring-before(.,' (')"-->
    <!--/>-->
    <!--</a>-->
    <!--</td>-->
    <!--</tr>-->
    <!--</xsl:for-each>-->
    <!--</table>-->
    <!--</xsl:template>-->

    <!--<xsl:template-->
    <!--match="dri:list[@n='jump-list']"-->
    <!--&gt;-->
    <!--<nav class="hidden-sm-down">-->
    <!--<ul class="pagination">-->
    <!--<xsl:for-each-->
    <!--select="./dri:item/dri:xref"-->
    <!--&gt;-->
    <!--<li class="page-item">-->
    <!--<a href="{./@target}" class="page-link">-->
    <!--<xsl:value-of-->
    <!--select="."-->
    <!--/>-->
    <!--</a>-->
    <!--</li>-->
    <!--</xsl:for-each>-->
    <!--</ul>-->
    <!--</nav>-->
    <!--</xsl:template>-->

    <!--<xsl:template-->
    <!--match="dri:div[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.div.browse-by-')]"-->
    <!--&gt;-->
    <!--&lt;!&ndash; TODO TODO TODOOOO WHEN IS THIS USED ??? &ndash;&gt;-->
    <!--<xsl:if-->
    <!--test="./dri:referenceSet[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.referenceSet.browse-by-')]/dri:reference"-->
    <!--&gt;-->
    <!--<h1>IMPORTANT TODO</h1>-->
    <!--<xsl:for-each-->
    <!--select="./dri:referenceSet[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.referenceSet.browse-by-')]/dri:reference"-->
    <!--&gt;-->
    <!--<xsl:variable-->
    <!--name="extMets"-->
    <!--&gt;-->
    <!--<xsl:text>cocoon://</xsl:text>-->
    <!--<xsl:value-of select="./@url"/>-->
    <!--<xsl:text>?sections=dmdSec,fileSec</xsl:text>-->
    <!--</xsl:variable>-->
    <!--<div class="media disable-math">-->
    <!--&lt;!&ndash;-->
    <!--<div class="media-left hidden-sm-down">-->
    <!--<img alt="page.general.thumbnail" class="img-fluid" i18n:attribute="alt">-->
    <!--<xsl:attribute name="data-src">-->
    <!--<xsl:text>holder.js/100x100</xsl:text>-->
    <!--<xsl:text>?text=No Thumbnail</xsl:text>-->
    <!--</xsl:attribute>-->
    <!--</img>-->
    <!--</div>&ndash;&gt;-->
    <!--<div class="media-body">-->
    <!--<h5 class="media-heading">-->
    <!--<a href="{document($extMets)/mets:METS/@OBJID}">-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"-->
    <!--/>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<i18n:text>page.item.missing.title</i18n:text>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</a>-->
    <!--</h5>-->
    <!--<div>-->
    <!--<i class="fa fa-user"></i>-->
    <!--<span class="text-muted">-->
    <!--<xsl:text> </xsl:text>-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor']"-->
    <!--&gt;-->
    <!--<xsl:for-each-->
    <!--select="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor']"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="."-->
    <!--/>-->
    <!--<xsl:if-->
    <!--test="position() != last()"-->
    <!--&gt;-->
    <!--<xsl:text>; </xsl:text>-->
    <!--</xsl:if>-->
    <!--</xsl:for-each>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<i18n:text>page.item.missing.author</i18n:text>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</span>-->
    <!--</div>-->
    <!--<div>-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']">-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="string-length(document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']) &gt; 410"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="substring(document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract'],0,410)"/>-->
    <!--<i18n:text>page.landing.ellipsis</i18n:text>-->
    <!--<xsl:text> </xsl:text>-->
    <!--<a href="{document($extMets)/mets:METS/@OBJID}">-->
    <!--<i18n:text>page.landing.more</i18n:text>-->
    <!--<xsl:text> </xsl:text>-->
    <!--<i class="fa fa-arrow-circle-right"></i>-->
    <!--</a>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<xsl:value-of-->
    <!--select="document($extMets)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"/>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<i18n:text>page.item.missing.abstract</i18n:text>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</div>-->
    <!--</div>-->
    <!--</div>-->
    <!--</xsl:for-each>-->
    <!--</xsl:if>-->
    <!--<xsl:if-->
    <!--test="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]"-->
    <!--&gt;-->
    <!--<div class="table-responsive">-->
    <!--<table class="table table-hover table-striped">-->
    <!--<thead class="thead-default">-->
    <!--<tr>-->
    <!--<th>-->
    <!--<i18n:text>-->
    <!--<xsl:value-of-->
    <!--select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[@role='header']/dri:cell"-->
    <!--/>-->
    <!--</i18n:text>-->
    <!--</th>-->
    <!--<th>-->
    <!--<i18n:text>page.search.table.occurences</i18n:text>-->
    <!--</th>-->
    <!--</tr>-->
    <!--</thead>-->
    <!--<tfoot class="thead-default">-->
    <!--<tr>-->
    <!--<th>-->
    <!--<i18n:text>-->
    <!--<xsl:value-of-->
    <!--select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[@role='header']/dri:cell"-->
    <!--/>-->
    <!--</i18n:text>-->
    <!--</th>-->
    <!--<th>-->
    <!--<i18n:text>page.search.table.occurences</i18n:text>-->
    <!--</th>-->
    <!--</tr>-->
    <!--</tfoot>-->
    <!--<tbody>-->
    <!--<xsl:for-each-->
    <!--select="./dri:table[starts-with(@id,'aspect.artifactbrowser.ConfigurableBrowse.table.browse-by-')]/dri:row[not(@role)]/dri:cell"-->
    <!--&gt;-->
    <!--<tr>-->
    <!--<td class="disable-math">-->
    <!--<a href="{./dri:xref/@target}">-->
    <!--<xsl:value-of-->
    <!--select="./dri:xref"-->
    <!--/>-->
    <!--</a>-->
    <!--</td>-->
    <!--<td>-->
    <!--<span class="label label-default label-pill float-left">-->
    <!--<xsl:value-of-->
    <!--select="./text()"-->
    <!--/>-->
    <!--</span>-->
    <!--</td>-->
    <!--</tr>-->
    <!--</xsl:for-each>-->
    <!--</tbody>-->
    <!--</table>-->
    <!--</div>-->
    <!--</xsl:if>-->
    <!--</xsl:template>-->

    <!--&lt;!&ndash;<xsl:template&ndash;&gt;-->
    <!--&lt;!&ndash;match="dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-navigation']"&ndash;&gt;-->
    <!--&lt;!&ndash;&gt;&ndash;&gt;-->
    <!--&lt;!&ndash;<form method="{@method}" action="{@action}">&ndash;&gt;-->
    <!--&lt;!&ndash;<div class="form-group row">&ndash;&gt;-->
    <!--&lt;!&ndash;<div class="col-4 hidden-md-up">&ndash;&gt;-->
    <!--&lt;!&ndash;<xsl:apply-templates&ndash;&gt;-->
    <!--&lt;!&ndash;select="./dri:div[@rend='row']/dri:div/dri:field"&ndash;&gt;-->
    <!--&lt;!&ndash;/>&ndash;&gt;-->
    <!--&lt;!&ndash;</div>&ndash;&gt;-->
    <!--&lt;!&ndash;<div class="col-8 col-md-12">&ndash;&gt;-->
    <!--&lt;!&ndash;<div class="hidden-sm-down">&ndash;&gt;-->
    <!--&lt;!&ndash;<xsl:apply-templates&ndash;&gt;-->
    <!--&lt;!&ndash;select="./dri:div[@rend='row']/dri:div/dri:list"&ndash;&gt;-->
    <!--&lt;!&ndash;/>&ndash;&gt;-->
    <!--&lt;!&ndash;</div>&ndash;&gt;-->
    <!--&lt;!&ndash;<div>&ndash;&gt;-->
    <!--&lt;!&ndash;<div class="input-group">&ndash;&gt;-->
    <!--&lt;!&ndash;<xsl:for-each&ndash;&gt;-->
    <!--&lt;!&ndash;select="./dri:div[@rend='row']/dri:div/dri:p[@rend='hidden']/dri:field"&ndash;&gt;-->
    <!--&lt;!&ndash;&gt;&ndash;&gt;-->
    <!--&lt;!&ndash;<input type="hidden" name="{@n}" value="{./dri:value}"/>&ndash;&gt;-->
    <!--&lt;!&ndash;</xsl:for-each>&ndash;&gt;-->
    <!--&lt;!&ndash;<input type="text" name="starts_with" class="form-control">&ndash;&gt;-->
    <!--&lt;!&ndash;<xsl:attribute name="placeholder">&ndash;&gt;-->
    <!--&lt;!&ndash;<xsl:text>xmlui.ArtifactBrowser.ConfigurableBrowse.general.starts_with_help</xsl:text>&ndash;&gt;-->
    <!--&lt;!&ndash;</xsl:attribute>&ndash;&gt;-->
    <!--&lt;!&ndash;<xsl:attribute name="i18n:attr">placeholder</xsl:attribute>&ndash;&gt;-->
    <!--&lt;!&ndash;</input>&ndash;&gt;-->
    <!--&lt;!&ndash;<span class="input-group-btn">&ndash;&gt;-->
    <!--&lt;!&ndash;<button class="btn btn-secondary" type="submit">&ndash;&gt;-->
    <!--&lt;!&ndash;<i18n:text>xmlui.general.go</i18n:text>&ndash;&gt;-->
    <!--&lt;!&ndash;</button>&ndash;&gt;-->
    <!--&lt;!&ndash;</span>&ndash;&gt;-->
    <!--&lt;!&ndash;</div>&ndash;&gt;-->
    <!--&lt;!&ndash;</div>&ndash;&gt;-->
    <!--&lt;!&ndash;</div>&ndash;&gt;-->
    <!--&lt;!&ndash;</div>&ndash;&gt;-->
    <!--&lt;!&ndash;</form>&ndash;&gt;-->
    <!--&lt;!&ndash;</xsl:template>&ndash;&gt;-->

    <!--<xsl:template-->
    <!--match="dri:div[@id='aspect.artifactbrowser.ConfigurableBrowse.div.browse-controls']"-->
    <!--&gt;-->
    <!--tadada-->
    <!--<form action="{@action}" method="{@method}" id="{translate(@id,'.','_')}" class="float-right">-->
    <!--<div class="hidden">-->
    <!--<xsl:for-each-->
    <!--select="./dri:p[@n='hidden-fields']/dri:field[@type='hidden']"-->
    <!--&gt;-->
    <!--<input type="hidden" name="{@n}" value="{./dri:value}"/>-->
    <!--</xsl:for-each>-->
    <!--<xsl:for-each-->
    <!--select="./dri:p/dri:field"-->
    <!--&gt;-->
    <!--<xsl:apply-templates-->
    <!--select="."-->
    <!--/>-->
    <!--</xsl:for-each>-->
    <!--</div>-->
    <!--<div class="dropdown">-->
    <!--<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu1"-->
    <!--data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">-->
    <!--<i class="fa fa-cog"></i>-->
    <!--</button>-->
    <!--<div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">-->
    <!--<xsl:for-each-->
    <!--select="./dri:p[not(@n='hidden-fields')]"-->
    <!--&gt;-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="./dri:field"-->
    <!--&gt;-->
    <!--<xsl:for-each-->
    <!--select="./dri:field/dri:option"-->
    <!--&gt;-->
    <!--<a class="dropdown-item" data-name="{parent::*/@n}" data-value="{./@returnValue}"-->
    <!--href="#">-->
    <!--<span class="btn-xs invisible">-->
    <!--<xsl:if-->
    <!--test="parent::*/dri:value[@option=current()/@returnValue]"-->
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
    <!--select="."-->
    <!--/>-->
    <!--</i18n:text>-->
    <!--</a>-->
    <!--</xsl:for-each>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<h6 class="dropdown-header">-->
    <!--<i18n:text>-->
    <!--<xsl:value-of-->
    <!--select="."-->
    <!--/>-->
    <!--</i18n:text>-->
    <!--</h6>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</xsl:for-each>-->
    <!--</div>-->
    <!--</div>-->
    <!--</form>-->
    <!--</xsl:template>-->

    <!--<xsl:template-->
    <!--match="dri:div[@pagination='simple']"-->
    <!--mode="pagination"-->
    <!--&gt;-->
    <!--<i18n:translate>-->
    <!--<xsl:choose>-->
    <!--<xsl:when test="@itemsTotal = -1">-->
    <!--<i18n:text>xmlui.dri2xhtml.structural.pagination-info.nototal</i18n:text>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<i18n:text>xmlui.dri2xhtml.structural.pagination-info</i18n:text>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--<i18n:param>-->
    <!--<xsl:value-of select="@firstItemIndex"/>-->
    <!--</i18n:param>-->
    <!--<i18n:param>-->
    <!--<xsl:value-of select="@lastItemIndex"/>-->
    <!--</i18n:param>-->
    <!--<i18n:param>-->
    <!--<xsl:value-of select="@itemsTotal"/>-->
    <!--</i18n:param>-->
    <!--</i18n:translate>-->
    <!--</xsl:template>-->
</xsl:stylesheet>
