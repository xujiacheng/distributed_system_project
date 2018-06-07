package finalProject;

import java.net.*;
import java.io.*;
import java.util.*;

public class receiveThread implements Runnable {
	String IP; 
	int id; 
	int port;
	boolean running = true;
	int counter = 0;
	
	
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
		Player p = new Player(); 
		
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
				
				peerSerialData toReceive = null;
				
				
					try { 
					
						InputStream is = clntSock.getInputStream();  
					    ObjectInputStream ois = new ObjectInputStream(is);  
						toReceive = (peerSerialData)ois.readObject();
						
						int senderID = toReceive.getSenderID(); 
						
						String senderMessage = toReceive.getMsg();
						String [] messageList = senderMessage.split(",");
						HashMap<Integer,String> temp_table = toReceive.getDatabase();
						if (messageList == null || messageList.length <= 1) {
							System.out.println("The message you receive is in incorrect format");
						}
						else if (Integer.parseInt(messageList[0]) == 1) {
							// normal bid message type
							// first decide whether it should be include in its own database
							
							int num1 = Integer.parseInt( messageList[1]);
							int num2 = Integer.parseInt( messageList[2]);
							
							synchronized(p.database)
					        {
								if (senderID% 6 == id%6) {}
								else if ((p.database.size() + 1 ) == temp_table.size()) {
									// now it is valid bid message to me. 
									// now check whether I want to chanllenge or keep biding
									p.database.put(p.database.size() + 1  , "" + senderID + ":" + num1 + ":" + num2);
									
									if ((senderID % 6 + 1 ) == id % 6 || (senderID == 5 && id == 1) ) {
										System.out.println("player" + senderID + "has bid " + num1 + " of " + num2);
										System.out.println("It is your turn now. You can choose bid or challenge");
									}
									else {
										System.out.println("player" + senderID + "has bid " + num1 + " of " + num2);
										System.out.println("Not your turn yet.");
									}
								}
								else if ((p.database.size() + 1 ) < temp_table.size() ) {
									// receiver doesn't keep the latest database
									for (int i : temp_table.keySet()) {
										if (! p.database.containsKey(i)) {
											String t = temp_table.get(i);
											String [] arr = t.split(":");
											System.out.println("Missing records: " + "Player " + arr[0] + "has bid " + arr[1] + " of " + arr[2]);
										}
									}
									p.database = temp_table;
									if ((senderID % 6 + 1 ) == id  || (senderID == 5 && id == 1)) {
										System.out.println("player" + senderID + "has bid " + num1 + " of " + num2);
										System.out.println("It is your turn now. You can choose bid or challenge");
									}
									else {
										
										System.out.println("player" + senderID + "has bid " + num1 + " of " + num2);
										System.out.println("Not your turn yet.");
									}
									
									
								}
								// when receiver has newer database than sender, which should not happen actually, so do nothing
								else {}
					           
					        }
							
						}
						else if (Integer.parseInt(messageList[0]) == 2) {
							// received a chanllenge message, check its own data as updated. 
							synchronized(p.database) {
								for (int i : temp_table.keySet()) {
									if (! p.database.containsKey(i)) {
										String t = temp_table.get(i);
										String [] arr = t.split(":");
										System.out.println("Missing records: " + "Player " + arr[0] + "has bid " + arr[1] + " of " + arr[2]);
									}
								}
								
								
								if (senderID == id) {}
								
								// if someone challenges me
								else if (  senderID == (id +1) || (id == 5 && senderID == 1)) {
									// compute the winner
									//p.bidder_ID = id;
									System.out.println("Your next player challenges you, please broadcast in 3,3,bid_freq, bid_val format");
								}
								else {
									System.out.println("Somebody challenges, please broadcast type 3, -1, 0,0, bidder's bid to decide winner");
								}
							}
						}
						
						else if (Integer.parseInt(messageList[0]) == 3) {
							
							p.counter ++;
							HashMap<Integer, Integer> receivedHand = toReceive.getHand();
							if (Integer.parseInt(messageList[1] )== 3 ){
								p.bidder_bid_fre = Integer.parseInt(messageList[2]);
								p.bidder_bid_val = Integer.parseInt(messageList[3]);
								p.bidder_ID = senderID;
							}
							
							for(int i : receivedHand.keySet()) {
								int frequency = receivedHand.get(i);
								p.finalHand[i-1] += frequency;
							}
							
							if (p.counter == 5) {
								if (p.finalHand[p.bidder_bid_val -1] < p.bidder_bid_fre ) {
									// challenger wins
									System.out.println("Chanlleger Wins! Say bye to quit game");
								}
								else {
									System.out.println("Bidder with ID " + p.bidder_ID + " wins! Say bye to quit game");
								}
							}
						}
						
						else {
							System.out.println("You didn't receive things in correct format");
						}
						
						
						
						
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
