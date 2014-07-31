package com.matson.arch.poc.alfresco.cmis.opencmis;


import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionImpl;
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

//    public static final String ALFRESCO_WEB_SERVER = "http://10.3.6.181:8080"; //DEV
    public static final String ALFRESCO_WEB_SERVER = "http://10.3.5.130:8080"; //PRE
    private static String serviceUrl = ALFRESCO_WEB_SERVER + "/alfresco/cmis";   //DEV
//    private static String serviceUrl = "http://10.3.5.130:8080/alfresco/cmis"; //PRE
//    public static final String QUOTE_ID = "1690739_000";//DEV
    public static final String QUOTE_ID = "7815936_000";//PRE
//    public static final String QUOTE_ID = "9346758_000";//PROD

    public static final String HTTP_ALFRESCO_ATOMPUB_URL = ALFRESCO_WEB_SERVER + "/alfresco/cmisatom";
    public static final String ALFRESCO_CMIS_10_URL = ALFRESCO_WEB_SERVER + "/alfresco/api/-default-/public/cmis/versions/1.0/atom";
    public static final String ALFRESCO_CMIS_11_URL= ALFRESCO_WEB_SERVER + "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom";
    public static final String ALFRESCO_DOC_DETAILS_PATH_PREFIX = ALFRESCO_WEB_SERVER + "/share/page/document-details?nodeRef=";
    static Logger log = LoggerFactory.getLogger(AlfrescoIntegrationTest.class);

    private Session getSession() {
//        return getSession(BindingType.WEBSERVICES);
        return getSession(BindingType.ATOMPUB);
    }


    public Session getSession(BindingType bindingType){
        SessionFactory sessionFactory;
        Session session;
        Map<String,String> sessionFactoryParameters;

        sessionFactory = SessionFactoryImpl.newInstance();
        sessionFactoryParameters = new HashMap<String, String>();
        //credentials
        sessionFactoryParameters.put(SessionParameter.USER, "akapoor");
        sessionFactoryParameters.put(SessionParameter.PASSWORD, "akapoor");

        log.info("Using binding {}", bindingType.value());

        if(BindingType.ATOMPUB.equals(bindingType)){
        //Connection setting - Atompub binding
            sessionFactoryParameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            sessionFactoryParameters.put(SessionParameter.ATOMPUB_URL, HTTP_ALFRESCO_ATOMPUB_URL);
        }

        //Browser Binding
/*
        sessionFactoryParameters.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
        sessionFactoryParameters.put(SessionParameter.BROWSER_URL, )
*/

        //Connection setting - Web service binding
        else if(BindingType.WEBSERVICES.equals(bindingType)){

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
        }


        // Set the alfresco object factory
        //This allows for being able to use Aspects
        sessionFactoryParameters.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

//        What is the repository id?
//        parameter.put(SessionParameter.REPOSITORY_ID, "A1");
//        Session session = sessionFactory.createSession(parameter);
//        List of repositories - for alfresco impl there is only one
        List<Repository> repositories = new ArrayList<Repository>();
        log.info("Getting repositories");
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

        return session;
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
        Folder root = getSession().getRootFolder();
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

        CmisObject folderObject = getSession().getObjectByPath(folderPath);
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
//        String query = "SELECT * FROM gat:gqtCustomer  WHERE   ( gat:quoteIdentifier = '"+ QUOTE_ID+"' )" ;
//        String query = "SELECT * FROM gat:gbkReport  WHERE   ( gat:reportIdentifier = 'BKG50OR_BKGR050A' )" ;
//        String query = "SELECT * FROM gat:gCustomerPaper  WHERE   ( gat:requestId IN ('1941', '1942', '1947', '1950', '1951', '1953', '1954', '1955', '1956', '1957', '1958', '1959', '1960', '1961', '1962', '1963', '1964', '1965', '1966', '1967', '1968', '1969', '1970' )  )" ;
        String query = "SELECT * FROM gat:gCustomerPaper  WHERE   ( gat:requestId IN ('1623', '1556', '2178', '1701' )  )";
        //PRE WS Binding takes 4 mins in all for 2167 rows. 3 mins in DB, 1 min on alfresco. Once query was fired next time took 1.5 mins, after that 1 min 18 secs.
        //PRE ATOM binding takes 50 secs after the query had already been fired once before.

        //TODO 1: test if setting operation context on session.query is making a diff
        //TODO 2: test if removing results.skipto makes a difference, just do a getPage
        //TODO 3: test if just setting the max page size in operation context to Integer.MAX and do a session.query is enough

        Session session = getSession();
        OperationContext operationContext = session.createOperationContext();
        int maxItemsPerPage = Integer.MAX_VALUE;
        operationContext.setMaxItemsPerPage(maxItemsPerPage);
        log.info("firing query: {}", query);
        ItemIterable<QueryResult> results = session.query(query, false, operationContext);
        int pageNum = 0, itemNum = 0;
//        ItemIterable<QueryResult> queryResultsPage = null;
        log.info("TotalNumItems: {}", results.getTotalNumItems());
//        int totalNumItems = (int) results.getTotalNumItems();
//        if (-1 == totalNumItems)
//            totalNumItems = Integer.MAX_VALUE;
//        queryResultsPage = results.getPage(totalNumItems);
        log.info("***QUERY PAGE {} has {} items", ++pageNum, results.getPageNumItems());
        for( QueryResult queryResult : results){
            log.info("" + ++itemNum + " , "
                    + queryResult.getPropertyByQueryName("cmis:objectTypeId") + " , "
                    + queryResult.getPropertyByQueryName("cmis:name") + " , "
//                    + queryResult.getPropertyByQueryName("gat:quoteIdentifier").getFirstValue() + " , "
                    + queryResult.getPropertyByQueryName("gat:reportIdentifier") + " , "
//                    + ((Calendar)qr.getPropertyByQueryName("crp:generationDateTime").getFirstValue()).getTime() + " , "
//                    + qr.getPropertyByQueryName("cmis:objectId").getFirstValue() + " , "
//                    + qr.getPropertyByQueryName("cmis:contentStreamFileName").getFirstValue() + " , "
                    + queryResult.getPropertyByQueryName("cmis:contentStreamMimeType")) ;
//                    + qr.getPropertyByQueryName("cmis:contentStreamLength").getFirstValue());
            }


    }


    @Test
    public void test_cmisQueryPaginatedResults(){
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
//        String query = "SELECT * FROM gat:gqtCustomer  WHERE   ( gat:quoteIdentifier = '"+ QUOTE_ID+"' )" ;
//        String query = "SELECT * FROM gat:gbkReport  WHERE   ( gat:reportIdentifier = 'BKG50OR_BKGR050A' )" ;
        String query = "SELECT * FROM gat:gCustomerPaper  WHERE   ( gat:requestId IN ('1941', '1942', '1947', '1950', '1951', '1953', '1954', '1955', '1956', '1957', '1958', '1959', '1960', '1961', '1962', '1963', '1964', '1965', '1966', '1967', '1968', '1969', '1970' )  )" ;
//        String query = "SELECT * FROM gat:gCustomerPaper  WHERE   ( gat:requestId IN ('118', '119', '120', '121', '122', '123', '124', '131', '132', '133', '134', '135', '136', '147', '14', '152', '15', '161', '16', '170', '171', '172', '178', '179', '17', '184', '185', '186', '188', '189', '18', '193', '194', '19', '202', '203', '204', '20', '211', '212', '213', '21', '221', '222', '226', '22', '231', '234', '237', '23', '244', '249', '24', '250', '255', '256', '258', '259', '260', '261', '262', '263', '264', '265', '277', '278', '279', '27', '280', '281', '282', '285', '287', '296', '297', '298', '299', '29', '300', '301', '302', '303', '304', '305', '306', '307', '308', '309', '30', '312', '314', '315', '316', '317', '318', '319', '31', '320', '321', '322', '323', '324', '325', '326', '327', '328', '329', '32', '330', '331', '332', '333', '334', '33', '34', '354', '355', '356', '358', '359', '35', '360', '361', '362', '363', '364', '365', '366', '367', '368', '369', '36', '370', '371', '372', '373', '376', '377', '379', '37', '381', '382', '383', '384', '385', '386', '387', '388', '389', '38', '390', '391', '392', '393', '395', '396', '399', '39', '400', '401', '403', '404', '405', '406', '407', '408', '409', '40', '410', '411', '412', '413', '414', '415', '416', '417', '418', '419', '41', '420', '421', '422', '423', '424', '42', '43', '444', '445', '446', '447', '449', '44', '450', '451', '452', '453', '454', '455', '456', '457', '458', '459', '45', '460', '461', '462', '463', '464', '465', '466', '467', '468', '469', '46', '470', '471', '472', '473', '474', '475', '476', '477', '478', '479', '47', '480', '481', '482', '483', '484', '485', '486', '487', '488', '489', '48', '490', '491', '492', '493', '494', '495', '496', '497', '498', '499', '49', '500', '501', '502', '503', '504', '505', '506', '507', '508', '509', '50', '510', '511', '512', '513', '514', '515', '516', '517', '518', '519', '520', '521', '522', '523', '524', '525', '526', '527', '528', '529', '530', '531', '532', '533', '534', '535', '542', '54', '555', '55', '561', '562', '563', '564', '56', '573', '582', '583', '584', '58', '590', '591', '592', '593', '594', '597', '604', '606', '607', '609', '60', '610', '611', '616', '623', '625', '627', '628', '630', '632', '633', '635', '636', '637', '638', '640', '641', '642', '643', '645', '650', '651', '652', '653', '654', '655', '65', '663', '665', '666', '66', '675', '678', '67', '680', '681', '684', '68', '691', '697', '698', '699', '69', '700', '701', '705', '706', '707', '708', '709', '710', '711', '716', '724', '725', '726', '727', '728', '729', '72', '730', '732', '735', '736', '737', '738', '739', '73', '740', '741', '745', '748', '749', '74', '750', '751', '752', '753', '755', '756', '757', '758', '759', '75', '760', '762', '763', '764', '765', '766', '767', '768', '769', '76', '773', '77', '78', '796', '797', '798', '799', '79', '801', '802', '803', '804', '805', '807', '808', '809', '80', '811', '812', '813', '814', '815', '819', '81', '820', '821', '82', '83', '84', '85')  )" ;
//        String query = "SELECT * FROM gat:gCustomerPaper" ;

        log.info("Getting Session");
        Session session = getSession();
        OperationContext operationContext = session.createOperationContext();
        int maxItemsPerPage = 1000;
        operationContext.setMaxItemsPerPage(maxItemsPerPage);
        ItemIterable<QueryResult> results = session.query(query, false, operationContext);
//        QueryStatement queryStatement = session.createQueryStatement(query);

        log.info(results.getTotalNumItems() + " results in all from query " + query);
        int pageNum = 1, itemNum = 0;
        ItemIterable<QueryResult> resultsPage = null;

        do{

            int skipToPosition = (pageNum-1) * maxItemsPerPage;
            log.info("SkipToPosition: {}", skipToPosition);
            resultsPage = results.skipTo(skipToPosition).getPage(maxItemsPerPage);
            log.info("PAGE: {}, items on page: {}", pageNum, resultsPage.getPageNumItems());

            for (QueryResult qr : resultsPage) {
                log.info("" + ++itemNum + " , "
                        + qr.getPropertyByQueryName("gat:requestId") + " , "
                        + qr.getPropertyByQueryName("cmis:objectTypeId") + " , "
                        + qr.getPropertyByQueryName("cmis:name") + " , "
                        + qr.getPropertyByQueryName("gat:reportIdentifier") + " , "
                        + qr.getPropertyByQueryName("cmis:contentStreamMimeType")) ;
            }
            if(pageNum ==10) break;
            ++pageNum;

        }while (resultsPage.getHasMoreItems());

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
        Document document = (Document) getSession().getObject(documentId);
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
        Document document = (Document) getSession().getObject(documentId);
        log.info("Content mimetype: {}", document.getPropertyValue("cmis:"));
        InputStream contentStream = document.getContentStream().getStream();
        log.info("Content read back: {}", getContentAsBytes(contentStream));
        contentStream.close();
    }

    //Returns the document id of the created content stream.
    public String uploadContentToRepository(String uploadDirectory, InputStream contentInputStream
            , String contentMimeType, String contentFileName, Map<String, Object> properties){
        Session session = getSession();
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
        Document document = (Document) getSession().getObject(documentId);
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
        Session session = getSession();
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
