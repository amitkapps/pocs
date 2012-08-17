import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.io.*;

public class VINDecoderClient {

    public static void main(String[] args) {
        try {
            System.out.println("Connecting to VINDecoder Service");
            VINDecoder vd = null;
            //System.out.println("Start decoding vins");
            //System.out.println( "VIN: 1GTDL19W5YB530809\n" + vd.decode("1GTDL19W5YB530809", null) );
            //1D7HA18N32S709816
            //System.out.println( "VIN: 1D7HA18N32S709816\n" + vd.decode("1D7HA18N32S709816", null) );
            //System.out.println( "VIN: <Blank>\n" + vd.decode("", null) );

        DataInputStream din = new
            DataInputStream (System.in);
            String vin = null;

            //read vins from input screen
            for(;;){
              System.out.print ("Enter VIN Number (e to exit/es to stop VINDecoder service): ");
              try{
                  vin = din.readLine();System.out.println();
                  if(vin != null && vin.equalsIgnoreCase("e"))
                      break;
                  else if(vin != null && vin.equalsIgnoreCase("es")){
                      if(vd == null){
                           vd = (VINDecoder)
                           Naming.lookup( "rmi://localhost/VINDecoderService");
                           System.out.println("Stopping Server...");
                      }
                      vd.killServer();
                  }
                  else if(vin != null && vin.equalsIgnoreCase("ss")){
                      startRMIServer();
                  }
                  else{
                      if(vd == null){
                           vd = (VINDecoder)
                           Naming.lookup( "rmi://localhost/VINDecoderService");
                           vd.setDB("/usr/local/vendor/bea/user_projects/matson/lib/jato_matson.jar");
                      }
                      try{
                        System.out.println(vd.decode(vin, null));
                      }catch(ConnectException ce){
                          System.out.println("Re-establising connection...");
                           vd = (VINDecoder)
                           Naming.lookup( "rmi://localhost/VINDecoderService");
                           System.out.println(vd.decode(vin, null));
                      }
                  }
              }catch (MalformedURLException murle) {
                System.out.println();
                System.out.println(
                  "MalformedURLException");
                System.out.println(murle);
            }
            catch (RemoteException re) {
                System.out.println();
                System.out.println(
                            "RemoteException");
                System.out.println(re);
            }
            catch (NotBoundException nbe) {
                System.out.println();
                System.out.println(
                           "NotBoundException");
                System.out.println(nbe);
            }
            catch(Exception e){
              e.printStackTrace();
          }
       } // for loop

        }//try
        catch (Exception e) {
            e.printStackTrace();
        }
    }



  public static void startRMIServer(){
    try{
        Runtime rt = Runtime.getRuntime();
        String[] cmd = new String[] {"java","-classpath",":.:/export/home/akapoor/dev/java/test/rmi/server/jato/lib/derby.jar:/export/home/akapoor/dev/java/test/rmi/server/jato/lib/log4j-1.2.8.jar:/export/home/akapoor/dev/java/test/rmi/server/jato/lib/vl_matson_o.jar:/export/home/akapoor/dev/java/test/rmi/server/jato/lib/vp4jo_vl.jar:/export/home/akapoor/dev/java/test/rmi/server/jato/lib/xerces.jar:","VINDecoderServer"};
        File workingDir = new File("/export/home/akapoor/dev/java/test/rmi/server/jato/src");
        Process p = rt.exec(cmd,null,workingDir);
        //p.waitFor();
        
        InputStream es = p.getErrorStream();
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        System.out.println("Output:\n" );
        String line = null;
        while ( ( line = br.readLine() ) != null ) {
            System.out.println( line );
            if(-1 != line.indexOf("bound")){
                break;
            }
        }
        is.close();
        es.close();
        p.getOutputStream().close();

        
    }catch(Exception e){
        e.printStackTrace();
    }
  }
}