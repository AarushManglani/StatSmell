import java.util.Scanner;
public class PatternProg1 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int a;
        a=input.nextInt();
        for (int i=0;i<=a;i++){
            for(int j=0;j<=i;j++){
                System.out.print    (j + " ");
            }
            System.out.println();
        }
    }
}
