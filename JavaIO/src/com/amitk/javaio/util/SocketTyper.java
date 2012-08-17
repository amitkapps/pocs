package com.amitk.javaio.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class SocketTyper {

	URL url;
	Socket socket = null;
	
	public SocketTyper(String urlString)
		throws IllegalArgumentException{
		try {
			this.url = new URL(urlString);
			
		} catch (MalformedURLException me) {
			throw new IllegalArgumentException(me.getMessage());
		}
		if(! "http".equalsIgnoreCase(this.url.getProtocol()) ){
			throw new IllegalArgumentException(this.url.getProtocol() + " not supported!");
		}
	}
	
	
	public void showRawContent()
		throws IOException{
		StringBuffer requestBuff = new StringBuffer();
		requestBuff.append("GET ").append(url.getFile()).append(" HTTP/1.1\r\n")
					.append("User-Agent: SocketTyper\r\n")
					.append("Accept: text/*\r\n")
					.append("Host: ").append(url.getHost()).append(":").append(url.getPort()).append("\r\n")
					.append("\r\n");

		byte[] requestBytes = requestBuff.toString().getBytes();
		try{
			OutputStream os = getSocket().getOutputStream();
			InputStream is = getSocket().getInputStream();
			
			os.write(requestBytes);
			os.flush();
			
			StreamUtil.copyToStream(is, System.out, 256);
		}finally{
			if(socket != null && socket.isConnected()){
				socket.close();
			}
		}
	}
	
	private Socket getSocket()
		throws IOException{
		if(null == socket ){
			socket = new Socket(url.getHost(), url.getPort());
		}
		return socket;
	}
	
	public static void main(String[] args) {
		try {
			//String url = "http://10.8.7.145:9001/VINSight/common/login.jsp";
			String url = "http://10.8.7.145:9001/AMISPollerStartupServlet/adminMenu.jsp?userName=ak&password=ak";
			SocketTyper st = new SocketTyper(url);
			st.showRawContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
