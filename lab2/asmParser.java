import java.io.*;
import java.util.*;

public class asmParser {

    // NOTE: created void functions but can change later to return hashmaps

    public static void main(String args[]) throws Exception {
        Map<String, Integer> labels;
        Map<String, Integer> registers;
        List<ArrayList<String>> instrList;
        int instrCount = 1;

        labels = getLabels(args[0]);
        instrList = getInstructions(args[0]);
        registers = getRegisters();

        //use instruction list to generate binary code
        for(ArrayList instr: instrList){
            if(instr.size() != 0){
                int target = 0;
                String command = instr.get(0).toString(); //get instruction command
                //System.out.println("Instr " + instrCount + instr);
                //System.out.println(instr);

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
                        or.printBinary();
                        or.setRD(registers.get(instr.get(1).toString()));
                        or.setRS(registers.get(instr.get(2).toString()));
                        or.setRT(registers.get(instr.get(3).toString()));
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
                        addi.setRT(registers.get(instr.get(1).toString()));
                        addi.setRS(registers.get(instr.get(2).toString()));
                        addi.setImd(Integer.parseInt(instr.get(3).toString()));
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
                        Instruction slt = new Instruction(Instruction.SLT_CMD);
                        slt.setRD(registers.get(instr.get(1).toString()));
                        slt.setRS(registers.get(instr.get(2).toString()));
                        slt.setRT(registers.get(instr.get(3).toString()));
                        slt.printBinary();
                        break;
                    case "beq":
                        Instruction beq = new Instruction(Instruction.BEQ_CMD);
                        target = instrCount - labels.get(instr.get(3).toString());
                        target ^= 0xFFFFFFFF;
                        target++;
                        target &= 0x0000FFFF;
                        beq.setRS(registers.get(instr.get(1).toString()));
                        beq.setRT(registers.get(instr.get(2).toString()));
                        beq.setImd(target);
                        beq.printBinary();
                        break;                                                                        
                    case "bne":
                        Instruction bne = new Instruction(Instruction.BNE_CMD);
                        target = instrCount - labels.get(instr.get(3).toString());
                        target ^= 0xFFFFFFFF;
                        target++;
                        target &= 0x0000FFFF;
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
                        j.setImd(labels.get(instr.get(1).toString()));
                        j.printBinary();
                        break;                                                                        
                    case "jr":
                        Instruction jr = new Instruction(Instruction.JR_CMD);
                        jr.setRS(registers.get(instr.get(1).toString()));
                        jr.printBinary();
                        break;                                                     
                    case "jal":
                        Instruction jal = new Instruction(Instruction.JAL_CMD);
                        System.out.println(instr);
                        System.out.println(target);
                        jal.setImd(labels.get(instr.get(1).toString()));
                        jal.printBinary();
                        break;                         
                    default:
                }
            }
            instrCount++;
        }
    }

    // first pass
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

    // second pass
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

            //printing out list (Debug)
            //int c = 1;
            //System.out.print(instrList);
            //for (ArrayList<String> list: instrList) {
                //System.out.print("line" + c + ": ");
                //System.out.println(list);
                //for(String s : list) {
                //   System.out.println(s);
                //}
                //c++;
            //}

            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return instrList;
    }

    public static Map<String, Integer> getRegisters() {
        // create String array of registers
        String[] reg = {"0", "zero", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1",
                        "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
                        "s3", "s4", "s5", "s6", "s7", "t8", "t9", "sp", "ra"};
        Integer[] regNums = {0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                            16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 29, 31};

        // create hashmap and input registers as key and nums as values
        HashMap<String, Integer> registers = new HashMap<>();

        for(int i = 0; i < reg.length; i++) {
            registers.put(reg[i], regNums[i]);
        }

        return registers;
    }

}
