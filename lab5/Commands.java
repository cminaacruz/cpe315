import java.util.Map;
import java.util.ArrayList;

public class Commands {

    String cmd;
    static int bneReach = 0;

    // dummy constructor
    public Commands() {}

    public void help() {
        System.out.println("\nh = show help");
        System.out.println("d = dump register state");
        System.out.println("s = single step through the program (i.e. execute 1 instruction and stop)");
        System.out.println("s num = step through num instructions of the program");
        System.out.println("r = run until the program ends");
        System.out.println("m num1 num2 = display data memory from location num1 to num2");
        System.out.println("c = clear all registers, memory, and the program counter to 0");
        System.out.println("q = exit the program");
        System.out.println("o = output a comma separated listing of the x,y coordinates to a file");
        System.out.println("b = output the branch predictor accuracy\n");
    }

    public void dump(Map<String, Integer> registers) {
        String[] reg = {"pc", "0", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1",
                        "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
                        "s3", "s4", "s5", "s6", "s7", "t8", "t9", "sp", "ra"};

        // prints out registers
        int count = 0;
        System.out.println("\n" + reg[0] + " = " + lab5.progCount);
        for (int r = 1; r < reg.length; r++) {
            Integer value = registers.get(reg[r]);
            if ((count != 0) && (count % 4) == 0) {
                System.out.println();
            }
            if (reg[r] == "0") {
                System.out.print("$" + reg[r] + " = " + value + " \t\t");
            }
            else {
                System.out.print("$" + reg[r] + " = " + value + " \t");
            }
            count++;
        }
        System.out.println("\n");
    }

    public void run(){
        ArrayList<String> currInstruct;
        while (lab5.progCount != lab5.instrList.size()){
            currInstruct = lab5.instrList.get(lab5.progCount);
            execInstruction(currInstruct);
        }
    }
    public void step(String numSteps) {
        int stepCnt;
        boolean executed = false;
        int steps = Integer.parseInt(numSteps);
        ArrayList<String> currInstruct;
        for (stepCnt = 0; stepCnt < steps && lab5.progCount != lab5.instrList.size(); stepCnt++){
            executed = true;
            currInstruct = lab5.instrList.get(lab5.progCount);
            execInstruction(currInstruct);
        }
        if(executed)
            System.out.println("\t" + stepCnt + " instruction(s) executed");
    }

    public static void displayDataMem(int startIdx, int endIdx){
        System.out.print("\n");
        for (int idx = startIdx; idx <= endIdx; idx++){
            System.out.println("[" + idx + "] = " + lab5.dataMemory[idx]);
        }
        System.out.print("\n");
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

        lab5.progCount = 0;

        System.out.println("\tSimulator reset\n");
    }

