package hr.fer;

import javax.swing.text.html.parser.Entity;
import java.util.*;

public class Algorithms {

    public static Map.Entry<List<String>,Integer> breadthFirstSearch(States states) {
        LinkedList<String> queue = new LinkedList<>();
        List<String> closed = new ArrayList<>();
        Map<String, String> parents = new HashMap<>();
        queue.add(states.getStart());
        int visitedStates = 0;
        while(queue.size() != 0) {
            String state = queue.poll();
            visitedStates++;
            closed.add(state);
            if(states.getGoal().contains(state)) {
                return new AbstractMap.SimpleEntry<List<String>,Integer>(reconstructPath(states, state, parents),Integer.valueOf(visitedStates));
            }

            Map<String, Double> map = states.getTransitions().get(state);
            for(String k : map.keySet()) {
                if(! closed.contains(k)){
                    queue.add(k);
                    parents.put(k,state);
                }
            }
        }
        return new AbstractMap.SimpleEntry<List<String>,Integer>(new ArrayList<>(),Integer.valueOf(visitedStates));
    }

    public static List<String> reconstructPath (States states, String state, Map<String, String> parents) {
        List<String> result = new ArrayList<>();
        String current = state;
        while( ! current.equals(states.getStart())){
            result.add(current);
            current = parents.get(current); //nađi roditelja
        }

        result.add(states.getStart());
        Collections.reverse(result);
        return result;

    }

    public static Map.Entry<List<Map.Entry<String, Double>>,Integer> uniformCostSearch(States states) {
        LinkedList<Map.Entry<String, Double>> queue = new LinkedList<>();
        List<String> closed = new ArrayList<>();
        Map<Map.Entry<String, Double>, Map.Entry<String, Double>> parents = new HashMap<>();
        queue.add(new AbstractMap.SimpleEntry<String, Double>(states.getStart(),0.0));
        int visitedStates = 0;
        while(queue.size() != 0) {
            Map.Entry<String,Double> state = queue.poll();
            visitedStates++;
            closed.add(state.getKey());
            if(states.getGoal().contains(state.getKey())) {
                return new AbstractMap.SimpleEntry<List<Map.Entry<String, Double>>,Integer>(reconstructPath2(states, state, parents),Integer.valueOf(visitedStates));
            }

            Map<String, Double> map = states.getTransitions().get(state.getKey());
            for(Map.Entry<String, Double> k : map.entrySet()) {
                if(! closed.contains(k.getKey())){
                    Map.Entry<String, Double> novi = new AbstractMap.SimpleEntry<String, Double>(k.getKey(),k.getValue() + state.getValue());
                    queue.add(novi);
                    parents.put(novi,state);
                }

            }
            queue.sort(Map.Entry.comparingByValue());

        }
        return new AbstractMap.SimpleEntry<List<Map.Entry<String, Double>>,Integer>(new ArrayList<>(),Integer.valueOf(visitedStates));
    }

    private static List<Map.Entry<String, Double>> reconstructPath2(States states, Map.Entry<String, Double> state, Map<Map.Entry<String, Double>, Map.Entry<String, Double>> parents) {
        List<Map.Entry<String, Double>> result = new ArrayList<>();

        Map.Entry<String, Double> current = state;

        while(! current.getKey().equals(states.getStart())){
            result.add(current);
            current = parents.get(current); //nađi roditelja
        }

        result.add(new AbstractMap.SimpleEntry<String, Double>(states.getStart(),0.0));
        Collections.reverse(result);
        return result;

    }

    public static Map.Entry<List<Map.Entry<String, Double>>,Integer> aStar(States states, Heuristics heuristics) {
        LinkedList<Map.Entry<Map.Entry<String,Double>, Double>> queue = new LinkedList<>();
        List<String> closed = new ArrayList<>();
        Map<Map.Entry<String, Double>, Map.Entry<String, Double>> parents = new HashMap<>();
        queue.add(new AbstractMap.SimpleEntry<Map.Entry<String, Double>, Double>( new AbstractMap.SimpleEntry<String, Double>(states.getStart(),0.0),0.0));
        int visitedStates = 0;
        while(queue.size() != 0) {
            Map.Entry<Map.Entry<String,Double>, Double> state = queue.poll();
            visitedStates++;
            closed.add(state.getKey().getKey());
            if(states.getGoal().contains(state.getKey().getKey())) {
                return new AbstractMap.SimpleEntry<List<Map.Entry<String, Double>>,Integer>(reconstructPath2(states, state.getKey(), parents),Integer.valueOf(visitedStates));
            }

            Map<String, Double> map = states.getTransitions().get(state.getKey().getKey());
            for(Map.Entry<String, Double> k : map.entrySet()) {
                if(! closed.contains(k.getKey())){
                    Map.Entry<String, Double> novi = new AbstractMap.SimpleEntry<String, Double>(k.getKey(),k.getValue() + state.getKey().getValue());
                   double value = novi.getValue() + heuristics.getHeuristics().get(k.getKey());
                   queue.add(new AbstractMap.SimpleEntry<>(novi, Double.valueOf(value)));
                   parents.put(novi,state.getKey());
                }

            }
            queue.sort(Map.Entry.comparingByValue());


        }
        return new AbstractMap.SimpleEntry<List<Map.Entry<String, Double>>,Integer>(new ArrayList<>(),Integer.valueOf(visitedStates));
    }


    }



