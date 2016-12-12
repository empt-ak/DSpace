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

    <xsl:output indent="no"/>

    <xsl:template
            match="dri:document/dri:body/dri:div[@id='aspect.discovery.SimpleSearch.div.search']"
    >
        <h1>
            <i18n:text>
                <xsl:value-of
                        select="./dri:head"
                />
            </i18n:text>
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

        <xsl:apply-templates
                select="dri:div[@id='aspect.discovery.SimpleSearch.div.search-results']"
        />
    </xsl:template>

    <xsl:template
            match="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-search-box']/dri:div[@id='aspect.discovery.SimpleSearch.div.general-query']"
    >
        <form method="{@method}" action="{@action}" id="{translate(@id,'.','_')}">
            <fieldset>
                <div class="row form-group">
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
                <div class="row used-filters">
                    <div class="col-sm-12">
                        <p>
                            <i18n:text>page.discovery.filters-in-use</i18n:text>
                            <xsl:text> </xsl:text>
                            <xsl:for-each
                                    select="./dri:p/dri:field"
                            >
                                <xsl:variable
                                        name="dataID"
                                >
                                    <xsl:call-template
                                            name="substring-after-last"
                                    >
                                        <xsl:with-param
                                                name="string"
                                                select="@n"
                                        />
                                        <xsl:with-param
                                                name="delimiter"
                                                select="'_'"
                                        />
                                    </xsl:call-template>
                                </xsl:variable>
                                <input
                                        type="hidden"
                                        name="{@n}"
                                        value="{./dri:value}"
                                        data-remove="{$dataID}"
                                />
                                <xsl:if
                                        test="starts-with(@n,'filter_') and not(contains(@n,'relational'))"
                                >
                                    <span class="badge badge-default" data-remove-input="{$dataID}">
                                        <xsl:value-of select="./dri:value"/>

                                        <xsl:text> </xsl:text>
                                        <i class="fa fa-times"></i>
                                    </span>
                                    <xsl:if
                                            test="position() != last()"
                                    >
                                        <xsl:text> </xsl:text>
                                    </xsl:if>
                                </xsl:if>
                            </xsl:for-each>
                        </p>
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
        <div class="hidden">
            <form action="{@action}" method="{@method}" id="{translate(@id,'.','_')}">
                <xsl:for-each
                        select="./dri:p/dri:field"
                >
                    <input type="hidden" name="{@n}" value="{./dri:value}"/>
                </xsl:for-each>
            </form>
        </div>
    </xsl:template>

    <xsl:template
            match="dri:div[@id='aspect.discovery.SimpleSearch.div.search-results']"
    >
        <div class="row form-group">
            <div class="col-12">
                <p class="form-control-static">
                    <i18n:translate>
                        <xsl:choose>
                            <xsl:when test="@itemsTotal = -1">
                                <i18n:text>xmlui.dri2xhtml.structural.pagination-info.nototal</i18n:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <i18n:text>xmlui.dri2xhtml.structural.pagination-info</i18n:text>
                            </xsl:otherwise>
                        </xsl:choose>
                        <i18n:param>
                            <xsl:value-of select="@firstItemIndex"/>
                        </i18n:param>
                        <i18n:param>
                            <xsl:value-of select="@lastItemIndex"/>
                        </i18n:param>
                        <i18n:param>
                            <xsl:value-of select="@itemsTotal"/>
                        </i18n:param>
                    </i18n:translate>
                    <xsl:apply-templates
                            select="./dri:div[@id='aspect.discovery.SimpleSearch.div.masked-page-control']/dri:div[@id='aspect.discovery.SimpleSearch.div.search-controls-gear']"
                            mode="gear-button"
                    />
                </p>
            </div>
        </div>
        <hr/>
        <xsl:for-each
                select="./dri:list[@id='aspect.discovery.SimpleSearch.list.search-results-repository']"
        >
            <xsl:apply-templates/>
        </xsl:for-each>

        <xsl:apply-templates
                select="./@pagination"
        />
    </xsl:template>
    <xsl:template
            match="dri:list[@n='comm-coll-result-list']"
    >
        <h4>
            <i18n:text>
                <xsl:value-of
                        select="./dri:head"
                />
            </i18n:text>
        </h4>
        <xsl:for-each
                select="./dri:list"
        >
            <xsl:variable
                    name="h"
            >
                <xsl:value-of
                        select="substring-before(@n,':community')"
                />
                <xsl:value-of
                        select="substring-before(@n,':collection')"
                />
            </xsl:variable>
            <xsl:variable
                    name="type"
                    select="substring-after(@n,':')"
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
            <xsl:variable name="extMets" select="document($extMetsURL)"/>

            <div class="media">
                <div class="media-left hidden-sm-down">
                    <!--
                    <img alt="page.general.thumbnail" class="img-fluid" i18n:attribute="alt">
                        <xsl:attribute name="data-src">
                            <xsl:text>holder.js/100x100</xsl:text>
                            <xsl:text>?text=No Thumbnail</xsl:text>
                        </xsl:attribute>
                    </img>
                    -->
                </div>
                <div class="media-body">
                    <h5 class="media-heading">
                        <a href="{$extMets/mets:METS/@OBJID}">
                            <xsl:choose>
                                <xsl:when
                                        test="dri:list[@n=(concat($h,':dc.title'))]/dri:item"
                                >
                                    <xsl:apply-templates
                                            select="./dri:list[@n=concat($h,':dc.title')]/dri:item[1]"
                                    />
                                </xsl:when>
                                <xsl:otherwise>
                                    <i18n:text>
                                        <xsl:value-of
                                                select="concat('page.',$type,'.missing.title')"
                                        />
                                    </i18n:text>
                                </xsl:otherwise>
                            </xsl:choose>
                        </a>
                    </h5>
                    <div>
                        <xsl:choose>
                            <xsl:when test="./dri:list[@n=concat($h,':dc.description.abstract')]/dri:item">
                                <xsl:apply-templates
                                        select="./dri:list[@n=concat($h,':dc.description.abstract')]/dri:item"
                                />
                            </xsl:when>
                            <xsl:otherwise>
                                <i18n:text>
                                    <xsl:value-of
                                            select="concat('page.',$type,'.missing.abstract')"
                                    />
                                </i18n:text>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                </div>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template
            match="dri:list[@n='item-result-list']"
    >
        <div class="row">
            <div class="col-sm-12">
                <h4>
                    <i18n:text>
                        <xsl:value-of
                                select="./dri:head"
                        />
                    </i18n:text>
                </h4>

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
                    <xsl:variable name="extMets" select="document($extMetsURL)"/>
                    <div class="media">
                        <div class="media-left hidden-sm-down">
                            <!--
                            <img alt="page.general.thumbnail" class="img-fluid" i18n:attribute="alt">
                                <xsl:attribute name="data-src">
                                    <xsl:text>holder.js/100x100</xsl:text>
                                    <xsl:text>?text=No Thumbnail</xsl:text>
                                </xsl:attribute>
                            </img>
                            -->
                        </div>
                        <div class="media-body disable-math">
                            <h5 class="media-heading">
                                <a href="{$extMets/mets:METS/@OBJID}">
                                    <xsl:choose>
                                        <xsl:when
                                                test="dri:list[@n=(concat($h,':dc.title')) and descendant::text()]"
                                        >
                                            <xsl:apply-templates
                                                    select="./dri:list[@n=concat($h,':dc.title')]/dri:item[1]"
                                            />
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <i18n:text>page.item.missing.title</i18n:text>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </a>
                            </h5>
                            <div>
                                <i class="fa fa-user"></i>
                                <span class="text-muted">
                                    <xsl:text> </xsl:text>
                                    <xsl:choose>
                                        <xsl:when
                                                test="./dri:list[@n=concat($h,':dc.contributor.author') or n=concat($h,':dc.contributor')]"
                                        >
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
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <i18n:text>page.item.missing.author</i18n:text>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </span>
                            </div>
                            <div>
                                <xsl:choose>
                                    <xsl:when test="./dri:list[@n=concat($h,':dc.description.abstract')]/dri:item">
                                        <xsl:apply-templates
                                                select="./dri:list[@n=concat($h,':dc.description.abstract')]/dri:item"
                                        />
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <i18n:text>page.item.missing.abstract</i18n:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </div>
                            <div class="text-muted">
                                <small>
                                    <i18n:text>page.item.date.lastupdate</i18n:text>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of
                                            select="substring-before(./dri:list[@n=concat($h,':dc.date.available')]/dri:item,'T')"
                                    />
                                </small>
                            </div>
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
        <mark>
            <strong>
                <xsl:value-of
                        select="."
                />
            </strong>
        </mark>
    </xsl:template>

    <xsl:template
            match="dri:div[@id='aspect.discovery.SimpleSearch.div.search-filters']"
    >
        <form method="{@method}" action="{@action}" id="{translate(@id,'.','_')}">
            <!--class="{./dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-filters-wrapper']/@rend}">-->
            <fieldset>
                <div class="form-group row math-row">
                    <div class="col-xl-3">
                        <b>Search is now math aware!</b>
                        You can now narrow you search result using math
                        <mark data-toggle="tooltip" data-placement="bottom"
                              title="Try entering quadratic formula $x^2+bx+c=0$">
                            <xsl:text>formulas.</xsl:text>
                        </mark>
                        <a href="#" class="show-math-help" data-toggle="tooltip" data-placement="top"
                           title="Click for detailed help.">
                            <i class="fa fa-question-circle-o" aria-hidden="true"></i>
                        </a>
                    </div>
                    <div class="col-xl-4">
                        <label class="h3">Latex or Mathml input</label>
                        <textarea class="form-control" placeholder="Enter formula" rows="5"
                                  id="MathInput"></textarea>
                        <div id="mathbuffer" style="visibility: hidden">
                            <xsl:text>${}$</xsl:text>
                        </div>
                    </div>
                    <div class="col-xl-5">
                        <span class="h3">Rendered output</span>
                        <div id="mathpreview"></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <!--<div class="float-right">-->
                        <p class="form-control-static">
                            <xsl:apply-templates
                                    select="./dri:div[@rend='clearfix']/dri:p[@rend='pull-right']/dri:xref[@rend='show-advanced-filters']"
                            />
                        </p>
                        <!--</div>-->
                    </div>
                </div>
                <div class="row hidden filters-hidden-section">
                    <div class="col-12">
                        <input type="hidden" name="query" value="{./dri:p/dri:field/dri:value}"/>
                        <input type="hidden" name="scope" value="{./dri:p/dri:field/dri:value}"/>
                        <xsl:apply-templates
                                select="./dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-filters-wrapper']"/>
                    </div>
                </div>
            </fieldset>
        </form>
    </xsl:template>

    <xsl:template
            match="dri:div[@id='aspect.discovery.SimpleSearch.div.discovery-filters-wrapper']"
    >
        <h4>
            <i18n:text>
                <xsl:value-of
                        select="./dri:head"
                />
            </i18n:text>
        </h4>
        <i18n:text>
            <xsl:value-of
                    select="./dri:p[1]"
            />
        </i18n:text>

        <xsl:apply-templates
                select="./dri:table[@id='aspect.discovery.SimpleSearch.table.discovery-filters']"
        />
    </xsl:template>

    <xsl:template
            match="dri:div[@rend='clearfix']/dri:p[@rend='pull-right']/dri:xref"
    >
        <button type="button" class="btn btn-primary {concat('cursor-pointer ',@rend)}">
            Toggle additional filters
        </button>
    </xsl:template>

    <xsl:template
            match="dri:table[@id='aspect.discovery.SimpleSearch.table.discovery-filters']"
    >
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template
            match="dri:row[@rend='search-filter used-filter']"
    >
        <xsl:if test="./dri:cell[1]/dri:field/dri:value[@type='option' and @option!='math']">
            <div class="form-group row in-use">
                <div class="col-4 col-sm-2">
                    <xsl:apply-templates
                            select="./dri:cell[1]/dri:field"
                    />
                </div>
                <div class="col-4 col-sm-2">
                    <xsl:apply-templates
                            select="./dri:cell[2]/dri:field"
                    />
                </div>
                <div class="col-4 col-sm-6">
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

    <xsl:template
            match="dri:row[@id='aspect.discovery.SimpleSearch.row.filter-controls']"
    >
        <div class="form-group row button-row">
            <div class="col-offset-4 col-12">
                <button type="button" class="btn btn-secondary filter-add hidden-sm-up">
                    <i18n:text>xmlui.ArtifactBrowser.SimpleSearch.filter.button.add</i18n:text>
                </button>
                <button type="submit" class="btn btn-secondary">
                    <i18n:text>xmlui.ArtifactBrowser.SimpleSearch.filter.button.apply</i18n:text>
                </button>
                <button type="reset" class="btn btn-secondary" value="reset">
                    <i18n:text>xmlui.ArtifactBrowser.SimpleSearch.filter.button.reset</i18n:text>
                </button>
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
                <div class="col-4 col-sm-2">
                    <xsl:apply-templates
                            select="./dri:cell[1]/dri:field"
                    />
                </div>
                <div class="col-4 col-sm-2">
                    <xsl:apply-templates
                            select="./dri:cell[2]/dri:field"
                    />
                </div>
                <div class="col-4 col-sm-6">
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

    <xsl:template
            match="dri:div[@id='aspect.discovery.SimpleSearch.div.search-controls-gear']"
            mode="gear-button"
    >
        <div class="dropdown" id="{translate(@id,'.','_')}">
            <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="false">
                <i class="fa fa-cog"></i>
            </button>
            <div class="dropdown-menu">
                <xsl:for-each
                        select="./dri:list/dri:item"
                >
                    <xsl:choose>
                        <xsl:when
                                test="./i18n:text"
                        >
                            <h6 class="dropdown-header">
                                <i18n:text>
                                    <xsl:value-of
                                            select="."
                                    />
                                </i18n:text>
                            </h6>
                        </xsl:when>
                        <xsl:otherwise>
                            <a class="dropdown-item {@rend}" href="{./dri:xref/@target}">
                                <span class="btn-xs invisible">
                                    <xsl:if
                                            test="@rend='gear-option gear-option-selected'"
                                    >
                                        <xsl:attribute
                                                name="class"
                                        >
                                            <xsl:text>btn-xs</xsl:text>
                                        </xsl:attribute>
                                    </xsl:if>
                                    <i class="fa fa-check"></i>
                                </span>
                                <i18n:text>
                                    <xsl:value-of
                                            select="./dri:xref"
                                    />
                                </i18n:text>
                            </a>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>