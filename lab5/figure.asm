# Name: Gonzalo Arana and Carmina Cruz
# Section: 1
# Description: This program uses the Bresenham line and circle
# algorithms to draw a stick figure

#points are stored in memory in the following way
#	| Memory| Co-ord|
#	| Addr  | value |
#	-----------------
#	|   0	|	x0	|
#	|   1	|	y0	|
#	|   2	|	x1	|
# 	|   3	|	y1	|
#	|	4	|	__	| <- $a0: accumulator: points to next open spot in memory

#Everytime a point is added to the memory, we increment an accumulator by 2
#(once for the x position and once for the y) so that we can keep track
#of which spot in memory we need to write to next

# MAIN()
#---------------------------------------------------
# registers used for main():
	# $v0 = returns # of co-ordinates added to memory (2 per x,y point pair)
	# $s0 = accumulator for current memory address (based on # of co-ords added)

main: 	add 	$s0, $0, $0 # initialize memory addr accumulator to 0
		addi 	$sp, $sp, 8192 # initialize sp to end of simulated data memory
		
		#Circle(30,100,20) #head
		addi 	$a0,$0, 30
		addi	$a1, $0, 100
		addi	$a2, $0, 20
		jal circle
		#need to update accumulator in plot()

        #Line(30,80,30,30) #body
		addi 	$a0,$0, 30
		addi	$a1, $0, 80
		addi	$a2, $0, 30
		addi	$a3, $0, 30
		jal line
		#need to update accumulator in plot()

        #Line(20,1,30,30) #left leg
        addi 	$a0,$0, 20
		addi	$a1, $0, 1
		addi	$a2, $0, 30
		addi	$a3, $0, 30
		jal line
		#need to update accumulator in plot()

        #Line(40,1,30,30) #right leg
        addi 	$a0,$0, 40
		addi	$a1, $0, 1
		addi	$a2, $0, 30
		addi	$a3, $0, 30
		jal line
		#need to update accumulator in plot()

        #Line(15,60,30,50) #left arm
        addi 	$a0,$0, 15
		addi	$a1, $0, 60
		addi	$a2, $0, 30
		addi	$a3, $0, 50
		jal line
		#need to update accumulator in plot()

        #Line(30,50,45,60) #right arm
        addi 	$a0,$0, 30
		addi	$a1, $0, 50
		addi	$a2, $0, 45
		addi	$a3, $0, 60
		jal line
		#need to update accumulator in plot()

        #Circle(24,105,3) #left eye
		addi 	$a0,$0, 24
		addi	$a1, $0, 105
		addi	$a2, $0, 3
		jal circle
		#need to update accumulator in plot()

        #Circle(36,105,3) #right eye
		addi 	$a0,$0, 36
		addi	$a1, $0, 105
		addi	$a2, $0, 3
		jal circle
		#need to update accumulator in plot()        

        #Line(25,90,35,90) #mouth center
      	addi 	$a0,$0, 25
		addi	$a1, $0, 90
		addi	$a2, $0, 35
		addi	$a3, $0, 90
		jal line
		#need to update accumulator in plot()

        #Line(25,90,20,95) #mouth left
        addi 	$a0,$0, 25
		addi	$a1, $0, 90
		addi	$a2, $0, 20
		addi	$a3, $0, 95
		jal line
		#need to update accumulator in plot()

        #Line(35,90,40,95) #mouth right
        addi 	$a0,$0, 35
		addi	$a1, $0, 90
		addi	$a2, $0, 40
		addi	$a3, $0, 95
		jal line
		#need to update accumulator in plot()        

# Exit (load 10 into $v0)
  	    j 		done		

# PLOT(x, y)
# registers used for plot()
#---------------------------------------------------
	# $a0 = x co-ord
	# $a1 = y co-ord
	# $s0 = accumulator: current memory address (based on # of co-ord points)

plot: 	sw		$a0, 0($s0)		# store x at memory address $s0
		sw		$a1, 1($s0)		# store y at memory address $s0 + 1
		addi	$s0, $s0, 2		# increment memory counter: 2 points added
		jr		$ra 			# end plot()

# CIRCLE(xc, yc, r)
# registers used for circle():
#---------------------------------------------------
	# $a0 = xc - x center
	# $a1 = yc - y center
	# $a2 = r  - radius 
	# $t0 = x 
	# $t1 = y
	# $t2 = g
	# $t3 = diagonalInc
	# $t4 = rightInc
	# $t5 = x > y   // used to tell if while loop is over 1 if x - y = $t6
	# $t6 = 1       // used to confirm that x > y
	# $t7 = stores copy of $a0
	# $t8 = stores copy of $a1
	# $t9 = g < 0   // to check condition: 1 if g < 0

