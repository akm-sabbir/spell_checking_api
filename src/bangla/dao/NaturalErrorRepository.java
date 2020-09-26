package bangla.dao;



import org.apache.log4j.Logger;

import com.jhlabs.image.DitherFilter;

import bangla.WithTrie.TrieNodeWithList;
import dbm.DBMR;
import repository.Repository;
import repository.RepositoryManager;
import bangla.WithTrie.*;
import java.sql.*;
import java.util.HashMap;

public class NaturalErrorRepository implements Repository{
	
	static Logger logger = Logger.getLogger(NaturalErrorRepository.class);
	static NaturalErrorRepository instance = null;
	public dictionaryNaturalError root;
	public static final String tableName="natural_error_word";
	private final static int DICTIONARY_WORD = 1;
	private final static int NAMED_ENTITY = 2;
	private final static int ANNOTATED_WORD = 3;
	private static WordSuggestionV3 wordSuggestionGenerator;

	public HashMap<String, String> errorToCorrect ;
	
	private NaturalErrorRepository(){
		
		root = new dictionaryNaturalError();
		errorToCorrect = new HashMap();
		wordSuggestionGenerator = new WordSuggestionV3();
		DictionaryRepository.getInstance().reload(true);
		AnnotatedWordRepository.getInstance().reload(true);
		NamedEntityRepository.getInstance().reload(true);
		RepositoryManager.getInstance().addRepository(this);
	}
	
	public static NaturalErrorRepository getInstance(){
		if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new NaturalErrorRepository();
			}
			
		}
		return instance;
	}
	
	
	public  void insert(long ID, String word) {
		
	
		TrieNodeWithList recurseRoot = root.dict;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNodeWithList temp=null;
			boolean isFound = false;
			for(TrieNodeWithList node: recurseRoot.children) {
				if(node.c==c) {
					temp = node;
					isFound = true;
					break;
				}
			}
			if(!isFound) {
				temp = new TrieNodeWithList(c);
				temp.parent = recurseRoot;
				temp.isWord= false;
				recurseRoot.children.add(temp);
			}
			if(i==word.length() - 1) {
				temp.isWord = true;
				temp.createTime = System.currentTimeMillis();
				this.root.inverseDict.put(ID, word);
			
			}
			recurseRoot = temp;
		}
		return;
	}
	
	public boolean loadInverseDictionary(String word, int reference_table, long word_reference) {
		
		if (reference_table == DICTIONARY_WORD) {
			if(DictionaryRepository.getInstance().root.inverseDict.containsKey(word_reference) == true)
				this.errorToCorrect.put(word, DictionaryRepository.getInstance().root.inverseDict.get(word_reference));
			return true;
		}else if(reference_table == ANNOTATED_WORD) {
			if(AnnotatedWordRepository.getInstance().root.inverseDict.containsKey(word_reference) == true)
				this.errorToCorrect.put(word, AnnotatedWordRepository.getInstance().root.inverseDict.get(word_reference));
			return true;
		}else if(reference_table == NAMED_ENTITY) {
			if(NamedEntityRepository.getInstance().root.inverseDict.containsKey(word_reference) == true)
				this.errorToCorrect.put(word, NamedEntityRepository.getInstance().root.inverseDict.get(word_reference));
			return true;
		}
		return false;
	}
	@Override
	public void reload(boolean realoadAll) {

		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sql = "select ID, content, reference_table, reference_id from natural_error_word where isDeleted=0";
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				this.insert(rs.getLong("ID"), rs.getString("content"));
				this.loadInverseDictionary(rs.getString("content"), rs.getInt("reference_table"), rs.getLong("reference_id"));
				// we have to load the errorToCorrect hashmap

			}				
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ if (stmt != null) {stmt.close();}} catch (Exception e){}
			try{ if (connection != null){ DBMR.getInstance().freeConnection(connection); } }catch(Exception ex2){}
		}
		
	}
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		
		return tableName;
	}

}
