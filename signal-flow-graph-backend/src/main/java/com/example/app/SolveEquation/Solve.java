package com.example.app.SolveEquation;

import com.example.app.Graph.ForwardPaths;
import com.example.app.Graph.Graph;
import com.example.app.Graph.Isolated;
import com.example.app.Graph.Loops;

import java.util.ArrayList;

public class Solve {
	Graph graph;
	public static String ans = "";

	public Solve(Graph g) {
		graph = g;
	}

	public String getResult() {
		return tmp();
	}

	private String tmp() {
		ForwardPaths fP = new ForwardPaths(graph);
		Loops loops = new Loops(graph);
		fP.getAllPaths(1, graph.graph.length - 1);
		// fP.print();
		loops.getLoops();
		Solve.ans += "#";
		Isolated iso = new Isolated(graph);
		iso.getAllIsolated();
		int temp = 0;
		String[] arr = new String[100];
		double rs = 0;
		for (int i = 0; i < graph.forwardPaths.size(); i++) {
			int deltaI = 1;
			for (int j = 0; j < graph.deltaI[i].size(); j++) {
				arr[temp] = toString(graph.deltaI[i].get(j));
				if (numberOfBrackets(arr[temp]) % 2 == 0) {
					System.out.println();
					deltaI += graph.allGains.get(arr[temp]);
				} else {
					deltaI -= graph.allGains.get(arr[temp]);
				}
				temp++;
			}
			ans += deltaI;
			if (i < graph.forwardPaths.size() - 1)
				ans += "$";
			rs += graph.forwardPathsGains[i] * deltaI;
		}

		double rs2 = 1;
		for (int i = 0; i < graph.singleAndNonTouching.size(); i++) {
			for (int j = 0; j < graph.singleAndNonTouching.get(i).size(); j++) {
				if (numberOfBrackets(toString(graph.singleAndNonTouching.get(i).get(j))) % 2 == 0)
					rs2 += graph.allGains.get(toString(graph.singleAndNonTouching.get(i).get(j)));
				else
					rs2 -= graph.allGains.get(toString(graph.singleAndNonTouching.get(i).get(j)));
			}

		}
		System.out.println("Result " + (rs / rs2));
		ans += "#";
		ans += rs / rs2;
		return ans;

	}

	private static String toString(ArrayList<Integer>[] newNonTouching) {
		// TODO Auto-generated method stub
		String res = "";
		for (int i = 0; i < newNonTouching.length; i++) {
			res += newNonTouching[i];
		}
//		System.out.println(res);

		return res;
	}

	private static int numberOfBrackets(String s) {
		int res = 0;
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == '[')
				res += 1;
		return res;

	}
}
