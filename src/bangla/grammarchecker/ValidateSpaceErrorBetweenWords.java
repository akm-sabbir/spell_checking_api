package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.WithTrie.TrieNodeWithList;
import bangla.spellchecker.word_dto;
import bangla.tokenizer.WordTokenizer;


public class ValidateSpaceErrorBetweenWords implements BanglaGrammerChecker{
	static TrieNodeWithList dictionary ;
	static List<TrieNodeWithList> dictionaries = new ArrayList<>();
	static WordTokenizer WT = new WordTokenizer();
	public GrammerCheckerDto hasError(String sentence) {
		WT.set_text(sentence);
		List<String> words = WT._tokenization();//Arrays.asList(sentence.split(" "));
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
	
	public static void setDictionary(TrieNodeWithList root) {
		dictionary = root;
	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		dictionaries.add(dictionary);
		return;
	}
	public static void buildTrie(List<word_dto> words) {
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
				TrieValidSpace.insert(mixed.get(0), dictionary);
			}
		}
	}
	public static boolean findWord(String word) {
		for(TrieNodeWithList dictionary : dictionaries) {
			if(TrieValidSpace.searchWord(word, dictionary) == true)
				return true;
		}
		return false;
	}
}
