import java.util.ArrayList;

public class Nod {

	String cuvant;
	ArrayList<Nod> frunze = new ArrayList<Nod>();
	ArrayList<Integer> pozitii = new ArrayList<Integer>();

	Nod(String cuvant) {
		this.cuvant = cuvant;

	}

	
	/**
	 * Functie care adauga un cuvant in fii nodului in care ma aflu
	 */
	void addWord(Nod n, String word) {
		int i = 0;
		while (i < 26) {

			if (n.frunze.get(i).cuvant.equals("%")) {
				n.frunze.get(i).cuvant = word;
				return;
			}
			i++;
		}
	}

	/**
	 *  Functie care adauga 26 de fii nodului in care ma aflu
	 */
	void initialize(Nod n) {
		if (n.frunze.size() < 26) {
			for (int i = 0; i < 26; i++) {
				Nod ned = new Nod("%");

				n.frunze.add(i, ned);
			}

		}
	}

	

	private static final int indent = 5;
	/**
	 *  Functie de care m-am folosit pentru a afisa arborele
	 */
	String printtree(Nod n, int increment) {
		String s = "  ";
		String inc = " ";
		for (int i = 0; i < increment; i++) {
			inc += " ";
		}
		s = inc + n.cuvant + " " + n.pozitii.toString();
		for (Nod fru : n.frunze) {
			if (fru.cuvant.equals("%") != true)
				s += "\n" + fru.printtree(fru, increment + indent);
		}
		return s;
	}

	/**
	 *  Functie care returneaza toti indecsii nodului
	 *  si fiilor fiilor fiilor etc. acestuia
	 */
	 

	public ArrayList<Integer> recursafisare(Nod n) {

		ArrayList<Integer> total = new ArrayList<Integer>();
		return afisare(n, total);

	}

	ArrayList<Integer> afisare(Nod n, ArrayList<Integer> total) {

		if (!n.pozitii.isEmpty()) {
			total.addAll(n.pozitii);
		}

		for (Nod fru : n.frunze) {
			total = fru.afisare(fru, total);
		}

		return total;
	}

	/**
	 *  Returneasa prefixul comun al 2 Stringuri
	 */
	public String prefix(String a, String b) {
		int minLength = Math.min(a.length(), b.length());
		for (int i = 0; i < minLength; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return a.substring(0, i);
			}
		}
		return a.substring(0, minLength);
	}

	/**
	 * Returneaza indexul unui fiu care are prefix comun cu, cuvantul cautat
	 * returneaza -1 daca nu s-a gasit un astfel de cuvant
	 */
	public int cautafiu(Nod n, String word) {

		for (Nod meu : n.frunze) {

			if ((meu.prefix(meu.cuvant, word).equals("") == false)) {
				return n.frunze.indexOf(meu);
			}

		}
		return -1;
	}

}