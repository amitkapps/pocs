package poc.hibernate.emp.vo;

import java.util.Date;

/**
 * Project generated by MyEclipse Persistence Tools
 */

public class Project implements java.io.Serializable {

	// Fields

	private Long projectId;

	private String projectCd;

	private String projectDesc;

	private Date projectStartDt;

	private Date projectEndDt;

	private String projectStatus;

	// Constructors

	/** default constructor */
	public Project() {
	}

	/** minimal constructor */
	public Project(Long projectId) {
		this.projectId = projectId;
	}

	/** full constructor */
	public Project(Long projectId, String projectCd, String projectDesc,
			Date projectStartDt, Date projectEndDt, String projectStatus) {
		this.projectId = projectId;
		this.projectCd = projectCd;
		this.projectDesc = projectDesc;
		this.projectStartDt = projectStartDt;
		this.projectEndDt = projectEndDt;
		this.projectStatus = projectStatus;
	}

	// Property accessors

	public Long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectCd() {
		return this.projectCd;
	}

	public void setProjectCd(String projectCd) {
		this.projectCd = projectCd;
	}

	public String getProjectDesc() {
		return this.projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public Date getProjectStartDt() {
		return this.projectStartDt;
	}

	public void setProjectStartDt(Date projectStartDt) {
		this.projectStartDt = projectStartDt;
	}

	public Date getProjectEndDt() {
		return this.projectEndDt;
	}

	public void setProjectEndDt(Date projectEndDt) {
		this.projectEndDt = projectEndDt;
	}

	public String getProjectStatus() {
		return this.projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return "{projectId=" + projectId + ", projectCd=" + projectCd + "}";
	}

}