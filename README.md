⚙️ MOTOR PARTS INVENTORY SYSTEM

A Java Console Application for Efficient Auto Parts Tracking and Stock Management

---

📌 1. Project Title

Motor Parts Inventory System

A Java-based console application designed to help automotive shops and small businesses manage motor parts, monitor stock levels, record inventory transactions, and generate inventory reports efficiently.

---

💡 2. Project Description

The Motor Parts Inventory System is an interactive Java console application developed to simplify the management of motor parts and automotive supplies. It provides an organized way to store, update, search, and monitor inventory records using a file-based storage system.

The application allows users to manage motor parts information, process sales and restocking transactions, adjust inventory quantities, and monitor items that are running low. It also provides inventory summaries, transaction history, and report generation to help users maintain accurate stock records.

The system uses Java Object-Oriented Programming (OOP) concepts and file handling to provide a simple, reliable, and user-friendly inventory management solution.

---

🎯 3. Problem Being Addressed

Many small automotive shops and motor parts businesses still manage their inventory manually using notebooks or spreadsheets. This process can be time-consuming and may result in inaccurate stock records, misplaced information, and difficulties monitoring available supplies.

The project addresses the following problems:

- Difficulty tracking available motor parts and their quantities.
- Errors in recording sales, restocking, and inventory adjustments.
- Lack of automatic notifications for low-stock items.
- Difficulty searching for specific motor parts and supplier information.
- Time-consuming preparation of inventory summaries and reports.
- Lack of organized transaction records for monitoring inventory activities.

Through this system, users can manage their inventory more efficiently, reduce manual recording errors, and maintain organized inventory information.

---

🎯 4. Project Objectives

The main objective of the project is to develop a Java-based Motor Parts Inventory System that improves the tracking and management of automotive parts.

Specifically, the project aims to:

1. Develop a system that allows users to add, view, search, edit, and delete motor parts records.
2. Provide accurate monitoring of available stock quantities.
3. Implement sales, restocking, and stock adjustment transactions.
4. Automatically identify motor parts that reach or fall below their minimum stock levels.
5. Generate inventory summaries and reports for easier monitoring.
6. Maintain a transaction history to track inventory changes.
7. Apply Object-Oriented Programming concepts in developing a functional Java application.
8. Store inventory records using file handling so that information remains available after closing the program.

---

👥 5. Target Users

The system is intended for the following users:

- Automotive Shop Owners – Can monitor available motor parts and check the overall inventory.
- Inventory Managers – Can update stock quantities, manage parts information, and monitor low-stock products.
- Store Employees – Can record sales, restocking activities, and inventory adjustments.
- Small Motor Parts Businesses – Can use the application to organize their inventory without requiring an expensive inventory management system.

---

🌟 6. Main Features

A. Inventory Management

- Add new motor parts.
- View all available inventory records.
- Edit existing motor parts information.
- Delete unwanted or discontinued parts.
- Prevent duplicate part IDs.

B. Search and Sorting

- Search parts using their ID, name, category, or supplier.
- Sort inventory by ID, name, category, quantity, and total value.

C. Stock Management

- Record sold or issued motor parts.
- Restock existing motor parts.
- Adjust stock quantities based on physical inventory counts.
- Automatically calculate remaining stock quantities.

D. Low-Stock Alerts

- Identify items that reach or fall below the minimum stock level.
- Display low-stock notifications.
- Calculate suggested reorder quantities.

E. Reports and Analytics

- Calculate total inventory value.
- Display total units in stock.
- Generate category summaries.
- Identify the top three highest-value inventory items.
- Export inventory reports into a text file.

F. Transaction History

- Record every inventory transaction.
- Store transaction dates and times.
- Track added, sold, restocked, adjusted, edited, and deleted items.

G. File-Based Data Storage

- Save inventory records in "parts.dat".
- Save transaction records in "history.dat".
- Automatically load saved information when the application starts.

---

🛠️ 7. Technologies Used

The following technologies and tools are used in developing the project:

- Programming Language: Java
- Development Environment: Any Java-compatible IDE or text editor.
- Java Development Kit: JDK 11 or higher.
- Data Storage: File-based storage using ".dat" files.
- File Handling: Java NIO ("java.nio.file").
- Collections Framework: LinkedHashMap, TreeMap, TreeSet, and List.
- Java Streams: Used for searching, filtering, sorting, and calculating inventory data.
- Documentation: README.md
- Version Control: Git and GitHub (if used by the team).

---

👨‍💻 8. Team Members and Roles

Team Member| Assigned Role
Jessie Boy Garcia| Team Leader
Mark Louie Dimaano| Documentation
Ser James Abante| Lead Programmer
KlarkLevin John Dollete| Assistant Programmer

📋 Responsibilities

