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

    static int cycleCount = 0; //Cycle Count used for CPI
    static int taken = 0; // default not taken (0)
    int jump;

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
        //update count and CPI
        cycleCount++;
    }

    public void memory() {
        //move instrName and Instr down the pipleine
        mem_wb.setInstrName(exe_mem.getInstrName());  //move InstrName from exe_mem to mem_wb
        mem_wb.setInstr(exe_mem.getInstr());  //move Instr from exe_mem to mem_wb
    }

    public void execute() {
        ArrayList<String> currInstruct = lab4.instrList.get(lab4.progCount);
        String currInstrName = currInstruct.get(0);
        //move instrName and Instr down the pipleine
        exe_mem.setInstrName(id_exe.getInstrName());  //move InstrName from id_exe to exe_mem
        exe_mem.setInstr(id_exe.getInstr());  //move Instr from id_exe to exe_mem

        if(currInstrName.equals("beq") || currInstrName.equals("bne")) {
            cmd.execInstruction(currInstruct);
        }

    }

    public void decode() {
        jump = 0;
        String currInstr = id_exe.getInstrName(); //get id_exe InstrName

        //use for debugging
        //System.out.println(currInstr);

        //If we just ran a jump, squash the if_id register
        if(currInstr.equals("jal") || currInstr.equals("jr")
            || currInstr.equals("j")){
            jump = 1;
            System.out.println("decode: " + currInstr);
        }

        //move instrName and Instr down the pipeline
        id_exe.setInstrName(if_id.getInstrName()); //move Instrname from if_id to id_exe
        id_exe.setInstr(if_id.getInstr());  //move Instr from if_id to id_exe
    }

    public void fetch() {
        // check PC for line number to grab that instruction
        // grab instruction from List
        // update pipeline output
        // Also runs NON-Branch and NON-jump instructions
        // Increments PC unless a branch, jump, or stall has just gone off

        ArrayList<String> currInstruct = lab4.instrList.get(lab4.progCount);
        String currInstrName;

        //place instrName and Instr into the pipeline
        if (jump == 1) {
            if_id.setInstrName("squash"); //squash the if_id InstrName
            currInstruct = lab4.instrList.get(lab4.progCount + 1);
            currInstrName = currInstruct.get(0);
            System.out.println("fetch: " + currInstrName);
        } else {
            currInstrName = currInstruct.get(0);
            if_id.setInstrName(currInstrName); //put InstrName to if_id
            if_id.setInstr(currInstruct);  //put Instr to if_id
        }

        //only run valid instructions that aren't branches or jumps
        //need to try running beq and bne instructions later on (in execute?)
        if(!currInstrName.equals("jal") && !currInstrName.equals("j") && !currInstrName.equals("jr")
            && !currInstrName.equals("beq") && !currInstrName.equals("bne")){
            cmd.execInstruction(currInstruct); //run instruction
        }

        if(lab4.incrementPC) //global flag checking if PC should be incremented
            lab4.progCount++;
    }

}
