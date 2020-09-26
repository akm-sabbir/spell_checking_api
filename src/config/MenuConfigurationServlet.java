//package config;
//
//import java.io.IOException;
//import java.util.*;
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
//import permission.MenuDAO;
//import permission.MenuDTO;
//import permission.MenuRepository;
//import role.MenuPermissionDAO;
//import role.PermissionRepository;
//import sessionmanager.SessionConstants;
//import user.UserDTO;
//import user.UserRepository;
//import util.CollectionUtils;
//import util.JSPConstant;
//import util.ServletConstant;
//
//@WebServlet("/MenuConfigurationServlet")
//public class MenuConfigurationServlet extends HttpServlet{
//	private static final long serialVersionUID = 1L;
//	MenuDAO menuDAO = new MenuDAO();
//	Logger logger = Logger.getLogger(MenuConfigurationServlet.class);
//
//	
//	public MenuConfigurationServlet()
//	{
//		super();
//	}
//	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		LoginDTO loginDTO = (LoginDTO)request.getSession().getAttribute(SessionConstants.USER_LOGIN);
//		UserDTO userDTO = UserRepository.getInstance().getUserDTOByUserID(loginDTO);
//		
//
//
//		try
//		{
//			String actionType = request.getParameter("actionType");
//
//			if(actionType.equals("getEditPage"))
//			{
//				if(PermissionRepository.getInstance().checkPermissionByRoleIDAndMenuID(userDTO.roleID, MenuConstants.MENU_SETTINGS_VIEW)){
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
//		request.getRequestDispatcher("configuration/menuConfig.jsp").forward(request, response);;
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		LoginDTO loginDTO = (LoginDTO)request.getSession().getAttribute(SessionConstants.USER_LOGIN);
//		UserDTO userDTO = UserRepository.getInstance().getUserDTOByUserID(loginDTO);
//		
//
//		try
//		{
//			String actionType = request.getParameter("actionType");
//
//			if(actionType.equals("edit"))
//			{
//				if(PermissionRepository.getInstance().checkPermissionByRoleIDAndMenuID(userDTO.roleID, MenuConstants.MENU_SETTINGS_UPDATE)){
//					updateMenuConfiguration(request, response);
//				}else{
//					request.getRequestDispatcher(JSPConstant.ERROR_GLOBAL).forward(request, response);
//				}
//				
//			}
//			if(actionType.equals("add")){
//				
//				if(PermissionRepository.getInstance().checkPermissionByRoleIDAndMenuID(userDTO.roleID, MenuConstants.MENU_ADD)){
//					addNewMenu(request,response);
//				}else{
//					request.getRequestDispatcher(JSPConstant.ERROR_GLOBAL).forward(request, response);
//				}
//				
//			}
//		}
//		catch(Exception ex)
//		{
//			logger.fatal("",ex);
//		}
//	}
//	
//	
//	
//	private void addNewMenu(HttpServletRequest request, HttpServletResponse response) throws Exception{
//
//		
//		LoginDTO loginDTO = (LoginDTO)request.getSession().getAttribute(SessionConstants.USER_LOGIN);
//		UserDTO userDTO = UserRepository.getInstance().getUserDTOByUserID(loginDTO);
//		
//
//		
//		MenuDTO menuDTO = new MenuDTO();
//		menuDTO.menuID=Integer.parseInt(request.getParameter("menuID"));
//		menuDTO.parentMenuID=Integer.parseInt(request.getParameter("parentMenuID"));
//		menuDTO.menuName=request.getParameter("menuName");
//		menuDTO.menuNameBangla=request.getParameter("menuNameBangla");
//		menuDTO.hyperLink=request.getParameter("hyperLink");
////		menuDTO.languageTextID=Integer.parseInt(request.getParameter("languageTextID"));
//		menuDTO.orderIndex=Integer.parseInt(request.getParameter("orderIndex"));
//		menuDTO.selectedMenuID=Integer.parseInt(request.getParameter("selectedMenuID"));
//		menuDTO.isVisible=  (request.getParameter("isVisible")!=null);
//		menuDTO.requestMethodType=Integer.parseInt(request.getParameter("requestMethodType"));
//		menuDTO.icon=request.getParameter("icon");
//		menuDTO.isAPI = (request.getParameter("isAPI")!=null);
//		menuDTO.constant = request.getParameter(ServletConstant.MENU_CONSTANT_NAME);
//		
//		if(menuDTO.isAPI){
//			menuDTO.isVisible = false;
//		}
//		
//		new MenuDAO().addMenuDTO(menuDTO);
//		new MenuPermissionDAO().addMenuPermissionByRoleIDAndMenuID(userDTO.roleID, menuDTO.menuID);
//
//		response.sendRedirect("MenuConfigurationServlet?actionType=getEditPage");
//	}
//
//	
//	// if any menu is isDeleted the total subtree will be deleted
//	// if any menu is isVisible the total subtree will be invisible
//	private List<Integer> markMenuTreeAsInvisibleAndDeleted(MenuDTO menuDTO,Set<Integer> deletedIDCollection,boolean isParentInvisble){
//		if(menuDTO == null){
//			return Collections.emptyList();
//		}
//		
//		List<Integer> deletedIDList = new ArrayList<>();
//		
//		if(deletedIDCollection.contains(menuDTO.menuID)){
//			deletedIDList.add(menuDTO.menuID);
//			menuDTO.parentMenuID = -1;
//			
//			
//			for(MenuDTO childMenu:menuDTO.getChildMenuList()){
//				deletedIDCollection.add(childMenu.menuID);
//			}
//			
//		}
//		
//		
//		if(isParentInvisble||!menuDTO.isVisible){
//			isParentInvisble = true;
//			menuDTO.isVisible = false;
//		}
//		
//		
//		for(MenuDTO childMenu:menuDTO.getChildMenuList()){
//			markMenuTreeAsInvisibleAndDeleted(childMenu, deletedIDCollection, isParentInvisble);
//		}
//		
//		return deletedIDList;
//	}
//	
//	private List<MenuDTO> createForest(List<MenuDTO> menuList){
//		
//		List<MenuDTO> rootMenuList = new ArrayList<>();
//		
//		Map<Integer,MenuDTO> mapOfMenuDTOToMenuID = new HashMap<>();
//		
//		for(MenuDTO menuDTO: menuList){
//			mapOfMenuDTOToMenuID.put(menuDTO.menuID, menuDTO);
//			if(menuDTO.parentMenuID == -1){
//				rootMenuList.add(menuDTO);
//			}
//		}
//		
//		for(MenuDTO menuDTO: menuList){
//			if(menuDTO.parentMenuID!=-1){
//				MenuDTO parentMenuDTO = mapOfMenuDTOToMenuID.get(menuDTO.parentMenuID);
//				if(parentMenuDTO != null){
//					parentMenuDTO.addChildPermission(menuDTO);
//				}
//			}
//		}
//		
//		
//		
//		return rootMenuList;
//		
//	}
//	
//
//	private void updateMenuConfiguration(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String[] menuIDStrings = request.getParameterValues("menuID");
////		String names[] = request.getParameterValues("name");
//		String[] menueNames = request.getParameterValues("menuName");
//		String[] menuNameBanlas = request.getParameterValues("menuNameBangla");
//		String[] parentMenuIDStrings = request.getParameterValues("parentMenuID"); 
//		String[] hyperLinkStrings = request.getParameterValues("hyperLink");
//		String[] selectedMenuIDStrings = request.getParameterValues("selectedMenuID");
//		String[] isVisibleIDStrings = request.getParameterValues("isVisible");
//		String[] isDeletedIDStrings = request.getParameterValues("isDeleted");
//		String[] orderIndexStrings = request.getParameterValues("orderIndex");
//		String[] requestMethodTypeStrings = request.getParameterValues("requestMethodType");
//		String[] iconStrings = request.getParameterValues("icon");
//		String[] isAPIIDStrings = request.getParameterValues("isAPI");
//		String[] menuConstantNames = request.getParameterValues(ServletConstant.MENU_CONSTANT_NAME);
//		
//		List<Integer> menuIDList = CollectionUtils.convertToList(Integer.class, menuIDStrings);
//		List<String> menuNameList = CollectionUtils.convertToList(String.class, menueNames);
//		List<String> menuNameBanglaList = CollectionUtils.convertToList(String.class, menuNameBanlas);
//		List<Integer> parentMenuIDList = CollectionUtils.convertToList(Integer.class, parentMenuIDStrings);
//		List<String> hyperLinkList = CollectionUtils.convertToList(String.class, hyperLinkStrings);
//		List<Integer> selectedMenuIDList = CollectionUtils.convertToList(Integer.class, selectedMenuIDStrings);
//		List<Integer> orderIndexList = CollectionUtils.convertToList(Integer.class, orderIndexStrings);
//		List<Integer> requestMethodTypeList = CollectionUtils.convertToList(Integer.class, requestMethodTypeStrings);
//		List<String> iconList = CollectionUtils.convertToList(String.class, iconStrings);
//		
//		List<Integer> isVisibleMenuIDList = CollectionUtils.convertToList(Integer.class, isVisibleIDStrings);
//		List<Integer> isDeletedIDList = CollectionUtils.convertToList(Integer.class, isDeletedIDStrings);
//		List<Integer> isAPIIDList = CollectionUtils.convertToList(Integer.class, isAPIIDStrings);
//		List<String> menuConstantNameList = CollectionUtils.convertToList(String.class, menuConstantNames);
//		
//		List<Integer> toBeDeletedMenuIDList = new ArrayList<>(); 
//		
//		
//		for(int isDeletedMenuID: isDeletedIDList){
//			toBeDeletedMenuIDList.addAll(MenuRepository.getInstance().getSubtreeMenuIDListByMenuID(isDeletedMenuID));
//		}
//		
//		menuDAO.deleteMenuByIDList(toBeDeletedMenuIDList);
//		
//		
//		List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
//		
//		Map<Integer,MenuDTO> mapOfMenuDTOToMenuID = new HashMap<>();
//		
//		
//		int[] parametetLengths = {menuIDList.size(),hyperLinkList.size(),menuNameList.size()
//				,orderIndexList.size(),parentMenuIDList.size(),requestMethodTypeList.size()
//				,selectedMenuIDList.size()};
//		
//		
//		for(int parameterLenth: parametetLengths){
//			if(parameterLenth!=parametetLengths[0]){
//				throw new Exception("Parameter Length Mismatched.");
//			}
//		}
//		
//		
//		for(int i = 0;i<menuIDList.size();i++){
//			
//			MenuDTO menuDTO = new MenuDTO();
//
//			menuDTO.menuID = menuIDList.get(i);
//			menuDTO.hyperLink = hyperLinkList.get(i);
//			menuDTO.menuName = menuNameList.get(i);
//			menuDTO.menuNameBangla = menuNameBanglaList.get(i);
//			menuDTO.orderIndex = orderIndexList.get(i);
//			menuDTO.parentMenuID = parentMenuIDList.get(i);
//			menuDTO.requestMethodType = requestMethodTypeList.get(i);
//			menuDTO.selectedMenuID = selectedMenuIDList.get(i);
//			menuDTO.icon = iconList.get(i);
//			menuDTO.constant = menuConstantNameList.get(i);
//			
//			menuDTOList.add(menuDTO);
//			
//			mapOfMenuDTOToMenuID.put(menuDTO.menuID, menuDTO);
//		}
//		
//		for(int isVisibleMenuID:isVisibleMenuIDList){
//			mapOfMenuDTOToMenuID.getOrDefault(isVisibleMenuID, new MenuDTO()).isVisible = true;
//		}
//		
//		for(int isAPIMenuID:isAPIIDList){
//			mapOfMenuDTOToMenuID.getOrDefault(isAPIMenuID, new MenuDTO()).isAPI = true;
//			mapOfMenuDTOToMenuID.getOrDefault(isAPIMenuID, new MenuDTO()).isVisible = false;
//		}
//		
//		
//		
//		List<MenuDTO> rootMenuList = createForest(menuDTOList);
//		for(MenuDTO menuDTO : rootMenuList){
//			markMenuTreeAsInvisibleAndDeleted(menuDTO, new HashSet<>(), false); // cascade delete and invisibility property
//		}
//		
//		
//		MenuDAO menuDAO = new MenuDAO();
//		menuDAO.updateMenuDTOs(menuDTOList);
//		
//		response.sendRedirect("MenuConfigurationServlet?actionType=getEditPage");
//	}	
//
//}
