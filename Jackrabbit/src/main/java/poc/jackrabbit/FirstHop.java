package poc.jackrabbit;

import javax.jcr.Repository;
import javax.jcr.Session;
import org.apache.jackrabbit.core.TransientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * First hop example. Logs in to a content repository and prints a
 * status message.
 */
public class FirstHop {

    /**
     * The main entry point of the example application.
     *
     * @param args command line arguments (ignored)
     * @throws Exception if an error occurs
     */
    public static void main(String[] args) throws Exception {

        Logger log = LoggerFactory.getLogger(FirstHop.class);
        
        Repository repository = new TransientRepository();
        Session session = repository.login();
        try {
            String user = session.getUserID();
            String name = repository.getDescriptor(Repository.REP_NAME_DESC);
            log.info("Logged in as {} to a {} repository", user, name);

        } finally {
            session.logout();
        }
    }

} 