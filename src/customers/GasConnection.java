package customers;

import java.util.Date;

public class GasConnection {
    public String name;
    public String street;
    public String area;
    public String pincode;
    public String mobile;
    public int numOfCylinders;
    protected Date lastDate;

    public GasConnection(String name, String street, String area, String pincode, String mobile, int numOfCylinders, Date lastDate) {
        this.name = name;
        this.street = street;
        this.area = area;
        this.pincode = pincode;
        this.mobile = mobile;
        this.numOfCylinders = numOfCylinders;
        this.lastDate = lastDate;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getArea() {
        return area;
    }

    public String getPincode() {
        return pincode;
    }

    public String getMobile() {
        return mobile;
    }

    public int getNumOfCylinders() {
        return numOfCylinders;
    }

    public Date getLastDate() {
        return lastDate;
    }
}
