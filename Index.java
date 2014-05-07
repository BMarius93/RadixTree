import java.util.ArrayList;

public class Index {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err
					.println("Usage: java -cp <classpath> Index <text file> <word file>");
			System.exit(1);
		}

		String word;
		Nod root = new Nod("#");
		Nod aux;
		Nod zero = new Nod("#");
		aux = root;
		aux.initialize(aux);

		FileParser textFile = new FileParser(args[0]);
		textFile.open();

		/**
		 * CONSTRUIREA ARBORELUI am luat mai multe cazuri si in functie de ele,
		 * 
		 * am adaugat sau modificat noduri in arbore pozitie reprezinta pozitia
		 * cuvantului in text
		 */
		int pozitie = 0;
		while ((word = textFile.getNextWord()) != null) {

			boolean gasit = false;
			while (gasit == false) {

				if (aux.cautafiu(aux, word) < 0) {

					aux.initialize(aux);
					aux.addWord(aux, word);
					aux.frunze.get(aux.cautafiu(aux, word)).pozitii
							.add(pozitie);
					break;

				}

				if ((aux.prefix(aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
						word).length() == aux.frunze.get(aux
						.cautafiu(aux, word)).cuvant.length())
						&& (aux.frunze.get(aux.cautafiu(aux, word)).cuvant
								.length() < word.length())) {
					String copilu = aux.prefix(
							aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
							word);
					word = (String) word.subSequence(aux.frunze.get(aux
							.cautafiu(aux, word)).cuvant.length(), word
							.length());
					aux = aux.frunze.get(aux.cautafiu(aux, copilu));

					aux.initialize(aux);

				}

				if (aux.cautafiu(aux, word) >= 0) {
					if (aux.prefix(
							aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
							word).length() < word.length()
							&& (aux.prefix(
									aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
									word).length() < aux.frunze.get(aux
									.cautafiu(aux, word)).cuvant.length())) {

						aux = aux.frunze.get(aux.cautafiu(aux, word));

						String prefix = aux.prefix(word, aux.cuvant);

						String fiu = (String) aux.cuvant.subSequence(
								prefix.length(), aux.cuvant.length());
						aux.cuvant = prefix;

						String fiu2 = (String) word.subSequence(
								prefix.length(), word.length());

						aux.initialize(aux);

						ArrayList<Nod> retinut = new ArrayList<Nod>();
						for (Nod meu : aux.frunze) {
							if (meu.cuvant.equals("%") == false) {

								retinut.add(meu);
							}
						}

						aux.addWord(aux, fiu);
						aux.addWord(aux, fiu2);

						aux.frunze.get(aux.cautafiu(aux, fiu)).pozitii
								.addAll(aux.pozitii);
						for (Nod meu : aux.frunze) {

							if (meu.cuvant.equals(fiu2)) {
								meu.pozitii.add(pozitie);
							}

						}

						aux.pozitii.clear();

						for (int i = 0; i < retinut.size(); i++) {
							aux.frunze.remove(retinut.get(i));
						}

						aux = aux.frunze.get(aux.cautafiu(aux, fiu));
						for (int i = 0; i < retinut.size(); i++) {
							aux.frunze.add(retinut.get(i));
						}
						break;

					}
				}
				if (aux.cautafiu(aux, word) >= 0) {
					if (aux.frunze.get(aux.cautafiu(aux, word)).cuvant.length() == word
							.length()) {
						if (aux.frunze.get(aux.cautafiu(aux, word)).pozitii
								.contains(pozitie) == false) {
							aux.frunze.get(aux.cautafiu(aux, word)).pozitii
									.add(pozitie);
						}
						break;
					}
				}
				if (aux.cautafiu(aux, word) >= 0) {
					if (aux.frunze.get(aux.cautafiu(aux, word)).cuvant.length() > word
							.length()) {
						aux = aux.frunze.get(aux.cautafiu(aux, word));
						String sufix = (String) aux.cuvant.subSequence(
								word.length(), aux.cuvant.length());

						aux.cuvant = word;

						ArrayList<Nod> retinut = new ArrayList<Nod>();
						for (Nod meu : aux.frunze) {
							if (meu.cuvant.equals("%") == false) {

								retinut.add(meu);
							}
						}
						for (int i = 0; i < retinut.size(); i++) {
							aux.frunze.remove(retinut.get(i));
						}

						aux.initialize(aux);
						aux.addWord(aux, sufix);
						aux.frunze.get(aux.cautafiu(aux, sufix)).pozitii
								.addAll(aux.pozitii);
						aux.pozitii.clear();
						aux.pozitii.add(pozitie);
						aux = aux.frunze.get(aux.cautafiu(aux, sufix));

						aux.initialize(aux);
						for (int i = 0; i < retinut.size(); i++) {
							aux.frunze.add(retinut.get(i));
						}
						gasit = true;
					}
				}

			}
			aux = root;
			pozitie++;
		}

		textFile.close();

		/**
		 * CAUTAREA CUVINTELOR IN ARBORE CU PREFIXUL DAT M-am dus cat de mult am
		 * putut in arbore, si din acel punct am apelat functia recursafisare
		 */
		FileParser prefixFile = new FileParser(args[1]);
		prefixFile.open();
		while ((word = prefixFile.getNextWord()) != null) {
			boolean gasit = false;
			while (gasit == false) {
				if (aux.cautafiu(aux, word) < 0) {
					aux = zero;
					break;
				}

				if (aux.cautafiu(aux, word) >= 0) {
					if ((aux.prefix(
							aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
							word).length() == aux.frunze.get(aux.cautafiu(aux,
							word)).cuvant.length())
							&& (aux.frunze.get(aux.cautafiu(aux, word)).cuvant
									.length() < word.length())) {

						String copilu = aux.prefix(
								aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
								word);
						word = (String) word.subSequence(aux.frunze.get(aux
								.cautafiu(aux, word)).cuvant.length(), word
								.length());
						aux = aux.frunze.get(aux.cautafiu(aux, copilu));

					}
				}
				if (aux.cautafiu(aux, word) >= 0) {
					if (aux.frunze.get(aux.cautafiu(aux, word)).cuvant
							.equals(word)) {
						aux = aux.frunze.get(aux.cautafiu(aux, word));
						break;
					}
				}

				if (aux.cautafiu(aux, word) >= 0) {
					if (aux.frunze.get(aux.cautafiu(aux, word)).cuvant.length() > word
							.length()) {
						if (aux.prefix(
								aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
								word).equals(word)) {
							aux = aux.frunze.get(aux.cautafiu(aux, word));
							break;
						} else {
							aux = zero;
							break;
						}
					}
				}
				if (aux.cautafiu(aux, word) >= 0) {
					if (((aux.prefix(
							aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
							word).length() < word.length()) && (((aux.prefix(
							aux.frunze.get(aux.cautafiu(aux, word)).cuvant,
							word).length() < aux.frunze.get(aux.cautafiu(aux,
							word)).cuvant.length()))))) {
						aux = zero;
						break;

					}
				}

			}

			// Afisarea in conformitate cu cerinta

			ArrayList<Integer> rezultat = aux.recursafisare(aux);
			System.out.print(rezultat.size() + " ");
			for (int i = 0; i < rezultat.size(); i++) {
				System.out.print(rezultat.get(i) + " ");
			}

			aux = root;
			System.out.print("\n");
		}

		prefixFile.close();
	}
}
