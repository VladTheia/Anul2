import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
    	Vector<Complex> v;
    	
        /* Citeste date de test. */
        v = Complex.readData(".date.in");
        System.out.println("Vectorul initial:");
        Complex.writeVector(v);
        
        /* Verifica sortarea. */
        Vector<Complex> sorted = getSorted(v);
        System.out.println("Vectorul sortat:");
        Complex.writeVector(sorted);

        /* Verifica maparea. */
        HashMap<Complex,Integer> mapping = getMapping(sorted);
        System.out.println("Maparea:");
        for (Complex element : sorted) {
        	System.out.println(String.format("%s e pe pozitia %d", element, mapping.get(element)));
        }
    }
    
    private static class ComplexComparator implements Comparator<Complex> {

    	/**
    	 * Functie ce compara doua numere complexe: intai compara partea reala
    	 * si daca ambele numere au aceeasi parte reala, atunci compara partea imaginara 
    	 */
		@Override
		public int compare(Complex a, Complex b) {
			//TODO Intoarceti un numar pozitiv daca a > b, negativ altfel

            if (a.r > b.r)
                return 1;
            if (a.r < b.r)
                return -1;
            if (a.i > b.i)
                return 1;
            if (a.i < b.i)
                return -1;
            return 0;
        }
    }

    /** 
     * Functie ce sorteaza crescator elementele unui vector de numere complexe
     * @param v		vectorul de numere complexe de intrare
     * @return		vectorul de numere complexe sortat descrescator
     */
    private static Vector<Complex> getSorted( Vector<Complex> v ) {
    	Vector<Complex> res = new Vector<Complex>();	// vectorul rezultat
    	
    	/*TODO Folosind PriorityQueue, adaugati elementele din v in ordine crescatoare.
    	 * Pentru PriorityQueue folositi comparatorul definit mai sus */
    	PriorityQueue<Complex> pq = new PriorityQueue<>(new ComplexComparator());

    	for(Complex complex : v) {
    	    pq.add(complex);
        }

        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }

        return res;
    }
    
    /**
     * Functie ce construieste un HashMap: pentru fiecare element complex atribuie pozitia sa 
     * in vectorul sortat
     * @param v		vectorul de elemente complexe, nesortat
     * @return		HashMap care atribuie pentru fiecare element pozitia sa in vectorul sortat
     */
    private static HashMap<Complex, Integer> getMapping ( Vector<Complex> v) {
    	HashMap<Complex, Integer> res = new HashMap<>();
    	
    	/*TODO Adaugati in map, pentru fiecare element din v, pozitia sa in vectorul sortat. */
        for (int i = 0; i < v.size(); i++) {
            res.put(v.get(i), i);
        }

        return res;
    }
    
    
}