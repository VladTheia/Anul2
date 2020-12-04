#include <fstream>
#include <vector>
#include <algorithm>
using namespace std;

class Task {
 public:
	void solve() {
		read_input();
		print_output(get_result());
	}

 private:
	int n;

	void read_input() {
		ifstream fin("in");
		fin >> n;
		fin.close();
	}

	bool isSafe(int k, int i, vector<int> sol) {
		for (int j = 1; j <=k; j++) {
			if (sol[j] == i || (abs(sol[j] - 1) == abs(j - k)))
				return false;
		}
		return true;
	}

	vector<int> nQueens(int k, int n, vector<int> sol) {
		for (int i = 1; i <= n; i++) {
			if (isSafe(k, i, sol)) {
				sol[k] = i;
				if (k == n)
					return sol;
				else
					nQueens(k + 1, n, sol);
			}
		}
	}

	vector<int> get_result() {
		vector<int> sol(n + 1, 0);

		/*
		TODO: Gasiti o solutie pentru problema damelor pe o tabla de dimensiune
		n x n.

		Pentru a plasa o dama pe randul i, coloana j:
			sol[i] = j.
		Randurile si coloanele se indexeaza de la 1 la n.

		De exemplu, configuratia (n = 5)
		X----
		--X--
		----X
		-X---
		---X-
		se va reprezenta prin sol[1..5] = {1, 3, 5, 2, 4}.
		*/
		sol = nQueens(1, n, sol);

		return sol;
	}

	void print_output(vector<int> result) {
		ofstream fout("out");
		for (int i = 1; i <= n; i++) {
			fout << result[i] << (i != n ? ' ' : '\n');
		}
		fout.close();
	}
};

int main() {
	Task task;
	task.solve();
	return 0;
}
