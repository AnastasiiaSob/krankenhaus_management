import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class JKrankenhaus extends JFrame  {
	
	private static final long serialVersionUID = 1L;
	private JList<Patient> patientenAsJList;
	private JList<Aufenthalte> aufenthaltAsJList;
	private JFrame frame;
	private JTextField entlassungsdatum, aufnahmedatum;
	private JLabel name, patientennummer;
	private JButton entlassenButton, aufnehmenButton;
	private Krankenhaus krankenhaus;
	
/**
 * Konstruktor, der ein JFrame erzeugt. 
 * Zum Konstruktor werden ein JPanel mit Patienten, ein JPanel mit Aufenthalte
 * und ein JMenueBar mit Menueeintraege hinzugefuegt.
 * die Groesse von JFrame wird automatisch zum Inhalt angepasst. 
 * @param krankenhaus Daten vom ausgewaehlten Krankenhaus werden uebergeben
 */
    public JKrankenhaus (Krankenhaus krankenhaus) {
    	this.krankenhaus = krankenhaus;
   
    	frame = new JFrame("Krankenhaus Manager");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	JPanel panel = (JPanel) frame.getContentPane();

    	JMenuBar menu = menue();
    	panel.add(menu,  BorderLayout.PAGE_START);

    	JPanel patientsPanel = listOfPatients(krankenhaus.getPatients());
    	panel.add(patientsPanel, BorderLayout.LINE_START);

    	JPanel aufenthaltePanel = panelOfAufenthalte();
    	panel.add(aufenthaltePanel, BorderLayout.LINE_END);
    	frame.pack();
    	frame.setVisible(true);
    }
    
/**
 * ein JPanel mit ScrollPane mit den Patienten wird erzeugt.
 * ein JLabel wird hinzugefuegt.
 * 
 */
    private JPanel listOfPatients(ArrayList<Patient> patients) {
    	JPanel panelForPatients = new JPanel(new BorderLayout());
    	JLabel heading = new JLabel("Patients: ");
    	panelForPatients.add(heading, BorderLayout.PAGE_START);
    	patientenAsJList  = new JList<Patient>(); 
    	DefaultListModel<Patient> modelWithPatienten = new DefaultListModel<Patient>();
    	for(int i = 0; i<patients.size(); i++) {
    		modelWithPatienten.addElement(patients.get(i));
    	}
    	patientenAsJList.setModel(modelWithPatienten);
    	patientenAsJList.setLayoutOrientation(JList.VERTICAL);
    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setViewportView(patientenAsJList);
    	panelForPatients.add(scrollPane, BorderLayout.PAGE_END);
    	patientenAsJList.addMouseListener(mouseListenerForPatienten);
    	return panelForPatients;
    }
 
    MouseListener mouseListenerForPatienten = new MouseAdapter() {
    	/**
    	 * Als einen Patient mit dem MausKlick ausgewaehlt wird,
    	 * werden seine Aufenthalte angezeigt.
    	 * Wenn er keinen geoeffneten Aufenthalt hat, dann wird das Button "Aufnehmen" aktiviert und "Entlassen" deaktiviert. 
    	 * Wenn er einen aktiven Aufenthalt hat, dann wird das Button "Aufnehmen" deaktiviert, und "Entlassen" aktiviert.
    	 */
    	public void mouseClicked(MouseEvent e) {

    		Patient selectedItem = (Patient) patientenAsJList.getSelectedValue();
    		Aufenthalte currentAufenthalt = null;

    		ArrayList<Aufenthalte> aufenthalteAsArrayList = new ArrayList<Aufenthalte>();
    		for(int i =0; i<krankenhaus.getAufenthalte().size(); i++) {
    			if(selectedItem.getId() == krankenhaus.getAufenthalte().get(i).getPatient().getId()) {
    				aufenthalteAsArrayList.add(krankenhaus.getAufenthalte().get(i));
    				if (krankenhaus.getAufenthalte().get(i).getEntlassungsdatum() == null) {
    					currentAufenthalt = krankenhaus.getAufenthalte().get(i);	
    				} 
    			}
    		}
    		DefaultListModel<Aufenthalte> modelWithAufenthalte = (DefaultListModel<Aufenthalte>) aufenthaltAsJList.getModel();
    		if(modelWithAufenthalte == null)
    		{
    			modelWithAufenthalte = new DefaultListModel<Aufenthalte>();
    		}
    		modelWithAufenthalte.clear();
    		for(int i =0; i<aufenthalteAsArrayList.size();i++) {
    			modelWithAufenthalte.addElement(aufenthalteAsArrayList.get(i));
    		}

    		if(currentAufenthalt != null ) {
    			aufenthaltAsJList.setSelectedValue(currentAufenthalt, true);
    			entlassungsdatum.setText(LocalDate.now()+"");
    			entlassungsdatum.setEditable(true);
    			entlassungsdatum.setEnabled(true);
    			aufnahmedatum.setText(currentAufenthalt.getAufnahmedatum()+"");
    			aufnahmedatum.setEditable(false);
    			aufnahmedatum.setEnabled(false);
    			aufnehmenButton.setEnabled(false);
    			entlassenButton.setEnabled(true);
    		} else {
    			entlassungsdatum.setText("");
    			entlassungsdatum.setEnabled(false);
    			entlassungsdatum.setEditable(false);
    			aufnahmedatum.setText(LocalDate.now()+"");
    			aufnahmedatum.setEditable(true);
    			aufnahmedatum.setEnabled(true);
    			entlassenButton.setEnabled(false);
    			aufnehmenButton.setEnabled(true);
    		}
    		name.setText(selectedItem.getVorname() + " " + selectedItem.getNachname());
    		patientennummer.setText(selectedItem.getId()+""); 
    	}   
    };

    MouseListener mouseListenerForAufenthalte = new MouseAdapter() {
    	/**
    	 * Mit dem MausKlick auf einen der Aufenthalte, 
    	 * werden die Aufnahmedatum und Entlassungsdatum angezeigt.
    	 * Wenn ein Patient keinen geoeffneten Aufenthalt hat, dann wird das Button "Aufnehmen" aktiviert und "Entlassen" deaktiviert. 
    	 * Wenn ein Patient einen aktiven Aufenthalt hat, dann wird das Button "Aufnehmen" deaktiviert, und "Entlassen" aktiviert.
    	 */
    	public void mouseClicked(MouseEvent e) {
    		Aufenthalte selectedItem = (Aufenthalte) aufenthaltAsJList.getSelectedValue();
    		aufnahmedatum.setText(selectedItem.getAufnahmedatum()+"");

    		if(selectedItem.getEntlassungsdatum() == null) {
    			entlassungsdatum.setText(LocalDate.now()+"");
    			entlassungsdatum.setEditable(true);
    			aufnahmedatum.setEditable(false);
    			aufnahmedatum.setEnabled(false);
    			aufnehmenButton.setEnabled(false);
    			entlassenButton.setEnabled(true);
    		} else {
    			entlassungsdatum.setText(selectedItem.getEntlassungsdatum()+" ");
    			entlassungsdatum.setText("");
    			entlassungsdatum.setEnabled(false);
    			entlassungsdatum.setEditable(false);
    			aufnahmedatum.setEditable(true);
    			aufnahmedatum.setEnabled(true);
    			entlassenButton.setEnabled(false);
    			aufnehmenButton.setEnabled(true);
    		}
    	}
	};
   /**
    * die Methode erzeugt einMenuBar mit Ertraege.
    * mit dem MausKlick auf "About" wird ein neues JFrame mit Info erzeugt. 
    * @return JMenuBar als menubar
    */
	static JMenuBar menue() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuPatienten = new JMenu("Patienten");
		menuPatienten.add(new JMenuItem("Anlegen"));
		menuPatienten.add(new JMenuItem("Nach Name suchen"));
		menuPatienten.add(new JMenuItem("Nach Patientennummer suchen"));
		JMenu menuVersicherungen = new JMenu("Krankenversicherungen");
		menuVersicherungen.add(new JMenuItem("Privatversicherung anlegen"));
		menuVersicherungen.add(new JMenuItem("Gesetzliche Versicherung anlegen"));
		menuVersicherungen.add(new JMenuItem("Versicherung anzeigen"));
		JMenu menuAufenthalte = new JMenu("Aufenthalte");
		menuAufenthalte.add(new JMenuItem("Patient aufnehmen"));
		menuAufenthalte.add(new JMenuItem("Patient entlassen"));
		JMenu menuInfo = new JMenu("Info");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == about) {
					JFrame infoFenster = new JFrame("About");
					infoFenster.setSize(300, 200);
					infoFenster.setLocation(100, 200);
					infoFenster.setVisible(true);
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Anastasiia Sobkovskaia, s0569294");
					panel.add(label);
					infoFenster.add(panel);
				}
			}
		});
		menuInfo.add(about);
		menuBar.add(menuPatienten);
		menuBar.add(menuVersicherungen);
		menuBar.add(menuAufenthalte);
		menuBar.add(menuInfo);
		return menuBar;
    }
	/**
	 * in der Methode wird ein JPanel mit Aufenthalte erzeugt und ein JLabel hinzugefuegt.
	 * @return ein JPanel mit Aufenthalte
	 */
    private JPanel panelOfAufenthalte() {
    	JPanel panel = new JPanel(new BorderLayout());

    	JLabel heading = new JLabel("Aufenthalte: ");
    	panel.add(heading, BorderLayout.PAGE_START);

    	aufenthaltAsJList = new JList<Aufenthalte>();
    	aufenthaltAsJList.setModel(new DefaultListModel<Aufenthalte>());
    	aufenthaltAsJList.setLayoutOrientation(JList.VERTICAL);
    	aufenthaltAsJList.addMouseListener(mouseListenerForAufenthalte);
    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setViewportView(aufenthaltAsJList);
    	panel.add(scrollPane, BorderLayout.LINE_START);

    	JPanel aufenthaltdata = createAufenthaltdata();

    	panel.add(aufenthaltdata, BorderLayout.CENTER);
    	return panel;
    }
