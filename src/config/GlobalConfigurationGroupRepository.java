package config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import dbm.*;

public class GlobalConfigurationGroupRepository {
	Logger logger = Logger.getLogger(getClass());
	private static GlobalConfigurationGroupRepository instance = null;
	private static HashMap<Integer, GlobalConfigGroupDTO> GlobalConfigGroupDTOByID;
	private GlobalConfigurationGroupRepository()
	{
		GlobalConfigGroupDTOByID = new HashMap<Integer, GlobalConfigGroupDTO>();
		reload();
	}
	public static GlobalConfigurationGroupRepository getInstance()
	{
		if(instance == null)
		{
			createInstance();
		}
		return instance;
	}
	private synchronized static void createInstance()
	{
		if(instance == null)
		{
			instance = new GlobalConfigurationGroupRepository();
		}
		
	}
	public synchronized Collection<GlobalConfigGroupDTO> getAllGroups()
	{		
		return GlobalConfigGroupDTOByID.values();
	}	
	public synchronized GlobalConfigGroupDTO getGlobalConfigGroupDTOByID(int configID)
	{
//		logger.debug("StateNameByStateID " + GlobalConfigGroupDTOByID);
		if(GlobalConfigGroupDTOByID.get(configID) == null)
		{
			GlobalConfigGroupDTO globalConfigGroupDTO = new GlobalConfigGroupDTO();
			return globalConfigGroupDTO;
		}
		return GlobalConfigGroupDTOByID.get(configID);
	}
	
	public void reload()
	{
		Connection connection = null;
		Statement statement = null;
		String sql = null;
		ResultSet resultSet = null;
		
		try{
			connection = DBMR.getInstance().getConnection();
			statement = connection.createStatement();
			
			sql = "select * from global_config_group";
			resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				GlobalConfigGroupDTO globalConfigGroupDTO = new GlobalConfigGroupDTO();
				globalConfigGroupDTO.ID = resultSet.getInt("ID");
				globalConfigGroupDTO.name = resultSet.getString("name");				
				GlobalConfigGroupDTOByID.put(globalConfigGroupDTO.ID, globalConfigGroupDTO);
			}
		}
		catch(Exception ex)
		{
			logger.fatal("",ex);
		}
		finally {
			try {if(statement != null){statement.close();}}catch(Exception e) {}			
			try{ if (connection != null){ DBMR.getInstance().freeConnection(connection); }}catch(Exception e) {}			
		}

		
	}
}
