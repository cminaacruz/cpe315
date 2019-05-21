import java.util.Map;
import java.util.ArrayList;

public class Pipeline {

    Commands cmd;

    //Need private variables for registers Status
    //program counter?
   	PipelineRegister if_id;
   	PipelineRegister id_exe;
   	PipelineRegister exe_mem;
   	PipelineRegister mem_wb;

    ArrayList<String> currInstr; //used to store instruction, if needed
    String currInstrName; //used to store instruction name/label (found in registers)

    static int cycleCount = 0; //Cycle Count used for CPI
    static int numInstrExec = 0; //Instruction count used for CPI
    static int brOffset = 0; //offset from branches
    static boolean taken = false; // default not taken (0)
    static boolean squash_fetch = false;  //used to produce squash in fetch
    static boolean squash_decode = false; //used to produce squash in decode
    static boolean squash_execute = false; //used to produce squash in execute
    static boolean stall_decode = false;   //used to produce stall in decode
    static boolean stall_fetch = false;    //used to stall PC 
    static boolean branch_flag = false;    //used to handle branch
    static boolean stall_branch = false; //used to stall PC on branch
    static boolean setOffset = false;
    static boolean endOfFile = false; //endOfFile


    // dummy constructor
    public Pipeline() {
		if_id = new PipelineRegister("empty");  //initialize an empty if_id register
   		id_exe = new PipelineRegister("empty"); //initialize an empty id_exe register
   		exe_mem = new PipelineRegister("empty"); //initialize an empty exe_mem register
		mem_wb = new PipelineRegister("empty"); //initialize an empty mem_wb register
    }

	//Prints Pipeline register's values in the format:
	//pc	if/id	id/exe	exe/mem	mem/wb
    public void printPipeRegs(){
    	System.out.println("\npc\tif/id\tid/exe\texe/mem\tmem/wb");
    	System.out.print(lab4.progCount + "\t" + if_id.getInstrName() + "\t"
    	                   + id_exe.getInstrName() + "\t" + exe_mem.getInstrName()
                           + "\t" + mem_wb.getInstrName() + "\n\n");
    }

    public void simulate_cpu_cycle() {
        writeBack(); //updates the Count and CPI
        memory(); //moves instrName and Instr down the pipeline
        execute(); //executes branch instructions and moves instrName and Instr down the pipeline
        decode(); //executes non branch instructions and moves instrName and Instr down the pipeline
        fetch(); //gets a new instruction using PC and moves it into if_id
    }

    public void writeBack() {
        
        currInstrName = mem_wb.getInstrName(); //get instruction label from id_exe register

        if(!currInstrName.equals("stall") && !currInstrName.equals("squash")
            && !currInstrName.equals("empty")){ //check if its a valid instruction
        	numInstrExec++; //update number of instructions
    	}

    	cycleCount++;  //increment cycles
    	//System.out.println(cycleCount);
    	//System.out.println(numInstrExec);
    }

    public void memory() {

        //if we saw a branch in exec stage
        if(branch_flag){
            squash_execute = true; //generate squash in execute
            squash_decode = true;  //generate squash in decode
            squash_fetch = true;   //generate squash in fetch
            stall_branch = true;    //stop program counter for one time
        }

        //move instrName and Instr down the pipleine
        mem_wb.setInstrName(exe_mem.getInstrName());  //move InstrName from exe_mem to mem_wb
        mem_wb.setInstr(exe_mem.getInstr());  //move Instr from exe_mem to mem_wb
    }

