package finalProject;
import java.io.*;
import java.util.*;

public class peerSerialData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String msg;
	private int senderID; 
	private int recvID; 
	private String senderIP;
	private String receiverIP;
	private int receiverPort;
	private HashMap<Integer,String> database;
	private HashMap<Integer, Integer> hand; 
	//private int senderOrder;
	/*
	 * 1: 
	 * 
	 * 
	 */
	//private int type;
	
	public peerSerialData(String msg, int senderID, int recvID, String senderIP, String receiverIP, int receiver_port, HashMap<Integer, String> database,HashMap<Integer,Integer> hand ) {
		this.msg = msg; 
		this.senderID = senderID;
		this.recvID = recvID;
		this.receiverIP = receiverIP;
		this.senderIP = senderIP;
		this.receiverPort = receiver_port;
		this.database = database;
		this.hand = hand;
		//this.senderOrder = senderOrder;
		//this.type = type;
	}
	
	//setter & getter of Msg
	public void setMsg(String msg) {	this.msg = msg;}
	public String getMsg() {	return this.msg;}
	
	//getters 
	public int getSenderID() {	return this.senderID;}
	public int getRecvID() {	return this.recvID; }
	
	public String getSenderIP() {return senderIP;}
	public String getReceiverIP() {return receiverIP;}
	public int getReceiverPort() {return receiverPort;}
	
	public HashMap<Integer, String> getDatabase(){return database;}
	public HashMap<Integer, Integer> getHand(){return hand;}
	
	
	//public int getSenderOrder() { return this.senderOrder; }
	
	
	public String toString() {
		return ("Sender " + senderID + " says: " + msg); 
	}
	
}
