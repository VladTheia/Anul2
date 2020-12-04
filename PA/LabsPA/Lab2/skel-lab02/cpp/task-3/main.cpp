#include <fstream>
#include <vector>
#include <bits/stdc++.h>
using namespace std;

struct Homework {
	int deadline;
	int score;

	Homework(int _deadline, int _score) : deadline(_deadline), score(_score) {}
};

class Task {
 public:
	void solve() {
		read_input();
		print_output(get_result());
	}

	static bool cmp(struct Homework a, struct Homework b)
	{
		return a.score > b.score;
	}

  private:
	int n;
	vector<Homework> hws;

	void read_input() {
		ifstream fin("in");
		fin >> n;
		for (int i = 0, deadline, score; i < n; i++) {
			fin >> deadline >> score;
			hws.push_back(Homework(deadline, score));
		}
		fin.close();
	}

	int get_result() {
		/*
		TODO: Aflati punctajul maxim pe care il puteti obtine planificand
		optim temele.
		*/
		int maxScore = 0;
		sort(hws.begin(), hws.end(), cmp);
		int weekNr = 0;
		for (struct Homework hw : hws) {
			if (hw.deadline > weekNr)
				weekNr = hw.deadline;
		}
		int *week = new int[weekNr];
		for (int i = 0; i < weekNr; i++) {
			week[i] = 0; //free week
		}

		for (struct Homework hw : hws) {
			for (int i = hw.deadline - 1; i >= 0; i--) {
				if(week[i] == 0) {
					maxScore += hw.score;
					week[i] = 1;
					break;
				}
			}
		}

		return maxScore;
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