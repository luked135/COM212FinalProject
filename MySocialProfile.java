import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class MySocialProfile {
	
	private BufferedWriter infoWriter;
	private FileWriter fileWrite;
	
	private BufferedReader infoReader;
	private FileReader fileRead;
	
	private File oldFile;
	private File newerFile;
	
	private String name;
	private String password;
	private String email;
	private String classYear;

	private ArrayStack timeline = new ArrayStack();
	private EventArrayPriorityQueue newQueue = new EventArrayPriorityQueue(10); //Should we keep this capacity?
	private DoublyLinkedList friendList = new DoublyLinkedList();
	
	public void makeFiles(){
		oldFile = new File("C:/Users/luked/Desktop/compSci/com212/FinalProject MySocialProfile.txt");
		newerFile = new File("C:/Users/luked/Desktop/compSci/com212/FinalProject TempFile.txt");
	}

	public void createWriteFile(String fileName) {
		try {
			fileWrite = new FileWriter(fileName);
			infoWriter = new BufferedWriter(fileWrite);
			
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createReadFile(String fileName) {
		try {
			
			fileRead = new FileReader(fileName);
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
			createReadFile("MySocialProfile.txt");
			String name = infoReader.readLine();
			String password = infoReader.readLine();
			
			cleanupReader();
			return(name.equals(userName) && password.equals(userPassword));
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void loginUser() {
		try {
			createReadFile("MySocialProfile.txt");
			String line;
			for (int i = 0; i < 4; i++) {
				infoReader.readLine();
			}

			line = infoReader.readLine();
			if (line.equals("You have no events!") == false) {
				String[] tempEvent = line.split("\\|");
			
				for (int i = 0; i < tempEvent.length; i++) {
					String[] eventComponents = tempEvent[i].split("\\<");
					int month = Integer.parseInt(eventComponents[0]);
					int day = Integer.parseInt(eventComponents[1]);
					int year = Integer.parseInt(eventComponents[2]);
					int hour = Integer.parseInt(eventComponents[3]);
					int minute = Integer.parseInt(eventComponents[4]);
					String description = eventComponents[5];
					
					makeEvent(month, day, year, hour, minute, description);
				}

				printQueue();
			}

			line = infoReader.readLine();
			if (line.equals("You have no posts on your timeline!") == false) {
				String[] tempTimeline = line.split("\\|");
					for (int i = 0; i < tempTimeline.length; i++) {
						postTimeline(tempTimeline[i]);
					}
			}

			line = infoReader.readLine();
			if (line.equals("You have no friends!") == false) {
				String[] tempFriend = line.split("\\|");
					for (int i = 0; i < tempFriend.length; i++) {
						friendList.add(tempFriend[i]);
					}
			}

		cleanupReader();
		}

			/*String test = "A B C D";
			String[] output = test.split("\\");
			for (int i =0; i < output.length; i++)
			System.out.println(output[i]);
			*/
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void displayProfile() {
		try {

			createReadFile("MySocialProfile.txt");
			//createWriteFile("MySocialProfile.txt");

			String line;
			
			line = infoReader.readLine();
			System.out.println("Name: " + line);

			infoReader.readLine();
			
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
			cleanupReader();
			//cleanupWriter();
		}  

		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeEvent(int m, int d, int y, int h, int min, String desc){
		
		Event newEvent = new Event(m, d, y, h, min, desc);
		newQueue.insert(newEvent);
		/*
		Scanner scan = new Scanner(System.in);
		//Calendar userCal = Calendar.getInstance();  
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
		
		*/
	}
	
	
	public void checkDate(){
		
		Calendar now = Calendar.getInstance();
		
		while(newQueue.isEmpty() == false && newQueue.getMin().getKey() < now.getTimeInMillis()){ //Will a while loop work here or do we want an if statement
			newQueue.extractMin();
		}
	}

	public void printQueue(){
		
		for(int i = 1; i <= newQueue.size(); i++){
			System.out.print(newQueue.getEvent(i));
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

	public void displayFriends() {
		if (friendList.isEmpty()) {
			System.out.println("You have no friends!");
		}
		else {
			friendList.display(); //if there is time, make this prettier by splitting up the friend list
		}
	}

	public void cleanupWriter(){
		try{
			infoWriter.close();
			fileWrite.close();
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
	
	/*
	I think the reason this isnt working is because to use the rename method you need to have an empty file that you rename the previous file to
	I dont think you can just rename a file with a name of an existing file
	*/
	public void logoutUser() {
		
			createWriteFile("MySocialProfile.txt");
			
			writeInfo(name);
			writeInfo(password);
			writeInfo(email);
			writeInfo(classYear);
			
			cleanupWriter();
	}
	
	public void storeValues(){
		try{
			createReadFile("MySocialProfile.txt");
			
			name = infoReader.readLine();
			System.out.println(name);
			
			password = infoReader.readLine();
			System.out.println(password);
			
			email = infoReader.readLine();
			System.out.println(email);
			
			classYear = infoReader.readLine();
			System.out.println(classYear);
			
			cleanupReader();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		MySocialProfile person = new MySocialProfile();
		person.loginUser();
		/*
		person.createReadFile();
		person.postTimeline("I like dogs");
		person.postTimeline("a");
		person.postTimeline("b");
		person.postTimeline("c");
		person.displayProfile();
		person.cleanupReader();
		*/
	}
}
