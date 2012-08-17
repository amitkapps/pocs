package hibernateSearch;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.hibernate.search.query.dsl.QueryBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

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
public class EmployeeHibernateSearchTest {

    @Test
    public void buildIndex() throws InterruptedException {
        Session session = getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer().startAndWait();
        session.close();
    }

    @Test
    public void testHibernateSearch() throws ParseException {
        Session session = getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();

        // create native Lucene query unsing the query DSL
        // alternatively you can write the Lucene query using the Lucene query parser
        // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Employee.class).get();
        org.apache.lucene.search.Query keywordQuery = qb
                .keyword()
                .onFields("firstName", "lastName")
                .matching("Doe")
                .createQuery();

        org.apache.lucene.search.Query rangeQuery = qb
                .range()
                .onField("joinDate")
                .from(new DateTime(2011,1,1,0,0,0,0).toDate()).to(new DateTime().toDate())
                .createQuery();

        //Combine queries
        Query combineQuery = qb.bool().must(rangeQuery).not().must(keywordQuery).createQuery();

        //Native lucene query
        TermQuery termQuery = new TermQuery(new Term("firstName", "John"));

        Query parsedQuery = new QueryParser(Version.LUCENE_31, "firstName", new WhitespaceAnalyzer(Version.LUCENE_31)).parse("lastName:Doe AND firstName:Jane AND joinDate:[20100101 TO 20120101]");

        // wrap Lucene query in a org.hibernate.Query
        FullTextQuery fullTextQuery =
                fullTextSession.createFullTextQuery(parsedQuery, Employee.class);

        // execute search
        List result = fullTextQuery.list();

        log.info("{} Employees found: {}", fullTextQuery.getResultSize(), result);
        tx.commit();
        session.close();
    }
}
