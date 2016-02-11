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

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:template name="substring-after-last">
        <xsl:param name="string" />
        <xsl:param name="delimiter" />
        <xsl:choose>
            <xsl:when test="contains($string, $delimiter)">
                <xsl:call-template name="substring-after-last">
                    <xsl:with-param name="string"
                                    select="substring-after($string, $delimiter)" />
                    <xsl:with-param name="delimiter" select="$delimiter" />
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of 
                    select="$string" />
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
        
    <xsl:template
        name="getSolrLocation"
    >
        <xsl:variable
            name="solrGetType"
            select="concat($solrServer,'select?q=*%3A*&amp;fq=handle%3A',$currentViewHandle,'&amp;rows=1&amp;fl=search.resourcetype%2Csearch.resourceid&amp;wt=xml')"
        />
            
        <xsl:variable
            name="resourceType"
            select="document($solrGetType)/response/result/doc/int[@name='search.resourcetype']"
        />
        <xsl:if
            test="$resourceType = 4"
        >
            <xsl:text>m</xsl:text>
        </xsl:if>
        <xsl:if
            test="$resourceType = 3"
        >
            <xsl:text>l</xsl:text>
        </xsl:if>
        <xsl:value-of
            select="document($solrGetType)/response/result/doc/int[@name='search.resourceid']"
        />
    </xsl:template>
  
    <xsl:template name="replace-gt">
        <xsl:param name="str"/>
        <xsl:variable name="gt" select="string('>')"/>
        <xsl:choose>
            <xsl:when test="contains($str, $gt)">
                <xsl:variable name="pref" select="substring-before($str, $gt)"/>
                <xsl:variable name="suff" select="substring-after($str, $gt)"/>
                <xsl:value-of select="$pref"/>
                <xsl:text disable-output-escaping="yes"><![CDATA[>]]></xsl:text>
                <xsl:call-template name="replace-gt">
                    <xsl:with-param name="str" select="$suff"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$str"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="replace-lt">
        <xsl:param name="str"/>
        <xsl:variable name="lt" select="string('&lt;')"/>
        <xsl:variable name="gt" select="string('>')"/>
        <xsl:choose>
            <xsl:when test="contains($str, $lt)">
                <xsl:variable name="pref" select="substring-before($str, $lt)"/>
                <xsl:variable name="suff" select="substring-after($str, $lt)"/>
                <xsl:call-template name="replace-gt">
                    <xsl:with-param name="str" select="$pref"/>
                </xsl:call-template>
                <xsl:text disable-output-escaping="yes"><![CDATA[<]]></xsl:text>
                <xsl:call-template name="replace-lt">
                    <xsl:with-param name="str" select="$suff"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="contains($str, $gt)">
                <xsl:call-template name="replace-gt">
                    <xsl:with-param name="str" select="$str"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$str"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="replace-ent">
        <xsl:param name="str"/>
        <xsl:variable name="amp" select="string('&amp;')"/>
        <xsl:variable name="lt" select="string('&lt;')"/>
        <xsl:variable name="gt" select="string('>')"/>
        <xsl:choose>
            <xsl:when test="contains($str, $amp)">
                <xsl:variable name="pref" select="substring-before($str, $amp)"/>
                <xsl:variable name="comp-suff" select="substring-after($str, $amp)"/>
                <xsl:variable name="ent" select="substring-before($comp-suff, ';')"/>
                <xsl:variable name="suff" select="substring-after($comp-suff, ';')"/>
                <xsl:call-template name="replace-lt">
                    <xsl:with-param name="str" select="$pref"/>
                </xsl:call-template>
                <xsl:choose>
                    <xsl:when test="(string-length($ent) &lt; 5) and (string-length($ent) != 0)">
                        <xsl:text disable-output-escaping="yes">&amp;</xsl:text>
                        <xsl:value-of select="$ent"/>
                        <xsl:text>;</xsl:text>
                        <xsl:choose>
                            <xsl:when test="contains($suff, $amp)">
                                <xsl:call-template name="replace-ent">
                                    <xsl:with-param name="str" select="$suff"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="replace-lt">
                                    <xsl:with-param name="str" select="$suff"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text disable-output-escaping="yes">&amp;amp;</xsl:text>
                        <xsl:choose>
                            <xsl:when test="contains($comp-suff, $amp)">
                                <xsl:call-template name="replace-ent">
                                    <xsl:with-param name="str" select="$comp-suff"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="replace-lt">
                                    <xsl:with-param name="str" select="$comp-suff"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="contains($str, $lt)">
                <xsl:call-template name="replace-lt">
                    <xsl:with-param name="str" select="$str"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="contains($str, $gt)">
                <xsl:call-template name="replace-gt">
                    <xsl:with-param name="str" select="$str"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$str"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="replacehtml-ent">
        <xsl:param name="fragment"/>
        <xsl:for-each select="$fragment/node()">
            <xsl:choose>
                <xsl:when test="not(child::node())">
                    <xsl:call-template name="replace-ent">
                        <xsl:with-param name="str" select="."/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:copy>
                        <xsl:copy-of select="@*"/>
                        <xsl:call-template name="replacehtml-ent">
                            <xsl:with-param name="fragment" select="."/>
                        </xsl:call-template>
                    </xsl:copy>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
    </xsl:template>
    
    
    <xsl:template
        name="buildTitle"
    >   
        <!--
        true only and if only we are at landing page
        -->
        <xsl:if
            test="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='title'] != /dri:document/dri:meta/dri:pageMeta/dri:trail[0]"
        >    
            <xsl:value-of
                select="/dri:document/dri:meta/dri:pageMeta/dri:metadata[@element='title']"
            />
            <xsl:text> | </xsl:text>
        </xsl:if> 
        <i18n:text>page.title</i18n:text>
    </xsl:template>
</xsl:stylesheet>