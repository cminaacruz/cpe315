import java.util.Scanner;

public class exponent {
    public static void main(String[] args){
        exponent();
    }

    public static void exponent(){
        int base, exponent;
        int answer;
        int num = 0; /* used to store previous sum that will be added up */
        int tmp; /* used to store tmp sum */
        Scanner input = new Scanner(System.in);
        System.out.println("This program calculates exponents\n");
        System.out.print("Enter a base: ");
        base = input.nextInt();
        System.out.print("Enter an exponent: ");
        exponent = input.nextInt();
        /* initialize answer to the user-specified base */
        answer = base;

        for (int i = 1; i < exponent; i++) {
            /* initialize num to answer before each iteration where
               answer will be the previous sum */
            num = answer;
            /* reinitialize tmp to 0 before next iteration in order to
               sum previous "answer" a "base" # of times */
            tmp = 0;
            for (int j = 0; j < base; j++) {
                tmp += num;
            }
            /* store sum from tmp into answer for next iteration */
            answer = tmp;
        }

        System.out.println("Result: " + answer);
    }
}
