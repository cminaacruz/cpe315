
/*
	Authors     : Carmina Cruz, Gonzalo Arana
	File        : lab6.java
	Description : Cache simulator
                  2KB, direct mapped, 1-word blocks
                  2KB, direct mapped, 2-word blocks
                  2KB, direct mapped, 4-word blocks
                  2KB, 2-way set associative, 1-word blocks
                  2KB, 4-way set associative, 1-word blocks
                  2KB, 4-way set associative, 4-word blocks
                  4KB, direct mapped, 1-word blocks
*/
import java.util.*;
import java.io.*;

public class lab6 {
    static List<String> addresses = new ArrayList<String>();
    public static void main(String args[]){
        /* File parsing that Scans in lines of the file and splits at the tab
           to grab just the addresses and then adds them to a List */
        Scanner readFile;
        
        try {
            readFile = new Scanner(new File(args[0]));

            while (readFile.hasNext()) {
                String line = readFile.nextLine();
                String[] lineSplit = line.split("\t");

                addresses.add(lineSplit[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist: " + args[0]);
        }

        // used to print out the memory reference addresses in address List
        for (int i = 0; i < addresses.size(); i++) {
            System.out.println(addresses.get(i));
        }
    }


}
