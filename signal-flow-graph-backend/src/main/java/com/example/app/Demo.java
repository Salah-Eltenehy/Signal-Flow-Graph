package com.example.app;

import com.example.app.Graph.ForwardPaths;
import com.example.app.Graph.Graph;
import com.example.app.Graph.Isolated;
import com.example.app.Graph.Loops;
import com.example.app.SolveEquation.Solve;

import java.util.ArrayList;

public class Demo {
	public static void demo() {
		Graph graph = new Graph(6);
//		g.addEdge(0, 1, 1);
//		g.addEdge(1, 2, 1);
//		g.addEdge(2, 1, 3);
//		g.addEdge(2, 3, 2);
//		g.addEdge(3, 4, 2);
//		g.addEdge(4, 3, 5);
//		g.addEdge(4, 5, 6);
//		g.addEdge(5, 6, 6);
//		g.addEdge(6, 7, 1);
//		g.addEdge(7, 6, 4);
//		g.addEdge(5, 8, 4);
//		g.addEdge(8, 5, 4);
//		g.addEdge(6, 5, 2);

		/*
		 * g.addEdge(0, 1, 1); g.addEdge(1, 2, 2); g.addEdge(2, 3, 4); g.addEdge(3, 4,
		 * 1); g.addEdge(4, 5, 4); g.addEdge(5, 6, 6); g.addEdge(6, 7, 7); g.addEdge(7,
		 * 1, 2); g.addEdge(7, 6, 3); g.addEdge(2, 1, 4); g.addEdge(4, 3, 5);
		 */
		/*
		 * g.addEdge(0, 1, 1); g.addEdge(1, 2, 2); g.addEdge(2, 3, 3); g.addEdge(3, 7,
		 * 2); g.addEdge(1, 5, 4); g.addEdge(5, 6, 6); g.addEdge(6, 7, 1); g.addEdge(6,
		 * 5, 2); g.addEdge(5, 4, 3); g.addEdge(3, 2, 5); g.addEdge(2, 1, 9);
		 * g.addEdge(4, 8, 20); g.addEdge(8, 4, 4);
		 *//*
			 * graph.addEdge(0, 1, 1); graph.addEdge(1, 2, 1); graph.addEdge(2, 3, 3);
			 * graph.addEdge(2, 2, 1); graph.addEdge(2, 1, 1); graph.addEdge(3, 4, 5);
			 * graph.addEdge(4, 3, 3); graph.addEdge(2, 4, 6); graph.addEdge(4, 5, 9);
			 * graph.addEdge(5, 4, 1);
			 */
		graph.addEdge(1, 2, 1);
		graph.addEdge(2, 3, 1);
		graph.addEdge(3, 4, 1);
		graph.addEdge(4, 5, 1);
		graph.addEdge(3, 2, 1);
		graph.addEdge(4, 2, 1);
		graph.addEdge(2, 2, 1);
		graph.addEdge(1, 3, 1);
		/*
		 * g.addEdge(1, 2, 1); g.addEdge(1, 2, 1); g.addEdge(1, 2, 1);
		 */

		System.out.println("Following are all different paths from " + 0 + " to " + 7);
		ForwardPaths fP = new ForwardPaths(graph);
		Loops loops = new Loops(graph);
		fP.getAllPaths(1, 5);
		// fP.print();
		loops.getLoops();
		Isolated iso = new Isolated(graph);
		iso.getAllIsolated();
		int temp = 0;
		String[] arr = new String[100];
		int rs = 0;
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
			Solve.ans += deltaI;
			if (i < graph.forwardPaths.size() - 1)
				Solve.ans += "$";
			rs += graph.forwardPathsGains[i] * deltaI;
		}

		int rs2 = 1;
		for (int i = 0; i < graph.singleAndNonTouching.size(); i++) {
			for (int j = 0; j < graph.singleAndNonTouching.get(i).size(); j++) {
				if (numberOfBrackets(toString(graph.singleAndNonTouching.get(i).get(j))) % 2 == 0)
					rs2 += graph.allGains.get(toString(graph.singleAndNonTouching.get(i).get(j)));
				else
					rs2 -= graph.allGains.get(toString(graph.singleAndNonTouching.get(i).get(j)));
			}

		}
		Solve.ans += "#";
		Solve.ans += rs / rs2;
		System.out.println("Result: " + rs + "    " + rs2);
		System.out.println(Solve.ans);
		/*
		 * int t1 = 0; for (int i=0; i<g.forwardPaths.size(); i++) { int deltaI = 1; for
		 * (int j=0; j<g.isolatedCount; j++)
		 * 
		 * //System.out.println("one: "+g.forwardPathsGains[i] +
		 * "  two: "+g.allGains.get(arr[0])); // t1 += g.forwardPathsGains[i] *
		 * g.allGains.get(arr[i]); } System.out.println("Result" + t1);
		 */

	}

	private static String toString(ArrayList<Integer>[] newNonTouching) {
		// TODO Auto-generated method stub
		String res = "";
		for (int i = 0; i < newNonTouching.length; i++) {
			res += newNonTouching[i];
		}
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
