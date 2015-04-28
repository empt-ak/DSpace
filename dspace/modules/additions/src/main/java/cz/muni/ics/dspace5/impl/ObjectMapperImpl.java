/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.api.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ObjectMapperImpl implements ObjectMapper
{
    private static final Logger logger = Logger.getLogger(ObjectMapper.class);
    @Autowired 
    private Unmarshaller unmarshaller;
    
    @Override
    public <T> T convertPathToObject(Path p, String fileName) throws IllegalArgumentException, FileNotFoundException
    {
        Path workingPath = p.resolve(fileName);        
        
        T t = null;
        
        try(InputStream is = Files.newInputStream(workingPath))
        {
            logger.info("Converting path : "+p);
            t = (T) unmarshaller.unmarshal(is);
            logger.info("Success");
        }
        catch(IOException | JAXBException ex)
        {
            logger.error(ex, ex.getCause());
        }
        
        return t;
    }
    
    private void init()
    {
        this.unmarshaller.setAdapter(new StringNormalizerAdapter());
    }
    
    private class StringNormalizerAdapter extends XmlAdapter<String, String>
    {
        @Override
        public String unmarshal(String v) throws Exception
        {
            return v.trim();
        }

        @Override
        public String marshal(String v) throws Exception
        {
            return v.trim();
        }        
    }
}
