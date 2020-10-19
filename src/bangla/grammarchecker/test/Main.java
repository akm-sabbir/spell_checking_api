package bangla.grammarchecker.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import bangla.ErrorsInBanglaLanguage;
import bangla.dao.AnnotatedWordRepository;
import bangla.dao.DictionaryRepository;
import bangla.dao.GradedPronoun;
import bangla.dao.NamedEntityRepository;
import bangla.dao.NaturalErrorRepository;
import bangla.dao.SadhuCholitMixture;
import bangla.dao.SubjectVerbRepository;
import bangla.grammarchecker.BanglaGrammerChecker;
import bangla.grammarchecker.GrammarCheckerConstant;
import bangla.grammarchecker.GrammerCheckerFactory;
import bangla.grammarchecker.GrammerCheckerTypes;
import bangla.grammarchecker.NirdeshokErrorChecker;
import bangla.grammarchecker.NoSpaceBetweenWordsChecker;
import bangla.grammarchecker.ShadhuCholitMixureChecker;
import bangla.grammarchecker.SpaceErrorBetweenWordsChecker;
import bangla.grammarchecker.SubVerbRelErrorChecker;
import bangla.spellchecker.SpellCheckingDto;
import bangla.tokenizer.WordTokenizer;
import repository.RepositoryManager;


public class Main {

