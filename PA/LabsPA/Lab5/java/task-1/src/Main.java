import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n, k;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				k = sc.nextInt();
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<ArrayList<Integer>> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result.size());
				for (ArrayList<Integer> arr : result) {
					for (int i = 0; i < arr.size(); i++) {
						pw.printf("%d%c", arr.get(i), i + 1 == arr.size() ?
								'\n' : ' ');
					}
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		void bkt(int steps, ArrayList<Integer> current, int[] visited, ArrayList<ArrayList<Integer>> all)
		{
			if (steps == k)
			{
				all.add(new ArrayList<Integer> (current));
				return;
			}
			for (int i = 1; i <= n ; i++) {
				if(visited[i] == 1)
					continue;
				visited[i] = 1;
				current.add(i);
				bkt(steps+1, current, visited, all);
				current.remove(current.size() - 1);
				visited[i] = 0;
			}
		}
		private ArrayList<ArrayList<Integer>> getResult() {
			ArrayList<ArrayList<Integer>> all = new ArrayList<>();

			// TODO: Construiti toate aranjamentele de N luate cate K ale
			// multimii {1, ..., N}.

			// Pentru a adauga un nou aranjament:
			//   ArrayList<Integer> aranjament;
			//   all.add(aranjament);
			int[] visited = new int[n+1];
			ArrayList<Integer>current = new ArrayList<>();
			bkt(0, current, visited, all);
			return all;
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
