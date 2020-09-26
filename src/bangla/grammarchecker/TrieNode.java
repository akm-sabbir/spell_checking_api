package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
	char c;
	boolean isWord;
	String viceVersaWord;
	List<TrieNode> children = new ArrayList<>();
	public TrieNode() {
	}
	public TrieNode(char c) {
		this.c = c;
	}
}
