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
        <h1>
            <xsl:value-of
                select="./dri:head"
            />
        </h1>
        <xsl:apply-templates
            select="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-search-box']/dri:div[@id='aspect.discovery.SimpleSearch.div.general-query']"
        />
        <xsl:apply-templates
            select="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-search-box']/dri:div[@id='aspect.discovery.SimpleSearch.div.search-filters']"
        />
        <xsl:apply-templates
            select="dri:div[@id='aspect.discovery.SimpleSearch.div.main-form']"
        />
        
        <hr />
        <h2>
            <b>!Search results</b>
        </h2>
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
            </fieldset>
        </form>
    </xsl:template>
    
    <!-- hides text from input field-->
    <xsl:template
        match="dri:field[@id='aspect.discovery.SimpleSearch.field.query']/dri:value"
    />
    
    <xsl:template
        match="dri:field[@rend='discovery-filter-input']/dri:value"
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
                        name="h"
                        select="substring-before(@n,':item')"
                    />
                    <xsl:variable name="extMetsURL">
                        <xsl:text>cocoon://metadata/handle/</xsl:text>
                        <xsl:value-of select="$h"/>
                        <xsl:text>/mets.xml</xsl:text>
                        <!-- Since this is a summary only grab the descriptive metadata, and the thumbnails -->
                        <xsl:text>?sections=dmdSec,fileSec&amp;fileGrpTypes=THUMBNAIL</xsl:text>
                        <!-- An example of requesting a specific metadata standard (MODS and QDC crosswalks only work for items)->
                        <xsl:if test="@type='DSpace Item'">
                            <xsl:text>&amp;dmdTypes=DC</xsl:text>
                        </xsl:if>-->
                    </xsl:variable> 
                    <xsl:variable name="extMets" select="document($extMetsURL)" />
                    <!--<div class="card">-->
                    <!--                        <img alt="!Thumbnail" class="card-img-top">
                        <xsl:attribute name="data-src">
                            <xsl:text>holder.js/100x200</xsl:text>
                            <xsl:text>?text=No Thumbnail</xsl:text>
                        </xsl:attribute>
                    </img>-->
                    <!--</div>-->
                    <div class="card">
                        <div class="card-block card-block-top-result">                                       
                            <a href="{$extMets/mets:METS/@OBJID}">
                                <h5 class="card-title disable-math">
                                    <xsl:choose>
                                        <xsl:when
                                            test="dri:list[@n=(concat($h,':dc.title')) and descendant::text()]"
                                        >
                                            <xsl:apply-templates
                                                select="./dri:list[@n=concat($h,':dc.title')]/dri:item[1]"
                                            />
                                        </xsl:when>
                                        <xsl:otherwise>
                                            !no title
                                        </xsl:otherwise>
                                    </xsl:choose>   
                                </h5>
                            </a>
                            <h6 class="card-subtitle"> 
                                <i class="fa fa-user"></i> 
                                <span class="text-muted">                            
                                    <xsl:text> </xsl:text>
                                    <xsl:for-each
                                        select="./dri:list[@n=concat($h,':dc.contributor.author')]/dri:item"
                                    >
                                        <xsl:apply-templates
                                            select="."
                                        />
                                        <xsl:if test="count(following-sibling::dri:item) != 0">
                                            <xsl:text>; </xsl:text>
                                        </xsl:if>
                                    </xsl:for-each>
                                    <xsl:for-each
                                        select="./dri:list[@n=concat($h,':dc.creator')]/dri:item"
                                    >
                                        <xsl:apply-templates
                                            select="."
                                        />
                                        <xsl:if test="count(following-sibling::dri:item) != 0">
                                            <xsl:text>; </xsl:text>
                                        </xsl:if>
                                    </xsl:for-each>
                                    <xsl:for-each
                                        select="./dri:list[@n=concat($h,':dc.contributor')]/dri:item"
                                    >
                                        <xsl:apply-templates
                                            select="."
                                        />
                                        <xsl:if test="count(following-sibling::dri:item) != 0">
                                            <xsl:text>; </xsl:text>
                                        </xsl:if>
                                    </xsl:for-each>
                                </span>
                            </h6>
                        </div>
                        <div class="card-block">
                            <p class="card-text disable-math">
                                <xsl:choose>
                                    <xsl:when test="./dri:list[@n=concat($h,':dc.description.abstract')]/dri:item">
                                        <xsl:apply-templates
                                            select="./dri:list[@n=concat($h,':dc.description.abstract')]/dri:item"
                                        />
                                    </xsl:when>
                                    <xsl:otherwise>
                                        !no abstract
                                    </xsl:otherwise>
                                </xsl:choose>
                            </p>
                            <p class="card-text hidden-xs-down">
                                <small class="text-muted">
                                    <xsl:text>!Last updated: </xsl:text>
                                    <xsl:value-of
                                        select="substring-before(./dri:list[@n=concat($h,':dc.date.available')]/dri:item,'T')"
                                    />
                                </small>
                            </p>
                        </div>
                    </div> 
                </xsl:for-each>                
            </div>
        </div>
    </xsl:template>
    
    <!-- highlights the matched word in result list -->
    <xsl:template
        match="dri:item/dri:hi[@rend='highlight']"
    >
        <strong>
            <xsl:value-of
                select="."
            />
        </strong>
    </xsl:template>
    
    <xsl:template
        match="dri:div[@id='aspect.discovery.SimpleSearch.div.search-filters']"
    >
        <div class="row">
            <div class="col-xs-12">
                <div class="pull-right">
                    <xsl:apply-templates
                        select="./dri:div[@rend='clearfix']/dri:p[@rend='pull-right']/dri:xref"
                    />
                </div>
            </div>
        </div>
        <form method="{@method}" action="{@action}" id="{translate(@id,'.','_')}" class="{./dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-filters-wrapper']/@rend}">            
            <fieldset>
                <input type="hidden" name="query" value="{./dri:p/dri:field/dri:value}" />
                <input type="hidden" name="scope" value="{./dri:p/dri:field/dri:value}" />
                <xsl:apply-templates select="./dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-filters-wrapper']" />                
            </fieldset>
        </form>
    </xsl:template>    
    
    <xsl:template 
        match="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-filters-wrapper']"
    >   
        <h4>
            <xsl:value-of
                select="./dri:head"
            />
        </h4>
        <xsl:value-of
            select="./dri:p[1]"
        />
                
        <xsl:apply-templates
            select="./dri:table[@id='aspect.discovery.SimpleSearch.table.discovery-filters']"
        />        
    </xsl:template> 
    
    <xsl:template
        match="dri:div[@rend='clearfix']/dri:p[@rend='pull-right']/dri:xref"
    >
        <a target="{@target}">
            <xsl:attribute
                name="class"
            >
                <xsl:value-of
                    select="@rend"
                />
            </xsl:attribute>
            <xsl:value-of
                select="."
            />
            <xsl:choose>
                <xsl:when
                    test="contains(@rend,'hidden')"
                >
                    <xsl:text> </xsl:text>
                    <i class="fa fa-chevron-up"></i>                    
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text> </xsl:text>
                    <i class="fa fa-chevron-down"></i>
                </xsl:otherwise>
            </xsl:choose>
        </a>
    </xsl:template>
    
    <xsl:template
        match="dri:table[@id='aspect.discovery.SimpleSearch.table.discovery-filters']"
    >
        <xsl:apply-templates />        
    </xsl:template>
    
    <xsl:template
        match="dri:row[@rend='search-filter used-filter']"
    >
        <div class="form-group row in-use">
            <div class="col-xs-4 col-sm-2">
                <xsl:apply-templates
                    select="./dri:cell[1]/dri:field"
                />
            </div>
            <div class="col-xs-4 col-sm-2">
                <xsl:apply-templates
                    select="./dri:cell[2]/dri:field"
                />
            </div>
            <div class="col-xs-4 col-sm-6">
                <xsl:apply-templates
                    select="./dri:cell[3]/dri:field"
                />
            </div>
            <div class="col-sm-2 hidden-xs-down">
                <div class="btn-group" role="group" aria-label="Basic example">
                    <button type="button" class="btn btn-secondary filter-add">
                        <i class="fa fa-plus"></i>
                    </button>
                    <button type="button" class="btn btn-secondary filter-remove">
                        <i class="fa fa-minus"></i>
                    </button>
                </div>             
            </div>
        </div>
    </xsl:template>
    
    <xsl:template
        match="dri:row[@id='aspect.discovery.SimpleSearch.row.filter-controls']"
    >
        <div class="form-group row button-row">
            <div class="col-sm-offset-2 col-xs-offset-4 col-sm-10">                
                <button type="submit" class="btn btn-secondary" value="apply">!apply filters</button>
                <button type="reset" class="btn btn-secondary" value="reset">Reseat</button>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template
        match="dri:row[@rend='search-filter' and contains(@n,'filter-new')]"
    >
        <xsl:if
            test="count(parent::node()/dri:row[@rend='search-filter used-filter']) &lt; 1"
        >
            <div class="form-group row in-use">
                <div class="col-xs-4 col-sm-2">
                    <xsl:apply-templates
                        select="./dri:cell[1]/dri:field"
                    />
                </div>
                <div class="col-xs-4 col-sm-2">
                    <xsl:apply-templates
                        select="./dri:cell[2]/dri:field"
                    />
                </div>
                <div class="col-xs-4 col-sm-6">
                    <xsl:apply-templates
                        select="./dri:cell[3]/dri:field"
                    />
                </div>
                <div class="col-sm-2 hidden-xs-down">
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <button type="button" class="btn btn-secondary filter-add">
                            <i class="fa fa-plus"></i>
                        </button>
                        <button type="button" class="btn btn-secondary filter-remove">
                            <i class="fa fa-minus"></i>
                        </button>
                    </div>             
                </div>
            </div>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>