circle: addi 	$sp, $sp, -1 	# make space for ra on stack 
		sw 		$ra, 0($sp) 	# store return address of function

		#copy xc and yc, since $a0 and $a1 will be altered to call plot()
		add 	$t7, $0, $a0  	# $t7 = xc
		add 	$t8, $0, $a1	# $t8 = yc

		#initialize variables
		add 	$t0, $0, $0		# x=0
		add 	$t1, $0, $a2	# y=r

		addi 	$t2, $0, 3		# g=3
		sll 	$a2, $a2, 1		# r=2*r, double value once, re-used below, so save it
		sub		$t2, $t2, $a2 	# g=3 - 2*r

		addi 	$t3, $0, 10		# diagonalInc=10
		sll 	$a2, $a2, 1		# r=2*r, double value one more time
		sub		$t3, $t3, $a2 	# diagonalInc=10 - "4*r"  
								# "4*r" is really (2(2*r)), where 2*r is from before

		addi	$t4, $0, 6		# rightInc=6

		addi 	$t6, $0, 1		# $t6= 1 used to check x<=y for while loop

		#while x<=y

whileCircle: sub 	$t5, $t0, $t1  	# $t5 = x - y, if x > y then x - y >= 1
		slt 	$t9, $t5, $t6		# check if x - y < 1, 0 means x - y >= 1
		beq		$t9, $0, endWhileCircle	# if x - y >= 1, then while loop needs to end

		#plot the 8 different points to construct the circle plot (xc+x, yc-y)

		add 	$a0, $t7, $t0	#xc+x
		add 	$a1, $t8, $t1	#yc+y
		jal plot				#plot (xc+x, yc+y)

		add 	$a0, $t7, $t0	#xc+x
		sub 	$a1, $t8, $t1	#yc-y
		jal plot				#plot (xc+x, yc-y) 

		sub 	$a0, $t7, $t0	#xc-x
		add 	$a1, $t8, $t1	#yc+y
		jal plot				#plot (xc-x, yc+y)

		sub 	$a0, $t7, $t0	#xc-x
		sub 	$a1, $t8, $t1	#yc-y
		jal plot				#plot (xc-x, yc-y)

		add 	$a0, $t7, $t1	#xc+y
		add 	$a1, $t8, $t0	#yc+x
		jal plot				#plot (xc+y, yc+x)

		add 	$a0, $t7, $t1	#xc+y
		sub 	$a1, $t8, $t0	#yc-x
		jal plot				#plot (xc+y, yc-x)

		sub 	$a0, $t7, $t1	#xc-y
		add 	$a1, $t8, $t0	#yc+x
		jal plot				#plot (xc-y, yc+x)

		sub 	$a0, $t7, $t1	#xc-y
		sub 	$a1, $t8, $t0	#yc-x
		jal plot				#plot (xc-y, yc-x)

		#if g>=0
		slt		$t9, $t2, $0 	# if g<0 -> $t9 = 1, else $t9 = 0
		bne 	$t9, $0, elseCircle 	# g<0, so go to else

		#g>=0
		add 	$t2, $t2, $t3	# g += diagonalInc
		addi 	$t3, $t3, 8		# diagonalInc += 8
		addi	$t1, $t1, -1		# y -= 1
 		j endifCircle

		#else (g<0)
elseCircle: add 	$t2, $t2, $t4 	# g += rightInc
		addi	$t3, $t3, 4	 	# diagonalInc +=4

		#increment rightInc and x
endifCircle: addi 	$t4, $t4, 4		# rightInc +=4
		addi 	$t0, $t0, 1		# x +=1

		j whileCircle

		#finished while
endWhileCircle: lw 		$ra, 0($sp)		# reload return address of function 
		addi 	$sp, $sp, 1		# return stack pointer to original location
		jr 		$ra 			# end circle()
		

# LINE()
# registers used for line():
#---------------------------------------------------
	# $a0 = x0
	# $a1 = x1
	# $a2 = y0
	# $a3 = y1
	# $t0 = x0
	# $t1 = x1
	# $t2 = y0
	# $t3 = y1
	# $t4 = abs(y1 - y0)
	# $t5 = abs(x1 - x0)
	# $t6 = st
	# $t7 = error
	# $t8 = used for if statements and for loops
	# $t9 = used to swap register values
	# $s1 = x
	# $s2 = y
	# $s3 = y-step


line: 	addi 	$sp, $sp, -4 	# make space for $ra, $s1-s3 on stack 
		sw 		$ra, 0($sp)		# store return address of function
		sw 		$s1, 1($sp)		# store $s1
		sw 		$s2, 2($sp)		# store $s2
		sw 		$s3, 3($sp)		# store $s3

		# making copies of arguments
		add 	$t0, $0, $a0 	# t0 = x0
		add 	$t1, $0, $a1 	# t1 = y0
		add 	$t2, $0, $a2 	# t2 = x1
		add 	$t3, $0, $a3 	# t3 = y1

		#finding abs subtraction values
		add 	$a0, $t3, $0	# argument 1 is y1
		add 	$a1, $t1, $0	# argument 2 is y0
		jal		absSub 			# absSub(y1, y0); abs(y1 - y0)
		add 	$t4, $v0, $0	# $t4 = abs(y1 - y0)

		add 	$a0, $t2, $0	# argument 1 is x1
		add 	$a1, $t0, $0	# argument 2 is x0
		jal		absSub 			# absSub(x1, x0); abs(x1 - x0)
		add 	$t5, $v0, $0	# $t5 = abs(x1 - x0)

		#if abs(x1 - x0) < abs(y1 - y0), this is equivalent to abs(y1 - y0) > abs(x1 - x0)
		slt 	$t8, $t5, $t4
		beq		$t8, $0, elseL1
		addi 	$t6, $0, 1		# st = 1
		j  		endifL1		
