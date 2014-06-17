package poc.ejbs.ejb;

import org.apache.commons.io.IOUtils;
import poc.ejbs.Unit;

import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/14/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "AutoServiceEJB", mappedName = "AutoServiceEJB")
public class AutoServiceBean {
    public AutoServiceBean() {
    }

    public List<Unit> getStatuses(){
        ArrayList<Unit> units = new ArrayList<Unit>();

        units.add(new Unit().setUnitId(1).setBookingNumber("B1"));
        units.add(new Unit().setUnitId(2).setBookingNumber("B2"));

        return units;
    }

}
