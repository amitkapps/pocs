<%@ page language="java" pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="java.util.List"/>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/displaytag" prefix="display" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>displayReport.jsp</title>

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<display:table name="sessionScope.activeUnitList.list" id="activeUnitListTable" 
  	               export="true" pagesize="10">
  	</display:table>
  
  </body>
</html:html>
