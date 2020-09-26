<%@page import="language.LM"%>
<%@page import="language.LC"%>
<%@page import="java.util.Calendar"%>
<div class="page-footer">
<div style="display:inline-block;float:left"><%=Calendar.getInstance().get(Calendar.YEAR)%> &copy; <%=LM.getText(LC.FOOTER_BOTTOM_LEFT_TEXT)%></div>
<div style="display:inline-block;float:right">Powered By  
<a target="_blank" href="http://www.revesoft.com">
<img src="<%=request.getContextPath()%>/images/reve.png" alt="REVE Systems" width="100px">
</a>
</div>
</div>