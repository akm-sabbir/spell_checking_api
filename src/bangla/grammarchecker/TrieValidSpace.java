package bangla.grammarchecker;

import java.util.List;

import bangla.WithTrie.TrieNodeWithList;




public class TrieValidSpace {
	public static void insert(String word,TrieNodeWithList dictionary) {
		List<TrieNodeWithList> child = dictionary.children;
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
	public static boolean searchWord(String word, TrieNodeWithList root) {
		List<TrieNodeWithList> child = root.children;
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
			if(!isFound) return false;
			child=temp.children;
			if(i==word.length()-1) {
				if(temp.isWord) return true;
			}
		}
		return false;
	}
}
