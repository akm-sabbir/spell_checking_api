package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bangla.spellchecker.spell_checking_dto;
import bangla.tokenizer.WordTokenizer;


public class ShadhuCholitMixureChecker implements BanglaGrammerChecker {
	private static Logger logger = LogManager.getLogger(ShadhuCholitMixureChecker.class);
	static TrieNode cholitToShadhu = new TrieNode();
	static TrieNode shadhuToCholit = new TrieNode();
	static WordTokenizer WT = new WordTokenizer();

	public static void buildTrie(List<spell_checking_dto> words) {

		for (spell_checking_dto mixed : words) {
			if (mixed == null)
				continue;
			boolean isValidSadhu = (mixed.wordType != null && !mixed.wordType.equals("invalid"));
			boolean isValidCholito = (mixed.word != null && !mixed.word.equals("invalid"));
			if (isValidSadhu && isValidCholito) {
				cholitToShadhu.insert(mixed.word, mixed.wordType);
				shadhuToCholit.insert(mixed.wordType, mixed.word);
			}
		}
//		for (spell_checking_dto mixed : pronoun) {
//			if (mixed == null)
//				continue;
//			boolean isValidCholito = (mixed.wordType != null && !mixed.wordType.equals("") && !mixed.wordType.equals("invalid"));
//			boolean isValidSadhu = (mixed.word != null && !mixed.word.equals("") && !mixed.word.equals("invalid"));
//			if (isValidSadhu && isValidCholito) {
//				System.out.println(mixed.wordType + " " + mixed.word);
//				cholitToShadhu.insert(mixed.wordType, mixed.word);
//				shadhuToCholit.insert(mixed.word, mixed.wordType);
//			}
//		}
	}

	public GrammerCheckerDto hasError(List<String> words) {
		GrammerCheckerDto ret = new GrammerCheckerDto();
		int cholitCount = 0;
		int shadhuCount = 0;
		List<WordSuggestion> suggestion = new ArrayList<>();
		int index = -1;
		for (String word : words) {
			index++;
			TrieNodeSearchResult res = cholitToShadhu.searchWord(word);
			WordSuggestion temp = new WordSuggestion();

			if (res.isFound) { // this is possible cholit word
				// If corresponding sadhu word is correct then we are adding it to suggestions
				if (res.viceVersaWord != null && !res.viceVersaWord.equals("") && !res.viceVersaWord.equals("NotFound")
						&& !res.viceVersaWord.equals("invalid") && !word.equals(res.viceVersaWord)) {
					cholitCount++;
					temp.wordName = word;
					temp.wordType = "cholit";
					temp.wordIndex = index;
					temp.suggestedWord = res.viceVersaWord;
					suggestion.add(temp);
				}
				continue;

			}

			res = shadhuToCholit.searchWord(word);
			if (res.isFound) { // this is possible shadhu word
				// If corresponding cholito word is correct then we are adding it to suggestions
				if (res.viceVersaWord != null && !res.viceVersaWord.equals("") && !res.viceVersaWord.equals("NotFound")
						&& !res.viceVersaWord.equals("invalid") && !word.equals(res.viceVersaWord)) {
					shadhuCount++;
					temp.wordName = word;
					temp.wordType = "shadhu";
					temp.wordIndex = index;
					temp.suggestedWord = res.viceVersaWord;
					suggestion.add(temp);
				}

			}
		}
		if (cholitCount > 0 && shadhuCount > 0) {
			ret.wordSuggestion = suggestion;
			ret.isValidSentence = false;
			ret.errorType = "ShadhuCholitMixure";
		} else {
			ret.isValidSentence = true;
			ret.errorType = "NoErrorFound";
		}
		return ret;
	}
}
