
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.net.InetAddress;

public class setConfig{
  
	public static void main(String[] args) {
	
		Properties prop = new Properties();
		OutputStream output = null;

	try {

		output = new FileOutputStream("config.properties");

		// set the properties value
		//prop.setProperty("local_host", InetAddress.getLocalHost().getHostAddress());
		prop.setProperty("1", InetAddress.getLocalHost().getHostAddress());
		prop.setProperty("2", InetAddress.getLocalHost().getHostAddress());
		prop.setProperty("3", InetAddress.getLocalHost().getHostAddress());
		prop.setProperty("4", InetAddress.getLocalHost().getHostAddress());
		prop.setProperty("5", InetAddress.getLocalHost().getHostAddress());
		prop.setProperty("max_delay", "10000");
		prop.setProperty("min_delay", "5000");

		// save properties to project root folder
		prop.store(output, null);

	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
  }
}
