package bangla.spellchecker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.BitSet;

public class WordDto {
    public long ID;
    public String word;
    public int word_type;
    public int lang_type;
    public int status_cat;
    public long reference_id = 0;
    public int reference_table = 0;
    public int freq = 0;
 
}
