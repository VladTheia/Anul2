import java.io.*;
import java.util.*;

public class P1 {
    public static void main(String[] args) throws IOException {
        int i, level;
        int maxLevel = 0;
        Writer wr = new FileWriter("p1.out");
        // Clasa mai rapida de citire implementata de echipa de PA
        MyScanner sc = new MyScanner("p1.in");
        // Numarul de elemente al vectorului
        int n = sc.nextInt();
        // Array de liste de distante fata de root, nu exista nod la distanta mai mare de n-1
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] dist = new ArrayList[n];
        for (i = 0; i < n; i++) {
            dist[i] = new ArrayList<Integer>();
        }
        // Adaugam in lista pentru distanta citita al i-lea element
        for (i = 1; i <= n; i++) {
            level = sc.nextInt();
            dist[level].add(i);
            if (level > maxLevel) {
                maxLevel = level;
            }
        }

        // Nu exista 2 noduri root 
        if (dist[0].size() > 1) {
            wr.write("-1");
            wr.close();
            return;
        }

        // Lista de muchii
        ArrayList<Edge> edges = new ArrayList<>();

        for (i = 1; i <= maxLevel; i++) {
            // Nu putem ajunge la nivelul i daca nu avem noduri pe nivelul i - 1
            if (dist[i - 1].size() == 0 && dist[i].size() != 0) {
                wr.write("-1");
                wr.close();
                return;
            }

            for (Integer j : dist[i]) {
                edges.add(new Edge(dist[i - 1].get(0), j));
            }
        }

        // Scriere output
        wr.write(edges.size() + "\n");
        for (Edge edge : edges) {
            wr.write(edge.x + " " + edge.y + "\n");
        }
        wr.close();
    }
}

class Edge {
    int x;
    int y;

    public Edge(int x, int y) {
        this.x = x;
        this.y = y;
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
  