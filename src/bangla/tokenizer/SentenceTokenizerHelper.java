package bangla.tokenizer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.*;
import java.util.stream.Collectors;
public class SentenceTokenizerHelper {
	String text;
    Pattern pattern = null;
    static String char_set = "[-\n]+";
    List<String> result = new ArrayList();
    String sub_pattern = null;
    String flag = null;
    static String front_patt = "^[!@#$%^&*(),.?\":{}|<>_; ]+";
    static String back_patt = "([!@#$%^&*(),.?\":{}|<>_; ]+)$";
    static String mid_patt = "[!@#$%^&*(),.?\":{}|<>_]+";
    static String char_pattern_one = "[অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়ৠৡঁংঃ়ৎািীুূৃৄেৈোৌ্ৗৢৣ০১২৩৪৫৬৭৮৯\\-]+";
    List<String> prefix_accent = Arrays.asList("\u09C7", "\u09C8", "\u09BF");
    static String inv_char_pattern_one = "[^অআইঈউঊঋঌএঐওঔকখগঘঙচছজঝঞটঠডঢণতথদধনপফবভমযরলশষসহড়ঢ়য়ৠৡঁংঃ়ৎািীুূৃৄেৈোৌ্ৗৢৣ০১২৩৪৫৬৭৮৯\\-,; ]+";
    static List<String> named_list = Arrays.asList( "প্রথম আলো", "নিজস্ব প্রতিবেদক");
    static String comma_pattern = "[,;]+";
    static String space_pattern = "[ ]+";
    static String highpen_pattern = "[-]+";
    static String named_patt = char_pattern_one + "[ ]+প্রতিনিধি";
    static String number_patt = "[০১২৩৪৫৬৭৮৯]+";
    Pattern dot_pattern = null;
    Pattern _0965_pattern = null;
    Pattern _0964_pattern = null;
    Pattern _003B_pattern = null;
    Pattern _003F_pattern = null;
    HashSet<Integer> terminator_pattern = new HashSet<Integer>();
    public  int length = 0;
	private Pattern _007C_pattern = null;
	private Pattern _0021_pattern = null;
	private Pattern _09F7_pattern = null;
	private Pattern newline_pattern;
    public SentenceTokenizerHelper(String text){
		this.text = text;
	}
	public SentenceTokenizerHelper() {}
	public void set_text(String text){
		this.text = text;
		return;
	}
	
	public void replace(){
		String replacing_char = " ";
		this.set_text(this.text.replaceAll(this.sub_pattern,replacing_char));
		return;
	}
	public void replace_group(){
		String group = "(-\\n)|(\\r\\n)|\\s|(<VBCRLF>)";
		String replacing_char=" ";
		this.set_text(this.text.replaceAll(group, replacing_char));
		return;
	}
	
	public void set_flag(String flag){
		this.flag = flag;
		return;
	}
	