1. Jessie Boy Garcia – Team Leader

- Leads and coordinates the development team.
- Assigns tasks and monitors project progress.
- Ensures that project requirements are completed.

2. Mark Louie Dimaano – Documentation

- Prepares the project documentation and README file.
- Organizes project descriptions, objectives, and technical information.
- Documents the system's features and instructions.

3. Ser James Abante – Lead Programmer

- Leads the programming and development of the application.
- Implements the main system functions and program structure.
- Ensures that the system operates properly.

4. KlarkLevin John Dollete – Assistant Programmer

- Assists in writing and improving the Java code.
- Helps identify and fix programming errors.
- Supports the testing and improvement of system functionality.

---

🧬 9. OOP Concepts Demonstrated

The project demonstrates important Object-Oriented Programming concepts in Java.

A. Encapsulation

The "MotorPart" and "Transaction" classes use private fields to protect their data. Getters, setters, and methods provide controlled access to information.

B. Abstraction

The system separates inventory operations and data management from the main user interface. The "InventoryManager" class handles the main inventory operations, while the main class manages user interaction.

C. Separation of Concerns

The program is organized into different classes, each with its own responsibility:

- "MotorPart" – Stores motor parts information.
- "Transaction" – Stores inventory transaction records.
- "InventoryManager" – Handles inventory operations and file management.
- "Input" – Handles user input validation.
- "MotorPartsInventory" – Manages the main console interface.

D. Polymorphism and Other Java Concepts

The application also uses Java features such as enums, collections, streams, and methods to organize and process inventory information. These support the system's functionality and maintainability.

---

▶️ 10. Instructions for Running the Program

🖥️ System Requirements

🗂️ **Program Structure**

```text
MotorPartsInventory/
├─ MotorPartsInventory.java   
├─ parts.dat                  
├─ history.dat                
├─ report.txt                 
└─ README.md

---

📸 11. Sample Screenshots

The following screenshots should be included to demonstrate the actual appearance and functionality of the application.

==========================================
   || MOTOR PARTS INVENTORY SYSTEM ||
==========================================

2 part(s) are at or below minimum stock. See option 9.

--- MAIN MENU ---
 1. Add new part          7. Adjust stock (count)
 2. View inventory        8. Delete part
 3. Search parts          9. Low-stock alerts
 4. Edit part details    10. Reports & summary
 5. Sell / issue stock   11. Transaction history
 6. Restock part          0. Save & exit
------------------------------------------
Enter choice: 1

- Add New Part -
Part ID: BRK-101
Part name: Ceramic Brake Pads
Category (e.g. Engine, Brakes, Electrical): Brakes
Supplier: Brembo PH
Initial quantity: 25
Minimum stock level (reorder alert): 10
Unit price: PHP 1850.00

 Part added successfully.

---

⚠️ 12. Known Limitations

Although the Motor Parts Inventory System provides essential inventory management functions, it has some limitations:

- The application currently operates through a command-line interface and does not have a graphical user interface.
- The system uses local ".dat" files instead of an online database.
- It does not currently support multiple users accessing the inventory simultaneously.
- There is no username and password authentication.
- The system requires users to operate the application through the terminal.
- Reports are generated in text format rather than directly producing Excel or PDF files.
- Data is stored locally, so users must maintain backups to prevent data loss.

---

🚀 13. Future Improvements

The following improvements may be implemented in future versions:

1. Graphical User Interface (GUI) – Develop a more user-friendly interface using Java Swing or JavaFX.
2. Database Integration – Use MySQL or SQLite to provide more advanced data storage and management.
3. User Authentication – Add login accounts and role-based access for administrators and employees.
4. Receipt Generation – Generate receipts for every recorded sale.
5. Advanced Reporting – Support exporting inventory reports to Excel and PDF formats.
6. Barcode Scanning – Add barcode support for faster identification of motor parts.
7. Cloud Backup – Allow users to back up inventory information online.
8. Sales Analytics – Provide more detailed sales summaries and inventory performance reports.

---

🏆 14. Project Contributors

This project was developed through the collaboration and contributions of the following team members:

- Jessie Boy Garcia – Team Leader
- Mark Louie Dimaano – Documentation
- Ser James Abante – Lead Programmer
- KlarkLevin John Dollete – Assistant Programmer

🙏 Acknowledgement

We would like to acknowledge the efforts and cooperation of all team members who contributed their time, knowledge, and skills to complete the Motor Parts Inventory System. This project demonstrates our understanding of Java programming, Object-Oriented Programming principles, file handling, and inventory management.

Through teamwork and collaboration, we were able to develop an application that provides a practical solution for managing motor parts inventory.

---

📌 Project Information

- Project: Motor Parts Inventory System
- Programming Language: Java
- Project Type: Object-Oriented Programming Final Project
