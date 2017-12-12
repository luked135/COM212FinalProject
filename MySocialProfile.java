import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class MySocialProfile {
	
	private BufferedWriter infoWriter;
	private FileWriter newFile;
	
	private BufferedReader infoReader;
	private FileReader fileRead;
	
	private ArrayStack timeline = new ArrayStack();
	private EventArrayPriorityQueue newQueue = new EventArrayPriorityQueue(10);
	private DoublyLinkedList friendList = new DoublyLinkedList();

	public void createWriteFile() {
		try {
			newFile = new FileWriter("MySocialProfile.txt");
			infoWriter = new BufferedWriter(newFile);
			
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createReadFile() {
		try {
			
			fileRead = new FileReader("MySocialProfile.txt");
			infoReader = new BufferedReader(fileRead);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeInfo(String info){
		try{
			infoWriter.write(info + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean checkInfo(String userName, String userPassword){ //make this return a boolean later and also put this in as a parameter: String userName, String userPassword
		try{
			
			String name = infoReader.readLine();
			String password = infoReader.readLine();
			
			return(name.equals(userName) && password.equals(userPassword));
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void loginUser() {

	}

	public void displayProfile() {
		try {

			createReadFile();
			String line = infoReader.readLine();
			System.out.println("Name: " + line);
			
			line = infoReader.readLine();
			line = infoReader.readLine();
			System.out.println("Email: " + line);

			line = infoReader.readLine();
			System.out.println("Class Year: " + line);
			line = infoReader.readLine();

			System.out.println("Timeline posts:");
			for (int t = 1; t < 4; t++) {
				if (timeline.size()-t >= 0 && timeline.peek(timeline.size()-t) != null) {
					System.out.println(timeline.peek(timeline.size()-t));
				}
			}

			System.out.println(timeline);

			cleanupReader();
		}  

		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeEvent(){
		
		Scanner scan = new Scanner(System.in);
		Calendar userCal = Calendar.getInstance();  

		int month, day, year, hour, min;
		String description;
		
		System.out.println("Please enter a description of your event: ");
		description = scan.nextLine();
		System.out.println("Please enter the month of your event in the form of a number (MM): ");
		month = scan.nextInt();								
		System.out.println("Please enter the day of your event (DD): ");
		day = scan.nextInt();	
		System.out.println("Please enter the year of your event (YYYY): "); 					
		year = scan.nextInt();	
		System.out.println("Please enter the hour of the day of your event (0-23): ");
		hour = scan.nextInt();	
		System.out.println("Please enter the minute of the hour of your event (00-59): ");
		min = scan.nextInt();	
		
		Event newEvent = new Event(month, day, year, hour, min, description);
		
		newQueue.insert(newEvent);
	}
	
	
	public void checkDate(){
		
		Calendar now = Calendar.getInstance();
		
		while(newQueue.isEmpty() == false && newQueue.getMin().getKey() < now.getTimeInMillis()){ //Will a while loop work here or do we want an if statement
			newQueue.extractMin();
		}
	}

	public void postTimeline (String post) {
		timeline.push(post);
	}

	public void manageFriends(String friend) {
		DNode checkFriend = friendList.find(friend);
		if (checkFriend == null) {
			friendList.add(friend);
		}
		else {
			friendList.delete(checkFriend.getElement());
		}
	}

	public void cleanupWriter(){
		try{
			infoWriter.close();
			newFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void cleanupReader(){
		try{
			fileRead.close();
			infoReader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void logoutUser() {

	}

	public static void main(String[] args) {
		MySocialProfile person = new MySocialProfile();
		person.createReadFile();
		person.postTimeline("I like dogs");
		person.postTimeline("a");
		person.postTimeline("b");
		person.postTimeline("c");
		person.displayProfile();
		person.cleanupReader();
		
	}
}
