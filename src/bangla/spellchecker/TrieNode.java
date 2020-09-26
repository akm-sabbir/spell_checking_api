package bangla.spellchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

class TrieNode {
	public char c;
	public boolean isWord;
	//public List< TrieNode> children = new ArrayList<>();
	public HashSet<TrieNode> sets = new HashSet<>();
	public TrieNode() {

	}
	public TrieNode(char c) {
		this.c = c;
	}
	public boolean isWord_() {
		return this.isWord;
	}
}