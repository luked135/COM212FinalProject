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
		int loginChoice;
		Scanner getInfo = new Scanner(System.in);
		
		
		do {
			System.out.println("\n Enter a letter: \n\n Quit program (q)");
			System.out.println("\n Create a new account/profile (c) \n Load an existing profile (l)");
			choice = getChar();
			
			switch(choice){
				
				case 'c':
					MySocialProfile person = new MySocialProfile();
					person.createWriteFile();
					
					System.out.println("\n Please enter your first and last name seperated by a space");
					String name = getInfo.nextLine();
					person.writeInfo(name);
					
					System.out.println("\n Please enter your preferred password");
					String password = getInfo.nextLine();
					person.writeInfo(password);
					
					System.out.println("\n Please enter your email address");
					String emailAddress = getInfo.nextLine();
					person.writeInfo(emailAddress);
					
					System.out.println("\n Please enter your class year");
					String classYear = getInfo.nextLine();
					person.writeInfo(classYear);
					
					person.cleanupWriter();
					break;
					
				case 'l':
					MySocialProfile loginPerson = new MySocialProfile();
					loginPerson.createReadFile();
					
					System.out.println("\n Please enter your username (which should be your first and last name seperated by a space)");
					String lName = getInfo.nextLine();
					System.out.println("\n Please enter your password");
					String lPassword = getInfo.nextLine();
					
					if(loginPerson.checkInfo(lName, lPassword)) {
						do {
							System.out.println("\n Enter a letter: \n\n Post to your timeline (p) \n Add an event (e)"); //Do we want to welcome the user?
							System.out.println(" View your friends (v) \n Add/remove friends (f) \n\n Log out (l)");
							loginChoice = getChar();
							
							switch(loginChoice){
								case 'p':
									System.out.println("p");
									break;
								
								case 'e':
									System.out.println("e");
									break;
									
								case 'v':
									System.out.println("v");
									break;
									
								case 'f':
									System.out.println("f");
									break;
									
								case 'l':
									System.out.println("\nLogged Out Successfully");
									break;
								
								default:
									System.out.print("\nNot a valid entry.\n");
							}
						}
						while (loginChoice != 'l'); {
							
						}
					}
					else {
						System.out.println("Login information is incorrect");
					}
					
					loginPerson.cleanupReader();
					break;
				
				case 'q':
					System.out.println("\nExited Program Successfully");
					break;
					
				default:
					System.out.print("\nNot a valid entry.\n");
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
