import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
*
*/
public class MySocialProfile {
	
	private BufferedWriter infoWriter;
	private FileWriter newFile;
	
	private BufferedReader infoReader;
	private FileReader fileRead;
	
	public MySocialProfile() { 
		
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
	
	
	public static void main(String[] args) {
		MySocialProfile person = new MySocialProfile();
		
		person.createWriteFile();
		person.writeInfo("a");
		person.writeInfo("b");
		person.writeInfo("c");
		person.writeInfo("d");
		person.cleanupWriter();
		
		person.createReadFile();
		
		System.out.println(person.checkInfo("a", "b"));
		System.out.println(person.checkInfo("b", "c"));
		System.out.println(person.checkInfo("a", "d"));
		System.out.println(person.checkInfo("c", "b"));
		
		person.cleanupReader();
		
		/*
		MySocialProfile person2 = new MySocialProfile();
		
		person2.createFile();
		person2.checkInfo();
		person2.cleanup();
		
	
		person.writeInfo("This project" + "\n");
		person.writeInfo("is tricky");
		person.cleanup();
		*/
		
		
	}
	
}
