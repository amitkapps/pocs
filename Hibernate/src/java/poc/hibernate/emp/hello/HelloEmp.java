package poc.hibernate.emp.hello;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import poc.hibernate.emp.vo.Address;
import poc.hibernate.emp.vo.Employee;
import poc.hibernate.emp.vo.Mail;
import poc.hibernate.emp.vo.Project;
import poc.hibernate.util.HibernateSessionFactory;

public class HelloEmp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String hbmConfFile = "poc/hibernate/emp/hibernate.emp.cfg.xml";
		HibernateSessionFactory.setConfigFile(hbmConfFile);
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		
		//selectEmp(session);
		//addEmp(session);
		updEmp(session);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static void selectEmp(Session session){
		Employee emp = (Employee)session.get(Employee.class, new Long(11148));
		System.out.println(emp.getPrimaryContact().toString());
	}
	
	public static void addEmp(Session session){
		Employee emp = new Employee();
		emp.setEmpFirstName("Don");
		emp.setEmpLastName("Bradman");
		session.save(emp); Criteria crit = session.createCriteria(emp.getClass());
	}
	
	public static void updEmp(Session session){
		Employee emp = new Employee();
		emp.setEmpId(new Long(11148));
		emp = getEmp(session, emp);
		//emp.getDepartment().setDeptDesc("IT System's development and support");
		//((Address)emp.getAddresses().iterator().next()).setAptSuite("500");

		//Address address = new Address("123 Main St", "Apt 100", "O", "Los Angeles", "CA", new Long(74255));
		//emp.getAddresses().remove(address);
		Mail mail = new Mail("akapoor@matson.com","a@b.com");
		session.save(mail);
		emp.setPrimaryContact(mail);
		session.update(emp);
	}
	
	public static Employee getEmp(Session session, Employee emp){
		return (Employee) session.get(Employee.class, emp.getEmpId());
	}

}
