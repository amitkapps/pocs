package flexjson;

import com.google.common.collect.Lists;
import flexjson.model.Employee;
import flexjson.model.Phone;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 10, 2010
 * Time: 12:37:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class FlexJsonTest {

    @Test
    public void testBuildJson(){
        Employee emp = new Employee("Amit", "Kapoor")
                .setPhones(Lists.newArrayList(new Phone("+1", 1234567890L), new Phone("+91", 9809901234L)));
        Employee emp2 = new Employee("Tim", "Johnes")
                .setPhones(Lists.newArrayList(new Phone("+1", 1234567890L), new Phone("+91", 9809901234L)));

        String empJson = new JSONSerializer().exclude("*.class", "fullName").serialize(Lists.newArrayList(emp,emp2));
        System.out.println("JSON: \n"  + empJson);

        empJson = new JSONSerializer().include("firstName", "lastName").exclude("*").serialize(Lists.newArrayList(emp,emp2));
        System.out.println("JSON: \n"  + empJson);
/*
        Employee employee = new JSONDeserializer<Employee>().use(null, Employee.class).deserialize(empJson);
        System.out.println("Deserialized EmpJson: \n" + employee);
*/


    }

    @Test
    public void testBuildJsonFromMap(){
        Employee emp = new Employee("Amit", "Kapoor")
                .setPhones(Lists.newArrayList(new Phone("+1", 1234567890L), new Phone("+91", 9809901234L)));
        Employee emp2 = new Employee("Tim", "Johnes")
                .setPhones(Lists.newArrayList(new Phone("+1", 1234567890L), new Phone("+91", 9809901234L)));

        Map<String, Employee> map = new HashMap<String, Employee>();
        map.put("akapoor", emp);
        map.put("tjohnes", emp);

        String empJson = new JSONSerializer().exclude("*.class", "fullName").serialize(map);
        System.out.println(empJson);
    }
}
