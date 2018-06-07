import java.net.*;
import java.io.*;
import java.util.*;


public class sendThread implements Runnable {

	static int self_id;
	public sendThread(int self_id) {
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
		String fromKeyboard = null;
		String message = "";
		int receiverID = -1; 
		int receiverPort = -1;
		String IPAddress = "127.0.0.1";
		Socket socket = null;
		//String sys_time;
		try {
			while (!(fromKeyboard = readFromKeyboard()).equalsIgnoreCase("bye") ) {
				//System.out.println(Thread.currentThread().getName() + " sending");
				String [] userinput = fromKeyboard.split(" ");
				receiverID = Integer.parseInt(userinput[1]);
				for (int i=2;i<userinput.length; i++) {
					message+= userinput[i];
					message += " ";
				}
				receiverPort = 5000 + receiverID;
				
				//send directly to receiver
				//socket = new Socket(IPAddress, receiverPort);
				
				//send to delay layer 
				socket = new Socket("127.0.0.1", 4999);
				
				
				
				serialData toSend = new serialData(message,self_id, receiverID);
				System.out.println("Sent " + message + " to process " + receiverID + ", system time is " + System.currentTimeMillis());
				
			    OutputStream os = socket.getOutputStream(); 
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.flush();
				oos.writeObject(toSend);   //send object to server
				oos.flush();
				
				socket.close();
				
			}
			System.out.println("send thread of peer " + self_id + " has finished");
			
			

		} catch (Exception E) {
			System.out.println("sendThread not working correctly");
			
		}
	}
	
}