    public static void execInstruction(ArrayList<String> instr){

        //used to save values from instuction
        boolean increment = true;  //flag for whether or not to increment PC
        String rd, rs, rt;         //String values for register
        int rd_val,rs_val, rt_val; //decimal value for register
        int immed, h, target, offset;
        String command = instr.get(0); //get the operation we are trying to run
        branchPredictor bp = new branchPredictor();
        bp.ifTaken = false;

        switch(command){
            case "and":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();

                //load register values from hashmap
                rs_val = regGet(rs);
                rt_val = regGet(rt);

                //perform AND ($s & $t) operation
                rd_val = rs_val & rt_val;

                //store result into rd register
                regPut(rd, rd_val);
                break;
            case "or":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();

                //load register values from hashmap
                rs_val = regGet(rs);
                rt_val = regGet(rt);

                //perform OR ($s | $t) operation
                rd_val = rs_val | rt_val;

                //store result into rd register
                regPut(rd, rd_val);
                break;
            case "add":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();

                //load register values from hashmap
                rs_val = regGet(rs);
                rt_val = regGet(rt);

                //perform ADD ($s + $t) operation
                rd_val = rs_val + rt_val;

                //store result into rd register
                regPut(rd, rd_val);
                break;
            case "addi":
                //save register values from instruction
                rt = instr.get(1).toString();
                rs = instr.get(2).toString();
                immed = Integer.parseInt(instr.get(3).toString());
                //immed &= 0x0000FFFF; //Mask away the upper 16 bits

                //load register values from hashmap
                rs_val = regGet(rs);
                //perform ADDI ($s + immed) operation
                rt_val = rs_val + immed;
                //store result into rt register
                regPut(rt, rt_val);
                break;
            case "sll":
                //save register values from instruction
                rd = instr.get(1).toString();
                rt = instr.get(2).toString();
                h = Integer.parseInt(instr.get(3).toString());
                //load register values from hashmap
                rt_val = regGet(rt);
                //perform SLL ($t << h) operation
                rd_val = rt_val << h;
                //store result into rd register
                regPut(rd, rd_val);
                break;
            case "sub":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();
                //load register values from hashmap
                rs_val = regGet(rs);
                rt_val = regGet(rt);
                //perform SUB($s - $t) operation
                rd_val = rs_val - rt_val;
                //store result into rd register
                regPut(rd, rd_val);
                break;
            case "slt":
                //save register values from instruction
                rd = instr.get(1).toString();
                rs = instr.get(2).toString();
                rt = instr.get(3).toString();
                //load register values from hashmap
                rs_val = regGet(rs);
                rt_val = regGet(rt);
                rd_val = 0; //assume $s>$t, Rd = 0
                //perform SLT ($s < $t) operation
                if(rs_val < rt_val)
                    rd_val = 1;
                //store result into rd (1 if $s<$t, 0 if $s>$t) register
                regPut(rd, rd_val);
                break;
            case "beq":
                //save register values from instruction
                rs = instr.get(1).toString();
                rt = instr.get(2).toString();
                immed = lab5.labels.get(instr.get(3).toString());
                //load register values from hashmap
                rs_val = regGet(rs);
                rt_val = regGet(rt);
                //perform BEQ: if $s == $t
                if(rs_val == rt_val){
                    //calculate target
                    offset = immed - (lab5.progCount + 1); //NOTE Need to make static variable lab5.progCount
                    lab5.progCount += offset;//jump to target
                    increment = false;
                    bp.ifTaken = true;
                }
                bp.totalPredict += 1;
                bp.doPrediction(bp.ifTaken);
                break;
            case "bne":
                //save register values from instruction
                rs = instr.get(1).toString();
                rt = instr.get(2).toString();
                immed = lab5.labels.get(instr.get(3).toString());
                //load register values from hashmap
                rs_val = regGet(rs);
                rt_val = regGet(rt);
                //perform BEQ: if $s != $t
                if(rs_val != rt_val){
                    //calculate target
                    offset = immed - (lab5.progCount + 1); //NOTE Need to make static variable lab5.progCount
                    lab5.progCount += offset;//jump to target
                    increment = false;
                    bp.ifTaken = true;
                    //bneReach += 1;
                }
                bp.totalPredict += 1;
                bp.doPrediction(bp.ifTaken);

                break;
            case "lw":
                //save register values from instruction
                rt = instr.get(1).toString();
                immed = Integer.parseInt(instr.get(2).toString());
                rs = instr.get(3).toString();
                //load register values from hashmap
                rs_val = regGet(rs);
                //load value from memory MEM[$s + immed]
                rt_val = lab5.dataMemory[rs_val + immed];
                //store value into $t
                regPut(rt, rt_val);
                break;
            case "sw":
                //save register values from instruction
                rt = instr.get(1).toString();
                immed = Integer.parseInt(instr.get(2).toString());
                rs = instr.get(3).toString();
                //load register values from hashmap
                rt_val = regGet(rt);
                rs_val = regGet(rs);
                //store $t value into memory MEM[$s + immed]
                lab5.dataMemory[rs_val + immed] = rt_val;
                break;
            case "j":
                //load jump target value from hashmap
                target = lab5.labels.get(instr.get(1).toString()) - 1;
                //jump to target
                lab5.progCount = target;
                increment = false;
                break;
            case "jr":
                //save register value from instruction
                rs = instr.get(1).toString();
                //load register values from hashmap
                rs_val = regGet(rs);
                //jump to value from register
                lab5.progCount = rs_val;
                increment = false;
                break;
            case "jal":
                //load jump target value from hashmap
                target = lab5.labels.get(instr.get(1).toString()) - 1;
                //store current PC value into $ra in hashmap
                regPut("ra", lab5.progCount + 1);
                //jump to target
                lab5.progCount = target;
                increment = false;
                break;
            default:
                System.out.println("invalid instruction: " + instr.get(0).toString());
                return;
        }
        if(increment)
            lab5.progCount++;
    }

    public static void regPut(String dest, int value){
        if(dest == "zero")
            dest = "0";
        lab5.registers.put(dest, value);
    }

    public static int regGet(String source){
        if(source == "zero")
            source = "0";
        return lab5.registers.get(source);
    }
}
