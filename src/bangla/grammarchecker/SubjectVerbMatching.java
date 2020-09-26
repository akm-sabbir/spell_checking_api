package bangla.grammarchecker;

public class SubjectVerbMatching {
	
	public static boolean isNamPurushSomv(String verb) {
		int len=verb.length();
		if(len>=2 && verb.charAt(len-1)=='ন' && verb.charAt(len-2)=='ে') {
			return true;
		}
		if(verb.contains("ছেন") || verb.contains("চ্ছেন")) {
			return true;
		}
		if(len>=2 && verb.charAt(len-1)=='ন' && verb.charAt(len-2)=='ু') {
			return true;
		}
        if(len>=2 && verb.charAt(len-1)=='ন' && verb.charAt(len-2)=='া') { 
			return true;
		}
		if(verb.contains("লেন")) {
			return true;
		}
		if(verb.contains("তেন")) {
			return true;
		}
		if(verb.contains("বেন")) {
			return true;
		}
		return false;
	}
	public static boolean isModdhomPurushSomv(String verb) {
		int len=verb.length();
		if(len>=2 && verb.charAt(len-1)=='ন' && verb.charAt(len-2)=='ে') {
			return true;
		}
		if(verb.contains("ছেন") || verb.contains("চ্ছেন")) {
			return true;
		}
		if(len>=2 && verb.charAt(len-1)=='ন' && verb.charAt(len-2)=='ু') {
			return true;
		}
        if(len>=2 && verb.charAt(len-1)=='ন' && verb.charAt(len-2)=='া') { 
			return true;
		}
		if(verb.contains("লেন")) {
			return true;
		}
		if(verb.contains("তেন")) {
			return true;
		}
		if(verb.contains("বেন")) {
			return true;
		}
		return false;
	}
	public static boolean isNamPurushSadharon(String verb) {
		int len=verb.length();
		if(len>1 && verb.charAt(len-1)=='ে') {
			return true;
		}
		if(len>2 && (verb.substring(len-2).equals("ছে") || verb.substring(len-3).equals("চ্ছে"))) {
			return true;
		}
		if(len>=2 && verb.charAt(len-1)=='ক' && verb.charAt(len-2)=='ু') {
			return true;
		}
		if(len>=2 && verb.charAt(len-1)=='ল') {
			return true;
		}
		if(len>2 && (verb.substring(len-2).equals("তে") || verb.substring(len-2).equals("তো") || verb.substring(len-2).equals("ইত"))) {
			return true;
		}
		if(len>=2 && verb.charAt(len-1)=='ত' && verb.charAt(len-2)=='ি') {
			return true;
		}
		if(len>2 && verb.substring(len-3).equals("ছিল")) {
			return true;
		}
		if(len>2 && verb.substring(len-2).equals("বে")) {
			return true;
		}
		return false;
	}
	public static boolean isModdhomPurushSadharon(String verb) {
		
		int len=verb.length();
		if(len>=2 && verb.charAt(len-1)=='ছ') {
			return true;
		}
		if(len>2 && verb.substring(len-2).equals("চ্ছ")) {
			return true;
		}
		if(len>2 && verb.substring(len-2).equals("লে")) {
			return true;
		}
		if(len>2 && verb.substring(len-2).equals("তে")) {
			return true;
		}
		if(len>2 && verb.substring(len-2).equals("বে")) {
			return true;
		}
		if(len>=2 && verb.charAt(len-1)=='ও') {
			return true;
		}
		// This is to check অ
		if(isNamPurushSadharon(verb)) return false;
		if(isModdhomPurushSomv(verb)) return false;
		if(isNamPurushSadharon(verb)) return false;
		if(isModdhomPurushTusso(verb)) return false;
		if(isUttomPurush(verb)) return false;
		return true;
	}
	public static boolean isModdhomPurushTusso(String verb) {
		int len=verb.length();
		if(len>=2 && verb.charAt(len-1)=='স' && verb.charAt(len-2)=='ি') {
			return true;
		}
		if(len>3 && verb.substring(len-2).equals("স্‌") && verb.charAt(len-3)=='ি') {
			return true;
		}
		if(len>=2 && verb.charAt(len-1)=='ি' && verb.charAt(len-2)=='ল') {
			return true;
		}
		if(len>=2 && verb.charAt(len-2)=='ব' && verb.charAt(len-1)=='ি') {
			return true;
		}
		if(len>3 && verb.substring(len-2).equals("ইস")) {
			return true;
		}
		return false;
	}
	public static boolean isUttomPurush(String verb) {
		int len=verb.length();
		if(len>=2 && (verb.charAt(len-1)=='ই' || verb.charAt(len-1)=='ি')) {
			return true;
		}
		if(len>=3 && verb.substring(len-3).equals("লাম")) {
			return true;
		}
		if(len>=3 && verb.substring(len-3).equals("লুম")) {
			return true;
		}
		if(len>=3 && verb.substring(len-3).equals("তাম")) {
			return true;
		}
		if(len>=3 && verb.substring(len-3).equals("তুম")) {
			return true;
		}
		if(len>=2 && (verb.charAt(len-1)=='ব' || verb.charAt(len-2)=='ি')) {
			return true;
		}
		if(len>=3 && verb.substring(len-2).equals("ইব")) {
			return true;
		}
		if(len>=3 && verb.substring(len-2).equals("বো")) {
			return true;
		}
		return false;
	}

}
