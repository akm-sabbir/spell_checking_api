package bangla.grammarchecker;


public interface BanglaGrammerChecker {
	GrammerCheckerDto hasError(String sentence);
	//GrammerCheckerDto validateSentence(List<String> words);
}
