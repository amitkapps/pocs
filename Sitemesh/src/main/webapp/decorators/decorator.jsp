<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC
  "-//W3C//DTD HTML 4.01//EN"
  "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <link href="${pageContext.servletContext.contextPath}/resources/css/ocean_menu.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h1><decorator:title/></h1>
<hr/>
<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
<decorator:body/>
<h6>Copyright ABCD Company</h6>
</body>
</html>
