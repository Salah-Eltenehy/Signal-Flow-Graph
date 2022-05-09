package com.example.app.Graph;

import java.util.ArrayList;
import java.util.List;

public class Isolated {
	Graph graph;

	public Isolated(Graph graph) {
		this.graph = graph;
	}

	public void getAllIsolated() {
		ArrayList<List<Integer>> fp = this.graph.forwardPaths;
		ArrayList<ArrayList<ArrayList<Integer>[]>> sT = this.graph.singleAndNonTouching;
		for (int i = 0; i < fp.size(); i++) {
			for (int j = 0; j < sT.size(); j++) {
				for (int k = 0; k < sT.get(j).size(); k++) {
					/*System.out.println(fp.get(i) + " " + toString(sT.get(j).get(k)) + " "
							+ isIsolated(fp.get(i), sT.get(j).get(k)) + "    i:" + i);*/
					if (isIsolated(fp.get(i), sT.get(j).get(k))) {
						this.graph.deltaI[i].add(sT.get(j).get(k));
						this.graph.isolatedCount++;
					}
				}
			}
		}
	}

	private boolean isIsolated(List<Integer> list, ArrayList<Integer>[] arrayLists) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < arrayLists.length; j++) {
				if (arrayLists[j].contains(list.get(i))) {
					return false;
				}
			}
		}
		return true;
	}

	private String toString(ArrayList<Integer>[] newNonTouching) {
		// TODO Auto-generated method stub
		String res = "";
		for (int i = 0; i < newNonTouching.length; i++) {
			res += newNonTouching[i];
		}
//		System.out.println(res);
		return res;
	}

}
