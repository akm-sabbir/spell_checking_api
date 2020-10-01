package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.List;

import bangla.ErrorsInBanglaLanguage;
import bangla.WithTrie.BitMasking;
import bangla.WithTrie.TrieNodeWithList;
import bangla.spellchecker.Pair;
import bangla.spellchecker.SpellCheckingDto;

public class NoSpaceBetweenWordsChecker implements BanglaGrammerChecker {
	static List<TrieNodeWithList> dictionaries = new ArrayList<>();

	public List<SpellCheckingDto> hasError(List<String> words) {
		List<SpellCheckingDto> spellCheckerDtos = new ArrayList<>();
		int pos = -1;
		int continuous;
		for (String str : words) {
			continuous = 0;
			for (TrieNodeWithList dictionary : dictionaries) {
				if (dictionary.searchWord(str).isFound) {
					continuous = 1;
					break;
				}
				// TODO: check a word is combined of two different dictionary words
				pos = isTwoCombinedWord(str, dictionary);
				if (pos != -1 && pos != str.length() - 1)
					break;
			}

			if (continuous == 1) {
				spellCheckerDtos.add(new SpellCheckingDto());
				continue;
			}

			if (pos != -1 && pos != str.length() - 1) {
				SpellCheckingDto dto = new SpellCheckingDto();
				int errorType = BitMasking.setBitAt(0, 1);
				dto.word = str;
				dto.errorType = BitMasking.setBitAt(errorType, ErrorsInBanglaLanguage.nospace);
				dto.suggestion = new ArrayList<Pair>();
				String suggWord = str.substring(0, pos + 1) + " " + str.substring(pos + 1, str.length());
				dto.suggestion.add(new Pair(suggWord, -1));
				spellCheckerDtos.add(dto);
				continue;
			}
			spellCheckerDtos.add(new SpellCheckingDto());
		}

		return spellCheckerDtos;
	}

//	public static void setDictionary(TrieNode root) {
//			dictionary = root;
//	}
	public static void registerDictionary(TrieNodeWithList dictionary) {
		dictionaries.add(dictionary);
		return;
	}

	private static int isTwoCombinedWord(String word, TrieNodeWithList dictionary) {
		int pos = dictionary.searchWord(word, true).position;
		if (pos == -1 || pos == word.length() - 1)
			return pos;
		int ret = pos;
		String sub = word.substring(pos + 1, word.length());
		pos = dictionary.searchWord(sub, true).position;
		if (pos == sub.length() - 1)
			return ret;
		return -1;
	}
}
