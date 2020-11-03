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

public class NirdeshokErrorChecker implements BanglaGrammerChecker {
	static List<String> nirdeshok = new ArrayList<>();
	static List<TrieNodeWithList> dictionaries = new ArrayList<>();
	public List<SpellCheckingDto> hasError(List<String> words) {
		List<SpellCheckingDto> spellCheckerDtos = new ArrayList<>();
		for (int i = 0; i < words.size() - 1; i++) {

			if ((!words.get(i).equals("ওই")) && (!words.get(i).equals("এই")) && (!words.get(i).equals("ঐ"))) {
				spellCheckerDtos.add(new SpellCheckingDto());
				continue;
			}
			String str = getNirdeshokWord(words.get(i + 1));
			if (!str.equals("")) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.nirdeshokChecker);
				dto.suggestion = new ArrayList<Pair>();
				spellCheckerDtos.add(dto);
				continue;
			}
			if (i + 2 >= words.size())
				break;
			str = getNirdeshokWord(words.get(i + 2));
			if (!str.equals("")) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.nirdeshokChecker);
				dto.suggestion = new ArrayList<Pair>();
				spellCheckerDtos.add(dto);
				continue;
			}
			spellCheckerDtos.add(new SpellCheckingDto());
		}
		return spellCheckerDtos;
	}

	private static String getNirdeshokWord(String word) {
		for (String each_nirdeshok : nirdeshok) {
			if (word.endsWith(each_nirdeshok) && isValidWord(word.substring(0, word.length() - each_nirdeshok.length()))) {
				return word.substring(0, word.length() - each_nirdeshok.length());
			}
		}
		return "";
	}
	public static void loadNirdeshok(List<GrammarDto> nirdeshokList) {
		for(GrammarDto dto: nirdeshokList) {
			nirdeshok.add(dto.marker);
		}
	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		dictionaries.add(dictionary);
	}
	public static boolean isValidWord(String word) {
		for(TrieNodeWithList dictionary : dictionaries) {
			if(dictionary.searchWord(word).isFound)
				return true;
		}
		return false;
	}
}
