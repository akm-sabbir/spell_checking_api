package bangla.WithTrie;

import java.util.HashMap;

public class dictionaryNamedEntity extends dictionary{

	public dictionaryNamedEntity() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}

}
