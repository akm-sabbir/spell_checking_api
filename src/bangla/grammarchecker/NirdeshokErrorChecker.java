package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NirdeshokErrorChecker implements BanglaGrammerChecker {
	static List<String> nirdeshok = Arrays.asList("টার","টাকে","টির","টিকে","টুকুর","টুকুকে","খানার","খানাকে","খানির","জনকে","গুলির","গুলিকে","গুলার","গুলাকে","গুলোর","গুলোকে","টা", "টে", "টো", "টি", "টু", "টুকু", "টুকুন", "টাক", "খানা", "খানি",
			"গাছা", "গাছি", "জন", "থান", "পাটি", "গুলি", "গুলা", "গুলো");

	public GrammerCheckerDto hasError(List<String> words) {
		GrammerCheckerDto ret = new GrammerCheckerDto();
		ret.wordSuggestion = new ArrayList<>();
		for (int i = 0; i < words.size() - 1; i++) {
			
			if ((!words.get(i).equals("এই")) && (!words.get(i).equals("ঐ"))) 
				continue;
			String str = getNirdeshokWord(words.get(i + 1));
			if (!str.equals("")) {
				WordSuggestion sug = new WordSuggestion();
				sug.wordName = words.get(i + 1);
				sug.wordType = "nirdeshok";
				sug.suggestedWord = str;
				sug.wordIndex = i + 1;
				ret.wordSuggestion.add(sug);
				continue;
			}
			if (i + 2 >= words.size())
				break;
			str = getNirdeshokWord(words.get(i + 2));
			if (!str.equals("")) {
				WordSuggestion sug = new WordSuggestion();
				sug.wordName = words.get(i + 2);
				sug.wordType = "nirdeshok";
				sug.suggestedWord = str;
				sug.wordIndex = i + 2;
				ret.wordSuggestion.add(sug);
			}

		}
		if (ret.wordSuggestion.size() > 0) {
			ret.isValidSentence = false;
			ret.errorType = "wrong-nirdeshok";
			// TODO: create constant type for error type
		}
		return ret;
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
