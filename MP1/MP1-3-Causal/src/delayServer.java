import java.net.*;
import java.io.*;
import java.util.*;


public class delayServer {
	
	
	public static void main(String[] args) throws IOException {

		//port# & IP address for delay layer 
		int servPort = 4999;
		String serverIP = "127.0.0.1"; 
		
		
		
		ServerSocket serverSocket = null;
		boolean listening = true;
		
		try {
			serverSocket = new ServerSocket(servPort);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + servPort + ". ");
			System.exit(-1);
		}
		
		// Create a server socket to accept client connection requests
		
		System.out.println("server waiting...... ");
		

		
		while (listening) { // Run forever, accepting and servicing connections   
			
			// the server is waiting for clients to connect. 
			
			Socket clntSock = serverSocket.accept();  // Get client connection
			delayThread server = new delayThread(clntSock);
			
			Thread T = new Thread(server);
			T.start();
			
			
		}
		serverSocket.close();
	}
	
	

}
