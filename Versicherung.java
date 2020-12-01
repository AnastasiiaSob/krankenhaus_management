import java.io.Serializable;

public abstract class Versicherung implements Serializable {
private String name;
private long versicherungsnummer;
/**
 * Konstruktor fuer Versicherungen
 * @param name
 * @param versicherungsnummer
 */
public Versicherung(String name, long versicherungsnummer) {
	this.name=name;
	this.versicherungsnummer = versicherungsnummer;
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
 * @return the versicherungsnummer
 */
public long getVersicherungsnummer() {
	return versicherungsnummer;
}
/**
 * @param versicherungsnummer the versicherungsnummer to set
 */
public void setVersicherungsnummer(long versicherungsnummer) {
	this.versicherungsnummer = versicherungsnummer;
}
abstract long calculateCoverage(long cost, int quarter, int previousQuarter);

public String toString() {
	return name+" "+versicherungsnummer;
	
}
}
