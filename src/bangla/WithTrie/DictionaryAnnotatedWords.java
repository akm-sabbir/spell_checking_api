package bangla.WithTrie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import bangla.spellchecker.WordDto;

public class DictionaryAnnotatedWords extends dictionary {

	public DictionaryAnnotatedWords() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}
	
}
