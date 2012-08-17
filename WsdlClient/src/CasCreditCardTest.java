import com.matson.cas.creditCardPayment.ws.client.CASCreditCardLocator;
import com.matson.cas.creditCardPayment.ws.client.CASCreditCardPort;
import com.matson.cas.service.creditCardPayment.model.AuditTrail;
import com.matson.cas.service.creditCardPayment.model.CreditCard;
import com.matson.cas.service.creditCardPayment.model.Transaction;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: axk
 * Date: 7/12/12
 * Time: 8:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CasCreditCardTest {

    public static void main(String[] args) throws RemoteException, ServiceException {
        String wsdlUrl = "http://vtaidala:vtaidala@10.3.4.45:8201/CASCreditCardPaymentService/CASCreditCardService?WSDL";
        CASCreditCardLocator casCreditCard = new CASCreditCardLocator(wsdlUrl, new QName("http://www.matson.com/CASCreditCardService", "CASCreditCard"));
        casCreditCard.setCASCreditCardPortEndpointAddress(wsdlUrl);
        CASCreditCardPort casCreditCardPort = casCreditCard.getCASCreditCardPort();
        // to use Basic HTTP Authentication:
        ((Stub) casCreditCardPort)._setProperty(Call.USERNAME_PROPERTY, "vtaidala");
        ((Stub) casCreditCardPort)._setProperty(Call.PASSWORD_PROPERTY, "vtaidala");

        String response = casCreditCardPort.authorizePayment(getTransactionXml());

        System.out.println(response);
        Transaction.fromXML(response);

    }


    public static String getTransactionXml(){
        Transaction tx = new Transaction();
        tx.setCard(new CreditCard());

        // price
        tx.setAmount(new BigDecimal(100));

        // app ids
        tx.setApplicationIdentifier("POV");
        tx.setApplicationKey("BOOKING1000");

        tx.setAuditTrail(new AuditTrail(true, "POV", new Date()));

        // Credit Card Information.
        tx.getCard().setCardHolderAddressLine("426 N 44th Street");
        tx.getCard().setCardHolderCity("Phoenix");
        tx.getCard().setCardHolderEmailAddress("akapoor@matson.com");
        tx.getCard().setCardHolderFirstName("Amit");
        tx.getCard().setCardHolderLastName("Kapoor");
        tx.getCard().setCardHolderPhone("4807365000");
        tx.getCard().setCardHolderPostalCode("85008");
        tx.getCard().setCardHolderStateCode("AZ");
        tx.getCard().setCardNumber(BigInteger.valueOf(4111111111111111L));
        tx.getCard().setExpirationDate(new Date());
        return tx.toXML();
/*
        ServiceInstanceFactory serviceFactory = ServiceInstanceFactory.getServiceInstance();
        CreditService creditService = serviceFactory.getCreditService();
        resultTx = creditService.authorizePayment(tx);

        // bypass duplicate submissions if they have been previously approved
        if (PovConstants.CC_ERROR == resultTx.getResultResponseCode() &&
                PovConstants.CC_DUPLICATE == resultTx.getResultResponseReasonCode() &&
                booking.getCredit().isAuthorized()) {
        } else {
            booking.getCredit().setAuthorized(resultTx.getResultResponseCode() == Transaction.TX_RESULT_APPROVED);
            booking.getCredit().setResponseMessage(resultTx.getResultResponseMessage());
            booking.getCredit().setTransactionKey(resultTx.getResultTransactionID());
            booking.getCredit().setAuthorizationNumber(resultTx.getResultApprovalCode());
            booking.getCredit().setCcCardDislay(
                    StringUtils.leftPad(StringUtils.right(booking.getCredit().getCardNumber(), 4), 12, "*"));
        }

        if (!booking.getCredit().isAuthorized()) {

        }
*/
    }
}
