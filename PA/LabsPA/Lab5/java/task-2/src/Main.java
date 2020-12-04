import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Main {
	static class Task {
		public final static String INPUT_FILE = "in";
		public final static String OUTPUT_FILE = "out";

		int n;

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
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

		public void subsets(ArrayList<Integer> A, ArrayList<Integer> subset, ArrayList<ArrayList<Integer>> all, int index){
			for (int i = index; i < A.size(); i++) {
				subset.add(A.get(i));
				all.add(new ArrayList<>(subset));
				subsets(A,subset,all,i+1);
				subset.remove(subset.size()-1);
			}
			return;
		}
		private ArrayList<ArrayList<Integer>> getResult() {

			ArrayList<ArrayList<Integer>> all = new ArrayList<>();
			ArrayList<Integer> A = new ArrayList<>();
			ArrayList<Integer> subset = new ArrayList<>();
			int index = 0;

			for (int i = 1; i <= n; i++) {
				A.add(i);
			}


			all.add(subset);

			subsets(A,subset,all,index);

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
