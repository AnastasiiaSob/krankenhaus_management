import java.io.Serializable;
import java.util.ArrayList;

public class Patient implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Anrede anrede;
	private String vorname;
	private String nachname;
	private String geburtsdatum;
	private String email;
	private long telefon;
	private ArrayList<Versicherung> kv = new ArrayList<Versicherung>();
	/**
	 * Patient Konstruktor
	 * @param id Patientennummer
	 * @param anrede Anrede
	 * @param vorname Vorname
	 * @param nachname Nachname
	 * @param geburtsdatum Geburtsdatum
	 * @param email Email
	 * @param telefon Telefonnummer
	 */
	public Patient(int id, Anrede anrede, String vorname, String nachname, String geburtsdatum, String email, long telefon) {
		this.setId(id);
		this.setAnrede(anrede);
		this.setVorname(vorname);
		this.setNachname(nachname);
		this.geburtsdatum = geburtsdatum;
		this.email = email;
		this.telefon = telefon;

	}
	/**
	 * 
	 * @return patientennummer
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id patientennummer to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the anrede
	 */
	public Anrede getAnrede() {
		return anrede;
	}
	/**
	 * @param anrede the anrede to set
	 */
	public void setAnrede(Anrede anrede) {
		this.anrede = anrede;
	}
	/**
	 * @return the vorname
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * @param nachname the nachname to set
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * 
	 * @return krankenversicherungen als ArrayList
	 */
	public ArrayList<Versicherung> getKv() {
		return kv;
	}
	/**
	 * 
	 * @param krankenversicherungen als ArrayList to set
	 */
	public void setKv(ArrayList<Versicherung> kv) {
		this.kv = kv;
	}
	/**
	 * eine Versicherung wird dem Patient zugewiesen
	 * @param versicherung
	 */
	public void addVersicherung(Versicherung versicherung) {
		kv.add(versicherung);	
	}
	/**
	 * Adresse wird dem Patient zugewiesen
	 * @param adresse
	 * @return
	 */
	public Adresse hasAdresse(Adresse adresse) {
		return adresse;
	}
	/**
	 * Ausgabe von Vorname und Nachname des Patients
	 */
	public String toString() {
		return vorname+" "+nachname;
	}
}
