package bangla.tokenizer;
import java.util.*;
public class TextNormalization {
	
	private WordTokenizer WT = new WordTokenizer();
	public String normalizeText(String s) 
	{
		// TODO Auto-generated method stub

		String t = s;
		t = t.replaceAll("[\\x{200A}\\x{200B}\\x{200C}]", "");			//	ZERO WIDTH	=	""
		//t = t.replaceAll("(\\x{09F0})", "\u09B0");						//	ৰ	=	র			???
		//t = t.replaceAll("(\\x{09F1})", "\u09AC");						//	ৱ	=	ব			???		
		t = t.replaceAll("(\\x{0985})(\\x{09BE})", "\u0986");			//	অ	+	 া	=	আ
		t = t.replaceAll("(\\x{09C7})(\\x{09BE})", "\u09CB");			//	ে	+	া	=	ো
		t = t.replaceAll("(\\x{09C7})(\\x{09D7})", "\u09CC");			//	ে	+	ৗ	=	ৌ
		t = t.replaceAll("(\\x{09AC})(\\x{09BC})", "\u09B0");			//	ব	+	়	=	র
		t = t.replaceAll("(\\x{09A1})(\\x{09BC})", "\u09DC");			//	ড	+	়	=	ড়
		t = t.replaceAll("(\\x{09A2})(\\x{09BC})", "\u09DD");			//	ঢ	+	়	=	ঢ়
		t = t.replaceAll("(\\x{09AF})(\\x{09BC})", "\u09DF");			//	য	+	়	=	য়		
		return t;		
	}
	
	public ArrayList<Integer> getUnicdeCodePoints(String text) {
		ArrayList<Integer> codePointContainer = new ArrayList<>();
		try {
			for (int i = 0; i < text.length(); i++) {
				codePointContainer.add(text.codePointAt(i));
			}
		}catch(IndexOutOfBoundsException indexBound) {
			System.out.println(indexBound.getMessage());
		}
		return codePointContainer;
	}
	
	public HashMap<Integer, String> getIndexedWords(List<String> sentences){
		HashMap<Integer, String> indexedWords = new HashMap<>();
		List<String> words = null;
		int baseIndex = 0;
		try {
			for(int sentence_i = 0; sentence_i < sentences.size(); sentence_i++ ) {
				WT.set_text(sentences.get(sentence_i));
				words = WT._tokenization();
				for(int word_i=0 ; word_i < words.size(); word_i++) {
					if (words.get(word_i).length() != 0) {
						indexedWords.put(baseIndex + word_i, this.normalizeText(words.get(word_i)));
					}
				}
			   baseIndex += words.size();
		   }
		}catch(IndexOutOfBoundsException ex) {
			System.out.println(ex.getMessage());
		}catch(NullPointerException ex) {
			System.out.println(ex.getMessage());
		}
		return indexedWords;
		
	}
}
