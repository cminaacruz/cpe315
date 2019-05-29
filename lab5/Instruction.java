public class Instruction {
	/* Constants for the op-codes for each possible command
	  without register/immediate values filled */
	public static final int AND_CMD  = 0x00000024;
	public static final int OR_CMD   = 0x00000025;
	public static final int ADD_CMD  = 0x00000020;
	public static final int SLT_CMD  = 0x0000002A;
	public static final int SUB_CMD  = 0x00000022;

	public static final int SLL_CMD  = 0x00000000;

	public static final int ADDI_CMD = 0x20000000;
	public static final int BEQ_CMD  = 0x10000000;
	public static final int BNE_CMD  = 0x14000000;
	public static final int LW_CMD   = 0x8C000000;
	public static final int SW_CMD   = 0xAC000000;
	public static final int J_CMD    = 0x08000000;
	public static final int JAL_CMD  = 0x0C000000;

	public static final int JR_CMD   = 0x00000008;

	private int opCode = 0;

	public Instruction(int opTemplate){
		opCode = opTemplate; //set base op code
	}

	public void setRS(int regS){
		//shift by 21 bits and or
		opCode |= regS<<21;
	}

	public void setRT(int regT){
		//shift by 16 bits and or
		opCode |= regT<<16;
	}

	public void setRD(int regD){
		//shift by 11 bits and or
		opCode |= regD<<11;
	}

	public void setH(int h){
		//shift by 6 bits and or
		opCode |= h<<6;
	}

	public void setImd(int imd){
		//just or
		opCode |= imd;
	}

	public void printBinary(){
		int tempCode = opCode;
		int counter = 32;  // counter used to only print 32 bits
		while(counter != 0){
			int bitVal = tempCode & 0x80000000; // only look at MSB by masking
			if(bitVal == 0)
				System.out.print("0");
			else
				System.out.print("1");

			tempCode = tempCode<<1;

			if(counter%4 == 0)
				System.out.print(" ");
			counter--;
		}
		System.out.print("\n");
	}

}
