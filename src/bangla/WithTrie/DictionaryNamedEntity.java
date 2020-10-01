package bangla.WithTrie;

import java.util.HashMap;

public class DictionaryNamedEntity extends Dictionary{

	public DictionaryNamedEntity() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}

}
