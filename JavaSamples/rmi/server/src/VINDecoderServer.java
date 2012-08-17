package rmi.server.src;

import rmi.server.src.VINDecoderImpl;

import java.rmi.Naming;
import java.rmi.*;
import java.rmi.registry.*;

public class VINDecoderServer {

   private static String jarPath = "/usr/local/vendor/bea/user_projects/matson/lib/jato_matson.jar";

   public VINDecoderServer() {
        try{
            //Start the RMI Server
            //Registry registry = LocateRegistry.createRegistry(1099);

            // Assign a security manager, in the event that dynamic
        // classes are loaded
            if (System.getSecurityManager() == null)
             System.setSecurityManager (new RMISecurityManager() {
                public void checkConnect (String host, int port) {}
                public void checkConnect (String host, int port, Object context) {}
                public void checkAccept(String host, int port){}
              });
            System.out.println("Set the security manager");
            // Create an instance of our power service server ...
            VINDecoderImpl vd = new VINDecoderImpl();
            vd.setDB("/usr/local/vendor/bea/user_projects/matson/lib/jato_matson.jar");
            System.out.println("Decoding vin...");
            System.out.println(vd.decode("1GTDL19W5YB530809",null));
            System.out.println("End Decoding vin...");


            // ... and bind it with the RMI Registry
            //Naming.bind ("VINDecoderService", vd);

            System.out.println ("VINDecoderService bound....");
        }catch(Exception e){e.printStackTrace();}
   }

   public static void main(String args[]) {
       try{
     //new VINDecoderServer();
            VINDecoderImpl vd = new VINDecoderImpl();
            vd.setDB("/usr/local/vendor/bea/user_projects/matson/lib/jato_matson.jar");
            System.out.println("Decoding vin...");
            System.out.println(vd.decode("1D7HA18N32S709816",null));
            System.out.println("End Decoding vin...");
            System.out.println("Starting the rmiregistry on port 1099");
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.bind ("VINDecoderService", vd);
            System.out.println ("VINDecoderService bound....");
        }catch(Exception e){e.printStackTrace();}
   }
}