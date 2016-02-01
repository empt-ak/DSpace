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
    
    <xsl:template name="navigation">
        <nav class="navbar navbar-dark navbar-fixed-top panel-color">
            <button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#exCollapsingNavbar2">
                <i class="fa fa-bars"></i>
            </button>
            <div class="collapse navbar-toggleable-sm" id="exCollapsingNavbar2">
                <a class="navbar-brand" href="{$contextPath}">
                    <i18n:text>page.head.title</i18n:text>
                </a>
                <ul class="nav navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link">
                            <xsl:attribute
                                name="href"
                            >
                                <xsl:value-of
                                    select="concat($contextPath,'/aboutus')"
                                />
                            </xsl:attribute>
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
                        <a class="nav-link" href="{concat($contextPath,'/conditions')}">
                            <i18n:text>navigation.main.button.terms</i18n:text>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="{concat($contextPath,'/archives')}">
                            <i18n:text>navigation.main.button.matharchives</i18n:text>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='page' and @qualifier='contactURL']}">
                            <i18n:text>navigation.main.button.contactus</i18n:text>
                        </a>
                    </li>
                    <li class="nav-item">
                        <div class="dropdown">
                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Other</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${context}/configuration/">Configurations</a>
                                <a class="dropdown-item" href="${context}/annotation/">AnnotationTags</a>
                            </div>
                        </div>
                    </li>
                </ul>
                <form class="form-inline navbar-form pull-xs-left pull-xl-right" method="post">
                    <xsl:attribute
                        name="action"
                    >
                        <xsl:value-of
                            select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='advancedURL']"
                        />
                    </xsl:attribute>
                    <div class="input-group">
                        <input
                            type="text" 
                            class="form-control" 
                            placeholder="navigation.main.button.search"
                            i18n:attr="placeholder" 
                        >
                            <xsl:attribute
                                name="name"
                            >
                                <xsl:value-of
                                    select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='search' and @qualifier='queryField']"
                                />
                            </xsl:attribute>
                        </input>
                        <span class="input-group-btn">
                            <button class="btn btn-secondary" type="submit">
                                <span class="fa fa-search"></span>
                            </button>
                        </span>
                    </div>
                </form>
            </div>
        </nav>       
    </xsl:template>
    
    <xsl:template 
        name="breadcrumb"
    >
        <xsl:if 
            test="count(/dri:document/dri:meta/dri:pageMeta/dri:trail) > 1"
        >
            <nav class="row">
                <div class="col-xs-12">
                    <ol class="breadcrumb">
                        <xsl:for-each
                            select="/dri:document/dri:meta/dri:pageMeta/dri:trail"
                        >
                            <li>
                                <xsl:if
                                    test="position() = 1">
                                    <i class="fa fa-home" />
                                    <xsl:text> </xsl:text>
                                </xsl:if>
                                <xsl:if
                                    test="position() = last()"
                                >
                                    <xsl:attribute
                                        name="class"
                                    >
                                        <xsl:text>active</xsl:text>
                                    </xsl:attribute>
                                </xsl:if>
                                <xsl:choose>
                                    <xsl:when
                                        test="position() != last()"
                                    >
                                        <a>
                                            <xsl:attribute
                                                name="href"
                                            >
                                                <xsl:value-of
                                                    select="./@target"
                                                />
                                            </xsl:attribute>                                                                       
                                            <xsl:value-of
                                                select="."
                                            />
                                        </a>
                                    </xsl:when>
                                    <!--
                                    last entry in trail chain is xmlui.ArtifactBrowser.ItemViewer.trail
                                    when we reach item
                                    -->
                                    <xsl:when
                                        test="count(/dri:document/dri:meta/dri:pageMeta/dri:trail) = 5"
                                    >
                                        <i18n:text>navigation.breadcrumb.viewitem</i18n:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of
                                            select="."
                                        />
                                    </xsl:otherwise>
                                </xsl:choose>                                 
                            </li>
                        </xsl:for-each>
                    </ol>
                </div>            
            </nav>
        </xsl:if>        
    </xsl:template>
    
    <xsl:template 
        name="footer"
    >
        <div class="col-xs-12">
            <div class="row footer-first-row">
                <div class="col-md-6 col-md-offset-3 col-xl-8 col-xl-offset-2 col-xs-12">
                    <div class="row">
                        <div class="col-xs-4">
                            <div class="banner-digilib"></div>
                            <address>
                                <strong>Digilib?</strong>
                                <br />
                                Arna Nováka 1 <br />
                                602 00 Brno <br />
                                Česká republika <br />
                                <abbr title="Email">E:</abbr> 
                                <a href="#">
                                    <span class="contact-email">
                                        <i18n:text>page.footer.contact.javascript</i18n:text>
                                    </span>
                                </a>
                            </address>
                        </div>
                        <div class="col-xs-4">
                            <h4>
                                <i18n:text>page.footer.links.title</i18n:text>
                            </h4>
                            <ul class="list-unstyled">
                                <li>
                                    <a href="{concat($contextPath,'/sitemap')}">
                                        <i18n:text>page.footer.links.sitemap</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="{concat($contextPath,'/aboutus')}">
                                        <i18n:text>page.footer.links.aboutus</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="{concat($contextPath,'/news')}">
                                        <i18n:text>page.footer.links.news</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="{concat($contextPath,'/faq')}">
                                        <i18n:text>page.footer.links.faq</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="{concat($contextPath,'/conditions')}">
                                        <i18n:text>page.footer.links.terms</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="{/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='page' and @qualifier='contactURL']}">
                                        <i18n:text>page.footer.links.contactus</i18n:text>
                                    </a>
                                </li>                                                  
                            </ul> 
                        </div>
                        <div class="col-xs-4">
                            <h4>
                                !follow us
                            </h4>
                            <ul class="list-inline follow-us">
                                <li class="list-inline-item">
                                    <i class="fa fa-facebook-square fa-3x" />
                                </li>
                                <li class="list-inline-item">
                                    <i class="fa fa-google-plus fa-3x" />
                                </li>
                                <li class="list-inline-item"> 
                                    <i class="fa fa-twitter fa-3x" />
                                </li>
                                <li class="list-inline-item">
                                    <a href="#" data-toggle="modal" data-target="#rssmodal">
                                        <i class="fa fa-rss fa-3x" />
                                    </a>
                                </li>
                            </ul> 
                            <div class="modal fade" id="rssmodal" tabindex="-1">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&#215;</span>
                                            </button>
                                            <h4 class="modal-title">Modal title</h4>
                                        </div>
                                        <div class="modal-body">
                                            <ul class="list-group list-group-flush">
                                                <xsl:for-each
                                                    select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='feed']"
                                                >
                                                    <li class="list-group-item">
                                                        <i class="fa fa-rss" />
                                                        <xsl:text> </xsl:text>
                                                        <a>
                                                            <xsl:attribute
                                                                name="href"
                                                            >
                                                                <xsl:value-of
                                                                    select="." 
                                                                />
                                                            </xsl:attribute>                                
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
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        </div>
                                    </div><!-- /.modal-content -->
                                </div><!-- /.modal-dialog -->
                            </div><!-- /.modal -->    
                        </div>
                    </div>                    
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 col-md-offset-3 col-xl-8 col-xl-offset-2 col-xs-12">            
                    <p class="footer-text">
                        <xsl:text>&#169; 2016 </xsl:text>
                        <a href="#">
                            <i18n:text>page.footer.copyright.text</i18n:text>
                        </a>
                        <xsl:text> !powered by </xsl:text>
                        <a href="#">DSpace</a>
                    </p>                    
                </div>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template
        name="search-panel"
    >        
        <div class="row search-panel">
            <div class="col-md-offset-3 col-xl-offset-2 col-md-6 col-xl-8">
                <div class="row">
                    <div class="col-xs-12 col-md-offset-3 col-md-6">
                        <h3 class="text-xs-center">!search</h3>
                        <div class="input-group">
                            <input class="form-control form-control-lg" type="text" placeholder="Search term..." />
                            <span class="input-group-btn">
                                <button class="btn btn-secondary btn-lg" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                        <h3>&#160;</h3>
                    </div>
                </div>                
            </div>
        </div>
    </xsl:template>
    
    <xsl:template 
        name="body-head"
    >       
        <div class="row jumbotron">
            <div class="col-md-offset-3 col-xl-offset-2 col-md-6 col-xl-8">                
                <p class="lead">
                    <i18n:text>page.landing.jubotron</i18n:text>
                </p>      
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
