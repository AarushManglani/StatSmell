class Billing {
    void generateBill(int a, int b) {
        System.out.println("Total: " + (a + b));
    }
    void generateBill(int a, int b, int c) {
        System.out.println("Total: " + (a + b + c));
    }
    void generateBill(int a, int b, int c, int d) {
        System.out.println("Total: " + (a + b + c + d));
    }
    public static void main(String[] args) {
        Billing b = new Billing();
        b.generateBill(10, 20);
        b.generateBill(10, 20, 30);
        b.generateBill(10, 20, 30, 40);
    }
}
