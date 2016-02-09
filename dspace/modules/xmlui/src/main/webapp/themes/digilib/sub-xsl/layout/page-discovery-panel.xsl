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
    
    
    <xsl:template name="discovery-panel">
        <div class="row browse-panel">
            <div class="col-md-offset-3 col-xl-offset-2 col-md-6 col-xl-8">
                <div class="row">
                    <div class="col-xs-12">
                        <h1 class="text-xs-center">!Browse</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 col-md-3">
                        <a href="/digilib5/browse?type=author" class="browse-by-link">
                            <div class="card">
                                <div class="card-block text-xs-center">
                                    <i class="fa fa-users fa-5x"></i>
                                </div>
                                <div class="card-block">
                                    <h4 class="card-title text-xs-center">!Autori</h4>                                
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="col-xs-6 col-md-3">
                        <a href="/digilib5/browse?type=title" class="browse-by-link">
                            <div class="card">
                                <div class="card-block text-xs-center">
                                    <i class="fa fa-quote-right fa-5x"></i>
                                </div>
                                <div class="card-block">
                                    <h4 class="card-title text-xs-center">!Nazvy</h4>                                
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="col-xs-6 col-md-3">
                        <a href="/digilib5/browse?type=subject" class="browse-by-link">
                            <div class="card">
                                <div class="card-block text-xs-center">
                                    <i class="fa fa-tags fa-5x"></i>
                                </div>
                                <div class="card-block">
                                    <h4 class="card-title text-xs-center">!Klicova slova</h4>                                
                                </div>
                            </div>     
                        </a>                   
                    </div>
                    <div class="col-xs-6 col-md-3">
                        <a href="/digilib5/browse?type=dateissued" class="browse-by-link">
                            <div class="card">
                                <div class="card-block text-xs-center">
                                    <i class="fa fa-clock-o fa-5x"></i>
                                </div>
                                <div class="card-block">
                                    <h4 class="card-title text-xs-center">!Datum vydani</h4>                                
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="row discovery-panel">
            <div class="col-md-offset-3 col-xl-offset-2 col-md-6 col-xl-8">        
                <div class="row">
                    <div class="col-xs-12">
                        <h1 class="text-xs-center">!Discover</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4 col-xs-12">
                        <div class="card">
                            <div class="card-block text-xs-center">
                                <i class="fa fa-users fa-5x"></i>
                            </div>
                            <div class="card-block">
                                <h4 class="card-title text-xs-center">!Autori</h4>                                
                            </div>
                            <ul class="list-group list-group-flush">                   
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
                                                    <span class="label label-default label-pill pull-right">
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
                        </div>
                    </div>
                    <div class="col-md-4 col-xs-12">
                        <div class="card">
                            <div class="card-block text-xs-center">
                                <i class="fa fa-tags fa-5x"></i>
                            </div>
                            <div class="card-block">
                                <h4 class="card-title text-xs-center">!Klicova slova</h4>                                
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
                                                    <span class="label label-default label-pill pull-right">
                                                        <xsl:variable
                                                            name="badge"
                                                        >
                                                            <xsl:call-template name="substring-after-last">
                                                                <xsl:with-param name="string" select="./text()" />
                                                                <xsl:with-param name="delimiter" select="'('" />
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
                        </div>
                    </div>
                    <div class="col-md-4 col-xs-12">
                        <div class="card">
                            <div class="card-block text-xs-center">
                                <i class="fa fa-globe fa-5x"></i>
                            </div>
                            <div class="card-block">
                                <h4 class="card-title text-xs-center">!Nejcastejsie jazyky</h4>                                
                            </div>
                            <div class="card-block">
                                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>
                                <p class="card-text">
                                    <small class="text-muted">Last updated 3 mins ago</small>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
