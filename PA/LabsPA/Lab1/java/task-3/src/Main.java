import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.lang.Math;;


public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, x, y;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				x = sc.nextInt();
				y = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int answer) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", answer);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		int N = (int)Math.pow(2, n);
		int count;
		int[][] grid = new int[N + 1][N + 1];

		private void parcurgere(int i, int  j, int n) {
			if (n > 0) {
				int offset = (int)Math.pow(2, n - 1);
				parcurgere(i, j, n - 1);
				parcurgere(i, j + offset, n - 1);
				parcurgere(i + offset, j, n - 1);
				parcurgere(i + offset, j + offset, n - 1);
			} else {
				grid[i][j] = count;
				count++;
			}
		}

		private int getAnswer(int n, int x, int y) {
			// TODO: Calculati valoarea de pe pozitia (x, y) din matricea de dimensiune
			// 2^N * 2^N.
			count = 1;
			parcurgere(1, 1, n);

			return grid[x][y];
		}

		public void solve() {
			readInput();
			writeOutput(getAnswer(n, x, y));
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
