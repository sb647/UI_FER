package hr.fer;

import java.util.Map;

public class Heuristics {

    private Map<String, Double> heuristics;

    public Heuristics() {

    }

    public Heuristics(Map<String, Double> heuristics) {
        this.heuristics = heuristics;
    }

    public Map<String, Double> getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(Map<String, Double> heuristics) {
        this.heuristics = heuristics;
    }

    @Override
    public String toString() {
        return "Heuristics{" +
                "heuristics=" + heuristics +
                '}';
    }
}
