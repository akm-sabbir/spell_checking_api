package bangla.WithTrie;

import java.util.HashMap;
import java.util.Map;

public class TrieNodeSearchResult {
	
	public boolean isFound;
	public String viceVersaWord;
	public int position;
    public Map<String,String> additional = new HashMap<>();
	
	public TrieNodeSearchResult(boolean isFound, String viceVersaWord,int position,Map<String,String> additional) {
		this.isFound=isFound;
		this.viceVersaWord=viceVersaWord;
		this.position = position;
		this.additional = additional;
	}

}
