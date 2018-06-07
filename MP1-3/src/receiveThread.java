import java.net.*;
import java.io.*;
import java.util.*;

public class receiveThread implements Runnable {
	String IP; 
	int id; 
	int port;
	boolean running = true;
	
	public receiveThread(int id, int port, String IP) {
		this.IP = IP; 
		this.id = id; 
		this.port = port;  
	} 
	public void setRunningF() {
		this.running = false;
	}
	
	public void run() {
		
		try {
			
			ServerSocket servSock = new ServerSocket(port);
			SocketAddress senderAddress;
			//System.out.println("receiver still running");
			while(running) {
				//System.out.println("receiver still running");
				Socket clntSock = servSock.accept(); // Get client connection		  
				senderAddress = clntSock.getRemoteSocketAddress();
				port = clntSock.getPort();
				System.out.println("Handling client at " + senderAddress + " with port# " + port + ", system time is " + System.currentTimeMillis());
				// Receive until client closes connection, indicated by -1 return
				
				serialData toReceive = null;
				
				try { 
					
						InputStream is = clntSock.getInputStream();  
					    ObjectInputStream ois = new ObjectInputStream(is);  
						toReceive = (serialData)ois.readObject();
						System.out.println("Received " + toReceive.getMsg() + " from process " + toReceive.getSenderID());
						
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (EOFException e) {  //needed to catch when client is done
					System.out.println("goodbye client at " + senderAddress + " with port# " + port);
					clntSock.close(); // Close the socket. We are done with this client!
				}
			}
			
			//System.out.println("receiver still running");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	

	
}
