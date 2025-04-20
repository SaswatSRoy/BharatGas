package GasBooking;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class FileHandler {

    // Save a list of deliveries to a file
    public static void saveDeliveries(Delivery[] deliveries) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("deliveries.txt", true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Delivery delivery : deliveries) {
                writer.write(delivery.name + "," +
                        delivery.street + "," +
                        delivery.area + "," +
                        delivery.pincode + "," +
                        delivery.mobile + "," +
                        delivery.numOfCylinders + "," +
                        sdf.format(delivery.date_1) + "," +  // Saving date_1 (booking date)
                        sdf.format(delivery.date_2) + "," +  // Saving date_2 (delivery date)
                        delivery.status + "," +
                        delivery.amount + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving deliveries: " + e.getMessage());
        }
    }

    // Load deliveries from the file
    public static List<Delivery> loadDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader("deliveries.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 10) {
                    String name = data[0];
                    String street = data[1];
                    String area = data[2];
                    String pincode = data[3];
                    String mobile = data[4];
                    int numOfCylinders = Integer.parseInt(data[5]);
                    Date date_1 = sdf.parse(data[6]);  // Parsing date_1 (booking date)
                    Date date_2 = sdf.parse(data[7]);  // Parsing date_2 (delivery date)
                    String status = data[8];
                    double amount = Double.parseDouble(data[9]);

                    Delivery delivery = new Delivery(name, street, area, pincode, mobile, numOfCylinders, date_1, date_2);
                    delivery.status = status;
                    delivery.amount = amount;
                    deliveries.add(delivery);
                }
            }
        } catch (IOException | java.text.ParseException e) {
            System.out.println("Error while loading deliveries: " + e.getMessage());
        }
        return deliveries;
    }
}
