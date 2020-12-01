
public class Logger {
	/**
	 * ein Instance von Logger wird erzeugt
	 */
	private static Logger ourInstance = new Logger();
/**
 * default logStrategie zum weiteren Auswahl wird gesetzt
 */
	LogStrategie logstrat = LogStrategie.KONSOLE;
	/**
	 * Objekte von zwei unterschiedlichen Strategien werden erzeugt
	 */
	StrategieKonsole konsole = new StrategieKonsole();
	StrategieFile file = new StrategieFile();
/**
 * getter fuer ein Logger
 * @return
 */
	public static Logger getInstance() {
		return ourInstance;
	}
/**
 * Logger default Konstruktor
 */
	private Logger() {
	}
/**
 * Methode zum Loggen
 * @param message Nachricht, die ausgegeben werden muss
 */
	public void log(String message){

		if(logstrat==LogStrategie.KONSOLE) {
			konsole.logStrategie(message);
		} else {
			file.logStrategie(message);
		}
	}
/**
 * Methode zum Auswahl der Strategie
 * @param strat
 */
	public void setStrategie(LogStrategie strat) {
		logstrat=strat;
	}
}

