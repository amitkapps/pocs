package hbm;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 14, 2010
 * Time: 12:39:45 AM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class Auditable {

    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;
//    private Long version;

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "LAST_UPDATE_USER")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

/*
    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
*/
}
