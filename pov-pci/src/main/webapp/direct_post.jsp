<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.authorize.sim.*" %>
<%@ page import="net.authorize.sim.button.*" %>
<%@ page import="net.authorize.data.*" %>
<%@ page import="net.authorize.data.creditcard.*" %>

<html>
<body>
<h2>Authorize.net direct post. Credit card information is input on the application page and gets posted directly to authorize.net from the client page. This still requires the matson web server to be PCI compliant.</h2>

<%
    String apiLoginId = "9JqM5H4BpuXu";
    String transactionKey = "9429M6t2c6tWhG4U";
    //My payment page should be exposed to the internet so authorize.net can post back response.
    String relayResponseUrl = "https://kapoors.me/pov-pci/relay_response.jsp";
    String amount = "1.99";

    Fingerprint fingerprint = Fingerprint.createFingerprint(
            apiLoginId,
            transactionKey,
            1234567890,  // Random sequence used for creating the fingerprint
            amount);

    long x_fp_sequence = fingerprint.getSequence();
    long x_fp_timestamp = fingerprint.getTimeStamp();
    String x_fp_hash = fingerprint.getFingerprintHash();
%>
<form name='secure_redirect_form' id='secure_redirect_form_id'
      action='https://test.authorize.net/gateway/transact.dll' method='post'>
    <label>CreditCardNumber</label>
    <input type='text' class='text' name='x_card_num' size='15' />
    <label>Exp.</label>
    <input type='text' class='text' name='x_exp_date' size='4' />
    <label>Amount</label>
    <input type='text' class='text' name='x_amount' size='9' readonly value='<%=amount%>' />
    <input type='text' name='x_invoice_num' value='<%=System.currentTimeMillis()%>' />
    <input type='text' name='x_relay_url' value='<%=relayResponseUrl%>' />
    <%--<input type='text' name='x_relay_always' value='true' />--%>
    <input type='text' name='x_login' value='<%=apiLoginId%>' />
    <input type='text' name='x_fp_sequence' value='<%=x_fp_sequence%>' />
    <input type='text' name='x_fp_timestamp' value='<%=x_fp_timestamp%>' />
    <input type='text' name='x_fp_hash' value='<%=x_fp_hash%>' />
    <input type='text' name='x_version' value='3.1' />
    <input type='text' name='x_method' value='CC' />
    <input type='text' name='x_type' value='AUTH_CAPTURE' />
    <input type='text' name='x_amount' value='<%=amount%>' />
    <input type='text' name='x_test_request' value='FALSE' />
    <input type='text' name='notes' value='extra hot please' />
    <input type='submit' name='buy_button' value='BUY' />

</form>

<!--#INCLUDE FILE=”simlib.asp”-->
<%--
<FORM METHOD=POST ACTION= "https://test.authorize.net/gateway/transact.dll">
    &lt;%&ndash;<% ret = InsertFP (APIloginid, sequence, amount, txnkey) %>&ndash;%&gt;
    <INPUT TYPE=HIDDEN NAME="x_version" VALUE="3.1">
    <INPUT TYPE=HIDDEN NAME="x_login" VALUE="9JqM5H4BpuXu">
    <INPUT TYPE=HIDDEN NAME="x_show_form" VALUE="PAYMENT_FORM">
    <INPUT TYPE=HIDDEN NAME="x_method" VALUE="CC">
    <INPUT TYPE=HIDDEN NAME="x_amount" VALUE="9.95">
    <INPUT TYPE=SUBMIT VALUE="Click here for the secure payment form">
</FORM>
--%>

<%
    //The SDK implementation for SIM is fairly concise. To easily create a finger-
//print for your form POST, you can reference the following code :

//Fingerprint fingerprint = Fingerprint.createFingerprint(
//"YOUR_API_LOGIN_ID",
//"YOUR_TRANSACTION_KEY",
//1234567890,
//"AMOUNT");

//String x_fp_sequence = fingerprint.getSequence();
//String x_fp_timestamp = fingerprint.getTimeStamp();
//String x_fp_hash = fingerprint.getFingerprintHash();


//Parsing a Relay Response is performed by using the ResponseParser class.
//It takes as it's only method parameter a pipe (|) delimited string
//that represents the transaction response passed to the merchant by
//Authorize.net.

//HashMap<ResponseField, String> responseMap =
//ResponseParser.parseResponseString(responseString);

//Setting up the necessary data containers and getting a form that can be
//displayed directly on the page can be performed via the following code (JSP) :
%>
<%
    /*
        String apiLoginId = "9JqM5H4BpuXu";
        String transactionKey = "9429M6t2c6tWhG4U";
        String amount = "1.99";
        Fingerprint fingerprint = Fingerprint.createFingerprint(
                apiLoginId,
                transactionKey,
                1234567890,
                amount);
        long x_fp_sequence = fingerprint.getSequence();
        long x_fp_timestamp = fingerprint.getTimeStamp();
        String x_fp_hash = fingerprint.getFingerprintHash();
    */
%>

<%--
<FORM NAME='formName' ID='formID' ACTION='https://test.authorize.net/gateway/transact.dll' METHOD='POST'>
    <INPUT TYPE='HIDDEN' NAME='x_login' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(apiLoginId)%>'>
    <INPUT TYPE='HIDDEN' NAME='x_fp_sequence' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(Long.toString(x_fp_sequence))%>'>
    <INPUT TYPE='HIDDEN' NAME='x_fp_timestamp' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(Long.toString(x_fp_timestamp))%>'>
    <INPUT TYPE='HIDDEN' NAME='x_fp_hash' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(x_fp_hash)%>'>
    <INPUT TYPE='HIDDEN' NAME='x_version' VALUE='3.1'>
    <INPUT TYPE='HIDDEN' NAME='x_method' VALUE='CC'>
    <INPUT TYPE='HIDDEN' NAME='x_type' VALUE='AUTH_CAPTURE'>
    <INPUT TYPE='TEXT' NAME='x_amount' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(amount)%>'>
    <INPUT TYPE='HIDDEN' NAME='x_show_form' VALUE='payment_form'>
    <INPUT TYPE='HIDDEN' NAME='x_test_request' VALUE='FALSE'>
    <INPUT TYPE='SUBMIT' NAME='submit_button' VALUE='Submit' CLASS='null'>
</FORM>
--%>
</body>
</html>
