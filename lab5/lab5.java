import java.util.*;
import java.io.*;
import java.lang.Math;
/*
	Authors     : Carmina Cruz, Gonzalo Arana
	File        : Lab5.java
	Description : A Command Line Interface for processing MIPS assembly (.asm) files.
*/

public class lab5 {

	static Map<String, Integer> registers = setRegisters();
	static int[] dataMemory = setDataMemory();
	static Map<String, Integer> labels;
	static int progCount = 0;
	static List<ArrayList<String>> instrList;

	public static void main(String args[]){
		branchPredictor bp = new branchPredictor();
		boolean exitFlag = true;

		labels = getLabels(args[0]);
		instrList = getInstructions(args[0]);

		// interactive mode
		if (args.length < 2) {
			while (exitFlag) {
				Scanner scan = userInput();
				String input = scan.nextLine();

				// splits user input into array of substrings
				String[] inpArr = input.split(" ");

				exitFlag = cmdExec(inpArr, exitFlag);
			}

		}
		// script mode
		else {
			//int bits;
			if (args.length == 3) {
				bp.ghrSize = Integer.parseInt(args[args.length-1]);
				bp.initializeGHR(bp.ghrSize);
			}
			else {
				bp.ghrSize = 2;
				bp.initializeGHR(bp.ghrSize);
			}
		    Scanner buffer;
		    List<String> script = new ArrayList<String>();

		    try {
		        // read lines of script into list
		        buffer = new Scanner(new File(args[1]));

		        while(buffer.hasNext()) {
		            script.add(buffer.nextLine());
		        }

		    } catch (FileNotFoundException e) {
		        System.out.println("FILE NOT FOUND: " + args[1]);
		    }

		    String[] scriptCmds = new String[script.size()];

		    // convert string list to array
		    //scriptCmds = new String[script.size()];
		    for (int i = 0; i < script.size(); i++) {
		        scriptCmds[i] = script.get(i);
		    }
		    // go through commands
		    String[] sCmd = new String[scriptCmds.length];
		    for (int i = 0; i < scriptCmds.length; i++) {
		        sCmd = scriptCmds[i].split(" ");
				if (sCmd.length > 1) {
					System.out.print("mips> " + sCmd[0]);
					for (int l = 1; l < sCmd.length; l++) {
						System.out.print(" " + sCmd[l]);
					}
					System.out.println();
				}
				else {
					System.out.println("mips> " + sCmd[0]);
				}
		        cmdExec(sCmd, exitFlag);
		    }
		}
	}

	public static boolean cmdExec(String[] inpArr, boolean exit) {
		Commands cmd = new Commands();
		branchPredictor bp = new branchPredictor();

		//check the zero index for the type of command
		switch(inpArr[0]){
			case "h":
				cmd.help();
				break;
			case "d":
				cmd.dump(registers);
				break;
			case "s":
				if(inpArr.length > 1)
					cmd.step(inpArr[1]);
				else
					cmd.step("1");
				break;
			case "r":
				cmd.run();
				break;
			case "m":
				cmd.displayDataMem(Integer.parseInt(inpArr[1]), Integer.parseInt(inpArr[2]));
				break;
			case "o":
				break;
			case "b":
				bp.printAccuracy();
				break;
			case "c":
				cmd.clear(registers, dataMemory);
				break;
			case "q":
				exit = false;
				break;
			default:
				System.out.println("Invalid Command");
				break;
		}
		return exit;
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

    public static Map<String, Integer> getLabels(String file) {
	    // create label HashMap
	    HashMap<String, Integer> labels = new HashMap<>();

	    try {
	        // counter for line number
	        int lnNum = 1;
	        Scanner scanner = new Scanner(new File(file));

	        // while loop to grab next line in file
	        while (scanner.hasNextLine()) {
	            String secondTrim = "";
	            String[] splitString;
	            String ln = scanner.nextLine();

	            //trim leading and trailing spaces
	            String trimString = ln.trim();

	            //split at # for comments
	            splitString = trimString.split("#");

	            //Only want to look at everything before the comments
	            if(splitString.length > 0)
	                secondTrim = splitString[0].trim(); /*remove spaces before # (used to
	                                                      ignore lines that only have comments)*/

	            //if the line wasn't empty and wasn't only occupied by comments
	            if(splitString.length > 0 && secondTrim.length() > 0){
	                int lst = splitString[0].lastIndexOf(':');
	                // if : detected, add label name + line number to map
	                if (lst != -1) {
	                    String[] result = ln.split(":");
	                    result[0] = result[0].replaceAll("^\\s+",""); //trim leading spaces
	                    labels.put(result[0], lnNum);
	                }
	                lnNum += 1;
	            }
	        }
	        scanner.close();
	    } catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    }
	    return labels;
    }

    public static List<ArrayList<String>> getInstructions(String file) {
        List<ArrayList<String>> instrList = new ArrayList<ArrayList<String>>();
        int count = 1;

        try {
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNextLine()) {
                ArrayList<String> instr = new ArrayList<String>();
                String ln = scanner.nextLine();

                // split at # for comments
                String[] result = ln.split("#");
                // ignores blank lines and filters out labels from asm code
                if (result.length > 0) {
                    // removes all whitespaces and returns string
                    if (result[0].trim().length() > 0) {
                        int lst = result[0].lastIndexOf(':');
                        // if ':' is detected in line (labels)
                        if (lst != -1) {
                            String[] result2 = result[0].split(":");
                            // splits up each instruction by delimiters
                            StringTokenizer tok = new StringTokenizer(result2[1], " ,$()\t");
                            while (tok.hasMoreTokens()) {
                                instr.add(tok.nextToken());
                            }
                            count += 1;
                        }
                        // lines without labels
                        else {
                            // splits up each instruction by delimiters
                            StringTokenizer tok = new StringTokenizer(result[0], " ,$()\t");
                            while (tok.hasMoreTokens()) {
                                instr.add(tok.nextToken());
                            }
                            count += 1;
                        }

                        instrList.add(instr);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        //remove empty instructions
        instrList.removeAll(Collections.singleton(new ArrayList<>()));

        return instrList;
    }
}
