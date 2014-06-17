package com.matson.arch.poc.alfresco.cmis.opencmis;


import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Unit test for simple App.
 * refer = http://chemistry.apache.org/java/developing/guide.html
 * Simple APIs to
 * Create Folder
 * Create Document
 * Read contents of a Document Object
 * Update a document
 * Delete a document
 * Navigate through folder tree
 * Display properties of an object (custom and standard)
 * CMIS Query
 */
public class GatesIntegrationTest {

//    public static final String ALFRESCO_WEB_SERVER = "http://svc.edm.matson.com";
//    public static final String ALFRESCO_WEB_SERVER = "http://10.3.4.84:8080"; //PROD
    public static final String ALFRESCO_WEB_SERVER = "http://10.3.5.130:8080"; //PREPROD
    public static final String HTTP_ALFRESCO_ATOMPUB_URL = ALFRESCO_WEB_SERVER + "/alfresco/cmisatom";

    private static SessionFactory sessionFactory;
    private static Session session;
    private static Map<String,String> sessionFactoryParameters;
    static Logger log = LoggerFactory.getLogger(GatesIntegrationTest.class);

    @BeforeClass
    public static void createSession(){
        sessionFactory = SessionFactoryImpl.newInstance();
        sessionFactoryParameters = new HashMap<String, String>();
        //credentials
        sessionFactoryParameters.put(SessionParameter.USER, "gatesApp");
        sessionFactoryParameters.put(SessionParameter.PASSWORD, "gatesApp");
        //Connection setting
        sessionFactoryParameters.put(SessionParameter.ATOMPUB_URL, HTTP_ALFRESCO_ATOMPUB_URL);
        sessionFactoryParameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

        // Set the alfresco object factory
        //This allows for being able to use Aspects
        sessionFactoryParameters.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

//        What is the repository id?
//        parameter.put(SessionParameter.REPOSITORY_ID, "A1");
//        Session session = sessionFactory.createSession(parameter);
//        List of repositories - for alfresco impl there is only one
        List<Repository> repositories = new ArrayList<Repository>();
        repositories = sessionFactory.getRepositories(sessionFactoryParameters);
        for (Repository r : repositories) {
            log.info("Found repository: {}", r.getName());
            log.info("RepoId {}", r.getId());
        }

//      Connect to the first repository
        Repository repository = repositories.get(0);
        sessionFactoryParameters.put(SessionParameter.REPOSITORY_ID, repository.getId());
        //OpenCMIS is thread-safe. The Session object can and should be reused across thread boundaries.
        session = sessionFactory.createSession(sessionFactoryParameters);
        log.info("Got a connection to repository:{}, with id:{}", repository.getName(), repository.getId());
    }

    @After
    public void destroySession(){
/*
        if(null != session)
            session.
*/
    }

    @Test
    public void sanityTestAlfrescoConnection() throws IOException {
//       list items under root folder
        Folder root = session.getRootFolder();
        ItemIterable<CmisObject> children = root.getChildren();
        log.info("Found the following objects in the root folder:-");
        for (CmisObject o : children) {
            log.info(o.getName() + " which is of type " + o.getType().getDisplayName());
        }

    }



    @Test
    public void test_cmisQuery(){
//      CMIS Query, find all rpt:report types under accouting folder
        //Refer http://wiki.alfresco.com/wiki/CMIS_Query_Language
/*
        String query = "SELECT * FROM cmis:document where" +
                " cmis:creationDate > TIMESTAMP '2010-04-01T12:15:00.000Z' " +
                " and IN_FOLDER('workspace://SpacesStore/7363f7ee-8b23-400e-ab8e-75955ec80bc6') " ;
*/
        //Issue running timestamp clause with in_folder or other clauses need lucene configuration
        //https://issues.alfresco.com/jira/browse/ALF-5378
        String query = "SELECT * FROM gat:gqtCustomer  WHERE   ( gat:quoteIdentifier = '9346758_000' )" ;

//        ItemIterable<QueryResult> q = getSession().query(query, false);
        ItemIterable<QueryResult> q = session.query(query, false);

        log.info(q.getPageNumItems() + " results on this page from query " + query);

        int i = 1;
        for (QueryResult qr : q) {
            log.info("--------------------------------------------\n" + i + " , "
                    + qr.getPropertyByQueryName("cmis:objectTypeId").getFirstValue() + " , "
                    + qr.getPropertyByQueryName("cmis:name").getFirstValue() + " , "
                    + qr.getPropertyByQueryName("gat:quoteIdentifier").getFirstValue() + " , "
//                    + ((Calendar)qr.getPropertyByQueryName("crp:generationDateTime").getFirstValue()).getTime() + " , "
//                    + qr.getPropertyByQueryName("cmis:objectId").getFirstValue() + " , "
//                    + qr.getPropertyByQueryName("cmis:contentStreamFileName").getFirstValue() + " , "
                    + qr.getPropertyByQueryName("cmis:contentStreamMimeType").getFirstValue()) ;
//                    + qr.getPropertyByQueryName("cmis:contentStreamLength").getFirstValue());
            i++;
        }
    }


