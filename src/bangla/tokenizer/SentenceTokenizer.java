package bangla.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bangla.grammarchecker.*;
import bangla.spellchecker.*;

public class SentenceTokenizer {
	
	private WordTokenizer WT = new WordTokenizer();
	private SentenceTokenizerHelper ST = new SentenceTokenizerHelper();
	public List<String> getTokenizedSentence(String text){
		
		ST.set_text(text);
		List<String> sentences = ST._tokenization();
		return sentences;
	}
	public  String concatWithSpaces(Collection<String> words) {
	    StringBuilder wordList = new StringBuilder();
	    for (String word : words) {
	        wordList.append(word + " ");
	    }
	    return new String(wordList.deleteCharAt(wordList.length() - 1));
	}
	public Map<Integer, spell_checking_dto> getCheckedWordsForSentences(GrammerCheckerDto res, int baseIndex){
		String[] errType = null;
		spell_checking_dto suggested_word=null;
		Map<Integer, spell_checking_dto> result_list = new HashMap<>();
		if(res.isValidSentence == false) {
			errType = res.errorType.split(" ");
			for (int i = 0; i < res.wordSuggestion.size(); i++) {
				suggested_word = new spell_checking_dto();
				suggested_word.word = res.wordSuggestion.get(i).wordName;
				suggested_word.isCorrect = 1;
				suggested_word.wordType = errType[i];
				suggested_word.suggestion = new ArrayList<Pair>();
				suggested_word.suggestion.add(new Pair(res.wordSuggestion.get(i).suggestedWord, 0));
				result_list.put(i, suggested_word);
			}
		}
		return result_list;
	}
	public HashMap<Integer, spell_checking_dto> getCheckedWordsForSentencesUpdateMap(GrammerCheckerDto res, HashMap<Integer, spell_checking_dto> maps, int baseIndex){
		int index;
		String errType[];
		if(res.isValidSentence == false) {
			//errType = res.errorType.split(" ");			
			for (int i = 0; i < res.wordSuggestion.size(); i++) {
				index = baseIndex + res.wordSuggestion.get(i).wordIndex;
				if(maps.get(index).isCorrect == 1 && (res.errorType == "invalid-space-in-word" || res.errorType == "no-space-between-words"))
					continue;
				//maps.get(index).isCorrect = 0;
				if (maps.get(index).suggestion == null){
					maps.get(index).suggestion = new ArrayList<Pair>();
				}
				if(res.errorType == "subject-verb-agreement-error")
					for(String each_suggestion : res.wordSuggestion.get(i).suggestedWordList)
						maps.get(index).suggestion.add(new Pair(each_suggestion, -1));
				else
					maps.get(index).suggestion.add(new Pair(res.wordSuggestion.get(i).suggestedWord, -1));/// null pointer exception here what is the reason
				if(res.errorType == "invalid-space-in-word")
					maps.get(index).word = res.wordSuggestion.get(i).wordName;
				if (maps.get(index).wordType == null)
					maps.get(index).wordType = res.errorType;
				else
					maps.get(index).wordType += " " + res.errorType;
			}
		}
		return maps;
	}
	public HashMap<Integer, spell_checking_dto> getSentenceValidationInfo(List<String> sentences, HashMap<Integer, spell_checking_dto> wordMapping){
		HashMap<Integer, spell_checking_dto> result_list = new HashMap<>();
		//ArrayList<spell_c-hecking_dto> result_list = new ArrayList<>();
		GrammerCheckerDto res;
		//int index = size ;
		List<String> words ;
		int baseInd = 0;
		Map<String,BanglaGrammerChecker> grammarCheckerMap = GrammerCheckerFactory.getRegisteredCheckers();
		for (String each_sentence : sentences) {
			
			
			WT.set_text(each_sentence);
			words = WT._tokenization();
			if(words.size() < 2) {
				baseInd += words.size();
				continue;
			}
			each_sentence = concatWithSpaces(words);
			//ValidateNoSpaceBetweenWords.setDictionary(dictionaries.correctWords.getDictionary());
			for(BanglaGrammerChecker entry: grammarCheckerMap.values()) {
				res = entry.hasError(words);
				wordMapping = getCheckedWordsForSentencesUpdateMap(res, wordMapping ,baseInd);
			}
			
			baseInd += words.size();
		}
		
		return wordMapping;
	}
	public String[] subArray(String[] arr, int begin, int end) {
		return Arrays.copyOfRange(arr, begin, end + 1);
	}
}
