package com.exercise.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.model.LevelType;
import com.exercise.model.Store;
import com.exercise.model.Log;
import com.exercise.repository.ILogRepository;

@Service
public class JobLoggerImpl {
	
	@Autowired
	private ILogRepository logRepository;
	
	private static Logger logger = Logger.getLogger("BelatrixLogger");;
	private static Map<String, String> configParams;
	
	static {
		configParams = new HashMap<String,String>();
        configParams.put("serverName", "localhost");
        configParams.put("port", "3306");
        configParams.put("user", "root");
        configParams.put("password", "root");
        configParams.put("fileFolder", "./target");
	}
	
	public boolean Log(String message, LevelType type, Store store) {

		if(StringUtils.isBlank(message) || type==null || store==null) {
			logger.log(Level.WARNING,"Bad Log Params");
			return false;			
		}
		
		message.trim();
		
		if(type.equals(LevelType.SEVERE_AND_WARNING)){
			if(registerLog(store, message, Level.SEVERE)){
				return registerLog(store, message, Level.WARNING);
			}
			return false;
		}else {
			return registerLog(store, message, Level.parse(type.name()));
		}
		
	}

	
	private boolean registerLog(Store store, String message, Level type){
		if(store.equals(Store.console)){
			logger.addHandler(new ConsoleHandler());
			logger.log(type, message);
			return true;
		}else if(store.equals(Store.database)){

			Connection connection = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", configParams.get("user"));
			connectionProps.put("password", configParams.get("password"));
			try {
				connection = DriverManager.getConnection("jdbc:" + configParams.get("dbms") + "://" + configParams.get("serverName")
						+ ":" + configParams.get("port") + "/", connectionProps);
				Statement stmt = connection.createStatement();
				int ok = stmt.executeUpdate("insert into Log_Values('" + type.getName() + "', '" + new Date() + "', '" + message + "')");
				
				if(ok==0) {
					logger.log(Level.SEVERE,"Register unsaved");
					return false;
				}
				
				
			} catch (SQLException e) {
				logger.log(Level.SEVERE,e.getMessage());
				return false;
			}
			
//			logRepository.save(new Log(type,message)); ///uncomment to save in memory
			return true;
		}else {
			try {
				logger.addHandler(new FileHandler(configParams.get("fileFolder")+"/logs.txt",true)); /////agregar ruta de la carpeta
				logger.log(type, message);
				return true;
			} catch (SecurityException e) {
				logger.log(Level.SEVERE,e.getMessage());
				return false;
			} catch (IOException e) {
				logger.log(Level.SEVERE,e.getMessage());
				return false;
			}
		}
		
	}

}
