package com.amitk.poc.commons.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileMonitor;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;

public class VFSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			StaticUserAuthenticator auth = new StaticUserAuthenticator(
					null, "akapoor", "tempe");
			FileSystemOptions opts = new FileSystemOptions();
			//if not set then error if the ftp server is not a known host
			//http://mail-archives.apache.org/mod_mbox/commons-user/200504.mbox/%3C20050427025219.60424.qmail@web31512.mail.mud.yahoo.com%3E
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			
			DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
			FileObject fo = VFS.getManager().resolveFile("sftp://10.8.7.145/export/home/akapoor/ftp", opts);
			//FileObject fo = VFS.getManager().resolveFile("ftp://10.8.4.172", opts);
			//writeOutput(fo.getContent().getOutputStream(), "Hello From Client");
			
			
			//register listener
			DefaultFileMonitor fm = new DefaultFileMonitor(new VMIFileListener());
			fm.setDelay(5000);
			fm.addFile(fo);
			fm.start();

			System.out.println("Done");
			//System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String readInput(InputStream is)throws IOException{
		
		byte readByte;
		StringBuffer sb = new StringBuffer();
		while((readByte = (byte)is.read()) != -1){
			sb.append(new String(new byte[] {readByte}));
		}
		return sb.toString();
	}

	public static void writeOutput(OutputStream os, String output)throws IOException{
		os.write(output.getBytes());
	}


}
