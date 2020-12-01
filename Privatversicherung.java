import java.io.Serializable;

public class Privatversicherung extends Versicherung implements Serializable {
/**
 * Konstruktor fuer Privatversicherungen
 * @param name
 * @param versicherungsnummer
 */
	public Privatversicherung(String name, long versicherungsnummer) {
		super(name, versicherungsnummer);
	}

	@Override
	long calculateCoverage(long cost, int quarter, int previousQuarter) {
		return 0;
	}

}
