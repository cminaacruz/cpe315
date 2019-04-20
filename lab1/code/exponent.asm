# Name: Gonzalo Arana and Carmina Cruz
# Section: 1
# Description: exponent.asm

# declare global so programmer can see actual addresses
.globl welcome
.globl base
.globl exponent

# Data Area (strings to be displayed during program)
.data

welcome:
		.asciiz "Please enter base and exponent \n"

base:
		.asciiz "Enter base: \n"

exponent:
		.asciiz "Enter exponent: \n"

# Text Area (instructions)
.text

		# registers used
		# $s0 = base
		# $s1 = exponent
		# $s2 = answer
		# $s3 = num
		# $s4 = tmp
		# $t0 = counter (i in for loop)
		# $t1 = counter2 (j in other for loop)

main:
		# display welcome message
		addi 		$v0, $0, 4

		# starting address for the welcome message
		lui			$a0, 0x1001
		syscall

		# display prompt
		addi		$v0, $0, 4

		# starting address of the prompt (0x21 is the num char for welcome)
		lui			$a0, 0x1001
		addi		$a0, 0x21
		syscall

		# read the user input (set to 5 to read) for base
		addi		$v0, $0, 5
		syscall

		# clear $s0 for base
		addi		$s0, $0, 0

		# save base
		addu		$s0, $v0, $0

		# display prompt again
		addi		$v0, $0, 4
		lui			$a0, 0x1001
		addi		$a0, 0x2F
		syscall

		# read user input for exponent
		addi		$v0, $0, 5
		syscall

		# clear $s1 for exponent
		addi		$s1, $0, 0

		# save exponent
		addu		$s1, $v0, $0

		# answer = base
		addu		$s2, $s0, $0

		# if (exponent == 0) { answer = 1;}
		beq			$s1, $0, base_case

		# clear $t0 and start counter @ i = 1
		addi		$t0, $0, 1

loop1:
		# if counter = exponent
		beq			$t0, $s1, done

		# set num = answer
		addi		$s3, $s2, 0

		# reinitialize tmp to 0
		addi		$s4, $0, 0

		# initialize loop2
		# clear $t1 to store counter
		addi		$t1, $0, 0

		# jump to inner for loop2
		j loop2

contLoop1:
		# set answer = tmp
		addi		$s2, $s4, 0

		# increment counter
		addi		$t0, $t0, 1

		# jump to top of loop1
		j loop1

loop2:
		# if counter2 = base
		beq			$t1, $s0, contLoop1

		# tmp += num
		addu		$s4, $s4, $s3

		# increment counter
		addi		$t1, $t1, 1

		# jump to top of loop2
		j loop2

base_case:
		# answer = 1
		addi		$s2, $0, 1
		j done

done:
		# display answer
		addi		$v0, $0, 1
		add			$a0, $s2, $0
		syscall

		# exit
		addi		$v0, $0, 10
		syscall
