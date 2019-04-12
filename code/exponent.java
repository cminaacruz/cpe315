import java.util.Scanner;

public class lab1 {
    public static void main(String[] args){
        exponent();
    }

    public static void exponent(){
        int base, exponent;
        int answer = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("This program calculates exponents\n");
        System.out.println("Enter a base: ");
        base = input.nextInt();
        System.out.println("Enter an exponent: ");
        exponent = input.nextInt();
        for (int i = 0; i <= exponent; i--){
            for (int j = 0; j <= i; j--){
                answer += base;
            }
        }
        System.out.println("Result: " + answer);
    }
}