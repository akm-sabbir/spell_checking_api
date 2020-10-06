package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
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
					"INSERT INTO test_out(content_id, detailed_log, word_error_type, detection_precision, detection_recall, correction_precision, correction_recall, request_time, execution_time, word_count, guid, complexity) "
				  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");	

//	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
//					"INSERT INTO test_out_iftekhar(content_id, detailed_log, word_error_type, detection_precision, detection_recall, correction_precision, correction_recall, request_time, execution_time, word_count, guid) "
//				  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");	    	
	    	
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
	        ps.setString(12, testout_dto.complexity);
	        
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
	
//	public void updateTestout(TestoutDTO testout_dto) throws Exception
//	{
//	    try 
//	    {
//	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
//	        						"update test_out set "
//	        						+ " detailed_log = ?, "
//	        						+ " word_error_type = ?, "
//	        						+ " detection_precision = ?, "
//	        						+ " detection_recall = ?, "
//	        						+ " correction_precision = ?, "
//	        						+ " correction_recall = ? "
//	        						+ " request_time = ? "
//	        						+ " execution_time = ? "
//	        						+ " word_count = ? "
//	        						+ " where content_id = ?");
//	        
//	        ps.setString(1, testout_dto.detailedLog);
//	        ps.setString(2, testout_dto.wordErrorType);
//	        ps.setFloat(3, testout_dto.detectionPrecision);
//	        ps.setFloat(4, testout_dto.detectionRecall);
//	        ps.setFloat(5, testout_dto.correctionPrecision);
//	        ps.setFloat(6, testout_dto.correctionRecall);
//	        ps.setLong(7, testout_dto.requestTime);
//	        ps.setLong(8, testout_dto.executionTime);
//	        ps.setLong(9, testout_dto.wordCount);
//	        ps.setLong(10, testout_dto.contentId);
//	        
//	        ps.executeUpdate();
//	    } 
//	    catch (SQLException ex) 
//	    {
//	        ex.printStackTrace();
//	    }		
//	}	
	
}
	