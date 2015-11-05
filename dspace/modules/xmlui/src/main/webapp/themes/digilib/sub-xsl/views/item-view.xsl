<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : landing-page.xsl
    Created on : July 24, 2015, 10:44 AM
    Author     : emptak
    Description:
        This template is a little bit tricky,
        first we match ItemView and then recursively apply
        template for matching referenceSets. second step is important
        for matching items at collection level for celebrities.
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
        match="dri:body/dri:div[@id='cz.muni.ics.digilib.aspects.ItemAspect.div.item-view' and @n='item-view']"
    >
        <xsl:apply-templates />
    </xsl:template>
    
    
    <xsl:template 
        match="dri:referenceSet[@id='cz.muni.ics.digilib.aspects.ItemAspect.referenceSet.current-item' or @id='cz.muni.ics.digilib.aspects.CollectionAspect.referenceSet.item-list']/dri:reference"
    >
        <xsl:variable
            name="itemMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of select="./@url"/>
            <xsl:text>?sections=dmdSec,fileSec</xsl:text>
        </xsl:variable>
        
        <div class="row">
            <div class="col-md-12">
                <h3 class="page-header article-title">
                    <xsl:value-of
                        select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title'][1]"
                    />
                </h3>
                <xsl:for-each
                    select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='title']"
                >
                    <xsl:if 
                        test="position() &gt; 1"
                    >
                        <h4>
                            <xsl:value-of
                                select="."
                            />
                        </h4>
                    </xsl:if>
                </xsl:for-each>
            </div>
        </div>
        <div class="row">
            <div class="col-md-2 text-right">
                <div class="item-thumbnail">
                    <xsl:choose>
                        <xsl:when test="document($itemMetadata)/mets:fileSec/mets:fileGrp[@USE='THUMBNAIL']/
                        mets:file[@GROUPID=current()/@GROUPID]">
                            <img alt="Thumbnail">
                                <xsl:attribute name="src">
                                    <xsl:value-of select="document($itemMetadata)/mets:fileSec/mets:fileGrp[@USE='THUMBNAIL']/
                                    mets:file[@GROUPID=current()/@GROUPID]/mets:FLocat[@LOCTYPE='URL']/@xlink:href"/>
                                </xsl:attribute>
                            </img>
                        </xsl:when>
                        <xsl:otherwise>
                            <img alt="!Thumbnail" class="img-responsive">
                                <xsl:attribute name="data-src">
                                    <xsl:text>holder.js/100px200</xsl:text>
                                    <xsl:text>?text=No Thumbnail</xsl:text>
                                </xsl:attribute>
                            </img>
                        </xsl:otherwise>
                    </xsl:choose>
                </div>
                <div class="item-download">
                    <h5>
                        <b>
                            <i18n:text>page.item.view</i18n:text>
                        </b>
                    </h5>
                    <i class="glyphicon glyphicon-file" />
                    <a>
                        <xsl:attribute 
                            name="href"
                        >
                            <xsl:value-of
                                select="document($itemMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/mets:FLocat/@xlink:href"
                            />
                        </xsl:attribute>
                        <xsl:variable
                            name="size"
                        >
                            <xsl:value-of
                                select="document($itemMetadata)/mets:METS/mets:fileSec/mets:fileGrp/mets:file/@SIZE"
                            />
                        </xsl:variable>
                        <i18n:text>page.item.fulltext</i18n:text> 
                        <xsl:text> (</xsl:text>
                        <xsl:choose>
                            <xsl:when test="$size &lt; 1024">
                                <xsl:value-of select="$size"/>
                                <xsl:text> </xsl:text>
                                <i18n:text>file.extension.size.bytes</i18n:text>
                            </xsl:when>
                            <xsl:when test="$size &lt; 1024 * 1024">
                                <xsl:value-of select="substring(string($size div 1024),1,5)"/>
                                <xsl:text> </xsl:text>
                                <i18n:text>file.extension.size.kbytes</i18n:text>
                            </xsl:when>
                            <xsl:when test="$size &lt; 1024 * 1024 * 1024">
                                <xsl:value-of select="substring(string($size div (1024 * 1024)),1,5)"/>
                                <xsl:text> </xsl:text>
                                <i18n:text>file.extension.size.mbytes</i18n:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="substring(string($size div (1024 * 1024 * 1024)),1,5)"/>
                                <xsl:text> </xsl:text>
                                <i18n:text>file.extension.size.gbytes</i18n:text>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:text>)</xsl:text>
                    </a>
                </div>
                <xsl:if
                    test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                >
                    <div class="item-author">
                        <h5>
                            <i class="glyphicon glyphicon-user" />&#160;
                            <b>
                                <i18n:text>page.item.authors</i18n:text>
                            </b>
                        </h5>
                        <ul class="list-unstyled">
                            <xsl:for-each
                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                            >
                                <li>
                                    <a>                                        
                                        <xsl:value-of
                                            select="." 
                                        />
                                    </a>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </div>
                </xsl:if>
                
                <xsl:if
                    test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and @qualifier='msc']"
                >
                    <div class="item-msc">
                        <h5>
                            <b>
                                <i18n:text>page.item.msc</i18n:text>
                            </b>
                        </h5>
                        <ul class="list-unstyled">
                            <xsl:for-each
                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and @qualifier='msc']"
                            >
                                <li>
                                    <a class="label label-info">
                                        <xsl:value-of
                                            select="."
                                        />
                                    </a>
                                </li>
                            </xsl:for-each>                 
                        </ul>
                    </div>
                </xsl:if> 
                <xsl:if
                    test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idzbl']"
                >
                    <div class="item-zbl">
                        <h5>
                            <b>
                                <i18n:text>page.item.zbmath</i18n:text>
                            </b>
                        </h5>      
                        <ul class="list-unstyled">
                            <li>
                                <a target="_blank">
                                    <xsl:attribute
                                        name="href"
                                    >
                                        <xsl:text>https://zbmath.org/?q=an:</xsl:text>
                                        <xsl:value-of
                                            select="substring-after(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idzbl'],'Zbl ')"
                                        />
                                    </xsl:attribute>
                                    <xsl:value-of
                                        select="substring-after(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idzbl'],'Zbl ')"
                                    />
                                    <xsl:text> </xsl:text>
                                    <i class="glyphicon glyphicon-new-window" />                            
                                </a>                                
                            </li>
                        </ul>
                    </div>
                </xsl:if>
                <xsl:if
                    test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idmr']"
                >
                    <div class="item-mr">
                        <h5>
                            <b>
                                <i18n:text>page.item.idmr</i18n:text>
                            </b>
                        </h5>      
                        <ul class="list-unstyled">
                            <li>
                                <a target="_blank">
                                    <xsl:attribute
                                        name="href"
                                    >
                                        <xsl:text>http://www.ams.org/mathscinet-getitem?mr=</xsl:text>
                                        <xsl:value-of
                                            select="substring-after(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idmr'],'MR')"
                                        />
                                    </xsl:attribute>
                                    <xsl:value-of
                                        select="substring-after(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idmr'],'MR')"
                                    />
                                    <xsl:text> </xsl:text>
                                    <i class="glyphicon glyphicon-new-window" />  
                                </a>
                            </li>
                        </ul>
                    </div>  
                </xsl:if>  
            </div>
            <div class="col-md-10">
                <div class="item-abstract text-justify">
                    <xsl:for-each
                        select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                    >
                        <xsl:value-of
                            select="./text()"
                        />
                        <xsl:if 
                            test="position() != last()"
                        >
                            <br />
                        </xsl:if>
                    </xsl:for-each>
                </div>
                <div class="item-citation">
                    <h5 class="item-description-title">
                        <i class="glyphicon glyphicon-envelope" />&#160;
                        <b>
                            <i18n:text>page.item.citation</i18n:text>
                        </b>
                    </h5>
                    [mono]<b>Item#title</b>. (Item#language). In: Collection#editors: Collection#title. Collection#publisher, Collection#publisher#place, Collection#date. pp. Item#extent
                </div>  
                <xsl:if
                    test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and not(@qualifier)]"
                >
                    <div class="item-keyword">
                        <h5 class="item-description-title">
                            <i class="glyphicon glyphicon-tag" />&#160;
                            <b>
                                <i18n:text>page.item.keywords</i18n:text>
                            </b>
                        </h5>
                        <ul class="list-inline">
                            <xsl:for-each
                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and not(@qualifier)]"
                            >
                                <li>
                                    <a class="label label-primary">
                                        <xsl:value-of
                                            select="."
                                        />
                                    </a>
                                </li>
                            </xsl:for-each>          
                        </ul>                    
                    </div>
                </xsl:if>  
                <div class="panel-group offset-top-75" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel panel-success">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    <xsl:text>!Similar articles </xsl:text>
                                    <span class="caret"></span>
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <ul class="list-group">
                                <li class="list-group-item similarity-item-header">Latent semantic indexing method</li>
                                <li class="list-group-item">$\varrho$-ideals in th <span class="badge">84%</span></li>
                                <li class="list-group-item">On midpoints in lattices <span class="badge">83%</span></li>
                                <li class="list-group-item">Multipliers on a nearl <span class="badge">83%</span></li>
                                <li class="list-group-item">Restricted ideals and <span class="badge">82%</span></li>
                                <li class="list-group-item">Concept lattices unify <span class="badge">81%</span></li>
                                <li class="list-group-item">Semiprime ideals in or <span class="badge">80%</span></li>
                                <li class="list-group-item">On lattices and algebr <span class="badge">80%</span></li>
                                <li class="list-group-item">Remarks on special ide <span class="badge">80%</span></li>
                                <li class="list-group-item">On the rhomboidal here <span class="badge">79%</span></li>
                                <li class="list-group-item similarity-item-header">Random projection method</li>
                                <li class="list-group-item">$\varrho$-ideals in th <span class="badge">84%</span></li>
                                <li class="list-group-item">On midpoints in lattices <span class="badge">83%</span></li>
                                <li class="list-group-item">Multipliers on a nearl <span class="badge">83%</span></li>
                                <li class="list-group-item">Restricted ideals and <span class="badge">82%</span></li>
                                <li class="list-group-item">Concept lattices unify <span class="badge">81%</span></li>
                                <li class="list-group-item">Semiprime ideals in or <span class="badge">80%</span></li>
                                <li class="list-group-item">On lattices and algebr <span class="badge">80%</span></li>
                                <li class="list-group-item">Remarks on special ide <span class="badge">80%</span></li>
                                <li class="list-group-item">On the rhomboidal here <span class="badge">79%</span></li>
                                <li class="list-group-item similarity-item-header">TFIDF method</li>
                                <li class="list-group-item">$\varrho$-ideals in th <span class="badge">84%</span></li>
                                <li class="list-group-item">On midpoints in lattices <span class="badge">83%</span></li>
                                <li class="list-group-item">Multipliers on a nearl <span class="badge">83%</span></li>
                                <li class="list-group-item">Restricted ideals and <span class="badge">82%</span></li>
                                <li class="list-group-item">Concept lattices unify <span class="badge">81%</span></li>
                                <li class="list-group-item">Semiprime ideals in or <span class="badge">80%</span></li>
                                <li class="list-group-item">On lattices and algebr <span class="badge">80%</span></li>
                                <li class="list-group-item">Remarks on special ide <span class="badge">80%</span></li>
                                <li class="list-group-item">On the rhomboidal here <span class="badge">79%</span></li>
                            </ul>
                        </div>
                    </div>
                    <div class="panel panel-primary">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    <xsl:text>!References </xsl:text>
                                    <span class="caret"></span>
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <div class="item-references">
                                    <small>
                                        <xsl:for-each
                                            select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@mdschema='muni' and @element='reference']"
                                        >
                                            <div class="reference-line">
                                                <xsl:call-template name="replacehtml-ent">
                                                    <xsl:with-param name="fragment" select="." />
                                                </xsl:call-template>
                                            </div> 
                                        </xsl:for-each>
                                    </small>
                                </div>
                            </div>
                        </div>
                    </div>                    
                </div>
            </div>                                          
        </div>
    </xsl:template>
            
</xsl:stylesheet>
