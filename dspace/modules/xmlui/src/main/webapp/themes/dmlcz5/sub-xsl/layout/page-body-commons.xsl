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
    <xsl:template name="buildNavigation">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">
                            <i18n:text>navigation.main.button.toggle</i18n:text>
                        </span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!--<a class="navbar-brand" href="#">Brand</a>-->
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <form class="navbar-form navbar-right" role="search">
                        <div class="form-group">
                            <input 
                                type="text" 
                                class="form-control" 
                                placeholder="navigation.main.button.search"
                                i18n:attr="placeholder" 
                            />
                        </div>
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a>
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
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.news</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.faq</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.terms</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.matharchives</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.contactus</i18n:text>
                            </a>
                        </li>                                                  
                    </ul>                        
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </xsl:template>
    
    <xsl:template 
        name="buildBreadcrumb"
    >
        <xsl:if 
            test="count(/dri:document/dri:meta/dri:pageMeta/dri:trail) > 1"
        >
            <nav class="row">
                <div class="col-md-12">
                    <ol class="breadcrumb">
                        <xsl:for-each
                            select="/dri:document/dri:meta/dri:pageMeta/dri:trail"
                        >
                            <li>
                                <xsl:if
                                    test="position() = 1">
                                    <i class="glyphicon glyphicon-home" />&#160;
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
        name="buildPUN"
    > 
        <div class="pun">      
            <ul class="pager">
                <li class="previous disabled">
                    <a href="#">
                        <i18n:text>navigation.pun.previous</i18n:text>
                    </a>
                </li>
                <!--            <li class="disabled">
                    <a href="#">
                        <i18n:text>navigation.pun.up</i18n:text>
                    </a>
                </li>-->
                <li class="next disabled">
                    <a href="#">
                        <i18n:text>navigation.pun.next</i18n:text>
                    </a>
                </li>
            </ul>
        </div>
    </xsl:template>
    
    <xsl:template 
        name="buildFooter"
    >
        <div class="footer-content">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <div class="row">
                        <div class="col-md-4">
                            <h4>
                                <i18n:text>page.footer.partners.title</i18n:text>
                            </h4>
                            <ul>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.partners.ascr</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.partners.eudml</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.partners.ics</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.partners.fimu</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.partners.ficuni</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.partners.libprague</i18n:text>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-md-4">
                            <h4>
                                <i18n:text>page.footer.links.title</i18n:text>
                            </h4>
                            <ul>
                                <li>
                                    <i18n:text>page.footer.links.sitemap</i18n:text>
                                </li>
                                <li>
                                    <a>
                                        <xsl:attribute
                                            name="href"
                                        >
                                            <xsl:value-of
                                                select="concat($contextPath,'/aboutus')"
                                            />
                                        </xsl:attribute>
                                        <i18n:text>page.footer.links.aboutus</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.links.news</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.links.faq</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.links.terms</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.links.matharchives</i18n:text>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <i18n:text>page.footer.links.contactus</i18n:text>
                                    </a>
                                </li>                                                  
                            </ul>
                        </div>
                        <div class="col-md-4">
                            <h4>
                                <i18n:text>page.footer.social.title</i18n:text>
                            </h4>
                            <ul class="list-inline">
                                <li>
                                    <i class="fa fa-facebook-square fa-3x" />
                                </li>
                                <li>
                                    <i class="fa fa-google-plus fa-3x" />
                                </li>
                                <li> 
                                    <i class="fa fa-twitter fa-3x" />
                                </li>
                            </ul>                           
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-8 col-md-offset-2">
            
                <p class="footer-text">
                &#169; 2015 <a href="#">
                        <i18n:text>page.footer.copyright.text</i18n:text>
                    </a>
                    <i18n:text>page.footer.contact</i18n:text>                    
                    <a href="#">
                        <span id="my-email">
                            <i18n:text>page.footer.contact.javascript</i18n:text>
                        </span>
                    </a>
                </p>                    
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
