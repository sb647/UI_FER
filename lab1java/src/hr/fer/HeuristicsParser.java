package hr.fer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HeuristicsParser {

    public  HeuristicsParser () {

    }

    public Heuristics parse (String string) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(new File(string)));

        String st;
        Map<String, Double> map = new HashMap<>();
        while ((st = br.readLine()) != null){
            if(st.startsWith("#")) {
                continue;
            }
            String[] parts = st.split(":");
            map.put(parts[0].trim(), Double.valueOf(parts[1].trim()));
        }
        return new Heuristics(map);
    }
}
