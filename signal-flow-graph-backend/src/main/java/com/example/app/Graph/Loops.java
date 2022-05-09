package com.example.app.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import com.example.app.Graph.LoopsAlgorithm.ElementaryCyclesSearch;
import com.example.app.SolveEquation.Solve;

public class Loops {

	Graph graph;
	// Different types of non touching -> Which non touching loops -> the loops

	public Loops(Graph graph) {
		this.graph = graph;
		this.graph.singleAndNonTouching = new ArrayList<ArrayList<ArrayList<Integer>[]>>();
	}

	public void getLoops() {
		ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(this.graph.graph);
		List cycles = ecs.getElementaryCycles();
		getSelfLoops(cycles);
		Solve.ans += "#";
		VectorToArrayList(cycles);
		getLoopsGain();
		printLoops();
		Solve.ans += "#";
		getNonTouching();
	}

	private void VectorToArrayList(List cycles) {
		this.graph.numOfLoops = cycles.size();
		this.graph.loops = new ArrayList[this.graph.numOfLoops];
		for (int i = 0; i < cycles.size(); i++) {
			this.graph.loops[i] = new ArrayList<>();
			List cycle = (List) cycles.get(i);
			cycle.add(cycle.get(0));
			Solve.ans += cycle;
			for (int j = 0; j < cycle.size(); j++) {
				Integer node = (Integer) cycle.get(j);
				this.graph.loops[i].add(node);
			}
		}
	}

	private void getSelfLoops(List cycles) {
		for (int i = 0; i < this.graph.graph.length; i++) {
			for (int j = 0; j < this.graph.graph[i].size(); j++) {
				if (i == graph.graph[i].get(j).get(0)) {
					boolean alreadyFound = false;
					
					for (int k = 0; k < cycles.size(); k++) {
						List cycle = (List) cycles.get(k);
						if (cycle.size() == 2 && (int)cycle.get(0) == i) {
							alreadyFound = true;
							break;
						}
					}
					if (!alreadyFound) {
						 System.out.println("Self loop found");
						List cycle = new ArrayList<Integer>();
						cycle.add(i);
						cycles.add(cycle);
						break;
					}

				}
			}
		}
		// this.printLoops();
	}

	private void getLoopsGain() {

		for (int i = 0; i < this.graph.numOfLoops; i++) {
			ArrayList<ArrayList<Integer>>[] temp = this.graph.graph.clone();
			ArrayList<Integer> cycle = this.graph.loops[i];

			int gain = 1;
			for (int j = 1; j < cycle.size(); j++) {
				Integer prevNode = (Integer) cycle.get(j - 1);
				Integer node = (Integer) cycle.get(j);
				// System.out.println(prevNode + " " + node);
				for (int k = 0; k < temp[prevNode].size(); k++) {
					ArrayList<Integer> list = temp[prevNode].get(k);
					if (list.get(0) == node) {
						gain *= list.get(1);
						break;
					}
				}
			}
			graph.loopsGain[i] = gain;
		}
	}

	public void printLoops() {
		/*
		 * for (int i = 0; i < this.graph.numOfLoops; i++) { ArrayList<Integer> cycle =
		 * this.graph.loops[i]; for (int j = 0; j < cycle.size(); j++) { Integer node =
		 * (Integer) cycle.get(j); if (j < cycle.size() - 1) { System.out.print(node +
		 * " -> "); } else { System.out.print(node); } }
		 * System.out.println(" with gain " + this.graph.loopsGain[i]); }
		 */
	}

	private boolean IsNonTouching(ArrayList<Integer> loop1, ArrayList<Integer> loop2) {
//		System.out.println(loop1 + " " + loop2);
		for (int i = 0; i < loop1.size(); i++) {
			if (loop2.contains(loop1.get(i))) {
				return false;
			}
		}
		return true;
	}

	private boolean isNonTouchingHighOrder(ArrayList<Integer>[] ExistingNonTouching1,
			ArrayList<Integer>[] ExistingNonTouching2) {
		for (int i = 0; i < ExistingNonTouching1.length; i++) {
			for (int j = 0; j < ExistingNonTouching2.length; j++) {
				if (!IsNonTouching(ExistingNonTouching1[i], ExistingNonTouching2[j])) {
					return false;
				}
			}
		}
		return true;
	}

