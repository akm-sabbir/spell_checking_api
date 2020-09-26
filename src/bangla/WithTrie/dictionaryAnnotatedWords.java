package bangla.WithTrie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import bangla.spellchecker.word_dto;

public class dictionaryAnnotatedWords extends dictionary {

	public dictionaryAnnotatedWords() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}
	
}
