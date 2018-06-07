
import java.net.*;
import java.io.*;
import java.util.*;

public class latencytest {

	public static void main(String[] args) throws Exception {
		try {
			
			delayServer s1 = new delayServer();
			
			//delayServer.main(null);
			String [] t = {""+1};
			peer.main(t);
			peer.main(t);
			
			/*
			ArrayList <  String [] > list = new ArrayList< >();
			for (int i = 1; i <= 4; i++) {
				String [] s = new String[1];
				s[0] = ""+i;
				list.add( s );
				peer.main( list.get(i));
			}
			*/
			
		}
		catch (Exception e) {
			
		}
	}

}
