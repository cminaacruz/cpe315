#Name: Gonzalo Arana and Carmina Cruz
#Section: 1
#Description: Takes in a 64 bit dividened ( 32 upper bits first and then the lower 32 bits) and a 31 bit divisor and outputs the 64 bit quotient in two 32bit portions (upper 32bits, lower 32bits)

#Java Code --------------

#public static void divide(){
#	int divUp, divLow; //upper and lower 32bits of dividend (also used for quotient)
#   int divisor;
#   int tranBit; //bit that transitions from upper32 to lower32
      
#   Scanner input = new Scanner(System.in);
#   System.out.print("Enter upper 32 bits of dividend: ");
#   divUp = input.nextInt();
#   System.out.print("Enter lower 32 bits of dividend: ");
#   divLow = input.nextInt();
#   System.out.print("Enter a 31 bit divisor: ");
#   divisor = input.nextInt();
#   /* initialize answer to the user-specified base */

#    while(divisor != 1){
#        divLow = divLow>>1;
#        tranBit = divUp&1;

#        if(tranBit == 1){
#            tranBit = tranBit<<31;
#            divLow |= tranBit;
#        }

#        divUp = divUp >> 1;
#        divisor = divisor >> 1;
#    }
#
#    System.out.println("quotient: " + divUp + ", " + divLow);
#}

#Assembly Code ----------

# declare global so programmer can see actual addresses.
.globl divUpper
.globl divLower
.globl divisor
.globl quotient

#  Data Area (this area contains strings to be displayed during the program)
.data

divUpper:
	.asciiz "Enter upper 32 bits of dividend: "

# 0x1001 0022
divLower: 
	.asciiz "Enter lower 32 bits of dividend: "

# 0x1001 0044
divisor: 
	.asciiz "Enter a 31 bit divisor: "

# 0x1001 005D
quotient: 
	.asciiz "quotient: "

# 0x1001 0068
comma: 
	.asciiz " , "

#Text Area (i.e. instructions)
.text

main:

	#initalize variables
	#$s0 = divUp
	#$s1 = divLow
	#$s2 = divisor
	#$s3 = tranBit
	#$t0 = 1 register

	#load $t0 with 1
	addi $t0, $0, 1

	# Ask for dividened upper 32
	addi     $v0, $0, 4
	lui      $a0, 0x1001
	syscall

	# Read dividened upper 32
	addi     $v0, $0, 5
	syscall

	#store dividened upper 32 into $s0
	addi $s0, $v0, 0

	# Ask for dividened lower 32
	addi     $v0, $0, 4			
	lui     $a0, 0x1001
	addi     $a0, $a0,0x22
	syscall

	# Read dividened lower 32
	addi     $v0, $0, 5
	syscall

	#store dividened lower 32 into $s1
	addi 	 $s1, $v0, 0
	
	# Ask for divisor
	addi     $v0, $0, 4			
	lui    	 $a0, 0x1001
	addi     $a0, $a0,0x44
	syscall

	# Read divisor
	addi	 $v0, $0, 5			
	syscall
	
	#store divisor into $s2
	addi 	 $s2, $v0, 0

#while(divisor != 1) use $t0 as a reg for 1
while: 
	beq $s2, $t0, done

	#divLow = divLow>>1;
	srl $s1, $s1, 1

    #tranBit = divUp&1;
    andi $s3, $s0, 1

		#if(tranBit == 1) use $t0 as a reg for 1
		bne $s3, $t0, tranZero

		#tranBit = tranBit<<31;
		sll $s3, $s3, 31

		#divLow |= tranBit;
		or $s1, $s1, $s3

tranZero: 

	#divUp = divUp >> 1;
	srl $s0, $s0, 1

    #divisor = divisor >> 1;
    srl $s2, $s2, 1

	j while

done:
    # Quotient part1
	addi     $v0, $0, 4			
	lui    	 $a0, 0x1001
	addi     $a0, $a0, 0x5D
	syscall

	# Print Quotient Upper 32 
	addi     $v0, $0, 1			
	addi     $a0, $s0, 0
	syscall

	# Quotient comma
	addi     $v0, $0, 4			
	lui    	 $a0, 0x1001
	addi     $a0, $a0, 0x68
	syscall

	# Print Quotient Lower 32
	addi     $v0, $0, 1			
	addi     $a0, $s1, 0
	syscall

	# Exit (load 10 into $v0)
	ori     $v0, $0, 10
	syscall

