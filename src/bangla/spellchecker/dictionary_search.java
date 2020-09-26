package bangla.spellchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class dictionary_search {

		//public  TrieNode root;
		private  int maxMisMatch = 1;
		
		/*
		public Map<String,Integer> wordWithDistance;
		public  void dfs(int curr, String currWord,  String word, int misMatch, TrieNode root) {
			if(root.isWord) {
				if(word.length()>curr) {
					int tempM = 0;
					if(word.charAt(curr)!=root.c) {
						tempM = 1;
					}
					if((misMatch+tempM+(word.length()-curr-1))<=maxMisMatch) {
						wordWithDistance.put(currWord+root.c, (misMatch+tempM+(word.length()-curr-1)));
					}
				} else if(misMatch+1<=maxMisMatch) {
					wordWithDistance.put(currWord+root.c, misMatch+1);
				}
				 
			}
			if(misMatch>maxMisMatch) {
				return;
			}
			
			if(word.length()-1 < curr) {
				for(Map.Entry<Character, TrieNode> entry: root.children.entrySet()) {
					dfs(curr,currWord+root.c, word, misMatch+1, entry.getValue());
				}
				return;
			}
			
			int match=0;
			if(word.charAt(curr)!=root.c) {
				match++;
			}
			for(Map.Entry<Character, TrieNode> entry: root.children.entrySet()) {
				dfs(curr+1,currWord + root.c, word, misMatch + match, entry.getValue());
				if(match>0) {
					dfs(curr,currWord + root.c, word, misMatch + match, entry.getValue());
				}
			}
			
		}
		public  Set<Map.Entry<String, Integer>> suggested_word_list(String word, dictionary dict) throws IOException {
			TrieNode root = dict.root;
			//String word = "কথন";
			// finding word in trie
			//System.out.println(searchWord(word));
			wordWithDistance = new HashMap<>();
				// get suggested word with distance
			for(Map.Entry<Character, TrieNode> entry: root.children.entrySet()) {
					dfs(0,"",word,0,entry.getValue());
			}
				/*
				for(Map.Entry<String, Integer> entry: wordWithDistance.entrySet()) {
					System.out.print(entry.getKey() + " " + entry.getValue() + "  ");
				}
			
			return wordWithDistance.entrySet();
		}
		// read all word from ankur dictionary
		public void readCsvFile() throws IOException {
		    String csvFile = "/home/bari/Desktop/ankurDictionary.csv";
	        BufferedReader br = null;
	        TrieNode root = new TrieNode();
	        String line = "";
	        try {
	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {
	                 //insert(line, root);
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	}


	public dictionary build_dictionary(dictionary dict, List<spell_checking_dto> data_list) {
		dict.build_dictionary(data_list);
		return dict;
	}
	public boolean is_correct_word(String word, dictionary dict) {
		if (((main_dictionary)dict).searchWord(word) == true)
			return true;
		return false;
	}
	
	public boolean is_incorrect_word(String word, dictionary dict) {
		
		return false;
	}
	
	public boolean is_named_entity(String word, dictionary dict) {
		
		if(((named_entity)dict).searchWord(word) == true)
			return true;
		return false;
	}*/
}
