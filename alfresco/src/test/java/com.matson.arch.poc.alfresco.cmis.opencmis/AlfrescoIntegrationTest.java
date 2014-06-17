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
public class AlfrescoIntegrationTest{

    public static final String ALFRESCO_WEB_SERVER = "http://alfresco.matson.com";
    private static String serviceUrl = "http://10.3.6.181:8080/alfresco/cmis";   //DEV
//    private static String serviceUrl = "http://10.3.5.130:8080/alfresco/cmis"; //PRE
    public static final String QUOTE_ID = "1690739_000";//DEV
//    public static final String QUOTE_ID = "7815936_000";//PRE
//    public static final String QUOTE_ID = "9346758_000";//PROD

    public static final String HTTP_ALFRESCO_ATOMPUB_URL = ALFRESCO_WEB_SERVER + "/alfresco/cmisatom";
    public static final String ALFRESCO_DOC_DETAILS_PATH_PREFIX = ALFRESCO_WEB_SERVER + "/share/page/document-details?nodeRef=";
    static Logger log = LoggerFactory.getLogger(AlfrescoIntegrationTest.class);
    
    private static SessionFactory sessionFactory;
    private static Session session;
    private static Map<String,String> sessionFactoryParameters;

    @BeforeClass
    public static void createSession(){
        sessionFactory = SessionFactoryImpl.newInstance();
        sessionFactoryParameters = new HashMap<String, String>();
        //credentials
        sessionFactoryParameters.put(SessionParameter.USER, "akapoor");
        sessionFactoryParameters.put(SessionParameter.PASSWORD, "akapoor");

        //Connection setting - Atompub binding
/*
        sessionFactoryParameters.put(SessionParameter.ATOMPUB_URL, HTTP_ALFRESCO_ATOMPUB_URL);
        sessionFactoryParameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
*/

        //Connection setting - Web service binding
        sessionFactoryParameters.put(SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value()); // Uncomment for Web Services binding
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_ACL_SERVICE, getServiceUrl() + "/ACLService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_DISCOVERY_SERVICE, getServiceUrl() + "/DiscoveryService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_MULTIFILING_SERVICE, getServiceUrl() + "/MultiFilingService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_NAVIGATION_SERVICE, getServiceUrl() + "/NavigationService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_OBJECT_SERVICE, getServiceUrl() + "/ObjectService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_POLICY_SERVICE, getServiceUrl() + "/PolicyService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE, getServiceUrl() + "/RelationshipService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_REPOSITORY_SERVICE, getServiceUrl() + "/RepositoryService");
        sessionFactoryParameters.put(SessionParameter.WEBSERVICES_VERSIONING_SERVICE, getServiceUrl() + "/VersioningService");


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

    private static String getServiceUrl() {
        return serviceUrl;
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


/*
    @Test
    public void test_showGatesReports() throws IOException {
        //Get the Gates/reports/accounting folder
        String folderPath = "/Gates/Reports/Accounting";
        CmisObject folderObject = session.getObjectByPath(folderPath);
        log.info(folderObject.getName() + " which is of type " + folderObject.getType().getDisplayName());
        Folder folder = (Folder) folderObject;
        log.info("{} folder id: {}", folderPath, folder.getId());

        //List all items under the accounting folder with their properties
        ItemIterable<CmisObject> accountsReports = folder.getChildren();
        log.info("Found the following objects in the {} folder:-", folderPath);
        for (CmisObject o : accountsReports) {
            Document document = (Document) o;
            log.info(o.getName() + " which is of type " + o.getType().getDisplayName());
            List<Property<?>> properties = o.getProperties();
//            log.info("properties" + properties);
            for(Property prop : properties){
                log.info("Property " + prop.getId() + ":" + prop.getValue());
            }

            log.info("crp:reportName: " + o.getPropertyValue("crp:reportName: "));
            log.info("crp:generationDateTime" + ((Calendar)o.getPropertyValue("crp:generationDateTime")).getTime());
            if("text/plain".equalsIgnoreCase((String)o.getPropertyValue("cmis:contentStreamMimeType"))){
                log.info("REPORT CONTENTS:\n" + getContentAsString(document.getContentStream()));
            }
        }
    }
*/

    @Test
    public void test_showFolderContentsRecursively() throws IOException {
        //Get the Gates/reports/accounting folder
        String folderPath = "/Sites/swsdp";
        showFolderContentsRecursively(folderPath);
    }

    private void showFolderContentsRecursively(String folderPath){

        CmisObject folderObject = session.getObjectByPath(folderPath);
        log.info(folderObject.getName() + " which is of type " + folderObject.getType().getDisplayName());
        Folder folder = (Folder) folderObject;
        log.info("{} folder id: {}", folderPath, folder.getId());

        //List all items under the accounting folder with their properties
        ItemIterable<CmisObject> children = folder.getChildren();
        log.info("Found the following objects in the {} folder:-", folderPath);
        for (CmisObject o : children) {
            if(o instanceof Document){

                Document document = (Document) o;
                log.info(o.getName() + "which is of type " + o.getType().getDisplayName());
                List<Property<?>> properties = o.getProperties();
    //            log.info("properties" + properties);
                for(Property prop : properties){
                    log.info("Property " + prop.getId() + ":" + prop.getValue());
                }
/*
                if("text/plain".equalsIgnoreCase((String)o.getPropertyValue("cmis:contentStreamMimeType"))){
                    log.info("REPORT CONTENTS:\n" + getContentAsString(document.getContentStream()));
                }
*/
            }
            if(o instanceof Folder){
                Folder f = (Folder) o;
                showFolderContentsRecursively(f.getPath());
            }

        }

    }

    /**
     * Helper method to get the contents of a stream
     *
     * @param inputStream
     * @return
     * @throws java.io.IOException
     */
    private static byte[] getContentAsBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            baos.write(data, 0, nRead);
        }

        baos.flush();
        return baos.toByteArray();
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
//        String query = "SELECT * FROM gat:quote where" +
//                " IN_FOLDER('workspace://SpacesStore/4fea0095-0ba4-4636-bcc4-e8401556cbfd') " ;
        String query = "SELECT * FROM gat:gqtCustomer  WHERE   ( gat:quoteIdentifier = '"+ QUOTE_ID+"' )" ;

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
    public void test_uploadTextContent() throws IOException {
        String dateStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String reportName = "AvailableContainers";
        final String textFileName = "Operations_"+reportName+"_Report_"+ dateStamp +".txt";
        log.info("creating a simple text document, {}", textFileName);
        String mimetype = "text/plain";
        String content = "Report content uploaded through CMIS client. Dated: " + new Date();
        String filename = textFileName;

        byte[] buf = content.getBytes("UTF-8");
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        String reportDropbox = "/Sites/swsdp/documentLibrary/akapoor";
        String documentId = uploadContentToRepository(reportDropbox, bais, mimetype, textFileName, null);

        log.info("Document Id: {}" + documentId);
        //Should be of a document object (not folder etc.)
        Document document = (Document) session.getObject(documentId);
        log.info("Document Mime Type: {}", document.getProperty("cmis:contentStreamMimeType").getValueAsString());

        InputStream contentStream = document.getContentStream().getStream();
        log.info("Content read back: {}", new String(getContentAsBytes(contentStream)));
        contentStream.close();

    }

    public byte[] readBytesFromFile(String directory, String fileName) throws IOException {
        File file = new File(directory, fileName);
        log.info("File {} Exists? {}", fileName, file.exists());
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = getContentAsBytes(fis);
        fis.close();
        return bytes;
    }

    @Test
    public void test_uploadBinaryContent() throws IOException, FileNotFoundException {
        //Upload new content to Dropbox
        // Create a simple text document in the new folder
        // First, create the content stream
        String localFileDirectory = "src/test/reports/";
        String reportName = "AvailableContainers";
        String fileExtension = ".pdf";
        String localFileName = reportName + fileExtension;
        String mimetype = "application/pdf";

        String dateStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String reportCategory = "Operations";
        final String pdfFileName = reportCategory + "_" +reportName+"_Report_"+ dateStamp +".pdf";

        String reportDropbox = "/Sites/swsdp/documentLibrary/akapoor";
        InputStream is = new ByteArrayInputStream(readBytesFromFile(localFileDirectory, localFileName));
        String documentId = uploadContentToRepository(reportDropbox, is, mimetype, pdfFileName, null);

        log.info("Document Id: {}" + documentId);
        //Should be of a document object (not folder etc.)
        Document document = (Document) session.getObject(documentId);
        log.info("Content mimetype: {}", document.getPropertyValue("cmis:"));
        InputStream contentStream = document.getContentStream().getStream();
        log.info("Content read back: {}", getContentAsBytes(contentStream));
        contentStream.close();
    }

    //Returns the document id of the created content stream.
    public String uploadContentToRepository(String uploadDirectory, InputStream contentInputStream
            , String contentMimeType, String contentFileName, Map<String, Object> properties){
        ContentStream contentStream = session.getObjectFactory().createContentStream(contentFileName, 0, contentMimeType, contentInputStream);

        Map<String, Object> baseProperties = new HashMap<String, Object>();
        baseProperties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        baseProperties.put(PropertyIds.NAME, contentFileName);
        baseProperties.put(PropertyIds.CONTENT_STREAM_FILE_NAME, contentFileName);
        if(null != properties)
            baseProperties.putAll(properties);

        CmisObject dropBoxObject = session.getObjectByPath(uploadDirectory);
        //the path should resolve to a directory not another type e.g. document
        Folder dropBoxFolder = (Folder) dropBoxObject;
        log.info(uploadDirectory +" folder id: " + dropBoxFolder.getId());
        Document document = dropBoxFolder.createDocument(baseProperties, contentStream, VersioningState.MAJOR);
        log.info("Report UPloaded, fileName:{}, stream id: {}, id:{}", new Object[]{contentFileName, document.getContentStreamId(), document.getId()});
        return document.getId();
    }

    @Test
    public void test_uploadBinaryContentWithCustomTypeAndAspect() throws IOException, FileNotFoundException {
        //Upload new content to Dropbox
        // Create a simple text document in the new folder
        // First, create the content stream
        String localFileDirectory = "src/test/reports/";
        String localFileName = "quote";
        String fileExtension = ".pdf";
        String mimetype = "application/pdf";

        String dateStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        final String uploadedFileName = localFileName+"_"+ dateStamp + fileExtension;

        String quotesDirectory = "/Sites/swsdp/documentLibrary/akapoor";
        InputStream is = new ByteArrayInputStream(readBytesFromFile(localFileDirectory, localFileName+fileExtension));
        //Add custom Type gat:Quote and Add Aspect gat:customer
        //The type definitions need to be uploaded to the Alfresco server before doing this
        Map <String, Object> properties = new HashMap<String, Object>();
//        properties.put(PropertyIds.OBJECT_TYPE_ID,"D:gat:quote,P:gat:customer");
        properties.put(PropertyIds.OBJECT_TYPE_ID,"D:gat:gqtCustomer");
        //gat:quoteNumber attribute of the gat:quote type
        properties.put("gat:quoteIdentifier", "9346758_000");
//        properties.put("gat:quoteNumber", 1817500);
        //gat:customerName attribute of the aspect gat:customer
//        properties.put("gat:customerName", "DHX-DEPENDABLE");

        String documentId = uploadContentToRepository(quotesDirectory, is, mimetype, uploadedFileName, properties);

        log.info("Document Id: {}" + documentId);
        //Should be of a document object (not folder etc.)
        Document document = (Document) session.getObject(documentId);
        log.info("Content mimetype: {}", document.getPropertyValue("cmis:"));
        InputStream contentStream = document.getContentStream().getStream();
        log.info("Content read back: {} bytes", getContentAsBytes(contentStream).length);
        contentStream.close();
    }

    @Test
    public void test_uploadANewVersionOfDocument() throws IOException, FileNotFoundException {
        //Upload some file for the first time and note its document ID.
        String quoteText = "Your quote is ... Dated: ";
        String mimetype = "text/plain";

/*
        String fileName = "quote_100_versioned.txt";
        log.info("creating a simple text document, {}", fileName);
        String contentStr = quoteText + new Date();
        ByteArrayInputStream bais = new ByteArrayInputStream(contentStr.getBytes("UTF-8"));
        String reportDropbox = "/Sites/gates/documentLibrary/Quotes";

        Map <String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID,"D:gat:quote,P:gat:customer");
        properties.put("gat:quoteNumber", 100);
        properties.put("gat:customerName", "WAL-MART");
        String documentId = uploadContentToRepository(reportDropbox, bais, mimetype, fileName, properties);

        log.info("DocumentId: {}", documentId);
*/


        String documentId = "workspace://SpacesStore/acbabc19-b7a2-4dc3-b188-c6d307bd17d8";
        log.info("Getting latest version of documentId: {}", documentId);
        Document document = (Document) session.getObject(documentId);
        Document docLatestVersion = document.getObjectOfLatestVersion(false);
        log.info("Checking out documentId: ", docLatestVersion.getId());
        ObjectId checkedOutId = docLatestVersion.checkOut();
        log.info("Original documentId: {}, Checked out documentId: {}",docLatestVersion.getId(), checkedOutId.getId());
        Document privateWorkingCopy = (Document) session.getObject(checkedOutId);
        ByteArrayInputStream bis = new ByteArrayInputStream((quoteText + new Date()).getBytes());
        ContentStream contentStream = session.getObjectFactory().createContentStream(privateWorkingCopy.getContentStreamFileName()
                , 0, mimetype, bis);
        ObjectId checkedInId = privateWorkingCopy.checkIn(false, null, contentStream, "updated: " + new Date());
        log.info("Checked in documentId: {}", checkedInId);
    }

}
