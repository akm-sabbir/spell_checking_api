package word_content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


import org.apache.log4j.Logger;

import dbm.*;

import repository.RepositoryManager;

import util.*;
import pb.*;

public class Word_contentDAO  
{
	
	Logger logger = Logger.getLogger(getClass());

	
	public Word_contentDAO(String tableName, String tempTableName)
	{
		//super(tableName, tempTableName, approval_module_mapDTO);		
	}
	
	public Word_contentDAO()
	{
				
	}
	
	public long add(CommonDTO commonDTO, String tableName, TempTableDTO tempTableDTO) throws Exception
	{
		
		Word_contentDTO word_contentDTO = (Word_contentDTO)commonDTO;
		
		Connection connection = null;
		PreparedStatement ps = null;

		long lastModificationTime = System.currentTimeMillis();	

		try{
			connection = DBMW.getInstance().getConnection();
			
			if(connection == null)
			{
				System.out.println("nullconn");
			}

			word_contentDTO.iD = DBMW.getInstance().getNextSequenceId(tableName);

			String sql = "INSERT INTO " + tableName;
			
			sql += " (";
			sql += "ID";
			sql += ", ";
			sql += "word";
			sql += ", ";
			sql += "language";
			sql += ", ";
			sql += "lastModificationTime";
			if(tempTableDTO!=null)
			{
				sql += ", permanent_table_id, operation_type, approval_path_type, approval_order";
			}
			sql += ")";
			
			
            sql += " VALUES(";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			if(tempTableDTO!=null)
			{
				sql += ", " + tempTableDTO.permanent_table_id + ", " + tempTableDTO.operation_type + ", " + tempTableDTO.approval_path_type + ", " + tempTableDTO.approval_order;
			}
			sql += ")";
				

			ps = connection.prepareStatement(sql);
			
			
			

			int index = 1;

			ps.setObject(index++,word_contentDTO.iD);
			ps.setObject(index++,word_contentDTO.word);
			ps.setObject(index++,word_contentDTO.language);
			ps.setObject(index++, lastModificationTime);
			
			System.out.println(ps);
			ps.execute();
			
			
			//recordUpdateTime(connection, ps, lastModificationTime, tableName);

		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if (ps != null) {
					ps.close();
				}
			} catch(Exception e){}
			try{
				if(connection != null)
				{
					DBMW.getInstance().freeConnection(connection);
				}
			}catch(Exception ex2){}
		}
		return word_contentDTO.iD;		
	}
		
	

	//need another getter for repository
	public CommonDTO getDTOByID (long ID, String tableName) throws Exception
	{
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		Word_contentDTO word_contentDTO = null;
		try{
			
			String sql = "SELECT * ";

			sql += " FROM " + tableName;
			
            sql += " WHERE ID=" + ID;
			
			//printSql(sql);
			
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();


			rs = stmt.executeQuery(sql);

			if(rs.next()){
				word_contentDTO = new Word_contentDTO();

				word_contentDTO.iD = rs.getLong("ID");
				word_contentDTO.word = rs.getString("word");
				word_contentDTO.language = rs.getString("language");
				word_contentDTO.lastModificationTime = rs.getLong("lastModificationTime");

			}			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ 
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e){}
			
			try{ 
				if (connection != null)
				{
					DBMR.getInstance().freeConnection(connection);
				} 
			}catch(Exception ex2){}
		}
		return word_contentDTO;
	}
	
	public long update(CommonDTO commonDTO, String tableName) throws Exception
	{		
		Word_contentDTO word_contentDTO = (Word_contentDTO)commonDTO;
		
		Connection connection = null;
		PreparedStatement ps = null;

		long lastModificationTime = System.currentTimeMillis();	
		try{
			connection = DBMW.getInstance().getConnection();

			String sql = "UPDATE " + tableName;
			
			sql += " SET ";
			sql += "word=?";
			sql += ", ";
			sql += "language=?";
			sql += ", lastModificationTime = "	+ lastModificationTime + "";
            sql += " WHERE ID = " + word_contentDTO.iD;
			ps = connection.prepareStatement(sql);
			int index = 1;
			
			ps.setObject(index++,word_contentDTO.word);
			ps.setObject(index++,word_contentDTO.language);
			System.out.println(ps);
			ps.executeUpdate();
			


			
			//recordUpdateTime(connection, ps, lastModificationTime, tableName);

						
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if (ps != null) {
					ps.close();
				}
			} catch(Exception e){}
			try{
				if(connection != null)
				{
					DBMW.getInstance().freeConnection(connection);
				}
			}catch(Exception ex2){}
		}
		return word_contentDTO.iD;
	}
	
	public List<Word_contentDTO> getDTOs(Collection recordIDs)
	{
		//return getDTOs(recordIDs, tableName);
		return null;
	}
	
	public List<Word_contentDTO> getDTOs(Collection recordIDs, String tableName){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		Word_contentDTO word_contentDTO = null;
		List<Word_contentDTO> word_contentDTOList = new ArrayList<>();
		if(recordIDs.isEmpty()){
			return word_contentDTOList;
		}
		try{
			
			String sql = "SELECT * ";

			sql += " FROM " + tableName;
            
            sql += " WHERE ID IN ( ";

			for(int i = 0;i<recordIDs.size();i++){
				if(i!=0){
					sql+=",";
				}
				sql+=((ArrayList)recordIDs).get(i);
			}
			sql+=")  order by lastModificationTime desc";
			
			//printSql(sql);
			
			logger.debug("sql " + sql);
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();


			rs = stmt.executeQuery(sql);

			while(rs.next()){
				word_contentDTO = new Word_contentDTO();
				word_contentDTO.iD = rs.getLong("ID");
				word_contentDTO.word = rs.getString("word");
				word_contentDTO.language = rs.getString("language");
				word_contentDTO.lastModificationTime = rs.getLong("lastModificationTime");
				System.out.println("got this DTO: " + word_contentDTO);
				
				word_contentDTOList.add(word_contentDTO);

			}			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ 
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e){}
			
			try{ 
				if (connection != null)
				{ 
					DBMR.getInstance().freeConnection(connection);
				} 
			}catch(Exception ex2){}
		}
		return word_contentDTOList;
	
	}
	
	

	
	
	
	//add repository
	public List<Word_contentDTO> getAllWord_content (boolean isFirstReload)
    {
		List<Word_contentDTO> word_contentDTOList = new ArrayList<>();

		String sql = "SELECT * FROM word_content";
		sql += " WHERE ";
	

		if(isFirstReload){
			sql+=" isDeleted =  0";
		}
		if(!isFirstReload){
			sql+=" lastModificationTime >= " + RepositoryManager.lastModifyTime;		
		}
		sql += " order by word_content.lastModificationTime desc";
		//printSql(sql);
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			

			while(rs.next()){
				Word_contentDTO word_contentDTO = new Word_contentDTO();
				word_contentDTO.iD = rs.getLong("ID");
				word_contentDTO.word = rs.getString("word");
				word_contentDTO.language = rs.getString("language");
				word_contentDTO.lastModificationTime = rs.getLong("lastModificationTime");
				
				word_contentDTOList.add(word_contentDTO);
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ 
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e){}
			try{ 
				if (connection != null)
				{ 
					DBMR.getInstance().freeConnection(connection);
				} 
			}catch(Exception ex2){}
		}

		return word_contentDTOList;
    }
	
	public List<Word_contentDTO> getDTOs(Hashtable p_searchCriteria, int limit, int offset)
	{
		return getDTOs(p_searchCriteria, limit, offset, "", true, 0);
	}
	
	public List<Word_contentDTO> getDTOs(Hashtable p_searchCriteria, int limit, int offset, String tableName, boolean isPermanentTable, long userApprovalPathType)
	{
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		List<Word_contentDTO> word_contentDTOList = new ArrayList<>();

		try{
			
			//String sql = getSqlWithSearchCriteria(p_searchCriteria, limit, offset, null, tableName, isPermanentTable, userApprovalPathType);
			String sql=null;
			//printSql(sql);
			
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();


			rs = stmt.executeQuery(sql);

			while(rs.next()){
				Word_contentDTO word_contentDTO = new Word_contentDTO();
				word_contentDTO.iD = rs.getLong("ID");
				word_contentDTO.word = rs.getString("word");
				word_contentDTO.language = rs.getString("language");
				word_contentDTO.lastModificationTime = rs.getLong("lastModificationTime");
				
				word_contentDTOList.add(word_contentDTO);
			}					
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ 
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e){}
			
			try{ 
				if (connection != null)
				{ 
					DBMR.getInstance().freeConnection(connection);
				} 
			}catch(Exception ex2){}
		}
		return word_contentDTOList;
	
	}

		
	
	public String getSqlWithSearchCriteria(Hashtable p_searchCriteria, int limit, int offset, String invalid, String tableName, boolean isPermanentTable, long userApprovalPathType)
    {
		Word_contentMAPS maps = new Word_contentMAPS(tableName);
		String joinSQL = "";
		return "";//getSqlWithSearchCriteria(p_searchCriteria, limit, offset, invalid, tableName, isPermanentTable, userApprovalPathType, maps, joinSQL);
    }			
}
	