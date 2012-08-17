package hibernateSearch;


import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "EMPLOYEE")
@Indexed
public class Employee {

    Long id;
    String firstName;
    String lastName;
    String department;
    Date joinDate;

    @Column(name = "DEPARTMENT")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "EMP_FIRST_NAME")
    @Field(index= Index.UN_TOKENIZED, store= Store.NO)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Id
    @Column(name = "EMP_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "JOIN_DATE")
    @Field(index=Index.UN_TOKENIZED)
    @DateBridge(resolution=Resolution.DAY)
    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    @Column(name = "EMP_LAST_NAME")
    @Field(index= Index.UN_TOKENIZED, store= Store.NO)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }
}
