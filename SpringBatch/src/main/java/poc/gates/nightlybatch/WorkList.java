package poc.gates.nightlybatch;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 11/25/14
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkList {
    private int id;
    private String workName;
    private String processedBy = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    @Override
    public String toString() {
        return "WorkList{" +
                "id=" + id +
                ", workName='" + workName + '\'' +
                ", processedBy='" + processedBy + '\'' +
                '}';
    }
}
