package HospitalStuff;

import android.annotation.SuppressLint;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A representation of a Hospital Database. A class that manages a collection
 * of Patients and their information.
 * @author Ou Ye, Lindsey Poad, Marina Tawfik , Dazhen Wang.
 */
public class HospitalDatabase implements Serializable {
	
	/** This HospitalDatabase's ID for serialization. */
	private static final long serialVersionUID = -7244646078820740281L;
	
	/** The Patients that this HospitalDatabase manages. */
	private Map<Integer, Patient> masterPatientsPool;
	
	/** Patients who have not yet been seen by a doctor grouped together 
	 * according to their urgency level.
	 */
	private Map<Integer,ArrayList<Patient>> PatientUrgencyMultiMap;

	/**
	 * Constructs a new HospitalDatabse that manages a collection of Patients' 
	 * records stored in directory dir in file named file Name.
	 * @param dir the directory in which the data file is stored.
	 * @param fileName the name of the data file.
	 * @throws IOException
	 */
	@SuppressLint("UseSparseArrays")
	public HospitalDatabase(File dir, String fileName) throws IOException{
		this.masterPatientsPool = new HashMap<Integer, Patient>();
		this.PatientUrgencyMultiMap = 
				new HashMap<Integer,ArrayList<Patient>>();
		File file = new File(dir, fileName);
		if (file.exists()){
			loadData(file.getPath());
		}else{
			file.createNewFile();
		}
	}
	
	/**
	 * returns the Patients who have not yet been seen by a doctor 
	 * grouped together according to their urgency level.
	 * @return the Patients who have not yet been seen by a 
	 * doctor grouped together according to their urgency level.
	 */
	public Map<Integer, ArrayList<Patient>> getPatientUrgencyMultiMap(){
		return PatientUrgencyMultiMap;
	}
	
	/**
	 * returns the Patient in this HospitalDatabase with health card
	 * number healthCardNumber.
	 * @param healthCardNumber the patient's health card number.
	 * @return the Patient in this HospitalDatabase with health card
	 * number healthCardNum.
	 */
	public Patient findPatient(Integer healthCardNumber) {
		return masterPatientsPool.get(healthCardNumber); 
	}

	/**
	 * adds Patient newPatient to this HospitalDatabase.
	 * @param newPatient the new patient to be added to this HospitalDatabse.
	 */
	public void addPatient(Patient newPatient) {
		masterPatientsPool.put(newPatient.getHealthCardNumber(), 
    			newPatient);
	}
	
	/**
	 * returns the Patients' information managed by this HospitalDatabase. 
	 * @return the Patients' information managed by this HospitalDatabase. 
	 */
	public Map<Integer, Patient> getMasterPatientPool(){
		return masterPatientsPool;
	}
	
	/**
	 * populates this HospitalDatabse with Patients' information from 
	 * the file at path filePath.
	 * @param filePath the file path of the Patients' data file.
	 * @throws FileNotFoundException
	 */
	public void loadData(String filePath)throws FileNotFoundException{
		Scanner scanner = new Scanner (new FileInputStream(filePath));
		String[] patientInfo ;
		while(scanner.hasNextLine()){
			patientInfo = scanner.nextLine().split("#");
			Patient newPatient = scan(patientInfo);
			addPatient(newPatient);
		}
		scanner.close();
	}
	
