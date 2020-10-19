package bangla.grammarchecker.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


import dbm.DBMW;

public class GcTestDao {
	
	public static Map<String,String> getAllErrorSentence() {
		Map<String,String> errorSentences = new HashMap<>();
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		try{
			connection = DBMW.getInstance().getConnection();
			stmt = connection.createStatement();
			stmt.setQueryTimeout(20);
			rs = stmt.executeQuery("SELECT * FROM error_sentence");
			while(rs.next()){
				String sentence = rs.getString("content");
				String type = rs.getString("error_type");
				errorSentences.put(sentence, type);
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
				if (connection != null){ 
					DBMW.getInstance().freeConnection(connection); 
				} 
			}catch(Exception ex2){}
		}
		return errorSentences;
	}

}
