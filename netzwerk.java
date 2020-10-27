package Alternative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
import java.util.Map.Entry;

public class netzwerk {
	private static String[] knotenid;
	private static String[] knotenentfernung;
	private static HashMap<String, ArrayList<Verbingung>> Ablaufplanung = new HashMap<String, ArrayList<Verbingung>>();
	static Scanner as = new Scanner(System.in);

	public static void main(String[] args) {
		/**Dieser Abschnitt des Codes wird verwendet, um die Knoten-Namen von der Konsole zu lesen
          * Benutzer muss sechs Knoten Namen (Komma getrennt) eingeben
         */
		do {
			System.out.println(
					"Bitte geben Sie die Knoten(durch Komma getrennt ) ein : Maximal 6 Knoten >> Zum Beispiel a,b,f,d,");

			knotenid = as.next().split(",");
			if (knotenid.length != 6) {
				System.out.println("Fehler :einegebene Knoten sind nicht 6, Bitte versuchen noch mal !..\n\n");
			}
		} while (knotenid.length != 6);

		füllungDerAblaufplanung();
	}
    // Dieser Abschnitt des Codes wird verwendet, um die Ablaufplanung basierend auf Knotennamen zu füllen
	private static void füllungDerAblaufplanung() {
 		for (String name : knotenid) {
          Ablaufplanung.put(name, new ArrayList<Verbingung>());
		}
		do {
			System.out.println("**** Bitte definieren Sie die Entfernung zwischen Knoten ****");
			System.out.println("*** Folge diesem Format  zum beispiel=>    a,b,2  *****");
			knotenentfernung = as.next().split(",");
               if (knotenentfernung.length != 3) {
				System.out.println("** Fehler : falsches format ist eingegeben, Noch mal versuchen einzugeben!..\n");
				System.out.println("**** Folge diesem Format zu beispiel =>     a,b,0.23 ****");
				continue;
			} else {
      //Fügen Sie den Knotenabstand in der Ablaufplanung hinzu (HashMap data Structure)
          Ablaufplanung.get(knotenentfernung[0]).add(new Verbingung(knotenentfernung[1], Integer.parseInt(knotenentfernung[2])));
			}
		} while (kontrolle());

		String startknoto = as.nextLine();
		System.out.println("Bitte geben Sie den Startknoten ein: ");
		startknoto = as.nextLine();
		System.out.println("Bitte geben Sie ZielKKnoten ein: ");
		String endknot = as.nextLine();

		System.out.println(
				"\nDas Programm ist beendet und laut eingegebene Knoten und Länge wurde kürtzen Weg berechnet\n");

		dieBerechnungDesKrzestenWeges(startknoto, endknot);

	}

	private static boolean kontrolle() {
		// TODO Auto-generated method stub
		Scanner b = new Scanner(System.in);
		System.out.print("Möchten Sie weiter hinzufügen oder Programm beenden?? Ja/Nein:");
		if (b.nextLine().equals("Ja")) { //Falls ja weitereingebn sonst wird abgeborchen und die eingegebene Knoten gerechnet

			return true;

		} else {
			return false;}}
	
