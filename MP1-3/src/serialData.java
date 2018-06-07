import java.io.*;

public class serialData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String msg;
	private int senderID; 
	private int recvID; 
	private String senderIP;
	private String receiverIP;
	
	
	public serialData(String msg, int senderID, int recvID) {
		this.msg = msg; 
		this.senderID = senderID;
		this.recvID = recvID; 
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
