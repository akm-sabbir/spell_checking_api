package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.WithTrie.TrieNodeWithList;
import bangla.spellchecker.*;;


public class NoSpaceBetweenWordsChecker implements BanglaGrammerChecker{
	static List<TrieNode> dictionaries = new ArrayList<>();
		public GrammerCheckerDto hasError(List<String> words) {
		GrammerCheckerDto ret = new GrammerCheckerDto();
		ret.wordSuggestion = new ArrayList<>();
		ret.isValidSentence=true;
        int index=0;
        int pos = -1;
        int continuous;
		for(String str: words) {
			continuous = 0;
			for (TrieNode dictionary : dictionaries) {
				if(dictionary.searchWord(str).isFound) {
					index++;
					continuous = 1;
					break;
				}
				// TODO: check a word is combined of two different dictionary words
				pos = isTwoCombinedWord(str, dictionary);
				if(pos != -1 && pos != str.length() - 1)
					break;
			}
		
			if (continuous == 1) {
				continue;
			}
			
			if(pos!=-1 && pos!=str.length()-1) {
				WordSuggestion sugg = new WordSuggestion();
				sugg.wordName = str;
                sugg.wordIndex = index;
				sugg.suggestedWord = str.substring(0, pos + 1) + " " + str.substring(pos + 1, str.length());
				ret.wordSuggestion.add(sugg);
				ret.errorType = "no-space-between-words";
				ret.isValidSentence=false;
			}
            index++;
		}
		
		return ret;
	}
		
//	public static void setDictionary(TrieNode root) {
//			dictionary = root;
//	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		TrieNode dictionaryNode = TrieNode.prepare(dictionary);
		dictionaries.add(dictionaryNode);
		return;
	}
	private static int isTwoCombinedWord(String word, TrieNode dictionary) {
		int pos = dictionary.searchWord(word,true).position;
		if(pos==-1 || pos==word.length()-1) return pos;
		int ret=pos;
		String sub = word.substring(pos + 1, word.length());
		pos = dictionary.searchWord(sub,true).position;
		if(pos==sub.length()-1) return ret;
		return -1;
	}
}
