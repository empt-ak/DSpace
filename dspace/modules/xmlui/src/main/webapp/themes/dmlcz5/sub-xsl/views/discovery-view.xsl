<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : browse-by-view.xsl
    Created on : October 5, 2015, 2:56 PM
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
        match="dri:body/dri:div[@id='aspect.discovery.SimpleSearch.div.search']/dri:div[@id='aspect.discovery.SimpleSearch.div.search-results']/dri:list[@id='aspect.discovery.SimpleSearch.list.search-results-repository']"
    >
        <table class="table">
            <xsl:for-each
                select="./dri:list[@id='aspect.discovery.SimpleSearch.list.item-result-list']/dri:list"
            >
                <xsl:variable 
                    name="handle" 
                    select="substring-before(./dri:list/@n,':')" 
                />
                <xsl:variable 
                    name="type" 
                    select="substring-after(./dri:list/@n,':')" 
                />
                <tr>
                    <td>
                        !preview
                    </td>
                    <td>
                        <div>
                            <h3>
                                <xsl:value-of
                                    select="./dri:list[@n = concat($handle,':','dc.title')]"
                                />
                            </h3>
                        </div>
                        <div>
                            <xsl:for-each
                                select="./dri:list[@n = concat($handle,':','dc.contributor.author')]/dri:item"
                            >
                                <xsl:value-of
                                    select="."
                                />
                                <xsl:if
                                    test="position() != last()"
                                >
                                    <xsl:text>, </xsl:text>
                                </xsl:if>
                            </xsl:for-each>
                        </div>
                        <div>
                            <xsl:value-of
                                select="./dri:list[@n = concat($handle,':','dc.description.abstract')]/dri:item"
                            />
                        </div>
                    </td>
                </tr>
            </xsl:for-each>
        </table>        
    </xsl:template>
</xsl:stylesheet>
