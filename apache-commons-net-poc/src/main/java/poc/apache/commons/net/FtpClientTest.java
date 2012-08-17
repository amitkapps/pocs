package poc.apache.commons.net;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class FtpClientTest{

    private static int timeout = 999999999;

    public static void main(String[] args) throws IOException {

        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("10.8.7.145");
        ftpClient.setSoTimeout(timeout);
        System.out.println("Connection: " + FTPReply.isPositiveCompletion(ftpClient.getReplyCode()));

        System.out.println("logged in:" + ftpClient.login("bea", "eaGle!"));
        System.out.println("Changed working directory:" + ftpClient.changeWorkingDirectory("amitk/GEMS"));

        FTPFile[] ftpFiles = ftpClient.listFiles();
        if(null != ftpFiles){
            System.out.println("Found {} files" + ftpFiles.length);
            for(int i=0; i<ftpFiles.length; i++){
                FTPFile ftpFile = ftpFiles[i];
                if(null == ftpFile)
                    System.out.println("ERROR: NULL FILE AT INDEX: " + i);
                System.out.println("ftpFile: {}" + ftpFile);
            }
        }

        if(null != ftpClient && ftpClient.isConnected()){
            try { ftpClient.logout(); } catch (Exception e) {}
            try { ftpClient.disconnect(); } catch (Exception ignore) {}
        }
        System.out.println("Disconnected ");

    }
}