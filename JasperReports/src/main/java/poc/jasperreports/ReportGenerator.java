package poc.jasperreports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class ReportGenerator {

    // JasperCompileManager  to compile reports - compiled to JasperReport class
    //Jasper report is a class ready to be filled with data
    //one can use fillReportXXX() methods exposed by JasperFillManager

    //Main classes
//    net.sf.jasperreports.engine.JasperCompileManager
//    net.sf.jasperreports.engine.JasperFillManager
//    net.sf.jasperreports.engine.JasperPrintManager
//    net.sf.jasperreports.engine.JasperExportManager
//    package  net.sf.jasperreports.engine.export has classes that handle exporting data to
    //appropriate file format
    //JRDatasource interface to point to an arbitrary DS, or some built in impls to wrap collection
    // or an array of JavaBeans

    //Jasper reports high level tutorial http://jasperforge.org/uploads/publish/jasperreportswebsite/JR%20Website/jasperreports_tutorial.html
    //Creating report using iReport http://aspalliance.com/1229_Creating_and_Designing_Report_Using_iReport__Part_1.5
    // Jasper report samples http://jasperforge.org/uploads/publish/jasperreportswebsite/trunk/samples.html
    // Jasper report schema reference http://jasperforge.org/website/jasperreportswebsite/trunk/schema.reference.html?header=project&target=jasperreports
    // javadoc api http://jasperreports.sourceforge.net/api/index.html

    private static Logger log = LoggerFactory.getLogger(ReportGenerator.class);

    public enum REPORT_OUTPUT_FORMAT {PDF};

    public static byte[] generateReportFromEmbeddedSql( Resource compiledJasperReport,
                                                        Map reportParameters,
                                                        REPORT_OUTPUT_FORMAT report_output_format,
                                                        DataSource dataSource)
            throws SQLException, IOException, JRException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(loadJasperReportObject(compiledJasperReport)
                                                                   , reportParameters, connection);
            return printReport(jasperPrint, REPORT_OUTPUT_FORMAT.PDF);
        } finally {
            if(null != connection)
                connection.close();
        }
    }

    public static byte[] generateReportFromPassedMapCollection(Resource compiledJasperReport,
                                                        Map reportParameters,
                                                        REPORT_OUTPUT_FORMAT report_output_format,
                                                        Collection<Map<String, Object>> mapCollection)
            throws IOException, JRException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(loadJasperReportObject(compiledJasperReport)
                                                               , reportParameters
                                                               , new JRMapCollectionDataSource(mapCollection) );
        return printReport(jasperPrint, report_output_format);
    }

    private static byte[] printReport(JasperPrint jasperPrint, REPORT_OUTPUT_FORMAT report_output_format) throws JRException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        switch (report_output_format){
            case PDF: JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
                break;
        }

        return baos.toByteArray();
    }

    private static JasperReport loadJasperReportObject(Resource compiledJasperReport) throws IOException, JRException {
        log.info("Loading report definition from {}", compiledJasperReport);
        InputStream is = compiledJasperReport.getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
        log.info("Loaded jasper report" + jasperReport.getClass());
        return jasperReport;
    }
}

