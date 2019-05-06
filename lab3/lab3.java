import java.util.*;
/*Authors     : Gonzalo Arana, Carmina Cruz
  File        : Lab3.java
   Description : A Command Line Interface for processing MIPS assembly (.asm)
                files.*/

public class lab3 {
	public static void main(String args[]){
		boolean exitFlag = true;
		// interactive mode
		if (args.length < 3) {
			while (exitFlag) {
				Scanner scan = userInput();
				String input = scan.nextLine();

				// splits user input into array of substrings
				String[] inpArr = input.split(" ");
				//System.out.println();

				//check the zero index for the type of command
				switch(inpArr[0]){
					case "h":
						help();
						break;
					case "d":
						System.out.println("Dump Registers");
						break;
					case "s":
						// NOTE: Need to work on formatting - couldn't get printf to work
						if (inpArr.length > 1) {
							String str = inpArr[1] + " instruction(s) executed\n";
							System.out.print("        " + str);
						} else {
							System.out.println("        1 instruction(s) executed");
						}
						break;
					case "r":
						// NOTE: Future implementation of run() - not in lab3
						break;
					case "m":
						System.out.println("Memory Locations");
						break;
					case "c":
						System.out.println("clear all");
						break;
					case "q":
						//System.out.println("quit");
						exitFlag = false;
						break;
					default:
						System.out.println("Invalid Command");
						break;
				}
			}

		}

		// script mode

		//help();
	}


	//takes in a command array with command name as 0th index and parameters afterwards
	// public static void cmdExec(char cmd[]){
	//
	// }

	public static Scanner userInput() {
		System.out.print("mips> ");
		Scanner scan = new Scanner(System.in);
		return scan;
	}

	public static void help() {
		System.out.println();
		System.out.println("h = show help");
		System.out.println("d = dump register state");
		System.out.println("s = single step through the program (i.e. execute 1 instruction and stop)");
		System.out.println("s num = step through num instructions of the program");
		System.out.println("r = run until the program ends");
		System.out.println("m num1 num2 = display data memory from location num1 to num2");
		System.out.println("c = clear all registers, memory, and the program counter to 0");
		System.out.println("q = exit the program");
		System.out.println();
	}

	public static void step(String numSteps) {

	}

}
