<%@ page import="poc.flexjson.model.Employee" %>
<%@ page import="com.google.common.collect.Lists" %>
<%@ page import="poc.flexjson.model.Phone" %>
<%@ page import="flexjson.JSONSerializer" %>
<%@ page language="java" %>
<html>
<body>
<h2>Testing jqGrid</h2>

<%
    Employee emp = new Employee("Amit", "Kapoor")
            .setPhones(Lists.newArrayList(new Phone("+1", 1234567890L), new Phone("+91", 9809901234L)));
    String empJson = new JSONSerializer().exclude("*.class", "firstName", "lastName").deepSerialize(emp);

%>
<%=empJson%>
</body>
</html>