	// Following function should be a part of word tokenizer
	public void set_substitutiion_pattern(){
		String sub_patt= "[(|)|{|}|:|]|[|\\s]+";
		this.sub_pattern = sub_patt;
		return ;
	}
	public void create_pattern(){
		List<String> pattern = Arrays.asList("[", "\u0965", "\u09f7", "\u0964",
				"\u003F", "\u003B", "]+");
		String self_pattern = String.join("|", pattern);
		this.pattern = Pattern.compile(self_pattern);
		return ;
	}
	public void create_0965_pattern() {
		String dot = "[\\u0965]{1}[ ]+";
		this._0965_pattern = Pattern.compile(dot);
		return;
	}
	public void create_09F7_pattern() {
		String dot = "[\\u09F7]+[ ]*";
		this._09F7_pattern = Pattern.compile(dot);
		return;
	}
	public void create_0964_pattern() {
		String dot = "[\\u0964]+[ ]*";
		this._0964_pattern = Pattern.compile(dot);
		return;
	}
	public void create_003F_pattern() {
		String dot = "[\\u003F]+[ ]*";
		this._003F_pattern = Pattern.compile(dot);
		return;
	}
	public void create_003B_pattern(){
		String dot = "[\\u003B]+[ ]*";
		this._003B_pattern = Pattern.compile(dot);
		return;
	}
	public void create_007C_pattern() {
		String dot = "[\\u007C]+[ ]*";
		this._007C_pattern = Pattern.compile(dot);
		return;
	}
	public void create_0021_pattern() {
		String dot = "[\\u0021]+[ ]*";
		this._0021_pattern = Pattern.compile(dot);
		return;
	}
	public void creat_003B_pattern() {
		String dot = "[\\u003B]+[ ]*";
		this._003B_pattern = Pattern.compile(dot);
		return;
	}
	public void create_dot_pattern() {
		String dot = "[\\.]+[ ]+";
		this.dot_pattern = Pattern.compile(dot);
		return;
	}
	public void create_newline_pattern() {
		String dot = "[\\u000D\\u000A|\\n\\r|\\r\\n|\\u000A]+[ ]*";
		//String dot2 = "(?s).*[\\r\\n|\\n].*";
		this.newline_pattern = Pattern.compile(dot);
		return;
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
	public String strip_named_pattern(String sentence) {
		return sentence.replaceAll(named_patt, "");
	}
	public String strip_name_list(String sentence){
		String replacing_char="";
		for (String name : named_list) 
		{
			sentence = sentence.replaceAll(name,replacing_char); 
		}
		return sentence;
	}
	public String remove_number_only_sentence(String result){
		String temp = result.replaceAll(number_patt, "");
		if(temp.length()<3) return temp;
		return result;
	}
	// Following function should be a part of word tokenizer
	public String strip_non_char(String sentence) {
		return sentence.replaceAll(inv_char_pattern_one, "");
	}
	//////////////////////////////////////////
	public List<String> back_front_stripping() {
		return this.result.stream()
				.map(s -> this.strip_front(s))
				.map(s -> this.strip_back(s))
				.filter(s -> s != null && s.length()>0)
				.collect(Collectors.toList());
	}
	// following three functions should be the part of word tokenizer
	public String remove_multi_highpen(String sentence) {
		return sentence.replaceAll(highpen_pattern, "-");
	}
	public String remove_multi_space(String sentence) {
		return sentence.replaceAll(space_pattern, " ");
	}
	public String remove_multi_comma(String sentence) {
		return sentence.replaceAll(comma_pattern, ",");
	}
	/////////////////////////////////////////////////////////////////
	public List<String> miselaneous_stripping() {
		return this.result.stream()
				.map(s -> this.strip_name_list(s))
				.map(s -> this.strip_non_char(s))
				.map(s -> this.strip_named_pattern(s))
				.map(s -> this.remove_number_only_sentence(s))
				.map(s -> this.remove_multi_comma(s))
				.map(s -> this.remove_multi_highpen(s))
				.map(s -> this.remove_multi_space(s))
				.collect(Collectors.toList());
	}
	public void print(List<String> res, int stage) {
		System.out.println("After stage: " + stage);
		for(String each : res) {
			
			System.out.println(each);
		}
		System.out.println("End of stage: " + stage);
	}
	public List<String> tokenization(String text) {
		
		this.result = Arrays.asList(text.split(this._0964_pattern.toString()));
		List<String> resultLocal = this.result.stream().map(w -> w.trim()).filter(w -> w.length()>2).map(w -> terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u0964': w).collect(Collectors.toList());
		//result = this.result.stream().map(w -> w.trim()).filter(w -> w.length()>2).map(w -> terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u0964': w).collect(Collectors.toList());
		//result = result.stream().map(w -> Arrays.asList(w.split(this.newline_pattern.toString()))).flatMap(pList -> pList.stream()).map(w -> terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u0964': w).collect(Collectors.toList());
		List<String> result = new ArrayList<>(); 
		try {
			for (int index =0; index<resultLocal.size();index++) {
				String[] subStr = resultLocal.get(index).split(this.newline_pattern.toString());
				//System.out.println(subStr[0]);
				for (String each : subStr) {
					if(each.length() !=0 && terminator_pattern.contains((int)each.charAt(each.length() - 1)) == false)
						result.add(each + '\u09F7');
					else
						result.add(each);
				}
			}
		
		
			result = result.stream().map(w -> Arrays.asList(w.split(this._003F_pattern.toString()))).flatMap(pList -> pList.stream()).map(w -> w.length() != 0 && terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u003F': w).collect(Collectors.toList());
			//print(result,stage );
			result = result.stream().map(w -> Arrays.asList(w.split(this._0021_pattern.toString()))).flatMap(pList -> pList.stream()).map(w -> w.length() != 0 &&terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u0021': w).collect(Collectors.toList());
			//print(result,stage );
			result = result.stream().map(w -> Arrays.asList(w.split(this._003B_pattern.toString()))).flatMap(pList -> pList.stream()).map(w -> w.length()!= 0 && terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u003B': w).collect(Collectors.toList());
			result = result.stream().map(w -> Arrays.asList(w.split(this._0965_pattern.toString()))).flatMap(pList -> pList.stream()).map(w -> w.length() != 0 && terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u0965': w).collect(Collectors.toList());
			result = result.stream().map(w -> Arrays.asList(w.split(this._09F7_pattern.toString()))).flatMap(pList -> pList.stream()).map(w -> w.length() !=0 && terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u09F7': w).collect(Collectors.toList());
			result = result.stream().map(w -> Arrays.asList(w.split(this.dot_pattern.toString()))).flatMap(pList -> pList.stream()).map(w -> w.length() != 0 && terminator_pattern.contains((int)w.charAt(w.length() - 1)) == false ? w + '\u002E': w).collect(Collectors.toList());
			result = result.stream().filter(w -> w.length() != 0).collect(Collectors.toList());
		}catch(StringIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		this.length = result.size();
		return result;
	}
	public List<String> _tokenization() {
		//this.set_substitutiion_pattern();
		this.create_pattern();
		//this.replace_group();
		//this.replace();
		this.create_dot_pattern();
		this.create_0965_pattern();
		this.create_0964_pattern();
		this.create_09F7_pattern();
		this.create_003F_pattern();
		this.create_007C_pattern();
		this.create_0021_pattern();
		this.create_003B_pattern();
		this.create_newline_pattern();
		this.terminator_pattern.addAll(Arrays.asList(0x0965, 0x09F7, 0x0964, 0x003F, 0x003B, 0x007C, 0x0021, 0x003B, 0x002E));
		return this.tokenization(this.text);
	}
	public int get_sentence_count() {
		return this.length;
		
	}
}
