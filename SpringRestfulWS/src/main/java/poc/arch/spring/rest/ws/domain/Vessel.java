package poc.arch.spring.rest.ws.domain;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 6/17/14
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Vessel {

    private String vesselCd;
    private String vesselName;


    public String getVesselCd() { return vesselCd; }
    public String getVesselName() { return vesselName; }

    public void setVesselCd(String value) { vesselCd = (value == null ? "" : value.trim()); }
    public void setVesselName(String value) { vesselName = (value == null ? "" : value.trim()); }

}
