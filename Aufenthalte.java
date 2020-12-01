import java.io.Serializable;
import java.time.LocalDate;
import java.util.Observable;

public class Aufenthalte extends Observable implements Serializable{
	private Patient patient;
	private LocalDate aufnahmedatum;
	private LocalDate entlassungsdatum;
	private int dauer;
	private Strategie LogStrategie;
/**
 * Konstruktor fuer Aufenthalte
 * @param patient ein Patient wird aufgenommen
 * @param aufnahmedatum wann ist der Patient aufgenommen
 */
	public Aufenthalte(Patient patient, LocalDate aufnahmedatum) {
		this.patient = patient;
		this.aufnahmedatum = aufnahmedatum;
	}
	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}
	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	/**
	 * @return the aufnahmedatum
	 */
	public LocalDate getAufnahmedatum() {
		return aufnahmedatum;
	}
	/**
	 * @param aufnahmedatum the aufnahmedatum to set
	 */
	public void setAufnahmedatum(LocalDate aufnahmedatum) {
		this.aufnahmedatum = aufnahmedatum;
	}
	/**
	 * @return the entlassungsdatum
	 */
	public LocalDate getEntlassungsdatum() {
		return entlassungsdatum;
	}
	/**
	 * @param entlassungsdatum the entlassungsdatum to set
	 */
	public void setEntlassungsdatum(LocalDate entlassungsdatum) {
		this.entlassungsdatum = entlassungsdatum;
	}
	/**
	 * @return the dauer
	 */
	public int getDauer() {
		return dauer;
	}
	/**
	 * @param dauer the dauer to set
	 */
	public void setDauer(int dauer) {
		this.dauer = dauer;
	}
	/**
	 * @return the logStrategie
	 */
	public Strategie getLogStrategie() {
		return LogStrategie;
	}
	/**
	 * @param logStrategie the logStrategie to set
	 */
	public void setLogStrategie(Strategie logStrategie) {
		LogStrategie = logStrategie;
	}
	/**
	 * Methode zum Loggen mit Auswahl der Strategie
	 * @param message
	 */
	public void log(String message) {
		this.LogStrategie.logStrategie(message);
	}
	/**
	 * die entsprechende Stellen werden ueber Veraenderungen benachrichtigt
	 */
	public void notifyInstitutions() {
		setChanged();
		notifyObservers();
	}	
	
	@Override
	public String toString() {
		if(entlassungsdatum==null) 
		{
			return aufnahmedatum+" ";
		}
		return aufnahmedatum+" "+entlassungsdatum;
	}
}