    public void execute() {
        currInstrName = id_exe.getInstrName(); //get instruction label from id_exe register
        taken = false; 	//reinitialize taken

        if(!currInstrName.equals("stall") && !currInstrName.equals("squash")
            && !currInstrName.equals("empty")){ //check if its a valid instruction
            currInstr = id_exe.getInstr();   //if so get that instruction

            if(!branch_flag && !currInstrName.equals("j")
            	&& !currInstrName.equals("jal") && !currInstrName.equals("jr")){
                cmd.execInstruction(currInstr);  // execute the instruction
            }
        }

        branch_flag = false; //reset branch_flag

        if(taken){ //if our instruction earlier was a taken branch
        	branch_flag = true;
        }

        //check if instruction is load word
        if(currInstrName.equals("lw")){
            String if_id_instrName = if_id.getInstrName(); //previous register instruction name;
            if(!if_id_instrName.equals("stall") && !if_id_instrName.equals("squash")){ //if the instruction after is valid
                ArrayList<String> if_id_Instruct = if_id.getInstr(); //get previous register intruction
                //next instruction registers T and S
                String nextRegT = "";
                String nextRegS = "";

                //load word register T
                //lw $t, offset($s)
                String lwRegT = currInstr.get(1); //get RT

                //get next instruction RT and RS depending on if they are used
                switch(if_id_instrName){
                    //These instructions use RS and RT
                    case "and":
                    case "or":
                    case "add":
                    case "slt":
                    case "sub":
                        nextRegS = if_id_Instruct.get(2);//RS at index 2
                        nextRegT = if_id_Instruct.get(3);//RT at index 3
                        break;
                    //These instructions use RT
                    case "sll":
                    case "beq":
                    case "bne":
                        nextRegT = if_id_Instruct.get(2);//RT at index 2
                        break;
                    //These instructions use RS
                    case "addi":
                        nextRegS = if_id_Instruct.get(2);//RS at index 2
                        break;
					case "sw":                        
                    case "lw":
                        nextRegS = if_id_Instruct.get(3);//RS at index 3
                        break;
                    case "jr":
                        nextRegS = if_id_Instruct.get(1);//RS at index 1
                        break;
                }

                //if the lw RT register gets used in the next instruction
                if(!lwRegT.equals("0") && !lwRegT.equals("zero")){
	                if(lwRegT.equals(nextRegT) || lwRegT.equals(nextRegS)){
	                    stall_decode = true;
	                    stall_fetch = true;
	                }
            	}
            }   
        }

        //if squash_execute flag is on
        if(squash_execute){
            branch_flag = false; //reset branch flag
            squash_execute = false; //reset squash flag
            exe_mem.setInstrName("squash"); //squash current instruction and pass it to exe_mem
        }
        else{ //otherwise pass along the instruction
            exe_mem.setInstrName(id_exe.getInstrName());  //move InstrName from id_exe to exe_mem
            exe_mem.setInstr(id_exe.getInstr());  //move Instr from id_exe to exe_mem
        }

    }

    public void decode() {
        currInstrName = if_id.getInstrName(); //get instruction label from id_exe register
        if(!currInstrName.equals("stall") && !currInstrName.equals("squash")
            && !currInstrName.equals("empty")){ //check if its a valid instruction
            currInstr = if_id.getInstr();   //if so get that instruction

            //If we just ran a jump, squash the if_id register
            if(currInstrName.equals("jal") || currInstrName.equals("jr")
                || currInstrName.equals("j")){
                squash_fetch = true;
            	stall_fetch = true;
            	cmd.execInstruction(currInstr);  // execute the instruction
            }
        }   
        
        if(stall_decode){ //if stall_decode flag is on
            stall_decode = false; //reset squash flag
            id_exe.setInstrName("stall"); //squash current instruction and pass it to exe_mem
        }
        else if(squash_decode){ //if squash_decode flag is on
            squash_decode = false; //reset squash flag
            id_exe.setInstrName("squash"); //squash current instruction and pass it to exe_mem
        }
        else{ //otherwise pass along the instruction
            id_exe.setInstrName(if_id.getInstrName()); //move Instrname from if_id to id_exe
            id_exe.setInstr(if_id.getInstr());  //move Instr from if_id to id_exe
        }
    }

    public void fetch() {
        // check PC for line number to grab that instruction
        // grab instruction from List
        // update pipeline output
        // Also runs NON-Branch and NON-jump instructions
        // Increments PC unless a branch, jump, or stall has just gone off
    	ArrayList<String> currInstr = new ArrayList<String>();
		String currInstrName;
    	if(!endOfFile){
        	currInstr = lab4.instrList.get(lab4.progCount);
        }

        //place instrName and Instr into the pipeline
        if(endOfFile){
        	if_id.setInstrName("empty"); //squash the if_id InstrName
        }else if(squash_fetch) {
            squash_fetch = false; //reset squash flag
            if_id.setInstrName("squash"); //squash the if_id InstrName
        }else if(!stall_fetch){
            currInstrName = currInstr.get(0);
            //System.out.println(currInstr);
            if_id.setInstrName(currInstrName); //put InstrName to if_id
            if_id.setInstr(currInstr);  //put Instr to if_id
        }

        if(setOffset){
        	lab4.progCount = brOffset;//jump to target
            setOffset = false;
        }

        if(lab4.incrementPC && !stall_fetch && !stall_branch) //global flag checking if PC should be incremented
        	lab4.progCount++;


        if(stall_branch){
        	setOffset = true;//jump to target
        	lab4.progCount++;
        }

        stall_fetch = false;
        stall_branch = false;

        if(lab4.progCount == lab4.instrList.size()){
        	lab4.incrementPC = false;
        	endOfFile = true;
        }
    }

    boolean isEmpty(){
    	String mem_wb_Name = mem_wb.getInstrName(); //get instruction label from id_exe register
    	if(mem_wb_Name.equals("empty") && !lab4.incrementPC){
    		return true;
    	}
    	return false;
    }

}
