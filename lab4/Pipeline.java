import java.util.Map;
import java.util.ArrayList;

public class Pipeline {

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
    	System.out.print(lab4.progCount + "\t");
    	System.out.print(if_id.getInstrName()+ "\t");
    	System.out.print(id_exe.getInstrName()+ "\t");
    	System.out.print(exe_mem.getInstrName()+ "\t");
    	System.out.println(mem_wb.getInstrName()+ "\t\n");
    }

}
