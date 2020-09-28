package bangla.dao;



import org.apache.log4j.Logger;

import bangla.WithTrie.TrieNodeWithList;
import bangla.grammarchecker.ShadhuCholitMixureChecker;
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

public class SadhuCholitMixture implements Repository{
	
	static Logger logger = Logger.getLogger(SadhuCholitMixture.class);
	static SadhuCholitMixture instance = null;
	public dictionaryAnnotatedWords root;
	public static final String tableName="annotated_word";
	
	private SadhuCholitMixture(){
		root = new dictionaryAnnotatedWords();
		RepositoryManager.getInstance().addRepository(this);
	}
	
	public static SadhuCholitMixture getInstance(){
		if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new SadhuCholitMixture();
			}
			
		}
		return instance;
	}
	
	
	public  void insert(long ID, String word) {
		
	
		//ValidateSubVerbRelError.buildSubVerbMap(subVerbMap);		
		return;
	}
	public void insert(String sql, List<String> columns){
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
		ShadhuCholitMixureChecker.buildTrie(data_DTO);
		SubVerbRelErrorChecker.buildTrie(data_DTO);
		return ;
	}
	@Override
	public void reload(boolean realoadAll) {
		String sql = "SELECT cholit_word, sadhu_word ";
		sql += " FROM verb_list " ;
		String[] columns = {"cholit_word", "sadhu_word"};
		List<String> column_ = Arrays.asList(columns);
		insert(sql, column_);
	
	}
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		
		return tableName;
	}

}
