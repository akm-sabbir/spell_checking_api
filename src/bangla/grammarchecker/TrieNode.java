package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.List;

import bangla.WithTrie.TrieNodeWithList;

public class TrieNode {
	private char c;
	boolean isWord;
	String viceVersaWord;
	private List<TrieNode> children = new ArrayList<>();
	public TrieNode() {
	}
	public TrieNode(char c) {
		this.c = c;
	}
	public char getChar() {
		return this.c;
	}
	public List<TrieNode> getChildren(){
		return children;
	}
	public void insert(String word, String viceVersa) {
		List<TrieNode> child = this.children;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNode temp=null;
			boolean isFound = false;
			for(TrieNode node: child) {
				if(node.c==c) {
					temp=node;
					isFound = true;
					break;
				}
			}
			if(!isFound) {
				temp = new TrieNode(c);
				child.add(temp);
			}
			child=temp.children;
			if(i==word.length()-1) {
				temp.viceVersaWord=viceVersa;
				temp.isWord = true;
			}
		}
	}
	public void insert(String word) {
		this.insert(word,null);
	}
	public TrieNodeSearchResult searchWord(String word) {
		return this.searchWord(word, false);
	}
	public TrieNodeSearchResult searchWord(String word, boolean isAllowPartialMatch) {
		List<TrieNode> child = this.children;
		int lastMatch = -1;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNode temp=null;
			boolean isFound = false;
			for(TrieNode node: child) {
				if(node.c==c) {
					temp=node;
					isFound = true;
					break;
				}
			}
			if(temp!= null && temp.isWord && isAllowPartialMatch) {
				lastMatch = i;
			}
			if(!isFound) return new TrieNodeSearchResult((lastMatch == -1) ? false:true, null, lastMatch);
			child=temp.children;
			if(i==word.length()-1) {
				if(temp.isWord) return new TrieNodeSearchResult(true, temp.viceVersaWord, word.length()-1);
			}
		}
		return new TrieNodeSearchResult((lastMatch == -1) ? false:true, null, lastMatch);
	}
	public static TrieNode prepare(TrieNodeWithList dictionary) {
		// TODO Auto-generated method stub
		// TODO this is a temporary method, next we will use TrieNodeWithList then it will not required
		if(dictionary==null) return null;
		TrieNode node = new TrieNode();
		node.c = dictionary.getChar();
		node.isWord = dictionary.isWord;
		if(dictionary.getChildren() != null && dictionary.getChildren().size() > 0) {
			node.children = new ArrayList<>();
			for(TrieNodeWithList child: dictionary.children) {
				node.children.add(prepare(child));
			}
		}
		return node;
	}
}
