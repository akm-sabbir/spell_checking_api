package bangla.grammarchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.WithTrie.TrieNodeWithList;
import bangla.WithTrie.WordSuggestionV3;
import bangla.spellchecker.*;;


public class ValidateNoSpaceBetweenWords implements BanglaGrammerChecker{
	static TrieNodeWithList dictionary;
	static List<TrieNodeWithList> dictionaries = new ArrayList<>();
		public GrammerCheckerDto hasError(String sentence) {
		List<String> words = Arrays.asList(sentence.split(" "));
		GrammerCheckerDto ret = new GrammerCheckerDto();
		ret.wordSuggestion = new ArrayList<>();
		ret.isValidSentence=true;
		int flag=0;
        int index=0;
        int pos = -1;
        int continuous;
		for(String str: words) {
			continuous = 0;
			for (TrieNodeWithList dictionary : dictionaries) {
				if(isValidWord(str, dictionary)==1) {
					index++;
					continuous = 1;
					break;
				}
				pos = isTwoCombinedWord(str, dictionary);
				if(pos != -1 && pos != str.length() - 1)
					break;
			}
			if (continuous == 1) {
				continue;
			}
			//TrieNodeWithList mainChar =WordSuggestionV
			if(pos!=-1 && pos!=str.length()-1) {
				flag=1;
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
		
	public static void setDictionary(TrieNodeWithList root) {
			dictionary = root;
	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		dictionaries.add(dictionary);
		return;
	}
	
	public static void buildTrie(List<word_dto> words) {
		
		for(int i=0;i<words.size();i++) {
			List<String> mixed = Arrays.asList(words.get(i).word.split(","));
			if(mixed.size()>0) {
				TrieNoSpace.insert(mixed.get(0), dictionary);
			}
		}
	}
	private static int isTwoCombinedWord(String word, TrieNodeWithList dictionary) {
		int pos = TrieNoSpace.searchWord(word, dictionary);
		if(pos==-1 || pos==word.length()-1) return pos;
		int ret=pos;
		String sub = word.substring(pos + 1, word.length());
		pos = TrieNoSpace.searchWord(sub, dictionary);
		if(pos==sub.length()-1) return ret;
		return -1;
	}
    private static int isValidWord(String word, TrieNodeWithList dictionary) {
		int pos = TrieNoSpace.searchWord(word, dictionary);
		if(pos==word.length()-1) {
			return 1;
		} else {
			return 0;
		}
	}
}