elseL1: add 	$t6, $0, $0		# st = 0
		
	
		#if (st == 1)
endifL1:beq 	$t6, $0, endifL2 # if st == 0, skip this

		#swap (x0, y0)
		add 	$t9, $t0, $0 	# copy x0 to temp var
		add 	$t0, $t1, $0	# copy y0 into x0 var
		add 	$t1, $t9, $0	# copy x0 from temp var into y0 var

		#swap (x1, y1)
		add 	$t9, $t2, $0 	# copy x1 to temp var
		add 	$t2, $t3, $0	# copy y1 into x1 var
		add 	$t3, $t9, $0	# copy x1 from temp var into y1 var
		

		#if x1 < x0, this is equivalent to x0 > x1
endifL2:slt 	$t8, $t2, $t0	# check x1 < x0
		beq		$t8, $0, endifL3 # if x1 > x0, skip this

		#swap (x0, x1)
		add 	$t9, $t0, $0 	# copy x0 to temp var
		add 	$t0, $t2, $0	# copy x1 into x0 var
		add 	$t2, $t9, $0	# copy x0 from temp var into x1 var

		#swap (y0, y1)
		add 	$t9, $t1, $0 	# copy y0 to temp var
		add 	$t1, $t3, $0	# copy y1 into y0 var
		add 	$t3, $t9, $0	# copy y0 from temp var into y1 var		


		#update deltaX, deltaY, error, and y
endifL3:sub $t5, $t2, $t0		# deltax = x1 - x0

		#find abs(y1 - y0) with new y1 and y0 and assign it to deltay
		add 	$a0, $t3, $0	# argument 1 is y1
		add 	$a1, $t1, $0	# argument 2 is y0
		jal		absSub 			# absSub(y1, y0); abs(y1 - y0)
		add 	$t4, $v0, $0	# $t4 = deltay = abs(y1 - y0)

		add 	$t7, $0, $0		# error = 0
		add 	$s2, $t1, $0	# y=y0

		#if y0 < y1
		slt 	$t8, $t1, $t3	# check y0 < y1
		beq		$t8, $0, elseL4 # if y0 > y1, go to else
		addi 	$s3, $0, 1		# ystep = 1
		j 		endifL4			# go to end of if
elseL4: addi 	$s3, $0, -1		# ystep = -1


		#for(x = x0; x < (x1 + 1); x++)
endifL4:add 	$s1, $0, $t0 	# x = x0	
		addi	$t2, $t2,, 1	# x1 = x1 + 1
forL:	beq		$s1, $t2, endforL # if x = x1 + 1, end for loop
		
		#if st == 1
		beq 	$t6, $0, elseL5 # if st == 0, go to else
		add 	$a0, $0, $s2	# argument 1 = y
		add 	$a1, $0, $s1 	# argument 2 = x
		jal 	plot 			# plot(y, x)
		j 		endifL5
elseL5: add 	$a0, $0, $s1	# argument 1 = x
		add 	$a1, $0, $s2 	# argument 2 = y
		jal 	plot 			# plot(x, y)

		#end if

endifL5:add 	$t7, $t7, $t4	# error = error + deltaY

		sll 	$t9, $t7, 1 	# $t9 = 2 * error
		#if 2*error >= deltax	
		slt 	$t8, $t9, $t5 	# check 2*error < deltax
		bne 	$t8, $0, endifL6 # if 2*error < deltax, skip this
		add 	$s2, $s2, $s3 	# y = y + ystep
		sub 	$t7, $t7, $t5 	# error = error - deltax
		
		#endif 
endifL6:addi 	$s1, $s1, 1		# x++
		j forL					#start for loop again

		# end for loop
		# de-allocating stack space
endforL:lw 		$ra, 0($sp)		# reload return address of function
		lw 		$s1, 1($sp)		# reload $s1
		lw 		$s2, 2($sp)		# reload $s2
		lw 		$s3, 3($sp)		# reload $s3
		addi 	$sp, $sp, 4 	# return stack pointer to original location
		jr		$ra				# end line()

# ABSSUB()
# takes two arguments to subtract and subtracts the smaller number from the larger number,
# resulting in a positive number 
# registers used for line():
#---------------------------------------------------
	# $a0 = num1
	# $a1 = num2
	# $t8 = used for if statements and for loops

absSub:	slt 	$t8, $a0, $a1 		# if num1 < num2 $t8 = 1, else $t8 = 0
		beq 	$t8, $0, num1larger # check if num1 > num2
		sub 	$v0, $a1, $a0 		# return num2 - num1
		j 		endAbs

#if num1 is larger, return num1 - num2
num1larger: sub 	$v0, $a0, $a1		# return num1 - num2

endAbs:	jr 		$ra 				# end absSub()

#end of program
done: 
