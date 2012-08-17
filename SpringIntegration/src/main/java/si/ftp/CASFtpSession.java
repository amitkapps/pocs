package si.ftp;

import com.matson.cas.service.ftp.FtpBizException;
import com.matson.cas.service.ftp.FtpProxyDeleterBiz;
import com.matson.cas.service.ftp.FtpProxyGetterBiz;
import com.matson.cas.service.ftp.FtpProxyListBiz;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.file.remote.session.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 5/12/11
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CASFtpSession implements Session{
    public static Logger log = LoggerFactory.getLogger(CASFtpSession.class);
    public static final String UNIX_FILE_SEPARATOR = "/";
    private int proxyId;

    public CASFtpSession(int proxyId) {
        this.proxyId = proxyId;
    }

    @Override
    public boolean remove(String path) throws IOException {
        log.info("Removing file: {}", path);
        try {
            return new FtpProxyDeleterBiz().removeFile(proxyId, getFileName(path), getFileDir(path));
        } catch (FtpBizException e) {
            log.error("Could not delete file", e);
            throw new IOException(e);
        }
    }

    @Override
    public FTPFile[] list(String path) throws IOException {
        log.info("Listing files from: {}", path);
        try {
            return new FtpProxyListBiz().getFileList(proxyId, path);
        } catch (FtpBizException e) {
            log.error("Could not list files:", e);
            throw new IOException(e);
        }
    }

    @Override
    public void read(String source, OutputStream outputStream) throws IOException {
        log.info("Reading file from: {}", source);
        try {
            byte[] fileBytes = new FtpProxyGetterBiz().getFileBytes(proxyId, getFileName(source), getFileDir(source));
            outputStream.write(fileBytes);
        } catch (FtpBizException e) {
            log.error("Could not read files:", e);
            throw new IOException(e);
        }
    }

    private String getFileDir(String source) {
        String filePath = null;
        if(source.lastIndexOf(UNIX_FILE_SEPARATOR) != -1)
            filePath = source.substring(0, source.lastIndexOf(UNIX_FILE_SEPARATOR));
        return filePath;
    }

    private String getFileName(String source) {
        return source.substring(source.lastIndexOf(UNIX_FILE_SEPARATOR) + 1);
    }

    @Override
    public void write(InputStream inputStream, String destination) throws IOException {
        log.info("Writing file to: {}", destination);
    }

    @Override
    public void rename(String pathFrom, String pathTo) throws IOException {
        log.info("Renaming file from: {}, to: {}", pathFrom, pathTo);
    }

    @Override
    public void close() {
        log.info("Close");
    }

    @Override
    public boolean isOpen() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
