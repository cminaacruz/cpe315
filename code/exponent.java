import java.util.Scanner;

public class exponent {
    public static void main(String[] args){
        exponent();
    }

    public static void exponent(){
        int base, exponent;
        int answer;
        Scanner input = new Scanner(System.in);
        System.out.println("This program calculates exponents\n");
        System.out.print("Enter a base: ");
        base = input.nextInt();
        System.out.print("Enter an exponent: ");
        exponent = input.nextInt();
        answer = base;
        for (int i = 1; i < exponent; i++)
            answer += answer;
        System.out.println("Result: " + answer);
    }
}