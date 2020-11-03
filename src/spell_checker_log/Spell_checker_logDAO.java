package spell_checker_log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import org.apache.log4j.Logger;

import dbm.*;

import repository.RepositoryManager;

import util.*;
import pb.*;
import user.UserDTO;

public class Spell_checker_logDAO  
{
	
	Logger logger = Logger.getLogger(getClass());
	String tableName = "spell_checker_log";
	
	public Spell_checker_logDAO(String tableName)
	{
		//super(tableName);
		//commonMaps = new Spell_checker_logMAPS(tableName);
	}
	
	public Spell_checker_logDAO()
	{
		this("spell_checker_log");		
	}
	
	
	
	public long add(CommonDTO commonDTO) throws Exception
	{
		
		Spell_checker_logDTO spell_checker_logDTO = (Spell_checker_logDTO)commonDTO;
		
		Connection connection = null;
		PreparedStatement ps = null;

		long lastModificationTime = System.currentTimeMillis();	

		try{
			connection = DBMW.getInstance().getConnection();
			
			if(connection == null)
			{
				System.out.println("nullconn");
			}

			spell_checker_logDTO.iD = DBMW.getInstance().getNextSequenceId(tableName);

			String sql = "INSERT INTO " + tableName;
			
			sql += " (";
			sql += "ID";
			sql += ", ";
			sql += "content";
			sql += ", ";
			sql += "response";
			sql += ", ";
			sql += "unknown_word_count";
			sql += ", ";
			sql += "client_cat";
			sql += ", ";
			sql += "ip";
			sql += ", ";
			sql += "time";
			sql += ", ";
			sql += "session";
			sql += ", ";
			sql += "isDeleted";
			sql += ", ";
			sql += "lastModificationTime";
		
			sql += ")";
			
			
            sql += " VALUES(";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			sql += ", ";
			sql += "?";
			
			sql += ")";
				

			ps = connection.prepareStatement(sql);
			
			
			

			int index = 1;

			ps.setObject(index++,spell_checker_logDTO.iD);
			ps.setObject(index++,spell_checker_logDTO.content);
			ps.setObject(index++,spell_checker_logDTO.response);
			ps.setObject(index++,spell_checker_logDTO.unknownWordCount);
			ps.setObject(index++,spell_checker_logDTO.clientCat);
			ps.setObject(index++,spell_checker_logDTO.ip);
			ps.setObject(index++,spell_checker_logDTO.time);
			ps.setObject(index++,spell_checker_logDTO.session);
			ps.setObject(index++,spell_checker_logDTO.isDeleted);
			ps.setObject(index++, lastModificationTime);
			
			System.out.println(ps);
			ps.execute();
			
			
			recordUpdateTime(connection, ps, lastModificationTime);

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
		return spell_checker_logDTO.iD;		
	}
		
	

	//need another getter for repository
	public CommonDTO getDTOByID (long ID) throws Exception
	{
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		Spell_checker_logDTO spell_checker_logDTO = null;
		try{
			
			String sql = "SELECT * ";

			sql += " FROM " + tableName;
			
            sql += " WHERE ID=" + ID;
			
			//printSql(sql);
			
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();


			rs = stmt.executeQuery(sql);

			if(rs.next()){
				spell_checker_logDTO = new Spell_checker_logDTO();

				spell_checker_logDTO.iD = rs.getLong("ID");
				spell_checker_logDTO.content = rs.getString("content");
				spell_checker_logDTO.response = rs.getString("response");
				spell_checker_logDTO.unknownWordCount = rs.getInt("unknown_word_count");
				spell_checker_logDTO.clientCat = rs.getInt("client_cat");
				spell_checker_logDTO.ip = rs.getInt("ip");
				spell_checker_logDTO.time = rs.getLong("time");
				spell_checker_logDTO.session = rs.getString("session");
				spell_checker_logDTO.isDeleted = rs.getInt("isDeleted");
				spell_checker_logDTO.lastModificationTime = rs.getLong("lastModificationTime");

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
		return spell_checker_logDTO;
	}
	
	public void recordUpdateTime(Connection connection, PreparedStatement ps, long lastModificationTime) throws SQLException
	{
		String query = "UPDATE vbSequencer SET table_LastModificationTime=? WHERE table_name=?";
		ps = connection.prepareStatement(query);
		ps.setLong(1,lastModificationTime);
		ps.setString(2,tableName);
		ps.execute();
		ps.close();
	}
	
	public long update(CommonDTO commonDTO) throws Exception
	{		
		Spell_checker_logDTO spell_checker_logDTO = (Spell_checker_logDTO)commonDTO;
		
		Connection connection = null;
		PreparedStatement ps = null;

		long lastModificationTime = System.currentTimeMillis();	
		try{
			connection = DBMW.getInstance().getConnection();

			String sql = "UPDATE " + tableName;
			
			sql += " SET ";
			sql += "content=?";
			sql += ", ";
			sql += "response=?";
			sql += ", ";
			sql += "unknown_word_count=?";
			sql += ", ";
			sql += "client_cat=?";
			sql += ", ";
			sql += "ip=?";
			sql += ", ";
			sql += "time=?";
			sql += ", ";
			sql += "session=?";
			sql += ", lastModificationTime = "	+ lastModificationTime + "";
            sql += " WHERE ID = " + spell_checker_logDTO.iD;
				

			ps = connection.prepareStatement(sql);
			
			

			int index = 1;
			
			ps.setObject(index++,spell_checker_logDTO.content);
			ps.setObject(index++,spell_checker_logDTO.response);
			ps.setObject(index++,spell_checker_logDTO.unknownWordCount);
			ps.setObject(index++,spell_checker_logDTO.clientCat);
			ps.setObject(index++,spell_checker_logDTO.ip);
			ps.setObject(index++,spell_checker_logDTO.time);
			ps.setObject(index++,spell_checker_logDTO.session);
			System.out.println(ps);
			ps.executeUpdate();
			


			
			recordUpdateTime(connection, ps, lastModificationTime);

						
			
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
		return spell_checker_logDTO.iD;
	}
	
	
	public List<Spell_checker_logDTO> getDTOs(Collection recordIDs){
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		Spell_checker_logDTO spell_checker_logDTO = null;
		List<Spell_checker_logDTO> spell_checker_logDTOList = new ArrayList<>();
		if(recordIDs.isEmpty()){
			return spell_checker_logDTOList;
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
				spell_checker_logDTO = new Spell_checker_logDTO();
				spell_checker_logDTO.iD = rs.getLong("ID");
				spell_checker_logDTO.content = rs.getString("content");
				spell_checker_logDTO.response = rs.getString("response");
				spell_checker_logDTO.unknownWordCount = rs.getInt("unknown_word_count");
				spell_checker_logDTO.clientCat = rs.getInt("client_cat");
				spell_checker_logDTO.ip = rs.getInt("ip");
				spell_checker_logDTO.time = rs.getLong("time");
				spell_checker_logDTO.session = rs.getString("session");
				spell_checker_logDTO.isDeleted = rs.getInt("isDeleted");
				spell_checker_logDTO.lastModificationTime = rs.getLong("lastModificationTime");
				System.out.println("got this DTO: " + spell_checker_logDTO);
				
				spell_checker_logDTOList.add(spell_checker_logDTO);

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
		return spell_checker_logDTOList;
	
	}
	
	

	
	
	
	//add repository
	public List<Spell_checker_logDTO> getAllSpell_checker_log (boolean isFirstReload)
    {
		List<Spell_checker_logDTO> spell_checker_logDTOList = new ArrayList<>();

		String sql = "SELECT * FROM spell_checker_log";
		sql += " WHERE ";
	

		if(isFirstReload){
			sql+=" isDeleted =  0";
		}
		if(!isFirstReload){
			sql+=" lastModificationTime >= " + RepositoryManager.lastModifyTime;		
		}
		sql += " order by spell_checker_log.lastModificationTime desc";
		//printSql(sql);
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			

			while(rs.next()){
				Spell_checker_logDTO spell_checker_logDTO = new Spell_checker_logDTO();
				spell_checker_logDTO.iD = rs.getLong("ID");
				spell_checker_logDTO.content = rs.getString("content");
				spell_checker_logDTO.response = rs.getString("response");
				spell_checker_logDTO.unknownWordCount = rs.getInt("unknown_word_count");
				spell_checker_logDTO.clientCat = rs.getInt("client_cat");
				spell_checker_logDTO.ip = rs.getInt("ip");
				spell_checker_logDTO.time = rs.getLong("time");
				spell_checker_logDTO.session = rs.getString("session");
				spell_checker_logDTO.isDeleted = rs.getInt("isDeleted");
				spell_checker_logDTO.lastModificationTime = rs.getLong("lastModificationTime");
				
				spell_checker_logDTOList.add(spell_checker_logDTO);
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

		return spell_checker_logDTOList;
    }
	
					
}
	