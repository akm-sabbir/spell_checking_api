package bangla.spellchecker;
import java.util.List;

public class SentenceDto {
	public long ID = 0;
	public String content = "";
	public int categoryID = 0;
    public int isDeleted = 0;
    public long lastModificationTime = 0;
	
    public long getID() {
    	return this.ID;
    }
    public String getcontent() {
    	return this.content;
    }
    public int getcategoryID() {
    	return this.categoryID;
    }
    public int getisDeleted() {
    	return this.isDeleted;
    }
    public long getlastModificationTime() {
    	return this.getlastModificationTime();
    }
    @Override
	public String toString() {
            return "$sentence_dto[" +
            " ID = " + ID +
            " content = " + content +
            " categoryID = " + categoryID +
            " isDelted = " + isDeleted +
            " lastModificationTime = " + lastModificationTime +
            "]";
    }
}
