package bangla.WithTrie;

import java.util.HashMap;
import java.util.Map.Entry;

import bangla.spellchecker.*;
import java.util.ArrayList;
import bangla.spellchecker.SpellCheckingDto;

public class JsonFormatter {
	
	public static String getSuggestion(ArrayList<Pair> suggestion) {
		String results = "";
		int index = 0;
		for(Pair each_pair : suggestion) {
			results += index != 0 ? "," :"";
			results += "{\"words\":\"" + each_pair.words + "\",\"score\":" + each_pair.score+"}"; 
			index += 1;
		}
		return results;
	}
	public static String decryptValue(SpellCheckingDto value) {
		String result = "";
		result += value.word != null ? "\"word\":" + value.word : "";
		result += "\"sequence\":" + value.sequence;
		result += "\"errorType\":" + value.errorType;
		result += "\"startIndex\":" + value.startIndex;
		result += "\"length\":" + value.endIndex;
		result += value.suggestion != null ? "suggestion:["+ getSuggestion(value.suggestion) +"]":"";
		return result;
	}
	public static String getFinalizer(int key, SpellCheckingDto value) {
		
		return "\"" + key + "\":{" + decryptValue(value) + "}";
	}
	public static String toJson(HashMap<Integer, SpellCheckingDto> maps) {
		String resultantStr ="{";
		int index = 0;
		for(Entry<Integer, SpellCheckingDto> each_object : maps.entrySet()) {
			resultantStr += index != 0 ? "," : "";
			resultantStr +=  getFinalizer(each_object.getKey(), each_object.getValue());
			index += 1;
		}
		resultantStr= "}";
		return resultantStr;
	}
}