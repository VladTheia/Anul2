#include <iostream>
#include <fstream>
#include <vector>
#include <math.h>

using namespace std;

class Task {
public:
	void solve() {
		read_input();
		print_output(get_result(n, x, y));
	}

private:
	int n, x, y;

	void read_input() {
		ifstream fin("in");
		fin >> n >> x >> y;
		fin.close();
	}

	int get_result(int n, int x, int y) {
		/*
		TODO: Calculati valoarea de pe pozitia (x, y) din matricea de dimensiune
		2^n x 2^n
		*/
		int x1 = 1, x2 = pow(2, n), y1 = 1, y2 = pow(2, n);
		int k = 1;
		int start = 1, end = pow(2, n+n);
		while(k <= n) {
			int aux = (end - start + 1) / 4;
			if(x >= x1 && x <= (x1 + x2)/2 && y >= y1 && y <= (y1 + y2)/2) {
				x2 = (x1 + x2)/2; y2 = (y1 + y2)/2;
				end = aux;
				cout << 1 << '\n';
			} else if (x >= x1 && x <= (x1 + x2)/2 && y >= (y1 + y2)/2 && y <= y2) {
				x2 = (x1 + x2)/2; y1 = (y1 + y2)/2;
				start = aux + 1;
				end = 2 * aux;
				cout << 2 << '\n';
			} else if (x >= (x1 + x2)/2 && x <= x2 && y >= y1 && y <= (y1 + y2) / 2) {
				x1 = (x1 + x2)/2; y2 = (y1 + y2)/2;
				start = 2 * aux + 1;
				end = 3 *aux;
				cout << 3 << '\n';
			} else if (x >= (x1 + x2)/2 && x <= x2 && y >= (y1 + y2)/2 && y <= y2) {
				x1 = (x1 + x2)/2; y1 = (y1 + y2)/2;
				start = 3* aux +1;
				end = 4* aux;
				cout << 4 << '\n';
			}
			k++;
			cout << x1 << " " << x2 << " " << y1 << " " << y2 << '\n'; 
		}
		cout << start <<" " << end;
		
		return start;
	}

	void print_output(int result) {
		ofstream fout("out");
		fout << result;
		fout.close();
	}
};

int main() {
	Task task;
	task.solve();
	return 0;
}
