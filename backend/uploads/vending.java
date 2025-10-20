import java.util.Scanner;
public class vending {
    public static void main(String[]args){
        Scanner input = new Scanner(System.in);
        System.out.println("Press 1 for juice or 2 for soda");
        int a=input.nextInt();
        if(a==1){
            System.out.println("Dispensing juice");
        }
        else if(a==2){
            System.out.println("Dispensing soda");
        }
        else{
            System.out.println("Invalid choice");
        }
    }
}
