package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbm.DBMR;
import dbm.DBMW;

public class TestoutDAO  
{
	public void insertTestout(TestoutDTO testout_dto) throws Exception
	{
	    try 
	    {
	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
	        						"INSERT INTO test_out(content_id, detailed_log, word_error_type, detection_precision, detection_recall, correction_precision, correction_recall, request_time, execution_time) "
	        					  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        
	        ps.setLong(1, testout_dto.content_id);
	        ps.setString(2, testout_dto.detailed_log);
	        ps.setString(3, testout_dto.word_error_type);
	        ps.setFloat(4, testout_dto.detection_precision);
	        ps.setFloat(5, testout_dto.detection_recall);
	        ps.setFloat(6, testout_dto.correction_precision);
	        ps.setFloat(7, testout_dto.correction_recall);
	        ps.setLong(8, testout_dto.request_time);
	        ps.setLong(9, testout_dto.execution_time);
	        
	        ps.executeUpdate();
	    } 
	    catch (SQLException ex) 
	    {
	        ex.printStackTrace();
	    }		
	}
	
	public void updateTestout(TestoutDTO testout_dto) throws Exception
	{
	    try 
	    {
	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
	        						"update test_out set "
	        						+ " detailed_log = ?, "
	        						+ " word_error_type = ?, "
	        						+ " detection_precision = ?, "
	        						+ " detection_recall = ?, "
	        						+ " correction_precision = ?, "
	        						+ " correction_recall = ? "
	        						+ " request_time = ? "
	        						+ " execution_time = ? "
	        						+ " where content_id = ?");
	        
	        ps.setString(1, testout_dto.detailed_log);
	        ps.setString(2, testout_dto.word_error_type);
	        ps.setFloat(3, testout_dto.detection_precision);
	        ps.setFloat(4, testout_dto.detection_recall);
	        ps.setFloat(5, testout_dto.correction_precision);
	        ps.setFloat(6, testout_dto.correction_recall);
	        ps.setLong(7, testout_dto.request_time);
	        ps.setLong(8, testout_dto.execution_time);
	        ps.setLong(9, testout_dto.content_id);
	        
	        ps.executeUpdate();
	    } 
	    catch (SQLException ex) 
	    {
	        ex.printStackTrace();
	    }		
	}	
	
}
	