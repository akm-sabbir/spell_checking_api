<%@page import="theme.ThemeRepository"%>
<%@page import="theme.ThemeDTO"%>
<%@page import="java.util.*"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<%-- 
<link href="<%=context%>assets/css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/google_font.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/simple-line-icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="<%=context%>assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/uniform.default.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/bootstrap-switch.min.css" rel="stylesheet"
	type="text/css" />
<link href="<%=context%>assets/css/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<%-- <link href="<%=context%>assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" /> --%>
<%-- 
<link href="<%=context%>assets/css/plugins.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/layout.min.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/blog.min.css" rel="stylesheet" type="text/css" />

<link href="<%=context%>assets/css/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/css/new_styles.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/scripts/jstree/dist/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/scripts/jstree/dist/themes/default-dark/style.css" rel="stylesheet" type="text/css" />
<link href="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/js/select2.min.js"></script>

<% 
String themeDescription = ThemeRepository.getInstance().getCurrentAppliedThemeDescriprion();
if(themeDescription!=null){
%>
<link href="<%=context%>assets/Templates/<%=themeDescription%>/style.css" rel="stylesheet" type="text/css" />

<%} %>

<link href="<%=context%>assets/css/custom.css" rel="stylesheet" type="text/css" />
<script src="<%=context%>assets/scripts/jquery.min.js" type="text/javascript"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js'></script>
<script src="<%=context%>assets/scripts/portlet-div-tag.js" type="text/javascript"></script>
<script src="<%=context%>assets/scripts/client-autocomplete-tag.js" type="text/javascript"></script>
<script src="<%=context%>assets/scripts/default-form-group-tag.js" type="text/javascript"></script>
<script src="<%=context%>assets/scripts/filePreview.js" type="text/javascript"></script>
<script src="<%=context%>assets/scripts/pb.js" type="text/javascript"></script>


<script src="<%=context%>assets/scripts/dropzone.js" ></script>



<link href="<%=context%>assets/scripts/basic.css" rel="stylesheet" type="text/css" />
<link href="<%=context%>assets/scripts/dropzone.css" rel="stylesheet" type="text/css" />




<link rel="stylesheet" type="text/css" href="http://account.beta.doptor.gov.bd/sso/lib/style.css"/>
<script type="text/javascript" src="http://account.beta.doptor.gov.bd/sso/lib/script.2.min.js"></script>
--%>