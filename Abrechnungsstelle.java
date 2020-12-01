import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Abrechnungsstelle implements Observer {
	/**
	 * ein ArrayList fuer Patienten die im Krankenhaus laenger als 2 Tage bleiben
	 */
	private ArrayList <Aufenthalte> aufenthaltLaengerAls2Tage;
	
	/**
	 * @return the aufenthaltLaengerAls2Tage
	 */
	public ArrayList <Aufenthalte> getAufenthaltLaengerAls2Tage() {
		return aufenthaltLaengerAls2Tage;
	}

	/**
	 * @param aufenthaltLaengerAls2Tage the aufenthaltLaengerAls2Tage to set
	 */
	public void setAufenthaltLaengerAls2Tage(ArrayList <Aufenthalte> aufenthaltLaengerAls2Tage) {
		this.aufenthaltLaengerAls2Tage = aufenthaltLaengerAls2Tage;
	}

	@Override
	public void update(Observable o, Object arg) {
		Aufenthalte myAufenthalt = (Aufenthalte) o;
		System.out.println("Abrechnungsstelle hat die Information erhalten, dass ein "+myAufenthalt.getDauer() +"-Tage Aufenthalt für den Patient "+myAufenthalt.getPatient()+" mit der Patientennummer "+myAufenthalt.getPatient().getId()+" stattgefunden hat.");
		aufenthaltLaengerAls2Tage = new ArrayList<Aufenthalte>();
		aufenthaltLaengerAls2Tage.add(myAufenthalt);
	}
}
