<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-layout.xsl
    Created on : July 24, 2015, 9:32 AM
    Author     : emptak
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:dri="http://di.tamu.edu/DRI/1.0/"                
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"               
                xmlns="http://www.w3.org/1999/xhtml"
                exclude-result-prefixes="dri xsl">

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
   
    <xsl:template match="dri:document">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;</xsl:text>
        <html>
            <head>
                <!-- in page-html-head.xsl -->
                <xsl:call-template name="buildHTMLHead" />
            </head>
            <body>
                <xsl:call-template name="navbar" />
                <div class="container-fluid mt-4">
                    <div class="row">
                        <main class="col-md-10 push-sm-2">
                            <xsl:apply-templates />
                        </main>
                        <aside class="col-md-2 pull-sm-10">
                            <xsl:call-template name="sidebar" />
                        </aside>
                    </div>
                </div>
                <div class="footer pt-4">
                    <div class="container-fluid">
                        <footer class="row">
                            <article class="offset-md-2 col-md-2">
                                <p class="h3">DML-CZ</p>
                                is offering an open access to the metadata and fulltext of mathematical journals, proceedings and
                                books published throughout history in the Czech lands.
                            </article>
                            <nav class="col-md-1">
                                <p class="h3">Links</p>
                                <ul class="list-unstyled">
                                    <li>
                                        <a href="/dmlcz5/sitemap">Sitemap</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/aboutus">About us</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/news">News</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/conditions">Conditions of Use</a>
                                    </li>
                                </ul>
                            </nav>
                            <nav class="col-md-1">
                                <p class="h3">&#160;</p>
                                <ul class="list-unstyled">
                                    <li>
                                        <a href="/dmlcz5/faq">FAQ</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/archives">Math archives</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/contact">Contact Us</a>
                                    </li>
                                </ul>
                            </nav>
                            <nav class="col-md-1">
                                <p class="h3">Subscribe</p>
                                <ul class="list-unstyled">
                                    <li>
                                        <a href="/dmlcz5/sitemap">Rss 1.0</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/aboutus">Rss 2.0</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/news">Atom</a>
                                    </li>
                                </ul>
                            </nav>
                            <nav class="col-md-2">
                                <p class="h3">Social networks</p>
                                <ul class="list-unstyled">
                                    <li>
                                        <a href="/dmlcz5/sitemap">Facebook</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/aboutus">Twitter</a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/news">Google+</a>
                                    </li>
                                </ul>
                            </nav>
                            <nav class="col-md-2">
                                <p class="h3">Contact</p>
                                <ul class="list-unstyled">
                                    <li>
                                        <a href="mailto:webmaster@dml.cz"><span class="contact-email">webmaster@dml.cz</span></a>
                                    </li>
                                    <li>
                                        <a href="/dmlcz5/aboutus">Contact form</a>
                                    </li>
                                </ul>
                            </nav>
                        </footer>
                        <footer class="row mt-3 p-2">
                            <div class="offset-md-2 col-md-5 ">
                                &#169; 2010&#8211;<span class="copyright-date"></span>
                                <a href="#">Institute of Mathematics ASCR</a>
                            </div>
                        </footer>
                    </div>
                </div>
                <!--<div class="container-fluid">-->
                    <!--&lt;!&ndash; in page-body-head.xsl &ndash;&gt;-->
                    <!--<xsl:call-template name="buildBodyHead" />-->
                    <!--&lt;!&ndash; in page-body-navigation.xsl &ndash;&gt;-->
                    <!--<xsl:call-template name="buildNavigation" />-->
                    <!--<div class="row content-body">-->
                        <!--<div class="col-md-3 col-xl-2 left-sidebar">-->
                            <!--<xsl:call-template name="buildLeftSidebar" />-->
                        <!--</div>-->
                        <!--<div class="col-md-6 col-xl-8">-->
                            <!--<xsl:call-template name="buildBreadcrumb" />-->
                            <!--<xsl:apply-templates />-->
                        <!--</div>-->
                        <!--<div class="col-md-3 col-xl-2">-->
                            <!--<xsl:call-template name="buildRightSidebar" />-->
                        <!--</div>-->
                    <!--</div>-->
                    <!--<footer class="row" id="footer">-->
                        <!--<xsl:call-template name="buildFooter" />&lt;!&ndash; in commons&ndash;&gt;-->
                    <!--</footer>-->
                <!--</div>-->
                <!--<xsl:call-template name="buildBodyFooter" />-->
                <xsl:call-template name="javascript-footer" />
            </body>
        </html>        
    </xsl:template>
</xsl:stylesheet>