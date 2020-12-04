import java.lang.Math;

public class Test {
    static int n = 3;
    static int N = (int)Math.pow(2, n);
    static int count;
	static int[][] grid = new int[N + 1][N + 1];

		private static void parcurgere(int i, int  j, int n) {
            if (n > 0) {
                int offset = (int)Math.pow(2, n - 1);
                parcurgere(i, j, n - 1);
				parcurgere(i, j + offset, n - 1);
				parcurgere(i + offset, j, n - 1);
				parcurgere(i + offset, j + offset, n - 1);
			} else {
                //System.out.println("count " + count);
				grid[i][j] = count;
				count++;
			}
		}

        public static void main(String[] args) {
            
            count = 1;
            parcurgere(1, 1, n);

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.print('\n');
            }
        }
}