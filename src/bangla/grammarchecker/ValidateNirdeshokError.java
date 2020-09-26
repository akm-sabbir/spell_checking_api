package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bangla.tokenizer.WordTokenizer;

public class ValidateNirdeshokError implements BanglaGrammerChecker{
	static List<String> nirdeshok = Arrays.asList("টা","টে","টো","টি","টু","টুকু","টুকুন","টাক","খানা","খানি","গাছা","গাছি","জন","থান","পাটি","গুলি","গুলা","গুলো");
	static WordTokenizer WT = new WordTokenizer();
	public GrammerCheckerDto hasError(String sentence) {
		GrammerCheckerDto ret = new GrammerCheckerDto();
		ret.wordSuggestion = new ArrayList<>();
		WT.set_text(sentence);
		List<String> words = WT._tokenization();
		for(int i=0;i<words.size()-1;i++) {
			if(isEiOi(words.get(i))) {
				String str = isWordContainNirdeshok(words.get(i+1));
				if(!str.equals("")) {
					WordSuggestion sug = new WordSuggestion();
					sug.wordName = words.get(i+1);
					sug.wordType = "nirdeshok";
					sug.suggestedWord = str;
                    sug.wordIndex = i+1;
					ret.wordSuggestion.add(sug);
				} else if(i+2<words.size()){
					str = isWordContainNirdeshok(words.get(i+2));
					if(!str.equals("")) {
						WordSuggestion sug = new WordSuggestion();
						sug.wordName = words.get(i+2);
						sug.wordType = "nirdeshok";
						sug.suggestedWord = str;
                        sug.wordIndex = i+2;
						ret.wordSuggestion.add(sug);
					} 
				}
			}
		}
		if(ret.wordSuggestion.size() > 0) {
			ret.isValidSentence = false;
			ret.errorType = "wrong-nirdeshok";
		} else {
			ret.isValidSentence = true;
		}
		return ret;
	}
	private static boolean isEiOi(String word) {
		if(word.equals("এই") || word.equals("ঐ")) {
			return true;
		}
		return false;
	}
	private static String isWordContainNirdeshok(String word) {
		for(int i=0;i<nirdeshok.size();i++) {
			int ln=nirdeshok.get(i).length();
			if(word.length()<ln) continue;
			if(word.substring(word.length()-ln, word.length()).equals(nirdeshok.get(i))) {
				return word.substring(0, word.length()-ln);
			}
		}
		return "";
	}
}
