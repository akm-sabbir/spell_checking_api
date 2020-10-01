package bangla.grammarchecker;

import java.util.List;

import bangla.spellchecker.SpellCheckingDto;


public interface BanglaGrammerChecker {
	//GrammerCheckerDto hasError(String sentence);
	List<SpellCheckingDto> hasError(List<String> words);
}
