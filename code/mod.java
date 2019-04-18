import java.util.Scanner;

public class mod{
	public static void main(String[] args){
		mod();
	}

	public static void mod(){
		int dividend, divisor, mask, counter, remainder;
		//counter is used during mask calculation
		//mask is used to find remainder

		mask = 0; //intialized mask
		counter = 1; //start counter at lowest bit index

		Scanner input = new Scanner(System.in);
        System.out.println("This program performs a mod operation\n");
        System.out.print("Enter a dividend: ");
        dividend = input.nextInt();
        System.out.print("Enter a divisor: ");
        divisor = input.nextInt();

		//mask    = 0b0000000
		//counter = 0b0000001
		//--OR--  -----------
		//newmask = 0b0000001

		//shift counter
		//counter = 0b0000010

		//mask    = 0b0000000
		//counter = 0b0000010
		//--OR--  -----------
		//newmask = 0b0000011

		//shift counter
		//counter = 0b0000100
		//... repeat until counter == divisor

		while(counter != divisor){
			mask = mask | counter; // add another bit to mask
			counter = counter<<1;  //shift counter bit left
		}

		remainder = dividend & mask;

		System.out.println("Remainder: " + remainder);

	}
}