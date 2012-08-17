package si.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 5/12/11
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileHandlerService {
    public static Logger log = LoggerFactory.getLogger(FileHandlerService.class);

    public void processFile(File file){
        log.info("Processing File:{}", file);
    }
}
