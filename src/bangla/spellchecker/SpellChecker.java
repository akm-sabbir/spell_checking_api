package bangla.spellchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bangla.WithTrie.*;
import bangla.WithTrie.kmpStringMatch;
import bangla.dao.AnnotatedWordRepository;
import bangla.dao.DictionaryRepository;
import bangla.dao.NamedEntityRepository;
import bangla.dao.NaturalErrorRepository;

public class SpellChecker {

		
	public SpellChecker() {
	}

	
	public spell_checking_dto copyDto(Map<Integer, spell_checking_dto> result_list, String data, Map<String, Integer> tracker,
			spell_checking_dto suggested_word) {
		spell_checking_dto temp_container  = result_list.get(tracker.get(data));
		suggested_word.ID = temp_container.ID + 1;
		suggested_word.isCorrect = temp_container.isCorrect;
		suggested_word.langType = temp_container.langType;
		if(temp_container.suggestion != null)
			suggested_word.suggestion = (ArrayList<Pair>) temp_container.suggestion;
		suggested_word.wordType = temp_container.wordType;
		return suggested_word;
	}
	public spell_checking_dto createSuggestion(spell_checking_dto suggested_word, String data, WordSuggestionV3 wordSuggestion) {
		suggested_word.isCorrect = 0;
		if (suggested_word.suggestion == null)
			suggested_word.suggestion = new ArrayList<Pair>();
		Map<String, Integer> wordWithDistance = wordSuggestion.getSuggestedWord(data);
		for(Map.Entry<String, Integer> entry: wordWithDistance.entrySet()) {
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
	public spell_checking_dto getProcessedMap(spell_checking_dto suggested_word, String data, Map<Integer, spell_checking_dto> result_list, Map<String,Integer> tracker,
		 Map<Integer, TrieNodeWithList> table_query){
		WordSuggestionV3 wordSuggestion = new WordSuggestionV3();
		//String data = normalizeText(data_);
		System.out.println("After normalization the string data is");
		System.out.println(data);
		if (tracker.containsKey(data) == true) {	
			suggested_word = copyDto(result_list, data, tracker, suggested_word);
		}else {
			
			wordSuggestion.setDictionary(NamedEntityRepository.getInstance().root);
			if(wordSuggestion.searchWord(data) == true) {
				suggested_word.isCorrect = 1;
				suggested_word.wordType="named-entity";
			}
			if (suggested_word.isCorrect == 0) {
				wordSuggestion.setDictionary(DictionaryRepository.getInstance().root);
				System.out.println("suggesting words not found in named entity ");
				if(wordSuggestion.searchWord(data) == true) {
					suggested_word.isCorrect = 1;
				}
				System.out.println("suggesting words not found in dictionary ");
				wordSuggestion.setDictionary(AnnotatedWordRepository.getInstance().root);
				if(wordSuggestion.searchWord(data) == true) {
					suggested_word.isCorrect = 1;
				}
				System.out.println("suggesting words not found in annotatedWords ");
			}
			
			if (suggested_word.isCorrect == 0){
				
				wordSuggestion.setDictionary(NaturalErrorRepository.getInstance().root);
				System.out.println("Start Searching error word dictionary");
				if (wordSuggestion.searchWord(data)==true) {
					System.out.println("I am searching natural error word dictionary and the word is: ");
					suggested_word.wordType="error";
					try {
					
					if(NaturalErrorRepository.getInstance().errorToCorrect.containsKey(data)== true) {
						String word = NaturalErrorRepository.getInstance().errorToCorrect.get(data);
						System.out.println(word);
						suggested_word.suggestion = getSuggestionList(suggested_word.suggestion);
						suggested_word.suggestion.add(new Pair(word,0));
					}
					
					}catch(NullPointerException ex) {
						ex.printStackTrace();
					}
				}else {
				
					suggested_word.wordType="unknown";
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
		}
		//encoded = this.gson.toJson(suggested_word);		
		return suggested_word;

	}
	public int getWhiteSpaceLen(String document, int seekPoint) {
		
		return 0;
	}
	public HashMap<Integer, spell_checking_dto> getWordValidationInfo(String text_data, Map<Integer, String> tokenized_data) {
		//String encoded;
		HashMap<Integer, spell_checking_dto> result_list = new HashMap<>();
		HashMap<String, Integer> tracker = new HashMap<String, Integer>();
		HashMap<Integer, TrieNodeWithList> table_query = new HashMap<>();
		String data;
		int key;
		
		for(Entry<Integer, String> eachWord : tokenized_data.entrySet()) {
			
			data = eachWord.getValue();
			key =  eachWord.getKey();
			spell_checking_dto suggested_word = new spell_checking_dto();
			suggested_word.word = data;
			suggested_word.ID = 0;
			suggested_word.isCorrect = 0;
			suggested_word = getProcessedMap(suggested_word, data, result_list, tracker, table_query);
			result_list.put(key, suggested_word);
			tracker.put(data, key);
			suggested_word.startIndex = kmpStringMatch.KMPSearch(data, text_data);
			suggested_word.endIndex =  suggested_word.startIndex + data.length() - 1;
		
		}
		
		return  result_list;
	}


	
}
