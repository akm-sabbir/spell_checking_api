package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbm.DBMR;
import dbm.DBMW;

public class TestinDAO  
{
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
			}
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return testin_dto;		
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
	