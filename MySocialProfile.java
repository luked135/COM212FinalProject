import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;


/**
* This class is run by MySocialProfileApp
* It stores user inputs both for immediate use, and saves them in a text file when logging out
* Users can input events, timeline posts, or friends for a friend list
*/
public class MySocialProfile {
	
	private BufferedWriter infoWriter;  //Buffered writer used whenever file is accessed
	private FileWriter fileWrite;
	
	private BufferedReader infoReader;  //Buffered reader used to record text information to the file
	private FileReader fileRead;
	
	private String name;		//These four strings store the user's inputs to be saved and/or displayed
	private String password;
	private String email;
	private String classYear;

	private ArrayStack timeline = new ArrayStack();		//These data structures are used to hold the user's information while they are using the app
	private EventArrayPriorityQueue newQueue = new EventArrayPriorityQueue(30);
	private DoublyLinkedList friendList = new DoublyLinkedList();

	/**
	* This method creates the writers used to write the user's information in a text document
	* @param fileName The name of the file that information is saved to
	*/
	public void createWriteFile(String fileName) {
		try {
			fileWrite = new FileWriter(fileName);
			infoWriter = new BufferedWriter(fileWrite);
			
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* This method creates the readers used to read the user's information from the saved text document
	* @param fileName The name of the file that information is read from
	*/
	public void createReadFile(String fileName) {
		try {
			
			fileRead = new FileReader(fileName);
			infoReader = new BufferedReader(fileRead);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* This method writes info out to the text file
	* It is called when a new user profile is constructed, as well as when the user logs out
	* @param info The line of text to be written in
	*/
	public void writeInfo(String info){
		try{
			infoWriter.write(info + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* This method is called when logging in to determine if the login information was correct
	* @param userName The user's inputted name
	* @param userPassword The user's inputted password
	* @return True if the username and password both match, false otherwise
	*/
	public boolean checkInfo(String userName, String userPassword){
		try{
			createReadFile("MySocialProfile.txt");
			String name = infoReader.readLine();		//These two strings are taken from the user's saved data
			String password = infoReader.readLine();
			
			cleanupReader();
			return(name.equals(userName) && password.equals(userPassword));	//The saved name and password are compared with the new inputted ones
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	* This method is called when the user logs in successfully
	* It reads text information from the text file and turns it into the appropriate data structures
	*/
	public void loginUser() {
		try {
			createReadFile("MySocialProfile.txt");
			String line;
			
			name = infoReader.readLine();		//The first four lines always contain name, password, email, and classyear, in that order
			password = infoReader.readLine();	//As such they can be directly read
			email = infoReader.readLine();
			classYear = infoReader.readLine();

			line = infoReader.readLine();
			if (line.equals("You have no events!") == false) {		//When there are no events/posts/friends, a line like this is added as a filler
																	//If this string isn't present, there is at least one approprite item to add
				String[] tempEvent = line.split("\\|");				//The events are stored separated by "|", so they are split into an array
			
				for (int i = 0; i < tempEvent.length; i++) {
					String[] eventComponents = tempEvent[i].split("\\<");	//The components of an event (day, month, etc.) are also split into an array
					int month = Integer.parseInt(eventComponents[0]);		//The array containing each event is read through, and the appropriate numbers taken out
					int day = Integer.parseInt(eventComponents[1]);
					int year = Integer.parseInt(eventComponents[2]);
					int hour = Integer.parseInt(eventComponents[3]);
					int minute = Integer.parseInt(eventComponents[4]);
					String description = eventComponents[5];
					
					makeEvent(month, day, year, hour, minute, description);		//The event object is created with the event information from the array
				}
			}

			line = infoReader.readLine();
			if (line.equals("You have no posts on your timeline!") == false) {
				String[] tempTimeline = line.split("\\|");							//The timeline information is split up into its separate posts
					for (int i = 0; i < tempTimeline.length; i++) {
						postTimeline(tempTimeline[i]);								//Timeline objects are created for each item from the array
					}
			}

			line = infoReader.readLine();
			if (line.equals("You have no friends!") == false) {
				String[] tempFriend = line.split("\\|");				//The friend list information is split up into the separate friend entries
					for (int i = 0; i < tempFriend.length; i++) {
						friendList.add(tempFriend[i]);					//Friend objects are created for each item in the array
					}
			}

		cleanupReader();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	* This method is called whenever the full profile needs to be displayed
	*/
	public void displayProfile() {

		createReadFile("MySocialProfile.txt");
		String line;
			
		System.out.print("\n\n");

		System.out.print("User Profile");
		System.out.println("");
		System.out.println("Name: " + name);				//The name, email, and classYear information is all stored directly in variables
		System.out.println("Email: " + email);
		System.out.println("Class Year: " + classYear);
		
		System.out.println("");
		System.out.println("Next Upcoming Event:");
		
		if (newQueue.isEmpty()) {									//If there are no events in the EventArrayPriorityQueue
			System.out.println("You have no upcoming events!");
		}
		
		else{
			System.out.println(newQueue.getMin());					//The events are sorted chronologically, so getMin will return the next upcoming event
		}

		System.out.println("");
		System.out.println("Timeline posts:");
		
		if (timeline.isEmpty()) {											//If there are no posts in the ArrayStack
			System.out.println("You have no posts on your timeline!");
		}
		
		else {
			for (int t = 1; t < 4; t++) {
				if (timeline.size()-t >= 0 && timeline.peek(timeline.size()-t) != null) {		//The loop runs 3 times by default but stops early if there are <3 posts
					System.out.println(timeline.peek(timeline.size()-t));						//Using size()-t allows for going backwards (i.e. finding the most recent posts)
				}
			}
		}
		System.out.println("");
    	System.out.println("List of Events:");
    
    	if (newQueue.isEmpty()) {									//If there are no events in the EventArrayPriorityQueue
      		System.out.println("You have no upcoming events!"); 
    	}
    
    	else {
      		printQueue();		//Prints the full list of events
    	}
    
    	System.out.println("");
	}  
	
	/**
	* This method creates an event object and adds it to the EventArrayPriorityQueue
	* @param m The month of the event
	* @param d The day of the event
	* @param y The year of the event
	* @param h The hour of the event
	* @param min The minute of the event
	* @param desc The description of the event
	*/
	public void makeEvent(int m, int d, int y, int h, int min, String desc){
		
		Event newEvent = new Event(m, d, y, h, min, desc);
		newQueue.insert(newEvent);

	}
	
	/**
	* This method is called when the user logs in, after the EventArrayPriorityQueue is assembled
	* It checks the events in order to see if any of them have passed
	*/
	public void checkDate(){
		
		Calendar now = Calendar.getInstance();
		
		while(newQueue.isEmpty() == false && newQueue.getMin().getKey() < now.getTimeInMillis()){	//True if there is an event with a timestamp before the current time
			newQueue.extractMin();	//The event is removed from the queue
		}
	}

	/**
	* This method prints out the list of events
	*/
	public void printQueue(){
		
		for(int i = 1; i <= newQueue.size(); i++){
			System.out.print(newQueue.getEvent(i));
		}
	}

	/**
	* This method adds a post to the timeline
	* @param post The user's new inputted post
	*/
	public void postTimeline (String post) {
		timeline.push(post);	//.push adds the post to the top of the stack
	}

	/**
	* This method will either add a friend to the list, or remove a friend, depending on the input
	* It searches through the friend list for the input friend and adds them if they are not found, and deletes them otherwise
	* @param friend The friend being searched for
	*/
	public void manageFriends(String friend) {
		DNode checkFriend = friendList.find(friend);	//Uses the DoublyLinkedList method .find to search for the friend input
		if (checkFriend == null) {		//True if the friend was not found
			friendList.add(friend);		//.add and .delete are both DoublyLinkedList methods
		}
		else {		//True if the friend was not found
			friendList.delete(checkFriend.getElement());
		}
	}

	/**
	* This method will print out the full friend list
	*/
	public void displayFriends() {
		if (friendList.isEmpty()) {
			System.out.println("You have no friends!");
		}
		else {
			System.out.println("Your friends:");
			friendList.display(); 		//Displays the full friend list
		}
	}

	/**
	* This method will close all of the writers
	* It is called whenever a method is done writing information
	*/
	public void cleanupWriter(){
		try{
			infoWriter.close();
			fileWrite.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	* This method will close all of the readers
	* It is called whenever a method is done reading information
	*/
	public void cleanupReader(){
		try{
			fileRead.close();
			infoReader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* This method is called when the user logs out
	* It saves all of their current information so it can be loaded when they log in again
	*/
	public void logoutUser() {
		
			createWriteFile("MySocialProfile.txt");
			
			writeInfo(name);		//These variables are already present, and the first info to be written
			writeInfo(password);
			writeInfo(email);
			writeInfo(classYear);

			if (newQueue.isEmpty()) {				//For each of events, timeline, and friends certain buffer phrase is added if there is no relevant data of that type
				writeInfo("You have no events!");	//The same phrases are checked during login, and signify that there is no data
			}
			else {
				String ev = "";		//The EventArrayPriorityQueue will be converted into a string for the purposes of the text file
      			
      			for(int i = 1; i <= newQueue.size(); i++){
        			ev = ev + newQueue.getEvent(i).getMonth() + "<" + newQueue.getEvent(i).getDay() + "<" + newQueue.getEvent(i).getYear() + "<" + newQueue.getEvent(i).getHour() + "<" + newQueue.getEvent(i).getMinute() + "<" + newQueue.getEvent(i).getDescription() + "|";
      			}		//The above line adds all of a given event's information together, along with "<"s, and a "|" at the end for dilineation
      			writeInfo(ev);		//The full event is written into the document
			}						//All of the events are written onto one line

			if (timeline.isEmpty()) {
				writeInfo("You have no posts on your timeline!");
			}
			else {
				String tl = "";		//The ArrayStack will be converted into a string for the purposes of the text file
				for (int i = 0; i < timeline.size(); i++) {
					tl = tl + timeline.peek(i) + "|";	//Timeline posts are added to the growing string
				}
				writeInfo(tl);		//The full string is written into the document
			}

			if (friendList.isEmpty()) {
				writeInfo("You have no friends!");
			}
			else {
				String fr = "";		//The DoublyLinkedList will be converted into a string for the purposes of the text file
				while (friendList.isEmpty() == false) {
					fr = fr + friendList.first() + "|";		//Friends are added to the growing string
					friendList.removeFirst();		//It is easiest to get information from the front of the DoublyLinkedList, so entries are removed to keep moving through the list
				}
				writeInfo(fr);		//The full string is written into the document
			}

			cleanupWriter();
	}
	
	/**
	* This method is called when the user logs in to record their four static data points (name, password, email, class year)
	*/
	public void storeValues(){
		try{
			createReadFile("MySocialProfile.txt");
			
			name = infoReader.readLine();		//These four data points are always in the same order, and can thus be directly read
			password = infoReader.readLine();
			email = infoReader.readLine();
			classYear = infoReader.readLine();
			
			cleanupReader();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
