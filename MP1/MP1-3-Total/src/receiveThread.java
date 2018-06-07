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
				System.out.println("receiver still running");
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
						
						//if already assigned a sequence number
						System.out.println(toReceive.getSequence());
						
						if (toReceive.getSequence()!= -1) {
							int sequenceN = toReceive.getSequence();  
							if (sequenceN == 1 + p.lastDeliveredN()) {
								
								p.lastDeliveredN++; 
								
								//System.out.println("order is " + p.lastDeliveredN + " ID is " + senderID);
								
								System.out.println( "Received  " +   toReceive.getMsg() + " from process " + senderID  );
								//System.out.println("sequencer buffer closest msg" + p.sequenceBuffer.get(p.lastDeliveredN+1).getMsg());
								
								while( p.sequenceBuffer != null && ! p.sequenceBuffer.isEmpty())  {
									if (p.sequenceBuffer.containsKey(p.lastDeliveredN+1)) {
										System.out.println( "Received  " +   p.sequenceBuffer.get(p.lastDeliveredN+1).getMsg() + " from process " +  p.sequenceBuffer.get(p.lastDeliveredN+1).getSenderID() );
										p.lastDeliveredN++; 
										p.sequenceBuffer.remove(p.lastDeliveredN);
									}
									
								}
							}
							else if (sequenceN > 1 + p.lastDeliveredN()) {
								//System.out.println("order is " + p.vector[senderID-1] + " ID is " + senderID);
									p.sequenceBuffer.put(sequenceN, toReceive );
							}
							else {
								//System.out.println("I'm in the else. The order is " + p.vector[senderID-1] + " ID is " + senderID);
								
							}
							
						}
						//if no sequence number is assigned 
						else {
							//if not the sequencer (id=1), put into the buffer 
							if (p.id != 1) {
								p.buffer.add(toReceive);
								System.out.println("I'm not 1, I have something in buffer 0 " + p.buffer.get(0).getMsg());
							}
							//if is the sequencer, assign a sequence number and broadcast
							else if (p.id == 1){
								toReceive.setSequence(p.sequencerN); 
								System.out.println("sequence number is " + p.sequencerN);
								for (int i = 1; i < 5; i++) {
									//if (i == p.id) continue;
									Socket socket = new Socket("127.0.0.1", 4999);
									serialData toSend = new serialData(toReceive.getMsg(), toReceive.getSenderID(), i, p.vector[toReceive.getSenderID()-1]);
									toSend.setSequence(p.sequencerN);
									System.out.println("Sent " + toReceive.getMsg() + " to process " + i + ", system time is " + System.currentTimeMillis());
									OutputStream os = socket.getOutputStream(); 
									ObjectOutputStream oos = new ObjectOutputStream(os);
									oos.flush();
									oos.writeObject(toSend);   //send object to server
									oos.flush();
									socket.close();
								}
								p.sequencerN++; 
								System.out.println("new sequencer is " + p.sequencerN);
							
							}
							
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
