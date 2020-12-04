public class Graph {
    private int matrice[][];
    private static final int Infinit = 9500;
    private int n;

    public Graph(int n) {
        this.n = n;
        matrice = new int[n + 1][n + 1];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                matrice[i][j] = 0;
    }

    int getSize() {
        return n;
    }

    void addArc(int v, int w, int cost) {
        matrice[v][w] = cost;
    }

    boolean isArc(int v, int w) {
        if (matrice[v][w] != 0)
            return true;
        return false;
    }

    public String toString() {
        int i, j;
        for(i = 0; i <= n; i++) {
            for (j = 0; j <= n; j++) {
                System.out.print("(" + i + ", " + j + ")" + " = " + matrice[i][j] + " ");
            }
            System.out.println(" ");
        }
        return "";
    }

    int[][] floydWarshall() {
        int result[][];
        result = new int[n+1][n+1];
        int k, i, j;
        for(i = 1; i <= n; i++) {
            for(j = 1; j <= n; j++) {
                if(i == j) {
                    result[i][j] = 0;
                } else if(isArc(i, j)) {
                    result[i][j] = matrice[i][j];
                } else {
                    result[i][j] = Infinit;
                }
            }
        }
        for(k = 1; k <= n; k++) {
            for(i = 1; i <= n; i++) {
                for(j = 1; j <= n; j++) {
                    int dist;
                    dist = result[i][k] + result[k][j];
                    if(result[i][j] > dist) {
                        result[i][j] = dist;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Graph g = new Graph(8);

        g.addArc(1,7,15);
        g.addArc(1,5,1);
        g.addArc(5,7,2);
        g.addArc(5,6,3);
        g.addArc(6,1,6);
        g.addArc(6,2,1);
        g.addArc(8,5,2);
        g.addArc(8,6,4);
        g.addArc(2,3,5);
        g.addArc(3,6,7);
        g.addArc(3,4,6);
        g.addArc(4,5,2);

        System.out.println(g);
        int[][] myMatrix = g.floydWarshall();
        System.out.println("Distanta minima dintre 1 si 4 = " + myMatrix[1][4]);
        return;
    }
}
