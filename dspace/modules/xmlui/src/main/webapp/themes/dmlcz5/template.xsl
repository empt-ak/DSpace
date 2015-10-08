<?xml version="1.0" encoding="UTF-8"?>

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
	
    <xsl:import href="sub-xsl/layout/page-layout.xsl" />
    <xsl:import href="sub-xsl/layout/page-html-head.xsl" />
    <xsl:import href="sub-xsl/layout/page-body-sidebars.xsl" />
    <xsl:import href="sub-xsl/layout/page-body-head.xsl" />
    <xsl:import href="sub-xsl/layout/page-body-footer.xsl" />
    <xsl:import href="sub-xsl/layout/page-body-commons.xsl" />
    
    <xsl:import href="sub-xsl/views/landing-page.xsl" />
    <xsl:import href="sub-xsl/views/community-view.xsl" />
    <xsl:import href="sub-xsl/views/collection-view.xsl" />
    <xsl:import href="sub-xsl/views/item-view.xsl" />
    <xsl:import href="sub-xsl/views/discovery-view.xsl" />
    <xsl:import href="sub-xsl/views/browse-by-view.xsl" />
    <xsl:import href="sub-xsl/views/exception-view.xsl" />
    
    <xsl:import href="sub-xsl/static/aboutus.xsl" />
    
    <xsl:import href="sub-xsl/page-variables.xsl" />
    <xsl:import href="sub-xsl/switch-off.xsl" />
    <xsl:import href="sub-xsl/functions.xsl" />
</xsl:stylesheet>
