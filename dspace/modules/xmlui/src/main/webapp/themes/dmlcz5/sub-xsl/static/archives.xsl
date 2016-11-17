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
        match="dri:body/dri:div[@id='cz.muni.ics.dmlcz5.aspects.statik.ArchiveAspect.div.archive-section']"
    >
        <div class="row">
            <div class="col-xs-12">
                <h1>Links to other Digital Mathematics Libraries</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <ul>
                    <li>
                        <a href="http://www.numdam.org/?lang=en">NUMDAM - French DML</a>
                    </li>
                    <li>
                        <a href="http://gdz.sub.uni-goettingen.de/en/dms/colbrowse/?DC=mathematica">Göttinger Digitalizierungszentrum - Mathematical Literature</a>
                    </li>
                    <li>
                        <a href="http://dmle.cindoc.csic.es/">Biblioteca Digital Espańola Matemáticas</a>
                    </li>
                    <li>
                        <a href="http://matwbn.icm.edu.pl">The Polish Virtual Library of Science - Mathematical Collection</a>
                    </li>
                    <li>
                        <a href="http://www.rusdml.de/rusdml/">RusDML - Russian DML</a>
                    </li>
                    <li>
                        <a href="http://www.mathnet.ru/">Math-Net.Ru - All-Russian Mathematical Portal</a>
                    </li>
                    <li>
                        <a href="http://elib.mi.sanu.ac.rs/">eLibrary of Mathematical Institute of the Serbian Academy of Sciences and Arts</a>
                    </li>
                    <li>
                        <a href="http://sci-gems.math.bas.bg/">BulDML - Bulgarian Digital Mathematics Library</a>
                    </li>
                    <li>
                        <a href="http://www.cedram.org/">CEDRAM - Center for diffusion of academic mathematical journals</a>
                    </li>
                    <li>
                        <a href="http://www.emis.de/ELibM.html">ELibM - Electronic Library of Mathematics</a>
                    </li>
                    <li>
                        <a href="http://projecteuclid.org/">Euclid - Mathematics and Statistics Online</a>
                    </li>
                    <li>
                        <a href="http://digital.library.cornell.edu/m/math/">Cornell University - Historical Math Monographs</a>
                    </li>
                    <li>
                        <a href="http://quod.lib.umich.edu/u/umhistmath/">University of Michigan - Historical Mathematics Collection</a>
                    </li>
                    <li>
                        <a href="http://arxiv.org/archive/math">ArXiv.org Mathematics</a>
                    </li>
                    <li>
                        <a href="https://www.math.uni-bielefeld.de/~rehmann/DML/dml_links.html">Portal of digitized mathematics worldwide (Ulf Rehmann, Univ Bielefeld)</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <img style="margin-left: 1em" class="hidden-lg-up" alt="page.general.thumbnail" i18n:attribute="alt">
                    <xsl:attribute
                        name="src"
                    >
                        <xsl:value-of
                            select="$resourcePath"
                        />
                        <xsl:text>/img/eudml.png</xsl:text>
                    </xsl:attribute>
                </img>
            </div>
        </div>
        <div class="media" style="margin-top: 1em">
            <div class="media-left media-top">
                <img alt="page.general.thumbnail" i18n:attribute="alt" class="media-object hidden-md-down">
                    <xsl:attribute
                        name="src"
                    >
                        <xsl:value-of
                            select="$resourcePath"
                        />
                        <xsl:text>/img/eudml.png</xsl:text>
                    </xsl:attribute>
                </img>
            </div>
            <div class="media-body">
                <a href="https://eudml.org/">The EuDML</a> combines data, tools 
                and services initially assembled or created in the 
                <a href="https://project.eudml.org/">project</a> 
                <i>The European Digital Mathematics Library</i> that was 
                co-funded by the European Commission from February 2009 to 
                January 2013. Access to EuDML content is provided by a central 
                search engine allowing easy and unified search and retrieval of 
                the mathematical literature. The EuDML services provide a 
                gateway to the electronic publications and repositories of 
                participating data providers, while the latter retain full 
                responsibility and control over the creation, maintenance and 
                the access provisions of their data collections. See the 
                <a href="https://eudml.org/statistics">statistics</a> for up to 
                date information about EuDML content.
            </div>
        </div>
    </xsl:template>
            
</xsl:stylesheet>
