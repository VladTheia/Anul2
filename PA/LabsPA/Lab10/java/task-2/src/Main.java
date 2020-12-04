import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	static class Task {
		public static final String INPUT_FILE = "in";
		public static final String OUTPUT_FILE = "out";
		public static final int NMAX = 200005;

		int n;
		int m;

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

				for (int i = 1; i <= n; i++)
					adj[i] = new ArrayList<>();
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
					adj[y].add(new Edge(x, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			/*
			TODO: Calculati costul minim al unui arbore de acoperire
			folosind algoritmul lui Prim.
			*/
			PriorityQueue<Edge> pq = new PriorityQueue<>(n, new Comparator<Edge>() {
				@Override
				public int compare(Edge e1, Edge e2) {
					int key1 = e1.cost;
					int key2 = e2.cost;
					return key1 - key2;
				}
			});

			int[] d = new int[n + 1];
			int[] p = new int[n + 1];
			int[] viz = new int[n + 1];

			for (int u = 1; u <= n; u++) {
				d[u] = NMAX;
				p[u] = -1;
				viz[u] = 0;
			}
			d[1] = 0;
			viz[1] = 1;

			for (Edge u : adj[1]) {
				pq.add(u);
			}

			int sum = 0;

			while (!pq.isEmpty()) {
				Edge u = pq.poll();
				if (viz[u.node] == 0) {
					sum += u.cost;
					viz[u.node] = 1;
					for (Edge v : adj[u.node]) {
						if (v.cost < d[v.node]) {
							d[v.node] = v.cost;
							p[v.node] = u.node;
							pq.add(v);
						}
					}
				}

			}


			return sum;
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
