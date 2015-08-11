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
    
    <xsl:template 
        name="buildBodyFooter"
    >
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">&#160;</script>
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
                <xsl:text>/themes/</xsl:text>
                <xsl:value-of 
                    select="$theme" 
                />
                <xsl:text>/js/chart.min.js</xsl:text>
                <!--<xsl:text>/js/Chart.min.alpha.2.js</xsl:text>-->
            </xsl:attribute>
                    &#160;
        </script>
<!--        <script>
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
                <xsl:text>/js/colorchain.js</xsl:text>
            </xsl:attribute>
                    &#160;
        </script>-->
        
        <script>
            $(document).ready(function(){
            
<!--            if($(".item-keyword")[0].scrollWidth > $(".item-keyword").innerWidth()){
                console.log('hue');
            }-->
            
            if($("#myChart").length){
            var colors = getRandomColor(6);
            
            var data = [
            <xsl:variable name="solrQuery">
                    <xsl:text>select?q=*%3A*&amp;fq=location%3A</xsl:text>
                    <xsl:call-template
                    name="getSolrLocation"
                />
                <xsl:text>&amp;rows=0&amp;wt=xml&amp;facet=true&amp;facet.field=msc_keyword</xsl:text>
                </xsl:variable>
                
                <!--<xsl:value-of select="concat($solrServer,$solrQuery)" />-->
                <xsl:for-each select="document(concat($solrServer,$solrQuery))/response/lst[@name='facet_counts']/lst[@name='facet_fields']/lst[@name='msc_keyword']/int[. > 0]">
                    { value: <xsl:value-of select="current()" />, label: "<xsl:value-of select="current()/@name" />",color: getRandomColor(),highlight: getRandomColor()}
                    <xsl:if 
                        test="position() != last()"
                    >
                        <xsl:text>,</xsl:text>
                    </xsl:if>
                </xsl:for-each>
            ];
           // Get context with jQuery - using jQuery's .get() method.
            var ctx = $("#myChart").get(0).getContext("2d");
            // This will get the first returned node in the jQuery collection.
            var myNewChart = new Chart(ctx).Pie(data,{responsive : true});
            }
            
            
<!--            function getRandomColor(numberOfColors)
            {
                return ColorSequenceGenerator.createColorSequence(numberOfColors, {lightnessStart:80, saturationStart:70, randomHueOffset: true}).getColors();
            }
            
            var colors = getRandomColor(10);
            
            console.log(data.length);
            
            $.each(colors,function(i,e){
                console.log(e);
            });-->
            
<!--            getRandomColor(10).each(function(){
                console.log($(this));
            });-->
            
            function getRandomColor() {
    return "#" + (Math.round(Math.random() * 0XFFFFFF)).toString(16);
}
            
            });
            
        </script>
        
        <script type="text/x-mathjax-config">
            MathJax.Hub.Config({
                tex2jax: {
                    inlineMath: [['$','$'], ['\\(','\\)']],
                    ignoreClass: "detail-field-data|detailtable|exception|issue-item-abstract|issue-item-title|keyword-sidebar"
                },
                TeX: {
                    Macros: {
                        AA: '{\\mathring A}'
                    }
                }
            });
        </script>
        <script type="text/javascript" src="//cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">&#160;</script>
    </xsl:template>

</xsl:stylesheet>
