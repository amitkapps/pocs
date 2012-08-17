package poc.jasperreports.test;

import net.sf.jasperreports.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import poc.jasperreports.ReportGenerator;

import javax.sql.DataSource;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
//Main classes
//    net.sf.jasperreports.engine.JasperCompileManager
//    net.sf.jasperreports.engine.JasperFillManager
//    net.sf.jasperreports.engine.JasperPrintManager
//    net.sf.jasperreports.engine.JasperExportManager
//    package  net.sf.jasperreports.engine.export has classes that handle exporting data to
//appropriate file format
//JRDatasource interface to point to an arbitrary DS, or some built in impls to wrap collection
// or an array of JavaBeans

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ReportGeneratorTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    @Qualifier("outgateNotificationJasperDefinition_EmbeddedSql")
    Resource outgateNotificationJasperDefinition_EmbeddedSql;

    @Autowired
    @Qualifier("outgateNotificationJasperDefinition_FromPassedMapCollection")
    Resource outgateNotificationJasperDefinition_FromPassedMapCollection;

    Logger log = LoggerFactory.getLogger(ReportGeneratorTest.class);

    @Test
    public void testSanity(){
        assertThat(dataSource, notNullValue());
    }

    @Test
    public void testGenerateReportFromEmbeddedSql() throws JRException, SQLException, IOException {

        Map reportParameters = new HashMap();
        reportParameters.put("ReportTitle", "OUT GATE NOTIFICATION");

        byte[] pdfBytes = ReportGenerator.generateReportFromEmbeddedSql(
                outgateNotificationJasperDefinition_EmbeddedSql,
                                                    reportParameters,
                                                    ReportGenerator.REPORT_OUTPUT_FORMAT.PDF,
                dataSource);

        assertThat(pdfBytes, notNullValue());
        assertThat(pdfBytes.length, is(not(0)));

        File outputReportFile = new File("generated/reports/"
                                        , "OutgateNotificationReport-GeneratedFromEmbeddedSql.pdf");

        FileOutputStream fos = new FileOutputStream(outputReportFile, false);
        BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
        try {
            bos.write(pdfBytes);
        } finally {
            bos.close();
        }
    }
    @Test
    public void testGenerateReportFromPassedMapCollection() throws JRException, SQLException, IOException {

        Map reportParameters = new HashMap();
        reportParameters.put("ReportTitle", "OUT GATE NOTIFICATION");

        byte[] pdfBytes = ReportGenerator.generateReportFromPassedMapCollection(
                outgateNotificationJasperDefinition_FromPassedMapCollection
                                                    ,reportParameters
                                                    ,ReportGenerator.REPORT_OUTPUT_FORMAT.PDF
                                                    ,getDataset());

        assertThat(pdfBytes, notNullValue());
        assertThat(pdfBytes.length, is(not(0)));

        File outputReportFile = new File("generated/reports/"
                                        , "OutgateNotificationReport-GeneratedFromPassedMapCollection.pdf");

        FileOutputStream fos = new FileOutputStream(outputReportFile, false);
        BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
        try {
            bos.write(pdfBytes);
        } finally {
            bos.close();
        }
    }

    private Collection<Map<String, Object>> getDataset(){
        return simpleJdbcTemplate.queryForList("select * from SHIPMENT_INFO"); 
    }
}
