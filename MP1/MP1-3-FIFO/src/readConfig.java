

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.Properties;
	 
public class readConfig {
	  OutputStream output = null;
	  private Properties configFile;
	  public readConfig(){
		  configFile = new java.util.Properties();
		  try {
			  configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
		  }catch(Exception eta){
		  eta.printStackTrace();
		  }
	  }
	 
	  public String getProperty(String key){
		  String value = this.configFile.getProperty(key);
		  return value;
	  }
	  
	  //public void setProperty(String key, String value) {
		 // this.configFile.setProperty(key, value);
		//  this.configFile.store(new FileOutputStream("config.properties"), null);
	 // }
}