package poc.google.collections;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 10, 2010
 * Time: 9:42:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Customer {
    String customerId;
    Set<ArolAuthorization> arolAuthorizations = new HashSet<ArolAuthorization>();

    public Customer(String customerId) {
        this.customerId = customerId;
    }

    public Set<ArolAuthorization> getArolAuthorizations() {
        return arolAuthorizations;
    }

    public void setArolAuthorizations(Set<ArolAuthorization> arolAuthorizations) {
        this.arolAuthorizations = arolAuthorizations;
    }

    @Override
    public String toString() {
        return "Cust{" +
                "id='" + customerId + '\'' +
                ", auths=" + arolAuthorizations +
                '}';
    }
}
