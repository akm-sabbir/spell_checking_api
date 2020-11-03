package bangla.dao;



import org.apache.log4j.Logger;

import bangla.WithTrie.TrieNodeWithList;
import bangla.grammarchecker.NirdeshokErrorChecker;
import bangla.grammarchecker.NoSpaceBetweenWordsChecker;
import bangla.grammarchecker.SpaceErrorBetweenWordsChecker;
import bangla.grammarchecker.SubVerbRelErrorChecker;
import dbm.DBMR;
import repository.Repository;
import repository.RepositoryManager;
import bangla.WithTrie.*;
import java.sql.*;

public class NamedEntityRepository implements Repository{
	
	static Logger logger = Logger.getLogger(NamedEntityRepository.class);
	static NamedEntityRepository instance = null;
	public DictionaryNamedEntity root = null;
	public static final String tableName="named_entity";
	
	private NamedEntityRepository(){
		root = new DictionaryNamedEntity();
		RepositoryManager.getInstance().addRepository(this);
	}
	
	public static NamedEntityRepository getInstance(){
		if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new NamedEntityRepository();
			}
			
		}
		return instance;
	}
	
	
	public  void insert(long ID, String word,int category) {
		
	
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
				temp.namedEntityCategory = category;
			
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
		String sql = "select ID, content, type_cat from named_entity where isDeleted=0";
		try{
			connection = DBMR.getInstance().getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				this.insert(rs.getLong("ID"), rs.getString("content"), rs.getInt("type_cat"));
			}				
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{ if (stmt != null) {stmt.close();}} catch (Exception e){}
			try{ if (connection != null){ DBMR.getInstance().freeConnection(connection); } }catch(Exception ex2){}
		}
		NoSpaceBetweenWordsChecker.registerDictionary(root.dict);
		SpaceErrorBetweenWordsChecker.registerDictionary(root.dict);
		SubVerbRelErrorChecker.addNamedEntity(root.dict);
		NirdeshokErrorChecker.registerDictionary(root.dict);
	}
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		
		return tableName;
	}

}
