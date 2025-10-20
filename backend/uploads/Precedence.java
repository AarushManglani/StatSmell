/*
Operator Precedence: PEMDAS: Here 1) Parenthesis
2)MULTIPLICATION,DIVISION AND MODULUS 3)ADDITION AND SUBTRACTION
BUT L to R will be used if precedence is same
*/
public class Precedence{
    public static void main(String[] args){
        int x = 9 + 3 / 2 * 4 - 2;
        System.out.println("Without paranthesis:"+x);
        int y = (9 + 3) / 2 * 4 - 2;
        System.out.println("With paranthesis:"+y);
    }
}