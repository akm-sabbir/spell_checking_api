<%@page import="role.PermissionRepository"%><%@page import="permission.MenuConstants"%><%@page import="util.JSPConstant"%><%@page import="config.GlobalConfigConstants"%><%@page import="config.GlobalConfigurationRepository"%><%@page import="user.UserDTO"%><%@page import="java.util.*"%><%@page import="user.UserRepository"%><%@page import="language.LC"%><%@page import="language.LM"%><%@page import="login.LoginDTO"%><%@page import="sessionmanager.SessionConstants"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="pb_notifications.*"%>
<%



String widgetURL =  request.getContextPath() + "/Widget";
%>
<%-- 
<div class="navbar navbar-collapse collapse">
<div class="page-logo">
<a href= "<%=context%>Welcome.do"><img src="<%=context%>assets/static/builder_logo.jpg" alt="logo" class="logo-default"  style="height:75px ;margin:0px !important"/> </a>
<div class="menu-toggler sidebar-toggler"><i class="fa fa-bars"></i></div>
</div>
<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
<ul class="nav navbar-nav position-horizontal">	
 <li class="dropdown notifications-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" onclick="showNotifications()">
              <i class="fa fa-bell-o"></i>
              <%if(countUnSeen > 0){%>
              <span class="label label-danger"><%=countUnSeen %></span>
              <%}%>
            </a>
            <ul class="dropdown-menu">
              <li>
                <!-- inner menu: contains the actual data -->
               <div id="notifications">
	               <ul class="menu">
	                </ul>
               </div>
                
              </li>
              <li class="footer"><a href="#">View all</a></li>
            </ul>
          </li>	
<%if( PermissionRepository.getInstance().checkPermissionByRoleIDAndMenuID(localUserDTO.roleID, MenuConstants.LANGUAGE_TEXT_EDIT)){ %>
<li>
<%
List<Integer> menuIDPathList = (List<Integer>)request.getAttribute("menuIDPath");
int currentMenuID = ((menuIDPathList!=null && menuIDPathList.size()>0)?menuIDPathList.get(menuIDPathList.size()-1):0);
%>
<a href="<%=context%>LanguageServlet?actionType=search&menuID=<%=currentMenuID%>&backLinkEnabled=<%=currentMenuID%>&<%=SessionConstants.SEARCH_CHECK_FIELD%>=true&&<%=SessionConstants.HTML_SEARCH_CHECK_FIELD%>=true&<%=SessionConstants.RECORDS_PER_PAGE%>=10000"> <i class="fa fa-circle"></i><%=LM.getText(LC.GLOBAL_CHANGE_LABEL,localLoginDTO) %></a>
</li>
<%} %>
<%if(Long.parseLong(GlobalConfigurationRepository.getInstance().getGlobalConfigDTOByID(GlobalConfigConstants.DEFAULT_USER_ID).value)!=localLoginDTO.userID){ %>
<li>
<a href="<%=context%>languageChangeServlet"> <i class="fa fa-circle"></i><%=LM.getText(LC.GLOBAL_CHANGE_LANGUAGE, localLoginDTO) %></a>
<li>
<a href=""> <i class="fa fa-circle"></i><%=localUserDTO.userName %></a>
</li>
<li class="separator hide"></li>
<li>
<a href="<%=context%>LogoutServlet"> <i class="fa fa-arrow-right"></i><%=LM.getText(LC.GLOBAL_LOGOUT, localLoginDTO) %></a>
</li>
<%}else{ %>
<li>
<a href="<%=context%>languageChangeServlet"> <i class="fa fa-circle"></i><%=LM.getText(LC.GLOBAL_CHANGE_LANGUAGE, localLoginDTO) %></a>
<li>
<li>
<a href="<%=context%>LoginServlet"> <i class="fa fa-arrow-right"></i><%=LM.getText(LC.GLOBAL_LOG_IN, localLoginDTO) %></a>
</li>
<%} %>

<li>
	<ul style="list-style:none; padding:0;">
		<div class="align-div">
	        <div class="mr-sm-2" style="padding-top: 15px;float:left;margin-right:10px;">
	            <li class="dropdown dropdown-language" id="sso-widget"></li>
	        </div>
	        <div class="my-2 my-sm-0" style="padding-top:10px; float:left;margin-right:7px;">
	            <li class="nav-item dropdown">
	                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
	                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
	                   style="color: #5e5f6b; font-size: 13px; font-weight: bold">
	                    <%
	                        HttpSession httpSession = request.getSession(true);
	                        String name = (String) httpSession.getAttribute("name");
	                        String designation = (String) httpSession.getAttribute("designation");
	                        String officeName = (String) httpSession.getAttribute("officeName");
	                        
	                        if(name == null)
	                        {
	                        	name = "";
	                        }
	                        
	                        if(designation == null)
	                        {
	                        	designation = "";
	                        }
	                        
	                        if(officeName == null)
	                        {
	                        	officeName = "";
	                        }
	                    %>
	                    <%=name%> <%=designation%>
	                    <br>
	                    <%=officeName%>
	                </a>
	                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	                    <a class="dropdown-item" style="color: #5e5f6b; padding: 10px;" href="/ssologout">Logout</a>
	                </div>
	            </li>
	        </div>
	    </div>
    </ul>
</li>


</ul>	
</div>

<%
String notify = "";
//System.out.println("nnnnnoti list size = " + pb_notificationsDTOList.size());
for(int i = 0; i < pb_notificationsDTOList.size(); i ++)
{
	
	String classType = "unseen";
	if(pb_notificationsDTOList.get(i).isSeen == true)
	{
		classType = "seen";
	}
	
	 notify += "<li><a class='" + classType + "' onclick = 'return seeNotification(" + pb_notificationsDTOList.get(i).iD + ");' href='" 
			+ pb_notificationsDTOList.get(i).url + "'>" + pb_notificationsDTOList.get(i).text + "</a></li>"; 
	
	/* out.println("<li><a class='" + classType + "' onclick = 'return seeNotification(" + pb_notificationsDTOList.get(i).iD + ");' href='" 
	+ pb_notificationsDTOList.get(i).url + "'>" + pb_notificationsDTOList.get(i).text + "</a></li>"); */
}
%>
<script>
      
    var widgetURL2 ="<%=widgetURL%>";
    widget.init({
        "widgetColor": "light",
        "widgetSize": "20px",
        "appPermissionURL": widgetURL2
    });
</script>

<script>

var notify ="<%=notify%>";
 
 
 $(document).ready(function(){
		$("div").removeClass("checker");
	});

function showNotifications(){
	$("#notifications ul").html(notify);
}
var isNotiShown = 0;

function seeNotification(i)
{
	console.log('seeNotification called');
	var formData = new FormData();
	

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() 
	{
		if (this.readyState == 4 && this.status == 200) 
		{
			if(this.responseText !='')
			{				
				console.log("Response");
			}
			else
			{
				console.log("No Response");				
			}
		}
		else if(this.readyState == 4 && this.status != 200)
		{
			alert('failed ' + this.status);
		}
	  };
	xhttp.open("POST", 'Pb_notificationsServlet?actionType=makeseen&id=' + i, false);
	xhttp.send(formData);
	return true;
}
</script>
--%>