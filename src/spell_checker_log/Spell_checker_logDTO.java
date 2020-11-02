package spell_checker_log;
import java.util.*; 
import util.*; 


public class Spell_checker_logDTO extends CommonDTO
{

    public String content = "";
    public String response = "";
	public int unknownWordCount = 0;
	public int clientCat = 0;
	public int ip = 0;
	public long time = 0;
    public String session = "";
	
	
    @Override
	public String toString() {
            return "$Spell_checker_logDTO[" +
            " iD = " + iD +
            " content = " + content +
            " response = " + response +
            " unknownWordCount = " + unknownWordCount +
            " clientCat = " + clientCat +
            " ip = " + ip +
            " time = " + time +
            " session = " + session +
            " isDeleted = " + isDeleted +
            " lastModificationTime = " + lastModificationTime +
            "]";
    }

}