/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class VolumeFolderProvider
{

    private static final Logger logger = Logger.getLogger(VolumeFolderProvider.class);
    
    @Autowired
    private DSpaceTools dSpaceTools;

    /**
     * Method returns list of paths of issues that belong to the same volume.
     * @param issuePath 
     * @return 
     */
    public List<Path> getVolumesFromIssue(Path issuePath)
    {
        return getVolumes(issuePath.getParent(), dSpaceTools.getVolumeNumber(issuePath));
    }

    public List<Path> getVolumes(Path rootPath, final String issueNumber)
    {
        List<Path> resultList = new ArrayList<>();

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(rootPath, new DirectoryStream.Filter<Path>()
        {
            @Override
            public boolean accept(Path entry) throws IOException
            {
                if(Files.isDirectory(entry))
                {                    
                    return entry.getFileName().toString().startsWith(issueNumber);
                }
                else
                {
                    return false;
                }   
            }
        }))
        {
            for(Path p : ds)
            {
                resultList.add(p);
            }
        }
        catch (IOException ex)
        {
            logger.info(ex);
        }
        
        return resultList;
    }
}
