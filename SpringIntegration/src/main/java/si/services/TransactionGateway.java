package si.services;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 */
public interface TransactionGateway {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doInTransaction(Object message);
}
