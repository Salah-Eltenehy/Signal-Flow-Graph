package com.example.app.Graph.LoopsAlgorithm;

import java.util.List;

import com.example.app.Graph.Graph;

/**
 * Testfile for elementary cycle search.
 *
 * @author Frank Meyer
 *
 */
public class TestCycles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Graph g = new Graph(4);
		g.addEdge(0, 1, 1);
		g.addEdge(1, 2, 2);
		g.addEdge(2, 0, 3);
		g.addEdge(2, 0, 4);
		g.addEdge(2, 1, 5);
		g.addEdge(1, 3, 6);

		ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(g.graph);
		List cycles = ecs.getElementaryCycles();

		for (int i = 0; i < cycles.size(); i++) {
			List cycle = (List) cycles.get(i);
			for (int j = 0; j < cycle.size(); j++) {
				Integer node = (Integer) cycle.get(j);
				if (j < cycle.size() - 1) {
					System.out.print(node + " -> ");
				} else {
					System.out.print(node);
				}
			}
			System.out.print("\n");
		}
	}

}
