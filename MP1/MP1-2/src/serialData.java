import java.io.*;

public class serialData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String msg;
	private int senderID; 
	private int recvID; 
	private String senderIP;
	private String receiverIP;
	private long sender_time;
	
	public serialData(String msg, int senderID, int recvID,long sender_time ) {
		this.msg = msg; 
		this.senderID = senderID;
		this.recvID = recvID; 
		this.sender_time = sender_time;
	}
	public long getSenderTime() {
		return sender_time;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public int getSenderID() {
		return this.senderID;
	}
	
	public int getRecvID() {
		return this.recvID; 
	}
	
	//get from config file. 
	
	public String getIP(int id) {
		readConfig tcp = new readConfig();
		return tcp.getProperty(""+id);
	}
	

	
	public String toString() {
		return ("Sender " + senderID + " says: " + msg); 
	}
	
}
