package bangla.WithTrie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FatFingerWordSuggestion {
	public static TrieNodeWithList root;
	private static int maxFatFinger = 0;
	private static final int bijoyPhone = 1;
	private static final int provatPhone = 2;
	private static final int gBoard = 3;
	private static int deviceType = 0;
	static Map<String,Integer> wordWithFatFinger;
	public static void insert(String word) {
		List<TrieNodeWithList> child = root.children;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNodeWithList temp=null;
			boolean isFound = false;
			for(TrieNodeWithList node: child) {
				if(node.c==c) {
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
	public static boolean searchWord(String word) {
		List<TrieNodeWithList> child = root.children;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNodeWithList temp=null;
			boolean isFound = false;
			for(TrieNodeWithList node: child) {
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
	/*
	public static void fatFingerWords(int curr,String currWord,  String word, int misMatch, TrieNodeWithList root) {
		
		if(root.isWord && curr==word.length()-1) {
			int fatFinger = 0;
			if(root.c != word.charAt(curr)) {
				if(isFatFinger(deviceType, word.charAt(curr), root.c)) {
					fatFinger = 1;
				} else fatFinger = -1;
			}
			if(fatFinger >= 0) {
				if(misMatch+fatFinger<=maxFatFinger) {
				    wordWithFatFinger.put((currWord+root.c), misMatch+fatFinger);
				}
			}
			return;
		}
		if(curr>=word.length()) return;
		if(root.c==word.charAt(curr)) {
			for(TrieNodeWithList node: root.children) {
				fatFingerWords(curr+1, currWord+root.c, word, misMatch, node);
			}
		} else if(isFatFinger(deviceType, word.charAt(curr), root.c)) {
			for(TrieNodeWithList node: root.children) {
				fatFingerWords(curr+1, currWord+root.c, word, misMatch+1, node);
			}
		}
	}
	public static void main(String[] args) {
		buildTrie();
		wordWithFatFinger = new HashMap<>();
		deviceType = bijoyPhone;
		setFatFinger(deviceType);
		maxFatFinger = 2;
		String typedWord = "এতিবাহন";
		for(TrieNodeWithList node: root.children) {
			fatFingerWords(0, "", typedWord, 0, node);
		}
		for(Map.Entry<String, Integer> map: wordWithFatFinger.entrySet()) {
			System.out.println(map.getKey() + " " + map.getValue());
		}
	}
	private static void buildTrie() {
		root = new TrieNodeWithList();
		List<String> words = new ArrayList<>();
		try {
			 words = ReadCsvFile.getAllWordsFromCsvFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String word: words) {
			insert(word);
		}
	}
	
	private static boolean isFatFinger(int deviceType, char typedChar, char fatChar) {
		if(deviceType == bijoyPhone) {
			return FatFingerChecker.isFatFingerBijoyPhone(typedChar, fatChar);
		} else if(deviceType == provatPhone) {
			return FatFingerChecker.isFatFingerProvatPhone(typedChar, fatChar);
		} else if(deviceType == gBoard) {
			return FatFingerChecker.isFatFingerGboard(typedChar, fatChar);
		}
		return false;
	}
	private static void setFatFinger(int deviceType) {
		if(deviceType == bijoyPhone) {
			FatFingerChecker.setFatFingerForBijoyPhone();
		} else if(deviceType == provatPhone) {
			FatFingerChecker.setFatFingerForProvatPhone();
		} else if(deviceType == gBoard) {
			FatFingerChecker.setFatFingerForGboard();
		}
	}*/
}
