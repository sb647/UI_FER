package hr.fer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser();
        HeuristicsParser hParser = new HeuristicsParser();
        States states = new States();
        Heuristics heuristics = new Heuristics();

        try{
            states = Parser.parse(args[0]);
            heuristics = hParser.parse(args[1]);


        }catch (Exception ex) {

        }
        Algorithms alg = new Algorithms();
//        System.out.println("Start state: " + states.getStart());
//        System.out.println("End state(s) : " + states.getGoal());
//        System.out.println("Running bfs:");
//        Map.Entry<List<String>,Integer> r1 = Algorithms.breadthFirstSearch(states);
//        System.out.println("States visited = " + r1.getValue());
//        System.out.println(r1.getKey());
//
//        System.out.println("Running ucs:");
//        Map.Entry<List<Map.Entry<String, Double>>,Integer> r2 = Algorithms.uniformCostSearch(states);
//        System.out.println("States visited = " + r2.getValue());
//        System.out.println("Found path of length "+ r2.getKey().size()+ " with total cost " + r2.getKey().get(r2.getKey().size()-1).getValue() + " :");
//        System.out.println(r2.getKey());
//
//
//        System.out.println("Running astar:");
//        Map.Entry<List<Map.Entry<String, Double>>,Integer> r3 = Algorithms.aStar(states,heuristics);
//        System.out.println("States visited = " + r3.getValue());
//        System.out.println("Found path of length "+ r3.getKey().size()+ " with total cost " + r3.getKey().get(r3.getKey().size()-1).getValue() + " :");
//        System.out.println(r3.getKey());

        boolean flag1 = true;
        for(String k : heuristics.getHeuristics().keySet()) {
            States st = new States();
            st.setStart(k);
            st.setGoal(states.getGoal());
            st.setTransitions(states.getTransitions());
            List<Map.Entry<String, Double>> result = Algorithms.uniformCostSearch(st).getKey();
            if(heuristics.getHeuristics().get(k) >  result.get(result.size()-1).getValue()) {
                System.out.println("[ERR] h("+ k + ") > h* : " + heuristics.getHeuristics().get(k) + " > " + result.get(result.size() - 1).getValue());
                flag1 = false;
            }
        }
        System.out.println(flag1 == true ? "Heuristic is optimistic" : "Heuristic is not optimistic");

        boolean flag2 = true;
        for(String k : heuristics.getHeuristics().keySet()) {
            HashMap<String, Double> transitions = states.getTransitions().get(k);

            for(String t : transitions.keySet()) {
                if(heuristics.getHeuristics().get(k) > heuristics.getHeuristics().get(t) + states.getTransitions().get(k).get(t)) {
                    System.out.println("[ERR] h("+ k + ") > h(" + t +") + c :" + heuristics.getHeuristics().get(k)+ " > "+ heuristics.getHeuristics().get(t) +" + " +  states.getTransitions().get(k).get(t));
                    flag2 = false;
                }
            }

        }

        System.out.println(flag2 == true ? "Heuristic is consistent" : "Heuristic is not consistent");


    }
}
