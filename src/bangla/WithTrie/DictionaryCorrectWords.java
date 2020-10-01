package bangla.WithTrie;


import java.util.HashMap;

public class DictionaryCorrectWords extends Dictionary{
	public DictionaryCorrectWords() {
		dict = new TrieNodeWithList();
		inverseDict = new HashMap<Long, String>();
	}
	
	public TrieNodeWithList getDictionary() {
		return this.dict;
	}

}
