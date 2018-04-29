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
	
	public serializable(int room, String name, int port, String ip, String id)
	{
		this.type = null;
		this.name = name;
		this.port = port;
		this.ip = ip;
		this.msg = null;
		this.diceFaces = new int[] {0,0,0,0,0};
		this.bid = null;
		this.id = id;
		this.room = room;
	}

	public void bids(int[] bid){       //type1: make bids
		this.type = 1;
		this.bid = bid;
	}

	public void challenge(){            //type2: challenge
		this.type = 2;
	}
	
	public void hands(int[] diceFaces){     //type3: reveal hands
		this.type = 3;
		this.diceFaces = diceFaces;
	}

	public void join(){     //type4: request to join game
		this.type = 4;
	}

	public void quit(){     //type5: request to quit room
		this.type = 5;
	}

	public void chat(String msg){
		this.msg = msg;
	}

	
	//getters 
	public int getRoom() { return this.room; }
	public int getType() { return this.type; }
	public String getIP() { return this.ip;}
	public String getName() {return this.name;}
	public int getID() { return this.id; }
	public String getMsg() { return this.msg;}
	public int[] getBid() { return this.bid;}	//bid[#, diceface]
	public int[] getDices() { return this.diceFaces;}
	
	

}
