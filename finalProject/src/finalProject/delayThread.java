package finalProject;
import java.net.*;
import java.io.*;
import java.util.*;


public class delayThread implements Runnable {
	
	Socket senderSock;
	Socket recvSock;
	
	public delayThread(Socket s) {
		//System.setProperty("java.net.preferIPv4Stack", "true");
		
		//System.out.println("Client Got Connected  " );
		this.senderSock = s;
	}
	 
	public void run() {
		
		peerSerialData toSend = null;
		int port;
		SocketAddress senderAddress;
		senderAddress = senderSock.getRemoteSocketAddress();
		port = senderSock.getPort();
		//System.out.println("Handling client at " + senderAddress + " with port# " + port);
	
		
		try {
				// get initial info (name, role)
				InputStream is = senderSock.getInputStream();  
			    ObjectInputStream ois = new ObjectInputStream(is);  
				toSend = (peerSerialData)ois.readObject(); 
				//System.out.println("From client ( " + senderAddress + ") : " + toSend.getMsg());
				
				
				String recvIP = toSend.getReceiverIP(); 
				int recvPort = toSend.getReceiverPort(); 
				
				//however that delay works
				//System.out.println(System.currentTimeMillis() );
				//readConfig tcp = new readConfig();
				int max = 5000;
				int min = 10000;
				
				Thread.sleep( min + (int) (Math.random() * (max-min)) );
				//System.out.println(System.currentTimeMillis());

				recvSock = new Socket(recvIP, recvPort);
				
				
				//System.out.println("Peer " + toSend.getSenderID() + " sent " + toSend.getMsg() + " to process " + toSend.getRecvID());
				
			    OutputStream os = recvSock.getOutputStream(); 
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.flush();
				oos.writeObject(toSend);   //send object to server
				oos.flush();
				
				recvSock.close();
		
		
	
	} catch (Exception e) {
		System.out.println("delay layer error");
	}
	}

}
