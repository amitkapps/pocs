package poc.flexjson.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 10, 2010
 * Time: 12:33:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Employee {
    String firstName;
    String lastName;
    List<Phone> phones;

    public Employee() {
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return lastName + ", " + firstName;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public Employee setPhones(List<Phone> phones) {
        this.phones = phones;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Employee");
        sb.append("{firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", fullName='").append(getFullName()).append('\'');
        sb.append(", phones=").append(phones);
        sb.append('}');
        return sb.toString();
    }
}