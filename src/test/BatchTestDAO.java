package test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbm.DBMW;

public class BatchTestDAO {
	public void insert(BatchTestDTO dto) throws Exception
	{
		try 
	    {
//	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
//	        						"INSERT INTO test_out(content_id, detailed_log, word_error_type, detection_precision, detection_recall, correction_precision, correction_recall, request_time, execution_time, word_count) "
//	        					  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	        PreparedStatement ps = DBMW.getInstance().getConnection().prepareStatement(
					"INSERT INTO batch_test_out(detection_precision, detection_recall, correction_precision, correction_recall, guid, complexity, word_error_type) "
				  + "VALUES (?, ?, ?, ?, ?, ?, ?)");	    	
	    	
	        ps.setFloat(1, dto.detectionPrecesion);
	        ps.setFloat(2, dto.detectionRecall);
	        ps.setFloat(3, dto.correctionPrecision);
	        ps.setFloat(4, dto.correctionRecall);
	        ps.setString(5, dto.guid);
	        ps.setString(6, dto.complexity);
	        ps.setString(7,  dto.wordErrorType);
	        
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
}
