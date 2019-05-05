/*Authors     : Gonzalo Arana, Carmina Cruz
  File        : Lab3.java
   Description : A Command Line Interface for processing MIPS assembly (.asm)
                files.*/

public class lab3(){
	public static void main(String args[]){

	}
}

//takes in a command array with command name as 0th index and parameters afterwards
public void cmdExec(char cmd[]){

	//check the zero index for the type of command
	switch(cmd[0]){
		case 'h':
			System.out.println("Help");
			break;
		case 'd':
			System.out.println("Dump Registers");
			break;
		case 's':
			System.out.println("Single Step");
			break;
		case 'r':
			System.out.println("Run");
			break;
		case 'm':
			System.out.println("Memory Locations");
			break;
		case 'c':
			System.out.println("clear all");
			break;
		case 'q':
			System.out.println("quit");
			break;
		default:
			System.out.println("Invalid Command");
			break;
	}
}