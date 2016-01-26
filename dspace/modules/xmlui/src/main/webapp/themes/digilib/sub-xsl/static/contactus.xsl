<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : landing-page.xsl
    Created on : July 24, 2015, 10:44 AM
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
        match="dri:body/dri:div[@id='aspect.artifactbrowser.Contact.div.contact']"
    >
        <div class="row">
            <div class="col-xs-12">
                <h1>
                    <i18n:text>xmlui.ArtifactBrowser.Contact.head</i18n:text>
                </h1>
                <p>
                    <i18n:text>page.contact.help</i18n:text>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <table class="table">
                    <tr>
                        <td>
                            <i18n:text>
                                <xsl:value-of
                                    select="./dri:list[@n='contact']/dri:label[1]"
                                />
                            </i18n:text>
                        </td>
                        <td>
                            <a href="{./dri:list[@n='contact']/dri:item[1]/dri:xref/@target}">
                                <i18n:text>
                                    <xsl:value-of
                                        select="./dri:list[@n='contact']/dri:item[1]/dri:xref"
                                    />
                                </i18n:text>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <i18n:text>
                                <xsl:value-of
                                    select="./dri:list[@n='contact']/dri:label[2]"
                                />
                            </i18n:text>
                        </td>
                        <td>
                            <a href="#">
                                <span class="contact-email">
                                    <i18n:text>page.footer.contact.javascript</i18n:text>
                                </span>
                            </a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </xsl:template>
            
</xsl:stylesheet>
