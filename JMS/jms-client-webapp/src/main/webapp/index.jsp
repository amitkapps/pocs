<%@ page import="amitk.poc.jms.producer.JMSMessageSender" %>
<html>
<body>
<h2>JMS Message Listener!</h2>
</body>
<form>
    Post a message to jms.queue.eir.to.facts <input type="submit" name="postMessage" value="true"/>
</form>

<%
    if(null != request.getParameter("postMessage")){
        new JMSMessageSender().sendMessage("MATU457203:70004  :376232    :201210290438");
    }

%>
</html>
