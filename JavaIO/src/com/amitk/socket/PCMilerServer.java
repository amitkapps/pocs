package com.amitk.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PCMilerServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Server Socket starting on 1234");
		
		try{
			ServerSocket svrSock = new ServerSocket(1234);
			Socket sock = svrSock.accept();
			System.out.println("Accepted new connection");

			BufferedReader br = new BufferedReader( new InputStreamReader(sock.getInputStream()));
			System.out.println("Client Input:" + br.readLine());

			Thread.sleep(15000);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			bw.write("Hello from Svr\n");
			bw.flush();

			br.close();
			bw.close();
			sock.close();
			svrSock.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
