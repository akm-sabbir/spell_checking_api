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
import test.metrics.BatchMetricInformation;
import test.metrics.PerformanceMetricCalculator;
import test.metrics.PerformanceMetricCalculatorImpl;
import test.metrics.PrecisionRecallPair;
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
		TestoutDAO testoutDAO = new TestoutDAO();
		BatchTestDAO batchTestDAO = new BatchTestDAO();
		String guid = UUID.randomUUID().toString();
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		String[] complexity = {"easy", "moderate", "complex"};
		String wordErrorType = "NON_WORD_ERROR";
		
		for(String comp : complexity)
		{
			int page_no = 0;			
			PerformanceMetricCalculator performanceMetricCalculator = new PerformanceMetricCalculatorImpl();
			BatchMetricInformation batchMetricInformation = new BatchMetricInformation();
			BatchTestDTO batchDto = new BatchTestDTO();
			batchDto.guid = guid;
					
			while(true)
			{
				List<TestinDTO> testinDTOList = testin_dao.get_paginated_Testin(page_no, 50, comp);
				
				if(testinDTOList.size() <= 0) break;
				
				for(TestinDTO testinDTO : testinDTOList)
				{
					String log = "";
					long time1 = System.currentTimeMillis();
					long time2 = System.currentTimeMillis();
					long wc = 0;
					
					TestoutDTO testoutDTO = new TestoutDTO();
					testoutDTO.guid = batchDto.guid;
					
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
						
						log += performanceMetricCalculator.populateDetectionMetricsAndGetLog(alignment, testoutDTO, batchMetricInformation);
						log += performanceMetricCalculator.populateCorrectionMetricsAndGetLog(alignment, testoutDTO, batchMetricInformation);
						
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
						testoutDTO.complexity = testinDTO.complexity;
						testoutDTO.wordErrorType = wordErrorType;
						testoutDTO.requestTime = time1;
						testoutDTO.executionTime = (time2-time1);
						testoutDTO.wordCount = wc;
						
						testoutDTO.detailedLog = log;
						
						testoutDAO.insertTestout(testoutDTO);
						
						logger.info(gson.toJson(testoutDTO));
						System.out.println(gson.toJson(testoutDTO));
					}
				}
				
				page_no++;
			}
			
			PrecisionRecallPair batchDetectionPrecision = performanceMetricCalculator.calculateMetrics(batchMetricInformation.detectionCalculationInformation.truePositive, batchMetricInformation.detectionCalculationInformation.falsePositive, batchMetricInformation.detectionCalculationInformation.falseNegative);
			PrecisionRecallPair batchCorretionPrecision = performanceMetricCalculator.calculateMetrics(batchMetricInformation.correctionCalculationInformation.truePositive, batchMetricInformation.correctionCalculationInformation.falsePositive, batchMetricInformation.correctionCalculationInformation.falseNegative);
			
			batchDto.detectionPrecesion = batchDetectionPrecision.precision;
			batchDto.detectionRecall = batchDetectionPrecision.recall;
			
			batchDto.correctionPrecision = batchCorretionPrecision.precision;
			batchDto.correctionRecall = batchCorretionPrecision.recall;
			
			batchDto.complexity = comp;
			batchDto.wordErrorType = wordErrorType;
			
			batchTestDAO.insert(batchDto);
		}
	}
}
