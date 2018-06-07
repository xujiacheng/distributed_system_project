package finalProject;
//import java.net.*; // for Socket, ServerSocket, and InetAddress
//import java.io.*; // for IOException and Input/OutputStream
import java.util.*;


//////////////////////////////////
//GAME SEVER THREAD UTILITY///////
///////ONLY FOR GAME!!!!!/////////


public class lobbyThreadUtility {
	
	private  String threadName;
	
	public lobbyThreadUtility(String thread) {
		this.threadName = thread;
		System.out.println("Thread [" + threadName + "] is serviced");
	}
	
	
	
	//找第二个list，check helper[0] is empty, 
	//if not -> get helper[0][0], put player into room[helper[0][0]].
			// if that room is full.  
			//		;
			//		helper[1].add(helper[0].remove(0));
	//if empty, 
	//	room.add(new liaGame)
	//	helper[0].add(room.size()-1)
	//wait until the room is full to send the complete list. 
	
	public synchronized serializable enterRooms(ArrayList<liarGame> roomList, ArrayList<ArrayList<Integer>> roomHelpers, 
			serializable toReceive) {
		int roomID;
		if (roomHelpers.get(0).isEmpty()) {
			roomID = roomList.size();
			roomList.add(new liarGame(roomID));
			roomHelpers.get(0).add(roomID); 
			toReceive.setRoom(roomID,1);// player id 从1开始数，比array index大1
			roomList.get(roomID).addPlayer(toReceive);
		}
		else {
			roomID = roomHelpers.get(0).get(0);
			int playerID = roomList.get(roomID).getPlayers().size();
			toReceive.setRoom(roomID, playerID);
			roomList.get(roomID).addPlayer(toReceive);
			if (roomList.get(roomID).getPlayers().size()==5) {
				roomHelpers.get(1).add(roomHelpers.get(0).remove(0));
			}
		}
		while (roomList.get(roomID).getPlayers().size()<6) {
			System.out.println("Player " + toReceive.getName()+ " is waiting in room " + roomID +
					" for the other players to come.");
			try { 
				
				wait(); 
	         }
	         catch(InterruptedException e) {
	        	 
	         }
		}
		
		toReceive.setroominfo(roomList.get(roomID));
		return toReceive;
	}
	
	public synchronized serializable quitRooms(ArrayList<liarGame> roomList, ArrayList<ArrayList<Integer>> roomHelpers,
			serializable toReceive) {
		int roomID = toReceive.getRoom(); 
		int pID = toReceive.getID();
		roomList.get(roomID).getPlayers().remove(pID-1); 
		for ( int i = 0; i< roomHelpers.get(1).size(); i++ ) {
			if (roomHelpers.get(1).get(i)==roomID) {
				//System.out.println(roomHelpers.get(1).get(i));
				roomHelpers.get(0).add(roomHelpers.get(1).remove(i));
			} 
		}
		System.out.println("Room " + roomID + " player " + pID + "'s space is freed");
		
		return toReceive; 
	}
	

}
