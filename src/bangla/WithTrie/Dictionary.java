package bangla.WithTrie;


import java.util.HashMap;

public class Dictionary {
	public TrieNodeWithList dict;	
	public HashMap<Long, String> inverseDict;
	
	public Dictionary() {
	
	}
	public TrieNodeWithList getDictionary() {return this.dict;}
	
}
