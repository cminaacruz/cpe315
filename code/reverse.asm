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
	.asciiz "Enter a number to reverse \n\n"

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

		# read user input
		addi		$v0, $0, 5
		syscall

		# store user input
		addu		$s0, $v0, $0

		# set mask = 1<<31
		addi		$t1, $0, 1
		sll			$s1, $t1, 31

		# count = 1
		addi		$t0, $0, 1

		# output = 0
		addi		$s3, $0, 0

while:
		# while(count != 0)
		beq			$t0, $0, done

		# tmp = 0;
		addi		$s2, $0, 0

		# tmp = input & mask;
		and			$s2, $s0, $s1

		# if(tmp != 0)
		beq			$s2, $0, whileCont

		# out = count | output;
		or			$s3, $t0, $s3

whileCont:
		# count = count<<1;
		sll			$t0, $t0, 1

		# mask = mask>>1;
		srl			$s1, $s1, 1

		# jump back to top of while
		j while

done:
		# display answer
		addi		$v0, $0, 1
		add			$a0, $s3, $0
		syscall

		# Exit (load 10 into $v0)
		ori     	$v0, $0, 10
		syscall
