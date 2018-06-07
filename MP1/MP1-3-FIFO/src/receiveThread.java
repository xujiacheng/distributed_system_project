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
	
	@SuppressWarnings("unchecked")
	public void run() {
		
		//peer
		peer p = new peer(); 
		
		try {
			
			ServerSocket servSock = new ServerSocket(port);
			SocketAddress senderAddress;
			//System.out.println("receiver still running");
			while(running) {
				//System.out.println("receiver still running");
				Socket clntSock = servSock.accept(); // Get client connection		  
				senderAddress = clntSock.getRemoteSocketAddress();
				port = clntSock.getPort();
				//System.out.println("Handling client at " + senderAddress + " with port# " + port + ", system time is " + System.currentTimeMillis());
				// Receive until client closes connection, indicated by -1 return
				
				serialData toReceive = null;
				
				try { 
					
						InputStream is = clntSock.getInputStream();  
					    ObjectInputStream ois = new ObjectInputStream(is);  
						toReceive = (serialData)ois.readObject();
						
						int senderID = toReceive.getSenderID(); 
						int senderOrder = toReceive.getSenderOrder();
						
						// if the sender order is exactly 1 larger than the self order, deliver the message 
						if (senderOrder == 1 + p.vector[senderID-1]) {
							
							// after deliver, self order should increase by 1
							p.vector[senderID-1]++;
							
							
							System.out.println( "Received  " +   toReceive.getMsg() + " from process " + senderID  );
							// After deliver the current message, check the buffer to see any other message that can be delivered
							while( p.buffer.get(senderID) != null && ! p.buffer.get(senderID).isEmpty())  {
								
								for (int i =0;i<p.buffer.get(senderID).size(); i++) {
									if (p.buffer.get(senderID).get(i).getSenderOrder() == 1+ p.vector[senderID-1] ) {
										
										System.out.println( "Received  " +   p.buffer.get(senderID).get(i).getMsg() + " from process " +  p.buffer.get(senderID).get(i).getSenderID() );
										p.vector[senderID-1]++;
										p.buffer.get(senderID).remove(i);
									}
								}
							}
						}
						// if the sender order is larger than the self order +1, then put to the buffer
						else if (senderOrder > 1 + p.vector[senderID-1]) {
							
							if (p.buffer.containsKey(senderID)) {
								
								p.buffer.get(senderID).add(toReceive);
							}
							else {
								ArrayList<serialData> temp= new ArrayList<>();
								temp.add(toReceive);
								p.buffer.put( senderID, temp );
							}
							
						}
						else {
							// reject the current message and do nothing
							
						}
						
						
						//System.out.println("Received " + toReceive.getMsg() + " from process " + toReceive.getSenderID());
						
						
					
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
