package poc.google.collections;

/**
 * Hello world!
 */
public class Authorization {

    public static final Authorization HS = new Authorization("H", "02");
    public static final Authorization HC = new Authorization("H", "03");
    public static final Authorization HN = new Authorization("H", "22");
    public static final Authorization GS = new Authorization("G", "02");
    public static final Authorization GC = new Authorization("G", "03");
    public static final Authorization GN = new Authorization("G", "22");
    public static final Authorization MS = new Authorization("M", "02");
    public static final Authorization MC = new Authorization("M", "03");
    public static final Authorization MN = new Authorization("M", "22");

    String trade;//H,G,F,M
    String partyType;//S,C,N,A,F


    public Authorization(String trade, String partyType) {
        this.trade = trade;
        this.partyType = partyType;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    @Override
    public String toString() {
        return "{" +
                trade +
                ", " + partyType + 
                '}';
    }
}
