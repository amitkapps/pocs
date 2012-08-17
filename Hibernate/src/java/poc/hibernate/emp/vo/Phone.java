package poc.hibernate.emp.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Phone extends Contact{

	Long intlCd;
	Long areaCd;
	Long phoneNo;
	
	public Phone() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Long getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(Long areaCd) {
		this.areaCd = areaCd;
	}
	public Long getIntlCd() {
		return intlCd;
	}
	public void setIntlCd(Long intlCd) {
		this.intlCd = intlCd;
	}
	public Long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this);
	}
}
