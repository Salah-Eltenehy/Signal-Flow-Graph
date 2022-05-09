package com.example.app.Graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.example.app.Graph.LoopsAlgorithm.ElementaryCyclesSearch;

public class Graph{
	
	int numberOfVertices;
	int numOfForwardPaths;
	int source;
	int destination;
	private final int MaxLoops = 1000;
	private final int MaxPaths = 1000;
    int numOfLoops;
	public ArrayList<ArrayList<Integer>>[] graph;
	public ArrayList<List<Integer>> forwardPaths;
	public int [] forwardPathsGains;
	public ArrayList<ArrayList<Integer>[]>[] deltaI;
	public int isolatedCount = 0;
	public ArrayList<Integer>[] loops;
	int[] loopsGain;
	public ArrayList<ArrayList<ArrayList<Integer>[]>> singleAndNonTouching;
	public Hashtable<String, Integer> allGains = new Hashtable<String, Integer>();
	public Graph(int vertices) {
		this.deltaI = new ArrayList[100];
		for (int i=0; i<100; i++)
			this.deltaI[i] = new ArrayList<>();
		this.numberOfVertices = vertices;
		this.numOfForwardPaths = 0;
		this.graph = new ArrayList[vertices];
		this.loopsGain = new int[MaxLoops];
		this.forwardPaths = new ArrayList<List<Integer>>() ;
		this.forwardPathsGains = new int[MaxPaths];
		this.numOfLoops = 0;
		for (int i = 0; i < vertices; i++) {
			this.graph[i] = new ArrayList<>();

		}

	}

	/** Add Edge from edge1 to edge2 */
	public void addEdge(int v1, int v2, int gain) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		arrayList.add(v2);
		arrayList.add(gain);
		/** Add edg2 to edge1's list */
		graph[v1].add(arrayList);

	}
	

}
