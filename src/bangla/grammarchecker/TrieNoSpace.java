package bangla.grammarchecker;

import java.util.List;

import bangla.WithTrie.TrieNodeWithList;




public class TrieNoSpace {
	public static void insert(String word,TrieNodeWithList root) {
		List<TrieNodeWithList> child = root.children;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNodeWithList temp=null;
			boolean isFound = false;
			for(TrieNodeWithList node: child) {
				if(node.getChar() == c) {
					temp=node;
					isFound = true;
					break;
				}
			}
			if(!isFound) {
				temp = new TrieNodeWithList(c);
				child.add(temp);
			}
			child=temp.children;
			if(i==word.length()-1) {
				temp.isWord = true;
			}
		}
	}
	public static int searchWord(String word, TrieNodeWithList root) {
		List<TrieNodeWithList> child = root.children;
		int lastMatch = -1;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNodeWithList temp=null;
			boolean isFound = false;
			for(TrieNodeWithList node: child) {
				if(node.getChar()==c) {
					temp=node;
					isFound = true;
					break;
				}
			}
			if(!isFound) break;
			if(temp.isWord) {
				lastMatch = i;
			}
			child=temp.children;
		}
		return lastMatch;
	}
}
