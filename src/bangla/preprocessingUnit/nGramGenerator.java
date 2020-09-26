package bangla.preprocessingUnit;
import java.util.*;
public class nGramGenerator {

	public static List<String> ngramGenerator(List<String> words, int numGram){
		
		ArrayList<String> sentences = new ArrayList<>();
		for(int ind = 0; ind < (words.size() - numGram) + 1; ind+=1) {
			StringBuilder sb = new StringBuilder();
			for (int subInd = 0; subInd < numGram; subInd+=1) {
				sb.append(" " + words.get(ind + subInd));
			}
			System.out.println(sb.toString().trim());
			sentences.add(sb.toString().trim());
		}
		
		return sentences;
		
	}
	public static int getWordIndex(int sentenceIndex, int numGram, int wordIndex) {
		return sentenceIndex*numGram + wordIndex;
	}
}
