package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.ErrorsInBanglaLanguage;
import bangla.WithTrie.BitMasking;
import bangla.WithTrie.TrieNodeWithList;
import bangla.spellchecker.Pair;
import bangla.spellchecker.SpellCheckingDto;

public class NirdeshokErrorChecker implements BanglaGrammerChecker {
	static List<String> nirdeshok = Arrays.asList("টার", "টাকে", "টির", "টিকে", "টুকুর", "টুকুকে", "খানার", "খানাকে",
			"খানির", "জনকে", "গুলির", "গুলিকে", "গুলার", "গুলাকে", "গুলোর", "গুলোকে", "টা", "টে", "টো", "টি", "টু",
			"টুকু", "টুকুন", "টাক", "খানা", "খানি", "গাছা", "গাছি", "জন", "থান", "পাটি", "গুলি", "গুলা", "গুলো");
	static List<TrieNodeWithList> dictionaries = new ArrayList<>();
	public List<SpellCheckingDto> hasError(List<String> words) {
		List<SpellCheckingDto> spellCheckerDtos = new ArrayList<>();
		for (int i = 0; i < words.size() - 1; i++) {

			if ((!words.get(i).equals("এই")) && (!words.get(i).equals("ঐ"))) {
				spellCheckerDtos.add(new SpellCheckingDto());
				continue;
			}
			String str = getNirdeshokWord(words.get(i + 1));
			if (!str.equals("")) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i+1);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.nirdeshokChecker);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(str,-1));
				spellCheckerDtos.add(new SpellCheckingDto());
				spellCheckerDtos.add(dto);
				i++;
				continue;
			}
			if (i + 2 >= words.size())
				break;
			str = getNirdeshokWord(words.get(i + 2));
			if (!str.equals("")) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i+2);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.nirdeshokChecker);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(str,-1));
				spellCheckerDtos.add(new SpellCheckingDto());
				spellCheckerDtos.add(new SpellCheckingDto());
				spellCheckerDtos.add(dto);
				i+=2;
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
