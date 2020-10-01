package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bangla.ErrorsInBanglaLanguage;
import bangla.WithTrie.BitMasking;
import bangla.WithTrie.TrieNodeSearchResult;
import bangla.WithTrie.TrieNodeWithList;
import bangla.dao.GrammarDto;
import bangla.spellchecker.Pair;
import bangla.spellchecker.SpellCheckingDto;
import bangla.tokenizer.WordTokenizer;

public class ShadhuCholitMixureChecker implements BanglaGrammerChecker {
	private static Logger logger = LogManager.getLogger(ShadhuCholitMixureChecker.class);
	static TrieNodeWithList cholitToShadhu = new TrieNodeWithList();
	static TrieNodeWithList shadhuToCholit = new TrieNodeWithList();
	static WordTokenizer WT = new WordTokenizer();

	public static void buildTrie(List<GrammarDto> words) {

		for (GrammarDto mixed : words) {
			if (mixed == null)
				continue;
			boolean isValidSadhu = (mixed.shadhu != null && !mixed.shadhu.equals("invalid"));
			boolean isValidCholito = (mixed.cholit != null && !mixed.cholit.equals("invalid"));
			if (isValidSadhu && isValidCholito) {
				cholitToShadhu.insert(mixed.cholit, mixed.shadhu);
				shadhuToCholit.insert(mixed.shadhu, mixed.cholit);
			}
		}
	}

	public List<SpellCheckingDto> hasError(List<String> words) {
		List<SpellCheckingDto> spellCheckerDtos = new ArrayList<>();
		int cholitCount = 0;
		int shadhuCount = 0;
		for (String word : words) {
			TrieNodeSearchResult res = cholitToShadhu.searchWord(word);
			// If corresponding sadhu word is correct then we are adding it to suggestions
			if (res.isFound && res.viceVersaWord != null && !res.viceVersaWord.equals("")
					&& !res.viceVersaWord.equals("NotFound") && !res.viceVersaWord.equals("invalid")
					&& !word.equals(res.viceVersaWord)) {
				cholitCount++;

				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = word;
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.sadhu);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(res.viceVersaWord, -1));
				spellCheckerDtos.add(dto);
				continue;
			}

			res = shadhuToCholit.searchWord(word);
			// If corresponding cholito word is correct then we are adding it to suggestions
			if (res.isFound && res.viceVersaWord != null && !res.viceVersaWord.equals("")
					&& !res.viceVersaWord.equals("NotFound") && !res.viceVersaWord.equals("invalid")
					&& !word.equals(res.viceVersaWord)) {
				shadhuCount++;
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = word;
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.sadhu);
				dto.suggestion = new ArrayList<Pair>();
				dto.suggestion.add(new Pair(res.viceVersaWord, -1));
				spellCheckerDtos.add(dto);
				continue;
			}
			spellCheckerDtos.add(new SpellCheckingDto());
		}
		if (cholitCount > 0 && shadhuCount > 0) {
			return spellCheckerDtos;
		} else {
			List<SpellCheckingDto> noErrorSpellCheckerDtos = new ArrayList<>();
			for(int i=0;i<words.size();i++) noErrorSpellCheckerDtos.add(new SpellCheckingDto());
			return noErrorSpellCheckerDtos;
		}
	}
}
