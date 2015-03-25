/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class XPathWrapper
{
    private static final Logger logger = Logger.getLogger(XPathWrapper.class);
    @Autowired
    private DocumentBuilder documentBuilder;
    @Autowired
    private XPath xpath;
    
    
    public NodeList evaluateXPathExpression(Path pathToXml, String xPathExpression) throws SAXException, XPathExpressionException
    {
        try(InputStream is = Files.newInputStream(pathToXml, StandardOpenOption.READ))
        {
            return (NodeList) xpath.compile(xPathExpression)
                    .evaluate(documentBuilder.parse(is),XPathConstants.NODESET);
        }
        catch(IOException ex)
        {
            logger.error(ex,ex.getCause());
        }
        
        return null;
    }
}