	public static void main(String[] args) {
		GrammerCheckerFactory.initializeRegisteredCheckers();
    	RepositoryManager.getInstance().addRepository(AnnotatedWordRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(DictionaryRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NamedEntityRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(NaturalErrorRepository.getInstance(), true);
    	RepositoryManager.getInstance().addRepository(SubjectVerbRepository.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(SadhuCholitMixture.getInstance(),true);
    	RepositoryManager.getInstance().addRepository(GradedPronoun.getInstance(),true);
		
    	Map<String,String> sentences = GcTestDao.getAllErrorSentence();
    	int failed = 0;
    	List<String> failedList = new ArrayList<>();
    	for(Map.Entry<String, String> mp: sentences.entrySet()) {
    	//	System.out.println(mp.getKey() + "    " + mp.getValue());
    		if(checkSentenece(mp.getKey(), mp.getValue())==0) {
    			failed++;
    			failedList.add(mp.getKey() + "    " + mp.getValue());
    		}
    	}
    	System.out.println("Total failed " + failed + " out of " + sentences.size());
    	for(String str: failedList) {
    		System.out.println(str);
    	}

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
			checkSentenece((1<<ErrorsInBanglaLanguage.sadhuCholitChecker-1)+1, validator,sentence);
	}
	private static void checkSubjectVerb() {
		SubVerbRelErrorChecker validator = new SubVerbRelErrorChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("তিনি খাবার খাবে");
		sentences.add("সে এটি করিতেছেন ");
		sentences.add("তুমি কাজটি করেন");
		for(String sentence: sentences)
			checkSentenece((1<<ErrorsInBanglaLanguage.subjectVerbChecker-1)+1,validator,sentence);
	}
	private static void checkNoSpaceError() {
		NoSpaceBetweenWordsChecker validator = new NoSpaceBetweenWordsChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("এবসতি গুলো একসাথে একই রাস্তার মধ্যে দেখা যায়");
		sentences.add("সারি সারি গাছগুলো দেখা  যায় ");
		sentences.add("বাইরে বাতাসহচ্ছে ");
		for(String sentence: sentences)
			checkSentenece((1<<ErrorsInBanglaLanguage.nospaceNeededChecker-1)+1,validator,sentence);
	}
	private static void checkInvalidSpaceError() {
		SpaceErrorBetweenWordsChecker validator = new SpaceErrorBetweenWordsChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("এত ধন দৌলত দিয়ে আপনি কী করবেন?");
		sentences.add("মাস্টার মশাই কি আমাকে ডেকেছেন? ");
		sentences.add("আহমদ ছফা ১৯৪৩ সালের ৩০ জুন জন্ম গ্রহণ করেন। ");
		for(String sentence: sentences)
			checkSentenece((1<<ErrorsInBanglaLanguage.validspacemissingChecker-1)+1,validator,sentence);
	}
	private static void checkNirdeshokError() {
		NirdeshokErrorChecker validator = new NirdeshokErrorChecker();
		List<String> sentences = new ArrayList<>();
		sentences.add("ঐ লোকটি খুব সৎ।");
		sentences.add("আমি এই মানুষটিকে চিনি। ");
		sentences.add("এই সূত্রটির মাধ্যেম সময় নির্ধারন বলা হয় স্থাণীয় সময়। ");
		for(String sentence: sentences)
			checkSentenece((1<<ErrorsInBanglaLanguage.nirdeshokChecker-1)+1,validator,sentence);
	}
	private static int checkSentenece(String sentence,String errorType) {
		BanglaGrammerChecker validator = null;
		int errorTypeBit = 0;
		if(errorType.equals(GrammerCheckerTypes.SHADHU_CHOLIT_ERROR.toString())) {
			validator = new ShadhuCholitMixureChecker();
			errorTypeBit = ErrorsInBanglaLanguage.sadhuCholitChecker;
		} else if(errorType.equals(GrammerCheckerTypes.SUBJECT_VERB_AGREEMENT_ERROR.toString())) {
			validator = new SubVerbRelErrorChecker();
			errorTypeBit = ErrorsInBanglaLanguage.subjectVerbChecker;
		}  else if(errorType.equals(GrammerCheckerTypes.NO_SPACE_BETWEEN_WORDS_ERROR.toString())) {
			validator = new NoSpaceBetweenWordsChecker();
			errorTypeBit = ErrorsInBanglaLanguage.nospaceNeededChecker;
		} else if(errorType.equals(GrammerCheckerTypes.SPACE_BETWEEN_WORDS_ERROR.toString())) {
			validator = new SpaceErrorBetweenWordsChecker();
			errorTypeBit = ErrorsInBanglaLanguage.validspacemissingChecker;
		} else if(errorType.equals(GrammerCheckerTypes.WRONG_NIRDESHOK_ERROR.toString())) {
			validator = new NirdeshokErrorChecker();
			errorTypeBit = ErrorsInBanglaLanguage.nirdeshokChecker;
		}
		errorTypeBit = (1<<errorTypeBit-1)+1;
		WT.set_text(sentence);
		List<String> words = WT._tokenization();
	    List<SpellCheckingDto> res = validator.hasError(words);
	    int isMatched = 0;
	    for(SpellCheckingDto dto: res) {
	    	if(dto.word != null) {
	    		if(dto.errorType==errorTypeBit) isMatched=1;
	    	//	System.out.println(dto.word + " Error Type:" + dto.errorType);
	    	}
	    	
	    }
	    return isMatched;
	}
	private static void checkSentenece(int errorType,BanglaGrammerChecker validator, String sentence) {
		WT.set_text(sentence);
		List<String> words = WT._tokenization();
	    List<SpellCheckingDto> res = validator.hasError(words);
	    System.out.println("++++++++++++++++++++++++++++");
	    System.out.println(sentence);
	    System.out.println(errorType);
	    int isMatched = 0;
	    for(SpellCheckingDto dto: res) {
	    	if(dto.word != null) {
	    		if(dto.errorType==errorType) isMatched=1;
	    		System.out.println(dto.word + " Error Type:" + dto.errorType);
	    	}
	    	
	    }
	    if(isMatched==1) System.out.println("Test Passed");
	    else System.out.println("Test Failed");
	    System.out.println("++++++++++++++++++++++++++++");
	}

}
// এবসতি গুলো একসাথে একই রাস্তার মধ্যে দেখা যায়।
// জাফর ধানও গমের চাষাাবাদ করে।