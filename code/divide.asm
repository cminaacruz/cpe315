#Name: Gonzalo Arana and Carmina Cruz
#Section: 1
#Description: !!!!!FILL IN LATER!!!

#Prompt -----------------
3. Write a function which divides a 64-bit unsigned number with a 31-bit unsigned number. The 31-bit divisor is guaranteed to be a power of 2. The program should take in the 64-bit as 2 32-bit numbers: first the upper 32 bits, then the lower 32 bits.
The answer should be printed out as 2 32-bit numbers. Do not worry if the output of your program is a negative number. PCSPIM prints out numbers as signed numbers, so if one of the answers happens to have a leading 1, the number will appear as negative. Here are some test cases (shown as dividend high 32, dividend low 32 / divisor = quotient high 32, quotient low 32): 1,1 / 65536 = 0,65536 and 2,10 / 65536 = 0,131072 and 42,32 / 32 = 1,1342177281 and 210,64 / 64 = 3, 1207959553. Name your file divide.asm. Program 3 only needs to work with positive numbers.

#Jave Code --------------

#Assembly Code ----------


# declare global so programmer can see actual addresses.
.globl welcome
.globl prompt
.globl sumText

#  Data Area (this area contains strings to be displayed during the program)
.data

welcome:
	.asciiz " This program adds two numbers \n\n"

prompt:
	.asciiz " Enter an integer: "

sumText: 
	.asciiz " \n Sum = "

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

