<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.authorize.sim.*" %>
<%@ page import="net.authorize.sim.button.*" %>
<%@ page import="net.authorize.data.*" %>
<%@ page import="net.authorize.data.creditcard.*" %>

<html>
<body>
<h2>Test app to integrate with authorize.net for PCI compliance</h2>


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
    String apiLoginId = "9JqM5H4BpuXu";
    String transactionKey = "9429M6t2c6tWhG4U";
    //This url should be registred in the authorize.net settings
    String relayResponseUrl = "http://63.64.20.165/pov-pci/relay_response.jsp";
    String cancelUrl = "http://63.64.20.165/pov-pci";

    String amount = request.getParameter("amount");
    if(null== amount || "".equals(amount.trim()))
        amount = "1200";

    //requirement to generate a hash for security
    Fingerprint fingerprint = Fingerprint.createFingerprint(
            apiLoginId,
            transactionKey,
            1234567890,
            amount);
    long x_fp_sequence = fingerprint.getSequence();
    long x_fp_timestamp = fingerprint.getTimeStamp();
    String x_fp_hash = fingerprint.getFingerprintHash();
%>

<FORM NAME='formName' ID='formID' ACTION='https://test.authorize.net/gateway/transact.dll' METHOD='POST'>
    <%--Configurable options documented under section Submitting Transactions : http://developer.authorize.net/guides/SIM/wwhelp/wwhimpl/js/html/wwhelp.htm#href=SIM_Submitting_transactions.06.1.html --%>
    <%--User Id--%>
    hidden x_login: <INPUT TYPE='text' NAME='x_login' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(apiLoginId)%>'> <br/>
        <%--Fingerprint--%>
    hidden x_fp_sequence: <INPUT TYPE='text' NAME='x_fp_sequence' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(Long.toString(x_fp_sequence))%>'><br/>
    hidden x_fp_timestamp: <INPUT TYPE='text' NAME='x_fp_timestamp' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(Long.toString(x_fp_timestamp))%>'>  <br/>
    hidden x_fp_hash: <INPUT TYPE='text' NAME='x_fp_hash' VALUE='<%=net.authorize.util.StringUtils.sanitizeString(x_fp_hash)%>'>                                <br/>

    hidden x_version: <INPUT TYPE='text' NAME='x_version' VALUE='3.1'><br/>
    hidden x_method: <INPUT TYPE='text' NAME='x_method' VALUE='CC'><br/>
    hidden x_type: <INPUT TYPE='text' NAME='x_type' VALUE='AUTH_CAPTURE'><br/>
        <%--Amount is part of fingerprint so you can't change amount after generating fp--%>
    hidden x_amount: <INPUT TYPE='text' NAME='x_amount' readonly="readonly" VALUE='<%=net.authorize.util.StringUtils.sanitizeString(amount)%>'><br/>
        <%--We're asking authorize.ne tto show the hosted payment form--%>
        <%--Hosted payment page can also be customized with logos, styles to some extent, font, color etc.
            Ref: http://developer.authorize.net/guides/SIM/wwhelp/wwhimpl/js/html/wwhelp.htm#href=SIM_Submitting_transactions.06.6.html
        --%>
    hidden x_show_form: <INPUT TYPE='text' NAME='x_show_form' VALUE='PAYMENT_FORM'><br/>
        <%--TRUE, to run in test mode to force an error message with CC# 4222222222222 and amount = the error code required (e.g. 27.00)
            Ref: http://developer.authorize.net/guides/SIM/wwhelp/wwhimpl/js/html/wwhelp.htm#href=SIM_Test_transactions.html
        --%>
    hidden x_test_request: <INPUT TYPE='text' NAME='x_test_request' VALUE='FALSE'><br/>

    hidden x_relay_response: <INPUT TYPE="text" NAME="x_relay_response" value="true"/><br/>
        <%--URL to post back the response to--%>
    hidden x_relay_response_url: <input type='text' name='x_relay_url' value='<%=relayResponseUrl%>' /><br/>
    hidden x_cancel_url: <input type="text" name="x_cancel_url" value='<%=cancelUrl%>'/><br/>
        <%--Some other pre defined attributes that can be passed in and will be menioned in the email that authorize.net sends out--%>
    hidden x_invoice_num: <input type='text' name='x_invoice_num' value='1234567' /><br/>
    hidden x_description: <input type='text' name='x_description' value='Vehicle abc shipping service by Matson' /><br/>

    <INPUT TYPE='SUBMIT' NAME='submit_button' VALUE='Submit' CLASS='null'><br/>

    Session: <%= request.getSession().getId()%><br/>

    <ol>
        <li>This page will direct to the payment form hosted at authorize.net. The payment form will input the credit card information (test with cc# 4007000000027, any future expiration date).
        <li>This page should also let the customers know that it will be directing the user to our payment gateway</li>
        <li>After the user submits the credit card information, authorize.net does an http post back to our relay_response.jsp (this url should be registered in your authorize.net account settings) page. Here we simply capture the response code and reason code and do a meta refresh with a url (order_receipt.jsp, this also needs to be a registered url in authorize.net account settings) with all response parameters to order_receipt.jsp on our server. authorize.net captures this html response and renders that in page after the submit.
        <li>Our final page: order_receipt.jsp can finally render our own result page to the customer with success/failure and store the transaction response etc.
    </ol>

</FORM>
</body>
</html>
