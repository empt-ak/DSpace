/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.xmlui.aspect.discovery;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by emptak on 12/8/16.
 */
public class TeXConverter
{
    public static String PARSE_ERROR = "parse_error";
    public static final String LATEX_TO_XHTML_CONVERSION_WS_URL = "https://mir.fi.muni.cz/cgi-bin/latex-to-mathml-via-latexml.cgi";
    private static final Logger logger = Logger.getLogger(TeXConverter.class);

    /**
     * Converts TeX formula to MathML using LaTeXML through a web service.
     *
     * @param query String containing one or more keywords and TeX formulae
     * (formulae enclosed in $ or $$).
     * @return String containing formulae converted to MathML that replaced
     * original TeX forms. Non math tokens are connected at the end.
     */
    public String convertTexLatexML(String query) {

        logger.fatal(query);
        query = query.replaceAll("\\$\\$", "\\$");
        if (query.matches(".*\\$.+\\$.*")) {
            try {
                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(LATEX_TO_XHTML_CONVERSION_WS_URL);

                // Request parameters and other properties.
                List<NameValuePair> params = new ArrayList<>(1);
                params.add(new BasicNameValuePair("code", query));
                httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

                // Execute and get the response.
                HttpResponse response = httpclient.execute(httppost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        try (InputStream responseContents = resEntity.getContent()) {
                            DocumentBuilder dBuilder = cz.muni.fi.mias.MIaSMathUtils.prepareDocumentBuilder();
                            org.w3c.dom.Document doc = dBuilder.parse(responseContents);
                            // todo: this should be inside xslt template
                            removeSubset(doc, Node.ELEMENT_NODE, "annotation-xml");
                            removeSubset(doc, Node.ELEMENT_NODE, "annotation");

                            doc.normalize();
                            NodeList ps = doc.getElementsByTagName("p");
                            StringBuilder sb = new StringBuilder();
                            for (int k = 0; k < ps.getLength(); k++) {
                                Node p = ps.item(k);
                                NodeList pContents = p.getChildNodes();
                                for (int j = 0; j < pContents.getLength(); j++) {
                                    Node pContent = pContents.item(j);
                                    if (pContent instanceof Text) {
                                        sb.append(pContent.getNodeValue());
                                    } else {
                                        TransformerFactory transFactory = TransformerFactory.newInstance();
                                        Source xslt = new StreamSource(new StringReader(TEMPLATE));

                                        Transformer transformer = transFactory.newTransformer(xslt);


                                        StringWriter buffer = new StringWriter();
                                        transformer.setOutputProperty(OutputKeys.INDENT, "no");
                                        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                                        transformer.transform(new DOMSource(pContent), new StreamResult(buffer));
                                        sb.append(buffer.toString());
                                    }
                                }
                            }
                            // converts symbols into unicode ones 
                            return org.apache.commons.lang3.StringEscapeUtils.escapeJava(sb.toString());
                        }
                    }
                }

            } catch (TransformerException | SAXException | ParserConfigurationException | IOException ex) {
                logger.error(ex);
            }
        }
        return query;
    }


    /**
     * really ugly nonworking recursive subtree deletor
     * @param node
     * @param type
     * @param name
     */
    private void removeSubset(Node node, short type, String name) {
        if (node.getNodeType() == type && (name == null || node.getNodeName().equals(name))) {
            node.getParentNode().removeChild(node);
        } else {
            NodeList list = node.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                removeSubset(list.item(i), type, name);
            }
        }
    }

    private static final String TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
            "  <xsl:template match=\"/math/semantics\">" +
            "    <xsl:copy>" +
            "        <xsl:apply-templates select=\"node()\" />" +
            "    </xsl:copy>" +
            "  </xsl:template>" +
            "  <xsl:template match=\"annotation-xml\" />" +
            "  <xsl:template match=\"*\">" +
            "    <xsl:element name=\"{local-name()}\">" +
            "        <xsl:apply-templates select=\"node()\" />" +
            "    </xsl:element>" +
            "  </xsl:template>" +
            "  <xsl:template match=\"annotation-xml\" />" +
            "  <xsl:template match=\"@*\">" +
            "    <xsl:copy>" +
            "      <xsl:apply-templates select=\"node()\" />" +
            "    </xsl:copy>" +
            "  </xsl:template>" +
            "</xsl:stylesheet>";


}
