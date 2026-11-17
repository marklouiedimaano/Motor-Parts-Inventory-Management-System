public class MotorPart {
    private final String partId;
    private String name;
    private String category;
    private String supplier;
    private int quantity;
    private int minStock;      // reorder level
    private double price;

    public MotorPart(String partId, String name, String category, String supplier,
                     int quantity, int minStock, double price) {
        this.partId = partId;
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.quantity = quantity;
        this.minStock = minStock;
        this.price = price;
    }

    public String getPartId()   { return partId; }
    public String getName()     { return name; }
    public String getCategory() { return category; }
    public String getSupplier() { return supplier; }
    public int getQuantity()    { return quantity; }
    public int getMinStock()    { return minStock; }
    public double getPrice()    { return price; }

    public void setName(String name)         { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public void setQuantity(int quantity)    { this.quantity = quantity; }
    public void setMinStock(int minStock)    { this.minStock = minStock; }
    public void setPrice(double price)       { this.price = price; }

    public double getTotalValue() { return quantity * price; }
    public boolean isLowStock()   { return quantity <= minStock; }

    public String toCsv() {
        return String.join("|", partId, name, category, supplier,
                String.valueOf(quantity), String.valueOf(minStock), String.valueOf(price));
    }

    public static MotorPart fromCsv(String line) {
        String[] f = line.split("\\|", -1);
        if (f.length != 7) throw new IllegalArgumentException("Bad record: " + line);
        return new MotorPart(f[0], f[1], f[2], f[3],
                Integer.parseInt(f[4]), Integer.parseInt(f[5]), Double.parseDouble(f[6]));
    }
}
