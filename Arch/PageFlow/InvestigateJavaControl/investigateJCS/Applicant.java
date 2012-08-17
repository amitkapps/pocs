package investigateJCS;

public class Applicant implements java.io.Serializable
{
    static final long serialVersionUID = 1L;  

    public String taxID;
    public String firstName;
    public String lastName;
    public boolean currentlyBankrupt;
    public int availableCCCredit;
    public int creditScore;
    public String approvalLevel;
    public String message;

    public Applicant() {}
}

