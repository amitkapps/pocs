<%@ page import="java.util.Date" %>
<html>
<body>
<h2>Hello World!</h2>


<%
    String requestTime = request.getParameter("requestTime");
    if(null!= requestTime){
        Thread.sleep(Long.parseLong(requestTime)*1000);
    }
    out.println(new Date() + ", Request Time:" + requestTime);
%>
    <form>
        <input name="requestTime" value="<%=(null!=requestTime)?requestTime:0%>"/>
        <input type="submit">
    </form>
</body>
</html>
