package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dbm.DBMR;
import dbm.DBMW;

public class TestoutDAO  
{
	public void insertTestout(TestoutDTO testout_dto) throws Exception
	{
	    try 
	    {
	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
					"INSERT INTO test_out(content_id, detailed_log, word_error_type, detection_precision, detection_recall, correction_precision, correction_recall, request_time, execution_time, word_count, guid, version, complexity) "
				  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");	

	        ps.setLong(1, testout_dto.contentId);
	        ps.setString(2, testout_dto.detailedLog);
	        ps.setString(3, testout_dto.wordErrorType);
	        ps.setFloat(4, testout_dto.detectionPrecision);
	        ps.setFloat(5, testout_dto.detectionRecall);
	        ps.setFloat(6, testout_dto.correctionPrecision);
	        ps.setFloat(7, testout_dto.correctionRecall);
	        ps.setLong(8, testout_dto.requestTime);
	        ps.setLong(9, testout_dto.executionTime);
	        ps.setLong(10, testout_dto.wordCount);
	        ps.setString(11, testout_dto.guid);
	        ps.setString(12, testout_dto.version);
	        ps.setString(13, testout_dto.complexity);
	        
	        ps.executeUpdate();
	    } 
	    catch (SQLException ex) 
	    {
	        ex.printStackTrace();
	    }		
	    finally
	    {
	    	//Close the connection here
	    }
	}
	
	public static Map<Object, Object> get_guid_list(long from, long to) throws Exception
	{
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		List<Object> list = new LinkedList<Object>();
		
		Map<Object, Object> m = null;
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select distinct guid, version from test_out where request_time >= " + from + " and request_time <= " + to + " ");
            
			while(rs.next())
			{
				m = new LinkedHashMap<Object, Object>();	
				m.put("guid", rs.getString("guid"));
				m.put("version", rs.getString("version"));
				
				list.add(m);
			}
			
			map.put("guidList", list);
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return map;		
	}	
	
	public static Map<Object, Object> get_id_list(String guid) throws Exception
	{
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		List<Object> list = new LinkedList<Object>();
		
		Map<Object, Object> m = null;
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select distinct content_id id, version, detection_precision DP, detection_recall DR, correction_precision CP, correction_recall CR from test_out where guid in ('" + guid + "') order by id asc ");
            
			while(rs.next())
			{
				m = new LinkedHashMap<Object, Object>();	
				m.put("id", rs.getInt("id"));
				m.put("version", rs.getString("version"));
				m.put("DP", String.valueOf(rs.getFloat("DP")));
				m.put("DR", String.valueOf(rs.getFloat("DR")));
				m.put("CP", String.valueOf(rs.getFloat("CP")));
				m.put("CR", String.valueOf(rs.getFloat("CR")));
				
//				JsonObject jsonObject = new JsonParser().parse(rs.getString("log")).getAsJsonObject();
//				m.put("log", jsonObject);
//				m.put("complexity", rs.getString("complexity"));

				list.add(m);
			}
			
			map.put("idList", list);
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return map;		
	}	
	
	
	public static Map<Object, Object> get_test_result(String guid, long id) throws Exception
	{
//		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
//		List<Object> list = new LinkedList<Object>();
		
		Map<Object, Object> m = new LinkedHashMap<Object, Object>();
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select distinct detailed_log log, version from test_out where content_id = " + id + " and guid in ('" + guid + "') ");
            
//			while(rs.next())
//			{
//				m = new LinkedHashMap<Object, Object>();	
//				m.put("log", rs.getInt("log"));
//
//				list.add(m);
//			}
			
			if(rs.next())
			{
				m = new LinkedHashMap<Object, Object>();	
				m.put("log", rs.getString("log"));
			}            
            
//			map.put("idList", list);
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
//        return map;
        return m;
	}	
}
	