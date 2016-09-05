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
        <xsl:variable name="confidenceIndicatorID" select="concat(translate(@id,'.','_'),'_confidence_indicator')"/>
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
                <xsl:value-of select="." />
            </xsl:otherwise>
<!--            <xsl:when test="@type= 'textarea'">
                    <textarea>
                        <xsl:call-template name="fieldAttributes"/>
                        <xsl:attribute name="onkeydown">event.cancelBubble=true;</xsl:attribute>

                        
                            if the cols and rows attributes are not defined we need to call
                            the templates for them since they are required attributes in strict xhtml
                         
                        <xsl:choose>
                            <xsl:when test="not(./dri:params[@cols])">
                                <xsl:call-template name="textAreaCols"/>
                            </xsl:when>
                        </xsl:choose>
                        <xsl:choose>
                            <xsl:when test="not(./dri:params[@rows])">
                                <xsl:call-template name="textAreaRows"/>
                            </xsl:when>
                        </xsl:choose>

                        <xsl:apply-templates />
                        <xsl:choose>
                            <xsl:when test="./dri:value[@type='raw']">
                                <xsl:copy-of select="./dri:value[@type='raw']/node()"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:copy-of select="./dri:value[@type='default']/node()"/>
                            </xsl:otherwise>
                        </xsl:choose>
                        <xsl:if  test="string-length(./dri:value) &lt; 1">
                            <i18n:text>xmlui.dri2xhtml.default.textarea.value</i18n:text>
                        </xsl:if>
                    </textarea>


                 add place to store authority value 
                <xsl:if test="dri:params/@authorityControlled">
                    <xsl:variable name="confidence">
                        <xsl:if test="./dri:value[@type='authority']">
                            <xsl:value-of select="./dri:value[@type='authority']/@confidence"/>
                        </xsl:if>
                    </xsl:variable>
                     add authority confidence widget 
                    <xsl:call-template name="authorityConfidenceIcon">
                        <xsl:with-param name="confidence" select="$confidence"/>
                        <xsl:with-param name="id" select="$confidenceIndicatorID"/>
                    </xsl:call-template>
                    <xsl:call-template name="authorityInputFields">
                        <xsl:with-param name="name" select="@n"/>
                        <xsl:with-param name="id" select="@id"/>
                        <xsl:with-param name="authValue" select="dri:value[@type='authority']/text()"/>
                        <xsl:with-param name="confValue" select="dri:value[@type='authority']/@confidence"/>
                        <xsl:with-param name="confIndicatorID" select="$confidenceIndicatorID"/>
                        <xsl:with-param name="unlockButton" select="dri:value[@type='authority']/dri:field[@rend='ds-authority-lock']/@n"/>
                        <xsl:with-param name="unlockHelp" select="dri:value[@type='authority']/dri:field[@rend='ds-authority-lock']/dri:help"/>
                    </xsl:call-template>
                </xsl:if>
                 add choice mechanisms 
                <xsl:choose>
                    <xsl:when test="dri:params/@choicesPresentation = 'suggest'">
                        <xsl:call-template name="addAuthorityAutocomplete">
                            <xsl:with-param name="confidenceIndicatorID" select="$confidenceIndicatorID"/>
                            <xsl:with-param name="confidenceName">
                                <xsl:value-of select="concat(@n,'_confidence')"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="dri:params/@choicesPresentation = 'lookup'">
                        <xsl:call-template name="addLookupButton">
                            <xsl:with-param name="isName" select="'false'"/>
                            <xsl:with-param name="confIndicator" select="$confidenceIndicatorID"/>
                        </xsl:call-template>
                    </xsl:when>

                    <xsl:when test="dri:params/@choicesPresentation = 'authorLookup'">
                        <xsl:call-template name="addLookupButtonAuthor">
                            <xsl:with-param name="isName" select="'true'"/>
                            <xsl:with-param name="confIndicator" select="$confidenceIndicatorID"/>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>

             This is changing dramatically 
            <xsl:when test="@type= 'checkbox' or @type= 'radio'">
                <fieldset>
                    <xsl:call-template name="standardAttributes">
                        <xsl:with-param name="class">
                            <xsl:text>ds-</xsl:text><xsl:value-of select="@type"/><xsl:text>-field </xsl:text>
                            <xsl:if test="dri:error">
                                <xsl:text>error </xsl:text>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute>
                    <xsl:apply-templates />
                </fieldset>
            </xsl:when>
            <xsl:when test="@type= 'composite'">
                 TODO: add error and help stuff on top of the composite 
                <span class="ds-composite-field">
                    <xsl:apply-templates select="dri:field" mode="compositeComponent"/>
                </span>
                <xsl:apply-templates select="dri:field/dri:error" mode="compositeComponent"/>
                <xsl:apply-templates select="dri:error" mode="compositeComponent"/>
                <xsl:apply-templates select="dri:field/dri:help" mode="compositeComponent"/>
            </xsl:when>
             text, password, file, and hidden types are handled the same.
                Buttons: added the xsl:if check which will override the type attribute button
                    with the value 'submit'. No reset buttons for now...
            
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
            <xsl:otherwise>
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


                <xsl:variable name="confIndicatorID" select="concat(@id,'_confidence_indicator')"/>
                <xsl:if test="dri:params/@authorityControlled">
                    <xsl:variable name="confidence">
                        <xsl:if test="./dri:value[@type='authority']">
                            <xsl:value-of select="./dri:value[@type='authority']/@confidence"/>
                        </xsl:if>
                    </xsl:variable>
                     add authority confidence widget 
                    <xsl:call-template name="authorityConfidenceIcon">
                        <xsl:with-param name="confidence" select="$confidence"/>
                        <xsl:with-param name="id" select="$confidenceIndicatorID"/>
                    </xsl:call-template>
                    <xsl:call-template name="authorityInputFields">
                        <xsl:with-param name="name" select="@n"/>
                        <xsl:with-param name="id" select="@id"/>
                        <xsl:with-param name="authValue" select="dri:value[@type='authority']/text()"/>
                        <xsl:with-param name="confValue" select="dri:value[@type='authority']/@confidence"/>
                    </xsl:call-template>
                </xsl:if>
                <xsl:choose>
                    <xsl:when test="dri:params/@choicesPresentation = 'suggest'">
                        <xsl:call-template name="addAuthorityAutocomplete">
                            <xsl:with-param name="confidenceIndicatorID" select="$confidenceIndicatorID"/>
                            <xsl:with-param name="confidenceName">
                                <xsl:value-of select="concat(@n,'_confidence')"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="dri:params/@choicesPresentation = 'lookup'">
                        <xsl:call-template name="addLookupButton">
                            <xsl:with-param name="isName" select="'false'"/>
                            <xsl:with-param name="confIndicator" select="$confidenceIndicatorID"/>
                        </xsl:call-template>
                    </xsl:when>

                    <xsl:when test="dri:params/@choicesPresentation = 'authorLookup'">
                        <xsl:call-template name="addLookupButtonAuthor">
                            <xsl:with-param name="isName" select="'true'"/>
                            <xsl:with-param name="confIndicator" select="$confidenceIndicatorID"/>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>
            </xsl:otherwise>-->
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="dri:field[@type='select']/dri:option">
        <option>
            <xsl:attribute name="value">
                <xsl:value-of select="@returnValue"/>
            </xsl:attribute>
            <xsl:if test="../dri:value[@type='option'][@option = current()/@returnValue]">
                <xsl:attribute name="selected">selected</xsl:attribute>
            </xsl:if>
            <xsl:apply-templates />
        </option>
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
                <xsl:value-of select="@n"/>
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
        <xsl:if test="@type= 'textarea'">
            <xsl:attribute name="onfocus">javascript:tFocus(this);</xsl:attribute>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
