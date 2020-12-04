#include <iostream>
#include <iomanip>
#include <fstream>
#include <vector>

using namespace std;

class Task {
public:
	void solve() {
		read_input();
		print_output(compute_sqrt());
	}

private:
	double n;

	void read_input() {
		ifstream fin("in");
		fin >> n;
		fin.close();
	}

	double compute_sqrt() {
		/*
		TODO: Calculati sqrt(n) cu o precizie de 0.001
		Precizie de 10^-x = |rezultatul vostru - rezultatul corect| <= 10^-x
		*/
		double result, start = 0, end = n + 1;
		result = start + (end - start)/2;
		while(abs(result * result - n) > 0.001) {
			result = start + (end - start)/2;

			if (result * result - n > 0)
				end = result;
			if (result * result - n < 0)
				start = result;
		}
		return result;
	}

	void print_output(double result) {
		ofstream fout("out");
		fout << setprecision(4) << fixed << result;
		fout.close();
	}
};

int main() {
	Task task;
	task.solve();
	return 0;
}
