import java.util.Scanner;

public class reverse {
    public static void main(String[] args){
        reverse();
    }

    public static void reverse(){
        int in, mask, tmp, out, cnt;

        Scanner input = new Scanner(System.in);
        System.out.print("Enter a number to reverse: ");
        in = input.nextInt();

        /* set mask to 32nd (last) bit 1000....0 */
        mask = 1<<31;
        cnt = 1;
        out = 0;

        while (cnt) {
            tmp = 0;
        }

    }
}
