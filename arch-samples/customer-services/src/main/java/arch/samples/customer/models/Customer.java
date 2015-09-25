package arch.samples.customer.models;

/**
 * Created by amitkapps on 9/24/15.
 */
public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String organizationName;

    public Customer(){}

    public Customer(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public Customer setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                '}';
    }
}
