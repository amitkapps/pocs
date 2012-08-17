import java.util.HashMap;

public interface VINDecoder extends java.rmi.Remote{

    public void setDB(String dbPath)
        throws java.rmi.RemoteException;
    public String decode(String vin, String jarPath)
        throws java.rmi.RemoteException;
    public void killServer()
        throws java.rmi.RemoteException;
}
//1D7HA18N32S709816