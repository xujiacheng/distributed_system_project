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
		readConfig tcp = new readConfig();
		for (int i = 1; i <= 5; i++) {
			map.put(i,tcp.getProperty(""+i));
		}
		
		String fromKeyboard = null;
		String message = "";
		//ArrayList<Socket> sockets = new ArrayList<>();
		
		//String sys_time;
		try {
			while (!(fromKeyboard = readFromKeyboard()).equalsIgnoreCase("bye") ) {
				//System.out.println(Thread.currentThread().getName() + " sending");
				String [] userinput = fromKeyboard.split(" ");
				
				for (int i=1;i<userinput.length; i++) {
					message+= userinput[i];
					message += " ";
				}
				
				
				//send directly to receiver
				//socket = new Socket(IPAddress, receiverPort);
				//Socket socket = new Socket("127.0.0.1", 4999);
				for (int i = 1; i <=5; i++) {
					//if (i == self_id) continue;
					Socket socket = new Socket("127.0.0.1", 4999);
					
					serialData toSend = new serialData(message,self_id, i);
					System.out.println("Sent " + message + " to process " + i + ", system time is " + System.currentTimeMillis());
					OutputStream os = socket.getOutputStream(); 
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.flush();
					oos.writeObject(toSend);   //send object to server
					oos.flush();
						
					socket.close();
					
				}
				//send to delay layer 
				
				
			}
			

		} catch (Exception E) {
			System.out.println("multicast not working correctly");
			
		}
	}
	
	
	
	
}
