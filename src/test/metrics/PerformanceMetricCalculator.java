package test.metrics;

import java.util.List;
import java.util.Map;
import test.TestinDTO;
import test.TestoutDTO;


public interface PerformanceMetricCalculator 
{
	public String populateDetectionMetricsAndGetLog(List<Object> alignment, TestoutDTO testoutDTO);
	public String populateCorrectionMetricsAndGetLog(List<Object> alignment, TestoutDTO testoutDTO);
//	
//	public String executeTestCases(TestinDTO testinDTO, Map<Integer, spell_checking_dto> originalResultList);
	
	public List<Object> formAlignment(TestinDTO testinDTO, String predictedContent);
}
