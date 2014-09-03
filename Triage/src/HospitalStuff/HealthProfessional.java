package HospitalStuff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * A representation of a health professional.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang.
 */
public class HealthProfessional implements Serializable {
	
	/** This HealthProfessional's ID for serialization. */
	private static final long serialVersionUID = -4228829257051267944L;
	
	/** This HealthProfessional's hospitalDatabase */
	protected HospitalDatabase hospitalDatabase ;
	
	/**
	 * Constructs a new HealthProfessional that manages a collection of 
	 * patients' records stored in directory dir in file named 
	 * fileName.
	 * @param dir the directory in which the data file is stored.
	 * @param fileName the data file containing patients' information.
	 * @throws IOException
	 */
	public HealthProfessional(File dir, String fileName) throws IOException{
		this.hospitalDatabase =  new HospitalDatabase(dir, fileName);
	}

	/**
	 * returns the Patient with health card number HealthCardNumber.
	 * @param healthCardNum the health card number of a patient.
	 * @return the Patient with health card number HealthCardNumber.
	 */
	public Patient lookUpPatient(Integer healthCardNumber){
		return hospitalDatabase.findPatient(healthCardNumber);
	}
	
	/**
	 * writes the data to file outputstream.
	 * @param outputStream the output stream to write the patients' records to.
	 */
	public void saveDataToFile(FileOutputStream outputStream){
		hospitalDatabase.saveDataToFile(outputStream);
	}
}
