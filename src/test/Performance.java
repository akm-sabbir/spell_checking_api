package test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import bangla.spellchecker.SpellCheckingDto;
import bangla.tokenizer.WordTokenizer;
import repository.RepositoryManager;
import word_content.Word_contentService;

public class Performance 
{
	public static Logger logger = Logger.getLogger(Performance.class);
	
	public static void main(String[] args) throws Exception
	{
		
		//dictionaryInitializer di = new dictionaryInitializer();
		//di.contextInitialized(null);
		
		calculate_batch_performance();
	}
	
	
	public static void calculate_batch_performance() throws Exception
	{
		TestinDAO testin_dao = new TestinDAO();
		
		Gson gson = new Gson();
		
		int page_no = 0;
		
		while(true)
		{
			List<TestinDTO> testin_dto_list = testin_dao.get_paginated_Testin(page_no, 50);
			
			if(testin_dto_list.size() <= 0) break;
			
			for(TestinDTO testin_dto : testin_dto_list)
			{
				float detection_precision = -1;
				float detection_recall = -1;
				float correction_precision = -1;
				float correction_recall = -1;
				String log = "";
				long time1 = System.currentTimeMillis();
				long time2 = System.currentTimeMillis();
				
				try
				{
					WordTokenizer wt  = new WordTokenizer();
					wt.set_text(testin_dto.corrected_content);
					List<String> corrected_tokens = wt._tokenization();
					
					Word_contentService wcs = new Word_contentService();
					time1 = System.currentTimeMillis();
					Map<Integer, SpellCheckingDto> original_result_list = wcs.executeSpellChecking(testin_dto.original_content);
					time2 = System.currentTimeMillis();
					
					List<Map<String, String>> detection_true_positive = calculate_detection_true_positive(corrected_tokens, original_result_list);
					List<Map<String, String>> detection_false_positive = calculate_detection_false_positive(corrected_tokens, original_result_list);
					List<Map<String, String>> detection_false_negative = calculate_detection_false_negative(corrected_tokens, original_result_list);
					
					if((detection_true_positive.size() + detection_false_positive.size()) != 0)
						detection_precision = 100 * detection_true_positive.size() / (detection_true_positive.size() + detection_false_positive.size());
					
					if((detection_true_positive.size() + detection_false_negative.size()) != 0)
						detection_recall = 100 * detection_true_positive.size() / (detection_true_positive.size() + detection_false_negative.size());
					
					List<Map<String, String>> correction_true_positive = calculate_correction_true_positive(corrected_tokens, original_result_list);
					List<Map<String, String>> correction_false_positive = calculate_correction_false_positive(corrected_tokens, original_result_list);
					List<Map<String, String>> correction_false_negative = calculate_correction_false_negative(corrected_tokens, original_result_list);
					
					if((correction_true_positive.size() + correction_false_positive.size()) != 0)
						correction_precision = 100 * correction_true_positive.size() / (correction_true_positive.size() + correction_false_positive.size());
					
					if((correction_true_positive.size() + correction_false_negative.size()) != 0)
						correction_recall = 100 * correction_true_positive.size() / (correction_true_positive.size() + correction_false_negative.size());
					
					log += "Predicted Content: " + System.lineSeparator();
					log += gson.toJson(original_result_list) + System.lineSeparator() + System.lineSeparator();
					
					log += "Detection True Positive: " + System.lineSeparator();
					log += gson.toJson(detection_true_positive) + System.lineSeparator() + System.lineSeparator();
					log += "Detection False Positive: " + System.lineSeparator();
					log += gson.toJson(detection_false_positive) + System.lineSeparator() + System.lineSeparator();					
					log += "Detection False Negative: " + System.lineSeparator();
					log += gson.toJson(detection_false_negative) + System.lineSeparator() + System.lineSeparator();
					
					log += "Correction True Positive: " + System.lineSeparator();
					log += gson.toJson(correction_true_positive) + System.lineSeparator() + System.lineSeparator();
					log += "Correction False Positive: " + System.lineSeparator();
					log += gson.toJson(correction_false_positive) + System.lineSeparator() + System.lineSeparator();					
					log += "Correction False Negative: " + System.lineSeparator();
					log += gson.toJson(correction_false_negative) + System.lineSeparator() + System.lineSeparator();						
				}
				catch(Exception e)
				{
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					log += sw.toString();
				}
				finally
				{
					TestoutDTO testout_dto = new TestoutDTO();
					
					testout_dto.content_id = testin_dto.ID;
					testout_dto.word_error_type = "NON_WORD_ERROR";
					testout_dto.detection_precision = detection_precision;
					testout_dto.detection_recall = detection_recall;
					testout_dto.correction_precision = correction_precision;
					testout_dto.correction_recall = correction_recall;
					testout_dto.request_time = time1;
					testout_dto.execution_time = (time2-time1);
					
					testout_dto.detailed_log = log;
					
					TestoutDAO testout_dao = new TestoutDAO();
					testout_dao.insertTestout(testout_dto);
					
					logger.info(gson.toJson(testout_dto));
					System.out.println(gson.toJson(testout_dto));
				}
			}
			
			page_no++;
		}
	}
	
