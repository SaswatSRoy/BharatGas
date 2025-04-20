package GasBooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
// —and any other individual AWT classes you actually need—
import java.util.List;


public class Main {
    private JFrame frame;
    private final JTextField nameField;
    private final JTextField streetField;
    private final JTextField areaField;
    private final JTextField pincodeField;
    private final JTextField mobileField;
    private final JTextField cylindersField;
    private final JTextField lastDateField;
    private final JTextField bookingDateField;
    private final JTextField delDateField;
    private final JTextArea reportArea;
    private final JComboBox<String> reportChoiceComboBox;

    public Main() {
        frame = new JFrame("Bharat Gas Agency");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JLabel titleLabel = new JLabel("Welcome to Bharat Gas Agency");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        // Input Fields
        panel.add(createInputField("Customer Name: ", nameField = new JTextField()));
        panel.add(createInputField("Street Address: ", streetField = new JTextField()));
        panel.add(createInputField("Area: ", areaField = new JTextField()));
        panel.add(createInputField("Pincode: ", pincodeField = new JTextField()));
        panel.add(createInputField("Mobile: ", mobileField = new JTextField()));
        panel.add(createInputField("Number of Cylinders (1/2): ", cylindersField = new JTextField()));
        panel.add(createInputField("Last Booking Date (dd/MM/yyyy): ", lastDateField = new JTextField()));
        panel.add(createInputField("Booking Date (dd/MM/yyyy): ", bookingDateField = new JTextField()));
        panel.add(createInputField("Delivery Date (dd/MM/yyyy): ", delDateField = new JTextField()));

        // Buttons and Combo Box
        JButton submitButton = new JButton("Submit Booking");
        submitButton.addActionListener(e -> handleSubmitBooking());
        panel.add(submitButton);

        String[] reportChoices = {"Cylinder Count by Month", "Late Delivery Report", "Single Cylinder Holders", "Double Cylinder Holders", "Delivery Person Details", "Overall Report", "Generate Invoices"};
        reportChoiceComboBox = new JComboBox<>(reportChoices);
        panel.add(reportChoiceComboBox);

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> handleGenerateReport());
        panel.add(generateReportButton);

        // Text area to show reports
        reportArea = new JTextArea(10, 40);
        reportArea.setEditable(false);
        panel.add(new JScrollPane(reportArea));

        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel createInputField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        textField.setPreferredSize(new Dimension(200, 25));
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private void handleSubmitBooking() {
        try {
            String name = nameField.getText();
            Delivery newDelivery = getDelivery(name);
            newDelivery.setDelPersonName();
            newDelivery.amountCalc();
            newDelivery.verifyOtp();

            Delivery[] deliveries = new Delivery[]{newDelivery};

            // Save the new delivery to file
            FileHandler.saveDeliveries(deliveries);

            JOptionPane.showMessageDialog(frame, "Booking Successful!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }

    private Delivery getDelivery(String name) throws ParseException {
        String street = streetField.getText();
        String area = areaField.getText();
        String pincode = pincodeField.getText();
        String mobile = mobileField.getText();
        int numCylinders = Integer.parseInt(cylindersField.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date lastDate = sdf.parse(lastDateField.getText());  // Last booking date
        Date bookingDate = sdf.parse(bookingDateField.getText());  // Booking date
        Date deliveryDate = sdf.parse(delDateField.getText());  // Delivery date

        // Create new Delivery object with the correct parameters
        Delivery newDelivery = new Delivery(name, street, area, pincode, mobile, numCylinders, bookingDate, deliveryDate);
        newDelivery.validate();
        return newDelivery;
    }


    private void handleGenerateReport() {
        try {
            String selectedReport = (String) reportChoiceComboBox.getSelectedItem();
            StringBuilder reportContent = new StringBuilder();

            // Load deliveries from file
            List<Delivery> loadedDeliveries = FileHandler.loadDeliveries();

            if (loadedDeliveries.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No data available for report.");
                return;
            }

            switch (selectedReport) {
                case "Cylinder Count by Month":
                    for (Delivery delivery : loadedDeliveries) {
                        reportContent.append("In the month of ").append(delivery.date_2.getMonth() + 1).append(": ").append(delivery.numOfCylinders).append(" cylinders delivered.\n");
                    }
                    break;
                case "Late Delivery Report":
                    for (Delivery delivery : loadedDeliveries) {
                        if (delivery.status.equals("D") && delivery.amount == 833.75) {  // Late delivery condition
                            reportContent.append("Late delivery for ").append(delivery.name).append("\n");
                        }
                    }
                    break;
                case "Single Cylinder Holders":
                    for (Delivery delivery : loadedDeliveries) {
                        if (delivery.numOfCylinders == 1) {
                            reportContent.append("Single Cylinder Holder: ").append(delivery.name).append("\n");
                        }
                    }
                    break;
                case "Double Cylinder Holders":
                    for (Delivery delivery : loadedDeliveries) {
                        if (delivery.numOfCylinders == 2) {
                            reportContent.append("Double Cylinder Holder: ").append(delivery.name).append("\n");
                        }
                    }
                    break;
                // More cases for other reports...
                case null:
                    break;
                default:
                    reportContent.append("No report selected.");
            }

            reportArea.setText(reportContent.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error generating report: " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        new Main();
    }
}
