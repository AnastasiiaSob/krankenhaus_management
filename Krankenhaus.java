import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Krankenhaus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Adresse adresse;
	private ArrayList<Patient> patients = new ArrayList<Patient>();
	private ArrayList<Aufenthalte> aufenthalte = new ArrayList<Aufenthalte>();
	private Abrechnungsstelle abrechnungsstelle = new Abrechnungsstelle();
	private Qualitaetsmanagementstelle qualitaetsmanagementstelle = new Qualitaetsmanagementstelle();
/**
 * ein Konstruktor zur Erstellung eines Krankenhauses
 * @param name des Krankenhauses
 * @param adresse des Krankenhauses
 */
	public Krankenhaus(String name, Adresse adresse) {
		this.name = name;
		this.adresse = adresse; 
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}
	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	/**
	 * @return the patients
	 */
	public ArrayList<Patient> getPatients() {
		return patients;
	}
	/**
	 * @param patients the patients to set
	 */
	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}
	/**
	 * Patient wird im Krankenhaus eingeschrieben
	 * @param patient
	 * @return 
	 */
	public boolean addPatient(Patient patient) {
		patients.add(patient);
		return true;
	}
	/**
	 * @return the aufenthalte
	 */
	public ArrayList<Aufenthalte> getAufenthalte() {
		return aufenthalte;
	}
	/**
	 * die Methode rechnet alle Aufenthalte, die offen sind(d.h. ohne Entlassungsdatum)
	 * @return ein ArrayList mit offenen Aufenthalten
	 */
	public ArrayList<Aufenthalte> getOffeneAufenthalte() {
		ArrayList<Aufenthalte> offeneAufenthalte = new ArrayList<>();
	
		for (int i =0; i<aufenthalte.size();i++) {
			if (aufenthalte.get(i).getEntlassungsdatum()==null) {
				offeneAufenthalte.add(aufenthalte.get(i));
			}
		}
		return offeneAufenthalte;
	}
	
	/**
	 * @param aufenthalte the aufenthalte to set
	 */
	public void setAufenthalte(ArrayList<Aufenthalte> aufenthalte) {
		this.aufenthalte = aufenthalte;
	}
	
	/**
	 * Die Patienten werden im Krankenhaus aufgenommen
	 * @param patient Patient, der aufgenommen wird
	 * @param date Datum des Aufnahmes
	 * @return ein neuer Aufenthalt, den ein Patient hat 
	 */
	public Aufenthalte patientAufnehmen(Patient patient, LocalDate date) {
		Aufenthalte aufenthalteNeu = new Aufenthalte(patient, date);
		aufenthalteNeu.addObserver(abrechnungsstelle);
		aufenthalteNeu.addObserver(qualitaetsmanagementstelle);
		aufenthalte.add(aufenthalteNeu);

		return aufenthalteNeu;
	}
	/**
	 * Patient wird aus dem Krankenhaus entlassen
	 * wenn der Augenthaltdauer mehr als 2 Tage ist, wird es geloggt
	 * @param patnum patientennummer
	 * @param entlassungsdatum 
	 */
	public  void patientEntlassen(int patnum, LocalDate entlassungsdatum) {
		int i = 0;
		for (;i<aufenthalte.size();i++) {
			if(aufenthalte.get(i).getPatient().getId()==patnum) break;
		}
		Aufenthalte aufenthalteNeu;
		try {
			aufenthalteNeu = aufenthalte.get(i);
			aufenthalteNeu.setDauer(Period.between(aufenthalteNeu.getAufnahmedatum(),entlassungsdatum).getDays());
		} catch (IndexOutOfBoundsException e) {
			aufenthalteNeu = null;
		}
		try {
			if (aufenthalteNeu.getDauer()>=2) {
				Logger.getInstance().log(" LOG_NACHRICHT: Patient Entlassen: aufgenommen am " + aufenthalteNeu.getPatient().getNachname()+" entlassen am "+aufenthalteNeu.getAufnahmedatum()+" "+entlassungsdatum);
				aufenthalteNeu.notifyInstitutions();
			}
		}catch(NullPointerException e1) {
			System.out.println("Exception go along");
		}
	}
	/**
	 * die Daten werden von einem CSV-File importiert
	 * @param a der CSV-File, aus denen die Daten importiert werden sollen
	 */
	public void importCSV(File a) {
		String line = "";
		String csvSplitBy = ";";
		try(BufferedReader br = new BufferedReader(new FileReader(a))){
			while ((line = br.readLine())!=null) {
				String [] data = line.split(csvSplitBy);
				for (int i =0; i<data.length; i++)
					System.out.println(data+" " );	
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * die Daten werden in einen CSV-File exportiert
	 * @param a der CSV-File, in denen die Daten exportiert werden sollen
	 * @throws IOException wenn keinen File gefunden werden kann
	 */
	public void exportCSV(File a) throws IOException {

		try (PrintWriter writer = new PrintWriter( a)){
			StringBuilder sb = new StringBuilder(  );
			Collections.sort(patients, new Comparator<Patient>() {
				public int compare(Patient o1, Patient o2) {
					return o1.getVorname().compareTo(o2.getVorname());
				}
			} );
			sb.append( "Patentennummer" );
			sb.append( "; " );
			sb.append( "Name" );
			sb.append( "; " );
			sb.append( "AnzahlKV" );
			sb.append( "; " );
			sb.append( "HauptKV" );
			sb.append( "\n" );
			for (int j = 0; j<patients.size(); j++) {
				sb.append( patients.get(j).getId() );
				sb.append( "; " );
				sb.append( patients.get(j).getNachname() );
				sb.append( "; " );
				sb.append( patients.get(j).getKv().size());
				sb.append( "; " );
				try{
					sb.append(patients.get(j).getKv().get(0) );
				}
				catch (IndexOutOfBoundsException e){
					sb.append( "-" );
				}
				sb.append( "\n" );
			}
			writer.write( sb.toString() );
			System.out.println(patients.size()+" Datensätze in die Datei "+a+" exportiert");
		} 
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
}

