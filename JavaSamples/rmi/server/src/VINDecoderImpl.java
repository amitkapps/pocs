package rmi.server.src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/*
import com.pki.jato.VPJResult;
import com.pki.jato.VinPowerJato;
import com.pki.vp4j.VinPower;
*/

public class VINDecoderImpl extends java.rmi.server.UnicastRemoteObject
    implements VINDecoder{

    public VINDecoderImpl() 
        throws java.rmi.RemoteException{
        super();
/*
        System.out.println("Off to Sleep...");
        try{
            Thread.sleep(30000);
        }catch(Exception e){e.printStackTrace();}
        System.out.println("Up from sleep");
*/
        //System.out.println("Initializing the VINDecoding service...");
        //this.setDB("/usr/local/vendor/bea/user_projects/matson/lib/jato_matson.jar");
        //System.out.println("Decoding sample vin :" + "1GTDL19W5YB530809");
        //System.out.println(this.decode("1GTDL19W5YB530809",null));
        //System.out.println("Ended Decoding vin...");

    }

    public synchronized String decode(String vin, String jarPath) 
        throws java.rmi.RemoteException{
        try{
            System.out.println("Received a request to decode:" + vin);
/*
            VinPowerJato vpj = new VinPowerJato();
            VPJResult vpjr = vpj.decode(vin);
            System.out.println("Decoded:" + vin);

            vpj.finalizeIt();
            return vpjr.getAsXML();
*/
        }catch(Exception e){
            return "error getting vin info: " + e.getMessage();
        }
        return "";
    }


    public void setDB(String dbPath) 
        throws java.rmi.RemoteException{
        System.out.println("Testing VINPower");
        try {
            System.out.println("Setting Jato DBPath:\n" + dbPath);
//            VinPowerJato.setDB(dbPath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void killServer()
        throws java.rmi.RemoteException{
        System.out.println("Shutdown of VINDecoder Service initiated...");
        System.exit(0);
    };

    protected void finalize(){
        System.out.println("VINDecoderImpl class " +
         this.getClass().hashCode() + " unloaded from the JVM");
    }


    public static void main(String[] args){
        try{
            VINDecoderImpl vd = new VINDecoderImpl();
            vd.setDB("/usr/local/vendor/bea/user_projects/matson/lib/jato_matson.jar");
            System.out.println("Decoding vin...");
            System.out.println(vd.decode("1D7HA18N32S709816",null));
            System.out.println("End Decoding vin...");
        }catch(Exception e){e.printStackTrace();}
    }


}
// 1GTDL19W5YB530809
