package poc.ejbs;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/14/13
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Unit {

    private int unitId;
    private Date receivedDate = new Date();

    public Date getReceivedDate() {
        return receivedDate;
    }

    public Unit setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
        return this;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public Unit setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
        return this;
    }

    private String bookingNumber;

    public int getUnitId() {
        return unitId;
    }

    public Unit setUnitId(int unitId) {
        this.unitId = unitId;
        return this;
    }
}
