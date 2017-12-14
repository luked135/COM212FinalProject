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
	private String eventtring;
	private String timelineString;
	private String friendString;

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

				//printQueue(); I dont think we want to print the queue here?
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

		createReadFile("MySocialProfile.txt");
			//createWriteFile("MySocialProfile.txt");
			
		System.out.println();

		System.out.println("Name: " + name);
		System.out.println("Email: " + email);
		System.out.println("Class Year: " + classYear);
		
		System.out.println("");
		System.out.println("Next Upcoming Event:");
		System.out.println("");
		
		if (newQueue.isEmpty()) {
			System.out.println("You have no upcoming events!");
		}
		
		else{
			System.out.println(newQueue.getMin());
		}

		System.out.println("");
		System.out.println("Timeline posts:");
		System.out.println("");
		
		if (timeline.isEmpty()) {
			System.out.println("You have no posts on your timeline!");
		}
		
		else {
			for (int t = 1; t < 4; t++) {
				if (timeline.size()-t >= 0 && timeline.peek(timeline.size()-t) != null) {
					System.out.println(timeline.peek(timeline.size()-t));
				}
			}
		}
		
		System.out.println("");
		System.out.println("List of Events:");
		System.out.println("");
		
		if (newQueue.isEmpty()) {
			System.out.println("You have no upcoming events!"); 
		}
		
		else {
			printQueue();
		}
		
		System.out.println("");
		
	}  
	
	public void makeEvent(int m, int d, int y, int h, int min, String desc){
		
		Event newEvent = new Event(m, d, y, h, min, desc);
		newQueue.insert(newEvent);

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
			
		if(newQueue.isEmpty()){
			writeInfo("You have no events!");
		}
		
		else{
			String ev = "";
			for(int i = 1; i <= newQueue.size(); i++){
				ev = ev + newQueue.getEvent(i).getMonth() + "<" + newQueue.getEvent(i).getDay() + "<" + newQueue.getEvent(i).getYear() + "<" + newQueue.getEvent(i).getHour() + "<" + newQueue.getEvent(i).getMinute() + "<" + newQueue.getEvent(i).getDescription() + "|";
			}
			writeInfo(ev);
		}
		
		
		if(timeline.isEmpty()){
			writeInfo("You have no posts on your timeline!");
		}
		else{
			String tl = "";
			for(int i = 0; i < timeline.size(); i++){
				tl = tl + timeline.peek(i) + "|";
			}
			writeInfo(tl);
		}
			
		if(friendList.isEmpty()){
			writeInfo("You have no friends!");
		}
			
		cleanupWriter();
	}
	
	//Might be able to put this just above logoutUser in main
	public void storeValues(){
		try{
			createReadFile("MySocialProfile.txt");
			
			name = infoReader.readLine();
			password = infoReader.readLine();
			email = infoReader.readLine();
			classYear = infoReader.readLine();
			
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
