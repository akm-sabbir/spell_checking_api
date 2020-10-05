package word_content;

public class Word_contentService 
{
	public String executeSpellChecking(String text_data, int option)
	{
		if (text_data.length() == 0) 
		{
			return null;
		}

		bangla.SpellAndGrammarChecker spgChecker = new bangla.SpellAndGrammarChecker();
		String results = spgChecker.check(text_data, option);		//	3: both option
		
		if(results == null ) 
		{
			return null;
		}
		
		return results;
	}
}
