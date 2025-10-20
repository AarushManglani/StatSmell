import java.util.*;
public class recursive_factorial{
    static int func(int n){
       if(n==0 || n==1){
           return 1;
       }
       else{
           n-=1;
           return (n+1)*func(n);
       }
    }
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int a=input.nextInt();
        System.out.println("Factorial of "+a+" is "+func(a));
    }
}