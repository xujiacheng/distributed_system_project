package finalProject;
import java.io.*;

public class playerInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int identifier = -1;
	private int port_num;
	private String IP;
	 
	public playerInfo(int inentifier, int port_num, String IP) {
		this.identifier = identifier;
		this.port_num = port_num;
		this.IP = IP;
	}
	
	public void setIdentifier(int i ) {
		identifier = i;
	}
	public int getIdentifier() { 
		return identifier;
	}
		
	
	public void setPortNum(int in) {
		port_num = in;
	}
	public int getPortNum() { return port_num;}
	
	public void setIP(String in) {
		IP = in;
	}
	public String getIP() {return IP;}
	
	public String toString() {
		return ("string of playerInfo"); 
	}
	
}
