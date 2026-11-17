import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryManager {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Map<String, MotorPart> parts = new LinkedHashMap<>();
    private final List<Transaction> history = new ArrayList<>();
    private final Path partsFile;
    private final Path historyFile;

    public InventoryManager(String partsFile, String historyFile) {
        this.partsFile = Paths.get(partsFile);
        this.historyFile = Paths.get(historyFile);
    }

    private static String key(String id) { return id.trim().toUpperCase(); }

    // ---------- core operations ----------
    public boolean exists(String id) { return parts.containsKey(key(id)); }

    public MotorPart find(String id) { return parts.get(key(id)); }

    public Collection<MotorPart> all() { return parts.values(); }

    public List<Transaction> history() { return history; }

    public void addPart(MotorPart p) {
        parts.put(key(p.getPartId()), p);
        log(p.getPartId(), Transaction.Type.ADDED, p.getQuantity(), "New part: " + p.getName());
        save();
    }

    public void sell(MotorPart p, int qty, String note) {
        p.setQuantity(p.getQuantity() - qty);
        log(p.getPartId(), Transaction.Type.SOLD, -qty, note);
        save();
    }

    public void restock(MotorPart p, int qty, String note) {
        p.setQuantity(p.getQuantity() + qty);
        log(p.getPartId(), Transaction.Type.RESTOCKED, qty, note);
        save();
    }

    public void adjust(MotorPart p, int newQty, String note) {
        int diff = newQty - p.getQuantity();
        p.setQuantity(newQty);
        log(p.getPartId(), Transaction.Type.ADJUSTED, diff, note);
        save();
    }

    public void edited(MotorPart p, String note) {
        log(p.getPartId(), Transaction.Type.EDITED, 0, note);
        save();
    }

    public void delete(MotorPart p) {
        parts.remove(key(p.getPartId()));
        log(p.getPartId(), Transaction.Type.DELETED, -p.getQuantity(), "Removed: " + p.getName());
        save();
    }

    // ---------- queries ----------
    public List<MotorPart> search(String term) {
        String t = term.toLowerCase();
        return parts.values().stream()
                .filter(p -> p.getPartId().toLowerCase().contains(t)
                        || p.getName().toLowerCase().contains(t)
                        || p.getCategory().toLowerCase().contains(t)
                        || p.getSupplier().toLowerCase().contains(t))
                .collect(Collectors.toList());
    }

    public List<MotorPart> lowStock() {
        return parts.values().stream().filter(MotorPart::isLowStock).collect(Collectors.toList());
    }

    public double totalValue() {
        return parts.values().stream().mapToDouble(MotorPart::getTotalValue).sum();
    }

    public int totalUnits() {
        return parts.values().stream().mapToInt(MotorPart::getQuantity).sum();
    }

    public Map<String, double[]> byCategory() { // [0]=units, [1]=value, [2]=distinct parts
        Map<String, double[]> m = new TreeMap<>();
        for (MotorPart p : parts.values()) {
            double[] v = m.computeIfAbsent(p.getCategory(), k -> new double[3]);
            v[0] += p.getQuantity();
            v[1] += p.getTotalValue();
            v[2] += 1;
        }
        return m;
    }

    public Set<String> categories() {
        return parts.values().stream().map(MotorPart::getCategory)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    // ---------- persistence ----------
    private void log(String id, Transaction.Type type, int change, String note) {
        history.add(new Transaction(LocalDateTime.now().format(FMT), id, type, change, note));
    }

    public void save() {
        try {
            Files.write(partsFile,
                    parts.values().stream().map(MotorPart::toCsv).collect(Collectors.toList()),
                    StandardCharsets.UTF_8);
            Files.write(historyFile,
                    history.stream().map(Transaction::toCsv).collect(Collectors.toList()),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("  [!] Could not save data: " + e.getMessage());
        }
    }

    public void load() {
        try {
            if (Files.exists(partsFile)) {
                for (String line : Files.readAllLines(partsFile, StandardCharsets.UTF_8)) {
                    if (line.isBlank()) continue;
                    MotorPart p = MotorPart.fromCsv(line);
                    parts.put(key(p.getPartId()), p);
                }
            }
            if (Files.exists(historyFile)) {
                for (String line : Files.readAllLines(historyFile, StandardCharsets.UTF_8)) {
                    if (line.isBlank()) continue;
                    history.add(Transaction.fromCsv(line));
                }
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("  [!] Problem loading saved data: " + e.getMessage());
        }
    }

    public void exportReport(String fileName) throws IOException {
        List<String> out = new ArrayList<>();
        out.add("MOTOR PARTS INVENTORY REPORT - " + LocalDateTime.now().format(FMT));
        out.add("=".repeat(60));
        out.add(String.format("Distinct parts : %d", parts.size()));
        out.add(String.format("Total units    : %d", totalUnits()));
        out.add(String.format("Total value    : $%,.2f", totalValue()));
        out.add(String.format("Low-stock items: %d", lowStock().size()));
        out.add("");
        out.add(String.format("%-8s %-24s %-14s %5s %5s %10s", "ID", "NAME", "CATEGORY", "QTY", "MIN", "PRICE"));
        for (MotorPart p : parts.values()) {
            out.add(String.format("%-8s %-24s %-14s %5d %5d %10.2f%s", p.getPartId(),
                    cut(p.getName(), 24), cut(p.getCategory(), 14), p.getQuantity(),
                    p.getMinStock(), p.getPrice(), p.isLowStock() ? "  <-- LOW" : ""));
        }
        Files.write(Paths.get(fileName), out, StandardCharsets.UTF_8);
    }

    public static String cut(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
