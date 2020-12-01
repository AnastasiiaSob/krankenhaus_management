import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Monitoring implements Runnable {

	private static final long millis = 30*1000;
	Krankenhaus krankenhaus;
/**
 * Konstruktor, der ein Krankenhaus einem Thread uebergibt
 * @param krankenhaus
 */
	public Monitoring(Krankenhaus krankenhaus) {
		this.krankenhaus = krankenhaus;
	}
	/**
	 * Jede 30 Sekunden werden der Message from monitor() ausgegeben
	 * Falls interrupted() Flag gesetzt wird, wird der Thread unterbrochen
	 */
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			monitor();
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				return;
			}	
		}	
	}
/**
 * Methode zur Ausgabe von Anzahl der Aufenthalte auf die Konsole
 */
	private void monitor() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatDateTime = now.format(formatter);
		System.out.println(formatDateTime + " Das Krankenhaus hat gerade "+ krankenhaus.getOffeneAufenthalte().size() + " Aufenthalte von insgesamt "+krankenhaus.getAufenthalte().size()+" Aufenthalte.");				
	}
}
