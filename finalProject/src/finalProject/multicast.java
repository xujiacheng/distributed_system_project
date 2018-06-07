package finalProject;

import java.net.*;
import java.io.*;
import java.util.*;



public class multicast implements Runnable {
	static int self_id;
	
	//HashMap<Integer, String> map = new HashMap<>();
	
	public multicast(int self_id) {
		this.self_id = self_id;
	}
	public String readFromKeyboard() throws IOException {
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Enter your message in form of [sent [id] [message]]");
		//System.out.println("If you want to leave, enter 'bye'.");
		System.out.println("Enter your message type, message in correct format");
		sendString = stdin.readLine();
		return sendString; 
	}
	
	public void run() {
		
		// in order to access the global vector and 
		Player p = new Player(); 
		
		
		
		String fromKeyboard = null;
		
		//ArrayList<Socket> sockets = new ArrayList<>();
		
		//String sys_time;
		try {
			while (!(fromKeyboard = readFromKeyboard()).equalsIgnoreCase("bye") ) {
				//System.out.println(Thread.currentThread().getName() + " sending");
				String [] userinput = fromKeyboard.split(" ");
				String message = fromKeyboard;
				if (userinput == null || userinput.length <= 1 ) {
					System.out.println("Wrong input format. Retype again");
					continue;
				}
				
				else if (Integer.parseInt(userinput[0]) ==1) {
					
					int num1 = Integer.parseInt(userinput[1]);
					int num2 = Integer.parseInt(userinput[2]);
					synchronized(p.database) {
						p.database.put( p.database.size()+1,  "" + self_id + ":" + num1 + ":" + num2);
					}
					
				}
				else if (Integer.parseInt(userinput[0]) ==2) {
					
				}
				else if (Integer.parseInt(userinput[0]) ==3) {
					
					
				}
				else {
					
				}
				// if identifier and table size == 0, just be the first one to bid
				
				
				
				//send directly to delay thread for each unicast 
				
				for (int i = 1; i < 5; i++) {
					
					//if (i == self_id) continue;
					Socket socket = new Socket( p.delay_server_IP, p.delay_server_port);
					
					playerInfo temp = p.playerList.get(i);
					peerSerialData toSend = new peerSerialData(message,self_id, i, p.IP, temp.getIP(), temp.getPortNum(), p.database, p.result);
					//System.out.println("Sent " + message + " to process " + i + ", system time is " + System.currentTimeMillis());
					OutputStream os = socket.getOutputStream(); 
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.flush();
					oos.writeObject(toSend);   //send object to server
					oos.flush();
						
					socket.close();
					
				} 
				
				
			}
			

		} catch (Exception E) {
			System.out.println("multicast not working correctly");
			
		}
	}
	
	
	
	
}
