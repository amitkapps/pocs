package com.amitk.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class PCMilerClient {

	public static void main(String[] args) {
		System.out.println("Starting socket client");
		
		try {
			Socket pcmSocket = new Socket("localhost", 1234);
			pcmSocket.setSoTimeout(10000);
			
			BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(pcmSocket.getOutputStream()));
			bw.write("Hello from client.\n");
			bw.flush();

			BufferedReader br = new BufferedReader( new InputStreamReader(pcmSocket.getInputStream()));
			System.out.println("Svr Response:" + br.readLine());

			bw.close();
			br.close();
			pcmSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
