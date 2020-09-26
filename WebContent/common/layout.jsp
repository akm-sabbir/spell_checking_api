<!DOCTYPE html>
<%@page import="user.*"%><%@page import="org.apache.commons.lang3.ArrayUtils"%><%@page contentType="text/html;charset=utf-8" %>
<%
	String context = "../../.."  + request.getContextPath() + "/";
	String pluginsContext = context +"assets/global/plugins/";   
    request.setAttribute("context", context);
    request.setAttribute("pluginsContext",pluginsContext);   
%>
<html lang="en">
<head>
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon-icon.png">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/favicon-ico" />
<title><%=pageTitle %></title>
<%@ include file="../skeleton/head.jsp"%>
<%
String[] cssStr = request.getParameterValues("css");
for(int i = 0; ArrayUtils.isNotEmpty(cssStr) &&i < cssStr.length;i++)
{
%>
<link href="${context}<%=cssStr[i]%>" rel="stylesheet" type="text/css" />
<%
}
%>
<script type="text/javascript">
var context = '${context}';
var pluginsContext = '${pluginsContext}';
</script>
</head>

<body class="page-container-bg-solid page-header-fixed page-sidebar-closed-hide-logo"  onload="activateMenu(<%=fullMenu%>)">	
<div id="fakeLoader"></div>	
<div class="page-header navbar navbar-fixed-top">		
<%@ include file="../skeleton/header.jsp"%>		
</div>	
<div class="clearfix"></div>	
<div class="page-container">		
<%@ include file="../skeleton/menu.jsp"%>		
<div class="page-content-wrapper">			
<div class="page-content">
<%-- <jsp:include page='flushActionStatus.jsp' /> --%>
<jsp:include page='<%=request.getParameter("body")%>' />				
</div>			
</div>		
</div>		
<%@ include file="../skeleton/includes.jsp"%>
<%
	String[] helpers = request.getParameterValues("helpers");
	for(int i = 0; ArrayUtils.isNotEmpty(helpers)&& i < helpers.length;i++)
	{
%>
<jsp:include page="<%=helpers[i] %>" flush="true">
<jsp:param name="helper" value="<%=i %>" />
</jsp:include>
	<% } %>
<%
String[] jsStr = request.getParameterValues("js");
for(int i = 0; ArrayUtils.isNotEmpty(jsStr)&& i < jsStr.length;i++)
{
%>
<script src="${context}<%=jsStr[i]%>" type="text/javascript"></script>
<%
}
%>
</body>
</html>