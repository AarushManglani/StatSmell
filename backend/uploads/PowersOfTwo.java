import java.util.Scanner;
public class PowersOfTwo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the exponent limit: ");
        int n = input.nextInt();
        int i = 0;
        int result;
        do {
            result = (int)Math.pow(2, i);
            System.out.println("2^" + i + " = " + result);
            i++;
        } while (i <= n);
    }
}
