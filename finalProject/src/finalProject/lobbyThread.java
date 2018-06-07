package finalProject;
import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*; // for IOException and Input/OutputStream
import java.util.*;

public class lobbyThread implements Runnable{
	
	Socket clntSock;
	private ArrayList<liarGame> roomList = new ArrayList<liarGame>();
	private ArrayList<ArrayList<Integer>> roomHelpers = new ArrayList<ArrayList<Integer>>();
	private boolean playerQuit = false;

	
	public lobbyThread(ArrayList<liarGame> a, ArrayList<ArrayList<Integer>> roomHelpers, Socket s) {
		
		System.out.println("Player Got Connected  " );
		this.clntSock = s;
		this.roomList = a;
		this.roomHelpers = roomHelpers;
		
	}
	
	
	
	public void run() {
		
		serializable toSend = null, toReceive;
		int port;
		SocketAddress clientAddress;
		clientAddress = clntSock.getRemoteSocketAddress();
		port = clntSock.getPort();
		System.out.println("Handling client at " + clientAddress + " with port# " + port);
	
		
		try {
				// get initial info (name, role)
				InputStream is = clntSock.getInputStream();  
			    ObjectInputStream ois = new ObjectInputStream(is);  
				toReceive = (serializable)ois.readObject(); 
				System.out.println("Get client ( " + clientAddress + ") : " + toReceive.getName());
				
				lobbyThreadUtility ltu = new lobbyThreadUtility(Thread.currentThread().getName());
				
				//assign room 
				if (toReceive.getType()==4) {
					toSend = ltu.enterRooms(roomList, roomHelpers, toReceive);
					OutputStream os = clntSock.getOutputStream();  
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(toSend);
				}
				
				//when player quit 
				if (toReceive.getType()==5) {
					ltu.quitRooms(roomList, roomHelpers, toReceive);
					OutputStream os = clntSock.getOutputStream();  
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(toSend);
					playerQuit = true;
					clntSock.close();
				} 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (EOFException e) {  //needed to catch when client is done
			System.out.println("goodbye client at " + clientAddress + " with port# " + port);
			try {
				clntSock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // Close the socket. We are done with this client!
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
			
			
		
		
	}

		
	

}
