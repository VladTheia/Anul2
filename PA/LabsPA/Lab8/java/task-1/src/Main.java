import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 100005; // 10^5

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		@SuppressWarnings("unchecked")
		ArrayList<Integer> adjt[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
					adjt[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adjt[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Integer>> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result.size());
				for (ArrayList<Integer> ctc : result) {
					for (int nod : ctc) {
						pw.printf("%d ", nod);
					}
					pw.printf("\n");
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		void dfs(Stack S, int n, int v, int c[]) {
			c[v] = 1;
			for(Integer u : adj[v]) {
				if (c[u] == 0) {
					dfs(S, n, u, c);
				}
			}
			S.push(v);
		}

		void dfsT(ArrayList ctc, int n, int v, int c[]) {
			c[v] = 1;
			ctc.add(v);
			for(Integer u : adjt[v]) {
				if (c[u] == 0)
					dfsT(ctc, n, u, c);
			}
		}

		private ArrayList<ArrayList<Integer>> getResult() {
			// TODO: Gasiti componentele tare conexe ale grafului orientat cu
			// n noduri, stocat in adj. Rezultatul se va returna sub forma
			// unui ArrayList, ale carui elemente sunt componentele tare conexe
			// detectate. Nodurile si componentele tare conexe pot fi puse in orice
			// ordine in arraylist.
			//
			// Atentie: graful transpus este stocat in adjt.
			ArrayList<ArrayList<Integer>> sol = new ArrayList<>();

			//get empty stack
			Stack<Integer> S = new Stack<Integer>();
			int[] c = new int[n + 1];

			//color all nodes white
			for (int i = 1; i <= n; i++) {
				c[i] = 0;
			}

			for (int i = 1; i <= n; i++) {
				if (!S.contains(i)) {
					dfs(S, n, i, c);
				}
			}

			//color all nodes white
			for (int i = 1; i <= n; i++) {
				c[i] = 0;
			}

			Integer v;
			ArrayList<Integer> ctc = new ArrayList<Integer>();
			while (!S.isEmpty()) {
				v = S.pop();
				if (c[v] == 0) {
					ctc = new ArrayList<Integer>();
					dfsT(ctc, n, v, c);
					sol.add(ctc);
				}
			}

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
