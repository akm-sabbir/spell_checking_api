package bangla.WithTrie;

import java.util.HashMap;


public class DictionaryNaturalError extends dictionary{
	
	public DictionaryNaturalError() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}
	
}