	/**
	 * Writes the information of the Patients managed by this HospitalDatabse
	 * to file outputStream: one patient's information per line.
	 * @param outputStream the output stream to which 
	 * the patients' information is written.
	 */
	public void saveDataToFile(FileOutputStream outputStream) {
		try {
			for (Patient p : masterPatientsPool.values()){
	        	outputStream.write((p.toString() + "\n").getBytes());
	        }outputStream.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	/**
	 * Creates and returns a Patient created from the given information info.
	 * (information obtained from reading the file).
	 * @param info information obtained from reading the file that 
	 * specifies a Patient.
	 * @return a Patient created from the given information info.
	 */
	@SuppressLint("UseValueOf")
	private Patient scan(String[] info){
		String[] dob = info[1].split("-");
		Patient newPatient = new Patient (info[0], Integer.parseInt(info[2]), 
				Integer.parseInt(dob[0]),
				Integer.parseInt(dob[1]), 
				Integer.parseInt(dob[2]));
		
		String[] vitalSigns = info[3].split("//");
		
		for (String v : vitalSigns){
			scanHelper(newPatient, v);
		}
		return newPatient;
	}
	
	/**
	 * Creates and returns a Patient created from the given information info.
	 * (information obtained from reading the file).
	 * @param newPatient Passing this patient that is currently 
	 * read from the file.
	 * @param vitalSigns The vital information about this patient.
	 */
	@SuppressLint("UseValueOf")
	private void scanHelper(Patient newPatient, String vitalSigns){
		String[] visitInfo = vitalSigns.split("@");
		String arrivalTime = visitInfo[1];
		newPatient.setArrivalTime(arrivalTime);

		String[] temperatures = visitInfo[2].replace("[","").
					replace("]","").split(",");
		populateWithTemperatures(newPatient, temperatures);
		String[] pressures = visitInfo[3].replace("[","").
				replace("]","").split(",");
		populateWithPressures(newPatient, pressures);
		String[] heartRates = visitInfo[4].replace("[","").
				replace("]","").split(",");
		populateWithHeartRates(newPatient, heartRates);
		String[] symptoms = visitInfo[5].replace("[","").
				replace("]","").split(",");
		populateWithSymptoms(newPatient, symptoms);
		String[] medicationName = visitInfo[6].replace("[","").
				replace("]","").split(",");
		populateWithMedicationName(newPatient, medicationName);
		String[] medicationInstruction = visitInfo[7].replace("[","").
				replace("]","").split(",");
		populateWithMedicationInstruction(newPatient,
				medicationInstruction);
		newPatient.getVisits().changeSeenByDr(Boolean.valueOf(visitInfo[8]), 
				visitInfo[9]);
		newPatient.getVisits().setUrgency(Integer.parseInt(visitInfo[10]));
		addPatientToUrgencyList(newPatient,
				Integer.parseInt(visitInfo[10]),
				Boolean.valueOf(visitInfo[8]));
	}
	
	/**
	 * adds every prescription name in medicationName to 
	 * Patient p's prescription names.
	 * @param p a patient.
	 * @param symptoms the array of prescription names to be added. 
	 */
	public void populateWithMedicationName(Patient p, String[] medicationName){
		for (String name : medicationName){
			p.getMedRecord().setMedicationName(name);
		}
	}
	
	/**
	 * adds every prescription instruction in medicationInstruction to 
	 * Patient p's prescription instructions.
	 * @param p a patient.
	 * @param medicationInstruction the array of prescription 
	 * instructions to be added. 
	 */
	private void populateWithMedicationInstruction(Patient p,
			String[] medicationInstruction){
		for (String instruction : medicationInstruction){
			p.getMedRecord().setMedicationInstruction(instruction);
		}
	}
	
	/**
	 * adds every symptom in symptoms to Patient p's symptoms.
	 * @param p a patient.
	 * @param symptoms the array of symptoms to be added. 
	 */
	private void populateWithSymptoms(Patient p, String[] symptoms){
		for (String symptom : symptoms){
			p.getMedRecord().updateSymptoms(symptom);
		}
	}
	
	/**
	 * adds every blood pressure in pressures to 
	 * Patient p's pressures.
	 * @param p a patient.
	 * @param pressures the array of pressures to be added.
	 */
	private void populateWithPressures(Patient p, String[] pressures){
		for (String i : pressures){
			String[] pressure = i.split("/");
			if(pressure.length == 1){
				p.getMedRecord().updateBloodPressure((0.0), 0.0);
			}else{
				p.getMedRecord().updateBloodPressure(
						Double.valueOf(pressure[0]), 
						Double.valueOf(pressure[1]));
			}
		}
	}
	
	/**
	 * adds every heart rate in heartRates to 
	 * Patient p's heart rates.
	 * @param p a patient.
	 * @param heartRates the array of heart rates to be added.
	 */
	private void populateWithHeartRates(Patient p, String[] heartRates){
		for (String rate : heartRates){
			Double rateDouble;
			if(rate.equals("")){
				rateDouble = 0.0;
			}else{
				rateDouble = Double.valueOf(rate);
			}
			p.getMedRecord().updateHeartRate(rateDouble);
		}
	}

	/**
	 * adds every temperature in temps to Patient p's temperatures.
	 * @param p a patient.
	 * @param temps the array of temperatures to be added.
	 */
	private void populateWithTemperatures(Patient p, String[] temps){
		
		for (String temp : temps){
			Double tempDouble;
			if(temp.equals("")){
				tempDouble = 0.0;
			}else{
				tempDouble = Double.valueOf(temp);
			}
			p.getMedRecord().updateTemperature(tempDouble);
		}
	}
	
	/**
	 * adds the Patient newPatient whose urgency level (based on 
	 * hospital policy)is urgency and whose seen by doctor status is seenByDr
	 * to this HospitalDatabase's urgency Map based on
	 * the patient's urgency level given that the patient 
	 * has not yet been seen by a doctor. 
	 * @param newPatient the new Patient to be added to the Urgency Map.
	 * @param urgency the urgency level of newPatient.
	 * @param seenByDr the seen by doctor status of newPatient.
	 */
	public void addPatientToUrgencyList(
			Patient newPatient, Integer urgency, boolean seenByDr){

		ArrayList<Patient> patientsOrderedByUrgency = new ArrayList<Patient>();

		// x takes on the possible values for urgency levels.
		for(int x = 0; x < 5; x = x + 1) {
			/* removes newPatient from the map (since the urgency level 
			 * might have changed).
			 */
			if(PatientUrgencyMultiMap.containsKey(x)){
	        	 PatientUrgencyMultiMap.get(x).remove(newPatient);
	        }
	    }
		/*
		 * if newPatient has not yet been seen by a doctor, 
		 * adds him to the correct grouping of patients 
		 * (grouped based on urgency value).
		 */
		if(seenByDr == false){
			if(PatientUrgencyMultiMap.containsKey(urgency)){
				PatientUrgencyMultiMap.get(urgency).add(newPatient);
			}else{
				PatientUrgencyMultiMap.put(urgency, patientsOrderedByUrgency);
				PatientUrgencyMultiMap.get(urgency).add(newPatient);
			}
		}
	}

	/**
	 * returns the list of Patients that have not yet been seen by a doctor 
	 * ordered according to their urgency value (based on the hospital policy)
	 * @return the list of Patients that have not yet been seen by a doctor 
	 * ordered according to their urgency value (based on the hospital policy)
	 */
	public ArrayList<Patient> getCategorizePatientList(){
		ArrayList<Patient> OrderPatientList = new ArrayList<Patient>();
		
		for (int i=4; i >= 0; i--){
			if(this.getPatientUrgencyMultiMap().containsKey(i)){
				OrderPatientList.addAll(
						(this.getPatientUrgencyMultiMap().get(i)));
			}
		}	
		return OrderPatientList;
	}
	
	/**
	 * creates and returns a list describing the medical record of a Patient
	 * managed by this HospitalDatabase whose health card number is 
	 * healthCardNumber.
	 * @param healthCardNumber the health card number of a Patient managed
	 * by this HospitalDatabase.
	 * @return a list describing the medical record of a Patient
	 * managed by this HospitalDatabase whose health card number is 
	 * healthCardNumber.
	 */
	public ArrayList<String> eachPatientHistory(Integer healthCardNumber){
		Patient patient = this.findPatient(healthCardNumber);
		ArrayList<String> patientVitalHistory = new ArrayList<String>();
		patientVitalHistory.add("Patient Name: " + 
				patient.getPatientName());
		patientVitalHistory.add("Temperature: " + 
				patient.getMedRecord().getTemperatureHistory());
		patientVitalHistory.add("Blood Pressure: " + 
				patient.getMedRecord().getBloodPressureHistory());
		patientVitalHistory.add("Heart Rate: " + 
				patient.getMedRecord().getHeartRateHistory());
		patientVitalHistory.add("Symptoms: " + 
				patient.getMedRecord().getSymptomsHistory());
		patientVitalHistory.add("Urgency: " + 
				patient.getVisits().getUrgency());
		patientVitalHistory.add("Medication Name: " + 
				patient.getMedRecord().getMedicationNameHistory());
		patientVitalHistory.add("Medication Instruction: " + 
				patient.getMedRecord().getMedicationInstructionHistory());
		patientVitalHistory.add("Seen By Doctor: " + 
				patient.getVisits().getSeenByDr());
		patientVitalHistory.add("Time Seen By Doctor: " + 
				patient.getVisits().getTimeSeenByDr());
		return patientVitalHistory;
	}
}
