/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.math;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class MathServiceImpl implements MathService
{

    private String[] fileNames;
    @Autowired
    private MetadatumFactory metadatumFactory;
//    private DocumentBuilderFactory factory;
//    private DocumentBuilder builder;
    private Transformer transformer;
//    private XPathFactory xFactory;
//    private XPath xpath;
    private PrintStream original = System.err;

    public MathServiceImpl() throws TransformerConfigurationException, ParserConfigurationException
    {
        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
//        factory = DocumentBuilderFactory.newInstance();
//        builder = factory.newDocumentBuilder();
//        builder.setEntityResolver(new EntityResolver()
//        {
//            @Override
//            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
//            {
//               if(systemId.contains("unknown.dtd"))
//               {
//                   return new InputSource(new StringReader(""));
//               }
//               else
//               {
//                   return null;
//               }
//            }
//        });
//        xFactory = XPathFactory.newInstance();
//        xpath = xFactory.newXPath();
    }

    private static final Logger logger = Logger.getLogger(MathServiceImpl.class);

    @Override
    public List<Metadatum> loadMathFormulas(final ObjectWrapper objectWrapper) throws IOException
    {  
        List<Metadatum> result = new ArrayList<>();
//        if (acceptablePaths.contains(objectWrapper.getPath()))
//        {

            try
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                builder.setEntityResolver(new EntityResolver()
                {
                    @Override
                    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
                    {
                        if (systemId.contains("unknown.dtd"))
                        {
                            return new InputSource(new StringReader(""));
                        }
                        else
                        {
                            return null;
                        }
                    }
                });
                
//                Console c = System.console();
//                c.
                
//                Console c = System.console();
//                c.
//                System.out.println(objectWrapper.getPath());
//                System.out.println(System.console());

                System.setErr(new PrintStream(System.err){
                    @Override
                    public void println(String s){
                        logger.fatal(s+" @"+objectWrapper.getPath());
                        super.println(s);
                    }                                        
                });
                Document doc = builder.parse(Files.newInputStream(objectWrapper.getPath().resolve("infty.xml"), StandardOpenOption.READ));
//                System.out.println("#"+System.console());
                XPathFactory xFactory = XPathFactory.newInstance();
                XPath xpath = xFactory.newXPath();
//                System.out.println("$"+System.console());
                XPathExpression expression = xpath.compile("//formula/math");
//                System.out.println("%"+System.console());
                
                NodeList nl = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);

                for (int i = 0; i < nl.getLength(); i++)
                {
                    String math = nodeToString(nl.item(i));
                    logger.debug(math);
                    result.add(metadatumFactory.createMetadatum("dmlcz", "math", null, null, math));
                }
            }
            catch (XPathExpressionException | TransformerException | SAXException ex)
            {
                logger.error(ex);
            }
            catch (ParserConfigurationException ex)
            {
                java.util.logging.Logger.getLogger(MathServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.setErr(original);
//        }

        return result;
    }

    private String nodeToString(Node node) throws TransformerException
    {
        StringWriter sw = new StringWriter();

        transformer.transform(new DOMSource(node), new StreamResult(sw));

        return sw.toString();
    }
}
