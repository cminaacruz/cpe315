#Name: Gonzalo Arana and Carmina Cruz
#Section: 1
#Description: inputs a dividend and divisor and returns the remainder

#Java Code --------------

#public static void mod(){
#		int dividend, divisor, mask, counter, remainder;
#		//counter is used during mask calculation
#		//mask is used to find remainder
#
#		 Scanner input = new Scanner(System.in);
#        System.out.println("This program performs a mod operation\n");
#        System.out.print("Enter a dividend: ");
#        dividend = input.nextInt();
#        System.out.print("Enter a divisor: ");
#        divisor = input.nextInt();
#
#		mask = 0; //intialized mask
#		counter = 1; //start counter at lowest bit index
#
#		while(counter != divisor){
#			mask = mask | counter; // add another bit to mask
#			counter = counter<<1;  //shift counter bit left
#		}
#
#		remainder = dividend & mask;
#
#		System.out.println("Remainder: " + remainder);
#}

#Assembly Code ----------

# declare global so programmer can see actual addresses.
.globl welcome
.globl div1
.globl div2
.globl remainder

# Data Area (this area contains strings to be displayed during the program)
.data

#strings start at 0x10010000
welcome:
	.asciiz "This program performs a mod operation\n\n"

#this string starts at 0x10010000 + 0d40 = 0x10010028
div1:
	.asciiz "Enter a dividend: "

#this string starts at 0x10010028 + 0d19 = 0x1001003B
div2:
	.asciiz "Enter a divisor: "

#this string starts at 0x1001003B + 0d18 = 0x1001004D
remainder: 
	.asciiz "Remainder: "

#Text Area (i.e. instructions)
.text

main:
	#use the following registers:
	#$s0 = dividend
	#$s1 = divisor
	#$s2 = mask
	#$s3 = counter
	#$s4 = remainder

	#initialize counter = 1 and mask = 0
	addi $s2, $0, 0
	addi $s3, $0, 1

	# Display the welcome message (load 4 into $v0 to display string)
	addi     $v0, $0, 4			

	# This generates the starting address for the welcome message.
	# (assumes the register first contains 0).
	lui      $a0, 0x1001
	syscall

	# Display div1 prompt
	addi     $v0, $0, 4			
	
	# This is the starting address of the prompt (notice the
	# different address from the welcome message)
	lui      $a0, 0x1001
	addi     $a0, $a0,0x28
	syscall

	# Read dividend (5 is loaded into $v0, then a syscall)
	addi     $v0, $0, 5
	syscall

	# store dividend into $s0
	addu     $s0, $0, $v0
	
	# Display div2 prompt (4 is loaded into $v0 to display)
	# 0x3B is hexidecimal for 59 decimal (the position of the next prompt)
	ori      $v0, $0, 4			
	lui      $a0, 0x1001
	addi     $a0, $a0,0x3B
	syscall

	# Read divisor
	addi	$v0, $0, 5			
	syscall
	
	# store divisor into $s1
	addu    $s1, $0, $v0

	#while(counter != divisor)
while:	beq $s3, $s1, done

		#mask = mask | counter;
	    or $s2, $s2, $s3

	    #counter = counter<<1;
	    sll $s3, $s3, 1
	    j while

	#remainder = dividend & mask
done:	and $s4, $s0, $s2  

	# Display the remainder text
      addi     $v0, $0, 4			
	  lui     $a0, 0x1001
	  addi     $a0, $a0,0x4D
	  syscall
	
	# Display the remainder
	# load 1 into $v0 to display an integer
	  addi     $v0, $0, 1			
	  add 	$a0, $s4, $0
	  syscall
	
	# Exit (load 10 into $v0)
	  addi     $v0, $0, 10
	  syscall

