package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.List;

import bangla.spellchecker.spell_checking_dto;
import bangla.tokenizer.WordTokenizer;

public class ValidateShadhuCholitMixure implements BanglaGrammerChecker{
	static TrieNode cholitToShadhu = new TrieNode();
	static TrieNode shadhuToCholit = new TrieNode();
	static WordTokenizer WT = new WordTokenizer();
	public static void buildTrie(List<spell_checking_dto> words) {
		/*
		List<String> words = new ArrayList<>();
		try {
			words = ReadCsvFile.getAllWordsFromCsvFile();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		spell_checking_dto mixed;
		for(int i=0;i<words.size();i++) {
			mixed = words.get(i);
			//if(mixed.size()>1) {
			if (mixed != null) {
				if(mixed.word != null &&!mixed.word.equals("invalid") && mixed.wordType != null && !mixed.wordType.equals("invalid")) {
					TrieSadhuChol.insert(mixed.word, mixed.wordType, cholitToShadhu);
					TrieSadhuChol.insert(mixed.wordType, mixed.word, shadhuToCholit);
				}
			}
		}
	}
	public GrammerCheckerDto hasError(String sentence) {
		GrammerCheckerDto ret = new GrammerCheckerDto();
		WT.set_text(sentence);
		List<String> words = WT._tokenization();
		int cholitCount=0; int shadhuCount=0;
		List<WordSuggestion> suggestion = new ArrayList<>();
        int index=0;
		for(String word: words) {
            word=refactorWord(word);
			Tuple res = TrieSadhuChol.searchWord(word, cholitToShadhu);
			WordSuggestion temp = new WordSuggestion();
			
			if(res.found==true) { // this is possible cholit word
				if(res.viceVersaWord!=null && !res.viceVersaWord.equals("") && !res.viceVersaWord.equals("NotFound") && !res.viceVersaWord.equals("invalid") && !word.equals(res.viceVersaWord)) {
					cholitCount++;
					temp.wordName=word;
					temp.wordType="cholit";
                    temp.wordIndex = index;
					temp.suggestedWord=res.viceVersaWord;
					suggestion.add(temp);
				}
				
			} else {
				res = TrieSadhuChol.searchWord(word, shadhuToCholit);
				if(res.found==true) { // this is possible shadhu word
					if(res.viceVersaWord!=null && !res.viceVersaWord.equals("") && !res.viceVersaWord.equals("NotFound") && !res.viceVersaWord.equals("invalid") && !word.equals(res.viceVersaWord)) {
						shadhuCount++;
						temp.wordName=word;
						temp.wordType="shadhu";
                        temp.wordIndex = index;
						temp.suggestedWord=res.viceVersaWord;
						suggestion.add(temp);
					}
					
				}
			}
			index++;
		}
		ret.wordSuggestion=suggestion;
		if(cholitCount>0 && shadhuCount>0) {
			ret.isValidSentence=false;
			ret.errorType="ShadhuCholitMixure";
		} else {
			ret.isValidSentence=true;
			ret.errorType="NoErrorFound";
		}
		return ret;
	}
    private static String refactorWord(String word) {
		int len=word.length();
		if(word.charAt(len-1)=='।') {
			word=word.substring(0, len-1);
		} else if(word.charAt(len-1)==',') {
			word=word.substring(0, len-1);
		}
		return word;
	}
}
