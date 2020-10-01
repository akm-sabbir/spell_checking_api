package bangla.spellchecker;

public class Pair {

	public String words;
	public Integer score;
	public Pair() {}
	public Pair(String word, Integer weight) {
		this.words = word;
		this.score = weight;
	}
}
