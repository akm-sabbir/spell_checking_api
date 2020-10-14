package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dbm.DBMR;
import dbm.DBMW;

public class TestinDAO  
{
	
	public static Map<Object, Object> get_content(long id) throws Exception
	{
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
//		List<Object> list = new LinkedList<Object>();
		
		Map<Object, Object> m = new LinkedHashMap<Object, Object>();
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select distinct original_content, corrected_content from test_in where id = " + id + " ");
            
//			while(rs.next())
//			{
//				m = new LinkedHashMap<Object, Object>();	
//				m.put("DP", rs.getInt("DP"));
//				m.put("DR", rs.getInt("DR"));
//				m.put("CP", rs.getInt("CP"));
//				m.put("CR", rs.getInt("CR"));
//				m.put("complexity", rs.getInt("complexity"));
//				m.put("wordErrorType", rs.getInt("word_error_type"));
//				
//				list.add(m);
//			}
			
			if(rs.next())
			{
				m = new LinkedHashMap<Object, Object>();	
				m.put("originalContent", rs.getString("original_content"));
				m.put("correctedContent", rs.getString("corrected_content"));
			}            
            
			map.put("content", m);
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return map;		
	}	
	
	public TestinDTO getTestin(int id) throws Exception
	{
		
		TestinDTO testin_dto = null;
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM test_in WHERE ID = " + id);
            
			if(rs.next())
			{
				testin_dto = new TestinDTO();

				testin_dto.id = rs.getLong("ID");
				testin_dto.originalContent = rs.getString("original_content");
				testin_dto.correctedContent = rs.getString("corrected_content");
				testin_dto.complexity = rs.getString("complexity");
			}
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return testin_dto;		
	}
	
	public List<TestinDTO> get_paginated_Testin(int page_no, int page_size, String complexity) throws Exception
	{
		
		TestinDTO testin_dto = null;
		List<TestinDTO> testin_dto_list = new ArrayList<TestinDTO>(); 
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            
            int offset = page_no * page_size;
            int row_count = page_size;
            
            
//            ResultSet rs = stmt.executeQuery("SELECT * FROM test_in limit " + offset + " , " + row_count);
            
            ResultSet rs = null;
            
            if(!complexity.equalsIgnoreCase("all"))
            {
            	String[] sa = complexity.split("[ ,]");
            	
            	String filter = "";
            	
            	for(int i = 0 ; i < sa.length; i++)
            	{
            		filter += "'" + sa[i] + "'";
            		
            		if(i < sa.length -1)
            			filter += " , ";
            	}
            	
            	rs = stmt.executeQuery("SELECT * FROM test_in where complexity in (" + filter + ") limit " + offset + " , " + row_count);
            }
            else
            	rs = stmt.executeQuery("SELECT * FROM test_in limit " + offset + " , " + row_count);
//            	rs = stmt.executeQuery("SELECT * FROM test_in where ID = 19 limit " + offset + " , " + row_count);
            
			while(rs.next())
			{
				testin_dto = new TestinDTO();

				testin_dto.id = rs.getLong("ID");
				testin_dto.originalContent = rs.getString("original_content");
				testin_dto.correctedContent = rs.getString("corrected_content");
				testin_dto.complexity = rs.getString("complexity");
				
				testin_dto_list.add(testin_dto);
			}
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return testin_dto_list;		
	}	
	
	public List<TestinDTO> get_paginated_Testin(int page_no, int page_size) throws Exception
	{
		
		TestinDTO testin_dto = null;
		List<TestinDTO> testin_dto_list = new ArrayList<TestinDTO>(); 
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            
            int offset = page_no * page_size;
            int row_count = page_size;
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM test_in limit " + offset + " , " + row_count);
//            ResultSet rs = stmt.executeQuery("SELECT * FROM test_in where id=4 limit " + offset + " , " + row_count);
//            ResultSet rs = stmt.executeQuery("SELECT * FROM test_in where id=146 limit " + offset + " , " + row_count);
            
			while(rs.next())
			{
				testin_dto = new TestinDTO();

				testin_dto.id = rs.getLong("ID");
				testin_dto.originalContent = rs.getString("original_content");
				testin_dto.correctedContent = rs.getString("corrected_content");
				testin_dto.complexity = rs.getString("complexity");
				
				testin_dto_list.add(testin_dto);
			}
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return testin_dto_list;		
	}	
	
	public List<TestinDTO> get_all_Testin() throws Exception
	{
		
		TestinDTO testin_dto = null;
		List<TestinDTO> testin_dto_list = new ArrayList<TestinDTO>(); 
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM test_in");
            
			while(rs.next())
			{
				testin_dto = new TestinDTO();

				testin_dto.id = rs.getLong("ID");
				testin_dto.originalContent = rs.getString("original_content");
				testin_dto.correctedContent = rs.getString("corrected_content");
				testin_dto.complexity = rs.getString("complexity");
				
				testin_dto_list.add(testin_dto);
			}
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return testin_dto_list;		
	}	
	
}
	