package com.matson.notify.cp;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORGANIZATION")
public class Organization
{
    private Long id;
    private String organizationAbbreviation;
    private String organizationName;
    private Set<Address> addresses = new HashSet<Address>();

    public Organization() {
    }

    @Id
    @GeneratedValue
    @Column(name = "ORGN_IDENTIFIER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORGN_ABBREVIATION", insertable = false, updatable = false)
    public String getOrganizationAbbreviation() {
        return organizationAbbreviation;
    }

    public void setOrganizationAbbreviation(String organizationAbbreviation) {
        this.organizationAbbreviation = organizationAbbreviation;
    }

    @Column(name = "ORGN_NAME")
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @OneToMany(mappedBy = "organization", targetEntity = Address.class)
    public Set<Address> getOrganizationAddresses() {
        return addresses;
    }

    public void setOrganizationAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", organizationAbbreviation='" + organizationAbbreviation + '\'' +
                ", organizationName='" + organizationName + '\'' +
/*
                ", addresses=" + addresses +
*/
                '}';
    }
}