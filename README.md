# ⚙️ **Motor Parts Inventory System**

### *A Java Console Application for Streamlined Auto Parts Tracking & Stock Management*

---

## 📌 **1. Project Title**

**Motor Parts Inventory System**  
*A single-file Java console application built for efficient parts tracking, stock auditing, and automated reporting.*

---

## 💡 **2. Description / Overview**

The **Motor Parts Inventory System** is an interactive Java console application designed to help auto shops, parts retailers, and small warehouses manage their spare parts inventory with accuracy and ease. Using lightweight file-based persistence (`.dat` pipe-delimited storage), the system tracks stock movements, flags low-inventory items, and generates clean summary reports.

The system allows you to:

* Add, view, search, edit, and delete motor parts
* Process sales/stock issuances, restocks, and physical count adjustments
* Automatically detect items at or below minimum reorder thresholds
* Generate category breakdowns, top-value stock lists, and exportable `.txt` reports
* Maintain a timestamped audit trail of every inventory transaction

---

## 🌟 **Key Features at a Glance**

✔️ **Full Inventory CRUD**  
Create, inspect, edit, and permanently remove parts with duplicate-ID protection and selective field editing.

✔️ **Multi-Criteria Search & Sorting**  
Filter parts by ID, name, category, or supplier, and sort inventory by ID, Name, Category, Quantity (low to high), or Total Value (high to low).

✔️ **Stock Movement & Audit Logging**  
Every addition, sale (`SOLD`), replenishment (`RESTOCKED`), physical count adjustment (`ADJUSTED`), edit (`EDITED`), or deletion (`DELETED`) is automatically timestamped and recorded in `history.dat`.

✔️ **Smart Low-Stock Alerts**  
Displays startup notifications when parts hit or drop below their minimum stock level and calculates suggested reorder quantities (targeting $2\times$ minimum stock).

✔️ **Analytics & Report Export**  
View total inventory valuation, unit counts, category summaries, top 3 highest-value items, and export a formatted report to `report.txt`.

✔️ **Crash-Resistant Console Input**  
A dedicated `Input` helper validates integers, decimals, confirmations, and text strings—sanitizing pipe (`|`) delimiters to prevent file corruption.

---

## 🧬 **3. OOP & Java Concepts Applied**

* **Encapsulation:** `MotorPart` and `Transaction` keep fields `private` (with `final` identifiers on immutable transaction records and part IDs) and expose controlled getters, setters, and domain methods (`getTotalValue()`, `isLowStock()`).
* **Separation of Concerns:** The application is cleanly divided into four cohesive components within `MotorPartsInventory.java`:
  * `MotorPart` — Domain model & CSV/pipe serialization
  * `Transaction` — Audit trail model with `Type` enum (`ADDED`, `SOLD`, `RESTOCKED`, `ADJUSTED`, `EDITED`, `DELETED`)
  * `InventoryManager` — Core business logic, stream queries, reporting, and file I/O
  * `Input` & `MotorPartsInventory` — Input validation and console UI presentation
* **Java Streams & Collections:** Uses `LinkedHashMap` for insertion-ordered lookups, `TreeMap` / `TreeSet` for sorted category aggregations, and Java Streams for searching, filtering, sorting, and valuation calculations.
* **File I/O Persistence (`java.nio.file`):** Automatically loads and saves state to `parts.dat` and `history.dat` in UTF-8 without needing an external database.

---

## 🗂️ **4. Program Structure**

```
MotorPartsInventory/
├─ MotorPartsInventory.java   
├─ parts.dat                  
├─ history.dat                
├─ report.txt                 
└─ README.md
```

### 🧩 **Class Relationship Overview**

```
MotorPartsInventory (Main UI Controller)
   ├─ uses → Input (validated console scanner)
   └─ delegates to → InventoryManager
                        ├─ manages → Map<String, MotorPart> (persisted to parts.dat)
                        └─ manages → List<Transaction>     (persisted to history.dat)
```

---

## ▶️ **5. How to Run the Program**

### 🧰 **Prerequisites**

* **Java Development Kit (JDK) 11 or higher** (uses `String.isBlank()` and `String.repeat()`).

### 🚀 **Steps to Run**

1. Save the source code as `MotorPartsInventory.java`.
2. Open a terminal in the folder containing the file.
3. Compile the program:
   ```bash
   javac MotorPartsInventory.java
   ```
4. Run the application:
   ```bash
   java MotorPartsInventory
   ```
5. Use the numbered menu (`0–11`) to manage parts, process stock movements, and view reports. Data is saved automatically after changes and upon selecting `0. Save & exit`.

---

## 🖥️ **6. Sample Console Output**

*(User inputs shown after prompts)*

```text
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
```

📦 **Selling Stock Example**

```text
Enter choice: 5

- Sell / Issue Stock -
Part ID (blank to cancel): BRK-101
 Ceramic Brake Pads - available: 25
Quantity to sell: 16
Note / customer / job (optional): Job #402 - Toyota Vios
 Sold 16. Total: $29600.00. Remaining: 9
 Warning: stock is now at/below the minimum level (10).
```

📊 **Transaction History Example**

```text
TIME                PART     TYPE     CHANGE NOTE
--------------------------------------------------------------------------------
2026-11-17 12:35:10 BRK-101  SOLD        -16 Job #402 - Toyota Vios
2026-11-17 12:34:02 BRK-101  ADDED       +25 New part: Ceramic Brake Pads
```

---

## 👥 **7. Authors & Acknowledgements**

* **Author:** *(Add your name / group members and GitHub profile links here)*
* **Acknowledgements:** Built as an Object-Oriented Programming final project utilizing Java Collections, Streams, and `java.nio.file` persistence.

---

## 🚀 **8. Future Enhancements**

* 🗃️ **Database Integration:** Migrate from `.dat` flat files to SQLite or MySQL via JDBC.
* 🔐 **Role-Based Access Control:** Add user logins separating cashiers/staff from inventory managers.
* 🧾 **Receipt & Purchase Order Generation:** Generate printable customer receipts and supplier POs.
* 📊 **CSV / Excel Export:** Add direct `.csv` or `.xlsx` export alongside `report.txt`.
* 🖥️ **Graphical User Interface (GUI):** Build a JavaFX or Swing dashboard with real-time stock charts.
