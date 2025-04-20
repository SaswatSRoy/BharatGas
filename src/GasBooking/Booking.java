package GasBooking;

import customers.GasConnection;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;


public class Booking extends GasConnection {
    public double otp = 5678, amount = 875.0, refund = 0;
    public String delDate, status, delMobileNumber = "8249365552", bookingDate;
    public Date date_1, date_2;

    public Booking(String name, String street, String area, String pincode, String mobile, int numOfCylinders, Date lastDate, SimpleDateFormat dateFormat) {
        super(name, street, area, pincode, mobile, numOfCylinders, lastDate);
    }

    // Modified to accept dates from GUI
    public void setDates(String bookingDate, String deliveryDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            this.date_1 = dateFormat.parse(bookingDate);
            this.date_2 = dateFormat.parse(deliveryDate);

            long timeDiff = date_1.getTime() - date_2.getTime();
            long newTimeDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

            if (newTimeDiff > 7) {
                status = "P"; // Pending Status
            }

        } catch (Exception e) {
            System.out.println("Error in date parsing: " + e);
        }
    }

    // Validate the booking and date logic
    public void validateBooking() {
        long validatingDiffDate = date_1.getTime() - lastDate.getTime();
        long updatedValidatingDiffDate = TimeUnit.DAYS.convert(validatingDiffDate, TimeUnit.MILLISECONDS);

        if (numOfCylinders == 1) {
            if (updatedValidatingDiffDate < 30) {
                System.out.println("Booking cannot be done.");
                status = "C"; // Booking canceled
            } else {
                status = "B"; // Booking successful
                System.out.println("Booking Successful");
                lastDate = date_1;
            }
        } else if (numOfCylinders == 2) {
            if (updatedValidatingDiffDate < 50) {
                System.out.println("Booking cannot be done.");
                status = "C";
            } else {
                status = "B";
                System.out.println("Booking Successful");
                lastDate = date_1;
            }
        }
    }

    // Save the booking to a file
    public void saveBooking() {
        try (FileWriter writer = new FileWriter("bookings.txt", true)) {
            writer.write("Booking Status: " + status + "\n");
            writer.write("Booking Date: " + bookingDate + "\n");
            writer.write("Delivery Date: " + delDate + "\n");
            writer.write("Amount: " + amount + "\n");
            writer.write("Refund: " + refund + "\n");
            writer.write("-----------------------------\n");
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e);
        }
    }

    // Load bookings from file
    public static void loadBookings() {
        try (Scanner scanner = new Scanner(new File("bookings.txt"))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading bookings: " + e);
        }
    }
}
