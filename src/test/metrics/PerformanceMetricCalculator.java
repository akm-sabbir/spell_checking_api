package test.metrics;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bangla.WithTrie.BitMasking;
import test.TestinDTO;
import test.TestoutDTO;


public abstract class PerformanceMetricCalculator 
{
	private List<Object> calculateDetectionTruePositive(List<Object> alignment, TestoutDTO testoutDTO) 
	{
		if(alignment == null) return null;
		
		List<Object> tp = new LinkedList<>();

		if(testoutDTO.wordErrorType.equalsIgnoreCase("NON_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				
				if(original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.CORRECT)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "CORRECT");
					
					tp.add(token);				
				}
				else if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.NON_WORD_ERROR)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "NON_WORD_ERROR");
					
					tp.add(token);
				}
					
			}
		}
		else if(testoutDTO.wordErrorType.equalsIgnoreCase("REAL_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				
				if(original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.CORRECT)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "CORRECT");
					
					tp.add(token);				
				}
				else if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.REAL_WORD_ERROR)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "REAL_WORD_ERROR");
					
					tp.add(token);
				}
					
			}			
		}
		
		return tp;
}	

	private List<Object> calculateDetectionFalsePositive(List<Object> alignment, TestoutDTO testoutDTO) 
	{
		if(alignment == null) return null;
		
		List<Object> tp = new LinkedList<>();
		
		if(testoutDTO.wordErrorType.equalsIgnoreCase("NON_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.NON_WORD_ERROR)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "NON_WORD_ERROR");
					
					tp.add(token);
				}
					
			}
		}
		else if(testoutDTO.wordErrorType.equalsIgnoreCase("REAL_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.REAL_WORD_ERROR)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "REAL_WORD_ERROR");
					
					tp.add(token);
				}
					
			}			
		}
		
		return tp;
	}	
	
	private List<Object> calculateDetectionFalseNegative(List<Object> alignment, TestoutDTO testoutDTO) 
	{
		if(alignment == null) return null;
		
		List<Object> tp = new LinkedList<>();

		if(testoutDTO.wordErrorType.equalsIgnoreCase("NON_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.CORRECT)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "CORRECT");
					
					tp.add(token);
				}
					
			}
		}
		else if(testoutDTO.wordErrorType.equalsIgnoreCase("REAL_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.CORRECT)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", (String) m.get("original"));
					token.put("corrected", (String) m.get("corrected"));
					token.put("predictedVerdict", "CORRECT");
					
					tp.add(token);
				}
					
			}			
		}
		
		return tp;
	}	
	
	private List<Object> calculateCorrectionTruePositive(List<Object> alignment, TestoutDTO testoutDTO) 
	{
		if(alignment == null) return null;
		
		List<Object> tp = new LinkedList<>();

		if(testoutDTO.wordErrorType.equalsIgnoreCase("NON_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.CORRECT)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					token.put("predictedVerdict", "CORRECT");
					
					tp.add(token);				
				}
					
				else if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.NON_WORD_ERROR 
						&& checkPredictedSuggestions(predictedAttributes, corrected))
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					token.put("predictedVerdict", "NON_WORD_ERROR");
					token.put("suggested", predictedAttributes.getAsJsonArray("suggestion"));
					
					tp.add(token);
				}
					
			}
		}
		else if(testoutDTO.wordErrorType.equalsIgnoreCase("REAL_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.CORRECT)
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					token.put("predictedVerdict", "CORRECT");
					
					tp.add(token);				
				}
					
				else if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.REAL_WORD_ERROR 
						&& checkPredictedSuggestions(predictedAttributes, corrected))
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					token.put("predictedVerdict", "REAL_WORD_ERROR");
					token.put("suggested", predictedAttributes.getAsJsonArray("suggestion"));
					
					tp.add(token);
				}
					
			}			
		}
		
		return tp;
	}
	
	private List<Object> calculateCorrectionFalsePositive(List<Object> alignment, TestoutDTO testoutDTO) 
	{
		if(alignment == null) return null;
		
		List<Object> tp = new LinkedList<>();

		if(testoutDTO.wordErrorType.equalsIgnoreCase("NON_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.NON_WORD_ERROR
						&& !checkPredictedSuggestions(predictedAttributes, corrected))
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					token.put("predictedVerdict", "NON_WORD_ERROR");
					token.put("suggested", predictedAttributes.getAsJsonArray("suggestion"));
					
					tp.add(token);
				}
			}
		}
		else if(testoutDTO.wordErrorType.equalsIgnoreCase("REAL_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(!original.equalsIgnoreCase(corrected) && predictVerdict(predictedAttributes) == Verdict.REAL_WORD_ERROR
						&& !checkPredictedSuggestions(predictedAttributes, corrected))
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					token.put("predictedVerdict", "REAL_WORD_ERROR");
					token.put("suggested", predictedAttributes.getAsJsonArray("suggestion"));
					
					tp.add(token);
				}
			}			
		}
		
		return tp;
	}	
	
	private List<Object> calculateCorrectionFalseNegative(List<Object> alignment, TestoutDTO testoutDTO) 
	{
		if(alignment == null) return null;
		
		List<Object> tp = new LinkedList<>();

		if(testoutDTO.wordErrorType.equalsIgnoreCase("NON_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(!original.equalsIgnoreCase(corrected) 
						&& !checkCorrectionAttempt(predictedAttributes))
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					
					tp.add(token);
				}
			}
		}
		else if(testoutDTO.wordErrorType.equalsIgnoreCase("REAL_WORD_ERROR"))
		{
			for(Object o : alignment)
			{
				Map<Object, Object> m = (Map<Object, Object>) o;
				
				String original = (String) m.get("original");
				String corrected = (String) m.get("corrected");
				
				JsonObject predictedAttributes = (JsonObject) m.get("predictedAttributes");
				
				if(!original.equalsIgnoreCase(corrected) 
						&& !checkCorrectionAttempt(predictedAttributes))
				{
					Map<Object, Object> token = new LinkedHashMap<>();
					
					token.put("startIndex", (int) m.get("startIndex"));
					token.put("original", m.get("original"));
					token.put("corrected", m.get("corrected"));
					
					tp.add(token);
				}
			}
		}
		
		return tp;
	}	

	public Map<Object, Object> populateDetectionMetricsAndGetLog(List<Object> alignment, TestoutDTO testoutDTO, BatchMetricInformation batchMetricInformation) 
	{
		float detectionPrecision = -1;
		float detectionRecall = -1;
		
//		List<Object> detectionTruePositive = calculateDetectionTruePositive(alignment);
//		List<Object> detectionFalsePositive = calculateDetectionFalsePositive(alignment);
//		List<Object> detectionFalseNegative = calculateDetectionFalseNegative(alignment);
		
		Map<Object, Object> detectionTruePositive = listToMap(calculateDetectionTruePositive(alignment, testoutDTO));
		Map<Object, Object> detectionFalsePositive = listToMap(calculateDetectionFalsePositive(alignment, testoutDTO));
		Map<Object, Object> detectionFalseNegative = listToMap(calculateDetectionFalseNegative(alignment, testoutDTO));
		
		int truePositive = detectionTruePositive.size();
		batchMetricInformation.detectionCalculationInformation.truePositive += truePositive;
		int falsePositive = detectionFalsePositive.size();
		batchMetricInformation.detectionCalculationInformation.falsePositive += falsePositive;
		int falseNegative = detectionFalseNegative.size();
		batchMetricInformation.detectionCalculationInformation.falseNegative += falseNegative;
		
		PrecisionRecallPair detectionMetrics = calculateMetrics(truePositive, falsePositive, falseNegative);
		
		testoutDTO.detectionPrecision = detectionMetrics.precision;
		testoutDTO.detectionRecall = detectionMetrics.recall;
		
//		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//		String log = "";
		
		Map<Object, Object> log = new LinkedHashMap<Object, Object>();
		log.put("DTP", detectionTruePositive);
		log.put("DFP", detectionFalsePositive);
		log.put("DFN", detectionFalseNegative);
		
//		log += "Detection True Positive: " + System.lineSeparator();
//		log += gson.toJson(detectionTruePositive) + System.lineSeparator() + System.lineSeparator();
//		log += "Detection False Positive: " + System.lineSeparator();
//		log += gson.toJson(detectionFalsePositive) + System.lineSeparator() + System.lineSeparator();					
//		log += "Detection False Negative: " + System.lineSeparator();
//		log += gson.toJson(detectionFalseNegative) + System.lineSeparator() + System.lineSeparator();
		
		return log;
	}
	
	public Map<Object, Object> populateCorrectionMetricsAndGetLog(List<Object> alignment, TestoutDTO testoutDTO, BatchMetricInformation batchMetricInformation) 
	{
//		List<Object> correctionTruePositive = calculateCorrectionTruePositive(alignment);
//		List<Object> correctionFalsePositive = calculateCorrectionFalsePositive(alignment);
//		List<Object> correctionFalseNegative = calculateCorrectionFalseNegative(alignment);
		
		Map<Object, Object> correctionTruePositive = listToMap(calculateCorrectionTruePositive(alignment, testoutDTO));
		Map<Object, Object> correctionFalsePositive = listToMap(calculateCorrectionFalsePositive(alignment, testoutDTO));
		Map<Object, Object> correctionFalseNegative = listToMap(calculateCorrectionFalseNegative(alignment, testoutDTO));
		
		int truePositive = correctionTruePositive.size();
		batchMetricInformation.correctionCalculationInformation.truePositive += truePositive;
		int falsePositive = correctionFalsePositive.size();
		batchMetricInformation.correctionCalculationInformation.falsePositive += falsePositive;
		int falseNegative = correctionFalseNegative.size();
		batchMetricInformation.correctionCalculationInformation.falseNegative += falseNegative;
		
		PrecisionRecallPair correctionMetrics = calculateMetrics(truePositive, falsePositive, falseNegative);
		
		testoutDTO.correctionPrecision = correctionMetrics.precision;
		testoutDTO.correctionRecall = correctionMetrics.recall;
		
//		Gson gson = new Gson();
//		String log = "";
		
		Map<Object, Object> log = new LinkedHashMap<Object, Object>();
		log.put("CTP", correctionTruePositive);
		log.put("CFP", correctionFalsePositive);
		log.put("CFN", correctionFalseNegative);
		
		
//		log += "Correction True Positive: " + System.lineSeparator();
//		log += gson.toJson(correctionTruePositive) + System.lineSeparator() + System.lineSeparator();
//		log += "Correction False Positive: " + System.lineSeparator();
//		log += gson.toJson(correctionFalsePositive) + System.lineSeparator() + System.lineSeparator();					
//		log += "Correction False Negative: " + System.lineSeparator();
//		log += gson.toJson(correctionFalseNegative) + System.lineSeparator() + System.lineSeparator();
		
		return log;
	}

	public List<Object> formAlignment(TestinDTO testinDTO, String predictedContent) 
	{
		DiffText dt = new DiffText();
		List<Object> alignment1  = dt.alignText(testinDTO.originalContent, testinDTO.correctedContent);
		
		List<Object> alignment2 = parseJson(predictedContent);
		
		List<Object> alignment = mergeAlignment(alignment1, alignment2);
		

		return alignment;
	}

	public List<Object> mergeAlignment(List<Object> alignment1, List<Object> alignment2)
	{
		List<Object> alignment = new LinkedList<Object>();
		
		for(Object o1 : alignment1)
		{
			Map<Object, Object> m1 = (Map<Object, Object>) o1;
			
//			int i1 = (int) m1.get("index");
			int i1 = (int) m1.get("startIndex");
			
			boolean matched = false;
			
			for(Object o2 : alignment2)
			{
				Map<Object, Object> m2 = (Map<Object, Object>) o2;
				
//				int i2 = (int) m2.get("index");
				int i2 = (int) m2.get("startIndex");
				
				if(i1 == i2)
				{
					Map<Object, Object> map = new LinkedHashMap<>();
					
					for(Object o : m1.keySet())
						map.put(o, m1.get(o));
					
					for(Object o : m2.keySet())
						map.put(o, m2.get(o));
					
					alignment.add(map);
					
					matched = true;
					break;
				}
			}
			
			if(!matched)
				alignment.add(m1);
			
		}
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		System.out.println("alignment : " + gson.toJson(alignment));		
		
		return alignment;
	}
	
	public Map<Object, Object> listToMap(List<Object> alignment)
	{
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		
		for(Object o : alignment)
		{
			Map<Object, Object> m = (Map<Object, Object>) o;
			map.put(Integer.parseInt(m.get("startIndex").toString()), m);
		}
		
		return map;
	}
	
	private List<Object> parseJson(String predictedContent)
	{
		JsonObject jsonObject = new JsonParser().parse(predictedContent).getAsJsonObject();
		
		List<Object> alignment = new LinkedList<>();
		Map<Object, Object> map = null;
		
		for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
		{
			map = new LinkedHashMap<>();
//			map.put("index", entry.getValue().getAsJsonObject().get("startIndex").getAsInt());
			map.put("startIndex", entry.getValue().getAsJsonObject().get("startIndex").getAsInt());
			map.put("predictedAttributes", entry.getValue().getAsJsonObject());
			
			alignment.add(map);
		}
		
		return alignment;
	}	
	
	private Verdict predictVerdict(JsonObject predictedAttributes)
	{
		if(predictedAttributes == null) return Verdict.UNKNOWN;
		
		JsonObject jo = predictedAttributes;
		int errorType = jo.getAsJsonPrimitive("errorType").getAsInt();

		if(BitMasking.extractNthBit(errorType, 1) == 1)		//	correct
			return Verdict.CORRECT;		

		if(BitMasking.extractNthBit(errorType, 2) == 1)		//	real-word
			return Verdict.REAL_WORD_ERROR;		
		
		if(BitMasking.extractNthBit(errorType, 3) == 1)		//	real-word
			return Verdict.REAL_WORD_ERROR;		
		
		if(BitMasking.extractNthBit(errorType, 4) == 1)		//	real-word
			return Verdict.REAL_WORD_ERROR;		
		
		if(BitMasking.extractNthBit(errorType, 5) == 1)		//	non-word
			return Verdict.NON_WORD_ERROR;
		
		if(BitMasking.extractNthBit(errorType, 6) == 1)		//	unknown
			return Verdict.UNKNOWN;

		if(BitMasking.extractNthBit(errorType, 7) == 1)		//	real-word
			return Verdict.REAL_WORD_ERROR;		
		
		if(BitMasking.extractNthBit(errorType, 8) == 1)		//	real-word
			return Verdict.REAL_WORD_ERROR;		
		
		if(BitMasking.extractNthBit(errorType, 9) == 1)		//	real-word
			return Verdict.REAL_WORD_ERROR;		
		
		return Verdict.UNKNOWN;
	}
	
	private boolean checkPredictedSuggestions(JsonObject predictedAttributes, String corrected)
	{
		if(predictedAttributes == null) return false;
		
		JsonObject jo = predictedAttributes;

		System.out.println("checkPredictedSuggestions() : " + jo.toString());

		if(!jo.has("suggestion")) return false;

		JsonArray ja = jo.getAsJsonArray("suggestion");
		
		
		for(int i = 0; i < ja.size() ; i++)
		{
			JsonElement je = ja.get(i);
			
			if(je.getAsJsonObject().get("words").getAsString().equalsIgnoreCase(corrected))
				return true;
		}
		
		return false;
	}	
	
	private boolean checkCorrectionAttempt(JsonObject predictedAttributes)
	{
		if(predictedAttributes == null) return false;
		
		JsonObject jo = predictedAttributes;

		if(!jo.has("suggestion")) return false;

		return true;
	}	
	
	public abstract PrecisionRecallPair calculateMetrics(int truePositive, int falsePositive, int falseNegative);
	
	
	enum Verdict
	{
		NON_WORD_ERROR, REAL_WORD_ERROR, CORRECT, UNKNOWN;
	}
}
