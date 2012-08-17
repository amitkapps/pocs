<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html >
  <head>
    <html:base />
    
    <title>displayReport.jsp</title>

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<display:table name="sessionScope.activeUnitList" id="activeUnitListTable" 
  	               export="true" pagesize="10" sort="list" >
  		<display:column property="unitId" sortable="true" />
  		<display:column property="consigneeName" sortable="true" />
  		<display:column property="receiveDt" sortable="true" />
  	</display:table>
  
  </body>
</html:html>
