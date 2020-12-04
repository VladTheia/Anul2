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

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				for (int node : result) {
					pw.printf("%d ", node);
				}
				pw.printf("\n");
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		int timp;

		void dfsCV(int n, int v, int[] idx, int[] low, int[] parinte, ArrayList<Integer> sol) {
			idx[v] = timp;
			low[v] = timp;
			timp++;
			ArrayList<Integer> copii = new ArrayList<Integer>();

			for (Integer u : adj[v]) {
				if (idx[u] == 0) {
					copii.add(u);
					parinte[u] = v;
					dfsCV(n, u, idx, low, parinte, sol);
					low[v] = Math.min(low[v], low[u]);
				} else {
					low[v] = Math.min(low[v], idx[u]);
				}
			}

			if (parinte[v] == 0) {
				if (copii.size() >= 2)
					sol.add(v);
			} else {
				for (Integer u : copii) {
					if (low[u] >= idx[v]) {
						sol.add(v);
						break;	
					}
				}
			}
		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti nodurile critice ale grafului neorientat stocat cu liste
			// de adiacenta in adj.
			ArrayList<Integer> sol = new ArrayList<Integer>();	
			int[] idx = new int[n + 1];
			int[] low = new int[n + 1];
			int[] parinte = new int[n + 1];
		
			timp = 1;
			for (int i = 1; i <=n; i++) {
				idx[i] = 0;
				low[i] = 0;
				parinte[i] = 0;
			}

			for (int v = 1; v <=n; v++) {
				if (idx[v] == 0) {
					dfsCV(n, v, idx, low, parinte, sol);
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
