import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 1005;

		int n;
		int m;

		@SuppressWarnings("unchecked")
		ArrayList<Integer> adj[] = new ArrayList[NMAX];
		int[] p;
		int C[][];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();

				C = new int[n + 1][n + 1];
				p = new int[n + 1];
				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, z;
					x = sc.nextInt();
					y = sc.nextInt();
					z = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
					C[x][y] += z;
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private boolean BFS(int source, int target) {
			int top;
			int v[] = new int[n + 1];
			for (int i = 0; i <= n; i++) v[i] = 0;
			for (int i = 0; i <= n; i++) p[i] = -1;

			// TODO: Faceti un BFS care sa construiasca in d valorile d[i] = numarul
			// minim de muchii de parcurs de la nodul source la nodul i.
			// d[source] = 0
			// d[x] = -1 daca nu exista drum de la source la x.
			// *******
			// ATENTIE: nodurile sunt indexate de la 1 la n.
			// *******
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(source);
			while (q.peek() != null) {
				top = q.poll();
				for (Integer succ : adj[top]) {
					if (v[succ] == 0 && C[top][succ] > 0) {
						p[succ] = top;
						v[succ] = 1;
						q.add(succ);
					}
				}
				v[top] = 1;
			}

			return (v[target] == 1);
		}		

		private int getResult() {
			// TODO: Calculati fluxul maxim pe graful orientat dat.
			// Sursa este nodul 1.
			// Destinatia este nodul n.
			//
			// In adj este stocat graful neorientat obtinut dupa ce se elimina orientarea
			// arcelor, iar in C sunt stocate capacitatile arcelor.
			// De exemplu, un arc (x, y) de capacitate z va fi tinut astfel:
			// C[x][y] = z, adj[x] contine y, adj[y] contine x.
			int k;
			int flow = 0;
			while (BFS(1, n)) {
				int path_flow = Integer.MAX_VALUE;
				for (int i = n; i != 1; i = p[i]) {
					k = p[i];
					path_flow = Math.min(path_flow, C[k][i]);
				}

				for (int i = n; i != 1; i = p[i]) {
					k = p[i];
					C[k][i] -= path_flow;
					C[i][k] += path_flow;
				}				

				flow += path_flow;
			}

			return flow;
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
