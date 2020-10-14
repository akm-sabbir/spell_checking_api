package bangla;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.*;

import org.apache.commons.collections4.iterators.EntrySetMapIterator;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import bangla.WithTrie.WordSuggestionV3;
import bangla.spellchecker.SpellCheckingDto;
import bangla.spellchecker.SpellChecker;
import bangla.tokenizer.SentenceTokenizer;
import bangla.tokenizer.TextNormalization;
import bangla.tokenizer.WordTokenizer;
import word_content.Word_contentDAO;
import word_content.Word_contentServlet;

public class SpellAndGrammarChecker {
	private static final long serialVersionUID = 1L;
    public static Logger logger = Logger.getLogger(Word_contentServlet.class);
    String tableName = "word_content";
    String tempTableName = "word_content_temp";
	Word_contentDAO word_contentDAO;
	int maxMissmatch=100;
	SpellCheckingDto suggested_word;
	TextNormalization textNormalizer = new TextNormalization();
	private SpellChecker spellChecker = new SpellChecker();
	private SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
    private Gson gson = new Gson();

	public String check(String text_data, int option) {
		
		
		logger.info("Recieved a request to process: " );
		if (text_data.length() == 0) {
			logger.debug("End of request processing: " );
			return null;
		}
		List<String> tokenized_sentence = null;
		HashMap<Integer, SpellCheckingDto> result_list = null;
		try {
			tokenized_sentence = sentenceTokenizer.getTokenizedSentence(text_data);
			if(tokenized_sentence.size() <= 0 ) {
				logger.debug("End of request processing: " );
				return null;
			}
		}catch(Exception ex) {
			logger.info("sentence tokenization process is throwing exception: " + ex.getMessage());
			logger.debug("End of request processing: " );
			return null;
		}
		
		try {
			StringBuilder sbData = new StringBuilder(text_data);
			Map<Integer, String> indexedWords = textNormalizer.getIndexedWords(tokenized_sentence);
			if(option == 3) {
				result_list = spellChecker.getWordValidationInfo(sbData, indexedWords);
				logger.debug("Total Tokenized words after validation process completion " + result_list.size());
				result_list = sentenceTokenizer.getSentenceValidationInfo(tokenized_sentence, result_list);
				logger.debug("Total Valided words after process completion" + result_list.size());
			}else if(option == 2) {
				result_list = spellChecker.getWordValidationInfo(sbData, indexedWords);
				logger.debug("Total Tokenized words after validation process completion " + result_list.size());
			}else {
				
			}
			
		}catch(Exception ex ) {
			logger.fatal(ex);
		}
		//gson formitting should be done explicityly
		String final_result = this.gson.toJson(result_list);
		
		return final_result;
	}
}
