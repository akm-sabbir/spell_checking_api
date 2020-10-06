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

import test.TestinDTO;
import test.TestoutDTO;

import bangla.WithTrie.*;

public class PerformanceMetricCalculatorImpl extends PerformanceMetricCalculator
{

	@Override
	public PrecisionRecallPair calculateMetrics(int truePositive, int falsePositive, int falseNegative)
	{
		PrecisionRecallPair result = new PrecisionRecallPair();
		
		if((truePositive + falsePositive) != 0)
			result.precision = 100 * truePositive / (truePositive + falsePositive);
		
		if((truePositive + falseNegative) != 0)
			result.recall = 100 * truePositive / (truePositive + falseNegative);
		
		return result;
		
	}
}
