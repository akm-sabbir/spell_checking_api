package bangla.dao;



import org.apache.log4j.Logger;

import bangla.WithTrie.TrieNodeWithList;
import bangla.grammarchecker.NirdeshokErrorChecker;
import bangla.grammarchecker.NoSpaceBetweenWordsChecker;
import bangla.grammarchecker.SpaceErrorBetweenWordsChecker;
import dbm.DBMR;
import repository.Repository;
import repository.RepositoryManager;
import bangla.WithTrie.*;
import java.sql.*;

public class DictionaryRepository implements Repository{
	
	static Logger logger = Logger.getLogger(DictionaryRepository.class);
	static DictionaryRepository instance = null;
	public DictionaryCorrectWords root = null;
	public static final String tableName="dictionary_words";
	
	private DictionaryRepository(){
		root = new DictionaryCorrectWords();
		RepositoryManager.getInstance().addRepository(this);
	}
	
	public static DictionaryRepository getInstance(){
		if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new DictionaryRepository();
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
	
	@Override
	public void reload(boolean reloadAll) {
		
		logger.debug("DictionaryRepository.reload("+reloadAll+") Started");
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		GlobalDictionaryRepository repo =  GlobalDictionaryRepository.getInstance();
		String sql = "select ID, content from dictionary_words where isDeleted=0";
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				this.insert(rs.getLong("ID"), rs.getString("content"));
				repo.addToGlobalDictionary(rs.getString("content"), rs.getInt("ID"));

			}				
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ if (stmt != null) {stmt.close();}} catch (Exception e){}
			try{ if (connection != null){ DBMR.getInstance().freeConnection(connection); } }catch(Exception ex2){}
		}
		NoSpaceBetweenWordsChecker.registerDictionary(root.dict);
		SpaceErrorBetweenWordsChecker.registerDictionary(root.dict);
		NirdeshokErrorChecker.registerDictionary(root.dict);
	}
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		
		return tableName;
	}

}