	public static List<Map<String, String>> calculate_detection_true_positive(List<String> corrected_tokens, Map<Integer, SpellCheckingDto> original_result_list)
	{
		if(corrected_tokens == null || original_result_list == null) return null;
		
		List<Map<String, String>> tp = new ArrayList<Map<String, String>>();
		Map<String, String> token = null;
		
		for(Integer index : original_result_list.keySet())
		{
			SpellCheckingDto info = original_result_list.get(index);
			
			String corrected_word = null;
			
			if(info.isCorrect == 0)
			{
				if(index < corrected_tokens.size())
					corrected_word = corrected_tokens.get(index);
				
				if(corrected_word == null || !info.word.equalsIgnoreCase(corrected_word))
				{
					token = new HashMap<String, String>();
					token.put("original", info.word);
					token.put("corrected", corrected_word);

					tp.add(token);					
				}
			}
		}
		
		return tp;
	}
	
	public static List<Map<String, String>> calculate_detection_false_positive(List<String> corrected_tokens, Map<Integer, SpellCheckingDto> original_result_list)
	{
		if(corrected_tokens == null || original_result_list == null) return null;
		
		List<Map<String, String>> fp = new ArrayList<Map<String, String>>();
		Map<String, String> token = null;
		
		for(Integer index : original_result_list.keySet())
		{
			SpellCheckingDto info = original_result_list.get(index);
			
			String corrected_word = null;
			
			if(info.isCorrect == 0)
			{
				if(index < corrected_tokens.size())
					corrected_word = corrected_tokens.get(index);
				
				if(corrected_word != null && info.word.equalsIgnoreCase(corrected_word))
				{
					token = new HashMap<String, String>();
					token.put("original", info.word);
					token.put("corrected", corrected_word);

					fp.add(token);					
				}
			}			
		}
		
		return fp;		
	}	
	
	public static List<Map<String, String>> calculate_detection_false_negative(List<String> corrected_tokens, Map<Integer, SpellCheckingDto> original_result_list)
	{
		if(corrected_tokens == null || original_result_list == null) return null;
		
		List<Map<String, String>> fn = new ArrayList<Map<String, String>>();
		Map<String, String> token = null;
		
		for(Integer index : original_result_list.keySet())
		{
			SpellCheckingDto info = original_result_list.get(index);
			
			String corrected_word = null;
			
			if(info.isCorrect == 1)
			{
				if(index < corrected_tokens.size())
					corrected_word = corrected_tokens.get(index);
				
				if(corrected_word == null || !info.word.equalsIgnoreCase(corrected_word))
				{
					token = new HashMap<String, String>();
					token.put("original", info.word);
					token.put("corrected", corrected_word);

					fn.add(token);					
				}
			}				
		}
		
		return fn;
	}
	
	public static List<Map<String, String>> calculate_correction_true_positive(List<String> corrected_tokens, Map<Integer, SpellCheckingDto> original_result_list)
	{
		if(corrected_tokens == null || original_result_list == null) return null;
		
		List<Map<String, String>> tp = new ArrayList<Map<String, String>>();
		return tp;
	}
	
	public static List<Map<String, String>> calculate_correction_false_positive(List<String> corrected_tokens, Map<Integer, SpellCheckingDto> original_result_list)
	{
		if(corrected_tokens == null || original_result_list == null) return null;
		
		List<Map<String, String>> fp = new ArrayList<Map<String, String>>();
		return fp;
	}	
	
	public static List<Map<String, String>> calculate_correction_false_negative(List<String> corrected_tokens, Map<Integer, SpellCheckingDto> original_result_list)
	{
		if(corrected_tokens == null || original_result_list == null) return null;
		
		List<Map<String, String>> fn = new ArrayList<Map<String, String>>();
		return fn;
	}	
	
	
}
