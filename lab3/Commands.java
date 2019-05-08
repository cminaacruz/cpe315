import java.util.Map;
import java.util.ArrayList;

public class Commands {

    String cmd;

    // dummy constructor
    public Commands() {}

    public void help() {
		System.out.println("\nh = show help");
		System.out.println("d = dump register state");
		System.out.println("s = single step through the program (i.e. execute 1 instruction and stop)");
		System.out.println("s num = step through num instructions of the program");
		System.out.println("r = run until the program ends");
		System.out.println("m num1 num2 = display data memory from location num1 to num2");
		System.out.println("c = clear all lab3.registers, memory, and the program counter to 0");
		System.out.println("q = exit the program\n");
	}

    public void dump(Map<String, Integer> registers) {
        String[] reg = {"pc", "0", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1",
						"t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
						"s3", "s4", "s5", "s6", "s7", "t8", "t9", "sp", "ra"};

        // prints out lab3.registers
        int count = 0;
        System.out.println("$" + reg[0] + " = " + registers.get(reg[0]));
        for (int r = 1; r < reg.length; r++) {
            Integer value = registers.get(reg[r]);
            if ((count != 0) && (count % 4) == 0) {
                System.out.println();
            }
            if (reg[r] == "0") {
                System.out.print("$" + reg[r] + "  = " + value + " \t");
            }
            else {
                System.out.print("$" + reg[r] + " = " + value + " \t");
            }
            count++;
        }
        System.out.println();
    }

    public void step(String numSteps) {
        ArrayList<String> currInstruct;
        for (int i = 0; i < Integer.parseInt(numSteps); i++){
            currInstruct = lab3.instrList.get(lab3.progCount);
            execInstruction(currInstruct);
        }
    }

    public static void clear(Map<String, Integer> registers, int[] dataMemory) {
        // clears register map
        for (String key : registers.keySet()) {
            registers.put(key, 0);
        }

        // clears data memory array
        for (int i : dataMemory) {
			dataMemory[i] = 0;
		}
    }

    public static void execInstruction(ArrayList<String> instr){
        //static Map<String, Integer> lab3.registers = setlab3.registers();
        //static int[] lab3.dataMemory = setlab3.dataMemory();
        //static programCount = 1;
        System.out.println("Current Instruction: " + instr);

        //used to save values from instuction
        boolean increment = true;  //flag for whether or not to increment PC
        String rd, rs, rt;         //String values for register
        int rd_val,rs_val, rt_val; //decimal value for register
        int immed, h, target;
        String command = instr.get(0); //get the operation we are trying to run
        switch(command){
            case "and":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();

                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                rt_val = lab3.registers.get(rt);

                //perform AND ($s & $t) operation
                rd_val = rs_val & rt_val;

                //store result into rd register
                lab3.registers.put(rd, rd_val);
                break;
            case "or":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();

                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                rt_val = lab3.registers.get(rt);

                //perform OR ($s | $t) operation
                rd_val = rs_val | rt_val;

                //store result into rd register
                lab3.registers.put(rd, rd_val);
                break;
            case "add":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();

                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                rt_val = lab3.registers.get(rt);

                //perform ADD ($s + $t) operation
                rd_val = rs_val + rt_val;

                //store result into rd register
                lab3.registers.put(rd, rd_val);
                break;
            case "addi":
                //save register values from instruction
                rt = instr.get(1).toString();
                rs = instr.get(2).toString();
                immed = Integer.parseInt(instr.get(3).toString());
                immed &= 0x0000FFFF; //Mask away the upper 16 bits

                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                //perform ADDI ($s + immed) operation
                rt_val = rs_val + immed;
                //store result into rt register
                lab3.registers.put(rt, rt_val);
                break;
            case "sll":
                //save register values from instruction
                rd = instr.get(1).toString();
                rt = instr.get(2).toString();
                h = Integer.parseInt(instr.get(3).toString());
                //load register values from hashmap
                rt_val = lab3.registers.get(rt);
                //perform SLL ($t << h) operation
                rd_val = rt_val << h;
                //store result into rd register
                lab3.registers.put(rd, rd_val);
                break;
            case "sub":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();
                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                rt_val = lab3.registers.get(rt);
                //perform SUB($s - $t) operation
                rd_val = rs_val - rt_val;
                //store result into rd register
                lab3.registers.put(rd, rd_val);
                break;
            case "slt":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();
                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                rt_val = lab3.registers.get(rt);
                rd_val = 0; //assume $s>$t, Rd = 0
                //perform SLT ($s < $t) operation
                if(rs_val < rt_val)
                    rd_val = 1;
                //store result into rd (1 if $s<$t, 0 if $s>$t) register
                lab3.registers.put(rd, rd_val);
                break;
            case "beq":
                //save register values from instruction
                rs = instr.get(1).toString();
                rt = instr.get(2).toString();
                immed = lab3.labels.get(instr.get(3).toString());
                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                rt_val = lab3.registers.get(rt);
                //perform BEQ: if $s == $t
                if(rs_val == rt_val){
                    //calculate target
                    target = immed - (lab3.progCount + 1); //NOTE Need to make static variable lab3.progCount
                    target &= 0x0000FFFF; //Mask away the upper 16 bits
                    lab3.progCount = target;//jump to target
                    increment = false;
                }
                break;
            case "bne":
                //save register values from instruction
                rs = instr.get(1).toString();
                rt = instr.get(2).toString();
                immed = lab3.labels.get(instr.get(3).toString());
                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                rt_val = lab3.registers.get(rt);
                //perform BEQ: if $s != $t
                if(rs_val != rt_val){
                    //calculate target
                    target = immed - (lab3.progCount + 1); //NOTE Need to make static variable lab3.progCount
                    target &= 0x0000FFFF; //Mask away the upper 16 bits
                    //Note might need to get rid of mask
                    lab3.progCount = target;//jump to target
                    increment = false;
                }
                break;
            case "lw":
                //save register values from instruction
                rt = instr.get(1).toString();
                immed = Integer.parseInt(instr.get(2).toString());
                rs = instr.get(3).toString();
                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                //load value from memory MEM[$s + immed]
                rt_val = lab3.dataMemory[rs_val + (immed >> 2)];
                //store value into $t
                lab3.registers.put(rt, rt_val);
                break;
            case "sw":
                //save register values from instruction
                rt = instr.get(1).toString();
                immed = Integer.parseInt(instr.get(2).toString());
                rs = instr.get(3).toString();
                //load register values from hashmap
                rt_val = lab3.registers.get(rt);
                rs_val = lab3.registers.get(rs);
                //store $t value into memory MEM[$s + immed]
                lab3.dataMemory[rs_val + (immed >> 2)] = rt_val;   
                break;
            case "j":
                //load jump target value from hashmap
                target = lab3.labels.get(instr.get(1).toString()) - 1;
                //jump to target
                lab3.progCount = target;
                increment = false;
                break;
            case "jr":
                //save register value from instruction
                rs = instr.get(1).toString();
                //load register values from hashmap
                rs_val = lab3.registers.get(rs);
                //jump to value from register
                lab3.progCount = rs_val;
                increment = false;
                break;
            case "jal":
                //load jump target value from hashmap
                target = lab3.labels.get(instr.get(1).toString()) - 1;
                //store current PC value into $ra in hashmap
                lab3.registers.put("ra", lab3.progCount);
                //jump to target
                lab3.progCount = target;
                increment = false;
                break;
            default:
                System.out.println("invalid instruction: " + instr.get(0).toString());
                return;
        } 
        if(increment)
            lab3.progCount++;      
    }
}
