package hibernateSearch;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.impl.SimpleIndexingProgressMonitor;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static hibernateSearch.HibernateUtil.getSessionFactory;
import static hibernateSearch.HibernateUtil.log;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 4/30/11
 * Time: 2:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class MnsOceanEventHibernateSearchTest {

    public static Logger log = LoggerFactory.getLogger(MnsOceanEventHibernateSearchTest.class);

    /**
     * Run this to create the index for the first time
     * @throws InterruptedException
     */
    @Test
    public void buildIndex() throws InterruptedException {
        Session session = getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        log.info("Start Indexing");
        long startTime = System.currentTimeMillis();
        fullTextSession
                .createIndexer()
                .batchSizeToLoadObjects(500)
                .threadsToLoadObjects(5)
                .threadsForIndexWriter(3)
                .threadsForSubsequentFetching(20)
                .progressMonitor(new SimpleIndexingProgressMonitor())
                .startAndWait();
        log.info("Indexing ended, time taken: {} secs", (System.currentTimeMillis() - startTime)/1000 );
        session.close();
    }

    @Test
    public void testHibernateSearch() throws ParseException {
        String startTimeGmt = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZone(DateTimeZone.forID("GMT")).print(new DateTime(2011, 4, 7, 5, 15, 0, 0));
        String endTimeGmt = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZone(DateTimeZone.forID("GMT")).print(new DateTime(2011, 5, 6, 5, 15, 0, 0));
        String query = String.format("eventCode:LTV  AND partyList.typeCode:30 AND eventDate:[%s TO %s]", startTimeGmt, endTimeGmt);
        testHibernateSearch(query, OceanEvent.class);
    }


    private void testHibernateSearch(String luceneQuery, Class entityClass) throws ParseException {
        Session session = getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        // create native Lucene query using the query DSL
        // alternatively you can write the Lucene query using the Lucene query parser
        // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(entityClass).get();
/*
        Query eventCodeQuery = qb
                .keyword()
                .onField("eventCode")
                .matching("AVD")
                .createQuery();

        Query rangeQuery = qb
                .range()
                .onField("joinDate")
                .from(new DateTime(2011,4,10,0,0,0,0).toDate()).to(new Date())
                .createQuery();
*/

        //Combine queries
//        Query combineQuery = qb.bool().must(rangeQuery).not().must(eventCodeQuery).createQuery();

        //Native lucene query
//        TermQuery termQuery = new TermQuery(new Term("firstName", "John"));

        //IMP: Lucene stores all dates as GMT hence query string should also use GMT dates
        Query mnsEventsQuery = new QueryParser(Version.LUCENE_31, null, new WhitespaceAnalyzer(Version.LUCENE_31))
                .parse(luceneQuery);

/*        Equivalent SQL Query:
        select oe.ocean_event_id, oep.TYPE_CODE
        from mns_ocean_event_mt oe
        join mns_ocean_event_party_mt oep on oep.OCEAN_EVENT_ID = oe.OCEAN_EVENT_ID
        where oep.type_code = '30'
        and oe.event_code = 'LTV'
        and oe.event_date between (to_date('20110407','yyyymmdd')) and to_date('20110502', 'yyyymmdd')
*/

        // wrap Lucene query in a org.hibernate.Query
        FullTextQuery fullTextQuery =
                fullTextSession.createFullTextQuery(mnsEventsQuery, entityClass);

        log.info("Lucene Query: {}", luceneQuery);
        // execute search
        List result = fullTextQuery
                        .initializeObjectsWith(
                                //Let hibernate check in cache first before querying DB
                                ObjectLookupMethod.SECOND_LEVEL_CACHE,
                                //Will automatically batch. If batch size is custom tuned in the entity mapping
                                // itself we can use DatabaseRetrievalMethod.FIND_BY_ID instead
                                DatabaseRetrievalMethod.QUERY)
                        .list();

        log.info("{} Events found: {}", fullTextQuery.getResultSize(), result);
        tx.commit();
        session.close();
    }
    @Test
    public void test_hibernateSearchWithCache() throws ParseException {
        log.info("%%% Searching first time, will populate the cache %%%");
        testHibernateSearch();
        log.info("%%% Searching second time, will query directly from cache- no sql should be logged %%%");
        testHibernateSearch();

        log.info("%%% Searching third time, slight change in parameters, will return partial list directly from cache, and rest from DB%%%");
        String startTimeGmt = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZone(DateTimeZone.forID("GMT")).print(new DateTime(2011, 4, 7, 5, 15, 0, 0));
        String endTimeGmt = DateTimeFormat.forPattern("yyyyMMddHHmmss").withZone(DateTimeZone.forID("GMT")).print(new DateTime(2011, 5, 6, 5, 15, 0, 0));
        String query = String.format("eventCode:LTV  AND partyList.typeCode:(30 22) AND eventDate:[%s TO %s]", startTimeGmt, endTimeGmt);
        testHibernateSearch(query, OceanEvent.class);

    }
}
