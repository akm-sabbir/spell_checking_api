package bangla.grammarchecker;

import java.util.ArrayList;
import java.util.List;

import config.GlobalConfigConstants;
import config.GlobalConfigDTO;
import config.GlobalConfigurationRepository;


public class GrammerCheckerFactory {
	
	private GrammerCheckerFactory() {
	}
	private static List<BanglaGrammerChecker> result = null;
	private static BanglaGrammerChecker getGrammerChecker(String checkerType) {
		if(checkerType==null) return null;
		if(checkerType.equals(GrammerCheckerTypes.SHADHU_CHOLIT_ERROR.toString())) 
			return new ValidateShadhuCholitMixure();
		else if(checkerType.equals(GrammerCheckerTypes.SUBJECT_VERB_AGREEMENT_ERROR.toString()))
			return new ValidateSubVerbRelError();
		else if(checkerType.equals(GrammerCheckerTypes.NO_SPACE_BETWEEN_WORDS_ERROR.toString())) 
			return new ValidateNoSpaceBetweenWords();
		else if(checkerType.equals(GrammerCheckerTypes.SPACE_BETWEEN_WORDS_ERROR.toString()))
			return new ValidateSpaceErrorBetweenWords();
		else if(checkerType.equals(GrammerCheckerTypes.WRONG_NIRDESHOK_ERROR.toString()))
			return new ValidateNirdeshokError();
		return null;
	}
	public static void initializeRegisteredCheckers() {
		result = new ArrayList<BanglaGrammerChecker>();
		GlobalConfigDTO registerType = GlobalConfigurationRepository.getGlobalConfigDTOByID(GlobalConfigConstants.REGISTERED_GRAMMAR_CHECKER_TYPES);
		if(registerType == null || registerType.value == null || registerType.value == "") return;
		System.out.println(registerType.value);
		String[] splittedEnums = registerType.value.split(",");
		
		for(int index = 0; index < splittedEnums.length; index ++) {
			BanglaGrammerChecker checker = getGrammerChecker(splittedEnums[index]);
			if(checker == null) continue;
			result.add(checker);
		}
	}
	public static List<BanglaGrammerChecker> getRegisteredCheckers() {
		if(result==null) return new ArrayList<BanglaGrammerChecker>();
		return result;
	}

}
