package hbm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MZ_ORGANIZATION")
public class Organization implements Serializable{

    private Long id;
    private String organizationAbbreviation;
    private String organizationName;
    private String status;
    private Set<Address> addresses = new HashSet<Address>();

    public Organization() {
    }

    @Id
    @Column(name = "ORGANIZATION_ID")
    public Long getId() {
        return id;
    }

    public Organization setId(Long id) {
        this.id = id;
        return this;
    }

    @Column(name = "ABBREVIATION")
    public String getOrganizationAbbreviation() {
        return organizationAbbreviation;
    }

    public void setOrganizationAbbreviation(String organizationAbbreviation) {
        this.organizationAbbreviation = organizationAbbreviation;
    }

    @Column(name = "ORGANIZATION_NAME")
    public String getOrganizationName() {
        return organizationName;
    }

    public Organization setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "organization")
    public Set<Address> getOrganizationAddresses() {
        return addresses;
    }

    public void setOrganizationAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Org{" +
                "id=" + id +
                ", abbr='" + organizationAbbreviation + '\'' +
                ", name='" + organizationName + '\'' +
/*
                ", addresses=" + addresses +
*/
                '}';
    }
}