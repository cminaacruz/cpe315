import java.util.Map;

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
		System.out.println("c = clear all registers, memory, and the program counter to 0");
		System.out.println("q = exit the program\n");
	}

    public void dump(Map<String, Integer> registers) {
        String[] reg = {"pc", "0", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1",
						"t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
						"s3", "s4", "s5", "s6", "s7", "t8", "t9", "sp", "ra"};

        // prints out registers
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
        // for (int currentStep = 0; currentStep < numSteps; currentStep++){
        //
        // }

        // if (inpArr.length > 1) {
        //     String str = inpArr[1] + " instruction(s) executed\n";
        //     System.out.print("        " + str);
        // } else {
        //     System.out.println("        1 instruction(s) executed");
        // }
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
}
