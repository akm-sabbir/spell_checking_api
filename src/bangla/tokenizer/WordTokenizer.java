package bangla.tokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordTokenizer {
	String text;
    Pattern pattern = null;
    static String char_set = "[-\n]+";
    List<String> result = new ArrayList();
    String sub_pattern = null;
    String flag = null;
    static String front_patt = "^[!@#$%^&*(),.?\":{}|<>_; ]+";
    static String back_patt = "([!@#$%^&*(),.?\":{}|<>_; ]+)$";
    static String mid_patt = "[!@#$%^&*(),.?\":{}|<>_]+";
    static String char_pattern_one = "অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়ৠৡঁংঃ়ৎািীুূৃৄেৈোৌ্ৗৢৣ০১২৩৪৫৬৭৮৯";
    static String char_pattern_one_brac = "[অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়ৠৡঁং়ৎািীুূৃৄেৈোৌ্ৗৢৣ০১২৩৪৫৬৭৮৯]+";
    static String dependant_char = "[ঁংঃ়ৎািীুূৃৄেৈোৌ্ৗৢৣ]";
    static String independant_char = "[অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়ৠৡ]";
    List<String> prefix_accent = Arrays.asList("\u09C7", "\u09C8", "\u09BF");
    static String inv_char_pattern_one = "[^অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়ৠৡঁংঃ়ৎািীুূৃৄেৈোৌ্ৗৢৣ০১২৩৪৫৬৭৮৯\\-,; ]+";
    static String comma_pattern = "[,;]+";
    static String all_chars = "[অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়ৠৡঁংঃ়ৎািীুূৃৄেৈোৌ্ৗৢৣ]+";
    static String space_pattern = "[ ]+";
    static String highpen_pattern = "[-]+";
    static String number_patt = "[০১২৩৪৫৬৭৮৯]+";
    private int length = 0;
    
    public void set_text(String text){
		this.text = text;
		return;
	}
    
    public void set_substitutiion_pattern(String sub_patt){
    	if(sub_patt == null) {
    		sub_patt= "[(|)|{|}|:|\\]|\\[|\\|\\s]+";
    	}
		this.sub_pattern = sub_patt;
		return ;
	}
    
    public void replace(String replacing_char){
    	if(this.sub_pattern == null) return;
		replacing_char = " ";
		this.set_text(this.text.replaceAll(this.sub_pattern,replacing_char));
		return;
	}
    
    public String replace_group( String text){
    	//if(group == null) {
    	String replacing_char = " ";
    	String group = "(-\\n)|(\\r\\n)|\\s|(<VBCRLF>|\\n)";
    	
		return text.replaceAll(group, replacing_char);
	}
    
    public String strip_front(String sentence){
		String replacing_char="";
		sentence = sentence.replaceAll(front_patt, replacing_char);
		return sentence;
	}
    
	public String strip_back(String sentence){
		String replacing_char="";
		sentence = sentence.replaceAll(back_patt, replacing_char);
		return sentence;
	}
	
	public String strip_mid(String sentence){
		String replacing_char="";
		sentence = sentence.replaceAll(mid_patt, replacing_char);
		return sentence;
	}
	
    public List<String> spliting(String text) {
    	
    	String[] str =  text.split("[ \\\\/]+");
    	return Arrays.asList(str);
    }
    public List<String> splitting_slash(String text){
    	String[] str= text.split("[\\\\/]+");
    	return Arrays.asList(str);
    }
    
    public String trim_tokens(String text) {
    	text = trim_left(text);
    	text = trim_right(text);
    	return text;
    }
    
    public String trim_left(String text) {
    	return text.replaceAll("^[^" + char_pattern_one + "\\s+]+", "");
    }
    
    public String trim_right(String text) {
    	return text.replaceAll("[^" + char_pattern_one + "\\s+]+$", "");
    }
    
    public String replace_parenthesis(String text) {
    	return text.replaceAll("[(\\[{)}\\],;\\\\]+", " ");
    }
    
    public String replace_hyphen(String text) {
    	return text.replaceAll("[-]+", "-");
    }
    
    public String replace_stop_signs(String text) {
    	String self_pattern="";
    	List<String> pattern= null;
    	try {
    		pattern = Arrays.asList("[\u0965", "\u09f7", "\u0964", "\u003F", "\u003B", "\\|", "]+");
    	}catch(UnsupportedOperationException ex) {
    		System.out.println(ex);
    	}
		self_pattern = String.join("|", pattern);
    	return text.replaceAll(self_pattern, "");
    }
    
    public List<String> split_compound(String text) {
    	String[] str =  text.split("-");
    	return Arrays.asList(str);
    }
    
    public String valid_alphanumeric(String text) {
    	if(getAllMatches(text, number_patt).size() == 0) 
    		return text;
    	return null;
    }
    
    public boolean valid_alpha_num(String text) {
    	if(getAllMatches(text, number_patt+all_chars).size() > 0) return true;
    	if(getAllMatches(text, all_chars+number_patt).size() > 0) return true;
    	return false;
    }
    
    public String valid_alphabets(String text) {
    	if(getAllMatches(text, number_patt).size() > 0) {
    		List<String> res = getAllMatches(text, "^"+number_patt+all_chars);
    		if(res.size() > 0) {
    			text = res.get(0);
    		}
    	}
    	return text;
    }
    
    public List<String> _tokenization() {
    	if (this.text == null) throw new NullPointerException("text field can not be null");
    	this.text = this.replace_parenthesis(this.text);
//    	System.out.println(this.text);
    	List<String> words = this.spliting(this.text);
    	List<String> results = new ArrayList<>();
    	for(String word: words) {
    		
    		word = this.replace_hyphen(word);
    		List<String> tWord = this.split_compound(word);
    		tWord = tWord.stream().map(t -> this.trim_tokens(t)).filter(t -> t != null).collect(Collectors.toList());
    		tWord = tWord.stream().map(t -> this.valid_alphanumeric(t)).filter(t -> t != null).collect(Collectors.toList());
    		tWord = tWord.stream().map(t -> this.valid_alphabets(t)).filter(t -> t != null).collect(Collectors.toList());
    		tWord = tWord.stream().map(t -> this.replace_stop_signs(t)).filter(t -> t != null).collect(Collectors.toList());
    		tWord = tWord.stream().map(t -> this.replace_group(t)).map(t -> this.trim_tokens(t)).filter(t -> t != null).collect(Collectors.toList());
    		
//    		System.out.println("Word length can be of any size except zero");
    		for(String w: tWord) {
    			if(w.length() >= 1) {
    				results.add(w);
    			}
    		}
    	}
    	this.length = results.size();
    	return results;
    }
    
    public int get_word_count() {
    	return this.length;
    }
    
    public void set_substitution_pattern(String subPattern) {
    	if(subPattern == null) subPattern = "[(|)|{|}|:|\\]|\\[|\\s]+";
    	this.sub_pattern = subPattern;
    }
    
    public String remove_multi_highpen(String sentance) {
    	return sentance.replaceAll(highpen_pattern, "-");
    }
    
    public String remove_multi_space(String sentance) {
    	return sentance.replaceAll(space_pattern, " ");
    }
    
    public String remove_multi_comma(String sentance) {
    	return sentance.replaceAll(comma_pattern, ",");
    }
    
    private static List<String> getAllMatches(String text, String regex) {
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile("(?=(" + regex + "))").matcher(text);
        while(m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }
    

}

