package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.ErrorsInBanglaLanguage;
import bangla.WithTrie.BitMasking;
import bangla.WithTrie.TrieNodeWithList;
import bangla.dao.GrammarDto;
import bangla.spellchecker.Pair;
import bangla.spellchecker.SpellCheckingDto;

public class NegativeTypeWordErrorChecker implements BanglaGrammerChecker {
	static List<TrieNodeWithList> dictionaries = new ArrayList<>();
	static TrieNodeWithList verbList = new TrieNodeWithList();
	static List<String> negativeStr = Arrays.asList("নাই","নেই","না");
	static String NI = "নি";

	public List<SpellCheckingDto> hasError(List<String> words) {
		List<SpellCheckingDto> spellCheckerDtos = new ArrayList<>();
		for(int i=0;i<words.size();i++) {
			String word = words.get(i);
			String str = isErrorWord(word);
			if(str.length()>0) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.negativeTypeWordChecker);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(str,-1));
				spellCheckerDtos.add(dto);
			} else if(i+1<words.size() && words.get(i+1).equals(NI)) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.negativeTypeWordChecker);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(words.get(i)+NI,-1));
				spellCheckerDtos.add(dto);
				i++;
			} else {
				spellCheckerDtos.add(new SpellCheckingDto());
			}
		}
		return spellCheckerDtos;
	}
	private static String isErrorWord(String word) {
		int len = word.length();
		// check negative as suffix
		for(int i=0;i<negativeStr.size();i++) {
			int slen = negativeStr.get(i).length();
			if(word.endsWith(negativeStr.get(i))) {
				for (TrieNodeWithList dictionary : dictionaries) {
					if(isValidDictionaryWord(word.substring(0, len-slen), dictionary)) {
						return word.substring(0, len-slen) + " " + negativeStr.get(i);
					}
				}
				
			}
		}
		// check negative as prefix
		for(int i=0;i<negativeStr.size();i++) {
			int slen = negativeStr.get(i).length();
			if(word.startsWith(negativeStr.get(i))) {
				for (TrieNodeWithList dictionary : dictionaries) {
					if(isValidDictionaryWord(word.substring(slen, len), dictionary)) {
						return negativeStr.get(i) + " " + word.substring(slen, len);
					}
				}
				
			}
		}
		//  if verb after na is attached then it is wrong should separate
		if(word.startsWith(negativeStr.get(2))) {
			if(isVerb(word.substring(2, len))) {
				return negativeStr.get(2) + " " + word.substring(2, len);
			}
		}
		return "";
	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		dictionaries.add(dictionary);
		return;
	}
	
	private static boolean isValidDictionaryWord(String word, TrieNodeWithList dictionary) {
		return dictionary.searchWord(word).isFound;
	}
	public static void buildTrie(List<GrammarDto> words) {
		GrammarDto mixed;
		for (int i = 0; i < words.size(); i++) {
			mixed = words.get(i);
			if (mixed.cholit != null && !mixed.cholit.equals("invalid")) {
				verbList.insert(mixed.cholit);
			}
			if (mixed.shadhu != null && !mixed.shadhu.equals("invalid")) {
				verbList.insert(mixed.shadhu);
			}
		}
	}
	private static boolean isVerb(String word) {
		return verbList.searchWord(word).isFound;
	}

}
