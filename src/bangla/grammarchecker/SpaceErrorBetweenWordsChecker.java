package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.List;

import bangla.ErrorsInBanglaLanguage;
import bangla.WithTrie.BitMasking;
import bangla.WithTrie.TrieNodeWithList;
import bangla.spellchecker.Pair;
import bangla.spellchecker.SpellCheckingDto;


public class SpaceErrorBetweenWordsChecker implements BanglaGrammerChecker{
	static TrieNodeWithList dictionary ;
	static List<TrieNodeWithList> dictionaries = new ArrayList<>();
	public List<SpellCheckingDto> hasError(List<String> words) {
		List<SpellCheckingDto> spellCheckerDtos = new ArrayList<>();
		for(int i=0;i<words.size()-1;i++) {
			if(findWord(words.get(i)+words.get(i+1))) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i) + " " + words.get(i+1);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.validspacemissingChecker);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(words.get(i) + words.get(i+1), -1));
				spellCheckerDtos.add(dto);
				i++;
			} else {
				spellCheckerDtos.add(new SpellCheckingDto());
			}
		}
		spellCheckerDtos.add(new SpellCheckingDto());
		return spellCheckerDtos;
	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		dictionaries.add(dictionary);
	}
	public static boolean findWord(String word) {
		for(TrieNodeWithList dictionary : dictionaries) {
			if(dictionary.searchWord(word).isFound)
				return true;
		}
		return false;
	}
}
