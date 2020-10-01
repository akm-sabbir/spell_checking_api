package word_content;
import java.util.List;
import java.util.HashMap;

import bangla.WithTrie.WordSuggestionV3;
import bangla.spellchecker.SpellCheckingDto;
import bangla.spellchecker.SpellChecker;
import bangla.tokenizer.SentenceTokenizer;
import bangla.tokenizer.TextNormalization;

public class Word_contentService 
{

	WordSuggestionV3 wordSuggestion;
	SpellChecker sp_checker;
	SentenceTokenizer gr_checker;
	
	public HashMap<Integer, SpellCheckingDto> executeSpellChecking(String text_data)
	{
		TextNormalization textNormalizer = new TextNormalization();
		sp_checker = new SpellChecker();
		gr_checker = new SentenceTokenizer();
		
		if (text_data.length() == 0) {
//			logger.debug("End of request processing: " );
//			response.sendError(400, "could not understand input request text is either empty or noninterpretable");
			return null;
		}
		List<String> tokenized_sentence = null;
		HashMap<Integer, SpellCheckingDto> result_list = null;
		try {
			tokenized_sentence = gr_checker.getTokenizedSentence(text_data);
		}catch(Exception ex) {
//			logger.info("sentence tokenization process is throwing exception: " + ex.getMessage());
//			logger.debug("End of request processing: " );
//			response.sendError(400, "could not understand input request text is either empty or noninterpretable");
			return null;
		}
		//List<String> tokenized_data = sp_checker.getTokenizedWord(text_data);
		if(tokenized_sentence.size() <= 0 ) {
//			logger.debug("End of request processing: " );
//			response.sendError(400, "could not understand input request text is either empty or noninterpretable");
			return null;
		}
		
		try {
			HashMap<Integer, String> indexedWords = textNormalizer.getIndexedWords(tokenized_sentence);
			result_list = sp_checker.getWordValidationInfo(text_data, indexedWords);
			System.out.println("Total Tokenized words after validation process completion " + result_list.size());
			result_list = gr_checker.getSentenceValidationInfo(tokenized_sentence, result_list);
			System.out.println("Total Valided words after process completion" + result_list.size());
		}catch(Exception ex ) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			//logger.info("Something wrong in the process" + ex.getMessage());
		}
		
		return result_list;
		
	}
}