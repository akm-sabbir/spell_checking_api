//package config;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//
//import login.LoginDTO;
//import permission.MenuConstants;
//import role.PermissionRepository;
//import sessionmanager.SessionConstants;
//import user.UserDTO;
//import user.UserRepository;
//import util.JSPConstant;
///**
// * @author Kayesh Parvez
// *
// */
//@WebServlet("/GlobalConfigurationServlet")
//public class GlobalConfigurationServlet extends HttpServlet{
//	
//	private static final long serialVersionUID = 1L;
//	Logger logger = Logger.getLogger(GlobalConfigurationServlet.class);
//	
//	public GlobalConfigurationServlet()
//	{
//		super();
//	}
//	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		LoginDTO loginDTO = (LoginDTO)request.getSession().getAttribute(SessionConstants.USER_LOGIN);
//		UserDTO userDTO = UserRepository.getInstance().getUserDTOByUserID(loginDTO);
//		
//
//
//		try
//		{
//			String actionType = request.getParameter("actionType");
//
//			if("getEditPage".equals(actionType))
//			{
//				if(PermissionRepository.getInstance().checkPermissionByRoleIDAndMenuID(userDTO.roleID, MenuConstants.GLOBAL_SETTINGS_VIEW)){
//					getEditPage(request, response);
//				}else{
//					request.getRequestDispatcher(JSPConstant.ERROR_GLOBAL).forward(request, response);
//				}
//			}
//		}
//		catch(Exception ex)
//		{
//			logger.fatal("",ex);
//		}
//	}
//	
//	private void getEditPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		request.getRequestDispatcher("configuration/config.jsp").forward(request, response);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		LoginDTO loginDTO = (LoginDTO)request.getSession().getAttribute(SessionConstants.USER_LOGIN);
//		UserDTO userDTO = UserRepository.getInstance().getUserDTOByUserID(loginDTO);
//		
//
//		
//		try
//		{
//			String actionType = request.getParameter("actionType");
//
//			if(actionType.equals("edit"))
//			{
//				if(PermissionRepository.getInstance().checkPermissionByRoleIDAndMenuID(userDTO.roleID, MenuConstants.GLOBAL_SETTINGS_UPDATE)){
//					updateGlobalConfiguration(request, response);
//				}else{
//					request.getRequestDispatcher(JSPConstant.ERROR_GLOBAL).forward(request, response);
//				}
//			}
//			else if(actionType.equals("search"))
//			{
//				searchGlobalSettings(request, response);
//			}
//			else if(actionType.equals("getEditPage"))
//			{
//				if(PermissionRepository.getInstance().checkPermissionByRoleIDAndMenuID(userDTO.roleID, MenuConstants.GLOBAL_SETTINGS_VIEW)){
//					getEditPage(request, response);
//				}else{
//					request.getRequestDispatcher(JSPConstant.ERROR_GLOBAL).forward(request, response);
//				}
//			}
//		}
//		catch(Exception ex)
//		{
//			logger.fatal("",ex);
//		}
//	}
//
//	private void searchGlobalSettings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		int groupID = Integer.parseInt(request.getParameter("groupID"));
//		ArrayList<GlobalConfigDTO> list = (ArrayList<GlobalConfigDTO>) GlobalConfigurationRepository.getInstance().getConfigsByGroupID(groupID);
//		request.setAttribute("list", list);
//		
//		request.getRequestDispatcher("GlobalConfigurationServlet?actionType=getEditPage").forward(request, response);
//	}
//
//	private void updateGlobalConfiguration(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String IDs[] = request.getParameterValues("ID");
//		String groupIDs[] = request.getParameterValues("groupID");
//		String values[] = request.getParameterValues("value");
//		ArrayList<GlobalConfigDTO> globalConfigurationDTOList = new ArrayList<GlobalConfigDTO>();
//		for(int i = 0; i < IDs.length; i++)
//		{
//			try
//			{
//				GlobalConfigDTO globalConfigDTO = new GlobalConfigDTO();
//				globalConfigDTO.ID = Integer.parseInt(IDs[i]);
//				globalConfigDTO.value = values[i];
//				globalConfigDTO.groupID = Integer.parseInt(groupIDs[i]);
//				globalConfigurationDTOList.add(globalConfigDTO);
//			}
//			catch(Exception ex) {
//				logger.fatal("",ex);
//			}
//		}
//		GlobalConfigDAO globalConfigDAO = new GlobalConfigDAO();
//		globalConfigDAO.updateGlobalConfiguration(globalConfigurationDTOList);
//		response.sendRedirect("GlobalConfigurationServlet?actionType=getEditPage");
//	}	
//
//}