      private static void dieBerechnungDesKrzestenWeges(String start, String ziel) {
 
		final double infinity = Double.POSITIVE_INFINITY; 
		HashMap<String, String> knotenspeicher = new HashMap<String, String>();
		HashMap<String, Double> entfernung = new HashMap<String, Double>();

        //Definieren Sie die Knoten in HashMap-Daten Struktur
		 // Initialisiere den entfernung zwischen Knoten
	       //  Unendlichkeit als Anfangswert hinzugefügt
         for (String knotepunkt : Ablaufplanung.keySet()) {
        	 knotenspeicher.put(knotepunkt, knotepunkt);
			entfernung.put(knotepunkt, infinity);
		}
         
        //Zuweisen von 0,00 für den Startknoten
         entfernung.put(start, 0.00);
        //elemente fügen in die Prioritätswarteschlange knotenlsite ein, In den Zeilen 100 und 101 initialisieren 
 		//wir eine Prioritätswarteschlange und fügen das Tupel (dist [Quelle], Quelle) ein, da wir mit dem Quellenknoten beginnen.
		PriorityQueue<Kreuzung> kreuzungsliste = new PriorityQueue<Kreuzung>();
		kreuzungsliste.add(new Kreuzung(entfernung.get(start), start));
	/*	  Die Zeilen 104 bis 117 implementieren die Wiederholungsschleife. In Zeile 114 versuchen wir den nächsten Knoten mit dem kleinsten Abstandswert zu finden,
indem wir ein Element aus der Prioritätswarteschlange mit der Poll-Methode extrahieren.
 		*/
		while (!kreuzungsliste.isEmpty()) {//solange die knotenliste noch knoten hat
			Kreuzung u = kreuzungsliste.poll();//alle andere Knoten werden registriert durch u
			if (u.entfernung == entfernung.get(u.id)) {
				for (Verbingung v : Ablaufplanung.get(u.id)) {
					double w_uv = v.gewicht;
					 //ignoriert automatisch Kanten, die zu Knoten v innerhalb von u führen
                     //weil dist [u_id] + w_uv <dist [v_id]
                    //kann niemals wahr sein, weil dist [v_id]
                    //ist der minentfernung für v, sobald es zu S hinzugefügt wurde
					double alt = entfernung.get(u.id) + w_uv;
					if (alt < entfernung.get(v.id)) {
						entfernung.put(v.id, alt);
						kreuzungsliste.add(new Kreuzung(entfernung.get(v.id), v.id));
						knotenspeicher.put(v.id, u.id);}}
			}
		}
	      // Wenn es keinen kürzesten Weg gibt, müssen wir Nachricht zeigen//oder prüfen wir, ob ein Pfad gefunden wurde.
 		if (entfernung.get(ziel).equals(infinity)) {
 			System.out.println("kein Weg zwischen  " + start + " und " + ziel);
		}
 		else {
 				/*
 			 * In den Zeilen 133 bis 144 rekonstruieren wir den kürzesten Pfad mit Hilfe des Vorgängerwörterbuchs.
             Dies geschieht insbesondere in den Zeilen 136 bis 138, 
             wo wir dem kürzesten Weg vom Ziel zur Quelle folgen.
            Wir erkennen die Quelle, indem wir überprüfen, ob pred [node] == node
 			*/
 			
 	         // Wenn wir einen kürzesten Weg haben
			Stack<String> st = new Stack<String>();//Die Stack-Klasse repräsentiert einen LIFO-Stapel (LIFO = last-in-first-out) von Objekten
			StringBuffer sb = new StringBuffer();//erstellt einen leeren Zeichenfolgenpuffer mit der Anfangskapazität von 16.
			String knotepunkt = ziel;
			while (!knotenspeicher.get(knotepunkt).equals(knotepunkt)) {
				st.push(knotepunkt);
				knotepunkt = knotenspeicher.get(knotepunkt);
			}
			st.push(knotepunkt);
			while (!st.isEmpty()) {
				sb.append(st.pop() + " ");}
 			 

			System.out.println("Shortest path from  :" + start + "  bis " + ziel + "  ist  :   " + sb.toString().trim());
			System.out.println("Entfernung von   " + start + "  bis  " + ziel + " ist     :" + entfernung.get(ziel));
		}
		System.out.println(); //hier die Ausgabe der Entfernung für jede gerechnete Knote 
		//mit der entrySet Mehtode gibt eine Auflistungsanssicht der gerechneten Knoten mit Längen zurück
		for (Entry<String, Double> eingabe1 : entfernung.entrySet()) {
			 
			System.out.println("Die Entfernung zu '" +  eingabe1.getKey()+ "' is: " +  eingabe1.getValue());
		}
		System.out.println(); //Heir wird die Vorgänger ausgegeben
		for (Entry<String, String> eingabe2 : knotenspeicher.entrySet()) {
			System.out.println("Vorgänger  von  " + eingabe2.getKey() + "|   ist | " + eingabe2.getValue());
		}

	}

}
