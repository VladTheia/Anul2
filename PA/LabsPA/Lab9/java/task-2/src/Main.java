import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 50005;

		int n;
		int m;
		int source;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge> adj[] = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				source = sc.nextInt();

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(
								OUTPUT_FILE));
				StringBuilder sb = new StringBuilder();
				if (result.size() == 0) {
					sb.append("Ciclu negativ!\n");
				} else {
					for (int i = 1; i <= n; i++) {
						sb.append(result.get(i)).append(' ');
					}
					sb.append('\n');
				}
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private ArrayList<Integer> getResult() {
			// TODO: Gasiti distantele minime de la nodul source la celelalte noduri
			// folosind BellmanFord pe graful orientat cu n noduri, m arce stocat in
			// adj.
			//	d[node] = costul minim / lungimea minima a unui drum de la source la
			//	nodul node;
			//	d[source] = 0;
			//	d[node] = -1, daca nu se poate ajunge de la source la node.

			// Atentie:
			// O muchie este tinuta ca o pereche (nod adiacent, cost muchie):
			//	adj[x].get(i).node = nodul adiacent lui x,
			//	adj[x].get(i).cost = costul.

			// In cazul in care exista ciclu de cost negativ, returnati un vector gol:
			//	return new ArrayList<Integer>();
			ArrayList<Integer> d = new ArrayList<>();
			ArrayList<Integer> p = new ArrayList<>();

			for (int i = 0; i <= n; i++) {
				d.add(100000);
				p.add(0);
			}
			d.set(source, 0);

			for (Edge edge : adj[source]) {
				d.set(edge.node, edge.cost);
			}

			for (int j = 1; j <= n - 2; j++) {
				for (int i = 1; i <= n; i++) {
					for (Edge edge : adj[i]) {
						if (d.get(edge.node) > d.get(i) + edge.cost) {
							d.set(edge.node, d.get(i) + edge.cost);
							p.set(edge.node, i);
						}
					}
				}
				
			}

			for (int i = 1; i <= n; i++) {
				for (Edge edge : adj[i]) {
					if (d.get(edge.node) > d.get(i) + edge.cost)
						return new ArrayList<Integer>();
				}
			}
			
			for (int i = 0; i <= n; i++) {
				if (d.get(i) == 100000) {
					d.set(i, -1);
				}
			}

			return d;
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
