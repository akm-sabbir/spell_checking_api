package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import dbm.*;
/**
 * @author Kayesh Parvez
 *
 */
public class GlobalConfigDAO {
	Logger logger = Logger.getLogger(GlobalConfigDAO.class);
	
	public void updateGlobalConfiguration(ArrayList<GlobalConfigDTO> globalConfigurationDTOList)
	{
		Connection connection = null;
		PreparedStatement ps = null;

		try{
			connection = DBMW.getInstance().getConnection();

			String sql ="UPDATE global_config SET value=? WHERE ID = ?";

			ps = connection.prepareStatement(sql);
			
			for(GlobalConfigDTO globalConfigDTO: globalConfigurationDTOList)
			{				
				int index = 1;

				ps.setObject(index++,globalConfigDTO.value);
				ps.setObject(index++,globalConfigDTO.ID);
				
				ps.addBatch();
			}

			ps.executeBatch();
			
			GlobalConfigurationRepository.getInstance().reload(true);
			
		}catch(Exception ex){
			logger.fatal("",ex);
		}finally{
			try{
				if (ps != null) {
					ps.close();
				}
			} catch(Exception e){}
			try{
				if(connection != null){
					DBMW.getInstance().freeConnection(connection);
				}
			}catch(Exception ex2){}
		}	
	}
}
