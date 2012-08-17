package flexjson;

import com.google.common.collect.Lists;
import poc.flexjson.model.Employee;
import poc.flexjson.model.Phone;
import org.junit.Test;

import java.util.ArrayList;

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
        Employee emp2 = new Employee("Tim", "Johnes");
        Employee emp3 = new Employee("Tim", "Pollock");
        String empsJson = new JSONSerializer().exclude("*.class", "fullName").serialize(Lists.<Object>newArrayList(emp, emp2, emp3));
        System.out.println("JSON: \n"  + empsJson);

        ArrayList<Employee> employees = new JSONDeserializer<ArrayList<Employee>>().use(null, ArrayList.class).deserialize(empsJson);
        System.out.println("Deserialized EmpJson: \n" + employees);


    }
}