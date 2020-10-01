package bangla.spellchecker;

import java.util.ArrayList;

import bangla.grammarchecker.WordSuggestion;

public class SpellCheckingDto {

	public String word;
	public int ID;
	public int isCorrect = 0;
	public int langType = 0;
	public String wordType= null;
	public String errorType = null;
	public long startIndex = 0;
	public long endIndex = 0;
	public ArrayList<Pair> suggestion=null; 
	//public ArrayList<WordSuggestion> suggestion_pair = null;
}
