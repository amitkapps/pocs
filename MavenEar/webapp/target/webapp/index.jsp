<%@ page import="com.matson.notify.util.HibernateUtil" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.hibernate.Criteria" %>
<%@ page import="com.matson.notify.cp.Customer" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ page import="java.util.List" %>
<html>
<body>
<h2>Hello World!</h2>
<%
    Session ses = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = ses.beginTransaction();
    Criteria hCriteria = ses.createCriteria(Customer.class);
    hCriteria.add(Restrictions.eq("webId", "SAFSTO01"));
    List custs = hCriteria.list();
    String customers = String.valueOf(custs);
    tx.commit();
    ses.close();

%>
<%=customers%>
</body>
</html>
