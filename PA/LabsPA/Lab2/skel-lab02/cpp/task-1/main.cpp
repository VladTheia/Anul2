#include <fstream>
#include <iomanip>
#include <vector>
#include <bits/stdc++.h>
using namespace std;

struct Object {
	int weight;
	int price;

	Object(int _weight, int _price) : weight(_weight), price(_price) {}
};

class Task {
 public:
	void solve() {
		read_input();
		print_output(get_result());
	}

	static bool cmp(struct Object a, struct Object b) {
		double r1 = (double)a.price / a.weight;
		double r2 = (double)b.price / b.weight;
		return r1 > r2;
	}
 private:
	int n, w;
	vector<Object> objs;


	void read_input() {
		ifstream fin("in");
		fin >> n >> w;
		for (int i = 0, weight, price; i < n; i++) {
			fin >> weight >> price;
			objs.push_back(Object(weight, price));
		}
		fin.close();
	}

	double get_result() {
		/*
		TODO: Aflati profitul maxim care se poate obtine cu obiectele date.
		*/
		sort(objs.begin(), objs.end(), cmp);

		int curWeight = 0;
		double finalValue = 0.0;

		for (int i = 0; i < n; i++) {
			if (curWeight + objs[i].weight <= w) {
				curWeight += objs[i].weight;
				finalValue += objs[i].price;
			} else {
				int remain = w - curWeight;
				finalValue += objs[i].price * ((double) remain / objs[i].weight);
				break;
			}
		}

		return finalValue;
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