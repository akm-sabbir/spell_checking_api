package bangla;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


import org.apache.log4j.Logger;

import bangla.spellchecker.SpellCheckingDto;
import bangla.spellchecker.SpellChecker;
import bangla.tokenizer.SentenceTokenizer;
import bangla.tokenizer.TextNormalization;
import word_content.Word_contentServlet;

public class SpellAndGrammarChecker {
	
    public static Logger logger = Logger.getLogger(Word_contentServlet.class);

    public static final int OPTION_SPELL_CHECKING=2;
    public static final int OPTION_SPELL_AND_GRAMMAR_CHECKING=3;
    
    TextNormalization textNormalizer;
    SentenceTokenizer sentenceTokenizer;
    SpellChecker spellChecker;
	

	public SpellAndGrammarChecker()
	{
	    textNormalizer = new TextNormalization();
		sentenceTokenizer = new SentenceTokenizer();
		spellChecker = new SpellChecker();
	}

	public HashMap<Integer, SpellCheckingDto> check(String param_str, int option) 
	{
		logger.debug("SpellAndGrammarChecker:check("+param_str+", "+option+")");
		if (param_str.length() == 0) {
			logger.debug("Given text length is zero." );
			return null;
		}
		
		String text_data = textNormalizer.normalizeText(param_str);
		
		List<String> tokenized_sentence = null;
		try {
			tokenized_sentence = sentenceTokenizer.getTokenizedSentence(text_data);
			if(tokenized_sentence.size() <= 0 ) {
				logger.debug("Received 0 tokenized sentence." );
				return null;
			}
		}catch(Exception ex) {
			logger.info("sentence tokenization process is throwing exception: " + ex.getMessage());
			logger.debug("End of request processing: " );
			return null;
		}
		
		HashMap<Integer, SpellCheckingDto> result_list = null;
		try {
			StringBuilder sbData = new StringBuilder(text_data);
			Map<Integer, String> indexedWords = textNormalizer.getIndexedWords(tokenized_sentence);
			
			switch(option)
			{
			case OPTION_SPELL_AND_GRAMMAR_CHECKING:
					result_list = spellChecker.getWordValidationInfo(sbData, indexedWords);
					logger.debug("Total Tokenized words after validation process completion " + result_list.size());
					result_list = sentenceTokenizer.getSentenceValidationInfo(tokenized_sentence, result_list);
					logger.debug("Total Valided words after process completion" + result_list.size());
					break;
			case OPTION_SPELL_CHECKING:
					result_list = spellChecker.getWordValidationInfo(sbData, indexedWords);
					logger.debug("Total Tokenized words after validation process completion " + result_list.size());
					break;
			default:
			break;
				
				
			}
			
		}catch(Exception ex ) {
			logger.fatal(ex);
		}
		//gson formitting should be done explicityly
		
		return result_list;
	}
}
