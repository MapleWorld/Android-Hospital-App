package HospitalStuff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * A class that implements the functionality required for a log in system.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang.
 */
public class Login implements Serializable{

	/** This Login's ID for serialization. */
	private static final long serialVersionUID = 6047776794105607040L;

	/**
	 * Constructs a new Login class that validates user
	 * name and password with the passwords.txt file.
	 */
	public Login(){
	}
	
	/**
	 * returns True iff user name userName and userPassword password are valid
	 * user name and password.
	 * @param userName user name.
	 * @param userPassword password.
	 * @return True iff user name userName and userPassword password are valid.
	 * @throws FileNotFoundException
	 */
	public String readFileCheckLoginInfo(File dir, String fileName, 
			String userName, String userPassword) throws FileNotFoundException{
		File passwdFile = new File (dir, fileName);
		/* scans the file containing the passwords to check if the user name
		 * and password are valid.
		 */
		Scanner scanner = new Scanner(new FileInputStream(
				passwdFile.getPath()));
		String [] record;
	        
		while(scanner.hasNextLine()) {
			record = scanner.nextLine().split(" ");
			if(userName.equals(record[0].toString()) 
					&& userPassword.equals(record[1].toString())){
				return record[2].toString();
			}
		}
		scanner.close();
		return "false";
	}
}
