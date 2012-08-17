package poc.flexjson.model;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 10, 2010
 * Time: 12:34:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Phone {
    String countryCode;
    Long number;

    public Phone() {
    }

    public Phone(String countryCode, Long number) {
        this.countryCode = countryCode;
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getNumber() {
        return number;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Phone");
        sb.append("{countryCode='").append(countryCode).append('\'');
        sb.append(", number=").append(number);
        sb.append('}');
        return sb.toString();
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}