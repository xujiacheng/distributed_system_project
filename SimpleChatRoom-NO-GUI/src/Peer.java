import java.net.*;
import java.io.*;
import java.util.*;

public class Peer {
	public static void main(String args[]) throws Exception {
		peerSendThread PST;
		peerReceiveThread PRT;
		int PORT = Integer.parseInt(args[1]);
		DatagramSocket clientSocket = new DatagramSocket(); //for network
		
		
		peerUtilites peerU = new peerUtilites(args[0],PORT);
		peerU.setName();
		peerU.joinGroup();
		
		PST = new peerSendThread(peerU);
		Thread T = new Thread(PST);
		T.start();
		PRT = new peerReceiveThread(peerU);
		Thread TT = new Thread(PRT);
		TT.setDaemon(true);
		TT.start();
		T.join();
		peerU.leaveGroup();
	} // main
} // class PEER
