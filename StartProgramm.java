import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Scanner;

public class StartProgramm {

	public static void main(String[] args) throws IOException {

		Adresse adresseKrankenhaus = new Adresse("Treskowallee", "6", "Berlin", 12345);
		Krankenhaus helios = new Krankenhaus("Helios", adresseKrankenhaus );
		Thread monitor = new Thread(new Monitoring(helios));
		Patient p1 = new Patient(1, Anrede.Herr, "Max", "Mueller", "13-02-1997", "max@mail.de", 01234567 );
		Patient p2 = new Patient(2, Anrede.Frau, "Anna", "Klein", "09-08-1996", "anna@mail.de", 4737362 );
		Patient p3 = new Patient(3, Anrede.Herr, "Nikos", "Mueller", "13-12-1997", "nikos@mail.de", 0111111 );
		Patient p4 = new Patient(4, Anrede.Frau, "Karina", "Schwarz", "20-02-1988", "karina@mail.de", 2093831 );
		Patient p5 = new Patient(5, Anrede.Herr, "Friedrich", "Klaus", "13-11-2000", "klaus@mail.de", 2920223 );
		Privatversicherung pv1 = new Privatversicherung("AOK", 123456);
		Familienversicherung fv1 = new Familienversicherung("Beamer", 98765);
		Privatversicherung pv2 = new Privatversicherung("DAK", 432221);
		Familienversicherung fv2 = new Familienversicherung("TK", 233445);
		Privatversicherung pv3 = new Privatversicherung("AOK", 123456);
		Familienversicherung fv3 = new Familienversicherung("Beamer", 98765);
		p1.addVersicherung(pv1);
		p1.addVersicherung(fv1);
		p2.addVersicherung(pv2);
		p4.addVersicherung(pv3);
		p5.addVersicherung(fv2);
		p5.addVersicherung(fv3);
		Adresse adressePatient1 = new Adresse("Linkoln", "Strasse,6", "Berlin", 23455);
		p1.hasAdresse(adressePatient1);
		helios.addPatient(p1);
		helios.addPatient(p2);
		helios.addPatient(p3);
		helios.addPatient(p4);
		helios.addPatient(p5);
		System.out.println(helios.getPatients().toString()+" these are patients");

		if(args.length > 0 && args[0].equals("GUI")) {
			JKrankenhaus krankenhausFenster = new JKrankenhaus(helios);
		}
		int input;
		Scanner sc = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.println("(01) Patient anlegen");
			System.out.println("(02) Gesetzliche Versicherung anlegen und Patientennummer zuordnen");
			System.out.println("(03) Privatversicherung anlegen und Patientennummer zuordnen");
			System.out.println("(04) Patient mit Versicherungen anzeigen (Auswahl durch Patientennummer)");
			System.out.println("(05) Patient mit Versicherungen anzeigen (Auswahl durch Name)");
			System.out.println("(06) Versicherung anzeigen (Auswahl durch KVN)");
			System.out.println("(07) Alle Patienten sortiert nach aufsteigender Patientennummer anzeigen");
			System.out.println("(08) Alle Patienten sortiert nach aufsteigendem Nachnamen");
			System.out.println("(09) Alle Krankenversicherungen unsortiert anzeigen");
			System.out.println("(10) Daten Export");
			System.out.println("(11) Daten Import");
			System.out.println("(12) Patienten nach Namen sortiert als CSV-Datei exportieren");
			System.out.println("(13) Patient aufnehmen");
			System.out.println("(14) Patient entlassen");
			System.out.println("(15) Protokoll-Strategie wählen");
			System.out.println("(16) GUI starten");
			System.out.println("(17) Monitoring aktivieren");
			System.out.println("(18) Monitoring deaktivieren");
			System.out.println("(19) Beenden");
			input = sc.nextInt();
			switch(input) {
			case 10: try{
				FileOutputStream fos = new FileOutputStream( "krankenhaus.csv" );
				ObjectOutputStream oos = new ObjectOutputStream( fos );
				oos.writeObject( helios.getPatients() );
				for (int i =0; i<helios.getPatients().size();i++)
					oos.writeObject(helios.getPatients().get(i).getKv());
				oos.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			break;
			case 11:
				System.out.println("Bitte den Pfad eingeben in Format 'Name.csv': ");
				String c = reader.readLine();
				File d = new File(c);
				helios.importCSV(d);
				break;
			case 12:
				System.out.println("Bitte den Pfad eingeben in Format 'Name.csv': ");
				String a = reader.readLine();
				File b = new File(a);
				helios.exportCSV(b);
				break;
			case 13:
				System.out.println("Please enter the patient ID:");
				int patientnumber = sc.nextInt();
				System.out.println("Please enter date: ");
				String mydate = reader.readLine();
				LocalDate thisdate; 
				if(!mydate.isEmpty()) {
					thisdate = LocalDate.parse(mydate);
				} else {
					thisdate = LocalDate.now();
				}
				int i=0;
				for(;i<helios.getPatients().size();i++) {
					if(patientnumber == helios.getPatients().get(i).getId()) {
						helios.patientAufnehmen(helios.getPatients().get(i), thisdate);
						System.out.println("Patient "+helios.getPatients().get(i).getNachname()+" wurde am "+thisdate+" aufgenommen.");
					} 
				}
				break;
			case 14: 
				System.out.println("Please enter the patient ID:");
				int patientnumber1 = sc.nextInt();
				System.out.println("Please enter date: ");
				String mydate1 = reader.readLine();
				LocalDate thisdate1; 
				if(!mydate1.isEmpty()) {
					thisdate1 = LocalDate.parse(mydate1);
				} else {
					thisdate1 = LocalDate.now();
				}
				int j=0;
				for(;j<helios.getPatients().size();j++) {
					if(patientnumber1 == helios.getPatients().get(j).getId()) {
						helios.patientEntlassen(patientnumber1, thisdate1);
						System.out.println("Patient "+helios.getPatients().get(j).getNachname()+" wurde am "+thisdate1+" entlassen.");
					} 
				}
				break;
			case 15: 
				System.out.println("Auf Konsole zeigen oder in der File schreiben? KONSOLE/FILE ");
				String myinput = " ";
				myinput = reader.readLine();
				Logger.getInstance().setStrategie(LogStrategie.valueOf(myinput));
				break;
			case 16:
				JKrankenhaus jKrankenhaus = new JKrankenhaus(helios);
				break;
			case 17:
				monitor.start();
				break;
			case 18:
				monitor.interrupt();
				System.out.println("Is interrupted");
				break;
			case 19:
				System.out.println("Das Programm ist beendet!");
			}
		} while (input!=19);
		sc.close();
	}	
}
