<%@ page import="java.util.Map" %>
<%@ page import="net.authorize.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%
        System.out.println("Relay response being processed!!!");

        //API Login ID provided by Authorize.net
        String apiLoginId = "9JqM5H4BpuXu";
        String receiptPageUrl = "http://63.64.20.165/pov-pci/order_receipt.jsp";
         /*
          * Leave the MD5HashKey as is - unless you have explicitly set it in the
          * merchant interface: Account > Settings > Security Settings > MD5-Hash
          */
        String MD5HashKey = "1234";

        // Perform Java server side processing...
        //API captures all the response from the authorize.net server (nothing but a set of params in the url)
        net.authorize.sim.Result result = net.authorize.sim.Result.createResult(apiLoginId,
                MD5HashKey, request.getParameterMap());

        // Build receipt url buffer
        StringBuffer receiptUrlBuffer = new StringBuffer(receiptPageUrl);

        if(result != null)
        {
            receiptUrlBuffer.append("?");
            receiptUrlBuffer.append(ResponseField.RESPONSE_CODE.getFieldName()).append("=");
            receiptUrlBuffer.append(result.getResponseCode().getCode());
            receiptUrlBuffer.append("&");
            receiptUrlBuffer.append(ResponseField.RESPONSE_REASON_CODE.getFieldName()).append("=");
            receiptUrlBuffer.append(result.getReasonResponseCode().getResponseReasonCode());
            receiptUrlBuffer.append("&");
            receiptUrlBuffer.append(ResponseField.RESPONSE_REASON_TEXT.getFieldName()).append("=");
            receiptUrlBuffer.append(result.getResponseMap().get(
                    ResponseField.RESPONSE_REASON_TEXT.getFieldName()));

            if(result.isApproved())
            {
                receiptUrlBuffer.append("&").append(ResponseField.TRANSACTION_ID.getFieldName()
                ).append("=");
                receiptUrlBuffer.append(result.getResponseMap().get(
                        ResponseField.TRANSACTION_ID.getFieldName()));
            }
        }
    %>


</head>
<body>

<meta http-equiv="refresh" content="0;url=<%=receiptUrlBuffer.toString()%>">

</body>
</html>
