import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				for (int i = 1; i <= n; i++) {
					pw.printf("%d%c", result.get(i), i == n ? '\n' : ' ');
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		boolean canPlace(ArrayList<Integer> sol, int k, int i) { 
        	for (int j = 1; j <= k - 1; j++) { 
				if (sol.get(j) == i ||  
					(Math.abs(sol.get(j) - i) == Math.abs(j - k))) 
				{ 
					return false; 
				} 
        	} 
        	return true; 
    	} 

		private void back(int row, int size, ArrayList<Integer> sol) {
			for (int i = 1; i <= n; i++) {
				if (canPlace(sol, row, i)) {
					sol.set(row, i);
					if (row == size) {
						return;
					} else {
						back(row + 1, size, sol);
					}

				}
			}

		}

		private ArrayList<Integer> getResult() {
			ArrayList<Integer> sol = new ArrayList<Integer>();
			for (int i = 0; i <= n; i++)
				sol.add(0);

			// TODO: Gasiti o solutie pentru problema damelor pe o tabla de
			// dimensiune n x n.

			// Pentru a plasa o dama pe randul i, coloana j:
			//     sol[i] = j.
			// Randurile si coloanele se indexeaza de la 1 la n.

			// De exemplu, configuratia (n = 5)
			// X----
			// --X--
			// ----X
			// -X---
			// ---X-
			// se va reprezenta prin sol[1..5] = {1, 3, 5, 2, 4}.
			
			back(1, n, sol);

			return sol;
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
