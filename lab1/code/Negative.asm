# Name: Gonzalo Arana and Carmina Cruz
# Section: 1
# Description:

# import java.util.Scanner;
#
# public class reverse {
#    public static void main(String[] args){
#        reverse();
#    }
#
#    public static void reverse(){
#        int in, mask, tmp, out, cnt;
#
#        Scanner input = new Scanner(System.in);
#        System.out.print("Enter a number to reverse: ");
#        in = input.nextInt();
#
#        /* set mask to 32nd (last) bit 1000....0 */
#        mask = 1<<31;
#        cnt = 1;
#
#        while(cnt != 0) {
#            tmp = 0;
#
#            tmp = in & mask;
#            if(tmp != 0) {
#                out = cnt | out;
#            }
#            cnt = cnt<<1;
#            mask = mask>>1;
#        }
#        System.out.print("Reversed: " + out + "\n");
#    }
#}



# declare global so programmer can see actual addresses.
.globl prompt

#  Data Area (this area contains strings to be displayed during the program)
.data

prompt:
	.asciiz "Saves negative numbers into registers a0-a4 \n\n"

#Text Area (i.e. instructions)
.text

			# registers used:
			# $s0 = input
			# $s1 = mask
			# $s2 = tmp
			# $s3 = output
			# $t0 = count
			# t1 = temp value for mask bit shift (1)

main:
		# display prompt
		addi		$v0, $0, 4
		lui			$a0, 0x1001
		syscall

		# load 1 into a0
		addi		$a0, $0, 1

		# load -1 into a1
		addi		$a1, $0, -1

		# load -500 into a2
		addi		$a2, $0, -500

		# load largest negative value into a3
		addi 		$a3, $0, 0
		addi		$a3, $a3, -2

# Exit (load 10 into $v0)
done:	ori     	$v0, $0, 10
		syscall
