package finalProject;
import java.io.Serializable;
import java.util.*;


public class liarGame implements Serializable{
	
	//Serializable object
	private static final long serialVersionUID = 1L;
	
	private String message, roomName;
	private int roomID;
	private ArrayList<serializable> players; 
	//private player lastBidPlayer; //上一位
	//private int[] lastBid; //latest bid
	//private HashMap<Integer, Integer> nOfDices; // # of each dice face
	//private boolean claimOfOne; 
	
	public liarGame(int id) {
		//this.roomName = name;
		this.roomID = id;
		this.players = new ArrayList<serializable>();
		//this.claimOfOne = false;
		//this.nOfDices = new HashMap<Integer, Integer>();
		//this.lastBid = new int[2];
		//this.lastBidPlayer = null;
	}
	
	//setters
	public void setRoomID(int roomID) { this.roomID = roomID;}
	public void setMessage(String msg) { this.message = msg;}
	

	
	//getters
	public String getRoomName() { return this.roomName;}
	public int getRoomID() { return this.roomID;}
	public String getMessage() { return this.message;}
	public ArrayList<serializable> getPlayers() {return this.players;}
	//public player getLastPlayer() { return this.lastBidPlayer;} 
	//public boolean getClaim() { return this.claimOfOne;}

	//add player to the room 
	public void addPlayer(serializable p) { this.players.add(p); }
	
	//get #of players in the room
	public int size() {
		return this.players.size();
	}
}
