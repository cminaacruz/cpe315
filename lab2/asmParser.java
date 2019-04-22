import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class asmParser {

    public static void main(String args[]) throws Exception {
        FileReader fr = new FileReader(args[0]);

        // int i;
        // while((i = fr.read()) != -1) {
        //     System.out.print((char) i);
        // }
        createRegMap();
    }

    public static void createRegMap() {
        // create String array of registers
        String[] reg = {"zero", "v0", "v1", "a0", "a1", "a2", "a3", "t0", "t1",
                        "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
                        "s3", "s4", "s5", "s6", "s7", "t8", "t9", "ra"};
        Integer[] regNums = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                            16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 31};

        // create hashmap
        HashMap<String, Integer> registers = new HashMap<>();

        for(int i = 0; i < reg.length; i++) {
            registers.put(reg[i], regNums[i]);
        }

        // printing out components of hashmap
        for(int j = 0; j < reg.length; j++)
        {
            Integer a = registers.get(reg[j]);
            System.out.println(reg[j] + ": " + a + "\n");
        }

        registers.clear();
    }
}
