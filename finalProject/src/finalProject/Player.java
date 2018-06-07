package finalProject;

import java.net.*;
import java.io.*;
import java.util.*;

public class Player {

	public static int identifier = -1;
	public static int port_num;
	public static String IP;
	public static boolean canPlay = false;
	public static String name;
	public static int roomID;
	public static String delay_server_IP;
	public static int delay_server_port = 4990;
	
	public static int bidder_ID = -1;
	public static int bidder_bid_fre = -1;
	public static int bidder_bid_val = -1;
	
	//public int room_ID;
	
	public static ArrayList<serializable> players = new ArrayList<>();
	
	
	public static HashMap<Integer, Integer> result = new HashMap<>();
	public static HashMap<Integer, playerInfo> playerList = new HashMap<>();
	
	public static HashMap<Integer, String> database = new HashMap<>();
	
	public static int []  finalHand = new int [6];
	public static int counter = 0;
	
	
	
	public Player() {
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// get IP address
		try {
			IP = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Your IP address is: " + IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		String lobby_ip;
		int lobby_port;
		
		try {
			// get user input from keyboard, including self name and port and lobby_ip, lobby_port
			BufferedReader stdin; /* input from keyboard */
			String sendString; /* string to be sent */
			stdin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter your name, port, lobby_ip, lobby_port, DelayServerIP. Separate by comma: ");
			sendString = stdin.readLine();
			String [] arr = sendString.split(",");
			name = arr[0];
			port_num = Integer.parseInt(arr[1]);
			lobby_ip = arr[2];
			lobby_port = Integer.parseInt(arr[3]);
			delay_server_IP = arr[4];
			System.out.println("Your IP Address and PORT: " + IP + " , " + port_num);
			System.out.println("Ready to connect with Lobby");
			
			// connect with lobby now 
			String message;
			Socket socket = new Socket(lobby_ip, lobby_port);
			serializable toSend = null;
			toSend = new serializable(name,port_num,IP);
			OutputStream os = socket.getOutputStream(); 
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.flush();
			oos.writeObject(toSend);   //send object to server
			oos.flush();
			socket.close();	
		
	
		}
		catch(Exception e) {

		}
		
		// now try to receive data from lobby
		try {
			ServerSocket servSock = new ServerSocket(port_num);
			//SocketAddress senderAddress;
			while (!canPlay) {
				Socket clntSock = servSock.accept(); // Get client connection		  
				//senderAddress = clntSock.getRemoteSocketAddress(); 
				serializable toReceive = null;
				
				InputStream is = clntSock.getInputStream();  
			    ObjectInputStream ois = new ObjectInputStream(is);  
				toReceive = (serializable)ois.readObject();
				
				/* 
				 * let the player know other players info
				 * self id, room_id
				 * canPlay = true
				 * populate    playerList 
				 */
				players = toReceive.getRoomInfo().getPlayers();
				identifier = toReceive.getID();
				roomID = toReceive.getRoom();
				
				for (int i = 0; i < players.size(); i++) {
					int temp_id = i+1;
					String temp_ip = players.get(i).getIP();
					int temp_port =  players.get(i).getPort(); 
					//if (temp_id == identifier) continue;
					playerInfo new_player = new playerInfo(temp_id, temp_port,temp_ip);
					playerList.put(temp_id, new_player);
				}
				
				
				canPlay = true;
			}
			
			System.out.println(" Succesfully receiving back from Lobby, game can start");
		}
		catch (Exception e) {
			
		}
		
		
		
		try {
			// Now ready to generate dice info and start send, receive thread
			int [] hands = new int [5];
			for (int i = 0; i < 5; i++) {
				hands[i] =  1 + (int)(Math.random() * 6);
			}
			
			for (int i = 0; i <5; i++) {
				if (!result.containsKey( hands[i] )) {
					result.put(hands[i], 1);
				} 
				else {
					int temp = result.get(hands[i]) +1;
					result.put( hands[i],temp);
				}
			}
			

			multicast PST;
			receiveThread PRT; 
			//int PORT = cfg.getPortNum(id); 
			//String IPAddress = cfg.getIP(); 
		
		
			PST = new multicast(identifier);
			Thread T = new Thread(PST);
			//System.out.println("1");
			PRT = new receiveThread(identifier, port_num, IP);
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
		catch(Exception e) {}
	
		
		
		
			
	}

}
