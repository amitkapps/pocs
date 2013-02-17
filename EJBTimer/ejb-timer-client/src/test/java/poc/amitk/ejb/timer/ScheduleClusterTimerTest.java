package poc.amitk.ejb.timer;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import amitk.poc.ejb.timer.ReportGenerator;
import junit.framework.TestCase;
import org.junit.Test;


public class ScheduleClusterTimerTest extends TestCase{

    public static final String jndi = "ejb-timer-ear-1.0-SNAPSHOT/ReportGeneratorBean/remote";

    @Test
    public void testClusterScheduleIntervalTimer() throws Exception{
        InitialContext ctx = createClusterLoginInitialContext();
        ReportGenerator timer = (ReportGenerator) ctx.lookup(jndi);
        timer.scheduleTimer(10000, 5000, "ReportGenerator");
    }

    @Test
    public void testClusterCancelTimer() throws Exception {
        InitialContext ctx = createClusterLoginInitialContext();
        ReportGenerator timer = (ReportGenerator) ctx.lookup(jndi);
        timer.cancelTimer("ReportGenerator");
    }

    private InitialContext createClusterLoginInitialContext() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        env.setProperty(Context.PROVIDER_URL,"jnp://10.0.1.93:1099");
        env.setProperty(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
        env.setProperty("jnp.partitionName", "DefaultPartition");
        return new InitialContext(env);
    }
}