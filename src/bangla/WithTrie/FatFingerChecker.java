package bangla.WithTrie;

import java.util.HashMap;
import java.util.Map;

public class FatFingerChecker {
	static Map<Character, String> fatFingerMap = new HashMap<>();
	/*
	public static void setFatFingerForBijoyPhone() {
		
		fatFingerMap.put('à¦…', "à¦†à¦�à¦‹");
		fatFingerMap.put('à¦†', "à¦…à¦‹à¦�à¦�à¦‡");
		fatFingerMap.put('à¦‡', "à¦†à¦“à¦�à¦�à¦ˆ");
		fatFingerMap.put('à¦ˆ', "à¦”à¦“à¦‰à¦�à¦‡");
		fatFingerMap.put('à¦‰', "à¦”à¦“à¦Šà¦ˆ");
		fatFingerMap.put('à¦Š', "à¦”à¦‰");
		fatFingerMap.put('à¦‹', "à¦…à¦†à¦�");
		fatFingerMap.put('à¦�', "à¦…à¦‹à¦�à¦�à¦‡");
		fatFingerMap.put('à¦�', "à¦†à¦“à¦�à¦�à¦ˆ");
		fatFingerMap.put('à¦“', "à¦‡à¦“à¦”à¦�");
		fatFingerMap.put('à¦•', "à¦šà¦¬à¦¨à¦¸à¦¤à¦¹à¦œ");
		fatFingerMap.put('à¦–', "à¦›à¦­à¦£à¦·à¦¥à¦žà¦�");
		fatFingerMap.put('à¦—', "à¦¹à¦¤à¦¦à§Žà§œ");
		fatFingerMap.put('à¦˜', "à¦žà¦¥à¦§à¦ƒà§�");
		fatFingerMap.put('à¦™', "à§ƒà§�à¦¯");
		fatFingerMap.put('à¦š', "à¦Ÿà§�à¦¬à¦•à¦œ");
		fatFingerMap.put('à¦›', "à¦ à¥¤à¦­à¦–à¦�");
		fatFingerMap.put('à¦œ', "à¦šà¦¬à¦¤à¦¹à¦•");
		fatFingerMap.put('à¦�', "à¦›à¦­à¦–à¦¥à¦ž");
		fatFingerMap.put('à¦ž', "à¦�à¦–à¦¥à¦§à¦˜");
		fatFingerMap.put('à¦Ÿ', "à¦ªà¦¬à¦šà¦¾à§�");
		fatFingerMap.put('à¦ ', "à¦«à¦…à¥¤à¦­à¦›");
		fatFingerMap.put('à¦¡', "à¦¯à§�à¦¿à¦¾à¦ª");
		fatFingerMap.put('à¦¢', "à§Ÿà§‚à§€à¦…à¦«");
		fatFingerMap.put('à¦£', "à¥¤à¦²à¦·à¦–à¦­");
		fatFingerMap.put('à¦¤', "à¦œà¦•à¦®à¦¦à¦¸à¦—à¦¹");
		fatFingerMap.put('à¦¥', "à¦�à¦–à¦·à¦¶à¦§à¦˜à¦ž");
		fatFingerMap.put('à¦¦', "à¦¹à¦¤à¦®à§Žà§œà¦—");
		fatFingerMap.put('à¦§', "à¦žà¦¥à¦¶à¦ƒà§�à¦˜");
		fatFingerMap.put('à¦¨', "à¦¬à¦°à¦¸à¦•");
		fatFingerMap.put('à¦ª', "à¦¡à¦¬à¦šà¦¿,");
		fatFingerMap.put('à¦«', "à¦¢à§€à¦…à¥¤à¦ ");
		fatFingerMap.put('à¦¬', "à¦°à¦¨à¦•à¦œà¦š,à¦Ÿ");
		fatFingerMap.put('à¦­', "à¦ à¥¤à¦²à¦£à¦–à¦�à¦›");
		fatFingerMap.put('à¦®', "à¦¤à¦¸;");
		fatFingerMap.put('à¦¯', "à¦™à§ƒà§�à¦¿à¦¡");
		fatFingerMap.put('à§Ÿ', "à¦‚à§‚à§€à¦¢");
		fatFingerMap.put('à¦°', "à§�à§‡à¦¨à¦¬");
		fatFingerMap.put('à¦²', "à¥¤à¦�à¦£à¦­");
		fatFingerMap.put('à¦¶', "à¦¥à¦·;à¦§");
		fatFingerMap.put('à¦·', "à¦–à¦£;à¦¶à¦¥");
		fatFingerMap.put('à¦¸', "à¦•à¦¨;à¦®à¦¤");
		fatFingerMap.put('à¦¹', "à¦œà¦•à¦¤à¦¦à¦—");
		fatFingerMap.put('à§œ', "à¦—à¦¦à§Ž");
		fatFingerMap.put('à§�', "à¦˜à¦§à¦ƒ");
		fatFingerMap.put('à¦‚', "à¦°à§�à§‚à§Ÿ");
		fatFingerMap.put('à¦ƒ', "à¦˜à¦§à§�");
		fatFingerMap.put('à§Ž', "à¦—à¦¦à§œ");
		fatFingerMap.put('à¦¾', "à¦¡à¦¿à¦“à§‡,à¦Ÿà¦ª");
		fatFingerMap.put('à§‡', "à¦¾à¦“à¦°à¦¬,");
		fatFingerMap.put('à¦¿', "à¦¡à¦¯à§�à§�à¦“à¦¾à¦ª");
		fatFingerMap.put('à§€', "à¦¢à§Ÿà§‚à§�à¦¯à¦…à¦«à§—");
		fatFingerMap.put('à§‹', "à¦¾à¦“à¦°,à¦¡à¦¿à§‡à¦Ÿà¦ª");
		fatFingerMap.put('à§ˆ', "à¦…à§—à¦²à¥¤");
		fatFingerMap.put('à§Œ', "à¦¾à¦“à¦°à¦¬,à§‚à§�à§ˆà¦…à§€");
		fatFingerMap.put('à§�', "à¦™à§ƒà§�à¦°à¦¿à¦¡à¦¯");
		fatFingerMap.put('à§‚', "à¦‚à§�à¦¯à§€à¦¢à§Ÿ");
	}
	public static boolean isFatFingerBijoyPhone(char a, char b) {
		if(fatFingerMap.get(a).contains(b+"")) return true;
    	return false;
    }
	public static void setFatFingerForProvatPhone() {
		fatFingerMap.put('à¦…', "à¦§à¦Šà¦·");
		fatFingerMap.put('à¦†', "à¦¤à¦šà¦¬");
		fatFingerMap.put('à¦‡', "à¦‰à¦”à¦ƒà¦�");
		fatFingerMap.put('à¦ˆ', "à¦Šà¦¡à¦·");
		fatFingerMap.put('à¦‰', "à¦�à¦‡à¦˜à¦ƒ");
		fatFingerMap.put('à¦Š', "à¦§à¦…à¦·à¦ˆ");
		fatFingerMap.put('à¦‹', "à¦›à¦­");
		fatFingerMap.put('à¦�', "à¦Ÿà§�à¦¤à¦—");
		fatFingerMap.put('à¦�', "à¦ à¦¥à¦˜à¦‰");
		fatFingerMap.put('à¦“', "à¦¿à¦œà¦ª");
		fatFingerMap.put('à¦”', "à¦‡à¦�à¦«");
		
		fatFingerMap.put('à¦•', "à¦“à¦œà§ƒà§�à¦²à¦ª");
		fatFingerMap.put('à¦–', "à¦”à¦�à§ƒà¦�à§ˆ");
		fatFingerMap.put('à¦—', "à¦�à¦¤à¦†à¦¬à¦¨à¦¹à¦¿à¦‰");
		fatFingerMap.put('à¦˜', "à¦�à¦¥à¦‹à¦­à¦£à¦ƒà¦‰");
		fatFingerMap.put('à¦™', "à¦ƒà¦£à§ƒà¦�");
		fatFingerMap.put('à¦š', "à¦¡à¦¶à¦†à¦¤");
		fatFingerMap.put('à¦›', "à¦¢à§�à¦‹à¦¥");
		fatFingerMap.put('à¦œ', "à¦¿à¦¹à¦®à§ƒà¦•à¦ªà¦“");
		fatFingerMap.put('à¦�', "à¦‡à¦ƒà¦™à§ƒà¦–à¦«à¦”");
		fatFingerMap.put('à¦ž', "à¦�à¦ à¦¥à¦˜à¦‰");
		fatFingerMap.put('à¦Ÿ', "à¦°à¦¡à¦¤à¦�");
		fatFingerMap.put('à¦ ', "à§œà¦¢à¦¥à¦˜à¦‰");
		fatFingerMap.put('à¦¡', "à¦°à¦¸à¦¶à¦šà¦¤à¦Ÿ");
		fatFingerMap.put('à¦¢', "à¦ˆà¦·à§�à¦›à¦¥à¦ à§œ");
		fatFingerMap.put('à¦£', "à¦˜à¦­à¦™à¦ƒ");
		fatFingerMap.put('à¦¤', "à¦Ÿà¦¡à¦šà¦…à¦—à¦�");
		fatFingerMap.put('à¦¥', "à¦ à¦¢à¦›à¦‹à¦˜à¦�");
		fatFingerMap.put('à¦¦', "à§‚à¦¾");
		fatFingerMap.put('à¦§', "à¦Šà¦…");
		fatFingerMap.put('à¦¨', "à¦—à¦¬à¦®à¦¹");
		fatFingerMap.put('à¦ª', "à¦“à¦œà¦•à§‡");
		fatFingerMap.put('à¦«', "à¦”à¦�à¦–à§ˆ");
		fatFingerMap.put('à¦¬', "à¦†à¦¨à¦—");
		fatFingerMap.put('à¦­', "à¦˜à¦‹à¦£à¦ƒ");
		fatFingerMap.put('à¦®', "à¦¹à¦¨à§ƒà¦œ");
		fatFingerMap.put('à¦¯', "à¦·à§�");
		fatFingerMap.put('à§Ÿ', "à¦¸à¦¶");
		fatFingerMap.put('à¦°', "à§€à¦¸à¦¡à¦Ÿ");
		fatFingerMap.put('à¦²', "à§‡à¦ªà¦•à§�");
		fatFingerMap.put('à¦¶', "à¦¸à§Ÿà¦šà¦¡");
		fatFingerMap.put('à¦·', "à¦Šà¦…à¦¯à§�à¦¢à§œà¦ˆ");
		fatFingerMap.put('à¦¸', "à§‚à¦¾à§Ÿà¦¶à¦¡à¦°à§€");
		fatFingerMap.put('à¦¹', "à§‚à¦—à¦¬à¦¨à¦œà¦¿");
		fatFingerMap.put('à§œ', "à¦ˆà¦·à¦¢à¦ ");
		fatFingerMap.put('à§�', "à¦·à¦¯à¦›à¦¢");
		fatFingerMap.put('à¦‚', "à¦–à¦�à§ˆ");
		fatFingerMap.put('à¦ƒ', "à¦‰à¦˜à¦£à¦™à¦�à¦‡");
		fatFingerMap.put('à§Ž', "à¦žà¦‡à¦˜à¦ƒ");
		fatFingerMap.put('à¦¾', "à¦¦à§‚à§€à¦¸à§Ÿ");
		fatFingerMap.put('à§‡', "à¦ªà§‹");
		fatFingerMap.put('à¦¿', "à§�à¦“à¦¹à¦œ");
		fatFingerMap.put('à§€', "à§‚à¦°à¦¸");
		fatFingerMap.put('à§‹', "à§‡à¦²");
		fatFingerMap.put('à§ˆ', "à¦«à§Œà¦–à¦‚");
		fatFingerMap.put('à§Œ', "à§ˆà¦‚");
		fatFingerMap.put('à§�', "à¦�à¦¿à¦—à¦¹");
		fatFingerMap.put('à§‚', "à¦¦à§€à¦¾à¦¸");
	}
	public static boolean isFatFingerProvatPhone(char a, char b) {
		if(fatFingerMap.get(a).contains(b+"")) return true;
    	return false;
    }
	public static void setFatFingerForGboard() {
		fatFingerMap.put('à¦…', "à¦•à¦–à¦†");
		fatFingerMap.put('à¦†', "à¦…à¦•à¦–à¦—à¦‡");
		fatFingerMap.put('à¦‡', "à¦…à¦–à¦—à¦˜à¦ˆ");
		fatFingerMap.put('à¦ˆ', "à¦‡à¦—à¦˜à¦™à¦‰");
		fatFingerMap.put('à¦‰', "à¦ˆà¦˜à¦™à¦šà¦Š");
		fatFingerMap.put('à¦Š', "à¦‰à¦™à¦šà¦›à¦�");
		fatFingerMap.put('à¦‹', "à¦°à¦²à¦¶à§�à§�");
		fatFingerMap.put('à¦�', "à¦Šà¦šà¦›à¦œà¦�");
		fatFingerMap.put('à¦�', "à¦�à¦›à¦œà¦�à¦“");
		fatFingerMap.put('à¦“', "à¦�à¦œà¦�à¦žà¦”");
		fatFingerMap.put('à¦”', "à¦“à¦�à¦ž");
		
		fatFingerMap.put('à¦•', "à¦…à¦†à¦–à¦Ÿà¦ ");
		fatFingerMap.put('à¦–', "à¦…à¦†à¦‡à¦•à¦—à¦Ÿà¦ à¦¡");
		fatFingerMap.put('à¦—', "à¦†à¦‡à¦ˆà¦–à¦˜à¦ à¦¡à¦¢");
		fatFingerMap.put('à¦˜', "à¦‡à¦ˆà¦‰à¦—à¦™à¦¡à¦£");
		fatFingerMap.put('à¦™', "à¦ˆà¦‰à¦Šà¦˜à¦šà¦¢à¦£à¦¤");
		fatFingerMap.put('à¦š', "à¦‰à¦Šà¦�à¦™à¦›à¦£à¦¤à¦ ");
		fatFingerMap.put('à¦›', "à¦Šà¦�à¦�à¦šà¦œà¦¤à¦¥à¦¦");
		fatFingerMap.put('à¦œ', "à¦�à¦�à¦“à¦›à¦�à¦¥à¦¦à¦§");
		fatFingerMap.put('à¦�', "à¦�à¦“à¦”à¦œà¦žà¦¦à¦§à¦¨");
		fatFingerMap.put('à¦ž', "à¦“à¦”à¦�à¦§à¦¨");
		fatFingerMap.put('à¦Ÿ', "à¦•à¦–à¦ à¦ªà¦«");
		fatFingerMap.put('à¦ ', "à¦•à¦–à¦—à¦Ÿà¦¡à¦ªà¦«à¦¬");
		fatFingerMap.put('à¦¡', "à¦–à¦—à¦˜à¦ à¦¢à¦«à¦¬à¦™");
		fatFingerMap.put('à¦¢', "à¦—à¦˜à¦™à¦¡à¦£à¦¬à¦­à¦®");
		fatFingerMap.put('à¦£', "à¦˜à¦™à¦šà¦¢à¦¤à¦­à¦®à¦¯");
		fatFingerMap.put('à¦¤', "à¦™à¦šà¦›à¦¨à¦¥à¦®à¦¯à¦°");
		fatFingerMap.put('à¦¥', "à¦šà¦œà¦›à¦¤à¦¦à¦¯à¦°à¦²");
		fatFingerMap.put('à¦¦', "à¦›à¦œà¦�à¦¥à¦§à¦°à¦²à¦¶");
		fatFingerMap.put('à¦§', "à¦œà¦�à¦žà¦¦à¦¨à¦²à¦¶à¦·");
		fatFingerMap.put('à¦¨', "à¦�à¦žà¦§à¦¶à¦·");
		fatFingerMap.put('à¦ª', "à¦Ÿà¦ à¦«à¦¸à¦¹");
		fatFingerMap.put('à¦«', "à¦Ÿà¦ à¦¡à¦ªà¦¬à¦¸à¦¹à§œ");
		fatFingerMap.put('à¦¬', "à¦ à¦¡à¦¢à¦«à¦­à¦¹à§œà§�");
		fatFingerMap.put('à¦­', "à¦¡à¦¢à¦£à¦¬à¦®à§œà§�à§Ÿ");
		fatFingerMap.put('à¦®', "à¦¢à¦£à¦¤à¦­à¦¯à§�à§Ÿà§Ž");
		fatFingerMap.put('à¦¯', "à¦£à¦¤à¦¥à¦®à¦°à§Ÿà§Žà§�");
		fatFingerMap.put('à§Ÿ', "à¦­à¦®à¦¯à§�à§Ž");
		fatFingerMap.put('à¦°', "à¦¤à§�à¦¯à¦¥à¦¦à¦¯à¦²à§Žà¦‹");
		fatFingerMap.put('à¦²', "à¦¥à¦¦à¦§à¦°à¦¶à§�à¦‹à§�");
		fatFingerMap.put('à¦¶', "à¦¦à¦§à¦¨à¦²à¦·à¦‹à§� ");
		fatFingerMap.put('à¦·', "à¦§à¦¨à¦¶à§�");
		fatFingerMap.put('à¦¸', "à¦ªà¦«à¦¹,");
		fatFingerMap.put('à¦¹', "à¦ªà¦«à¦¬à¦¸à§œ,");
		fatFingerMap.put('à§œ', "à¦«à¦¬à¦­à¦¹à§�,");
		fatFingerMap.put('à§�', "à¦¬à¦­à¦®à§œà§Ÿ");
		fatFingerMap.put('à¦‚', "");
		fatFingerMap.put('à¦ƒ', "");
		fatFingerMap.put('à§Ž', "à¦®à¦¯à¦°à§Ÿà§�à¥¤");
		fatFingerMap.put('à¦¾', "");
		fatFingerMap.put('à§‡', "");
		fatFingerMap.put('à¦¿', "");
		fatFingerMap.put('à§€', "");
		fatFingerMap.put('à§‹', "");
		fatFingerMap.put('à§ˆ', "");
		fatFingerMap.put('à§Œ', "");
		fatFingerMap.put('à§�', "");
		fatFingerMap.put('à§‚', "");
	}
	public static boolean isFatFingerGboard(char a, char b) {
		if(fatFingerMap.get(a).contains(b+"")) return true;
    	return false;
    }
*/
}
