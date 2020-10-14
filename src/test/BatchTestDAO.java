package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dbm.DBMW;

public class BatchTestDAO {
	public void insert(BatchTestDTO dto) throws Exception
	{
		try 
	    {
	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
					"INSERT INTO batch_test_out(detection_precision, detection_recall, correction_precision, correction_recall, guid, version, complexity, word_error_type) "
				  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");	    	
	    	
	        ps.setFloat(1, dto.detectionPrecesion);
	        ps.setFloat(2, dto.detectionRecall);
	        ps.setFloat(3, dto.correctionPrecision);
	        ps.setFloat(4, dto.correctionRecall);
	        ps.setString(5, dto.guid);
	        ps.setString(6, dto.version);
	        ps.setString(7, dto.complexity);
	        ps.setString(8,  dto.wordErrorType);
	        
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
	
	public static Map<Object, Object> get_batch_statistics(String guid) throws Exception
	{
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
//		List<Object> list = new LinkedList<Object>();
		
		Map<Object, Object> m = new LinkedHashMap<Object, Object>();
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select detection_precision DP, detection_recall DR, correction_precision CP, correction_recall CR, complexity, word_error_type, version from batch_test_out where guid in ('" + guid + "') ");
            
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
				m.put("DP", rs.getFloat("DP"));
				m.put("DR", rs.getFloat("DR"));
				m.put("CP", rs.getFloat("CP"));
				m.put("CR", rs.getFloat("CR"));
				m.put("complexity", rs.getString("complexity"));
				m.put("wordErrorType", rs.getString("word_error_type"));
				m.put("version", rs.getString("version"));
			}            
            
			map.put("batchStatistics", m);
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return map;		
	}	
	
	public List<BatchTestDTO> get_paginated_BatchTest(int page_no, int page_size) throws Exception
	{
		
		BatchTestDTO batchtest_dto = null;
		List<BatchTestDTO> batchtest_dto_list = new ArrayList<>(); 
		
        try 
        {
            Statement stmt = DBMW.getInstance().getConnection().createStatement();
            
            int offset = page_no * page_size;
            int row_count = page_size;
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM batch_test_out limit " + offset + " , " + row_count);
            
			while(rs.next())
			{
				batchtest_dto = new BatchTestDTO();

				batchtest_dto.id = rs.getInt("ID");
				batchtest_dto.guid = rs.getString("guid");
				batchtest_dto.version = rs.getString("version");
				
				batchtest_dto.detectionPrecesion = rs.getFloat("detection_precision");
				batchtest_dto.detectionRecall = rs.getFloat("detection_recall");
				batchtest_dto.correctionPrecision = rs.getFloat("correction_precision");
				batchtest_dto.correctionRecall = rs.getFloat("correction_recall");
				
				batchtest_dto.complexity = rs.getString("complexity");
				batchtest_dto.wordErrorType = rs.getString("word_error_type");
				
				batchtest_dto_list.add(batchtest_dto);
			}
			
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }

        return batchtest_dto_list;		
	}	
}
