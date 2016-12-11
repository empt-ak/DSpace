<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-html-head.xsl
    Created on : July 24, 2015, 10:02 AM
    Author     : emptak
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                xmlns:dri="http://di.tamu.edu/DRI/1.0/"
                xmlns:mets="http://www.loc.gov/METS/"
                xmlns:xlink="http://www.w3.org/TR/xlink/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:dim="http://www.dspace.org/xmlns/dspace/dim"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:mods="http://www.loc.gov/mods/v3"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns="http://www.w3.org/1999/xhtml"
                exclude-result-prefixes="i18n dri mets xlink xsl dim xhtml mods dc">

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>

    <xsl:template name="navbar">
        <nav class="navbar navbar-light bg-faded">
            <a class="navbar-brand" href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='contextPath']}">
                <img src="{concat($resourcePath,'/img/dml-logo.gif')}" alt="page.general.banner"
                     i18n:attribute="alt" width="30" height="30"/>
                <i18n:text>page.head.title</i18n:text>
            </a>
            <ul class="nav navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="supportedContentDropdown"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i18n:text>page.sidebar.left.browseby</i18n:text>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="supportedContentDropdown">
                        <xsl:for-each
                                select="/dri:document/dri:options/dri:list[@id='aspect.viewArtifacts.Navigation.list.browse']/dri:list[@id='aspect.browseArtifacts.Navigation.list.global']/dri:item"
                        >
                            <xsl:if test="not(contains(dri:xref/@target, 'community-list'))">
                                <a href="{./dri:xref/@target}" class="dropdown-item">
                                    <i18n:text>
                                        <xsl:value-of
                                                select="./dri:xref"
                                        />
                                    </i18n:text>
                                </a>
                            </xsl:if>
                        </xsl:for-each>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="{concat($contextPath,'/aboutus')}">
                        <i18n:text>navigation.main.button.aboutus</i18n:text>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="{concat($contextPath,'/news')}">
                        <i18n:text>navigation.main.button.news</i18n:text>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="{concat($contextPath,'/faq')}">
                        <i18n:text>navigation.main.button.faq</i18n:text>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='page' and @qualifier='contactURL']}">
                        <i18n:text>navigation.main.button.contactus</i18n:text>
                    </a>
                </li>
            </ul>

            <ul class="nav navbar-nav float-right ml-1">
                <li class="nav-item">
                    <a class="nav-link"
                       href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='advancedURL']}">
                        Advanced Search
                    </a>
                </li>
            </ul>
            <form class="form-inline float-right"
                  action="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='advancedURL']}">
                <div class="input-group">
                    <input type="text" class="form-control rounded-0" placeholder="navigation.main.button.search"
                           i18n:attr="placeholder"
                           name="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='queryField']}"/>
                    <span class="input-group-btn">
                        <button class="btn btn-outline-success rounded-0" type="submit">
                            <i class="fa fa-search"></i>
                        </button>
                    </span>
                </div>
            </form>
        </nav>
    </xsl:template>


    <xsl:template name="sidebar">
        <div class="card sidebar">
            <!--<div class="card-block text-center">-->
                <!--<a href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='contextPath']}">-->
                    <!--<img src="{concat($resourcePath,'/img/dml-logo.gif')}" alt="page.general.banner"-->
                         <!--i18n:attribute="alt" class="dspace-banner"/>-->
                <!--</a>-->
            <!--</div>-->
            <xsl:if
                    test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.subject']/dri:item"
            >
                <div class="card-header">
                    <i18n:text>page.sidebar.right.discovery.keyword</i18n:text>
                </div>
                <ul class="list-group list-group-flush">
                    <xsl:for-each
                            select="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.subject']/dri:item/dri:xref"
                    >
                        <li class="list-group-item">
                            <a class="disable-math">
                                <xsl:attribute
                                        name="href"
                                >
                                    <xsl:value-of
                                            select="./@target"
                                    />
                                </xsl:attribute>
                                <xsl:choose>
                                    <xsl:when
                                            test="substring(./@target,string-length(./@target)- 26) = 'search-filter?field=subject'"
                                    >
                                        <i18n:text>page.sidebar.right.discovery.more</i18n:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <span class="badge badge-info float-right">
                                            <xsl:variable
                                                    name="badge"
                                            >
                                                <xsl:call-template name="substring-after-last">
                                                    <xsl:with-param name="string" select="./text()"/>
                                                    <xsl:with-param name="delimiter" select="'('"/>
                                                </xsl:call-template>
                                            </xsl:variable>

                                            <xsl:value-of
                                                    select="substring-before($badge,')')"
                                            />
                                        </span>
                                        <xsl:value-of
                                                select="substring-before(./text(),' (')"
                                        />
                                    </xsl:otherwise>
                                </xsl:choose>
                            </a>
                        </li>
                    </xsl:for-each>
                </ul>
            </xsl:if>
            <xsl:if
                    test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.author']/dri:item"
            >
                <div class="card-header">
                    <i18n:text>page.sidebar.right.discovery.author</i18n:text>
                </div>
                <ul class="list-group list-group-flush">
                    <!-- todo if we are at browse by author item has no xref element -->
                    <xsl:for-each
                            select="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.author']/dri:item/dri:xref"
                    >
                        <li class="list-group-item">
                            <a>
                                <xsl:attribute
                                        name="href"
                                >
                                    <xsl:value-of
                                            select="./@target"
                                    />
                                </xsl:attribute>
                                <xsl:choose>
                                    <xsl:when
                                            test="substring(./@target,string-length(./@target)- 25) = 'search-filter?field=author'"
                                    >
                                        <i18n:text>page.sidebar.right.discovery.more</i18n:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <span class="badge badge-info float-right">
                                            <xsl:value-of
                                                    select="substring-before(substring-after(./text(),'('),')')"
                                            />
                                        </span>
                                        <xsl:value-of
                                                select="substring-before(./text(),' (')"
                                        />
                                    </xsl:otherwise>
                                </xsl:choose>
                            </a>
                        </li>
                    </xsl:for-each>
                </ul>
            </xsl:if>
            <xsl:if
                    test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.msc']/dri:item"
            >
                <div class="card-header">
                    <i18n:text>page.sidebar.right.discovery.msc</i18n:text>
                </div>
                <ul class="list-group list-group-flush">
                    <xsl:for-each
                            select="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.msc']/dri:item/dri:xref"
                    >
                        <li class="list-group-item">
                            <a>
                                <xsl:attribute
                                        name="href"
                                >
                                    <xsl:value-of
                                            select="./@target"
                                    />
                                </xsl:attribute>
                                <xsl:choose>
                                    <xsl:when
                                            test="substring(./@target,string-length(./@target)- 22) = 'search-filter?field=msc'"
                                    >
                                        <i18n:text>page.sidebar.right.discovery.more</i18n:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <span class="badge badge-info float-right">
                                            <xsl:value-of
                                                    select="substring-before(substring-after(./text(),'('),')')"
                                            />
                                        </span>
                                        <xsl:value-of
                                                select="substring-before(./text(),' (')"
                                        />
                                    </xsl:otherwise>
                                </xsl:choose>
                            </a>
                        </li>
                    </xsl:for-each>
                </ul>
            </xsl:if>
            <xsl:if
                    test="/dri:document/dri:body/dri:div[@id='aspect.discovery.RelatedItems.div.item-related-container']/dri:div[@id='aspect.discovery.RelatedItems.div.item-related']/dri:referenceSet[@id='aspect.discovery.RelatedItems.referenceSet.item-related-items']"
            >
                <div class="card-header">
                    <i18n:text>page.sidebar.right.related.items</i18n:text>
                </div>
                <ul class="list-group list-group-flush">
                    <xsl:for-each
                            select="/dri:document/dri:body/dri:div[@id='aspect.discovery.RelatedItems.div.item-related-container']/dri:div[@id='aspect.discovery.RelatedItems.div.item-related']/dri:referenceSet[@id='aspect.discovery.RelatedItems.referenceSet.item-related-items']/dri:reference"
                    >
                        <xsl:variable
                                name="relatedItemMetadata"
                        >
                            <xsl:text>cocoon://</xsl:text>
                            <xsl:value-of
                                    select="./@url"
                            />
                            <xsl:text>?sections=dmdSec</xsl:text>
                        </xsl:variable>
                        <li class="list-group-item">
                            <a href="{./@url}">
                                <xsl:value-of
                                        select="document($relatedItemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                                />
                            </a>
                        </li>
                    </xsl:for-each>
                </ul>
            </xsl:if>
        </div>
    </xsl:template>


    <!--<xsl:template name="buildNavigation">-->
    <!--<nav class="navbar navbar-light bg-faded">-->
    <!--<button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#exCollapsingNavbar2">-->
    <!--&#9776;-->
    <!--</button>-->
    <!--<div class="collapse navbar-toggleable-sm" id="exCollapsingNavbar2">-->
    <!--<ul class="nav navbar-nav">-->
    <!--<li class="nav-item">-->
    <!--<a class="nav-link">-->
    <!--<xsl:attribute-->
    <!--name="href"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="concat($contextPath,'/aboutus')"-->
    <!--/>-->
    <!--</xsl:attribute>-->
    <!--<i18n:text>navigation.main.button.aboutus</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li class="nav-item">-->
    <!--<a class="nav-link" href="{concat($contextPath,'/news')}">-->
    <!--<i18n:text>navigation.main.button.news</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li class="nav-item">-->
    <!--<a class="nav-link" href="{concat($contextPath,'/faq')}">-->
    <!--<i18n:text>navigation.main.button.faq</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li class="nav-item">-->
    <!--<a class="nav-link" href="{concat($contextPath,'/conditions')}">-->
    <!--<i18n:text>navigation.main.button.terms</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li class="nav-item">-->
    <!--<a class="nav-link" href="{concat($contextPath,'/archives')}">-->
    <!--<i18n:text>navigation.main.button.matharchives</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li class="nav-item">-->
    <!--<a class="nav-link" href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='page' and @qualifier='contactURL']}">-->
    <!--<i18n:text>navigation.main.button.contactus</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--</ul>-->
    <!--<form class="form-inline navbar-form pull-xs-left pull-xl-right" method="post">-->
    <!--<xsl:attribute-->
    <!--name="action"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='advancedURL']"-->
    <!--/>-->
    <!--</xsl:attribute>-->
    <!--<input-->
    <!--type="text" -->
    <!--class="form-control" -->
    <!--placeholder="navigation.main.button.search"-->
    <!--i18n:attr="placeholder" -->
    <!--&gt;-->
    <!--<xsl:attribute-->
    <!--name="name"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='queryField']"-->
    <!--/>-->
    <!--</xsl:attribute>-->
    <!--</input>-->
    <!--<button class="btn btn-success-outline" type="submit">-->
    <!--<span class="fa fa-search"></span>-->
    <!--</button>-->
    <!--</form>-->
    <!--</div>-->
    <!--</nav>       -->
    <!--</xsl:template>-->
    <!---->
    <!--<xsl:template -->
    <!--name="buildBreadcrumb"-->
    <!--&gt;-->
    <!--<xsl:if -->
    <!--test="count(/dri:document/dri:meta/dri:pageMeta/dri:trail) > 1"-->
    <!--&gt;-->
    <!--<nav class="row">-->
    <!--<div class="col-xs-12">-->
    <!--<ol class="breadcrumb">-->
    <!--<xsl:for-each-->
    <!--select="/dri:document/dri:meta/dri:pageMeta/dri:trail"-->
    <!--&gt;-->
    <!--<li>-->
    <!--<xsl:if-->
    <!--test="position() = 1">-->
    <!--<i class="fa fa-home" />-->
    <!--<xsl:text> </xsl:text>-->
    <!--</xsl:if>-->
    <!--<xsl:if-->
    <!--test="position() = last()"-->
    <!--&gt;-->
    <!--<xsl:attribute-->
    <!--name="class"-->
    <!--&gt;-->
    <!--<xsl:text>active</xsl:text>-->
    <!--</xsl:attribute>-->
    <!--</xsl:if>-->
    <!--<xsl:choose>-->
    <!--<xsl:when-->
    <!--test="position() != last()"-->
    <!--&gt;-->
    <!--<a>-->
    <!--<xsl:attribute-->
    <!--name="href"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="./@target"-->
    <!--/>-->
    <!--</xsl:attribute>-->
    <!--<xsl:copy-of-->
    <!--select="."-->
    <!--/>-->
    <!--</a>-->
    <!--</xsl:when>-->
    <!--&lt;!&ndash;-->
    <!--last entry in trail chain is xmlui.ArtifactBrowser.ItemViewer.trail-->
    <!--when we reach item-->
    <!--&ndash;&gt;-->
    <!--<xsl:when-->
    <!--test="count(/dri:document/dri:meta/dri:pageMeta/dri:trail) = 5"-->
    <!--&gt;-->
    <!--<i18n:text>navigation.breadcrumb.viewitem</i18n:text>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<xsl:copy-of-->
    <!--select="."-->
    <!--/>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>                                 -->
    <!--</li>-->
    <!--</xsl:for-each>-->
    <!--</ol>-->
    <!--</div>            -->
    <!--</nav>-->
    <!--</xsl:if>        -->
    <!--</xsl:template>-->
    <!---->
    <!--<xsl:template -->
    <!--name="buildPUN"-->
    <!--&gt; -->
    <!--<div class="pun hidden-sm-down">      -->
    <!--<ul class="pager">-->
    <!--<li class="pager-prev disabled">-->
    <!--<a href="#">-->
    <!--&lt;!&ndash;                        <span class="hidden-xl-down">-->
    <!--&#8592;-->
    <!--</span>&ndash;&gt;-->
    <!--<i18n:text>navigation.pun.previous</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li class="pager-next disabled">-->
    <!--<a href="#">                        -->
    <!--<i18n:text>navigation.pun.next</i18n:text>-->
    <!--&lt;!&ndash;                        &#8594;&ndash;&gt;-->
    <!--</a>-->
    <!--</li>-->
    <!--</ul>-->
    <!--</div>-->
    <!--</xsl:template>-->
    <!---->
    <!--<xsl:template -->
    <!--name="buildFooter"-->
    <!--&gt;-->
    <!--<div class="footer-content">-->
    <!--<div class="row">-->
    <!--<div class="col-md-6 col-md-offset-3 col-xl-8 col-xl-offset-2 col-xs-12">-->
    <!--<div class="row">-->
    <!--<div class="col-md-4 col-xs-12">-->
    <!--<h4>-->
    <!--<i18n:text>page.footer.partners.title</i18n:text>-->
    <!--</h4>-->
    <!--<ul>-->
    <!--<li>-->
    <!--<a href="#">-->
    <!--<i18n:text>page.footer.partners.ascr</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="#">-->
    <!--<i18n:text>page.footer.partners.eudml</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="#">-->
    <!--<i18n:text>page.footer.partners.ics</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="#">-->
    <!--<i18n:text>page.footer.partners.fimu</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="#">-->
    <!--<i18n:text>page.footer.partners.ficuni</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="#">-->
    <!--<i18n:text>page.footer.partners.libprague</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--</ul>-->
    <!--</div>-->
    <!--<div class="col-md-4 col-xs-12">-->
    <!--<h4>-->
    <!--<i18n:text>page.footer.links.title</i18n:text>-->
    <!--</h4>-->
    <!--<ul>-->
    <!--<li>-->
    <!--<a href="{concat($contextPath,'/sitemap')}">-->
    <!--<i18n:text>page.footer.links.sitemap</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="{concat($contextPath,'/aboutus')}">-->
    <!--<i18n:text>page.footer.links.aboutus</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="{concat($contextPath,'/news')}">-->
    <!--<i18n:text>page.footer.links.news</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="{concat($contextPath,'/faq')}">-->
    <!--<i18n:text>page.footer.links.faq</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="{concat($contextPath,'/conditions')}">-->
    <!--<i18n:text>page.footer.links.terms</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="{concat($contextPath,'/archives')}">-->
    <!--<i18n:text>page.footer.links.matharchives</i18n:text>-->
    <!--</a>-->
    <!--</li>-->
    <!--<li>-->
    <!--<a href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='page' and @qualifier='contactURL']}">-->
    <!--<i18n:text>page.footer.links.contactus</i18n:text>-->
    <!--</a>-->
    <!--</li>                                                  -->
    <!--</ul>-->
    <!--</div>-->
    <!--<div class="col-md-4 col-xs-12">-->
    <!--<div class="hidden-sm-down">-->
    <!--<xsl:if-->
    <!--test="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='feed']"-->
    <!--&gt;  -->
    <!--<h4>-->
    <!--<i18n:text>page.sidebar.left.rss</i18n:text>-->
    <!--</h4>-->
    <!--<ul>-->
    <!--<xsl:for-each-->
    <!--select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='feed']"-->
    <!--&gt;-->
    <!--<li>-->
    <!--<i class="fa fa-rss" />-->
    <!--<xsl:text> </xsl:text>-->
    <!--<a>-->
    <!--<xsl:attribute-->
    <!--name="href"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="." -->
    <!--/>-->
    <!--</xsl:attribute>                                -->
    <!--<xsl:choose>-->
    <!--<xsl:when -->
    <!--test="contains(., 'rss_1.0')"-->
    <!--&gt;-->
    <!--<xsl:text>RSS 1.0</xsl:text>-->
    <!--</xsl:when>-->
    <!--<xsl:when -->
    <!--test="contains(., 'rss_2.0')"-->
    <!--&gt;-->
    <!--<xsl:text>RSS 2.0</xsl:text>-->
    <!--</xsl:when>-->
    <!--<xsl:when -->
    <!--test="contains(., 'atom_1.0')"-->
    <!--&gt;-->
    <!--<xsl:text>Atom</xsl:text>-->
    <!--</xsl:when>-->
    <!--<xsl:otherwise>-->
    <!--<xsl:value-of -->
    <!--select="@qualifier"-->
    <!--/>-->
    <!--</xsl:otherwise>-->
    <!--</xsl:choose>-->
    <!--</a>-->
    <!--</li>-->
    <!--</xsl:for-each>-->
    <!--</ul>-->
    <!--</xsl:if>-->
    <!--</div>-->
    <!--&lt;!&ndash;-->
    <!--<h4>-->
    <!--<i18n:text>page.footer.social.title</i18n:text>-->
    <!--</h4>-->
    <!--<ul class="list-inline">-->
    <!--<li>-->
    <!--<i class="fa fa-facebook-square fa-3x" />-->
    <!--</li>-->
    <!--<li>-->
    <!--<i class="fa fa-google-plus fa-3x" />-->
    <!--</li>-->
    <!--<li> -->
    <!--<i class="fa fa-twitter fa-3x" />-->
    <!--</li>-->
    <!--</ul>-->
    <!--&ndash;&gt;                           -->
    <!--</div>-->
    <!--</div>-->
    <!--</div>-->
    <!--</div>-->
    <!--<div class="col-md-6 col-md-offset-3 col-xl-8 col-xl-offset-2 col-xs-12">            -->
    <!--<p class="footer-text">-->
    <!--<xsl:text>&#169; 2016 </xsl:text>-->
    <!--<a href="#">-->
    <!--<i18n:text>page.footer.copyright.text</i18n:text>-->
    <!--</a>-->
    <!--<xsl:text> </xsl:text>-->
    <!--<i18n:text>page.footer.contact</i18n:text>                    -->
    <!--<a href="#">-->
    <!--<span class="contact-email">-->
    <!--<i18n:text>page.footer.contact.javascript</i18n:text>-->
    <!--</span>-->
    <!--</a>-->
    <!--</p>                    -->
    <!--</div>-->
    <!--</div>-->
    <!--</xsl:template>-->
    <!---->
    <!--<xsl:template -->
    <!--name="buildBodyHead"-->
    <!--&gt;-->
    <!--<xsl:if-->
    <!--test="contains($debug,'true')"-->
    <!--&gt;-->
    <!--<div class="alert alert-danger">-->
    <!--<xsl:value-of select="$solrServer" />-->
    <!--<br />-->
    <!---->
    <!--<xsl:value-of select="$communityType" />-->
    <!--<br/>-->
    <!--<xsl:variable name="solrQuery">-->
    <!--<xsl:text>select?q=*%3A*&amp;fq=location%3A</xsl:text>-->
    <!--<xsl:call-template-->
    <!--name="getSolrLocation"-->
    <!--/>-->
    <!--<xsl:text>&amp;rows=0&amp;wt=xml&amp;facet=true&amp;facet.field=msc_keyword</xsl:text>-->
    <!--</xsl:variable>-->
    <!--<xsl:value-of-->
    <!--select="$solrQuery"-->
    <!--/>-->
    <!--</div>-->
    <!--</xsl:if>-->
    <!--<div class="row">-->
    <!--<div class="col-sm-2 hidden-sm-down">-->
    <!--<a href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='contextPath']}" >-->
    <!--<img alt="page.general.banner" i18n:attribute="alt" class="dspace-banner">-->
    <!--<xsl:attribute-->
    <!--name="src"-->
    <!--&gt;-->
    <!--<xsl:value-of-->
    <!--select="$resourcePath"-->
    <!--/>-->
    <!--<xsl:text>/img/dml-logo.gif</xsl:text>-->
    <!--</xsl:attribute>-->
    <!--</img>-->
    <!--</a>            -->
    <!--</div>-->
    <!--<div class="col-sm-10">-->
    <!--<p class="align-baseline">-->
    <!--<a href="{$contextPath}" class="page-header">-->
    <!--<h1 class="align-baseline">-->
    <!--<i18n:text>page.head.title</i18n:text>-->
    <!--</h1>-->
    <!--</a>-->
    <!--</p> -->
    <!--</div>-->
    <!--</div>-->
    <!--</xsl:template>-->
</xsl:stylesheet>
