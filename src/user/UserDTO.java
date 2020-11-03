package user;

import java.util.ArrayList;

//import annotation.ColumnName;
//import annotation.PrimaryKey;
//import annotation.TableName;
//import common.PerformanceLog;
//import report.Join;
//import role.RoleDTO;

/**
 * @author Kayesh Parvez
 *
 */
//@TableName(UserDAO.userTable)
public class UserDTO {
	//@PrimaryKey
	//@ColumnName(UserDAO.userTable + ".ID")
	//@Join(PerformanceLog.class)
	public long ID;
	//@ColumnName(UserDAO.userTable + ".username")
	public String userName = "";
	//@ColumnName(UserDAO.userTable + ".password")
	public String password = "";
	//@ColumnName(UserDAO.userTable + ".userType")
	//@Join(UserTypeDTO.class)
	public int userType = 1;
	//@ColumnName(UserDAO.userTable + ".roleID")
	//@Join(RoleDTO.class)
	public long roleID = 1;
	//@ColumnName(UserDAO.userTable + ".languageID")
	public int languageID = 1;
	//@ColumnName(UserDAO.userTable + ".isDeleted")
	public int isDeleted;
	//@ColumnName(UserDAO.userTable + ".mailAddress")
	public String mailAddress = "";
	//@ColumnName(UserDAO.userTable + ".fullName")
	public String fullName = "";
	//@ColumnName(UserDAO.userTable + ".phoneNo")
	public String phoneNo = "";
	//@ColumnName(UserDAO.userTable + ".centre_Type")
	public int centreType = 3;
	public String roleName = "";
	public String userTypeName = "";
	public ArrayList<String> loginIPs = new ArrayList<>();
	//@ColumnName(UserDAO.userTable + ".otoSMS")
	public boolean otpSMS;
	//@ColumnName(UserDAO.userTable + ".otpEmail")
	public boolean otpEmail;
	//@ColumnName(UserDAO.userTable + ".otpPushNotification")
	public boolean otpPushNotification;	
	public long officeID = -1;
	public long employeeID = -1;
	public long organogramID = -1;
	public long unitID = -1;
	
	public long approvalPathID = -1;
	public long approvalOrder = -1;
	public long approvalRole = -1;
	public long maxApprovalOrder = -1;
	
	
	public ArrayList<Integer> roleIDS = null;
	
	@Override
	public String toString() {
		return "UserDTO [ID=" + ID + ", userName=" + userName + ", userType=" + userType + ", roleID=" + roleID
				+ ", languageID=" + languageID + ", isDeleted=" + isDeleted + ", mailAddress=" + mailAddress
				+ ", fullName=" + fullName + ", phoneNo=" + phoneNo + ", roleName=" + roleName + ", userTypeName="
				+ userTypeName + ", loginIPs=" + loginIPs 
				+ "\n officeID = " + officeID
				+ " employeeID = " + employeeID
				+ " organogramID = " + organogramID
				+ "\n approvalPathID = " + approvalPathID
				+ " approvalOrder = " + approvalOrder
				+ " approvalRole = " + approvalRole
				+ " maxApprovalOrder = " + maxApprovalOrder
				+ "]";
	}
	


}
