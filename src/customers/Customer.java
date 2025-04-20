package customers;

public class Customer {
    private String name;
    private String street;
    private String area;
    private String pincode;
    private String mobile;

    // Constructor
    public Customer(String name, String street, String area, String pincode, String mobile) {
        this.name = name;
        this.street = street;
        this.area = area;
        this.pincode = pincode;
        this.mobile = mobile;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Other getters and setters for the other fields if necessary
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
}
