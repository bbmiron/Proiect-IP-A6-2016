package AlgoritmSecventa;

import CustomExceptions.InvalidTypesException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import BazaDate.BazaDate;

public class Algoritm {
	private BazaDate bazaDeDate;

	private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> listaDeAdiacenta;
	private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> listaDeFunctii;

	public Algoritm(BazaDate bazaDeDate) {

		this.bazaDeDate = bazaDeDate;
		try {
			this.genereazaListaDeAdiacenta();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Integer> getListaCalculatoare(int input, int output) throws InvalidTypesException {
		LinkedList<Integer> rezultat = new LinkedList<Integer>();

		if (!listaDeAdiacenta.containsKey(input)) {
			throw new InvalidTypesException(0, input);
		}

		PriorityQueue<Element> heap = new PriorityQueue<Element>();
		heap.add(new Element(input));

		ConcurrentHashMap<Integer, Integer> dist = new ConcurrentHashMap<Integer, Integer>();
		ConcurrentHashMap<Integer, Integer> prec = new ConcurrentHashMap<Integer, Integer>();

		while (!heap.isEmpty()) {
			Element element = heap.poll();
			int nod = element.getNod();
			Integer distanta = dist.get(nod);
			if (distanta == null || distanta > element.getDistanta()) {
				distanta = element.getDistanta();
				dist.put(nod, distanta);
				prec.put(nod, element.getPrecedent());

				ConcurrentHashMap<Integer, Integer> vecini = listaDeAdiacenta.get(element.getNod());

				if (vecini != null) {
					for (Enumeration<Integer> vecin = vecini.keys(); vecin.hasMoreElements();) {
						int urmator = vecin.nextElement();
						Integer distanta2 = dist.get(urmator);
						if (distanta2 == null || distanta2 > distanta + 1) {
							heap.add(new Element(urmator, distanta + 1, nod));
						}
					}
				}
			}
		}

		int curent = output;
		Integer precedent = prec.get(curent);
		if (precedent == null) {
			throw new InvalidTypesException(1, output);
		}
		while (precedent != 0) {
			rezultat.addFirst(listaDeFunctii.get(precedent).get(curent));
			curent = precedent;
			precedent = prec.get(curent);
		}

		return rezultat;
	}

	public int getFunctie(int in, int out) {

		ConcurrentHashMap<Integer, Integer> V = listaDeFunctii.get(in);
		if (V == null) {
			return -1;
		}

		Integer result = V.get(out);
		if (result == null) {
			return -1;
		}

		return result;
	}

	public int getCost(int in, int out) {

		ConcurrentHashMap<Integer, Integer> V = listaDeAdiacenta.get(in);
		if (V == null) {
			return 0;
		}

		Integer result = V.get(out);
		if (result == null) {
			return 0;
		}

		return result;
	}

	public void genereazaListaDeAdiacenta() throws SQLException {

		listaDeAdiacenta = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>>();
		listaDeFunctii = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>>();

		ArrayList<ClasaSpeciala> listaDeArce = this.bazaDeDate.getList();

		for (ClasaSpeciala element : listaDeArce) {

			// Actualizam listaDeAdiacenta
			ConcurrentHashMap<Integer, Integer> V = this.listaDeAdiacenta.get(element.getInput());
			if (V == null) {
				V = new ConcurrentHashMap<Integer, Integer>();
			}

			Integer numarDeFunctii = V.get(element.getOutput());
			if (numarDeFunctii == null) {
				numarDeFunctii = new Integer(1);
			} else {
				numarDeFunctii++;
			}

			V.put(element.getOutput(), numarDeFunctii);
			listaDeAdiacenta.put(element.getInput(), V);

			// Actualizam listaDeFunctii
			V = this.listaDeFunctii.get(element.getInput());
			if (V == null) {
				V = new ConcurrentHashMap<Integer, Integer>();
			}

			V.put(element.getOutput(), element.getTipFunctie());
			listaDeFunctii.put(element.getInput(), V);

		}

	}

	public void addFunctie(int input, int output, int tipFunctie) {
		// Actualizam listaDeAdiacenta
		ConcurrentHashMap<Integer, Integer> V = this.listaDeAdiacenta.get(input);
		if (V == null) {
			V = new ConcurrentHashMap<Integer, Integer>();
		}

		Integer numarDeFunctii = V.get(output);
		if (numarDeFunctii == null) {
			numarDeFunctii = new Integer(1);
		} else {
			numarDeFunctii++;
		}

		V.put(output, numarDeFunctii);
		listaDeAdiacenta.put(input, V);

		// Actualizam listaDeFunctii
		V = this.listaDeFunctii.get(input);
		if (V == null) {
			V = new ConcurrentHashMap<Integer, Integer>();
		}

		V.put(output, tipFunctie);
		listaDeFunctii.put(input, V);
	}

	public void removeFunctie(int input, int output, int tipFunctie) {
		// Actualizam listaDeAdiacenta
		ConcurrentHashMap<Integer, Integer> V = this.listaDeAdiacenta.get(input);
		if (V != null) {

			Integer numarDeFunctii = V.get(output);
			if (numarDeFunctii != null) {
				numarDeFunctii--;
				if (numarDeFunctii == 0) {
					V.remove(output);
				} else {
					V.put(output, numarDeFunctii);
					listaDeAdiacenta.put(input, V);
				}
			}

		}

	}
}
