import java.util.Scanner;

public class divide {
    public static void main(String[] args){
        divide();
    }

    public static void divide(){
        int divUp, divLow; //upper and lower 32bits of dividend (also used for quotient)
        int divisor;
        int tranBit; //bit that transitions from upper32 to lower32
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter upper 32 bits of dividend: ");
        divUp = input.nextInt();
        System.out.print("Enter lower 32 bits of dividend: ");
        divLow = input.nextInt();
        System.out.print("Enter a 31 bit divisor: ");
        divisor = input.nextInt();
        /* initialize answer to the user-specified base */
        
        while(divisor != 1){
            divLow = divLow>>1;
            tranBit = divUp&1;

            if(tranBit == 1){
                tranBit = tranBit<<31;
                divLow |= tranBit;
            }

            divUp = divUp >> 1;
            divisor = divisor >> 1;
        }

        System.out.println("quotient: " + divUp + ", " + divLow);
    }
}