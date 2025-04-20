# Bharat Gas Agency Management System

A comprehensive Java-based gas booking and management system that integrates a Swing GUI, file-based persistence, and robust reporting capabilities.

---

## Table of Contents
1. [Features](#features)
2. [Class Structure](#class-structure)
3. [Database Schema](#database-schema)
4. [Implementation Details](#implementation-details)
5. [GUI Usage](#gui-usage)
6. [Menu Navigation](#menu-navigation)
7. [Data Validation Rules](#data-validation-rules)
8. [Financial Calculations](#financial-calculations)
9. [Error Handling](#error-handling)
10. [Future Enhancements](#future-enhancements)
11. [Contributing](#contributing)
12. [License](#license)

---

## Features

1. **Customer Management**
   - Capture and store customer details:
     - Name, Street, Area, Pincode, Mobile Number
     - Number of cylinders (1 or 2)
     - Last booking date (persisted)
2. **Booking & Delivery**
   - Unified `Delivery` class for both booking and delivery logic.
   - Automated validation:
     - Minimum gap between bookings (30 days for single, 50 days for double cylinders)
     - Standard delivery window of 7 days
   - Status tracking: **B** (Booked), **C** (Cancelled), **D** (Delivered), **P** (Pending)
   - OTP verification (default: 5678)
   - Amount calculation with automatic late-refund (₹41.25 after 7 days)
3. **Persistence**
   - File-based storage via `FileHandler`:
     - `saveCustomerData(...)` → `customers.txt`
     - `loadCustomerData()` reads back customer records
     - `saveDeliveries(...)` → `deliveries.txt`
     - `loadDeliveries()` reads back delivery records
4. **Reporting**
   - Cylinder count by month
   - Late delivery report
   - Single vs. double cylinder holder lists
   - Delivery person assignments
   - Overall status summary
   - Invoice listings
5. **GUI**
   - Swing-based form for data entry
   - Dropdown to select report type
   - Text area to display results

---

## Class Structure
```plaintext
gasPackage
├─ gasAgency (interface)
├─ Customer
├─ GasConnection
└─ Delivery  // extends GasConnection, replaces Booking class

utility
└─ FileHandler  // handles CSV-style file I/O for persistence

app
└─ Main  // Swing GUI entry point
```

---

## Database Schema (for future RDBMS integration)
```plaintext
Customers
  - customer_id   INT PK
  - name          VARCHAR
  - street        VARCHAR
  - area          VARCHAR
  - pincode       VARCHAR
  - mobile        VARCHAR
  - cylinder_count INT
  - last_booking  DATE

Deliveries
  - delivery_id    INT PK
  - customer_id    INT FK
  - booking_date   DATE
  - delivery_date  DATE
  - status         CHAR(1)
  - amount         DECIMAL
  - refund_amount  DECIMAL
  - otp_verified   BOOLEAN
```

---

## Implementation Details

- **gasAgency Interface**: Defines agency constants and a default `display()` method.
- **Customer**: Stores basic customer fields with getters.
- **GasConnection**: Inherits from `Customer`, adds `numOfCylinders` and `lastDate`.
- **Delivery**:
  - Constructor signature:
    ```java
    public Delivery(
      String name, String street, String area, String pincode,
      String mobile, int numOfCylinders,
      Date lastDate, SimpleDateFormat dateFormat,
      Date bookingDate, Date deliveryDate
    )
    ```
  - Methods:
    - `validateBooking()`
    - `setDelPersonName(String)`
    - `amountCalc()`
    - `verifyOtp(int)`
- **FileHandler**:
  - CSV format using `dd/MM/yyyy`
  - `saveCustomerData(Customer)` appends to `customers.txt`
  - `loadCustomerData()` parses into `GasConnection` objects
  - `saveDeliveries(Delivery[])` appends to `deliveries.txt`
  - `loadDeliveries()` parses into `Delivery` objects

---

## GUI Usage

1. **Compile**
   ```bash
   javac -d bin src/GasBooking/*.java src/customers/*.java src/utility/*.java
   ```

2. **Run**
   ```bash
   java -cp bin GasBooking.Main
   ```

3. **Workflow**
   - Fill in customer & booking fields
   - Click **Submit Booking** (triggers OTP input)
   - Select report from dropdown, then **Generate Report**
   - View output in the text area

---

## Menu Navigation
This GUI replaces the traditional console menu:
```plaintext
[Form fields]
[Submit Booking]
[Report Type ▼] → [Generate Report]
[Text area for output]
```

---

## Data Validation Rules

- **Single Cylinder**: 30-day minimum interval between bookings
- **Double Cylinder**: 50-day minimum interval
- **Delivery Window**: 7 days (late deliveries get refund)

---

## Financial Calculations

- **Base Amount**: ₹875.00 per cylinder booking
- **Late-Delivery Refund**: ₹41.25 if `deliveryDate - bookingDate > 7`

---

## Error Handling

- Invalid date formats are caught and reported via GUI dialogs.
- Missing or malformed inputs prompt user-friendly messages.
- File I/O exceptions are logged to console.

---

## Future Enhancements

- Migrate persistence to SQLite or another RDBMS
- Export reports as CSV/JSON
- Integrate SMS/email notifications for OTP and booking confirmations
- Provide a RESTful API for web/mobile clients

---

## Git Repository Initialization

If you're adding version control to an existing project, follow these steps:

1. **Open a terminal** and navigate to the root directory of your project:
   ```bash
   cd /path/to/your/project
   ```

2. **Initialize a new Git repository**:
   ```bash
   git init
   ```

3. **Add all existing files** to the index:
   ```bash
   git add .
   ```

4. **Make your first commit**:
   ```bash
   git commit -m "Initial commit: project import"
   ```

5. **(Optional)** If you have a remote repository (e.g., GitHub), add it:
   ```bash
   git remote add origin https://github.com/your-username/your-repo.git
   git push -u origin main
   ```

---

## Contributing

Contributions are welcome! Please fork the repo, create a feature branch, and submit a pull request. For substantial changes, open an issue first.

---

## License

This project is licensed under the **Bharat Gas Agency Open License**, permitting non-commercial use, modification with attribution, and redistribution under the same terms. The software is provided "as is" without warranty.

