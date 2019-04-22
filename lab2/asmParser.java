import java.io.*;

public class asmParser {

    public static void main(String args[]) throws Exception {
        FileReader fr = new FileReader(args[0]);

        int i;
        while((i = fr.read()) != -1)
            System.out.print((char) i);
    }
}
