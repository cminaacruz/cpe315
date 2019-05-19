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

    // dummy constructor
    public Pipeline() {
		if_id = new PipelineRegister("empty");
   		id_exe = new PipelineRegister("empty");
   		exe_mem = new PipelineRegister("empty");
		mem_wb = new PipelineRegister("empty");
    }

	//Prints Pipeline register's values in format:
	//pc	if/id	id/exe	exe/mem	mem/wb
    public void printPipeRegs(){
    	System.out.println("\npc\tif/id\tid/exe\texe/mem\tmem/wb");
    	System.out.print(lab4.progCount + "\t" + if_id.getInstrName() + "\t"
    	                   + id_exe.getInstrName() + "\t" + exe_mem.getInstrName()
                           + "\t" + mem_wb.getInstrName() + "\n\n");
    }

    public void simulate_cpu_cycle() {
        ArrayList<String> instr = new ArrayList<String>();

        writeBack();
        memory();
        execute();
        decode(instr);
        instr = fetch();
        //instr = fetch();
    }

    public void writeBack() {

    }

    public void memory() {

    }

    public void execute() {

    }

    public void decode(ArrayList<String> currInstruct) {
        if (currInstruct.isEmpty()) {
            return;
        }
        cmd.execInstruction(currInstruct);
        //System.out.println(currInstruct.get(0));

    }

    public ArrayList<String> fetch() {
        // check PC for line number to grab that instruction
        // grab instruction from List
        // update pipeline output

        ArrayList<String> currInstruct = lab4.instrList.get(lab4.progCount);
        if_id.setInstrName(currInstruct.get(0));

        return currInstruct;
    }

}
