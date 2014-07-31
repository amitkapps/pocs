<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Map" %>
<%@ page import="net.authorize.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
<h1>My Receipt Page</h1></br>
<%
    System.out.println("Order receipt being displayed!!!");
    // Show the confirmation data
    Map<String, String[]> requestParameterMap = request.getParameterMap();

    if (requestParameterMap != null && requestParameterMap.containsKey(
            ResponseField.RESPONSE_CODE.getFieldName())) {

        String transactionId = "";
        if (requestParameterMap.containsKey(ResponseField.TRANSACTION_ID.getFieldName())) {
            transactionId = requestParameterMap.get(ResponseField.TRANSACTION_ID.getFieldName())[0];
        }

        // 1 means we have a successful transaction
        if ("1".equals(requestParameterMap.get(ResponseField.RESPONSE_CODE.getFieldName())[0])) {
%>
<h2>Success!</h2>
<h4>Authorize.net Transaction ID:
    <div><%=net.authorize.util.StringUtils.sanitizeString(transactionId)%>
    </div>
</h4>
<%
    } else {
%>
<h2>Error!</h2>

Response Reason: "<%=request.getParameter(ResponseField.RESPONSE_REASON_TEXT.getFieldName())%>"<br/>
Response code: "<%=request.getParameter(ResponseField.RESPONSE_CODE.getFieldName())%><br/>
Response reason code: "<%= request.getParameter(ResponseField.RESPONSE_REASON_CODE.getFieldName())%><br/>
<%
        }
    }
%>
Session: <%=request.getSession().getId()%><br/>
Info: <br/>
<ol>
    <li>This page can display the result of the credit card transaction that happened at authorize.net.</li>
    <li>At no time any credit card information entered servers hosting our code</li>
    <li>No page on Matson servers need to have any inputs for credit card information</li>

</ol>
<a href="index.jsp">Start Over</a>

</body>
</html>