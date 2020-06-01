package hr.fer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser  {

    private File file;

    public Parser (){
    }

    public static States parse(String string) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(new File(string)));
        String st;
        List<String> lines = br.lines().filter(l ->!l.startsWith("#")).collect(Collectors.toList());
        States states = new States();
        states.setStart(lines.get(0));
        List<String> goals = Arrays.asList(lines.get(1).split(" "));
        states.setGoal(goals);
        HashMap<String, HashMap<String, Double>> map = new HashMap<String, HashMap<String, Double>>();
        for(int i = 2; i < lines.size(); i++){
            String line = lines.get(i);
            String[] parts1 = line.split(":");
            List<String> parts2 = Arrays.asList(parts1[1].trim().split(" "));
            HashMap<String, Double> submap = new HashMap<String, Double>();
            for(String p : parts2) {
                String[] parts3 = p.split(",");
                submap.put(parts3[0], Double.valueOf(parts3[1]));
            }
            map.put(parts1[0], submap);
        }
        states.setTransitions(map);
        return states;
    }




}
