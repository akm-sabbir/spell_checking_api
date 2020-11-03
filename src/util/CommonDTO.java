package util;

public class CommonDTO {
	public long iD = 0;
	public int isDeleted = 0;
	public long lastModificationTime = 0;
	public int jobCat = -1;
	public String remarks = "";
	public long fileID = -1;
	
	
	public static final int ACTIVE = 0;
	public static final int DELETED = 1;
	public static final int WAITING_FOR_APPROVAL = 2;
	public static final int PREVIOUS_ROW_IN_VALIDATION_HISTORY = 3;

}
