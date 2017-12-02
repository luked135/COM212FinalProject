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
	
	public MySocialProfile(String name) { //Ask if it is ok to put this code up in the constructor 
		try {
			newFile = new FileWriter(name + ".text");
			infoWriter = new BufferedWriter(newFile);
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

	public void cleanup(){
		try{
			infoWriter.close();
			newFile.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	public void createFile() {
		try {
			FileWriter newFile = new FileWriter(name + ".text");
			BufferedWriter infoWriter = new BufferedWriter(newFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public static void main(String[] args) {
		MySocialProfile person = new MySocialProfile("Professor Tarimo");
		
		person.writeInfo("This project" + "\n");
		person.writeInfo("is tricky");
		person.cleanup();
		
		
	}
	
}
