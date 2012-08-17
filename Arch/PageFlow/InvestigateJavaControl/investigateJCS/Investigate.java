package investigateJCS; 

import com.bea.control.Control;

public interface Investigate extends Control
{ 

    interface Callback
    {
        void onCreditReportDone(Applicant m_currentApplicant);
    }

    void cancelInvestigation();

    void requestCreditReport(java.lang.String taxID);

} 
