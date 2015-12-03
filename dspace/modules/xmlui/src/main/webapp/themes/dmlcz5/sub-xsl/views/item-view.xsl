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
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.ItemAspect.div.item-view' and @n='item-view']"
    >
        <xsl:apply-templates />
    </xsl:template>
    
    
    <xsl:template 
        match="dri:referenceSet[@id='cz.muni.ics.dmlcz5.aspects.ItemAspect.referenceSet.current-item' or @id='cz.muni.ics.dmlcz5.aspects.CollectionAspect.referenceSet.item-list']/dri:reference"
    >
        <xsl:variable
            name="itemMetadata"
        >
            <xsl:text>cocoon://</xsl:text>
            <xsl:value-of select="./@url"/>
            <xsl:text>?sections=dmdSec,fileSec</xsl:text>
        </xsl:variable>
        <div class="col-xs-12">
            <ul class="nav nav-tabs pull-sm-left pull-md-right" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" href="#entryTab" role="tab" data-toggle="tab">
                        <i18n:text>page.item.entry</i18n:text>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#fullentryTab" role="tab" data-toggle="tab">
                        <i18n:text>page.item.fullentry</i18n:text>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#referencesTab" role="tab" data-toggle="tab">
                        <i18n:text>page.item.references</i18n:text>
                        <xsl:text> (</xsl:text>
                        <xsl:text>12</xsl:text>
                        <xsl:text>)</xsl:text>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#similarTab" role="tab" data-toggle="tab">
                        <i18n:text>page.item.similar</i18n:text>
                    </a>
                </li>
            </ul>
        </div>        
        <div class="row">
            <div class="col-xs-12">
                <div class="row">
                    <div class="col-md-12">
                        <h3>
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
                                <h4 class="text-muted">
                                    <xsl:value-of
                                        select="."
                                    />
                                </h4>
                            </xsl:if>
                        </xsl:for-each>
                    </div>
                </div>
                <div class="tab-content">            
                    <div role="tabpanel" class="tab-pane active" id="entryTab">
                        <div class="row">
                            <div class="col-xs-12 col-md-6 col-xl-3">
                                <div class="card">
                                    <xsl:choose>
                                        <xsl:when test="document($itemMetadata)/mets:fileSec/mets:fileGrp[@USE='THUMBNAIL']/
                        mets:file[@GROUPID=current()/@GROUPID]">
                                            <img alt="Thumbnail" class="card-img-top hidden-xs-down">
                                                <xsl:attribute name="src">
                                                    <xsl:value-of select="document($itemMetadata)/mets:fileSec/mets:fileGrp[@USE='THUMBNAIL']/
                                    mets:file[@GROUPID=current()/@GROUPID]/mets:FLocat[@LOCTYPE='URL']/@xlink:href"/>
                                                </xsl:attribute>
                                            </img>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <img alt="!Thumbnail" class="card-img-top hidden-xs-down">
                                                <xsl:attribute name="data-src">
                                                    <xsl:text>holder.js/100px200</xsl:text>
                                                    <xsl:text>?text=No Thumbnail</xsl:text>
                                                </xsl:attribute>
                                            </img>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <div class="card-block card-block-reduced">
                                        <!--<h5>-->
                                        <i class="fa fa-file"></i>&#160;
                                        <b>
                                            <i18n:text>page.item.view</i18n:text>
                                        </b>
                                        <!--</h5>-->
                                        <ul class="list-unstyled">
                                            <li>
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
                                            </li>
                                        </ul>                                        
                                    </div>
                                    
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='contributor' and @qualifier='author']"
                                    >
                                        <div class="card-block card-block-reduced">
                                            <!--<h5>-->
                                            <i class="fa fa-user"></i>&#160;
                                            <b>
                                                <i18n:text>page.item.authors</i18n:text>
                                            </b>
                                            <!--</h5>-->
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
                                        <div class="card-block card-block-reduced">
                                            
                                            <!--<h5>-->
                                            <b>
                                                <i class="fa fa-link"></i>&#160;
                                                <i18n:text>page.item.msc</i18n:text>
                                            </b>
                                            <!--</h5>-->
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
                                        <div class="card-block card-block-reduced">
                                            <!--<h5>-->  
                                            <i class="fa fa-link"></i>&#160;                                              
                                            <b>                                                    
                                                <i18n:text>page.item.zbmath</i18n:text>
                                            </b>
                                            <!--</h5>-->      
                                            <ul class="list-unstyled">
                                                <li>
                                                    <a target="_blank" href="{concat($zblUrl,substring-after(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idzbl'],'Zbl '))}">
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
                                        <div class="card-block card-block-reduced">
                                            <!--<h5>-->
                                            <i class="fa fa-link"></i>
                                            <xsl:text> </xsl:text>
                                            <b>
                                                <i18n:text>page.item.idmr</i18n:text>
                                            </b>
                                            <!--</h5>-->      
                                            <ul class="list-unstyled">
                                                <li>
                                                    <a target="_blank" href="{concat($amsUrl,substring-after(document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='identifier' and @qualifier='idmr'],'MR'))">
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
                            </div>
                            <div class="col-xs-12 col-md-6 col-xl-9">
                                <div class="card">
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='description' and @qualifier='abstract']"
                                    >
                                        <div class="card-block">
                                            <h5 class="card-title">
                                                <i class="fa fa-file-text-o"></i>
                                                <xsl:text> </xsl:text>
                                                <i18n:text>page.item.abstract</i18n:text>
                                            </h5>                                    
                                            <p class="card-text text-justify">
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
                                            </p>
                                        </div>
                                    </xsl:if>
                                    <xsl:if
                                        test="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@element='subject' and not(@qualifier)]"
                                    >
                                        <div class="card-block">
                                            <h5 class="card-title">
                                                <i class="fa fa-tags"></i>
                                                <xsl:text> </xsl:text>
                                                <i18n:text>page.item.keywords</i18n:text>
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
                                    <div class="card-block">
                                        <h5 class="card-title">
                                            <i18n:text>page.item.citation</i18n:text>
                                        </h5>
                                        [mono]<b>Item#title</b>. (Item#language). In: Collection#editors: Collection#title. Collection#publisher, Collection#publisher#place, Collection#date. pp. Item#extent
                                    </div>
                                </div>
                            </div>                                          
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="fullentryTab">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="thead-default">
                                    <tr>
                                        <th>
                                            <i18n:text>page.item.fullentry.schema</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.element</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.qualifier</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.language</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.value</i18n:text>
                                        </th>
                                    </tr>
                                </thead>
                                <tfoot class="thead-default">
                                    <tr>
                                        <th>
                                            <i18n:text>page.item.fullentry.schema</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.element</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.qualifier</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.language</i18n:text>
                                        </th>
                                        <th>
                                            <i18n:text>page.item.fullentry.value</i18n:text>
                                        </th>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <xsl:for-each
                                        select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[not(contains(./@mdschema,'dmlcz'))]"
                                    >
                                        <tr>
                                            <td>
                                                <xsl:value-of
                                                    select="./@mdschema"
                                                />
                                            </td>
                                            <td>
                                                <xsl:value-of
                                                    select="./@element"
                                                />
                                            </td>
                                            <td>
                                                <xsl:value-of
                                                    select="./@qualifier"
                                                />
                                            </td>
                                            <td>
                                                <xsl:value-of
                                                    select="./@language"
                                                />
                                            </td>
                                            <td>
                                                <xsl:value-of
                                                    select="."
                                                />
                                            </td>
                                        </tr>
                                    </xsl:for-each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="referencesTab">
                        <small>
                            <ul>
                                <li>[1] Aiki, T., Kopfová, J.: A mathematical model for bacterial growth described by a hysteresis operator. Recent Advances in Nonlinear Analysis\vadjust{\goodbreak} Proceedings of the international conference on nonlinear analysis M. Chipot World Scientific, Hackensack 1-10 (2008). MR 2410735</li>
                                <li>[2] Alt, H. W.: On the thermostat problem. Control Cybern. 14 (1985), 171-193. MR 0839520</li>
                                <li>[3] Apushinskaya, D. E., Uraltseva, N. N., Shahgholian, H.: Lipschitz property of the free boundary in the parabolic obstacle problem. St. Petersburg Math. J. 15 (2004), 375-391. DOI 10.1090/S1061-0022-04-00813-1 | MR 2052937</li>
                                <li>[4] Gurevich, P., Shamin, R., Tikhomirov, S.: Reaction-diffusion equations with spatially distributed hysteresis. SIAM J. Math. Anal. 45 (2013), 1328-1355. DOI 10.1137/120879889 | MR 3054588 | Zbl 1276.35107</li>
                                <li>[5] Gurevich, P., Tikhomirov, S.: Uniqueness of transverse solutions for reaction-diffusion equations with spatially distributed hysteresis. Nonlinear Anal., Theory Methods Appl., Ser. A 75 (2012), 6610-6619. DOI 10.1016/j.na.2012.08.003 | MR 2965244 | Zbl 1252.35009</li>
                                <li>[6] Hoppensteadt, F. C., Jäger, W.: Pattern formation by bacteria. Lecture Notes in Biomath. 38 W. Jäger, H. Rost, P. Tautu (1980), 68-81 Springer, Berlin. DOI 10.1007/978-3-642-61850-5_7 | MR 0609347 | Zbl 0437.92023</li>
                                <li>[7] Hoppensteadt, F. C., Jäger, W., Pöppe, C.: A hysteresis model for bacterial growth patterns. Modelling of patterns in space and time. Lecture Notes in Biomath. 55 W. Jäger, J. D. Murray (1984), 123-134 Springer, Berlin. DOI 10.1007/978-3-642-45589-6_11 | MR 0813709</li>
                                <li>[8] Il'in, A. M., Markov, B. A.: Nonlinear diffusion equation and Liesegang rings. Dokl. Math. 84 (2011), 730-733 \kern 3sp Translated from Dokl. Akad. Nauk 440 (2011), 164-167 Russian. MR 2919131 | Zbl 1234.35292</li>
                                <li>[9] Ivasishen, S. D.: Green's matrices of boundary value problems for systems of a general form that are parabolic in the sense of I. G. Petrovskii. II. Mat. Sb., N. Ser. 114 (1981), 523-565. MR 0615340</li>
                                <li>[10] Klein, O.: Representation of hysteresis operators acting on vector-valued monotaffine functions. Adv. Math. Sci. Appl. 22 (2012), 471-500. MR 3100006</li>
                                <li>[11] Kopfová, J.: Hysteresis in biological models. Journal of Physics M. P. Mortell, R. E. O'Malley, A. V. Pokrovskii, V. A. Sobolev Proceedings of ``International Workshop on Multi-Rate Processess and Hysteresis'', Conference Series 55 130-134 (2007).</li>
                                <li>[12] Krasnosel'skii, M. A., Pokrovskii, A. V.: Systems with Hysteresis. Translated from the Russian. Springer, Berlin (1989). MR 0987431 | Zbl 0665.47038</li>
                                <li>[13] Ladyzhenskaya, O. A., Solonnikov, V. A., Uraltseva, N. N.: Linear and Quasilinear Equations of Parabolic Type. Russian Nauka, Moskva (1967).</li>
                                <li>[14] Rothe, F.: Global Solutions of Reaction-Diffusion Systems. Lecture Notes in Mathematics 1072 Springer, Berlin (1984). MR 0755878 | Zbl 0546.35003</li>
                                <li>[15] Shahgholian, H., Uraltseva, N., Weiss, G. S.: A parabolic two-phase obstacle-like equation. Adv. Math. 221 (2009), 861-881. DOI 10.1016/j.aim.2009.01.011 | MR 2511041 | Zbl 1168.35452</li>
                                <li>[16] Smoller, J.: Shock Waves and Reaction-Diffusion Equations. Grundlehren der Mathematischen Wissenschaften 258 Springer, New York (1994). DOI 10.1007/978-1-4612-0873-0_14 | MR 1301779 | Zbl 0807.35002</li>
                                <li>[17] Visintin, A.: Evolution problems with hysteresis in the source term. SIAM J. Math. Anal. 17 (1986), 1113-1138. DOI 10.1137/0517079 | MR 0853520 | Zbl 0618.35053</li>
                                <li>[18] Visintin, A.: Differential Models of Hysteresis. Applied Mathematical Sciences 111 Springer, Berlin (1994). DOI 10.1007/978-3-662-11557-2 | MR 1329094 | Zbl 0820.35004</li>
                            </ul>
                            <!--                                <xsl:for-each
                                select="document($itemMetadata)/mets:METS/mets:dmdSec/mets:mdWrap/mets:xmlData/dim:dim/dim:field[@mdschema='muni' and @element='reference']"
                            >
                                <div class="reference-line">
                                    <xsl:call-template name="replacehtml-ent">
                                        <xsl:with-param name="fragment" select="." />
                                    </xsl:call-template>
                                </div> 
                            </xsl:for-each>-->
                        </small>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="similarTab">  
                        <div class="row">
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-block">
                                        <h4 class="card-title">
                                            <i18n:text>page.item.similar.lsi.title</i18n:text>
                                        </h4>
                                    </div>
                                    <div class="card-block">
                                        <p class="text-muted">
                                            <i18n:text>page.item.similar.lsi.description</i18n:text>
                                        </p>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            $\varrho$-ideals in th
                                            <progress class="progress" value="84" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 84%;">84%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">On midpoints in lattices 
                                            <progress class="progress" value="83" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 83%;">83%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Multipliers on a nearl <progress class="progress" value="83" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 83%;">83%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Restricted ideals and <progress class="progress" value="82" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 82%;">82%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Concept lattices unify <progress class="progress" value="70" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 70%;">70%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Semiprime ideals in or <progress class="progress" value="65" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 70%;">65%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">On lattices and algebr <progress class="progress" value="55" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 70%;">55%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Remarks on special ide <progress class="progress" value="45" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 70%;">45%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">On the rhomboidal here <progress class="progress" value="25" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 70%;">25%</span>
                                                </div>
                                            </progress>
                                        </li>
                                    </ul>                                    
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-block">
                                        <h4 class="card-title">
                                            <i18n:text>page.item.similar.rpi.title</i18n:text>
                                        </h4>
                                    </div>
                                    <div class="card-block">
                                        <p class="text-muted">
                                            <i18n:text>page.item.similar.rpi.description</i18n:text>
                                        </p>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            $\varrho$-ideals in th
                                            <progress class="progress" value="84" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 84%;">84%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">On midpoints in lattices 
                                            <progress class="progress" value="83" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 83%;">83%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Multipliers on a nearl <progress class="progress" value="83" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 83%;">83%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Restricted ideals and <span class="badge">82%</span></li>
                                        <li class="list-group-item">Concept lattices unify <span class="badge">81%</span></li>
                                        <li class="list-group-item">Semiprime ideals in or <span class="badge">80%</span></li>
                                        <li class="list-group-item">On lattices and algebr <span class="badge">80%</span></li>
                                        <li class="list-group-item">Remarks on special ide <span class="badge">80%</span></li>
                                        <li class="list-group-item">On the rhomboidal here <span class="badge">79%</span></li>
                                    </ul>                                    
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-block">
                                        <h4 class="card-title">
                                            <i18n:text>page.item.similar.tfidf.title</i18n:text>
                                        </h4>
                                    </div>
                                    <div class="card-block">
                                        <p class="text-muted">
                                            <i18n:text>page.item.similar.tfidf.description</i18n:text>
                                        </p>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">
                                            $\varrho$-ideals in th
                                            <progress class="progress" value="84" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 84%;">84%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">On midpoints in lattices 
                                            <progress class="progress" value="83" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 83%;">83%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Multipliers on a nearl <progress class="progress" value="83" max="100">
                                                <div class="progress">
                                                    <span class="progress-bar" style="width: 83%;">83%</span>
                                                </div>
                                            </progress>
                                        </li>
                                        <li class="list-group-item">Restricted ideals and <span class="label label-default label-pill pull-right">82%</span></li>
                                        <li class="list-group-item">Concept lattices unify <span class="label label-default label-pill pull-right">81%</span></li>
                                        <li class="list-group-item">Semiprime ideals in or <span class="label label-default label-pill pull-right">80%</span></li>
                                        <li class="list-group-item">On lattices and algebr <span class="label label-default label-pill pull-right">80%</span></li>
                                        <li class="list-group-item">Remarks on special ide <span class="label label-default label-pill pull-right">80%</span></li>
                                        <li class="list-group-item">On the rhomboidal here <span class="label label-default label-pill pull-right">80%</span></li>
                                    </ul>                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>       
    </xsl:template>
            
</xsl:stylesheet>
