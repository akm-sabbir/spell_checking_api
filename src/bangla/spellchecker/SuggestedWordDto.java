package bangla.spellchecker;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import org.apache.log4j.Logger;



public class SuggestedWordDto {
	//Logger logger = Logger.getLogger(getClass());
	public String word;
	public List<Pair> suggested_word = new ArrayList();
	
}
