package test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

//		String[] sa = {"all", "easy", "moderate", "complex", "easy, moderate", "moderate, complex"};
		
		String[] sa = {"all"};
		
		String[] eta = {"NON_WORD_ERROR", "REAL_WORD_ERROR"};
		
		String v = "2.0.0";
		
		for(String s : sa)
			for(String et : eta)
				calculateBatchPerformance(s, v, et);
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
	
//	private static String listToString(List<String> sl)
//	{
//        String cs = " ";
//        
//        String[] sa = sl.toArray(new String[0]);
//        
//        
//        for(int i = 0 ; i < sa.length ; i++)
//        {
//        	if(sa[i].equalsIgnoreCase("all"))
//        	{
//        		cs += "'easy', 'moderate', 'difficult'";
//        	}
//        	else
//        	{
//        		cs += " '" + sa[i] + "' ";
//        	}
//        	
//        	if(i < sa.length - 1)
//        		cs += ", ";
//
//        }
//        
//        cs += " ";
//        
//        return cs;
//	}
//	
//	private static List<List<String>> generateCombination(String[] complexity)
//	{
//		List<List<String>> batch = new LinkedList<List<String>>();
//
//		List<String> list = new LinkedList<String>();
//		list.add("all");
//		batch.add(list);
//		
//		for(int i = 0; i < complexity.length; i++)
//		{
//			list = new LinkedList<String>();
//			list.add(complexity[i]);
//			batch.add(list);
//			
//			for(int j = i+1; j < complexity.length; j++)
//			{
//				list = new LinkedList<String>();
//				list.add(complexity[i]);
//				list.add(complexity[j]);	
//				
//				batch.add(list);
//			}
//		}		
//		
//		return batch;
//	}
	
	private static void calculateBatchPerformance(String comp, String version, String wordErrorType) throws Exception
	{
		TestinDAO testin_dao = new TestinDAO();
		TestoutDAO testoutDAO = new TestoutDAO();
		BatchTestDAO batchTestDAO = new BatchTestDAO();
		String guid = UUID.randomUUID().toString();
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		int page_no = 0;			
		PerformanceMetricCalculator performanceMetricCalculator = new PerformanceMetricCalculatorImpl();
		BatchMetricInformation batchMetricInformation = new BatchMetricInformation();
		BatchTestDTO batchDto = new BatchTestDTO();
		batchDto.guid = guid;
		batchDto.version = version;
		batchDto.wordErrorType = wordErrorType;
		batchDto.complexity = comp;
				
		while(true)
		{
			List<TestinDTO> testinDTOList = testin_dao.get_paginated_Testin(page_no, 50, comp);
			
			if(testinDTOList.size() <= 0) break;
			
			for(TestinDTO testinDTO : testinDTOList)
			{
				Map<Object, Object> log = new LinkedHashMap<Object, Object>();;
				long time1 = System.currentTimeMillis();
				long time2 = System.currentTimeMillis();
				long wc = 0;
				
				TestoutDTO testoutDTO = new TestoutDTO();
				testoutDTO.guid = batchDto.guid;
				testoutDTO.version = batchDto.version;
				testoutDTO.wordErrorType = batchDto.wordErrorType;
				
				try
				{
					Word_contentService wcs = new Word_contentService();
					time1 = System.currentTimeMillis();
					String predictedContent = wcs.executeSpellChecking(testinDTO.originalContent, 3);	//	3: both option
					time2 = System.currentTimeMillis();
					
//					wc = originalResultList.size();
					
					log.put("predictedContent", new JsonParser().parse(predictedContent).getAsJsonObject());
					
					List<Object> alignment = performanceMetricCalculator.formAlignment(testinDTO, predictedContent);
					
					log.put("totalAlignmentMap", performanceMetricCalculator.listToMap(alignment));
					
					Map<Object, Object> m1 = performanceMetricCalculator.populateDetectionMetricsAndGetLog(alignment, testoutDTO, batchMetricInformation); 
					Map<Object, Object> m2 = performanceMetricCalculator.populateCorrectionMetricsAndGetLog(alignment, testoutDTO, batchMetricInformation);

					log.putAll(m1);
					log.putAll(m2);

				}
				catch(Exception e)
				{
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					log.put("exception", sw.toString());
				}
				finally
				{
					testoutDTO.contentId = testinDTO.id;
					testoutDTO.complexity = testinDTO.complexity;
					testoutDTO.requestTime = time1;
					testoutDTO.executionTime = (time2-time1);
					testoutDTO.wordCount = wc;
					
					testoutDTO.detailedLog = gson.toJson(log);
					
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
		
//		batchDto.complexity = comp;
//		batchDto.wordErrorType = wordErrorType;
		
		batchTestDAO.insert(batchDto);
	}
}
