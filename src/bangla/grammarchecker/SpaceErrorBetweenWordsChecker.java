package bangla.grammarchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.WithTrie.TrieNodeWithList;
import bangla.spellchecker.WordDto;


public class SpaceErrorBetweenWordsChecker implements BanglaGrammerChecker{
	static TrieNode dictionary ;
	static List<TrieNode> dictionaries = new ArrayList<>();
	public GrammerCheckerDto hasError(List<String> words) {
		GrammerCheckerDto ret = new GrammerCheckerDto();
		ret.wordSuggestion = new ArrayList<>();
		for(int i=0;i<words.size()-1;i++) {
			if(findWord(words.get(i)+words.get(i+1))) {
				WordSuggestion sugg = new WordSuggestion();
				sugg.wordName = words.get(i) + " " + words.get(i+1);
				sugg.suggestedWord = words.get(i) + words.get(i+1);
				sugg.wordIndex = i;
				ret.wordSuggestion.add(sugg);
				i++;
			}
		}
		if(ret.wordSuggestion.size()>0) {
			ret.errorType = "invalid-space-in-word";
			ret.isValidSentence = false;
		} else {
			ret.isValidSentence = true;
		}
		return ret;
	}
	
	public static void setDictionary(TrieNode root) {
		dictionary = root;
	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		TrieNode dictionaryNode = TrieNode.prepare(dictionary);
		dictionaries.add(dictionaryNode);
		return;
	}
	public static void buildTrie(List<WordDto> words) {
		/*
		List<String> words = new ArrayList<>();
		try {
			words = ReadCsvFile.getDictionaryWordsFromCsvFile();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		for(int i=0;i<words.size();i++) {
			List<String> mixed = Arrays.asList(words.get(i).word.split(","));
			if(mixed.size()>0) {
				dictionary.insert(mixed.get(0));
			}
		}
	}
	public static boolean findWord(String word) {
		for(TrieNode dictionary : dictionaries) {
			if(dictionary.searchWord(word).isFound)
				return true;
		}
		return false;
	}
}
