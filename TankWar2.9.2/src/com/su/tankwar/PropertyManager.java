package com.su.tankwar;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
	// private the construct method in order to not new propertyManger, only just call the static method
	private PropertyManager(){};
	
	// using static and private to keep only one
	private static Properties properties = new Properties();
	// using static chunk to run 
	static{
		try {
			properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	};
	
	
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
}
