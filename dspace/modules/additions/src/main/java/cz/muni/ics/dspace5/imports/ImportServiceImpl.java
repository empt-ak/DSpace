/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.CommandLineService;
import cz.muni.ics.dspace5.core.ImportService;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.ObjectWrapperResolverFactory;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.InputArguments;
import cz.muni.ics.dspace5.impl.ObjectWrapperFactory;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "importService")
public class ImportServiceImpl implements ImportService
{

    private static final Logger logger = Logger.getLogger(ImportServiceImpl.class);    
    //awires
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;
    @Autowired
    private CommandLineService commandLineService;    
    @Autowired
    private ImportCommunity importCommunity;
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private ObjectWrapperResolverFactory objectWrapperResolverFactory;

    @Override
    public void execute(String[] args)
    {
        boolean error = false;

        try
        {
            commandLineService.getCommandLine("import").process(args);
        }
        catch (ParseException pe)
        {
            error = true;
        }

        if (!error)
        {
            try
            {
                contextWrapper.setContext(new Context());
                contextWrapper.getContext().setCurrentUser(findEPerson(inputArguments.getValue("user")));
            }
            catch (SQLException ex)
            {
                logger.info(ex, ex.getCause());
            }
            contextWrapper.getContext().turnOffAuthorisationSystem();
            
            ObjectWrapper importTarget = objectWrapperFactory.createObjectWrapper();
            importTarget.setPath(inputArguments.getTypedValue("path", Path.class));
            
            ObjectWrapper realImport = objectWrapperResolverFactory
                    .provideObjectWrapperResolver(importTarget.getPath())
                    .resolveObjectWrapper(importTarget, true);
            
//            try
//            {
                importCommunity.importToDspace(realImport, new ArrayList<ObjectWrapper>(), new HashMap<String, Object>());
//            }
//            catch(Exception e)
//            {
//                logger.fatal(e,e.getCause());
                contextWrapper.getContext().abort();
//                
//            }
//            finally
//            {
                try
                {
                    contextWrapper.getContext().complete();
                }
                catch (SQLException ex)
                {
                    logger.error(ex, ex.getCause());
                }
//            }
            
            
            contextWrapper.getContext().restoreAuthSystemState();
            contextWrapper.setContext(null);
        }
    }
    
    /**
     * Method finds {@code EPerson} inside system based on given input value. If null, or empty string is passed then first user found is set as current operating DSpace user. Otherwise EPerson (if found) is returned by given input.
     * @param email of person to be found
     * @return EPerson with given email, or first one ever created if no input is specified
     * @throws IllegalStateException if {@code Context} has not been initialized yet
     * @throws IllegalArgumentException if EPerson with given email does not exist.
     */
    private EPerson findEPerson(String email) throws IllegalStateException, IllegalArgumentException
    {
        if(contextWrapper.getContext() == null)
        {
            throw new IllegalStateException("Context not yet initialized.");
        }
        
        EPerson[] persons = new EPerson[0];
        
        if(email == null || email.isEmpty())
        {
            persons = new EPerson[0];
            try
            {
                persons = EPerson.findAll(contextWrapper.getContext(), EPerson.ID);
            }
            catch(SQLException ex)
            {
                logger.error(ex,ex.getCause());
            }
            
            if(persons == null || persons.length == 0)
            {
                throw new IllegalStateException("There are no users created yet. Run 'dspace create-administrator' from /bin folder first.");
            }
            else
            {
                return persons[0];
            }
        }
        else
        {
            EPerson person = null;
            try
            {
                person = EPerson.findByEmail(contextWrapper.getContext(), email);
            }
            catch(SQLException | AuthorizeException ex)
            {
                logger.error(ex,ex.getCause());
            }
            
            if(person == null)
            {
                throw new IllegalArgumentException("There is no such user with given email. ["+email+"]");
            }
            else
            {
                return person;
            }
        }
    }
}
