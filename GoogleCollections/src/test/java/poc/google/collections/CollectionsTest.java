package poc.google.collections;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple Authorization.
 */
public class CollectionsTest {

    Logger log = LoggerFactory.getLogger(CollectionsTest.class);

    Customer customer = new Customer("AMITK");

    public Collection<ArolAuthorization> getTradePartyTypeCombos() {
        Set<ArolAuthorization> tradePartyTypeCombos = new HashSet();
        tradePartyTypeCombos.add(new ArolAuthorization(0L, "G", "29"));
//        tradePartyTypeCombos.add(new ArolAuthorization(0L, "M", "22"));
        return tradePartyTypeCombos;
    }

    public void updateTradePartyTypeCombosForArol(Collection<ArolAuthorization> auths, Long arolId){
        for (ArolAuthorization auth : auths){
            auth.setArolId(arolId);
        }
    }

    public void buildTestData() {
        customer.getArolAuthorizations().add(new ArolAuthorization(1L, "H", "02"));
        customer.getArolAuthorizations().add(new ArolAuthorization(1L, "H", "03"));
        customer.getArolAuthorizations().add(new ArolAuthorization(1L, "H", "22"));
        customer.getArolAuthorizations().add(new ArolAuthorization(1L, "G", "29"));
        customer.getArolAuthorizations().add(new ArolAuthorization(2L, "M", "22"));
        customer.getArolAuthorizations().add(new ArolAuthorization(2L, "F", "02"));
        customer.getArolAuthorizations().add(new ArolAuthorization(2L, "F", "03"));
        customer.getArolAuthorizations().add(new ArolAuthorization(2L, "G", "29"));
        log.debug("Customer: {}", customer);
    }

    @Test
    public void googleCollection() {
        buildTestData();
//        log.debug("Authorizations: {}", customer.getArolAuthorizations());
        ImmutableListMultimap<Long, ArolAuthorization> authsByArol
                = Multimaps.index(customer.getArolAuthorizations(), new Function<ArolAuthorization, Long>() {
            public Long apply(ArolAuthorization from) {
//                log.debug("return arol: {}", from.getArolId());
                return from.getArolId();
            }
        });

        log.debug("Auths by Arol: \n {}", authsByArol);

        Collection<ArolAuthorization> filterCriteria = getTradePartyTypeCombos();
        for (Long arolId : authsByArol.keySet()) {
            updateTradePartyTypeCombosForArol(filterCriteria, arolId);
            boolean selected = authsByArol.values().containsAll(filterCriteria);
            if(selected)
                log.info("Selected: {}", arolId);

        }
    }

}
