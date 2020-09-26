package pb;
public class TempTableDTO {
	
	public long permanent_table_id = 0;
    public int operation_type = 1;
    public long approval_path_type = -1;
    public long approval_order = 0;
   
	
	
    @Override
	public String toString() {
            return "$AptestDTO[" +
            " permanent_table_id = " + permanent_table_id +
            " operation_type = " + operation_type +
            " approval_path_type = " + approval_path_type + 
            " approval_order = " + approval_order + 
            "]";
    }

}