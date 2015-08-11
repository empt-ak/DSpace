<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : page-html-head.xsl
    Created on : July 24, 2015, 10:02 AM
    Author     : emptak
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:i18n="http://apache.org/cocoon/i18n/2.1"
                xmlns:dri="http://di.tamu.edu/DRI/1.0/"
                xmlns:mets="http://www.loc.gov/METS/"
                xmlns:xlink="http://www.w3.org/TR/xlink/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:dim="http://www.dspace.org/xmlns/dspace/dim"
                xmlns:xhtml="http://www.w3.org/1999/xhtml"
                xmlns:mods="http://www.loc.gov/mods/v3"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns="http://www.w3.org/1999/xhtml"
                exclude-result-prefixes="i18n dri mets xlink xsl dim xhtml mods dc">
    
    <xsl:template name="buildNavigation">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">
                            <i18n:text>navigation.main.button.toggle</i18n:text>
                        </span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!--<a class="navbar-brand" href="#">Brand</a>-->
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <form class="navbar-form navbar-right" role="search">
                        <div class="form-group">
                            <input 
                                type="text" 
                                class="form-control" 
                                placeholder="navigation.main.button.search"
                                i18n:attr="placeholder" 
                            />
                        </div>
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <!--                        <li class="active">
                            <a href="#">About DML-CZ</a>
                        </li>-->
<!--                        <li>
                            <a href="#">
                                <i class="glyphicon glyphicon-home" />
                            </a>
                        </li>-->
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.news</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.faq</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.terms</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.matharchives</i18n:text>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <i18n:text>navigation.main.button.contactus</i18n:text>
                            </a>
                        </li>
                                                  
                    </ul>                        
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </xsl:template>

</xsl:stylesheet>
