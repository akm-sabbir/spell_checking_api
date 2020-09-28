package bangla.grammarchecker;

public class TrieNodeSearchResult {
	
	boolean isFound;
	String viceVersaWord;
	int position;
	
	public TrieNodeSearchResult(boolean isFound, String viceVersaWord,int position) {
		this.isFound=isFound;
		this.viceVersaWord=viceVersaWord;
		this.position = position;
	}

}
