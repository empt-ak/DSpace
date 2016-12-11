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
        match="dri:body/dri:div[@id='aspect.artifactbrowser.FeedbackForm.div.feedback-form']"
    >
        <div class="row">
            <div class="col-12">
                <h1>Feedback</h1>
            </div>
        </div>
        <form action="{@action}" method="{@method}">
            <xsl:variable
                name="fieldEmail"
                select="./dri:list[@id='aspect.artifactbrowser.FeedbackForm.list.form']/dri:item/dri:field[@id='aspect.artifactbrowser.FeedbackForm.field.email']"
            />
            <div class="form-group row">
                <label for="inputEmail" class="col-sm-2 form-control-label">
                    <i18n:text>
                        <xsl:value-of
                            select="$fieldEmail/dri:label"
                        />
                    </i18n:text>
                </label>
                <div class="col-sm-10">
                    <input class="form-control" id="inputEmail" i18n:attr="placeholder" placeholder="{$fieldEmail/dri:help}" name="{$fieldEmail/@n}" type="email" value="{$fieldEmail/dri:value}"/>
                </div>
            </div>
            
            <xsl:variable
                name="fieldComment"
                select="./dri:list[@id='aspect.artifactbrowser.FeedbackForm.list.form']/dri:item/dri:field[@id='aspect.artifactbrowser.FeedbackForm.field.comments']"
            />
            <div class="form-group row">
                <label for="inputEmail" class="col-sm-2 form-control-label">
                    <i18n:text>
                        <xsl:value-of
                            select="$fieldComment/dri:label"
                        />
                    </i18n:text>
                </label>
                <div class="col-sm-10">
                    <textarea class="form-control" id="inputEmail" placeholder="{$fieldComment/dri:help}" name="{$fieldComment/@n}" type="email" value="{$fieldEmail/dri:value}"/>
                </div>
            </div>
            
            <div class="form-group row">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-secondary">
                        <i18n:text>
                            <xsl:value-of
                                select="./dri:list[@id='aspect.artifactbrowser.FeedbackForm.list.form']/dri:item/dri:field['aspect.artifactbrowser.FeedbackForm.field.submit']/dri:label"
                            />
                        </i18n:text>
                    </button>
                </div>
            </div>
            <xsl:for-each
                select="./dri:p/dri:field"
            >
                <input type="hidden" name="{@n}" value="{./dri:value}" />
            </xsl:for-each>
        </form>
    </xsl:template>
</xsl:stylesheet>
