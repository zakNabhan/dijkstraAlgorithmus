package Alternative;

 

 //In dee klasse definieren wir eine Klasse für die Einträge der Prioritätswarteschlange
 
public class Kreuzung implements Comparable<Kreuzung> {

	  double entfernung;
	String id;

	public double minentfernung = Double.MAX_VALUE;

	public Kreuzung(double entfernung, String id) {
		this.entfernung = entfernung;
		this.id = id;

	}

	@Override
	public int compareTo(Kreuzung rest) {

		// vergleichsoperation wegentfernung mit anderen router
		return Double.compare(minentfernung, rest.minentfernung);

	}}
