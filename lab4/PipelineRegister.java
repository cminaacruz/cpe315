import java.util.Map;
import java.util.ArrayList;

public class PipelineRegister {

    //Instruction Data
   	private String instrName;  	//name of instruction
    private String rd;         	//rd register
    private String rs;			//rs register
    private String rt;			//rt register
    private int immed;			//used for addi
    private int h;				//used for sll as value to shift by
    private int target;			//used for jump instrs
    private int offset;			//used branch and load/store instrs

    // dummy constructor
    public PipelineRegister(String instrName) {
    	this.instrName = instrName;
    }

    public String getInstrName(){
    	return instrName;
    }

    public String getRD(){
    	return rd;
    }

    public String getRS(){
    	return rs;
    }

    public String getRT(){
    	return rt;
    }

    public int getImmed(){
    	return immed;
    }

    public int getH(){
    	return h;
    }

    public int getTarget(){
    	return rt;
    }

    public int getOffset(){
    	return offset;
    }

    public void setInstrName(String instrName){
    	this.instrName = instrName;
    }

    public void setRD(String rd){
    	this.rd = rd;
    }

    public void setRS(String rs){
    	this.rs = rs;
    }

    public void setRT(String rt){
    	this.rt = rt;
    }

    public void setImmed(int immed){
    	this.immed = immed;
    }

    public void setH(int h){
    	this.h = h;
    }

    public void setTarget(int target){
    	this.rt = rt;
    }

    public void setOffset(int offset){
    	this.offset = offset;
    }

}