package bangla.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bangla.WithTrie.BitMasking;
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

	public HashMap<Integer, SpellCheckingDto> getCheckedWordsForSentencesUpdateMap(List<SpellCheckingDto> res, HashMap<Integer, SpellCheckingDto> maps, int baseIndex){
		int index;
		
		
						
		for (int i = 0; i < res.size(); i++) {
			index = baseIndex + i;
			if(BitMasking.extractNthBit(res.get(i).errorType, 1) != 0 ) {
				if (maps.get(index).suggestion == null){
					maps.get(index).suggestion = new ArrayList<Pair>();
				}
				maps.get(index).errorType = res.get(i).errorType;
				for(Pair each_suggestion : res.get(i).suggestion)
					maps.get(index).suggestion.add(each_suggestion);
			}
		}
		return maps;
	}
	public HashMap<Integer, SpellCheckingDto> getSentenceValidationInfo(List<String> sentences, HashMap<Integer, SpellCheckingDto> wordMapping){
		int baseInd = 0;
		Map<String,BanglaGrammerChecker> grammarCheckerMap = GrammerCheckerFactory.getRegisteredCheckers();
		for (String each_sentence : sentences) {
			WT.set_text(each_sentence);
			List<String> words = WT._tokenization();
			//////////////each_sentence = concatWithSpaces(words);//////////////////////
			if(words.size() >= 2) {
				for(BanglaGrammerChecker entry: grammarCheckerMap.values()) {
					List<SpellCheckingDto> result = entry.hasError(words);
					wordMapping = getCheckedWordsForSentencesUpdateMap(result, wordMapping ,baseInd);
				}
			}
			
			baseInd += words.size();
		}
		
		return wordMapping;
	}
	public String[] subArray(String[] arr, int begin, int end) {
		return Arrays.copyOfRange(arr, begin, end + 1);
	}
}
