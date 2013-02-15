package poc.webservices.ejb;

import poc.webservices.Unit;

import javax.ejb.Stateless;
import javax.jws.WebResult;
import javax.jws.WebService;
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
@WebService(name = "AutoServiceWS", serviceName = "AutoService", portName = "AutoServiceWSPort",
        targetNamespace = "http://amitk.poc/AutoService")
public class AutoServiceBean {
    public AutoServiceBean() {
    }

    @WebResult(name = "unit")
    public List<Unit> getStatuses(){
        ArrayList<Unit> units = new ArrayList<Unit>();

        units.add(new Unit().setUnitId(1).setBookingNumber("B1"));
        units.add(new Unit().setUnitId(2).setBookingNumber("B2"));

        return units;
    }
}
