package finalProject;
import java.io.*;
import java.net.*;
import java.util.*;

public class Lobby {

	
	public static void main(String[] args) throws IOException {
		
		
		int servPort = 6666;
		InetAddress addr = InetAddress.getLocalHost(); 
		String lobbyIP = addr.getHostAddress(); 
		System.out.println("IP adress of lobby is: " + lobbyIP + ". Port number is " + servPort);
		
		ArrayList<liarGame> roomList = new ArrayList<liarGame>();
		ArrayList<ArrayList<Integer>> roomHelpers = new ArrayList<ArrayList<Integer>>();
		
		
		
		//initialLiarGame(roomList);
		//System.out.println(roomList.get(3).getgameIP());
		
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
			lobbyThread lobby = new lobbyThread(roomList, roomHelpers, clntSock);
			
			Thread T = new Thread(lobby);
			T.start();
			
			
		}
		serverSocket.close();
	}

}