/**
 * in der Methode wird ein JPanel erzeugt und dazu die Felder mit Patientennamen und Patientennummer hinzugefuegt.
 * @return ein JPanel mit Patientennummer, Namen sowie Aufnahme- und Entlassungsdatum
 */
    private JPanel createAufenthaltdata() {

    	JPanel thispanel = new JPanel();
    	thispanel.setLayout(new GridLayout(0,2));

    	thispanel.add(new JLabel("Name: "));
    	name = new JLabel();
    	thispanel.add(name);

    	thispanel.add(new JLabel("Nummer: "));
    	patientennummer = new JLabel();
    	thispanel.add(patientennummer);

    	thispanel.add(new JLabel("Aufnahmedatum: "));
    	aufnahmedatum = new JTextField();
    	thispanel.add(aufnahmedatum);

    	thispanel.add(new JLabel("Entlassungsdatum: "));
    	entlassungsdatum = new JTextField();
    	thispanel.add(entlassungsdatum);

    	aufnehmenButton = new JButton("AUFNEHMEN");
    	aufnehmenButton.addActionListener(new ActionListener() {
/**
 * ActionListener fuer das Aufnahmebutton
 * Wenn ein Patient keinen geoeffneten Aufenthalt hat, dann wird das Button "Aufnehmen" aktiviert und "Entlassen" deaktiviert. 
 * Wenn ein Patient einen aktiven Aufenthalt hat, dann wird das Button "Aufnehmen" deaktiviert, und "Entlassen" aktiviert.
 */
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (e.getSource() == aufnehmenButton) {
        			aufnehmenButton.setEnabled(false);
        			entlassenButton.setEnabled(true);
        			Aufenthalte e1 = new Aufenthalte(patientenAsJList.getSelectedValue(),LocalDate.parse(aufnahmedatum.getText()));
        			krankenhaus.getAufenthalte().add(e1);
        			aufnahmedatum.setEnabled(false);
        			Patient selectedItem = patientenAsJList.getSelectedValue();
        			ArrayList<Aufenthalte> aufenthalteAsArrayList = new ArrayList<Aufenthalte>();
        			for(int i = 0; i<krankenhaus.getAufenthalte().size(); i++) {
        				if(selectedItem.getId() == krankenhaus.getAufenthalte().get(i).getPatient().getId()) {
        					aufenthalteAsArrayList.add(krankenhaus.getAufenthalte().get(i));
        				}
        			}
        			DefaultListModel<Aufenthalte> model = (DefaultListModel<Aufenthalte>) aufenthaltAsJList.getModel();
        			if(model == null)
        			{
        				model = new DefaultListModel<Aufenthalte>();
        			}
        			model.clear();
        			for(int i =0; i<aufenthalteAsArrayList.size();i++) {
        				model.addElement(aufenthalteAsArrayList.get(i));
        			}
        			aufenthaltAsJList.setSelectedValue(e1, true);
        			entlassungsdatum.setText(LocalDate.now()+"");
        			entlassungsdatum.setEditable(true);
        			entlassungsdatum.setEnabled(true);
        			aufnahmedatum.setText(e1.getAufnahmedatum()+"");
        			aufnahmedatum.setEditable(false);
        			aufnahmedatum.setEnabled(false);
        			aufnehmenButton.setEnabled(false);
        			entlassenButton.setEnabled(true);	
        		}	
        	}
        });
        entlassenButton = new JButton("ENTLASSEN");
        entlassenButton.addActionListener(new ActionListener() {
/**
 * ActionListener fuer das Aufnahmebutton
 * Wenn ein Patient keinen geoeffneten Aufenthalt hat, dann wird das Button "Aufnehmen" aktiviert und "Entlassen" deaktiviert. 
 * Wenn ein Patient einen aktiven Aufenthalt hat, dann wird das Button "Aufnehmen" deaktiviert, und "Entlassen" aktiviert.
 */
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if (e.getSource() == entlassenButton) {
        			aufenthaltAsJList.getSelectedValue().setEntlassungsdatum(LocalDate.parse(entlassungsdatum.getText()));
        			Patient selectedItem = patientenAsJList.getSelectedValue();
        			ArrayList<Aufenthalte> aufenthalteAsArrayList = new ArrayList<Aufenthalte>();
        			for(int i = 0; i<krankenhaus.getAufenthalte().size(); i++) {
        				if(selectedItem.getId() == krankenhaus.getAufenthalte().get(i).getPatient().getId()) {
        					aufenthalteAsArrayList.add(krankenhaus.getAufenthalte().get(i));
        				}
        			} 
        			DefaultListModel<Aufenthalte> modelOfAufenthalte = (DefaultListModel<Aufenthalte>) aufenthaltAsJList.getModel();
        			if(modelOfAufenthalte == null)
        			{
        				modelOfAufenthalte = new DefaultListModel<Aufenthalte>();
        			}
        			modelOfAufenthalte.clear();
        			for(int i =0; i<aufenthalteAsArrayList.size();i++) {
        				modelOfAufenthalte.addElement(aufenthalteAsArrayList.get(i));
        			}
        			entlassenButton.setEnabled(false);
        			aufnehmenButton.setEnabled(true);
        			entlassungsdatum.setText("");
        			entlassungsdatum.setEnabled(false);
        			entlassungsdatum.setEditable(false);
        			aufnahmedatum.setText(LocalDate.now()+"");
        			aufnahmedatum.setEditable(true);
        			aufnahmedatum.setEnabled(true);    
        		} 	
        	}

        });
        thispanel.add(aufnehmenButton);
        thispanel.add(entlassenButton);
        return thispanel;
    }
}