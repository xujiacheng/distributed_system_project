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
				serialData toReceive = null;
				
				try { 
					
						InputStream is = clntSock.getInputStream();  
					    ObjectInputStream ois = new ObjectInputStream(is);  
						toReceive = (serialData)ois.readObject();
						
						int senderID = toReceive.getSenderID(); 
						//int senderOrder = toReceive.getSenderOrder();
						int [] senderVector = toReceive.getSenderVector();
						
						// This is to check whether the incoming message satisfies the deliver condition
						
						boolean deliver = true;
						for (int i = 1;i <= 4; i++ ) {
							if ( i == senderID ) {
								if (senderVector[i-1] != p.vector[i-1]+1) {
									deliver = false;
								}
								
							}
							else {
								if (senderVector[i-1] > p.vector[i-1]) {
									deliver = false;
								}
							}
							
						}
						// If satisfy, then deliver, and check the buffer 
						if (deliver == true) {
							p.vector[senderID-1]++;
							System.out.println( "Received  " + toReceive.getMsg()  + " from process " +  toReceive.getSenderID() );
							
							while ( p.buffer.get(senderID) != null && ! p.buffer.get(senderID).isEmpty()) {
								for (int i =0;i<p.buffer.get(senderID).size(); i++) {
									// if (p.buffer.get(senderID).get(i).getSenderOrder() == 1+ p.vector[senderID-1] )
									boolean deliver_t = true;
									for (int j = 1;j <= 4; j++ ) {
										if ( senderID == j ) {
											if (   p.buffer.get(senderID).get(i).getSenderVector()[j-1]  != p.vector[j-1]+1) {
												deliver_t = false;
											}
											
										}
										else {
											if ( p.buffer.get(senderID).get(i).getSenderVector()[j-1]  > p.vector[j-1]  ) {
												deliver_t = false;
											}
										}
										
									}
									
									if (deliver_t == true) {
										System.out.println( "[buffered]Received  " +   p.buffer.get(senderID).get(i).getMsg() + " from process " +  p.buffer.get(senderID).get(i).getSenderID() );
										p.vector[senderID-1]++;
										p.buffer.get(senderID).remove(i);
									}
									
								}
							}
						}
						
						// If cannot be delivered, put it into the buffer
						else if (senderVector[senderID-1] > p.vector[senderID-1] ) {
							if (p.buffer.containsKey(senderID)) {
								//ArrayList<serialData> current = p.buffer.get(senderID);
								p.buffer.get(senderID).add(toReceive);
							}
							else {
								ArrayList<serialData> temp= new ArrayList<>();
								temp.add(toReceive);
								p.buffer.put( senderID, temp );
							}
						}
						else {}
						
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (EOFException e) {  //needed to catch when client is done
					System.out.println("goodbye client at " + senderAddress + " with port# " + port);
					clntSock.close(); // Close the socket. We are done with this client!
				}
			}

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	

	
}
