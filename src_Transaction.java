public class Transaction {
    public enum Type { ADDED, SOLD, RESTOCKED, ADJUSTED, EDITED, DELETED }

    private final String timestamp;
    private final String partId;
    private final Type type;
    private final int qtyChange;
    private final String note;

    public Transaction(String timestamp, String partId, Type type, int qtyChange, String note) {
        this.timestamp = timestamp;
        this.partId = partId;
        this.type = type;
        this.qtyChange = qtyChange;
        this.note = note;
    }

    public String getPartId() { return partId; }

    public String toCsv() {
        return String.join("|", timestamp, partId, type.name(), String.valueOf(qtyChange), note);
    }

    public static Transaction fromCsv(String line) {
        String[] f = line.split("\\|", -1);
        if (f.length != 5) throw new IllegalArgumentException("Bad record: " + line);
        return new Transaction(f[0], f[1], Type.valueOf(f[2]), Integer.parseInt(f[3]), f[4]);
    }

    @Override
    public String toString() {
        return String.format("%-19s %-8s %-10s %+6d  %s", timestamp, partId, type, qtyChange, note);
    }
}
