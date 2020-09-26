package bangla.grammarchecker;

import java.util.List;


public class TrieSubVerbRel {
	public static void insert(String word,TrieNode root) {
		List<TrieNode> child = root.children;
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
				temp.isWord = true;
			}
		}
	}
	public static boolean searchWord(String word, TrieNode root) {
		List<TrieNode> child = root.children;
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
			if(!isFound) return false;
			child=temp.children;
			if(i==word.length()-1) {
				if(temp.isWord) return true;
			}
		}
		return false;
	}
}
