package si.ftp;

import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 5/12/11
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CASFtpSessionFactory  implements SessionFactory{
    private int proxyId;

    public CASFtpSessionFactory(int proxyId) {
        this.proxyId = proxyId;
    }

    @Override
    public Session getSession() {
//        return new CASFtpSession(proxyId);
        return null;
    }
}
