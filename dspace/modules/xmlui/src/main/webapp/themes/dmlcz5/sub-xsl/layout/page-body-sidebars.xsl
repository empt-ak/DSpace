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
    
    <xsl:template name="buildLeftSidebar">
        <div class="card">
            <div class="card-header">
                <i18n:text>page.sidebar.left.browseby</i18n:text>
            </div>
            <ul class="list-group list-group-flush">
                <xsl:for-each
                    select="/dri:document/dri:options/dri:list[@id='aspect.viewArtifacts.Navigation.list.browse']/dri:list[@id='aspect.browseArtifacts.Navigation.list.global']/dri:item"
                >
                    <li class="list-group-item">
                        <a href="{./dri:xref/@target}">
                            <i18n:text>
                                <xsl:value-of
                                    select="./dri:xref"
                                />
                            </i18n:text>
                        </a>
                    </li>
                </xsl:for-each>
            </ul> 
            <xsl:if
                test="/dri:document/dri:options/dri:list[@id='aspect.viewArtifacts.Navigation.list.browse']/dri:list[@id='aspect.browseArtifacts.Navigation.list.context']/dri:item"
            >
                <div class="card-header">
                    <i18n:text>
                        <xsl:value-of
                            select="/dri:document/dri:options/dri:list[@id='aspect.viewArtifacts.Navigation.list.browse']/dri:list[@id='aspect.browseArtifacts.Navigation.list.context']/dri:head"
                        />
                    </i18n:text>
                </div> 
                <ul class="list-group list-group-flush">
                    <xsl:for-each
                        select="/dri:document/dri:options/dri:list[@id='aspect.viewArtifacts.Navigation.list.browse']/dri:list[@id='aspect.browseArtifacts.Navigation.list.context']/dri:item"
                    >
                        <li class="list-group-item">
                            <a href="{./dri:xref/@target}">
                                <i18n:text>
                                    <xsl:value-of
                                        select="./dri:xref"
                                    />
                                </i18n:text>
                            </a>
                        </li>
                    </xsl:for-each>
                </ul> 
            </xsl:if>  
            <div class="hidden-sm-down">
                <xsl:if
                    test="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='feed']"
                >  
                    <div class="card-header">
                        <i18n:text>page.sidebar.left.rss</i18n:text>
                    </div>
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
                </xsl:if>
            </div>
            <div class="hidden-sm-down">
                <div class="card-header">
                    <i18n:text>page.sidebar.left.quickstats</i18n:text>
                </div>
                <ul class="list-group list-group-flush">              
                    <li class="list-group-item">
                        <i18n:text>page.sidebar.left.count.author</i18n:text>
                        <xsl:text> </xsl:text>
                        <xsl:value-of
                            select="document(concat($solrServer,'select?q=*%3A*&amp;fl=author_keyword&amp;wt=xml&amp;rows=0'))/response/result/@numFound"
                        />
                    </li>
                    <li class="list-group-item">
                        <i18n:text>page.sidebar.left.count.article</i18n:text>
                        <xsl:text> </xsl:text>
                        <xsl:value-of
                            select="document(concat($solrServer,'select?q=search.resourcetype%3A2&amp;rows=0&amp;wt=xml'))/response/result/@numFound"
                        />
                    </li>
                    <li class="list-group-item">
                        <i18n:text>page.sidebar.left.quickstats.more</i18n:text>
                    </li>
                </ul>
            </div>        
        </div>
    </xsl:template>
    
    <xsl:template name="buildRightSidebar">
        <xsl:if
            test="not(/dri:document/dri:body/dri:div[@n='landing-page']) 
                or not(/dri:document/dri:body/dri:div[@n='contact']) 
                or $level &gt; 2"
        >
        <!-- <xsl:text>TEST</xsl:text> -->
            <xsl:call-template
                name="buildPUN"
            />
        </xsl:if>        
        <div class="card disable-bottom-border">            
            <xsl:if
                test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.subject']/dri:item"
            >
                <div class="card-header disable-bottom-border">
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
            </xsl:if>  
            
            <xsl:if
                test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.author']/dri:item"
            >
                <div class="card-header disable-bottom-border">
                    <i18n:text>page.sidebar.right.discovery.author</i18n:text>
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
            </xsl:if>
            <xsl:if
                test="/dri:document/dri:options/dri:list[@id='aspect.discovery.Navigation.list.discovery']/dri:list[@id='aspect.discovery.SidebarFacetsTransformer.list.msc']/dri:item"
            >
                <div class="card-header disable-bottom-border">
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
            </xsl:if>
            <xsl:if                
                test="/dri:document/dri:body/dri:div[@id='aspect.discovery.RelatedItems.div.item-related-container']/dri:div[@id='aspect.discovery.RelatedItems.div.item-related']/dri:referenceSet[@id='aspect.discovery.RelatedItems.referenceSet.item-related-items']"
            >
                <a class="list-group-item active">
                    <i18n:text>page.sidebar.right.related.items</i18n:text>
                </a>
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
                    <a class="list-group-item">
                        <xsl:attribute
                            name="href"
                        >
                            <xsl:value-of
                                select="./@url"
                            />
                        </xsl:attribute>
                        <xsl:value-of
                            select="document($relatedItemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                        />        
                    </a>
                </xsl:for-each>
            </xsl:if>
        </div>
    </xsl:template>

</xsl:stylesheet>
