package bangla.grammarchecker;

import java.util.HashMap;
import java.util.Map;

import config.GlobalConfigConstants;
import config.GlobalConfigDTO;
import config.GlobalConfigurationRepository;


public class GrammerCheckerFactory {
	
	private GrammerCheckerFactory() {
	}
	private static Map<String,BanglaGrammerChecker> grammarCheckerMap = new HashMap<>();
	private static BanglaGrammerChecker getGrammerChecker(String checkerType) {
		if(checkerType==null) return null;
		if(checkerType.equals(GrammerCheckerTypes.SHADHU_CHOLIT_ERROR.toString())) 
			return new ShadhuCholitMixureChecker();
		else if(checkerType.equals(GrammerCheckerTypes.SUBJECT_VERB_AGREEMENT_ERROR.toString()))
			return new SubVerbRelErrorChecker();
		else if(checkerType.equals(GrammerCheckerTypes.NO_SPACE_BETWEEN_WORDS_ERROR.toString())) 
			return new NoSpaceBetweenWordsChecker();
		else if(checkerType.equals(GrammerCheckerTypes.SPACE_BETWEEN_WORDS_ERROR.toString()))
			return new SpaceErrorBetweenWordsChecker();
		else if(checkerType.equals(GrammerCheckerTypes.WRONG_NIRDESHOK_ERROR.toString()))
			return new NirdeshokErrorChecker();
		return null;
	}
	public static void initializeRegisteredCheckers() {
		GlobalConfigDTO registerType = GlobalConfigurationRepository.getGlobalConfigDTOByID(GlobalConfigConstants.REGISTERED_GRAMMAR_CHECKER_TYPES);
		if(registerType == null || registerType.value == null || registerType.value == "") return;
		
		String[] splittedEnums = registerType.value.split(",");
		
		for(int index = 0; index < splittedEnums.length; index ++) {
			BanglaGrammerChecker checker = getGrammerChecker(splittedEnums[index]);
			if(checker == null) continue;
			grammarCheckerMap.put(splittedEnums[index], checker);
		}
	}
	public static Map<String,BanglaGrammerChecker> getRegisteredCheckers() {
		GlobalConfigDTO registerType = GlobalConfigurationRepository.getGlobalConfigDTOByID(GlobalConfigConstants.REGISTERED_GRAMMAR_CHECKER_TYPES);
		if(registerType == null || registerType.value == null || registerType.value == "") return grammarCheckerMap;
		
		String[] splittedEnums = registerType.value.split(",");
		
		for(int index = 0; index < splittedEnums.length; index ++) {
			if(!grammarCheckerMap.containsKey(splittedEnums[index])) {
				BanglaGrammerChecker checker = getGrammerChecker(splittedEnums[index]);
				if(checker != null) {
					grammarCheckerMap.put(splittedEnums[index], checker);
				}
			}
		}
		
		return grammarCheckerMap;
	}

}
