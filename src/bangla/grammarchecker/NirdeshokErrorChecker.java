package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.ErrorsInBanglaLanguage;
import bangla.WithTrie.BitMasking;
import bangla.spellchecker.Pair;
import bangla.spellchecker.SpellCheckingDto;

public class NirdeshokErrorChecker implements BanglaGrammerChecker {
	static List<String> nirdeshok = Arrays.asList("টার", "টাকে", "টির", "টিকে", "টুকুর", "টুকুকে", "খানার", "খানাকে",
			"খানির", "জনকে", "গুলির", "গুলিকে", "গুলার", "গুলাকে", "গুলোর", "গুলোকে", "টা", "টে", "টো", "টি", "টু",
			"টুকু", "টুকুন", "টাক", "খানা", "খানি", "গাছা", "গাছি", "জন", "থান", "পাটি", "গুলি", "গুলা", "গুলো");

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
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.nirdeshok);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(str,-1));
				spellCheckerDtos.add(dto);
				continue;
			}
			if (i + 2 >= words.size())
				break;
			str = getNirdeshokWord(words.get(i + 2));
			if (!str.equals("")) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = words.get(i+2);
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.nirdeshok);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(str,-1));
				spellCheckerDtos.add(dto);
				continue;
			}
			spellCheckerDtos.add(new SpellCheckingDto());
		}
		return spellCheckerDtos;
	}

	private static String getNirdeshokWord(String word) {
		for (String each_nirdeshok : nirdeshok) {
			if (word.endsWith(each_nirdeshok)) {
				return word.substring(0, word.length() - each_nirdeshok.length());
			}
		}
		return "";
	}
}
