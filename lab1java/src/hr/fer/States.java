package hr.fer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class States {

    private String start;
    private List<String> goal;
    private Map<String, HashMap<String,Double>> transitions;

    public States () {

    }
    public States(String start, List<String> goal, Map<String, HashMap<String, Double>> transitions) {
        this.start = start;
        this.goal = goal;
        this.transitions = transitions;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public List<String> getGoal() {
        return goal;
    }

    public void setGoal(List<String> goal) {
        this.goal = goal;
    }

    public Map<String, HashMap<String, Double>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, HashMap<String, Double>> transitions) {
        this.transitions = transitions;
    }

    @Override
    public String toString() {
        return "States{" +
                "start='" + start + '\'' +
                ", goal=" + goal +
                ", transitions=" + transitions +
                '}';
    }
}
