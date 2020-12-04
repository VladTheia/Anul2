import java.io.*;
import java.util.*;

public class P3 {
    static int n, m, t;
    @SuppressWarnings("unchecked")
    static ArrayList<P3Edge> adj[] = new ArrayList[6000];
    static int[][] pen;


  public static void main(String[] args) throws IOException {
    // Clasa mai rapida de citire implementata de echipa de PA
    MyScanner sc = new MyScanner("p3.in");
    n = sc.nextInt();
    m = sc.nextInt();
    t = sc.nextInt();
    
    pen = new int[t + 1][t + 1];

    for (int i = 1; i <= n; i++)
		adj[i] = new ArrayList<>();
    
    for (int i = 1; i <= m; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      int c = sc.nextInt();
      int ty = sc.nextInt();

      adj[u].add(new P3Edge(v, c, ty));
      adj[v].add(new P3Edge(u, c, ty));
    }

    for (int i = 1; i <= t; i++) {
      for (int j = 1; j <= t; j++) {
        int tt = sc.nextInt();
        pen[i][j] = tt;
      }
    }

    boolean flag = true;
    int source = 1;

    ArrayList<Integer> d = new ArrayList<>();
	ArrayList<Integer> p = new ArrayList<>();

			for (int i = 0; i <= n; i++) {
				d.add(Integer.MAX_VALUE);
				p.add(0);
			}
			d.set(source, 0);

			for (P3Edge edge : adj[source]) {
				d.set(edge.node, edge.cost);
			}

			for (int j = 1; j <= n - 2; j++) {
				for (int i = 1; i <= n; i++) {
					for (P3Edge edge : adj[i]) {
                        if (d.get(edge.node) > d.get(i) + edge.cost) {
                            d.set(edge.node, d.get(i) + edge.cost);
                            p.set(edge.node, i);
                        }
					}
				}
            }
        
            

			for (int i = 1; i <= n; i++) {
				for (P3Edge edge : adj[i]) {
					if (d.get(edge.node) > d.get(i) + edge.cost)
						flag = false;
				}
			}
			
			for (int i = 0; i <= n; i++) {
				if (d.get(i) == Integer.MAX_VALUE) {
					d.set(i, -1);
				}
			}


    Writer wr = new FileWriter("p3.out");
    // Scriere output
    if (!flag) {
        wr.write("-1");
    } else {
        wr.write(d.get(n) + "");
    }
    wr.close();
  }
}

class P3Edge {
  public int node;
  public int cost;
  public int type;

  P3Edge(int _node, int _cost, int _type) {
    node = _node;
    cost = _cost;
    type = _type;
  }
}

class MyScanner {
    BufferedReader br;
    StringTokenizer st;
  
    public MyScanner(String filename) throws FileNotFoundException {
      br = new BufferedReader(new FileReader(filename));
    }
  
    String next() {
      while (st == null || !st.hasMoreElements()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return st.nextToken();
    }
  
    int nextInt() {
      return Integer.parseInt(next());
    }
  
    long nextLong() {
      return Long.parseLong(next());
    }
  
    double nextDouble() {
      return Double.parseDouble(next());
    }
  
    String nextLine(){
      String str = "";
      try {
        str = br.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return str;
    }
  }
  