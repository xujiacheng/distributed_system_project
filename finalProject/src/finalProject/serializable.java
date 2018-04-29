import java.io.Serializable;
import java.util.*;

public class serializable implements Serializable{
	
	//Serializable object
	private static final long serialVersionUID = 1L;
	
	private String name, msg, ip; //msg: set claim etc. 
	private int[] diceFaces, bid; //bid: [x, y] (#, dice face) 
	private int type, id, port, wins; //id: change every game, to keep the order of making bids. 
								// wins: how many times one have won
	//game record 
	
	public serializable(String name, int port, String ip)
	{
		this.type = null;
		this.name = name;
		this.port = port;
		this.ip = ip;
		this.msg = null;
		this.diceFaces = new int[] {0,0,0,0,0};
		this.bid = null;
		this.id = -1;
		this.wins = 0;
	}

	public void typeone(int[] bid){       //make bids
		this.type = 1;
		this.bid = bid;
	}

	public void typetwo(){            //challenge
		this.type = 2;
	}
	
	public void typethree(int[] diceFaces){
		this.diceFaces = diceFaces;
	}
	//setters 
	//public void setIP(String ip) { this.ip = ip;}
	//public void setMsg(String msg) { this.msg = msg;}
	//public void setBid(int[] bid) { this.bid = bid;}
	//public void win() { this.wins++;}
	
	
	//getters 
	public String getIP() { return this.ip;}
	public String getName() {return this.name;}
	public int getID() { return this.id; }
	public String getMsg() { return this.msg;}
	public int[] getBid() { return this.bid;}	//bid[#, diceface]
	public int[] getDices() { return this.diceFaces;}
	public int getWins() { return this.wins;}
	
	

}