    @Test
    public void testGetDocumentListRepeatedly(){
        for(int i=0; i<10000; i++){
            log.info("Query count: " + i);
            try{
                test_cmisQuery();
            }catch (Exception e){
                log.error("Error running test cmis query", e);
            }
        }
    }

    private Session getSession() {
        log.debug("Get Alfresco Session");
        if(sessionFactory == null) {
            try {
                log.info("Get New Alfresco Session");
                sessionFactory = SessionFactoryImpl.newInstance();
                sessionFactoryParameters = new HashMap<String, String>();
                //credentials
                sessionFactoryParameters.put(SessionParameter.USER, "gatesApp");
                sessionFactoryParameters.put(SessionParameter.PASSWORD, "gatesApp");
                //Connection setting
                sessionFactoryParameters.put(SessionParameter.ATOMPUB_URL, HTTP_ALFRESCO_ATOMPUB_URL);
                sessionFactoryParameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

                // Set the alfresco object factory
                //This allows for being able to use Aspects
                sessionFactoryParameters.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
//			    What is the repository id?
//		        parameter.put(SessionParameter.REPOSITORY_ID, "A1");
//		        Session session = sessionFactory.createSession(parameter);
//		        List of repositories - for alfresco impl there is only one
                List<Repository> repositories = new ArrayList<Repository>();
                repositories = sessionFactory.getRepositories(sessionFactoryParameters);
                for (Repository r : repositories) {
                    log.info("Found repository: {}", r.getName());
                    log.info("RepoId {}", r.getId());
                }

//		      	Connect to the first repository
                Repository repository = repositories.get(0);
                sessionFactoryParameters.put(SessionParameter.REPOSITORY_ID, repository.getId());
                log.info("Got a connection to repository:{}, with id:{}", repository.getName(), repository.getId());
            } catch (RuntimeException e) {
                sessionFactory = null;
                throw e;
            } catch (Error e) {
                sessionFactory = null;
                throw e;
            }
        }

        //OpenCMIS is thread-safe. The Session object can and should be reused across thread boundaries.
        Session session = sessionFactory.createSession(sessionFactoryParameters);
        return session;

    }

    @Test
    public void test_upload_document(){

        long currentTime = System.currentTimeMillis();
        String filename = "amit_doc_test_" + currentTime;
        byte[] body = ("Alfresco document body " + currentTime).getBytes();
        String mimetype = "text/plain";
        String directory = "";
        log.info("Uploading document to alfresco: {}", filename);

        try {
            Session session = getSession();
            InputStream is = new ByteArrayInputStream(body);

            ContentStream contentStream = session.getObjectFactory().createContentStream(filename, 0, mimetype, is);

            //if(!properties.containsKey(PropertyIds.OBJECT_TYPE_ID)) properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(PropertyIds.NAME, filename);
            properties.put(PropertyIds.CONTENT_STREAM_FILE_NAME, filename);

            CmisObject dropBoxObject = session.getObjectByPath(directory);

            //the path should resolve to a directory not another type e.g. document
            Folder dropBoxFolder = (Folder) dropBoxObject;
            log.info(directory +" folder id: " + dropBoxFolder.getId());
            log.info("Copies set in alfresco metadata :"+properties.get("gat:copies"));
            Document document = dropBoxFolder.createDocument(properties, contentStream, VersioningState.MAJOR);
            log.info("(saveFile)Report was UPloaded, filename:{}, stream id: {}, id:{}", new Object[]{filename, document.getContentStreamId(), document.getId()});

            String id = document.getId();
            is.close();
            log.info("Document created with id: {}", id);


        } catch (Exception e) {
            log.error("ERROR SAVING TO ALFRESCO: "+e);
            log.warn("Could not save document "+filename+" queing it instead");
        }
    }

}
