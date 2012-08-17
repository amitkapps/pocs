package com.vinsightutils.enabler.fixers.bulk;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.vinsightutils.common.Constants;
import com.shared.utils.GenericQueryResultVO;
import com.vinsightutils.enabler.utils.SelectQueryOutputVO;
import com.shared.utils.GenUtils;
import com.vinsightutils.enabler.utils.VSEnablerFacade;
import com.vinsightutils.enabler.utils.VSEnablerFacadeHelper;

public class VSMessageGenerator {
	private static Logger logger = Logger.getLogger(VSMessageGenerator.class);

	private static ArrayList validEventList = new ArrayList();
	
	public static final String EVT_STUFF = "STUFF" ; 
	public static final String EVT_LOAD_VSL = "LOAD VSL" ;
	public static final String EVT_DSCHRG_VSL = "DSCHRG VSL";
	public static final String EVT_UNSTUFF =  "UNSTUFF";
	public static final String EVT_AVAILABLE_PICK_SET = "AVAILABLE PICK SET" ;
	public static final String EVT_AVAILABLE_MANF_SET = "AVAILABLE MANF SET" ;
	public static final String EVT_OUT_GATE = "OUT GATE";
	public static final String EVT_OGT_CANCEL = "OGT CANCEL" ;
	public static final String EVT_AVAILABLE_PICK_CLEAR =  "AVAILABLE PICK CLEAR" ;
	public static final String EVT_AVAILABLE_MANF_CLEAR = "AVAILABLE MANF CLEAR" ;

	static{
		validEventList.add(EVT_STUFF);
		validEventList.add(EVT_LOAD_VSL);
		validEventList.add(EVT_DSCHRG_VSL);
		validEventList.add(EVT_UNSTUFF);
		validEventList.add(EVT_AVAILABLE_PICK_SET);
		validEventList.add(EVT_AVAILABLE_MANF_SET);
		validEventList.add(EVT_OUT_GATE);
		validEventList.add(EVT_OGT_CANCEL);
		validEventList.add(EVT_AVAILABLE_PICK_CLEAR);
		validEventList.add(EVT_AVAILABLE_MANF_CLEAR);
	}
	
/*	
 * 
 *  1      0      CHAR      Source A (AMIS) or F (FACTS) or V (VINsight)
	3      1-3    CHAR      'EVT'
	8      4-11   INT       VINsight Number
	20     12-31  CHAR      Event code
	4      32-35  CHAR      Event port (HON, LAX, OAK, BALT, etc.)
	8      36-43  INT       Event Date
	9      44-52  CHAR      Event Time (' HH:MM:SS')
	11     53-63  INT       Contianer
	7      64-70  INT       VVD
	
	Sample Messages:
	
	VEVT10093553STUFF               LAX 06052006 13:17:20CRXU4335485       |
	VEVT10441322LOAD VSL            OAK 05042006 11:02:37           LUR577W|
	VEVT10092920DSCHRG VSL          LAX 06022006 16:19:18           MAT446W|
	VEVT10093978UNSTUFF             LAX 06022006 16:21:00CRXU4335485       |
	VEVT10094890AVAILABLE PICK SET  HON 06272006 08:00:00                  |
	VEVT10094890AVAILABLE MANF SET  HON 06262006 15:36:34                  |
	VEVT10094310OUT GATE            LAX 06072006 14:42:28                  |
	VEVT10094207OGT CANCEL          LAX 06052006 13:13:43                  |
	VEVT10094308AVAILABLE PICK CLEARLAX 06022006 16:04:45FBLU4099126       |
	VEVT10094308AVAILABLE MANF CLEARLAX 06022006 16:04:45FBLU4099126       |
*/	
	/**
	 * Generates and returns EVT messages for a list of units.
	 * 
	 * @param appInstance Constants.APP_INSTANCE_XXX
	 * @param evtMessageType any of VSMessageGenerator.EVT_XXX_YYY
	 * @param unitIds
	 * 
	 * @return the generated String EVT messages as Arraylist 
	 */
	public static ArrayList getEVTMsgForUnits(String appInstance, String evtMessageType, int[] unitIds) throws Exception {
		ArrayList evtMessages = null;
		
		if(!validEventList.contains(evtMessageType))
			throw new Exception ("Event " + evtMessageType + " is not a valid event!");
		
		String getEvtSQL = getGenEVTMessageSQL(evtMessageType, unitIds);
		
		long startTime = System.currentTimeMillis();
		ArrayList rawResponse = 
		VSEnablerFacade.postQueryNReturnResult(	appInstance, 
												Constants.APP_ENV_VS, 
												Constants.QUERY_TYPE_SEL,
												false,
												getEvtSQL
											  );
		long timeTaken = System.currentTimeMillis() - startTime;
		
		//LogUtil.logContentsOnEachLine(rawResponse, "Generated EVT messages:");
		
		SelectQueryOutputVO queryOpObj = VSEnablerFacadeHelper.parseSelQryResponse(getEvtSQL, timeTaken, rawResponse);
		GenericQueryResultVO resObj = null;
		if(queryOpObj.isSuccess()){
			resObj = queryOpObj.getGenericQueryResultVO();
			logger.debug("Query executed on:" + appInstance + ", Status:" + queryOpObj.getExecutionStatus() + ", Rows returned:" + resObj.getRowCount());
			logger.debug("Query:[\n" + getEvtSQL + "\n]");
			evtMessages = resObj.getSpecificColumnsValuesAsAL(1);
		}else{
			Exception ex = new Exception( "Query for " + evtMessageType + " for unitIds:["+ GenUtils.getCharDelimitedStr(unitIds, ",") +"], failed!!\nError:\n" + queryOpObj.getFailureMessageAsString());
			logger.error(ex);
			throw ex;
		}
		
		return evtMessages;
	}
	
	private static String getGenEVTMessageSQL(String evtMessageType, int[] unitIds){
		String csvUnitIds = GenUtils.getCharDelimitedStr(unitIds, ",");
		
		return
		" select 'VEVT' || u.UNIT_ID || RPAD(e.event_cd, 20,' ') || t.PORT_CD || ' ' || " +
		"	to_char(e.EVENT_DT, 'MMDDYYYY HH24:MI:ss') " +
		" || RPAD(CASE WHEN e.equipment_number IS NULL THEN ' ' ELSE e.equipment_number END, 11, ' ') " + 
		" || RPAD(CASE WHEN e.VESSEL_VOYAGE_DIR IS NULL THEN ' ' ELSE e.VESSEL_VOYAGE_DIR END, 7, ' ') ||'|' " +
		" EVT_MESSAGE " +
		" from mt_unit u, mt_terminal_inventory ti, mz_terminal t, mt_event_log e " +
		" where ti.terminal_inventory_id = u.last_reported_location_id " +
		" 	and ti.terminal_id = t.terminal_id " +
		" 	and u.unit_id = e.unit_id " +
		" 	and e.EVENT_LOG_ID = " +
		"		(select max(event_log_id) " +
		"			from mt_event_log " +
		"				where unit_id = e.unit_id " +
		"				and event_cd = '" + evtMessageType + "' " +
		"		) " +
		" 	and u.unit_id in ("+ csvUnitIds +") " +
		" order by u.unit_id "

		;
	}
	
}