	// Different types of non touching -> Which non touching loops -> the loops
	// public ArrayList<ArrayList<ArrayList<Integer>[]>> nonTouching;
	private void getNonTouching() {

		ArrayList<ArrayList<Integer>[]> tempNonTouching = new ArrayList<ArrayList<Integer>[]>();
		for (int i = 0; i < graph.numOfLoops; i++) {
			ArrayList<Integer>[] loop = new ArrayList[1];
			loop[0] = graph.loops[i];

			tempNonTouching.add(loop);
			this.graph.allGains.put(toString(loop), graph.loopsGain[i]);
//			System.out.println(toString(loop) + " "+ gains.get(toString(loop)));
		}

		int currentIndex = 0;
		int largestNonTouching = 0;
		while (true) {
			int tempNonTouchingSizeBeforeAdding = tempNonTouching.size();
			if (currentIndex >= tempNonTouchingSizeBeforeAdding) {
				break;
			}

			ArrayList<Integer>[] currentNonTouching = tempNonTouching.get(currentIndex);
			for (int i = 0; i < tempNonTouchingSizeBeforeAdding; i++) {
				ArrayList<Integer>[] loops = tempNonTouching.get(i);
				int gain = 1;
				if (isNonTouchingHighOrder(currentNonTouching, loops)) {
					ArrayList<Integer>[] newNonTouching = new ArrayList[loops.length + currentNonTouching.length];
					int j = 0;
//					System.out.println(toString(loops) + " " + gains.get(toString(loops)));
					gain *= this.graph.allGains.get(toString(loops));
					for (j = 0; j < loops.length; j++) {
						newNonTouching[j] = loops[j];

					}
					for (int k = 0; k < currentNonTouching.length; k++) {
						newNonTouching[j++] = currentNonTouching[k];
					}

//					System.out.println(toString(currentNonTouching) + " " + gains.get(toString(currentNonTouching)));
					gain *= this.graph.allGains.get(toString(currentNonTouching));
//					System.out.println("NON TOUCHING");
//					for (int num = 0; num < newNonTouching.length; num++) {
//						System.out.print(newNonTouching[num] + " ");
//					}
//					System.out.println("\n________________________________");
					Arrays.sort(newNonTouching, new SortBy());
//					System.out.println(toString(newNonTouching) + " " + alreadyTaken(tempNonTouching, newNonTouching));
					if (!alreadyTaken(tempNonTouching, newNonTouching)) {
						this.graph.allGains.put(toString(newNonTouching), gain);
//						System.out.println(toString(newNonTouching)+ "  "+gain);

						tempNonTouching.add(newNonTouching);
						if (largestNonTouching < newNonTouching.length) {
							largestNonTouching = newNonTouching.length;
						}
					}
				}
			}
			currentIndex++;
		}
		if (largestNonTouching == 0) {
			this.graph.singleAndNonTouching.add(new ArrayList<ArrayList<Integer>[]>());
		}
		for (int i = 0; i < largestNonTouching; i++) {
			this.graph.singleAndNonTouching.add(new ArrayList<ArrayList<Integer>[]>());
		}

		for (int i = 0; i < tempNonTouching.size(); i++) {
			if (tempNonTouching.get(i).length - 1 != 0) {
				Solve.ans += toString(tempNonTouching.get(i));
				if (i < tempNonTouching.size() - 1)
					Solve.ans += "$";
			}

			this.graph.singleAndNonTouching.get(tempNonTouching.get(i).length - 1).add(tempNonTouching.get(i));
		}

		printNonTouching();
//		printNonTouchingGains(gains);

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

	private void printNonTouching() {
		// TODO Auto-generated method stub
		/*
		 * System.out.println("\t\tAll Non Touching\n"); for (int outer = 0; outer <
		 * this.graph.singleAndNonTouching.size(); outer++) { for (int i = 0; i <
		 * this.graph.singleAndNonTouching.get(outer).size(); i++) { String loop =
		 * toString(this.graph.singleAndNonTouching.get(outer).get(i));
		 * System.out.println(loop + "  " + this.graph.allGains.get(loop)); }
		 * System.out.println(); }
		 */
	}

	private boolean alreadyTaken(ArrayList<ArrayList<Integer>[]> tempNonTouching, ArrayList<Integer>[] newNonTouching) {
		// TODO Auto-generated method stub
		String newNon = toString(newNonTouching);
		for (int i = 0; i < tempNonTouching.size(); i++) {
			String loop = toString(tempNonTouching.get(i));
			if (newNon.equals(loop)) {
				return true;
			}
		}
		return false;
//		for (int i = 0; i < tempNonTouching.size(); i++) {
//			int count = 0;
////			System.out.println(tempNonTouching.get(i).length);
//
//			for (int j = 0; j < tempNonTouching.get(i).length; j++) {
//
//				if (tempNonTouching.get(i).length != newNonTouching.length) {
//					continue;
//				}
////				System.out.println(tempNonTouching.get(i)[j] + "  " + newNonTouching[j]);
//				for (int k = 0; k < newNonTouching.length; k++) {
//					if (tempNonTouching.get(i)[j].get(k).equals(newNonTouching[j].get(k))) {
//						count++;
//						break;
//					}
//				}
//				if (count == newNonTouching.length)
//					return true;
//			}
//
//		}
//		return false;
	}

}

class SortBy implements Comparator<ArrayList<Integer>> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
		return a.get(0) - b.get(0);
	}
}
