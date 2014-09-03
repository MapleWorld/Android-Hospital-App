package HospitalStuff;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * A representation of a Doctor. 
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang
 */
public class Doctor extends HealthProfessional implements Serializable{

	/** This Doctor's unique ID for serialization.*/
	private static final long serialVersionUID = 8854076321504422390L;

	/** 
	 * Constructs a new Doctor that manages a collection of Patients'
	 * records stored in a directory dir in file named fileName.
	 * @param dir the directory in which the patients' data is stored.
	 * @param fileName the data file containing the patients' information.
	 * @throws IOException
	 */
	public Doctor(File dir, String fileName) throws IOException {
		super(dir, fileName);
	}

	/**
	 * saves the medication's name name and medication's instruction 
	 * instruction in the medical record of the patient with health card number
	 * healthCardNumber.
	 * @param HealthCardNumber the patient's health card number.
	 * @param name the name of the prescribed medication.
	 * @param instruction the instructions of using the prescribed medication.
	 */
	public void recordPrescription(Integer healthCardNumber, String name,
			String instruction){
		Patient patient = hospitalDatabase.findPatient(healthCardNumber);
		patient.getMedRecord().setMedicationName(name);
		patient.getMedRecord().setMedicationInstruction(instruction);
	}
}
