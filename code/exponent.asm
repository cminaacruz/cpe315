#Name: Gonzalo Arana and Carmina Cruz
#Section: 1
#Description: !!!!!FILL IN LATER!!!

#Prompt -----------------
4. Write a function which does exponentiation: x raised to the power of y. Your function should not use multiplies, but may use repeated addition. Both x and y will be positive. Name your file exponent.asm.

#Jave Code --------------

public static void exponent(){
	
}

#Assembly Code ----------

# declare global so programmer can see actual addresses.
.globl welcome
.globl basePrompt
.globl expPrompt
.globl resultText

#  Data Area (this area contains strings to be displayed during the program)
.data

welcome:
	.asciiz " This program calculates exponents \n\n"

basePrompt:
	.asciiz " Enter a base: "

expPrompt:
	.asciiz " Enter an exponent: "	

resultText: 
	.asciiz " \n Result = "

//

#Text Area (i.e. instructions)
.text

main:

	# Display the welcome message (load 4 into $v0 to display)
	ori     $v0, $0, 4			

	# This generates the starting address for the welcome message.
	# (assumes the register first contains 0).
	lui     $a0, 0x1001
	syscall

	# Display prompt
	ori     $v0, $0, 4			
	
	# This is the starting address of the prompt (notice the
	# different address from the welcome message)
	lui     $a0, 0x1001
	ori     $a0, $a0,0x22
	syscall

	# Read 1st integer from the user (5 is loaded into $v0, then a syscall)
	ori     $v0, $0, 5
	syscall

	# Clear $s0 for the sum
	ori     $s0, $0, 0	

	# Add 1st integer to sum 
	# (could have put 1st integer into $s0 and skipped clearing it above)
	addu    $s0, $v0, $s0
	
	# Display prompt (4 is loaded into $v0 to display)
	# 0x22 is hexidecimal for 34 decimal (the length of the previous welcome message)
	ori     $v0, $0, 4			
	lui     $a0, 0x1001
	ori     $a0, $a0,0x22
	syscall

	# Read 2nd integer 
	ori	$v0, $0, 5			
	syscall
	# $v0 now has the value of the second integer
	
	# Add 2nd integer to sum
	addu    $s0, $v0, $s0 

	# Display the sum text
	ori     $v0, $0, 4			
	lui     $a0, 0x1001
	ori     $a0, $a0,0x36
	syscall
	
	# Display the sum
	# load 1 into $v0 to display an integer
	ori     $v0, $0, 1			
	add 	$a0, $s0, $0
	syscall
	
	# Exit (load 10 into $v0)
	ori     $v0, $0, 10
	syscall

