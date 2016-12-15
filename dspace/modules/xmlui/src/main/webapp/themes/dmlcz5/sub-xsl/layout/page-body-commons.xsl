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
            <nav class="navbar navbar-light bg-faded">
                <button class="navbar-toggler hidden-lg-up" type="button" data-toggle="collapse"
                        data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                        aria-label="Toggle navigation"></button>
                <a class="navbar-brand"
                   href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='contextPath']}">
                    <!--<img src="{concat($resourcePath,'/img/dml-logo.gif')}" alt="page.general.banner"-->
                    <!--i18n:attribute="alt" width="30" height="30"/>-->
                    <i18n:text>page.head.title</i18n:text>
                </a>
                <div class="collapse navbar-toggleable-md" id="navbarResponsive">
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
                                <i18n:text>navigation.search.advanced</i18n:text>
                            </a>
                        </li>
                    </ul>
                    <form class="form-inline float-right"
                          action="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='advancedURL']}">
                        <div class="input-group">
                            <input type="text" class="form-control rounded-0"
                                   placeholder="navigation.main.button.search"
                                   i18n:attr="placeholder"
                                   name="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='queryField']}"/>
                            <span class="input-group-btn">
                                <button class="btn btn-outline-success rounded-0" type="submit">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                    </form>
                </div>
            </nav>
        </nav>
    </xsl:template>

    <xsl:template name="math-help">
        <div class="modal fade" id="math-help" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">
                                <i class="fa fa-times" aria-hidden="true"></i>
                            </span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            <i18n:text>math.help.modal.title</i18n:text>
                        </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <div class="col-12 disable-math">
                                <i18n:text>math.help.modal.text</i18n:text>
                            </div>
                        </div>
                        <xhtml:h3>
                            <i18n:text>math.help.modal.examples</i18n:text>
                        </xhtml:h3>
                        <xhtml:table class="table">
                            <xhtml:thead>
                                <xhtml:tr>
                                    <xhtml:th>
                                        <i18n:text>math.help.modal.input</i18n:text>
                                    </xhtml:th>
                                    <xhtml:th>
                                        <i18n:text>math.help.modal.output</i18n:text>
                                    </xhtml:th>
                                </xhtml:tr>
                            </xhtml:thead>
                            <xhtml:tbody>
                                <xhtml:tr>
                                    <xhtml:td class="disable-math">$x^2 + y^2 = z^2$</xhtml:td>
                                    <xhtml:td>$x^2 + y^2 = z^2$</xhtml:td>
                                </xhtml:tr>
                                <xhtml:tr>
                                    <xhtml:td class="disable-math">$\iiint_V \mu(u,v,w) \,du\,dv\,dw$</xhtml:td>
                                    <xhtml:td>$\iiint_V \mu(u,v,w) \,du\,dv\,dw$</xhtml:td>
                                </xhtml:tr>
                                <xhtml:tr>
                                    <xhtml:td class="disable-math">$\sum_{n=1}^{\infty} 2^{-n} = 1$</xhtml:td>
                                    <xhtml:td>$\sum_{n=1}^{\infty} 2^{-n} = 1$</xhtml:td>
                                </xhtml:tr>
                                <xhtml:tr>
                                    <xhtml:td class="disable-math">$$\binom{n}{k} = \frac{n!}{k!(n-k)!}$$</xhtml:td>
                                    <xhtml:td>$$\binom{n}{k} = \frac{n!}{k!(n-k)!}$$</xhtml:td>
                                </xhtml:tr>
                            </xhtml:tbody>
                        </xhtml:table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <i18n:text>page.general.close</i18n:text>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="sidebar">
        <div class="card sidebar">
            <xsl:choose>
                <xsl:when
                        test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/
                        dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.subject']/dri:item |
                        /dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/
                        dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.author']/dri:item |
                        /dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/
                        dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.msc']/dri:item |
                        /dri:document/dri:body/dri:div[@id='aspect.discovery.RelatedItems.div.item-related-container']/
                        dri:div[@id='aspect.discovery.RelatedItems.div.item-related']/dri:referenceSet[@id='aspect.discovery.RelatedItems.referenceSet.item-related-items']"
                >
                    <xsl:if
                            test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.subject']/dri:item"
                    >
                        <div class="card-header">
                            <i18n:text>page.sidebar.discovery.keyword</i18n:text>
                        </div>
                        <ul class="list-group list-group-flush">
                            <xsl:for-each
                                    select="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.subject']/dri:item"
                            >
                                <xsl:apply-templates select="." mode="sidebar"/>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>
                    <xsl:if
                            test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.author']/dri:item"
                    >
                        <div class="card-header">
                            <i18n:text>page.sidebar.discovery.author</i18n:text>
                        </div>
                        <ul class="list-group list-group-flush">
                            <xsl:for-each
                                    select="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.author']/dri:item"
                            >
                                <xsl:apply-templates select="." mode="sidebar"/>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>
                    <xsl:if
                            test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.msc']/dri:item"
                    >
                        <div class="card-header">
                            <i18n:text>page.sidebar.discovery.msc</i18n:text>
                        </div>
                        <ul class="list-group list-group-flush">
                            <xsl:for-each
                                    select="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.msc']/dri:item"
                            >
                                <xsl:apply-templates select="." mode="sidebar"/>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>
                    <xsl:if
                            test="/dri:document/dri:body/dri:div[@id='aspect.discovery.RelatedItems.div.item-related-container']/dri:div[@id='aspect.discovery.RelatedItems.div.item-related']/dri:referenceSet[@id='aspect.discovery.RelatedItems.referenceSet.item-related-items']"
                    >
                        <div class="card-header">
                            <i18n:text>page.sidebar.related.items</i18n:text>
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
                </xsl:when>
                <xsl:otherwise>
                    <xhtml:div class="card-block text-muted">
                        <i18n:text>page.sidebar.noinfo</i18n:text>
                        <xhtml:div class="dropdown ">
                            <xhtml:button class="btn btn-secondary dropdown-toggle" id="rss-subscribe"
                                          data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fa fa-rss"/>
                                <xsl:text>RSS</xsl:text>
                            </xhtml:button>

                            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                <a class="dropdown-item" href="#">
                                    <xsl:text>RSS 1.0</xsl:text>
                                </a>
                                <a class="dropdown-item" href="#">
                                    <xsl:text>RSS 2.0</xsl:text>
                                </a>
                                <a class="dropdown-item" href="#">
                                    <xsl:text>Atom</xsl:text>
                                </a>
                            </div>
                        </xhtml:div>
                    </xhtml:div>
                    <xhtml:div class="card-header">
                        <i18n:text>page.sidebar.random</i18n:text>
                    </xhtml:div>
                    <xhtml:ul class="list-group list-group-flush">
                        <xhtml:li class="list-group-item">
                            <a href="#">!dummy text</a>
                        </xhtml:li>
                        <xhtml:li class="list-group-item">
                            <a href="#">!dummy text</a>
                        </xhtml:li>
                    </xhtml:ul>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>

    <xsl:template match="dri:item" mode="sidebar">
        <li class="list-group-item">
            <xsl:if test="@rend='selected'">
                <xsl:attribute name="class">
                    <xsl:text>list-group-item list-group-item-info</xsl:text>
                </xsl:attribute>
            </xsl:if>
            <a class="disable-math" href="{./dri:xref/@target}">
                <xsl:choose>
                    <xsl:when
                            test="./dri:xref/i18n:text"
                    >
                        <i18n:text>page.sidebar.discovery.more</i18n:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <span class="badge badge-info float-right">
                            <xsl:variable
                                    name="badge"
                            >
                                <!-- if ./rend='selected' == true then this node has no xref children
                                 so we pass value of this node like | ./text()
                                 -->
                                <xsl:call-template name="substring-after-last">
                                    <xsl:with-param name="string" select="./dri:xref/text() | ./text()"/>
                                    <xsl:with-param name="delimiter" select="'('"/>
                                </xsl:call-template>
                            </xsl:variable>

                            <xsl:value-of
                                    select="substring-before($badge,')')"
                            />
                        </span>
                        <xsl:value-of
                                select="substring-before(./dri:xref/text() | ./text(),' (')"
                        />
                    </xsl:otherwise>
                </xsl:choose>
            </a>
        </li>
    </xsl:template>

    <xsl:template name="footer">
        <div class="container-fluid footer-container pt-4">
            <xhtml:footer class="row">
                <xhtml:div class="offset-lg-2 offset-md-3 col-lg-2 col-12 hidden-md-down">
                    <xhtml:h3>
                        <i18n:text>xmlui.general.dspace_home</i18n:text>
                    </xhtml:h3>
                    <xsl:text> </xsl:text>
                    <i18n:text>page.footer.description</i18n:text>
                </xhtml:div>
                <xhtml:nav class="col-md-2 col-12">
                    <h3>
                        <i class="fa fa-sitemap"></i>
                        <i18n:text>page.footer.links.title</i18n:text>
                    </h3>
                    <ul class="list-unstyled">
                        <li>
                            <a href="{concat($contextPath,'/sitemap')}">
                                <i18n:text>page.footer.links.sitemap</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="{concat($contextPath,'/aboutus')}">
                                <i18n:text>navigation.main.button.aboutus</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="{concat($contextPath,'/news')}">
                                <i18n:text>navigation.main.button.news</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="{concat($contextPath,'/conditions')}">
                                <i18n:text>navigation.main.button.terms</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="{concat($contextPath,'/faq')}">
                                <i18n:text>navigation.main.button.faq</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="{concat($contextPath,'/archives')}">
                                <i18n:text>navigation.main.button.matharchives</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='page' and @qualifier='contactURL']}">
                                <i18n:text>navigation.main.button.contactus</i18n:text>
                            </a>
                        </li>
                    </ul>
                </xhtml:nav>

                <xsl:if
                        test="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='feed']"
                >
                    <xhtml:div class="col-md-2 col-12">
                        <h3>
                            <i class="fa fa-rss"/>
                            <xsl:text> </xsl:text>
                            <i18n:text>page.footer.subscribe</i18n:text>
                        </h3>rss
                        <ul class="list-unstyled">
                            <xsl:for-each
                                    select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='feed']"
                            >
                                <li>
                                    <xsl:text> </xsl:text>
                                    <a href="{.}">
                                        <xsl:choose>
                                            <xsl:when
                                                    test="contains(., 'rss_1.0')"
                                            >
                                                <xsl:text>RSS 1.0</xsl:text>
                                            </xsl:when>
                                            <xsl:when
                                                    test="contains(., 'rss_2.0')"
                                            >
                                                <xsl:text>RSS 2.0</xsl:text>
                                            </xsl:when>
                                            <xsl:when
                                                    test="contains(., 'atom_1.0')"
                                            >
                                                <xsl:text>Atom</xsl:text>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of
                                                        select="@qualifier"
                                                />
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </a>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xhtml:div>
                </xsl:if>

                <xhtml:div class="col-md-2 col-12">
                    <xhtml:h3>
                        <i18n:text>page.fotter.powered.title</i18n:text>
                    </xhtml:h3>
                    <ul class="list-unstyled">
                        <li>
                            <a href="https://mir.fi.muni.cz/mias/">
                                <i18n:text>page.fotter.powered.mias</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.dspace.org/">
                                <i18n:text>page.fotter.powered.dspace</i18n:text>
                            </a>
                        </li>
                    </ul>
                    <h3>
                        <i18n:text>page.footer.partners.title</i18n:text>
                    </h3>
                    <ul class="list-unstyled">
                        <li>
                            <a href="https://eudml.org/">
                                <i18n:text>page.footer.partners.eudml</i18n:text>
                            </a>
                        </li>
                    </ul>
                </xhtml:div>
                <xhtml:div class="col-md-1 col-12 hidden-xl-down">
                    <button type="button" class="btn btn-lg btn-outline-danger rounded-circle" data-toggle="tooltip"
                            data-placement="bottom" id="scroll-top"
                            title="page.footer.backtotop"
                            i18n:attr="title">
                        <i class="fa fa-chevron-up" aria-hidden="true"></i>
                    </button>
                </xhtml:div>
            </xhtml:footer>
            <xhtml:footer class="row">
                <div class="col-12 text-center">
                    <xsl:text>&#169; 2010&#8211; </xsl:text>
                    <span class="copyright-date"></span>
                    <a href="#">
                        <i18n:text>page.footer.copyright.text</i18n:text>
                    </a>
                    <i18n:text>page.footer.links.contactus</i18n:text>
                    <xsl:text> </xsl:text>
                    <a href="#">
                        <span class="contact-email">
                            <i18n:text>page.footer.contact.javascript</i18n:text>
                        </span>
                    </a>
                    <i18n:text>page.footer.links.contactus2</i18n:text>
                    <xsl:text> </xsl:text>
                    <a class="nav-link"
                       href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='page' and @qualifier='contactURL']}">
                        <i18n:text>page.footer.links.contactus3.form</i18n:text>
                    </a>
                </div>
            </xhtml:footer>
        </div>
    </xsl:template>

    <xsl:template name="breadcrumb">
        <ol class="breadcrumb">
            <xsl:for-each select="/dri:document/dri:meta/dri:pageMeta/dri:trail/text()">
                <xhtml:li class="breadcrumb-item">
                    <xhtml:a href="{./parent::dri:trail/@target}">
                        <xsl:value-of
                                select="."
                        />
                    </xhtml:a>
                </xhtml:li>
            </xsl:for-each>
        </ol>
    </xsl:template>
</xsl:stylesheet>
