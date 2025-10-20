// Type Conversion
public class TypeCasting {
    public static void main(String[] args) {
        double x = 500 / 1000;  // No type casting
        System.out.println("Without type casting the numbers individually: " + x);
        double y = (double)500 / (double)1000;  // Explicitly casting to double
        System.out.println("With type casting the numbers individually: " + y);
    }
}
