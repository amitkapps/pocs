package amitk.poc.ejb.timer;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/16/13
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportGenerator {
    void scheduleTimer(long initialDuration, long intervalDuration, Serializable info);

    void cancelTimer(Serializable info);

    boolean timerExists(Serializable info);
}
