<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-variables.xsl
    Created on : July 24, 2015, 9:49 AM
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

    <xsl:template match="/dri:document/dri:body/dri:div[@id='file.news.div.news']"></xsl:template>
    <xsl:template match="/dri:document/dri:body/dri:div[@id='aspect.artifactbrowser.CommunityBrowser.div.comunity-browser']"></xsl:template>
    <xsl:template match="/dri:document/dri:body/dri:div[@id='aspect.discovery.SiteRecentSubmissions.div.site-home']"></xsl:template>
    <xsl:template match="/dri:document/dri:options/dri:list"></xsl:template>
    <xsl:template match="/dri:document/dri:meta"></xsl:template>
    <xsl:template match="/dri:document/dri:body/dri:div[@id='aspect.artifactbrowser.CommunityViewer.div.community-home']"></xsl:template>
    <xsl:template match="/dri:document/dri:body/dri:div[@id='aspect.artifactbrowser.CollectionViewer.div.collection-home']"></xsl:template>
    <xsl:template match="/dri:document/dri:body/dri:div[@id='aspect.artifactbrowser.ItemViewer.div.item-view']"></xsl:template>
</xsl:stylesheet>