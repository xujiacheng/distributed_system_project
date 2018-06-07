import java.net.*;
import java.io.*;
import java.util.*;



public class multicast implements Runnable {
	static int self_id;
	
	HashMap<Integer, String> map = new HashMap<>();
	
	public multicast(int self_id) {
		this.self_id = self_id;
	}
	public String readFromKeyboard() throws IOException {
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your message in form of [sent [id] [message]]");
		System.out.println("If you want to leave, enter 'bye'.");
		sendString = stdin.readLine();
		return sendString; 
	}
	
	public void run() {
		
		// in order to access the global vector and 
		peer p = new peer(); 
		
		readConfig tcp = new readConfig();
		for (int i = 1; i < 5; i++) {
			map.put(i,tcp.getProperty(""+i));
		}
		
		String fromKeyboard = null;
		
		//ArrayList<Socket> sockets = new ArrayList<>();
		
		//String sys_time;
		try {
			while (!(fromKeyboard = readFromKeyboard()).equalsIgnoreCase("bye") ) {
				//System.out.println(Thread.currentThread().getName() + " sending");
				String [] userinput = fromKeyboard.split(" ");
				String message = "";
				for (int i=1;i<userinput.length; i++) {
					message+= userinput[i];
					message += " ";
				}
				
				//increment the order by 1 of the sender 
				p.vector[self_id-1]++; 
				
				//send directly to delay thread for each unicast 
				
				for (int i = 1; i < 5; i++) {
					if (i == self_id) continue;
					Socket socket = new Socket("127.0.0.1", 4999);
					
					serialData toSend = new serialData(message,self_id, i, p.vector[self_id-1]);
					System.out.println("Sent " + message + " to process " + i + ", system time is " + System.currentTimeMillis());
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
