package bangla.spellchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bangla.WithTrie.*;
import bangla.WithTrie.KmpStringMatch;
import bangla.dao.AnnotatedWordRepository;
import bangla.dao.DictionaryRepository;
import bangla.dao.GlobalDictionaryRepository;
import bangla.dao.NamedEntityRepository;
import bangla.dao.NaturalErrorRepository;

public class SpellChecker {

		
	public SpellChecker() {
	}

	
	public SpellCheckingDto copyDto(Map<Integer, SpellCheckingDto> result_list, String data, Map<String, Integer> tracker,
			SpellCheckingDto suggested_word) {
		SpellCheckingDto temp_container  = result_list.get(tracker.get(data));
		suggested_word.sequence = temp_container.sequence + 1;
		suggested_word.errorType = temp_container.errorType;
		if(temp_container.suggestion != null)
			suggested_word.suggestion =  temp_container.suggestion;
		return suggested_word;
	}
	
	public SpellCheckingDto createSuggestion(SpellCheckingDto suggested_word, String data, WordSuggestionV3 wordSuggestion) {
		if (suggested_word.suggestion == null)
			suggested_word.suggestion = new ArrayList<Pair>();
		Map<String, Integer> wordWithDistance = wordSuggestion.getSuggestedWord(data);
		for(Map.Entry<String, Integer> entry: wordWithDistance.entrySet()) {
			if(suggested_word.suggestion.size() !=0) {
				if(suggested_word.suggestion.get(0).words.contentEquals( entry.getKey()) == false)
					suggested_word.suggestion.add(new Pair(entry.getKey(), entry.getValue()));
			}else
				suggested_word.suggestion.add(new Pair(entry.getKey(), entry.getValue()));
		}
		return suggested_word;
	}
	
	public Map<String, Integer> getInvertedIndex(List<String> tokenized_data){
		int index = 0;
		Map<String, Integer> invertedIndex = new HashMap<>();
		for(String word : tokenized_data) {
			if (invertedIndex.get(word) == null) {
				invertedIndex.put(word, index);
				index += 1;
			}
		}
		return invertedIndex;
	}
	
	public ArrayList<Pair> getSuggestionList(ArrayList<Pair> suggestion) {
		if (suggestion == null)
			return new ArrayList<Pair>();
		return suggestion;
	}
	
