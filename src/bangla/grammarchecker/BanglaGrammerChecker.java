package bangla.grammarchecker;

import java.util.List;


public interface BanglaGrammerChecker {
	//GrammerCheckerDto hasError(String sentence);
	GrammerCheckerDto hasError(List<String> words);
}
