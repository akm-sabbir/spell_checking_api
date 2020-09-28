package bangla.dao;



import org.apache.log4j.Logger;

import bangla.WithTrie.TrieNodeWithList;
import bangla.grammarchecker.SubVerbRelErrorChecker;
import bangla.spellchecker.spell_checking_dto;
import dbm.DBMR;
import repository.Repository;
import repository.RepositoryManager;
import bangla.WithTrie.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class gradedPronoun implements Repository{
	
	static Logger logger = Logger.getLogger(gradedPronoun.class);
	static gradedPronoun instance = null;
	public dictionaryAnnotatedWords root;
	public static final String tableName="annotated_word";
	
	private gradedPronoun(){
		root = new dictionaryAnnotatedWords();
		RepositoryManager.getInstance().addRepository(this);
	}
	
	public static gradedPronoun getInstance(){
		if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new gradedPronoun();
			}
			
		}
		return instance;
	}
	
	
	public  void insert(long ID, String word) {
		
	
		//ValidateSubVerbRelError.buildSubVerbMap(subVerbMap);		
		return;
	}
	public List<spell_checking_dto> getSpellCheckingDTOs(String sql, List<String> columns){
		List<spell_checking_dto> data_DTO = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		spell_checking_dto  sp_dto= null;
		try{
			//System.out.println(sql);
			//logger.debug("sql " + sql);
			//Class.forName("com.mysql.jdbc.Driver");
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			stmt.setQueryTimeout(20);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				sp_dto = new spell_checking_dto();
				sp_dto.word = rs.getString(columns.get(0));// column names should be explicit
				sp_dto.wordType = rs.getString(columns.get(1));
				//wordDto.word_type = rs.getInt("service_id");
				//wordDto.lang_type = rs.getInt("service_type");
				//System.out.println("got this DTO: " + word_dto.word);
				data_DTO.add(sp_dto);

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
					DBMR.getInstance().freeConnection(connection); 
				} 
			}catch(Exception ex2){}
		}
		return data_DTO;
	}
	public List<spell_checking_dto> getPurusInfo(String sql, List<String> columns){
		List<spell_checking_dto> data_DTO = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		spell_checking_dto  sp_dto= null;
		try{
			//System.out.println(sql);
			//logger.debug("sql " + sql);
			//Class.forName("com.mysql.jdbc.Driver");
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			stmt.setQueryTimeout(20);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				sp_dto = new spell_checking_dto();
				sp_dto.word = rs.getString(columns.get(0));// column names should be explicit
				sp_dto.wordType = String.valueOf(rs.getInt(columns.get(1)));
				//wordDto.word_type = rs.getInt("service_id");
				//wordDto.lang_type = rs.getInt("service_type");
				//System.out.println("got this DTO: " + word_dto.word);
				data_DTO.add(sp_dto);

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
					DBMR.getInstance().freeConnection(connection); 
				} 
			}catch(Exception ex2){}
		}
		return data_DTO;
	}
	@Override
	public void reload(boolean realoadAll) {
		

		String sql = "SELECT pronoun, marker ";
		sql += " FROM purus " ;
		String[] columns2 = {"pronoun", "marker"};
		List<String> column_ = Arrays.asList(columns2);
		List<spell_checking_dto> purus = getPurusInfo(sql, column_);
		SubVerbRelErrorChecker.setPurushMap(purus);
		
		
		
	}
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		
		return tableName;
	}

}

