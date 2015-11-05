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
        match="dri:body/dri:div[@n='browse-by-subject-results' or @n='browse-by-author-results']"
    >
        <xsl:text>!showing</xsl:text> 
        <xsl:value-of
            select="./@firstItemIndex"
        />
        <xsl:text> of </xsl:text>
        <xsl:value-of
            select="./@lastItemIndex"
        />
        <table class="table">
            <xsl:for-each
                select="./dri:table/dri:row"
            >
                <tr>
                    <td class="disable-math">
                        <xsl:value-of
                            select="./dri:cell/dri:xref"
                        />
                    </td>
                </tr>
            </xsl:for-each>
        </table>
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
                        <span aria-hidden="true">&#171;</span>
                        <xsl:text> !previous</xsl:text>
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
                        <xsl:text>!next </xsl:text>
                        <span aria-hidden="true">&#187;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </xsl:template>
    
    <xsl:template
        match="dri:list[@id='aspect.discovery.SearchFacetFilter.list.jump-list']"
    >
        <nav>
            <ul class="pagination">
                <!--                <li>
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&#171;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>-->
                <xsl:for-each
                    select="./dri:item/dri:xref"
                >
                    <li>
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
                    </li>
                </xsl:for-each>
                <!--                <li>
                    <a href="#" aria-label="Next">
                        <span aria-hidden="true">&#187;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>-->
            </ul>
        </nav>
        
        <div id="extended-controls">
            test
        </div>
        
        <xsl:apply-templates
            select="./dri:p"
        />
    </xsl:template>
    
    <xsl:template
        match="dri:p[dri:field[@id='aspect.discovery.SearchFacetFilter.field.starts_with']]"
    >
        <form class="form-inline">
            <div class="form-group">
                <label for="exampleInputName2">
                    !starts with
                </label>
                <input type="text" class="form-control" id="exampleInputName2" placeholder="Jane Doe" />
            </div>
            <button type="submit" class="btn btn-primary">Send invitation</button>
        </form>
        xixi
    </xsl:template>
    
</xsl:stylesheet>