	public SpellCheckingDto getProcessedMap(SpellCheckingDto suggested_word, String data, Map<Integer, SpellCheckingDto> result_list, Map<String,Integer> tracker,
		 Map<Integer, TrieNodeWithList> table_query, SpellCheckingDto unKnownCounter)
	{
		
		if (tracker.containsKey(data) == true) 
		{	
			suggested_word = copyDto(result_list, data, tracker, suggested_word);
			return suggested_word;
		}
		
		
		WordSuggestionV3 wordSuggestion = new WordSuggestionV3();
				
		wordSuggestion.setDictionary(NamedEntityRepository.getInstance().root);
		if(wordSuggestion.searchWord(data) == true) {
			suggested_word.errorType = BitMasking.resetBitAt(suggested_word.errorType, 1);
		}else {
			suggested_word.errorType = BitMasking.setBitAt(suggested_word.errorType, 1);
		}
				
		if (BitMasking.extractNthBit( suggested_word.errorType, 1) == 1) {
			wordSuggestion.setDictionary(DictionaryRepository.getInstance().root);
			System.out.println("suggesting words not found in named entity ");
			if(wordSuggestion.searchWord(data) == true) {
				suggested_word.errorType = BitMasking.resetBitAt(suggested_word.errorType, 1);
			}else {
				suggested_word.errorType = BitMasking.setBitAt(suggested_word.errorType, 1);
			}
			if (BitMasking.extractNthBit( suggested_word.errorType, 1) == 1) {
				System.out.println("suggesting words not found in dictionary ");
					wordSuggestion.setDictionary(AnnotatedWordRepository.getInstance().root);
					if(wordSuggestion.searchWord(data) == true) {
						suggested_word.errorType = BitMasking.resetBitAt(suggested_word.errorType, 1);
					}else {
						suggested_word.errorType = BitMasking.setBitAt(suggested_word.errorType, 1);
					}
				}
				
				
			}
			
			if (BitMasking.extractNthBit( suggested_word.errorType, 1) == 1){
				System.out.println("suggesting words not found in annotatedWords ");
				wordSuggestion.setDictionary(NaturalErrorRepository.getInstance().root);
				System.out.println("Start Searching error word dictionary");
				if (wordSuggestion.searchWord(data)==true) {
					System.out.println("I have found word in natural error word dictionary and the word is: ");
					suggested_word.errorType = BitMasking.setBitAt(suggested_word.errorType, 1);
					suggested_word.errorType = BitMasking.setBitAt(suggested_word.errorType, 5);
					try {
					
					if(NaturalErrorRepository.getInstance().errorToCorrect.containsKey(data)== true) {
						String word = NaturalErrorRepository.getInstance().errorToCorrect.get(data);
						System.out.println("the correct form of error word: " + word);
						suggested_word.suggestion = getSuggestionList(suggested_word.suggestion);
						suggested_word.suggestion.add(new Pair(word,0));
					}
					
					}catch(NullPointerException ex) {
						ex.printStackTrace();
					}
				}else {
					System.out.println("suggesting words not found in natural error dictionary ");
					if (BitMasking.extractNthBit( suggested_word.errorType, 1) == 1) {
						if(GlobalDictionaryRepository.getInstance().ahoGlobal.preparedSuffix == 0) {
							System.out.println("Suffix links have not been prepared yet preparing....");
							GlobalDictionaryRepository.getInstance().ahoGlobal.PrepareAho();
							GlobalDictionaryRepository.getInstance().ahoGlobal.preparedSuffix=1;
						}
						String ahoResults = "";//GlobalDictionaryRepository.getInstance().ahoGlobal.getInflectedWords(data);
						try {
							if(ahoResults != null && ahoResults.isEmpty()== false) {
								suggested_word.errorType= BitMasking.setBitAt(suggested_word.errorType, 10);
								suggested_word.errorType= BitMasking.resetBitAt(suggested_word.errorType, 1);
								suggested_word.suggestion = getSuggestionList(suggested_word.suggestion);
								suggested_word.suggestion.add(new Pair(ahoResults,0));
							}else {
								suggested_word.errorType= BitMasking.setBitAt(suggested_word.errorType, 6);
								suggested_word.errorType= BitMasking.resetBitAt(suggested_word.errorType, 1);
								unKnownCounter.sequence += 1;
							}
						}catch(NullPointerException ex) {
							System.out.println(ex.getStackTrace());
						}
					}
					
					//wordSuggestion.setDictionary(dictionaries.dicWords);
				}
				// following piece of code is necessary for querying server
				
				wordSuggestion.setDictionary(DictionaryRepository.getInstance().root);
				suggested_word = createSuggestion(suggested_word, data, wordSuggestion );
				wordSuggestion.setDictionary(AnnotatedWordRepository.getInstance().root );
				suggested_word = createSuggestion(suggested_word, data, wordSuggestion );
				wordSuggestion.setDictionary(NamedEntityRepository.getInstance().root);
				suggested_word = createSuggestion(suggested_word, data, wordSuggestion );
			}
				
		return suggested_word;

	}
	public int getWhiteSpaceLen(String document, int seekPoint) {
		
		return 0;
	}
	public HashMap<Integer, SpellCheckingDto> getWordValidationInfo(StringBuilder text_data, Map<Integer, String> tokenized_data) {
		//String encoded;
		HashMap<Integer, SpellCheckingDto> result_list = new HashMap<>();
		HashMap<String, Integer> tracker = new HashMap<String, Integer>();
		HashMap<Integer, TrieNodeWithList> table_query = new HashMap<>();
		SpellCheckingDto unKnownCounter = new SpellCheckingDto();
		String data;
		int key;
		
		for(Entry<Integer, String> eachWord : tokenized_data.entrySet()) {
			
			data = eachWord.getValue();
			key =  eachWord.getKey();
			SpellCheckingDto suggested_word = new SpellCheckingDto();
			suggested_word.word = data;
			suggested_word.sequence = 0;
			suggested_word = getProcessedMap(suggested_word, data, result_list, tracker, table_query, unKnownCounter);
			result_list.put(key, suggested_word);
			tracker.put(data, key);
			suggested_word.startIndex = KmpStringMatch.KMPSearch(data, text_data);
			suggested_word.length =  data.length();
		
		}
		result_list.put(2147483647, unKnownCounter);
		return  result_list;
	}


	
}
