<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="com.matson.vinsight.admin.common.Version"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Application Version</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h1>Application Version Details:</h1>
    <br>Impl Title: <%out.println(Version.getImplementationTitle());%>
    <br>Spec Version: <%out.println(Version.getSpecificationVersion());%>
    <br>Impl Version: <%out.println(Version.getImplementationVersion());%>
    <br>Impl Vendor: <%out.println(Version.getImplementationVendor());%>
    
  </body>
</html>
