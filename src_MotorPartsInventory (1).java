import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MotorPartsInventory {
    private static final InventoryManager inv = new InventoryManager("parts.dat", "history.dat");
    private static final Input in = new Input();

    public static void main(String[] args) {
        inv.load();
        banner();
        showStartupAlerts();

        boolean running = true;
        while (running) {
            menu();
            int choice = in.integer("Enter choice: ", 0, 11);
            System.out.println();
            switch (choice) {
                case 1:  addPart();          break;
                case 2:  viewInventory();    break;
                case 3:  searchParts();      break;
                case 4:  editPart();         break;
                case 5:  sellStock();        break;
                case 6:  restockPart();      break;
                case 7:  adjustStock();      break;
                case 8:  deletePart();       break;
                case 9:  lowStockReport();   break;
                case 10: summaryReport();    break;
                case 11: viewHistory();      break;
                case 0:
                    inv.save();
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;
            }
            if (running && choice != 0) in.pause();
        }
    }

    // ---------- display helpers ----------
    private static void banner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     MOTOR PARTS INVENTORY SYSTEM     ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private static void menu() {
        System.out.println("\n──────────────── MAIN MENU ────────────────");
        System.out.println(" 1. Add new part          7. Adjust stock (count)");
        System.out.println(" 2. View inventory        8. Delete part");
        System.out.println(" 3. Search parts          9. Low-stock alerts");
        System.out.println(" 4. Edit part details    10. Reports & summary");
        System.out.println(" 5. Sell / issue stock   11. Transaction history");
        System.out.println(" 6. Restock part          0. Save & exit");
        System.out.println("───────────────────────────────────────────");
    }

    private static void showStartupAlerts() {
        int n = inv.lowStock().size();
        if (n > 0) System.out.println("\n  ⚠  " + n + " part(s) are at or below minimum stock. See option 9.");
    }

    private static void printTable(Collection<MotorPart> list) {
        if (list.isEmpty()) { System.out.println("  (no parts to show)"); return; }
        String line = "-".repeat(104);
        System.out.println(line);
        System.out.printf("%-8s %-24s %-14s %-16s %5s %5s %10s %12s%n",
                "ID", "NAME", "CATEGORY", "SUPPLIER", "QTY", "MIN", "PRICE", "VALUE");
        System.out.println(line);
        for (MotorPart p : list) {
            System.out.printf("%-8s %-24s %-14s %-16s %5d %5d %10.2f %12.2f %s%n",
                    p.getPartId(), InventoryManager.cut(p.getName(), 24),
                    InventoryManager.cut(p.getCategory(), 14), InventoryManager.cut(p.getSupplier(), 16),
                    p.getQuantity(), p.getMinStock(), p.getPrice(), p.getTotalValue(),
                    p.isLowStock() ? "⚠ LOW" : "");
        }
        System.out.println(line);
        System.out.println(list.size() + " part(s) listed.");
    }

    /** Ask for a part ID until one that exists is entered (or blank to cancel). */
    private static MotorPart askExistingPart(String prompt) {
        while (true) {
            String id = in.text(prompt + " (blank to cancel): ", true);
            if (id.isEmpty()) return null;
            MotorPart p = inv.find(id);
            if (p != null) return p;
            System.out.println("  [!] Part ID not found.");
        }
    }

    // ---------- features ----------
    private static void addPart() {
        System.out.println("── Add New Part ──");
        String id;
        while (true) {
            id = in.text("Part ID: ", false);
            if (!inv.exists(id)) break;
            System.out.println("  [!] That Part ID already exists. Use Restock instead, or pick another ID.");
        }
        String name = in.text("Part name: ", false);

        Set<String> cats = inv.categories();
        if (!cats.isEmpty()) System.out.println("  Existing categories: " + String.join(", ", cats));
        String category = in.text("Category (e.g. Engine, Brakes, Electrical): ", false);
        String supplier = in.text("Supplier: ", false);
        int qty = in.integer("Initial quantity: ", 0, 1_000_000);
        int min = in.integer("Minimum stock level (reorder alert): ", 0, 1_000_000);
        double price = in.decimal("Unit price: PHP", 0.0);

        inv.addPart(new MotorPart(id.toUpperCase(), name, category, supplier, qty, min, price));
        System.out.println("\n  ✔ Part added successfully.");
    }

    private static void viewInventory() {
        System.out.println("── Inventory ──");
        System.out.println("Sort by: 1) ID  2) Name  3) Category  4) Quantity (low→high)  5) Value (high→low)");
        int s = in.integer("Choice: ", 1, 5);
        List<MotorPart> list = new ArrayList<>(inv.all());
        switch (s) {
            case 1: list.sort(Comparator.comparing(MotorPart::getPartId, String.CASE_INSENSITIVE_ORDER)); break;
            case 2: list.sort(Comparator.comparing(MotorPart::getName, String.CASE_INSENSITIVE_ORDER)); break;
            case 3: list.sort(Comparator.comparing(MotorPart::getCategory, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(MotorPart::getName)); break;
            case 4: list.sort(Comparator.comparingInt(MotorPart::getQuantity)); break;
            case 5: list.sort(Comparator.comparingDouble(MotorPart::getTotalValue).reversed()); break;
        }
        printTable(list);
    }

    private static void searchParts() {
        System.out.println("── Search ──");
        String term = in.text("Search (ID, name, category or supplier): ", false);
        printTable(inv.search(term));
    }

    private static void editPart() {
        System.out.println("── Edit Part ──");
        MotorPart p = askExistingPart("Part ID to edit");
        if (p == null) return;
        printTable(List.of(p));
        System.out.println("Leave a field blank to keep its current value.");

        List<String> changes = new ArrayList<>();
        String v = in.text("New name [" + p.getName() + "]: ", true);
        if (!v.isEmpty()) { changes.add("name " + p.getName() + "→" + v); p.setName(v); }

        v = in.text("New category [" + p.getCategory() + "]: ", true);
        if (!v.isEmpty()) { changes.add("category " + p.getCategory() + "→" + v); p.setCategory(v); }

        v = in.text("New supplier [" + p.getSupplier() + "]: ", true);
        if (!v.isEmpty()) { changes.add("supplier " + p.getSupplier() + "→" + v); p.setSupplier(v); }

        v = in.text("New price [" + p.getPrice() + "]: ", true);
        if (!v.isEmpty()) {
            try {
                double d = Double.parseDouble(v);
                if (d < 0) throw new NumberFormatException();
                changes.add("price " + p.getPrice() + "→" + d); p.setPrice(d);
            } catch (NumberFormatException e) { System.out.println("  [!] Invalid price – kept old value."); }
        }

        v = in.text("New minimum stock [" + p.getMinStock() + "]: ", true);
        if (!v.isEmpty()) {
            try {
                int m = Integer.parseInt(v);
                if (m < 0) throw new NumberFormatException();
                changes.add("min " + p.getMinStock() + "→" + m); p.setMinStock(m);
            } catch (NumberFormatException e) { System.out.println("  [!] Invalid number – kept old value."); }
        }

        if (changes.isEmpty()) { System.out.println("\n  No changes made."); return; }
        inv.edited(p, String.join("; ", changes));
        System.out.println("\n  ✔ Part updated.");
    }

    private static void sellStock() {
        System.out.println("── Sell / Issue Stock ──");
        MotorPart p = askExistingPart("Part ID");
        if (p == null) return;
        if (p.getQuantity() == 0) { System.out.println("  [!] Out of stock."); return; }
        System.out.println("  " + p.getName() + " – available: " + p.getQuantity());
        int qty = in.integer("Quantity to sell: ", 1, p.getQuantity());
        String note = in.text("Note / customer / job (optional): ", true);
        inv.sell(p, qty, note.isEmpty() ? "Sale" : note);
        System.out.printf("  ✔ Sold %d. Total: $%.2f. Remaining: %d%n", qty, qty * p.getPrice(), p.getQuantity());
        if (p.isLowStock()) System.out.println("  ⚠ Warning: stock is now at/below the minimum level (" + p.getMinStock() + ").");
    }

    private static void restockPart() {
        System.out.println("── Restock ──");
        MotorPart p = askExistingPart("Part ID");
        if (p == null) return;
        System.out.println("  " + p.getName() + " – current: " + p.getQuantity());
        int qty = in.integer("Quantity received: ", 1, 1_000_000);
        String note = in.text("Note / invoice no. (optional): ", true);
        inv.restock(p, qty, note.isEmpty() ? "Restock" : note);
        System.out.println("  ✔ Restocked. New quantity: " + p.getQuantity());
    }

    private static void adjustStock() {
        System.out.println("── Adjust Stock (physical count) ──");
        MotorPart p = askExistingPart("Part ID");
        if (p == null) return;
        System.out.println("  " + p.getName() + " – system quantity: " + p.getQuantity());
        int newQty = in.integer("Actual counted quantity: ", 0, 1_000_000);
        if (newQty == p.getQuantity()) { System.out.println("  Counts match – nothing to change."); return; }
        String reason = in.text("Reason (damaged, miscount, etc.): ", false);
        inv.adjust(p, newQty, reason);
        System.out.println("  ✔ Stock adjusted to " + newQty + ".");
    }

    private static void deletePart() {
        System.out.println("── Delete Part ──");
        MotorPart p = askExistingPart("Part ID to delete");
        if (p == null) return;
        printTable(List.of(p));
        if (in.confirm("Permanently delete this part?")) {
            inv.delete(p);
            System.out.println("  ✔ Part deleted.");
        } else {
            System.out.println("  Cancelled.");
        }
    }

    private static void lowStockReport() {
        System.out.println("── Low-Stock Alerts ──");
        List<MotorPart> low = inv.lowStock();
        if (low.isEmpty()) { System.out.println("  ✔ All parts are above their minimum stock level."); return; }
        low.sort(Comparator.comparingInt(MotorPart::getQuantity));
        printTable(low);
        System.out.println("Suggested reorder quantities (to reach 2× minimum):");
        for (MotorPart p : low) {
            int need = Math.max(1, p.getMinStock() * 2 - p.getQuantity());
            System.out.printf("  %-8s %-24s order ~%d  (supplier: %s)%n",
                    p.getPartId(), InventoryManager.cut(p.getName(), 24), need, p.getSupplier());
        }
    }

    private static void summaryReport() {
        System.out.println("── Reports & Summary ──");
        System.out.printf("Distinct parts  : %d%n", inv.all().size());
        System.out.printf("Total units     : %d%n", inv.totalUnits());
        System.out.printf("Inventory value : $%,.2f%n", inv.totalValue());
        System.out.printf("Low-stock items : %d%n", inv.lowStock().size());

        if (!inv.all().isEmpty()) {
            System.out.println("\nBy category:");
            System.out.printf("  %-16s %8s %8s %14s%n", "CATEGORY", "PARTS", "UNITS", "VALUE");
            for (Map.Entry<String, double[]> e : inv.byCategory().entrySet()) {
                double[] v = e.getValue();
                System.out.printf("  %-16s %8.0f %8.0f %14s%n", InventoryManager.cut(e.getKey(), 16),
                        v[2], v[0], String.format("$%,.2f", v[1]));
            }
            System.out.println("\nTop 3 most valuable stock lines:");
            inv.all().stream()
                    .sorted(Comparator.comparingDouble(MotorPart::getTotalValue).reversed())
                    .limit(3)
                    .forEach(p -> System.out.printf("  %-8s %-24s $%,.2f%n",
                            p.getPartId(), InventoryManager.cut(p.getName(), 24), p.getTotalValue()));
        }

        if (in.confirm("\nExport full report to report.txt?")) {
            try {
                inv.exportReport("report.txt");
                System.out.println("  ✔ Report saved to report.txt");
            } catch (IOException e) {
                System.out.println("  [!] Could not write report: " + e.getMessage());
            }
        }
    }

    private static void viewHistory() {
        System.out.println("── Transaction History ──");
        System.out.println("1) Show last 20   2) Filter by part ID   3) Show all");
        int c = in.integer("Choice: ", 1, 3);
        List<Transaction> list = new ArrayList<>(inv.history());
        if (c == 2) {
            String id = in.text("Part ID: ", false);
            list = list.stream().filter(t -> t.getPartId().equalsIgnoreCase(id)).collect(Collectors.toList());
        } else if (c == 1 && list.size() > 20) {
            list = list.subList(list.size() - 20, list.size());
        }
        if (list.isEmpty()) { System.out.println("  (no transactions)"); return; }
        System.out.printf("%-19s %-8s %-10s %6s  %s%n", "TIME", "PART", "TYPE", "CHANGE", "NOTE");
        System.out.println("-".repeat(80));
        for (int i = list.size() - 1; i >= 0; i--) System.out.println(list.get(i)); // newest first
    }
}
