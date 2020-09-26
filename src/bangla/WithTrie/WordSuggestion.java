package bangla.WithTrie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class TrieNode {
	char c;
	boolean isLeaf;
	HashMap<Character, TrieNode> children = new HashMap<>();
	public TrieNode() {
		
	}
	public TrieNode(char c) {
		this.c = c;
	}
}
public class WordSuggestion {
	public static TrieNode root;
	private static double maxMisMatch = 0;
	private static String letter = "অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়";
	private static double similarLetterWeight = 0.25;
	private static double firstHalfWeight = 1;
	private static double secondHalfWeight = 0.5;
	private static double nonRootLetterWeight = 0.25;
	public static void insert(String word) {
		HashMap<Character, TrieNode> children = root.children;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNode temp;
			if(children.containsKey(c)) {
				temp = children.get(c);
			} else {
				temp = new TrieNode(c);
				children.put(c, temp);
			}
			children = temp.children;
			if(i==word.length()-1) {
				temp.isLeaf = true;
			}
		}
	}
	public static boolean searchWord(String word) {
		HashMap<Character, TrieNode> children = root.children;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			TrieNode temp;
			if(children.containsKey(c)) {
				temp = children.get(c);
				children = temp.children;
			} else {
				return false;
			}
			if(i==word.length()-1) {
				if(temp.isLeaf) return true;
			}
		}
		return false;
	}
	static Map<String,Double> wordWithDistance;
	static int cnt;
	public static void dfs(int curr,String currWord,  String word, double misMatch, TrieNode root) {
		cnt++;
		if(root.isLeaf) {
			String tempWord = currWord+root.c;
			if(word.length()>curr) {
				double tempM = 0;
				if(word.charAt(curr)!=root.c) {
					tempM = 1;
				}
				double total = misMatch+tempM+(word.length()-curr-1);
				if(total<=maxMisMatch) {
					if(wordWithDistance.containsKey(tempWord)) {
						if(total<wordWithDistance.get(tempWord)) {
							wordWithDistance.put(tempWord, total);
						}
					} else {
						wordWithDistance.put(tempWord, total);
					}
					
				}
			} else if(misMatch+1<=maxMisMatch) {
				if(wordWithDistance.containsKey(tempWord)) {
					if(misMatch+1<wordWithDistance.get(tempWord)) {
						wordWithDistance.put(tempWord, misMatch+1);
					}
				} else {
					wordWithDistance.put(tempWord, misMatch+1);
				}
				
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
			match=1;
		}
		for(Map.Entry<Character, TrieNode> entry: root.children.entrySet()) {
			dfs(curr+1,currWord+root.c, word, misMatch+match, entry.getValue());
			if(match>0) {
				dfs(curr,currWord+root.c, word, misMatch+match, entry.getValue());
			}
		}
		
	}
	public static void dfsV2(int curr,String currWord,  String word, double misMatch, TrieNode root) {
		cnt++;
		if(root.isLeaf) {
			if(word.length()>curr) {
				double tempM = 0;
				if(word.charAt(curr)!=root.c) {
					if(letter.indexOf(root.c) >= 0) {
						if(isSimilarLetter(word.charAt(curr), root.c))
							tempM = similarLetterWeight; // if letters are similar
						else if(curr>=(word.length()/2)) 
							tempM = secondHalfWeight;
						else 
							tempM = firstHalfWeight;
					} else tempM = nonRootLetterWeight;
					
				}
				double currMisMatch = misMatch+tempM+((word.length()-curr-1)/2.0);
				if(currMisMatch<=maxMisMatch) {
					String tempWord = currWord+root.c;
					if(wordWithDistance.containsKey(tempWord)) {
						double d = wordWithDistance.get(tempWord);
						if(d>currMisMatch) {
							wordWithDistance.put(tempWord, currMisMatch);
						}
					} else {
						wordWithDistance.put(tempWord, currMisMatch);
					}
				}
			} else if(misMatch+secondHalfWeight<=maxMisMatch) {
				String tempWord = currWord+root.c;
				if(wordWithDistance.containsKey(tempWord)) {
					double d = wordWithDistance.get(tempWord);
					if(d>(misMatch+secondHalfWeight)) {
						wordWithDistance.put(tempWord, misMatch+secondHalfWeight);
					}
				} else {
					wordWithDistance.put(tempWord, misMatch+secondHalfWeight);
				}
			}
			 
		}
		if(misMatch>maxMisMatch) {
			return;
		}
		
		if(word.length()-1 < curr) {
			for(Map.Entry<Character, TrieNode> entry: root.children.entrySet()) {
				if(letter.indexOf(root.c) >= 0) {
					dfsV2(curr,currWord+root.c, word, misMatch+secondHalfWeight, entry.getValue());
				} else {
					dfsV2(curr,currWord+root.c, word, misMatch+nonRootLetterWeight, entry.getValue());
				}
				
			}
			return;
		}
		
		double match=0;
		if(word.charAt(curr)!=root.c) {
			if(letter.indexOf(root.c) >= 0) {
				if(isSimilarLetter(word.charAt(curr), root.c))
					match = similarLetterWeight;
				else if(curr>=(word.length()/2)) 
					match = secondHalfWeight;
				else 
				   match = firstHalfWeight;
			} else {
				match = nonRootLetterWeight;
			}
            
		}
		for(Map.Entry<Character, TrieNode> entry: root.children.entrySet()) {
			dfsV2(curr+1,currWord+root.c, word, misMatch+(match*2), entry.getValue());
			dfsV2(curr,currWord+root.c, word, misMatch+match, entry.getValue());
		}
		
	}
	static List<String> arr = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		root = new TrieNode();
		readCsvFile();
		Runtime runtime = Runtime.getRuntime();
		long before = runtime.totalMemory() - runtime.freeMemory();
		buildTrie();
		long after = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("space taken by trie " + (after-before));
	//	insert("অনবদ্য");
		String word = "আনুগত";
		Scanner in = new Scanner(System.in);
		// finding word in trie
	//	System.out.println(searchWord(word));
		maxMisMatch=1;
		while(true) {
			wordWithDistance = new HashMap<>();
			// get suggested word with distance
			cnt=0;
			for(Map.Entry<Character, TrieNode> entry: root.children.entrySet()) {
				//dfsV2(0,"",word,0,entry.getValue());
				dfsV2(0,"",word,0,entry.getValue());
			}
			
		//	System.out.println(cnt);
		//	System.out.println(wordWithDistance.size());
			for(double i=0;i<=maxMisMatch;i+=0.25) {
				if(!wordWithDistance.containsValue(i))
					continue;
				System.out.println("distance = " + i);
				
				for(Map.Entry<String, Double> entry: wordWithDistance.entrySet()) {
					if(i==entry.getValue())
					System.out.print(entry.getKey() + " ");
				}
				System.out.println();
			}
			word = in.next();
		}
	}
	private static boolean isSimilarLetter(char a, char b) {
		if((a=='শ'||a=='ষ'||a=='স') && (b=='শ'||b=='ষ'||b=='স')) return true;
		
		if((a=='র'||a=='ড'||a=='ঢ') && (b=='র'||b=='ড'||b=='ঢ')) return true;
		
		if((a=='জ'||a=='য') && (b=='জ'||b=='য')) return true;
		
		if((a=='অ'||a=='আ') && (b=='অ'||b=='আ')) return true;
		
		if((a=='এ'||a=='ঐ') && (b=='এ'||b=='ঐ')) return true;
		
		if((a=='ও'||a=='ঔ') && (b=='ও'||b=='ঔ')) return true;
		
		if((a=='ই'||a=='ঈ') && (b=='ই'||b=='ঈ')) return true;
		
		if((a=='উ'||a=='ঊ') && (b=='উ'||b=='ঊ')) return true;
		
		if((a=='ন'||a=='ণ') && (b=='ন'||b=='ণ')) return true;
		
		return false;
	}
	// read all word from ankur dictionary
	public static void readCsvFile() throws IOException {
	    String csvFile = "/home/bari/Desktop/ankurDictionary.csv";
        BufferedReader br = null;
        String line = "";
        int cnt=0;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // insert(line);
                 arr.add(line);
                 cnt++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("total word " + cnt);
     }
	private static void buildTrie() {
		for(String s: arr) {
			insert(s);
		}
	}

}
