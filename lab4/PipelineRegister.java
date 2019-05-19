import java.util.Map;
import java.util.ArrayList;

public class PipelineRegister {

    //Instruction Data
   	private String instrName;  	//name of instruction
    ArrayList<String> instr;    //full instruction

    // constructor used to start pipeline registers as "empty"
    public PipelineRegister(String instrName) {
    	this.instrName = instrName;
    }

    //Getter code for variables ------------------
    public String getInstrName(){
    	return instrName;
    }

    public ArrayList<String> getInstr(){
        return instr;
    }

    //Setter code for variables-------------------
    public void setInstrName(String instrName){    //store instruction name
    	this.instrName = instrName;
    }

    public void setInstr(ArrayList<String> instr){ //store instruction array
        this.instr = instr;
    }

    // needed to be able to print PipelineRegister to string
    public String toString() {
        return (instrName + " ");
    }

}
