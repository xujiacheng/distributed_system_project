import java.net.*;
import java.io.*;
import java.util.*;


public class peer {
	
	public static int[] vector = {0, 0, 0, 0};
	public static int lastDeliveredN = -1;
	public static int sequencerN = 0;
	public static int id;
	
	public static ArrayList<serialData> buffer = new ArrayList<serialData>(); 
	public static HashMap<Integer, serialData> sequenceBuffer = new HashMap<>();
	
	public peer() {
		
	}
	
	
	public ArrayList<serialData> getBuffer (){
		return this.buffer;
	}
	public HashMap<Integer, serialData> getSBuffer (){
		return this.sequenceBuffer;
	}
	public int lastDeliveredN() {
		return this.lastDeliveredN;
	}
	
	public static void main(String[] args) throws Exception {
		//System.setProperty("java.net.preferIPv4Stack", "true");

		config cfg = new config(); 
		//int id = cfg.getLatestCounter(); 
		readConfig tcp = new readConfig();

		
		try {
			
			BufferedReader stdin; /* input from keyboard */
			String sendString; /* string to be sent */
			stdin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter your id: ");
			sendString = stdin.readLine();
			id = Integer.parseInt(sendString); 
			int PORT = 5000+ id; 
			System.out.println("your id is " + id + ". your port# is " + PORT);
		
			
			multicast PST;
			receiveThread PRT; 
			//int PORT = cfg.getPortNum(id); 
			String IPAddress = cfg.getIP(); 
		
		
			PST = new multicast(id);
			Thread T = new Thread(PST);
			//System.out.println("1");
			PRT = new receiveThread(id, PORT, IPAddress);
			//System.out.println("1");
			Thread TT = new Thread(PRT);
			//System.out.println("1");
			
			//System.out.println("2");
			T.start(); 
			
			//System.out.println("3");
			TT.setDaemon(true); 
			TT.start();
			//System.out.println("4");
			T.join(); 
			System.out.println("I'm done"); 
			
		}
		catch(Exception e) {
			//System.out.println("wrong input for id and port number");
		}

	}
	
}


