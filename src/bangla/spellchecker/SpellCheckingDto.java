package bangla.spellchecker;

import java.util.ArrayList;

import bangla.grammarchecker.WordSuggestion;

public class SpellCheckingDto {

	public String word;
	public int sequence;
	public int errorType = 0;
	public long startIndex = 0;
	public long length = 0;
	public ArrayList<Pair> suggestion=null; 
}
