package bangla.WithTrie;

public class TrieNodeSearchResult {
	
	public boolean isFound;
	public String viceVersaWord;
	public int position;
	
	public TrieNodeSearchResult(boolean isFound, String viceVersaWord,int position) {
		this.isFound=isFound;
		this.viceVersaWord=viceVersaWord;
		this.position = position;
	}

}
