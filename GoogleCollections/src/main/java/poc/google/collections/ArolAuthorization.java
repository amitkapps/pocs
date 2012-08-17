package poc.google.collections;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 10, 2010
 * Time: 6:49:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArolAuthorization {
    Long arolId;
    String trade;//H,G,F,M
    String partyType;//S,C,N,A,F

    public ArolAuthorization(Long arolId, String trade, String partyType) {
        this.arolId = arolId;
        this.trade = trade;
        this.partyType = partyType;
    }

    public Long getArolId() {
        return arolId;
    }

    public void setArolId(Long arolId) {
        this.arolId = arolId;
    }

    @Override
    public String toString() {
        return "{" +
                arolId +
                ", '" + trade + '\'' +
                ", '" + partyType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArolAuthorization)) return false;

        ArolAuthorization that = (ArolAuthorization) o;

        if (!arolId.equals(that.arolId)) return false;
        if (!partyType.equals(that.partyType)) return false;
        if (!trade.equals(that.trade)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = arolId.hashCode();
        result = 31 * result + trade.hashCode();
        result = 31 * result + partyType.hashCode();
        return result;
    }
}
