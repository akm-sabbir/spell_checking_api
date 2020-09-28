package bangla.grammarchecker;

import java.util.List;

public class GrammerCheckerDto {
	public boolean isValidSentence = true;
	public String errorType;
	public List<WordSuggestion> wordSuggestion;
}
