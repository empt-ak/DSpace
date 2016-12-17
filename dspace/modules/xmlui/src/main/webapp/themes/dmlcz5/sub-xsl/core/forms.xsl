<!--

    The contents of this file are subject to the license and copyright
    detailed in the LICENSE and NOTICE files at the root of the source
    tree and available online at

    http://www.dspace.org/license/

-->

<!--
    Templates to cover the forms and forms fields.

    Author: art.lowel at atmire.com
    Author: lieven.droogmans at atmire.com
    Author: ben at atmire.com
    Author: Alexey Maslov

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

    <xsl:output indent="yes"/>

    <xsl:template match="dri:field">
        <xsl:choose>
            <xsl:when test="@type= 'select'">
                <select>
                    <xsl:call-template name="fieldAttributes"/>
                    <xsl:apply-templates/>
                </select>
            </xsl:when>
            <xsl:when test="@type='button'">
                <button>
                    <xsl:call-template name="fieldAttributes"/>
                    <xsl:attribute name="type">submit</xsl:attribute>
                    <xsl:choose>
                        <xsl:when test="dri:value/i18n:text">
                            <xsl:apply-templates select="dri:value/*"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="dri:value"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </button>
            </xsl:when>
            <xsl:when test="@type='text'">
                <input>
                    <xsl:call-template name="fieldAttributes"/>
                    <xsl:attribute name="value">
                        <xsl:choose>
                            <xsl:when test="./dri:value[@type='raw']">
                                <xsl:value-of select="./dri:value[@type='raw']"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="./dri:value[@type='default']"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <xsl:if test="dri:value/i18n:text">
                        <xsl:attribute name="i18n:attr">value</xsl:attribute>
                    </xsl:if>
                    <xsl:apply-templates/>
                </input>
            </xsl:when>
            <xsl:otherwise>
                TODO?
                <xsl:value-of select="."/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="dri:field[@type='select']/dri:option">
        <!-- hack to hide math from select -->
        <!--<xsl:if-->
                <!--test="@returnValue != 'math'"-->
        <!--&gt;-->
            <option>
                <xsl:attribute name="value">
                    <xsl:value-of select="@returnValue"/>
                </xsl:attribute>
                <xsl:if test="../dri:value[@type='option'][@option = current()/@returnValue]">
                    <xsl:attribute name="selected">selected</xsl:attribute>
                </xsl:if>
                <xsl:apply-templates/>
            </option>
        <!--</xsl:if>-->
    </xsl:template>

    <xsl:template name="fieldAttributes">
        <xsl:call-template name="standardAttributes">
            <xsl:with-param name="class">
                <xsl:choose>
                    <xsl:when test="@type='button'">
                        <xsl:text>btn</xsl:text>
                        <xsl:if test="not(contains(@rend, 'btn-'))">
                            <xsl:text> btn-secondary</xsl:text>
                        </xsl:if>
                    </xsl:when>
                    <xsl:when test="not(@type='file')">
                        <xsl:text>form-control </xsl:text>
                    </xsl:when>
                </xsl:choose>

                <xsl:if test="@rend">
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="@rend"/>
                </xsl:if>
                <xsl:if test="dri:error or parent::node()[@type='composite']/dri:error">
                    <xsl:text>input-with-feedback </xsl:text>
                </xsl:if>
            </xsl:with-param>
            <xsl:with-param name="placeholder">
                <xsl:if test="@placeholder">
                    <xsl:value-of select="@placeholder"/>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:if test="@disabled='yes' or ../@rend = 'disabled'">
            <xsl:attribute name="disabled">disabled</xsl:attribute>
        </xsl:if>
        <xsl:if test="@type != 'checkbox' and @type != 'radio' and @n != 'submit'">
            <xsl:attribute name="name">
                <xsl:value-of select="@n" />
            </xsl:attribute>
        </xsl:if>
        <xsl:if test="@type != 'select' and @type != 'textarea' and @type != 'checkbox' and @type != 'radio' ">
            <xsl:attribute name="type">
                <xsl:choose>
                    <xsl:when test="@n = 'login_email'">
                        <xsl:text>email</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@type"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
