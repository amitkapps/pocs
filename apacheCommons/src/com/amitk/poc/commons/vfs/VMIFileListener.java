package com.amitk.poc.commons.vfs;

import org.apache.commons.vfs.FileChangeEvent;
import org.apache.commons.vfs.FileListener;

public class VMIFileListener implements FileListener {

	public void fileChanged(FileChangeEvent event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("File Changed " + event.getFile().getURL());
		System.out.println("File Contents:" + VFSTest.readInput(event.getFile().getContent().getInputStream()));
	}

	public void fileCreated(FileChangeEvent event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("File Created " + event.getFile().getURL());
	}

	public void fileDeleted(FileChangeEvent event) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("File Deleted " + event.getFile().getURL());
	}

}
