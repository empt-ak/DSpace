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
        match="dri:body/dri:div[@id='cz.muni.ics.digilibcz5.aspects.statik.AboutUsAspect.div.aboutus-section']"
    >
        <div class="row">
            <div class="col-xs-12">
                <h1>O digitální knihovně FF MU</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <p>Digitální knihovna Filozofické fakulty Masarykovy univerzity je interní projekt FF MU, jehož cílem je zpřístupnit odborné i širší veřejnosti veškerou odbornou produkci fakulty od jejího vzniku v roce 1919 až do současnosti. Digitální knihovna je vytvářena Centrem informačních technologií FF MU a Knihovnicko-informačním centrem MU při Ústavu výpočetní techniky MU. Kromě digitalizovaných starších tištěných dokumentů jsou do knihovny průběžně zařazovány i nově vycházející publikace. Většina dokumentů je zpřístupněna veřejnosti volně v režimu otevřeného přístupu (open access).</p>
                
                <p>V současné době digitální knihovna zpřístupňuje hlavní časopisy a periodika FF od jejich prvních ročníků vydávání a monografickou edici Spisy Filozofické fakulty MU. Ke zpřístupnění se připravují další periodické tituly, sborníky z konferencí a další knižní edice.</p>
                
                <p>Beta verze digitální knihovny byla širší veřejnosti otevřena 1. května 2013. K tomuto datu obsahovala přes 23 000 dokumentů od téměř 4 500 autorů. Zpřístupněny jsou plné texty článků z odborných časopisů a kapitoly knih (ev. celé knihy) v PDF formátu společně s metadatovými záznamy.</p>
                <p>Průběžně pracujeme na dalším rozšiřování a zdokonalování digitální knihovny. Omlouváme se za chyby a nedostatky, které digitální knihovna obsahuje – budeme vděčni, když nás na ně upozorníte (<a href="#">
                        <span class="contact-email">
                            <i18n:text>page.footer.contact.javascript</i18n:text>
                        </span>
                    </a>).</p>

                <h4>Project partners</h4>

                <ul>
                    <li>
                        <a href="http://www.math.cas.cz/">Institute of Mathematics CAS, Praha</a>
                    </li>
                    <li>
                        <a href="http://www.ics.muni.cz/index_en.html">Institute of Computer Science, Masaryk University, Brno</a>
                    </li>
                    <li>
                        <a href="http://www.fi.muni.cz/index.xhtml.en">Faculty of Informatics, Masaryk University, Brno</a>
                    </li>
                    <li>
                        <a href="http://www.karlin.mff.cuni.cz/sekce/en_index.php">Faculty of Mathematics and Physics, Charles University, Praha</a>
                    </li>
                    <li>
                        <a href="http://www.lib.cas.cz/en">Library CAS, Praha</a>
                    </li>
                </ul>
                <p>Details about the project teams can be found <a href="http://project.dml.cz/partners.html">here</a>.</p>

                <h4>Ownership of the data</h4>

                <p>The digitized journal and proceedings papers are displayed with the agreement of the publisher who owns the digital data. The digitized monographs are displayed with the agreement of the author and/or the publisher while the digital data are property of the Institute of Mathematics CAS. The database itself, in particular the bibliographic data, are property of the Institute of Mathematics CAS.</p>

                <h4>Principles</h4>

                <p>DML-CZ presents full texts articles and book chapters in PDF format, equipped with enhanced metadata including bibliographical references linked to <a href="https://zbmath.org/">Zentrablatt MATH</a> and <a href="http://www.ams.org/mathscinet/search.html">MathSciNet</a>.</p>

                <p>Each retrodigitised page is scanned in a high quality (mostly 600 DPI bitonal) and adjusted with image processing software. The digital born documents are being obtained from the original sources provided by publishers. The presented page content and format corresponds to the original one.</p>

                <p>Journals are presented and accessed according to the terms of a contract with the publisher. The digital documents displayed in the DML-CZ are authorized with electronic stamps.</p>
            </div>
        </div>
    </xsl:template>
            
</xsl:stylesheet>
