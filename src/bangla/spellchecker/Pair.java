package bangla.spellchecker;

public class Pair {

	String words;
	Integer score;
	public Pair() {}
	public Pair(String word, Integer weight) {
		this.words = word;
		this.score = weight;
	}
}