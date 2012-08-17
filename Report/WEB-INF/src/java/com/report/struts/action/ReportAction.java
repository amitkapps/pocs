package com.report.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.report.business.ActiveUnitAction;

/** 
 * MyEclipse Struts
 * Creation date: 02-04-2007
 * 
 * XDoclet definition:
 * @struts.action
 * @struts.action-forward name="success" path="/jsp/displayReport.jsp"
 */
public class ReportAction extends Action {
	/*
	 * Generated Methods
	 */
	Logger log = Logger.getLogger(ReportAction.class);
	
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List list = ActiveUnitAction.listActiveUnits();
		request.getSession().setAttribute("activeUnitList", list);
		log.debug("Returning " + list.size()+" records from ReportAction");
		return mapping.findForward("success");
	}
}