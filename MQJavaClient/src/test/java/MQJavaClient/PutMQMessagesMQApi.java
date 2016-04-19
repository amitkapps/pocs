package MQJavaClient;

import com.ibm.mq.*;
import com.ibm.mq.pcf.CMQC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by amitkapps on 12/29/15.
 */
public class PutMQMessagesMQApi {


        private MQQueueManager queueManager;
        private MQQueue queue;

        @Before
        public void init() throws MQException {
            initializeMQ();
            System.out.println("MQ Resources initialized");
        }

        @After
        public void destroy() throws MQException {
            queue.close();
            queueManager.disconnect();
            System.out.println("MQ Resources closed");
        }

        @Test
        public void test_putMessages() throws InterruptedException, IOException, MQException {

            System.out.println("Putting messages to MQ");

            MQPutMessageOptions pmo = new MQPutMessageOptions();

            for(int i=1; i<=1; i++){
                MQMessage mqMessage = new MQMessage();
//                mqMessage.writeInt(i);
                mqMessage.writeString("<GroovyMsg msgType='UNIT_DELIVER'  unitClass='CONTAINER' ctrNo='BMOU459670' checkDigit='2' chassisNumber='null' chassisCd='null' category='IMPRT' accessory='null' mgWeight='null' facility='ANK' ybBarge='1' ybTrucker='null' flex02='1'  tareWeight='10320' typeCode='1' hgt='1' strength='FB' owner='MAT' damageCode='null' srv='MAT' temp='null' tempMeasurementUnit='null' temp2='null' tempMeasurementUnit2='null' equipTypeCode='R40H' hazOpenCloseFlag='A' tempSetting='null' cell='null'  locationRow='MAT' cWeight='10320' seal='null' stowRestCode='null' stowFlag='1' odf='null'  bookingNumber='null' consignee='null' shipper='null' cneeCode='null' hazF='null' hazImdg='null' hazUnNum='null' locationCategory='null' arrDate='null' consigneePo='null' restow='NONE' shipperId='null' hazDesc='null' hazRegs='null' ucc='null' ecc='null' doNotBackLoad='null'  commodity='null' dir='IN' dsc='1' planDisp='null' ds='CY' orientation='F'  shipperPool='null' dischargePort='null' dPort='null' loadPort='1' retPort='null' overLongBack='1' overLongFront='1' overWideLeft='1' overWideRight='1' overHeight='1'  loc='null' locationTier='null' locationStatus='3' locationStallConfig='null'  vesvoy='ANK123' truck='ACME' misc1='1' actualVessel='1' actualVoyage='1' leg='1'  hsf7='null' pmd='null' locationRun='1' misc2='null' misc3='null'  action='OGC' aDate='07/16/2015' aTime='04:50:40' doer='LANE 3kramachandran' sectionCode='Z' lastAction='OGC' lastADate='07/16/2015' lastATime='04:50:40' lastDoer='LANE 3kramachandran' aei='null' dss='1' erf='null'  comments='null' crStatus='null' cargoNotes='null'  safetyExpiry='1' lastInspection='1' lastAnnual='1' chassisAlert='1' mgp='null' chassisHold='MAT' chassisNotes='1' chassTareWeight='null'  gateTruckCmpyCd='ACME' gateTruckCmpyName='ACME' gateTruckId='150716045040445' batNumber='null' turnTime='null' gateSeqNo='15197045040' laneGateId='ANK GATE' deptTruckCode='null' deptDport='null' deptVesVoy='null' deptOutGatedDt='null' deptConsignee='null' deptCneeCode='null' deptBkgNum='null' deptMisc3='null' deptCargoNotes='null' gateId='LONG HAUL' />");

                queue.put(mqMessage, pmo);
                System.out.println("Sent message: " + i);
//            Thread.sleep(100);
            }
        }

        private void initializeMQ() throws MQException {
            MQEnvironment.hostname = "mqpoc.dev.matson.com";
            MQEnvironment.port = 1421;
            MQEnvironment.channel = "SYSTEM.ADMIN.SVRCONN";

            queueManager = new MQQueueManager("MQMI_POC");
            System.out.println("Is Queue Manager Connected?" + queueManager.isConnected());
            queue = queueManager.accessQueue("TEST.QL", CMQC.MQOO_OUTPUT);
            System.out.println("Is Queue open? " + queue.isOpen());

        }

  }

