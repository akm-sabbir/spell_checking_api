package test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bangla.dao.AnnotatedWordRepository;
import bangla.dao.DictionaryRepository;
import bangla.dao.GradedPronoun;
import bangla.dao.NamedEntityRepository;
import bangla.dao.NaturalErrorRepository;
import bangla.dao.SadhuCholitMixture;
import bangla.dao.SubjectVerbRepository;
import bangla.grammarchecker.GrammerCheckerFactory;
import bangla.tokenizer.WordTokenizer;
import repository.RepositoryManager;
import test.metrics.PerformanceMetricCalculator;
import test.metrics.PerformanceMetricCalculatorImpl;
import word_content.Word_contentService;

public class Performance 
{
	public static Logger logger = Logger.getLogger(Performance.class);
	
	public static void main(String[] args) throws Exception
	{
		initializeData();
		calculateBatchPerformance();
	}
	
	
	private static void initializeData()
	{
    	GrammerCheckerFactory.initializeRegisteredCheckers();
    	RepositoryManager.getInstance().addRepository(AnnotatedWordRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(DictionaryRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NamedEntityRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NaturalErrorRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(GradedPronoun.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(SubjectVerbRepository.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(SadhuCholitMixture.getInstance(),true);		
	}
	
	private static void calculateBatchPerformance() throws Exception
	{
		TestinDAO testin_dao = new TestinDAO();
		
//		Gson gson = new Gson();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		int page_no = 0;
		
		while(true)
		{
			List<TestinDTO> testinDTOList = testin_dao.get_paginated_Testin(page_no, 50);
			
			if(testinDTOList.size() <= 0) break;
			
			for(TestinDTO testinDTO : testinDTOList)
			{
				String log = "";
				long time1 = System.currentTimeMillis();
				long time2 = System.currentTimeMillis();
				long wc = 0;
				
				TestoutDTO testoutDTO = new TestoutDTO();
				
				PerformanceMetricCalculator performanceMetricCalculator = new PerformanceMetricCalculatorImpl();
				
				try
				{
					
					Word_contentService wcs = new Word_contentService();
					time1 = System.currentTimeMillis();
					String predictedContent = wcs.executeSpellChecking(testinDTO.originalContent, 3);	//	3: both option
					time2 = System.currentTimeMillis();
					
//					wc = originalResultList.size();
					
					log += "Predicted Content: " + System.lineSeparator();
					log += predictedContent + System.lineSeparator() + System.lineSeparator();

					List<Object> alignment = performanceMetricCalculator.formAlignment(testinDTO, predictedContent);
					
					log += "Total Alignment: " + System.lineSeparator();
					log += gson.toJson(alignment) + System.lineSeparator() + System.lineSeparator();					
					
					log += performanceMetricCalculator.populateDetectionMetricsAndGetLog(alignment, testoutDTO);
					log += performanceMetricCalculator.populateCorrectionMetricsAndGetLog(alignment, testoutDTO);
					
//					log += performanceMetricCalculator.executeTestCases(testinDTO, originalResultList);
				}
				catch(Exception e)
				{
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					log += sw.toString();
				}
				finally
				{
					//TestoutDTO testout_dto = new TestoutDTO();
					
					testoutDTO.contentId = testinDTO.id;
					testoutDTO.wordErrorType = "NON_WORD_ERROR";
//					testoutDTO.wordErrorType = "NON_WORD_ERROR (assuming UNKNOWN as CORRECT WORD)";
//					testoutDTO.wordErrorType = "NON_WORD_ERROR (assuming UNKNOWN as ERROR WORD)";
					testoutDTO.requestTime = time1;
					testoutDTO.executionTime = (time2-time1);
					testoutDTO.wordCount = wc;
					
					testoutDTO.detailedLog = log;
					
					TestoutDAO testoutDAO = new TestoutDAO();
					testoutDAO.insertTestout(testoutDTO);
					
					logger.info(gson.toJson(testoutDTO));
					System.out.println(gson.toJson(testoutDTO));
				}
			}
			
			page_no++;
		}
	}
}
