import java.io.Serializable;

public class Familienversicherung extends Versicherung implements Serializable {
/**
 * Konstruktor zum Erzeugen der Familienversicherungen
 * @param name des versicherung
 * @param versicherungsnummer der Versicherung
 */ 
	public Familienversicherung(String name, long versicherungsnummer) {
		super(name, versicherungsnummer);
	}

	@Override
	long calculateCoverage(long cost, int quarter, int previousQuarter) {
		return 0;
	}

}
