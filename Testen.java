import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class Testen {
	Adresse adressForTest = new Adresse("Adress", "Strasse", "Berlin", 1234);
	Krankenhaus krankenhausForTest = new Krankenhaus("Krankenhaus Berlin",adressForTest );
	Patient patientForTest = new Patient(2, Anrede.Herr, "Marc", "Mueller", "13-02-1997", "max@mail.de", 01234567);
	Familienversicherung familienversicherungForTest = new Familienversicherung("VersicherungTest", 987654);
	
	@Test 
	 void testAufnehmen() {
		LocalDate date = LocalDate.now();
		Aufenthalte aufenthalt = krankenhausForTest.patientAufnehmen(patientForTest, date);
		assertEquals(0, aufenthalt.getPatient().getNachname().compareTo("Mueller"));	
	}
	@Test
	void testEntlassen() {
		int patientNr = 2;
		LocalDate date = LocalDate.now();
		krankenhausForTest.patientEntlassen(patientNr, date);
		assertEquals(patientNr, patientForTest.getId());
	}
	@Test
	 void testCSVexport() throws IOException {
		krankenhausForTest.addPatient(patientForTest);
		File a = new File ("krankenhaus.csv");
		krankenhausForTest.exportCSV(a);
		assertTrue(a.exists());
	} 
	@Test
	void testGezKrankenversicherung() {
		patientForTest.addVersicherung(familienversicherungForTest);
		assertEquals(patientForTest.getKv().get(0), familienversicherungForTest);	
	}
	@Test
	void testCSVImport() {
		krankenhausForTest.addPatient(patientForTest);
		File a = new File ("krankenhaus.csv");
		krankenhausForTest.importCSV(a);
		assertTrue(a.exists());
	}
}
