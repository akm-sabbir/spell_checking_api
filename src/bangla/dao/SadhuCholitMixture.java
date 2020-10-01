package bangla.dao;



import org.apache.log4j.Logger;

import bangla.WithTrie.TrieNodeWithList;
import bangla.grammarchecker.ShadhuCholitMixureChecker;
import bangla.grammarchecker.SubVerbRelErrorChecker;
import bangla.spellchecker.SpellCheckingDto;
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
	public DictionaryAnnotatedWords root;
	public static final String tableName="annotated_word";
	
	private SadhuCholitMixture(){
		root = new DictionaryAnnotatedWords();
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
		List<SpellCheckingDto> data_DTO = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		SpellCheckingDto  sp_dto= null;
		try{
			//System.out.println(sql);
			//logger.debug("sql " + sql);
			//Class.forName("com.mysql.jdbc.Driver");
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			stmt.setQueryTimeout(20);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				sp_dto = new SpellCheckingDto();
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
	public void insertSadhuCholitPronoun(String sql, List<String> columns){
		List<SpellCheckingDto> data_DTO = new ArrayList<>();
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		SpellCheckingDto  sp_dto= null;
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			stmt.setQueryTimeout(20);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				sp_dto = new SpellCheckingDto();
				// as column 0 is pronoun which is shadhu so we put it into wordType
				sp_dto.wordType = rs.getString(columns.get(0));// column names should be explicit
				// as column 1 is vice_versa which is cholito so we put it into word
				sp_dto.word = rs.getString(columns.get(1));
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
		return ;
	}
	@Override
	public void reload(boolean realoadAll) {
		String sql = "SELECT cholit_word, sadhu_word ";
		sql += " FROM verb_list " ;
		String[] columns = {"cholit_word", "sadhu_word"};
		List<String> column_ = Arrays.asList(columns);
		insert(sql, column_);
	
		sql = "SELECT pronoun, vice_versa ";
		sql += " FROM purus " ;
		String[] columns1 = {"pronoun", "vice_versa"};
		column_ = Arrays.asList(columns1);
		insertSadhuCholitPronoun(sql,column_);
	}
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		
		return tableName;
	}

}

