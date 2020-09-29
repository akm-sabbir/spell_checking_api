package bangla.grammarchecker.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bangla.dao.AnnotatedWordRepository;
import bangla.dao.DictionaryRepository;
import bangla.dao.NamedEntityRepository;
import bangla.dao.NaturalErrorRepository;
import bangla.dao.SadhuCholitMixture;
import bangla.dao.SubjectVerbRepository;
import bangla.dao.gradedPronoun;
import bangla.grammarchecker.BanglaGrammerChecker;
import bangla.grammarchecker.GrammerCheckerDto;
import bangla.grammarchecker.GrammerCheckerFactory;
import bangla.grammarchecker.NirdeshokErrorChecker;
import bangla.grammarchecker.NoSpaceBetweenWordsChecker;
import bangla.grammarchecker.ShadhuCholitMixureChecker;
import bangla.grammarchecker.SpaceErrorBetweenWordsChecker;
import bangla.grammarchecker.SubVerbRelErrorChecker;
import bangla.tokenizer.WordTokenizer;
import repository.RepositoryManager;


public class Main {
	
//	public static String concatWithCommas(Collection<String> words) {
//	    StringBuilder wordList = new StringBuilder();
//	    for (String word : words) {
//	        wordList.append(word + " ");
//	    }
//	    return new String(wordList.deleteCharAt(wordList.length() - 1));
//	}
//	public void mainInvalidateSpace() {
//		spell_checking_dao sp_dao = new spell_checking_dao();
//    	String sql = "SELECT * ";
//		sql += " FROM word " ;
//		sql += " limit " + new Integer(113000).toString();
//		System.out.println(sql);
//		ArrayList<Integer> arrList = new ArrayList<>();
//		//List<word_dto> words =  sp_dao.getDTOs(arrList,  sql, null);
//		//ValidateNoSpaceBetweenWords.buildTrie(words);
//		GrammerCheckerDto res = ValidateNoSpaceBetweenWords.hasError(" এবসতি গুলো একসাথে একই রাস্তার মধ্যে দেখা যায়।");
//		if(res.isValidSentence) {
//			System.out.println("Valid sentence");
//		} else {
//			System.out.println(res.errorType);
//			System.out.println("Invalid word: " + res.wordSuggestion.get(0).wordName);
//			System.out.println("Correct words: " + res.wordSuggestion.get(0).suggestedWord);
//		}
//	}
//	
//	public void mainValidateSpace() {
//		// TODO Auto-generated method stub
//				spell_checking_dao sp_dao = new spell_checking_dao();
//		    	String sql = "SELECT * ";
//				sql += " FROM word " ;
//				sql += " limit " + new Integer(200000).toString();
//				System.out.println(sql);
//				ArrayList<Long> arrList = new ArrayList<>();
//				List<word_dto> words =  sp_dao.getDTOs(arrList,  sql, null);
//				ValidateSpaceErrorBetweenWords.buildTrie(words);
//				GrammerCheckerDto res = ValidateSpaceErrorBetweenWords.hasError("মাস্টার মশাই কি আমাকে ডেকেছেন?");
//				if(res.isValidSentence) {
//					System.out.println("Valid sentence");
//				} else {
//					System.out.println(res.errorType);
//					System.out.println("Wrong word: " + res.wordSuggestion.get(0).wordName);
//					System.out.println("Correct word: " + res.wordSuggestion.get(0).suggestedWord);
//				}
//	}
//
//	public void mainValidateVerbSubRelation() {
//		//ValidateSubVerbRelError.setPurushMap();
//				//ValidateSubVerbRelError.buildTrie();
//				//System.out.println(ValidateSubVerbRelError.isPurushWord("আমরা"));
//				//System.out.println(ValidateSubVerbRelError.isVerb("আগালে"));
//				dictionaryInitializer sp = new dictionaryInitializer();
//				spell_checking_dao sp_dao = new spell_checking_dao();
//				sp.buildVerbAndPurusList(sp_dao);
//				//while(true) {
//					//Scanner in = new Scanner(System.in);
//					String sentence = "তিনি ভাত খাই";;
//					WordTokenizer wt = new WordTokenizer();
//					wt.set_text(sentence);
//					List<String> strings = wt._tokenization();
//					sentence = concatWithCommas(strings);
//					GrammerCheckerDto res = ValidateSubVerbRelError.hasError(sentence);
//					if(res.isValidSentence==false) { 
//						System.out.println("Error Type: " + res.errorType);
//						System.out.println("Word: " + res.wordSuggestion.get(0).wordName + "\nWord type: " + res.wordSuggestion.get(0).wordType + "\nsuggested word: " + res.wordSuggestion.get(0).suggestedWord);
//					} else {
//						System.out.println("Right sentence");
//					}
//				//}
//				
//	}
//	
	public static void main(String[] args) {
		GrammerCheckerFactory.initializeRegisteredCheckers();
    	RepositoryManager.getInstance().addRepository(AnnotatedWordRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(DictionaryRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NamedEntityRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NaturalErrorRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(SubjectVerbRepository.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(SadhuCholitMixture.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(gradedPronoun.getInstance(),true);
//		dictionaryInitializer di = new dictionaryInitializer();
//		di.contextInitialized("");
		System.out.println("++++++++++++++++++++++++++++");
		checkSadhuCholitoMixure();
		System.out.println("++++++++++++++++++++++++++++");
		checkSubjectVerb();
		System.out.println("++++++++++++++++++++++++++++");
//		checkNoSpaceError();
//		System.out.println("++++++++++++++++++++++++++++");
//		checkInvalidSpaceError();
		System.out.println("++++++++++++++++++++++++++++");
		checkNirdeshokError();
	}
	static WordTokenizer WT = new WordTokenizer();
	private static void checkSadhuCholitoMixure() {
		ShadhuCholitMixureChecker validator = new ShadhuCholitMixureChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("তাহারা যাচ্ছিল");
		sentences.add("আমি খাবার খাইতে রাজি হলাম");
		sentences.add("তিনি আমাকে দেখে বিস্মিত হইলেন");
		sentences.add("আমি ভাবিয়াছিলাম তারা কাজটি করবে না");
		for(String sentence: sentences)
		   checkSentenece(validator,sentence);
	}
	private static void checkSubjectVerb() {
		SubVerbRelErrorChecker validator = new SubVerbRelErrorChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("তিনি খাবার খাবে");
		sentences.add("সে এটি করিতেছেন ");
		sentences.add("তুমি কাজটি করেন");
		for(String sentence: sentences)
		   checkSentenece(validator,sentence);
	}
	private static void checkNoSpaceError() {
		NoSpaceBetweenWordsChecker validator = new NoSpaceBetweenWordsChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("এবসতি গুলো একসাথে একই রাস্তার মধ্যে দেখা যায়");
		sentences.add("সারি সারি গাছগুলো দেখা  যায় ");
		sentences.add("বাইরে বাতাসহচ্ছে ");
		for(String sentence: sentences)
		   checkSentenece(validator,sentence);
	}
	private static void checkInvalidSpaceError() {
		SpaceErrorBetweenWordsChecker validator = new SpaceErrorBetweenWordsChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("এত ধন দৌলত দিয়ে আপনি কী করবেন?");
		sentences.add("মাস্টার মশাই কি আমাকে ডেকেছেন? ");
		sentences.add("আহমদ ছফা ১৯৪৩ সালের ৩০ জুন জন্ম গ্রহণ করেন। ");
		for(String sentence: sentences)
		   checkSentenece(validator,sentence);
	}
	private static void checkNirdeshokError() {
		NirdeshokErrorChecker validator = new NirdeshokErrorChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("ঐ লোকটি খুব সৎ।");
		sentences.add("আমি এই মানুষটিকে চিনি। ");
		sentences.add("এই সূত্রটির মাধ্যেম সময় নির্ধারন বলা হয় স্থাণীয় সময়। ");
		for(String sentence: sentences)
		   checkSentenece(validator,sentence);
	}
	private static void checkSentenece(BanglaGrammerChecker validator, String sentence) {
		WT.set_text(sentence);
		List<String> words = WT._tokenization();
		GrammerCheckerDto result = validator.hasError(words);
		System.out.println(sentence);
		if(!result.isValidSentence) {
			System.out.println("Incorrect sentence");
			System.out.println(result.errorType);
			for(int i=0;i<result.wordSuggestion.size();i++) {
				System.out.println(result.wordSuggestion.get(i).wordName);
				System.out.println(result.wordSuggestion.get(i).suggestedWord);
				System.out.println(result.wordSuggestion.get(i).suggestedWordList);
			}
		} else {
			System.out.println("Correct sentence");
		}
	}

}
// এবসতি গুলো একসাথে একই রাস্তার মধ্যে দেখা যায়।
// জাফর ধানও গমের চাষাাবাদ করে।