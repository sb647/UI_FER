package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Parser {

    public Parser(){

    }

    public static List<String> parse(String string) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(new File(string)));
        List<String> lines = br.lines().filter(l -> !l.startsWith("#")).collect(Collectors.toList());
        ListIterator<String> iterator = lines.listIterator();
        while (iterator.hasNext())
        {
            iterator.set(iterator.next().toLowerCase());
        }
        return lines;
    }
}
