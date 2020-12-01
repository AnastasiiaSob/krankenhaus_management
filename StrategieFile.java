import java.io.FileWriter;
import java.io.IOException;

public class StrategieFile implements Strategie{
/**
 * new FileWriter wird erzeugt
 */
	FileWriter csvWriter;
	public StrategieFile() {

	}
/**
 * Methode zum Schreiben der Nachricht in File
 */
	@Override
	public void logStrategie(String message) {
		try {
			
			csvWriter = new FileWriter("mydata.txt");
			csvWriter.write(message);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
