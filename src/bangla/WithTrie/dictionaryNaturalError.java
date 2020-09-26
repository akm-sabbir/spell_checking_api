package bangla.WithTrie;

import java.util.HashMap;


public class dictionaryNaturalError extends dictionary{
	
	public dictionaryNaturalError() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}
	
}
