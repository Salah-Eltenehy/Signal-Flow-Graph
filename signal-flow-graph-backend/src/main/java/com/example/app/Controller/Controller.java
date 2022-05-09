package com.example.app.Controller;

import com.example.app.Graph.Graph;
import com.example.app.SolveEquation.Solve;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/map")
public class Controller {
    /*private String v;
    private ArrayList<String> arrayOfVertices, arrayOfGains;*/
    Graph g;
    boolean t=true;
    /** Empty constructor */
    Controller() {
    }

    @PostMapping("/calculate")
    public String resultOfComputation(@RequestBody LinkedHashMap<String, ArrayList<String>> map) {

        for (Map.Entry<String, ArrayList<String>> entry: map.entrySet())
        {
            System.out.print(entry.getKey()+"  ");
            for (String s2: entry.getValue())
                System.out.print(s2+"    ");
            System.out.println();
        }
        System.out.println("\n\n\n\n");
        g = new Graph((map.size()/2) +2);
        //arrayOfVertices = arrayOfGains = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry: map.entrySet())
        {
            String vertex = entry.getKey();
            ArrayList<String> array = entry.getValue();
            if (!vertex.contains("g"))
            {
                saveData(vertex, array, map.get(vertex+"g"));
            }else
            {
                continue;
            }
        }
        Solve solve = new Solve(g);
        String res = solve.getResult();
        Solve.ans = "";
        System.out.println(res);
        return res;
    }
    private void saveData(String v, ArrayList<String> arrayOfVertices, ArrayList<String> arrayOfGains)
    {
        //System.out.println(arrayOfGains+"    "+ arrayOfVertices);
        /*for (int i=0; i<arrayOfVertices.size(); i++)
            System.out.println(arrayOfGains.get(i) + "    "+arrayOfVertices.get(i));*/
        int vertex = Integer.parseInt(v);
        int[] arr = new int[arrayOfVertices.size()];
        int[] arrOfGains = new int[arrayOfGains.size()];
        for (int i=0; i<arrayOfVertices.size(); i++)
        {
            arr[i] = Integer.parseInt(arrayOfVertices.get(i));
            arrOfGains[i] = Integer.parseInt(arrayOfGains.get(i));
        }
        for (int i=0; i<arr.length; i++)
        {
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(arr[i]);
            arrayList.add(arrOfGains[i]);
           // System.out.println(vertex+"  "+arr[i]+"  "+arrOfGains[i]);
            /** Add edg2 to edge1's list */
            g.graph[vertex].add(arrayList);
        }
    }
}
/*
*
* System.out.println("here");
        //@RequestBody Map<String, String[]> map
        for (String s: map.keySet())
            System.out.println(s);
        for (String[] s: map.values())
            for (String s2: s)
                System.out.println(s2);*/