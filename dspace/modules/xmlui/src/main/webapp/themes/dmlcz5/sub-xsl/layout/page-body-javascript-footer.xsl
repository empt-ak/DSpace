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
    <xsl:output method="xml" encoding="UTF-8" indent="no"/>

    <xsl:template
            name="javascript-footer"
    >
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/webjars/jquery/3.1.1/jquery.min.js</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/webjars/tether/1.3.7/dist/js/tether.min.js</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
        <!--<script>-->
        <!--<xsl:attribute-->
        <!--name="src"-->
        <!--&gt;-->
        <!--<xsl:value-of-->
        <!--select="$contextPath"-->
        <!--/>-->
        <!--<xsl:text>/webjars/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js</xsl:text>-->
        <!--</xsl:attribute>-->
        <!--&#160;-->
        <!--</script>-->
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/themes/</xsl:text>
                <xsl:value-of
                        select="$theme"
                />
                <xsl:text>/js/bootstrap.min.js</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/webjars/mathjax/2.7.0/MathJax.js?config=TeX-MML-AM_CHTML</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
        <script type="text/x-mathjax-config">
            MathJax.Hub.Config({
                tex2jax: {
                    inlineMath: [['$', '$'], ['\\(', '\\)']],
                    ignoreClass: "detail-field-data|exception|disable-math"
                },
                TeX: {
                    Macros: {
                        AA: '{\\mathring A}'
                    }
                }
            });
        </script>
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/themes/</xsl:text>
                <xsl:value-of
                        select="$theme"
                />
                <xsl:text>/js/dmlcz.js</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/themes/</xsl:text>
                <xsl:value-of
                        select="$theme"
                />
                <xsl:text>/js/dmlcz.math.js</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/webjars/holderjs/2.5.2/holder.min.js</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
        <script>
            <xsl:attribute
                    name="src"
            >
                <xsl:value-of
                        select="$contextPath"
                />
                <xsl:text>/webjars/cookieconsent/3.0.3/build/cookieconsent.min.js</xsl:text>
            </xsl:attribute>
            &#160;
        </script>
    </xsl:template>

</xsl:stylesheet>
