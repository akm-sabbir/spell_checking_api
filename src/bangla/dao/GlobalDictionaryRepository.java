package bangla.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import bangla.WithTrie.AhoCoharisck;
import bangla.WithTrie.Dictionary;
import bangla.WithTrie.DictionaryAnnotatedWords;
import bangla.WithTrie.TrieNodeWithList;
import bangla.grammarchecker.NoSpaceBetweenWordsChecker;
import bangla.grammarchecker.SpaceErrorBetweenWordsChecker;
import dbm.DBMR;
import repository.Repository;
import repository.RepositoryManager;

public class GlobalDictionaryRepository implements Repository{
	
	static Logger logger = Logger.getLogger(AnnotatedWordRepository.class);
	static GlobalDictionaryRepository instance = null;
	public Dictionary root = null;
	public AhoCoharisck ahoGlobal= null; 
	public static final String tableName="global_dictionary_word";
	
	private GlobalDictionaryRepository() {
		
		root = new Dictionary();
		ahoGlobal = new AhoCoharisck();
		RepositoryManager.getInstance().addRepository(this);
		//new AhoCoharisck();
	}
	
	
	
	public static GlobalDictionaryRepository getInstance(){
		if (instance == null){
			synchronized (tableName) {
				if(instance == null)
					instance = new GlobalDictionaryRepository();
			}
			
		}
		return instance;
	}
	public void addToGlobalDictionary(String content, int wordID) {
		ahoGlobal.initiateGlobalDict(content, wordID);
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
		ahoGlobal.PrepareAho();
	}
	

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		
		return tableName;
	}
}
