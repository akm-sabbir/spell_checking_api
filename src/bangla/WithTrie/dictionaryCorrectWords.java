package bangla.WithTrie;


import java.util.HashMap;

public class dictionaryCorrectWords extends dictionary{
	public dictionaryCorrectWords() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}

}
