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
	
	public void checkInfo(){ //make this return a boolean later and also put this in as a parameter: String userName, String userPassword
		try{
			String line = infoWriter.readLine();
			int count = 1;
			while (line != null) {
				System.out.println(count++ + ": " + line);
				line = infoWriter.readLine();
			}
			
			/*
			String name = infoWriter.readLine();
			String password = infoWriter.readLine();
			System.out.println(name);
			System.out.println(password);

			for (int i = 1; i < 4; i++){
				String password = infoWriter.readline();
			}
			*/
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cleanup(){
		try{
			infoWriter.close();
			newFile.close();
			fileRead.close()
			infoReader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createFile() {
		try {
			newFile = new FileWriter("MySocialProfile.txt");
			infoWriter = new BufferedWriter(newFile);
			
			fileRead = new FileReader("MySocialProfile.txt");
			infoReader = new BufferedReader(fileRead);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		MySocialProfile person = new MySocialProfile();
		
		person.createFile();
		person.writeInfo("Luke Karis");
		person.writeInfo("Password!!");
		person.writeInfo("2020");
		person.writeInfo("lkaris@conncoll.edu");
		person.cleanup();
		
		MySocialProfile person2 = new MySocialProfile();
		
		person2.createFile();
		person2.checkInfo();
		person2.cleanup();
		
		/*
		person.writeInfo("This project" + "\n");
		person.writeInfo("is tricky");
		person.cleanup();
		*/
		
		
	}
	
}
