<?xml version="1.0" encoding="UTF-8"?>
<!--

    The contents of this file are subject to the license and copyright
    detailed in the LICENSE and NOTICE files at the root of the source
    tree and available online at

    http://www.dspace.org/license/

-->

<!--
    Author: Art Lowel (art at atmire dot com)

    The purpose of this file is to transform the DRI for some parts of
    DSpace into a format more suited for the theme xsls. This way the
    theme xsl files can stay cleaner, without having to change Java
    code and interfere with other themes

    e.g. this file can be used to add a class to a form field, without
    having to duplicate the entire form field template in the theme xsl
    Simply add it here to the rend attribute and let the default form
    field template handle the rest.
-->

<xsl:stylesheet
                xmlns="http://di.tamu.edu/DRI/1.0/"
                xmlns:dri="http://di.tamu.edu/DRI/1.0/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                exclude-result-prefixes="xsl dri i18n">

    <xsl:output indent="yes"/>

    <xsl:variable name="page-meta" select="/dri:document/dri:meta/dri:pageMeta"/>
    <xsl:variable name="context-path" select="$page-meta/dri:metadata[@element='contextPath'][not(@qualifier)]"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template name="copy-attributes">
        <xsl:for-each select="@*">
            <xsl:attribute name="{name(.)}">
                <xsl:value-of select="."/>
            </xsl:attribute>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="string-replace-all">
        <xsl:param name="text"/>
        <xsl:param name="replace"/>
        <xsl:param name="by"/>
        <xsl:choose>
            <xsl:when test="contains($text, $replace)">
                <xsl:value-of select="substring-before($text,$replace)"/>
                <xsl:value-of select="$by"/>
                <xsl:call-template name="string-replace-all">
                    <xsl:with-param name="text"
                                    select="substring-after($text,$replace)"/>
                    <xsl:with-param name="replace" select="$replace"/>
                    <xsl:with-param name="by" select="$by"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$text"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="dri:pageMeta">
    <pageMeta>
        <xsl:call-template name="copy-attributes"/>
        <xsl:apply-templates/>
        <xsl:if test="/dri:document/dri:body/dri:div[@n='lookup' or @rend='vocabulary-container']">
            <metadata element="framing" qualifier="modal">true</metadata>
        </xsl:if>
        <xsl:if test="not(dri:trail)">
            <trail target="{$context-path}/">
                <i18n:text catalogue="default">xmlui.general.dspace_home</i18n:text>
            </trail>
            <trail>
                <xsl:text>-</xsl:text>
            </trail>
        </xsl:if>
    </pageMeta>
    </xsl:template>

    <!--Table cells check. Some rows are missing some cells. Only for tables without rowspan and colspan-->
    <xsl:template match="dri:table[not(dri:row/dri:cell[@cols!=1 or @rows!=1])]">

        <!--Max number of columns in the table-->
        <xsl:variable name="cols">
        <xsl:for-each select="dri:row">
            <xsl:sort select="count(./dri:cell)" order="descending" />
            <xsl:if test="position()=1">
                <xsl:value-of select="count(./dri:cell)"/>
            </xsl:if>
        </xsl:for-each>
        </xsl:variable>
        <table>
            <xsl:call-template name="copy-attributes"/>
            <xsl:apply-templates select="dri:head"/>
            <xsl:apply-templates select="dri:row" mode="cell-check">
                <xsl:with-param name="cols" select="$cols"/>
            </xsl:apply-templates>
        </table>
    </xsl:template>

    <xsl:template match="dri:row" mode="cell-check">
        <xsl:param name="cols"/>
        <xsl:variable name="missing" select="number($cols) - count(dri:cell)"/>
        <row>
            <xsl:call-template name="copy-attributes"/>
            <xsl:apply-templates/>
            <xsl:if test="$missing > 0">
            <xsl:call-template name="add-empty-cells">
                <xsl:with-param name="cells" select="$missing"/>
            </xsl:call-template>
            </xsl:if>
        </row>

    </xsl:template>

    <xsl:template name="add-empty-cells">
        <xsl:param name="cells"/>
        <xsl:if test="$cells > 0">
            <cell/>
            <xsl:call-template name="add-empty-cells">
                <xsl:with-param name="cells" select="$cells - 1"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
