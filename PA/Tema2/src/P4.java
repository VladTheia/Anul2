import java.io.*;
import java.util.*;

public class P4 {
    static int n, m, t;
    @SuppressWarnings("unchecked")
    static ArrayList<P4Edge> adj[] = new ArrayList[6000];
    static int[] pen;


  public static void main(String[] args) throws IOException {
    // Clasa mai rapida de citire implementata de echipa de PA
    MyScanner sc = new MyScanner("p4.in");
    n = sc.nextInt();
    m = sc.nextInt();
    t = sc.nextInt();
    
    pen = new int[t + 1];
    ArrayList<Integer> usedTypes = new ArrayList<>();

    for (int i = 1; i <= n; i++)
		adj[i] = new ArrayList<>();
    
    for (int i = 1; i <= m; i++) {
      int u = sc.nextInt();
      int v = sc.nextInt();
      int c = sc.nextInt();
      int ty = sc.nextInt();

      adj[u].add(new P4Edge(v, c, ty));
      adj[v].add(new P4Edge(u, c, ty));
    }

    for (int i = 1; i <= t; i++) {
        int tt = sc.nextInt();
        pen[i] = tt;
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

			for (P4Edge edge : adj[source]) {
				d.set(edge.node, edge.cost);
			}

			for (int j = 1; j <= n - 2; j++) {
				for (int i = 1; i <= n; i++) {
					for (P4Edge edge : adj[i]) {
                        int cost;
                        if (usedTypes.contains(edge.type)) {
                            cost = d.get(i) + edge.cost;
                        } else {
                            cost = d.get(i) + edge.cost + pen[edge.type];
                            usedTypes.add(edge.type);
                        }
                        if (d.get(edge.node) > cost) {
                            d.set(edge.node, cost);
                            p.set(edge.node, i);
                        }
					}
				}
            }
        
            

			for (int i = 1; i <= n; i++) {
				for (P4Edge edge : adj[i]) {
					if (d.get(edge.node) > d.get(i) + edge.cost)
						flag = false;
				}
			}
			
			for (int i = 0; i <= n; i++) {
				if (d.get(i) == Integer.MAX_VALUE) {
					d.set(i, -1);
				}
			}


    Writer wr = new FileWriter("p4.out");
    // Scriere output
    if (!flag) {
        wr.write("-1");
    } else {
        wr.write(d.get(n) + "");
    }
    wr.close();
  }
}

class P4Edge {
  public int node;
  public int cost;
  public int type;

  P4Edge(int _node, int _cost, int _type) {
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
  