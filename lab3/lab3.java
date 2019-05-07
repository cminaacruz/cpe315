import java.util.*;
/*
	Authors     : Gonzalo Arana, Carmina Cruz
	File        : Lab3.java
	Description : A Command Line Interface for processing MIPS assembly (.asm) files.
*/

public class lab3 {

	public static void main(String args[]){

		Commands cmd = new Commands();

		Map<String, Integer> registers = setRegisters();
		int[] dataMemory = setDataMemory();
		boolean exitFlag = true;

		// NOTE: testing stuff
		// for (int m = 0; m < 10; m++) {
		// 	dataMemory[m] = m;
		// 	System.out.println(dataMemory[m]);
		// }
		//
		// registers.put("a0", 4);
		// registers.put("t5", 4);

		// interactive mode
		if (args.length < 3) {
			while (exitFlag) {
				Scanner scan = userInput();
				String input = scan.nextLine();

				// splits user input into array of substrings
				String[] inpArr = input.split(" ");

				//check the zero index for the type of command
				switch(inpArr[0]){
					case "h":
						cmd.help();
						break;
					case "d":
						cmd.dump(registers);
						break;
					case "s":
						cmd.step(inpArr[1]);
						break;
					case "r":
						// NOTE: implementation of run()
						break;
					case "m":
						System.out.println("Memory Locations");
						break;
					case "c":
						cmd.clear(registers, dataMemory);
						break;
					case "q":
						exitFlag = false;
						break;
					default:
						System.out.println("Invalid Command");
						break;
				}
			}

		}
		// script mode
	}

	public static Scanner userInput() {
		System.out.print("mips> ");
		Scanner scan = new Scanner(System.in);
		return scan;
	}

	public static Map<String, Integer> setRegisters() {
        // create String array of registers
        String[] reg = {"pc", "0", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1",
                        "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
                        "s3", "s4", "s5", "s6", "s7", "t8", "t9", "sp", "ra"};
        Integer[] regNums = new Integer[reg.length];

        // create hashmap and input registers as key and nums as values
        HashMap<String, Integer> registers = new HashMap<>();

		// initialize all registers to 0
        for(int i = 0; i < reg.length; i++) {
            registers.put(reg[i], 0);
        }

        return registers;
    }

	public static int[] setDataMemory() {
		int[] dataMem = new int[8192];

		// initialize all data memory to 0
		for (int i : dataMem) {
			dataMem[i] = 0;
		}
		return dataMem;
	}
}
