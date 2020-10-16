package test;

public class ErrorChecker {
	public static int getErrors(String errorType)
	{
		if("NON_WORD_ERROR".equals(errorType)) return ErrorEnum.UNKNOWNCHECKER.getValue();
		if("REAL_WORD_ERROR".equals(errorType))
		{
			return ErrorEnum.CHOLIT.getValue() |
					ErrorEnum.NIRDESHOKCHECKER.getValue() |
					ErrorEnum.SADHUCHOLITCHECKER.getValue() |
					ErrorEnum.SUBJECTVERBCHECKER.getValue();
		}
		
		return ErrorEnum.UNKNOWNCHECKER.getValue();
	}
	
	public static boolean hasError(int errorType)
	{
		if((errorType & ErrorEnum.HasError.getValue()) == 0) return false;
		return true;
	}
	
	public static boolean isErrorMatched(int errorType, int checkingTypes)
	{
		
		if(!hasError(errorType)) return false;
		return ((errorType & checkingTypes)) > 0 ;
	}
}
