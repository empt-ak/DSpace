/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io.related;

import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelatedServiceImpl implements RelatedService
{
    private static final Logger logger = Logger.getLogger(RelatedServiceImpl.class);
    private final DecimalFormat df = new DecimalFormat("0.00");
    private Map<String, String> similarMap = new HashMap<>();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private HandleService handleService;
    @Autowired
    private MetadatumFactory metadatumFactory;

    private static final String DELIMITER = "☎"; // ☎

    @PostConstruct
    private void init()
    {
        similarMap.put("similar_lsi.xml", "lsi");
        similarMap.put("similar_rp.xml", "rp");
        similarMap.put("similar_tfidf.xml", "tfidf");
    }

    @Override
    public List<Metadatum> processSimilarity(ObjectWrapper objectWrapper)
    {
        List<Metadatum> resultList = new ArrayList<>();
        for (String allowedFile : similarMap.keySet())
        {
            if (Files.exists(objectWrapper.getPath().resolve(allowedFile)))
            {
                List<Article> relatedArticles = null;
                try
                {
                    relatedArticles = ((Related) objectMapper.convertPathToObject(objectWrapper.getPath(), allowedFile)).getArticle();
                }
                catch (IllegalArgumentException | FileNotFoundException ex)
                {
                    logger.error(ex);
                }

                if (relatedArticles != null && !relatedArticles.isEmpty())
                {
                    for (Article a : relatedArticles)
                    {
                        String similar = articleToString(a, objectWrapper);
                        if (similar != null)
                        {
                            resultList.add(metadatumFactory.createMetadatum("dmlcz", "related", similarMap.get(allowedFile), null, similar));
                        }
                        else
                        {
                            logger.warn("For file " + allowedFile + " # " + objectWrapper.getPath() + " is an unimported article.");
                        }
                    }
                }

            }
        }

        return resultList;
    }

    private String articleToString(Article article, ObjectWrapper objectWrapper)
    {
        StringBuilder sb = new StringBuilder(df.format(article.getWeight() * 100));
        sb.append(DELIMITER);

        Link link = article.getLinks().getLink();
        switch (link.getSource())
        {
            case "dmlcz":
            {
                sb.append("dmlcz:");
                Path path = dSpaceTools.getMeditorRootPath().resolve(link.getPath());
                String handle = null;
                try
                {
                    handle = handleService.getHandleForPath(path, false);
                }
                catch (RuntimeException ex)
                {
                    logger.error("Handle not found. skipping.");
                    return null;
                }
                sb.append(handle);
                sb.append(DELIMITER);
            }
            break;
            case "numdam":
            {
                sb.append("numdam:");
                sb.append(link.getId());
                sb.append(DELIMITER);
            }
            break;
            default:
                throw new IllegalArgumentException("Unsupported type of link " + link.getSource() + " # " + article.getTitle() + " # " + objectWrapper.getPath());
        }

        sb.append(article.getTitle());

        return sb.toString();
    }

}
