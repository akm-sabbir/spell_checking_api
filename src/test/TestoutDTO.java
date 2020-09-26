package test;
import java.util.*; 
import util.*; 


public class TestoutDTO
{
	long ID;
	long content_id;
	
//	String predicted_content;
	String detailed_log;
	String word_error_type;
	
	float detection_precision;
	float detection_recall;
	float correction_precision;
	float correction_recall;
	long request_time;
	long execution_time;
}