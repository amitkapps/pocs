package com.amitk.java.serialization;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 30, 2010
 * Time: 7:55:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Employee implements Serializable {

    Long id;
    Department department;

    public Long getId() {
        return id;
    }

    public Employee setId(Long id) {
        this.id = id;
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee setDepartment(Department department) {
        this.department = department;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee that = (Employee) o;

        if(this.id != null && that.id != null) return this.id.equals(that.id);
        return department.equals(that.department);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", dept=" + department +
                '}';
    }
}
