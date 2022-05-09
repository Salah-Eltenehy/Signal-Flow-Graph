package com.example.app.Graph;

import java.util.ArrayList;
import java.util.List;

import com.example.app.SolveEquation.Solve;

//import org.graalvm.compiler.graph.Graph_OptionDescriptors;

public class ForwardPaths {

	Graph graph;

	public ForwardPaths(Graph graph) {
		this.graph = graph;
	}

	public void getAllPaths(int source, int destination) {
		
		boolean[] isVisited = new boolean[graph.numberOfVertices];
		ArrayList<Integer> pathList = new ArrayList<>();
		// add source to path[]
		pathList.add(source);
		// Call recursive utility
		System.out.println("source is " + source + " destination is "+ destination);
		getAllForwardPaths(source, destination, isVisited, pathList);
		
	}

	private void getAllForwardPaths(Integer u, Integer d, boolean[] isVisited, ArrayList<Integer> localPathList) {
		
		if (u.equals(d)) {
			
			int res = findGain(localPathList);

			this.graph.forwardPathsGains[graph.numOfForwardPaths++] = res;
			this.graph.forwardPaths.add((List<Integer>) localPathList.clone());
			Solve.ans += (List<Integer>) localPathList.clone();
			System.out.println("ans is " + Solve.ans);
			// if match found then no need to traverse more till depth
		}
		// Mark the current node
		isVisited[u] = true;

		// Recur for all the vertices
		// adjacent to current vertex
		for (ArrayList<Integer> i : graph.graph[u]) {
			if (!isVisited[i.get(0)]) {
				// store current node
				// in path[]
				localPathList.add(i.get(0));
				getAllForwardPaths(i.get(0), d, isVisited, localPathList);
				// remove current node
				// in path[]
				localPathList.remove(i.get(0));
			}
		}
		// Mark the current node
		isVisited[u] = false;
	}

	private int findGain(List<Integer> path) {
		int result = 1;
		for (int i = 0; i < path.size() - 1; i++) {
			int v1 = path.get(i);
			int v2 = path.get(i + 1);
			ArrayList<ArrayList<Integer>> arrayList = graph.graph[v1];
			for (int j = 0; j < arrayList.size(); j++) {
				if (arrayList.get(j).get(0) == v2) {

					result *= arrayList.get(j).get(1);
				}
			}
		}
		return result;
	}

	/*public void print() {
		for (int i = 0; i < graph.numOfForwardPaths; i++) {
			System.out.println(graph.forwardPaths.get(i)+ " Gain = " + graph.forwardPathsGains[i]);
		}
	}*/
}
