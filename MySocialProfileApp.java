import java.io.*;
import java.util.Scanner;
/* import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;*/
/**
*
*/
public class MySocialProfileApp {
	
	public static void main(String[] args) throws IOException { 
		
		int choice;
		Scanner getInfo = new Scanner(System.in);
		
		
		do {
			System.out.println("\n Enter a letter: \n\n quit program (q)");
			System.out.println("\n create a new account/profile (c) \n Load an existing profile (l)");
			choice = getChar();
			
			switch(choice){
				
				case 'c':
					System.out.println("\n Please enter your first and last name seperated by a space");
					String name = getInfo.nextLine();
					MySocialProfile person = new MySocialProfile(name);
					person.writeInfo(name);
					
					System.out.println("\n Please enter your email address");
					String emailAddress = getInfo.nextLine();
					person.writeInfo(emailAddress);
					
					System.out.println("\n Please enter your class year");
					String classYear = getInfo.nextLine();
					person.writeInfo(classYear);
					
					System.out.println("\n Please enter your preferred password");
					String password = getInfo.nextLine();
					person.writeInfo(password);
					person.cleanup();
					break;
					
				case 'l':
					
					break;
			}
		}
		while(choice != 'q'); {
			
		}
	}
	public static String getString() throws IOException { 
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	public static char getChar() throws IOException { 
		String s = getString();
		return s.charAt(0);
	}
	
	//Might not need this method
	public static int getInt() throws IOException { 
	
		String s = getString();
		return Integer.parseInt(s);
	}
}
