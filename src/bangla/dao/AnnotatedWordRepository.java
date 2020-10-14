package bangla.dao;



import org.apache.log4j.Logger;

import bangla.WithTrie.TrieNodeWithList;
import dbm.DBMR;
import repository.Repository;
import repository.RepositoryManager;
import bangla.WithTrie.*;
import java.sql.*;

public class AnnotatedWordRepository implements Repository{
	
	static Logger logger = Logger.getLogger(AnnotatedWordRepository.class);
	static AnnotatedWordRepository instance = null;
	public DictionaryAnnotatedWords root = null;
	public static final String tableName="annotated_word";
	
	private AnnotatedWordRepository(){
		root = new DictionaryAnnotatedWords();
		RepositoryManager.getInstance().addRepository(this);
	}
	
	public static AnnotatedWordRepository getInstance(){
		if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new AnnotatedWordRepository();
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
	public void reload(boolean realoadAll) {
		
		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sql = "select ID, content from annotated_word where isDeleted = 0";
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				this.insert(rs.getLong("ID"), rs.getString("content"));

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

