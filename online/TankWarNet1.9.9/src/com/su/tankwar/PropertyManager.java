package com.su.tankwar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertyManager {

	// private the construct method in order to not new propertyManger, only just call the static method
	private PropertyManager(){
		
	}
	// using static and private to keep only one
	private static Properties properties = new Properties();
	private static Properties propertiesWrite = new Properties();

	// using static chunk to run 
	static{
		try {
			properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
			//FileOutputStream fileOutputStream = new FileOutputStream("tank.properties");
		    //propertiesWrite.store(new FileOutputStream(PropertyManager.class.getClassLoader().getResource("config/tank.properties")+""), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// get the property
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
	
	//set the property
	public static void setProperty(String key, String value){
		
		properties.setProperty(key, value);
		FileOutputStream fileOutputStream = null;
	    try {
	    	// get the file's URL
	    	URL url = PropertyManager.class.getClassLoader().getResource("config/tank.properties");
	    	// get the file's path
	    	String filePath = url.getFile();
	    	fileOutputStream = new FileOutputStream(filePath);
			properties.store(fileOutputStream, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (fileOutputStream != null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileOutputStream = null;
			}
			
		}

	}
}
