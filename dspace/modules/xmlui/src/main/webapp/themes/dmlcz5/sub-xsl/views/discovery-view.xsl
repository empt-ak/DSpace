<!--

    The contents of this file are subject to the license and copyright
    detailed in the LICENSE and NOTICE files at the root of the source
    tree and available online at

    http://www.dspace.org/license/

-->

<xsl:stylesheet
    xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
    xmlns:dri="http://di.tamu.edu/DRI/1.0/"
    xmlns:mets="http://www.loc.gov/METS/"
    xmlns:dim="http://www.dspace.org/xmlns/dspace/dim"
    xmlns:xlink="http://www.w3.org/TR/xlink/"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns="http://www.w3.org/1999/xhtml"
    xmlns:xalan="http://xml.apache.org/xalan"
    xmlns:encoder="xalan://java.net.URLEncoder"
    xmlns:stringescapeutils="org.apache.commons.lang3.StringEscapeUtils"
    xmlns:util="org.dspace.app.xmlui.utils.XSLUtils"
    exclude-result-prefixes="xalan encoder i18n dri mets dim  xlink xsl util stringescapeutils">

    <xsl:output indent="yes"/>
    
    <xsl:template
        match="dri:document/dri:body/dri:div[@id='aspect.discovery.SimpleSearch.div.search']"
    >
        yop
        <xsl:apply-templates
            select="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-search-box']/dri:div[@id='aspect.discovery.SimpleSearch.div.general-query']"
        />
        <xsl:apply-templates
            select="dri:div[@id='aspect.discovery.SimpleSearch.div.main-form']"
        />
        <xsl:apply-templates
            select="dri:div[@id='aspect.discovery.SimpleSearch.div.search-results']"
        />
    </xsl:template>
    
    <xsl:template
        match="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-search-box']/dri:div[@id='aspect.discovery.SimpleSearch.div.general-query']"
    >
        <form method="{@method}" action="{@action}">
            <fieldset>
                <div class="row">
                    <div class="col-sm-3">
                        <xsl:apply-templates
                            select="dri:list/dri:item/dri:field[@id='aspect.discovery.SimpleSearch.field.scope']"
                        />
                    </div>
                    <div class="col-sm-9">
                        <div class="input-group">
                            <xsl:apply-templates
                                select="dri:list/dri:item/dri:field[@id='aspect.discovery.SimpleSearch.field.query']"
                            />
                            <span class="input-group-btn">
                                <xsl:apply-templates
                                    select="dri:list/dri:item/dri:field[@id='aspect.discovery.SimpleSearch.field.submit']"
                                />
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <h5>!Vstupny text matematiky</h5>
                        <textarea id="mathInput" name="filter_1" class="form-control"></textarea>
                        <input type="hidden" name="filtertype_1" value="dmlcz.math" />
                        <input type="hidden" name="filter_relational_operator_1" value="contains" />
                    </div>
                    <div class="col-sm-6">
                        <h5>!Preview</h5>
                        <div id="mathPreview">&#160;</div>
                        <div id="mathBuffer">&#160;</div>
                        <div id="hiddenMathml"></div>
                    </div>
                </div>            
            </fieldset>
        </form>
    </xsl:template>
    
    <!-- hides text from input field-->
    <xsl:template
        match="dri:field[@id='aspect.discovery.SimpleSearch.field.query']/dri:value"
    />
    
    <xsl:template
        match="dri:div[@id='aspect.discovery.SimpleSearch.div.main-form']"
    >
        mainform a hidden stuff
    </xsl:template>
    
    <xsl:template
        match="dri:div[@id='aspect.discovery.SimpleSearch.div.search-results']"
    >        
        <xsl:for-each
            select="./dri:list[@id='aspect.discovery.SimpleSearch.list.search-results-repository']"
        >
            <xsl:apply-templates />
        </xsl:for-each>
        
        <xsl:apply-templates
            select="./@pagination"
        />
    </xsl:template>  
    
    <xsl:template
        match="dri:list[@n='comm-coll-result-list']"
    >  
        <h4>!Communties and collections matched query</h4>
        collections a comms
    </xsl:template>
    
    <xsl:template
        match="dri:list[@n='item-result-list']"
    >
        <div class="row">
            <div class="col-sm-12">
                <h4>!items matched query</h4>
                
                <xsl:for-each
                    select="./dri:list"
                >
                    <xsl:variable
                        name="itemMetadata"
                    >
                        <xsl:text>cocoon://</xsl:text>
                        <xsl:value-of select="./@url"/>
                        <xsl:text>?sections=dmdSec,fileSec</xsl:text>
                    </xsl:variable>
                    <!--<div class="card">-->
                    <!--                        <img alt="!Thumbnail" class="card-img-top">
                        <xsl:attribute name="data-src">
                            <xsl:text>holder.js/100x200</xsl:text>
                            <xsl:text>?text=No Thumbnail</xsl:text>
                        </xsl:attribute>
                    </img>-->
                    <!--</div>-->
                    <div class="card">
                        <div class="card-block">
                            <h4 class="card-title disable-math">
                                <xsl:value-of
                                    select="./dri:list[contains(@n,'dc.title')]/dri:item"
                                />
                            </h4>
                            <h6 class="card-subtitle text-muted">
                                <xsl:for-each
                                    select="./dri:list[contains(@n,'dc.contributor.author')]/dri:item"
                                >
                                    <i class="fa fa-user"></i>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of
                                        select="."
                                    />
                                    <xsl:if 
                                        test="position() != last()"
                                    >
                                        <xsl:text>; </xsl:text>
                                    </xsl:if>
                                </xsl:for-each>
                            </h6>
                        </div>
                        <div class="card-block">
                            <p class="card-text">
                                <xsl:choose>
                                    <xsl:when test="./dri:list[contains(@n,'dc.description.abstract')]/dri:item">
                                        <div class="disable-math">
                                            <xsl:value-of
                                                select="./dri:list[contains(@n,'dc.description.abstract')]/dri:item"
                                            />
                                        </div>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        !no abstract
                                    </xsl:otherwise>
                                </xsl:choose>
                            </p>
                            <p class="card-text">
                                <small class="text-muted">!added</small>
                            </p>
                        </div>
                    </div>  
                    <!--                        <div class="row">
                        <div class="col-sm-3 hidden-xs">
                            nahlad
                        </div>
                        <div class="col-sm-9">
                            <xsl:variable
                                name="extMeta"
                            >
                                <xsl:text>dri</xsl:text>
                            </xsl:variable>
                            <xsl:value-of select="@n" />
                        </div>
                    </div>-->
                </xsl:for-each>                
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>