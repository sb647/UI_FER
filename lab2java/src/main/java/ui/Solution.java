package ui;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {

	private List<String> entry = new ArrayList<>();
	private String goal;
	private boolean verbose;
	private static final String EXIT = "exit";
	private static final String RESOLUTION = "resolution";
	private static final String TEST = "cooking_test";
	private static final String INTERACTIVE = "cooking_interactive";
	private static final String VERBOSE = "verbose";

	public Solution() {

	}

	public Solution(List<String> entry, String goal) {
		this.entry = entry;
		this.goal = goal;
	}

	public boolean removeEntry(String clause) {
		//a v b == b v a
		List<String> parts1 = Arrays.asList(clause.split(" v "));
		Collections.sort(parts1);
		for(String e : entry) {
			List<String> parts2 = Arrays.asList(e.split(" v "));
			Collections.sort(parts2);
			if(parts1.equals(parts2)) {
				entry.remove(e);
				return true;
			}
		}
		return false;
	}

	private void readCommand(String line) {
		if(line.equalsIgnoreCase(EXIT)){
			System.exit(0);
		}
		if(! (line.endsWith("?") || line.endsWith("+") || line.endsWith("-"))) {
			System.out.println("Unknown command");
		}
		if(line.endsWith("+")) {
			String str = line.substring(0, line.length()-1).trim();
			this.entry.add(str);
			System.out.println("added " +str);
			return;
		}
		else if(line.endsWith("-")) {
			String str = line.substring(0, line.length()-1).trim();
			boolean flag = this.removeEntry(str);
			if (flag) {
				System.out.println("removed " +str);
			} else {
				System.out.println(str + " is unknown");
			}
			return;
		}else if(line.endsWith("?")) {
			String str = line.substring(0, line.length()-1);
			Resolution resolution = new Resolution(this.entry, str, this.verbose);
			String flag = resolution.plResolution() ? "true" : "unknown";
			if(this.verbose) System.out.println("=============");
			System.out.println(str +" is "+ flag);
			return;
		}
	}

	private void readTestCommand(String line) {
		String str = line.substring(0, line.length()-1).trim();
		if(line.endsWith("+")) {
			this.entry.add(str);
			return;
		}
		else if(line.endsWith("-")) {
			this.removeEntry(str);
			return;
		}else if(line.endsWith("?")) {
			Resolution resolution = new Resolution(this.entry, str, this.verbose);
			String flag = resolution.plResolution() ? "true" : "unknown";
			if(this.verbose) System.out.println("=============");
			System.out.println(str +" is "+ flag);
		}
	}

	private void interaction(){
		Scanner sc = new Scanner(System.in);
		String line;
		while(true) {
			System.out.println("Please enter your query");
			System.out.print(">>> ");
			line = sc.nextLine().trim().toLowerCase();
			readCommand(line);
		}
	}

	public static void main(String[] args) {
		Parser parser = new Parser();
		List<String> list = new ArrayList<>();
		try{
			list = parser.parse(args[1]);
		}catch(FileNotFoundException ex) {

		}
		boolean verbose = false;
		try {
			if(args[2].equals(VERBOSE) || args[3].equals(VERBOSE))
				verbose = true;
		} catch (IndexOutOfBoundsException ex) {}


		if(args[0].equals(RESOLUTION)) {
			Solution s = new Solution();
			s.verbose = verbose;
			Resolution resolution = new Resolution(list.subList(0, list.size()-1),list.get(list.size()-1), verbose);
			String flag = new String();
			flag = resolution.plResolution() ? "true" : "unknown";
			if(verbose) System.out.println("=============");
			System.out.println(list.get(list.size()-1)+" is "+ flag);
		}

		if(args[0].equals(INTERACTIVE)) {
			Solution s = new Solution(list,"");
			s.verbose = verbose;
			s.interaction();
		}

		if(args[0].equals(TEST)) {
			Solution s = new Solution(list,"");
			s.verbose = verbose;
			List<String> lines = new ArrayList<>();
			try{
				lines= parser.parse(args[2]);
			}catch (FileNotFoundException ex) {
			}
			for(String l : lines) {
				s.readTestCommand(l);
			}
		}
	}
}
