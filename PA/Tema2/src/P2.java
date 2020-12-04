import java.io.*;
import java.util.*;

public class P2 {
    // dimensiunile si limita
    static int n, m, k, count;
    // matrice cu elementele
    static int[][] grid; 

    public static void main(String[] args) throws IOException {
        // Clasa mai rapida de citire implementata de echipa de PA
        MyScanner sc = new MyScanner("p2.in");
        
        // Dimensiunea matricei
        n = sc.nextInt();
        m = sc.nextInt();
        grid = new int[n][m];
        // Diferenta maxima dintre minimul si maximul unei regiuni
        k = sc.nextInt();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // variabila in care salvam rezultatul cautat de algoritm
        int maxCount = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                count = 0;
                // Facem dfs pe fiecare element din matrice
                DFS(i, j, grid[i][j], grid[i][j], new int[n][m]);
                // Actualizam maximul daca numarul de elemente din regiunea 
                // obtinuta in urma cautarii din acel nod este mai mare decat
                // maximul precedent
                if (count > maxCount) {
                    maxCount = count;
                }
            }
        }

        Writer wr = new FileWriter("p2.out");
        // Scriere output
        wr.write(maxCount + "");
        wr.close();
    }

    private static void DFS(int x, int y, int max, int min, int[][] vis) {
        // Verificam sa nu iesim din apelam indecsi gresiti ai matricei
        if ((x < 0 || x >= n) || (y < 0 || y >= m)) {
          return;
        } else {
          // nu revenim in elemente deja vizitate
          if (vis[x][y] == 1) return;

          // actualizam minimul regiunii daca elementul curent este mai mic si
          // diferenta dintre el si maxim este <= k
          if (grid[x][y] < min) return;
          max = (grid[x][y] > max) ? grid[x][y] : max;
          if (max - min > k) return;

          // adaugam elementul in regiune
          vis[x][y] = 1;
          count++;

          // incepem explorare nodurilor adiacente
          DFS(x - 1, y, max, min, vis);
          DFS(x, y - 1, max, min, vis);
          DFS(x + 1, y, max, min, vis);
          DFS(x, y + 1, max, min, vis);

        }
        
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
  