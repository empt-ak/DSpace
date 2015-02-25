/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

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
public class FolderProvider
{
    private static final Logger logger = Logger.getLogger(FolderProvider.class);
    @Autowired
    private DirectoryStream.Filter<Path> filter;
    
    public List<Path> getFoldersFromPath(Path path)
    {
        List<Path> resultList = new ArrayList<>();
        
        try(DirectoryStream<Path> ds = Files.newDirectoryStream(path, filter))
        {
            for(Path p : ds)
            {
                resultList.add(p);
            }
        }
        catch(IOException ex)
        {
            logger.error(ex, ex.getCause());
        }
        
        return resultList;
    }
}
