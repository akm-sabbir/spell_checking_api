package test;
import java.util.*; 


public class TestinDTO
{
	public long id;
	public String originalContent;
	public String correctedContent;
	public String complexity;
	
	public List<ExpectedErrorSuggestion> expectedErrorSuggestions;
}