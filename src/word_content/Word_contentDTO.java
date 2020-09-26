package word_content;
import java.util.*; 
import util.*; 


public class Word_contentDTO extends CommonDTO
{

    public String word = "";
    public String language = "";
	
	
    @Override
	public String toString() {
            return "$Word_contentDTO[" +
            " iD = " + iD +
            " word = " + word +
            " language = " + language +
            " lastModificationTime = " + lastModificationTime +
            "]";
    }

}