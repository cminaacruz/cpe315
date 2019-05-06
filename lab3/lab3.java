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
		for (int currentStep = 0; currentStep < numSteps; currentStep++){

		}			
	}

	public static void executeInstruction(ArrayList Instruction){
		//for(ArrayList instr: instrList){
        //    if(instr.size() != 0){
        //        int target = 0;
        //        String command = instr.get(0).toString(); //get instruction command

                switch(command){
                    case "and":
                        Instruction and = new Instruction(Instruction.AND_CMD);
                        and.setRD(registers.get(instr.get(1).toString()));
                        and.setRS(registers.get(instr.get(2).toString()));
                        and.setRT(registers.get(instr.get(3).toString()));
                        and.printBinary();
                        break;
                    case "or":
                        Instruction or = new Instruction(Instruction.OR_CMD);
                        //or.printBinary();
                        or.setRD(registers.get(instr.get(1).toString()));
                        or.setRS(registers.get(instr.get(2).toString()));
                        or.setRT(registers.get(instr.get(3).toString()));
                        or.printBinary();
                        break;
                    case "add":
                        Instruction add = new Instruction(Instruction.ADD_CMD);
                        add.setRD(registers.get(instr.get(1).toString()));
                        add.setRS(registers.get(instr.get(2).toString()));
                        add.setRT(registers.get(instr.get(3).toString()));
                        add.printBinary();
                        break;
                    case "addi":
                        Instruction addi = new Instruction(Instruction.ADDI_CMD);
                        int imm = Integer.parseInt(instr.get(3).toString());
                        imm &= 0x0000FFFF; //Mask away the upper 16 bits
                        addi.setRT(registers.get(instr.get(1).toString()));
                        addi.setRS(registers.get(instr.get(2).toString()));
                        addi.setImd(imm);
                        addi.printBinary();
                        break;
                    case "sll":
                        Instruction sll = new Instruction(Instruction.SLL_CMD);
                        sll.setRD(registers.get(instr.get(1).toString()));
                        sll.setRT(registers.get(instr.get(2).toString()));
                        sll.setH(Integer.parseInt(instr.get(3).toString()));
                        sll.printBinary();
                        break;
                    case "sub":
                        Instruction sub = new Instruction(Instruction.SUB_CMD);
                        sub.setRD(registers.get(instr.get(1).toString()));
                        sub.setRS(registers.get(instr.get(2).toString()));
                        sub.setRT(registers.get(instr.get(3).toString()));
                        sub.printBinary();
                        break;
                    case "slt":
                        //System.out.println(instr);
                        Instruction slt = new Instruction(Instruction.SLT_CMD);
                        slt.setRD(registers.get(instr.get(1).toString()));
                        slt.setRS(registers.get(instr.get(2).toString()));
                        slt.setRT(registers.get(instr.get(3).toString()));
                        slt.printBinary();
                        break;
                    case "beq":
                        Instruction beq = new Instruction(Instruction.BEQ_CMD);
                        target = (labels.get(instr.get(3).toString())) - (instrCount + 1);
                        target &= 0x0000FFFF; //Mask away the upper 16 bits
                        beq.setRS(registers.get(instr.get(1).toString()));
                        beq.setRT(registers.get(instr.get(2).toString()));
                        beq.setImd(target);
                        beq.printBinary();
                        break;
                    case "bne":
                        Instruction bne = new Instruction(Instruction.BNE_CMD);
                        target = (labels.get(instr.get(3).toString())) - (instrCount + 1);
                        target &= 0x0000FFFF; //Mask away the upper 16 bits
                        bne.setRS(registers.get(instr.get(1).toString()));
                        bne.setRT(registers.get(instr.get(2).toString()));
                        bne.setImd(target);
                        bne.printBinary();
                        break;
                    case "lw":
                        Instruction lw = new Instruction(Instruction.LW_CMD);
                        lw.setRT(registers.get(instr.get(1).toString()));
                        lw.setImd(Integer.parseInt(instr.get(2).toString()));
                        lw.setRS(registers.get(instr.get(3).toString()));
                        lw.printBinary();
                        break;
                    case "sw":
                        Instruction sw = new Instruction(Instruction.SW_CMD);
                        sw.setRT(registers.get(instr.get(1).toString()));
                        sw.setImd(Integer.parseInt(instr.get(2).toString()));
                        sw.setRS(registers.get(instr.get(3).toString()));
                        sw.printBinary();
                        break;
                    case "j":
                        Instruction j = new Instruction(Instruction.J_CMD);
                        labelVal = labels.get(instr.get(1).toString()) - 1;
                        j.setImd(labelVal);
                        j.printBinary();
                        break;
                    case "jr":
                        Instruction jr = new Instruction(Instruction.JR_CMD);
                        jr.setRS(registers.get(instr.get(1).toString()));
                        jr.printBinary();
                        break;
                    case "jal":
                        Instruction jal = new Instruction(Instruction.JAL_CMD);
                        labelVal = labels.get(instr.get(1).toString()) - 1;
                        jal.setImd(labelVal);
                        jal.printBinary();
                        break;
                    default:
                        System.out.println("invalid instruction: " + instr.get(0).toString());
                        return;
                }
            }
            instrCount++;
        }
	}

}
