import java.net.*;
import java.io.*;
import java.util.*;


public class config {
	
	private static int counter; 
	private String IPAddress = "127.0.0.1";
	private HashMap<Integer,Integer> map = new HashMap<>();
	
	public config() {
		
	}
	
	public int getLatestCounter() {
		int result = counter;
		if (!map.containsKey(result)) {
			map.put(result,5000+result);
		}
		System.out.println("Your id is " + result + "; your port# is " + map.get(result));
		counter++;
		return result;
	}
	
	public int getPortNum(int id) {
		if (map.containsKey(id)) {
			return map.get(id);
		}
		else {
			System.out.println("please give me correct identifier");
			return -1;
		}
	}
	
	public String getIP() {
		return IPAddress; 
	}
	
	
	
	
	

}
