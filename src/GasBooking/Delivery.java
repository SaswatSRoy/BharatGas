package GasBooking;

import java.util.Date;

public class Delivery {
    // Customer Information
    String name;
    String street;
    String area;
    String pincode;
    String mobile;
    int numOfCylinders;

    // Dates
    Date date_1;  // Booking Date
    Date date_2;  // Delivery Date

    // Additional fields
    String status;  // For delivery status
    double amount;  // For calculating amount

    // Constructor
    public Delivery(String name, String street, String area, String pincode, String mobile, int numOfCylinders, Date date_1, Date date_2) {
        this.name = name;
        this.street = street;
        this.area = area;
        this.pincode = pincode;
        this.mobile = mobile;
        this.numOfCylinders = numOfCylinders;
        this.date_1 = date_1;
        this.date_2 = date_2;  // Initialize date_2 (delivery date)
    }

    // Methods (example)
    public void validate() {
        // Validation logic here
    }

    public void setDelPersonName() {
        // Set delivery person name
    }

    public void amountCalc() {
        // Calculate the delivery amount
    }

    public void verifyOtp() {
        // OTP verification logic
    }
}
