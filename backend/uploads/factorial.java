import java.util.Scanner;
public class factorial {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number whose factorial you want to calculate:");
        int num=input.nextInt();
        int factorial=1;
        for(int i=num;i>0;i--){
            factorial*=i;
        }
        System.out.println("The factorial of "+num+" is:"+factorial);
    }
}
