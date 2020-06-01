package ui;

import java.util.*;

public class Resolution {
    private List<Clause> entry = new ArrayList<>();
    private List<Clause> sos = new ArrayList<>();
    private List<Clause> generated = new ArrayList<>();
    private List<Clause> knowledge = new ArrayList<>();
    private List<Clause> goal = new ArrayList<>();
    private int id = 1;
    private boolean verbose;

    public Resolution(List<String> lines, String goal, boolean verbose) {
        this.verbose = verbose;
        setEntry(lines);
        setGoal(goal);
    }

    private Clause extractClause (String line, int id) {
        String[] parts = line.split(" v ");
        Set<Atom> atoms = new HashSet<>();
        for(String p : parts) {
            Atom atom;
            if(p.startsWith("~")) {
                String name = p.substring(1);
                atom = new Atom(name, false);
            }else{
                atom = new Atom(p, true);
            }
            atoms.add(atom);
        }
        return new Clause(atoms, id, 0, 0);
    }

    private void setEntry(List<String> list) {
        String line;
        for(int i=0; i <list.size(); i++) {
            line = list.get(i);
            this.entry.add(extractClause(line, id++));
            this.knowledge.add(extractClause(line, id));
        }
    }

    private void setGoal(String str) {
        String[] parts = str.split(" v ");
        for(String p : parts) {
            Set<Atom> atoms1 = new HashSet<>(); //jedinicne klauze
            Set<Atom> atoms2 = new HashSet<>();
            Atom atom;
            if(p.startsWith("~")) {
                String name = p.substring(1);
                atom = new Atom(name.trim(), true); //suprotno, jer negiramo cilj
            }else{
                atom = new Atom(p.trim(), false);
            }
            atoms1.add(atom);
            atoms2.add(atom);
            this.sos.add(new Clause(atoms1,id++,0,0));
            this.goal.add(new Clause(atoms2,id,0,0));
        }
    }

    public boolean plResolution () {
        if(verbose) print();
        while(true) {
            Clause clause = selectClauses();
            if(clause == null) return false;
            if(clause.getAtoms().size() == 0) {
                if(verbose) System.out.println(clause);
                return true;
            }
            if(clause.checkIfTautology()) continue;
            if(verbose) System.out.println(clause);
            sos.add(clause);
        }
        //dodat generated u klauzule
    }

    private boolean compare(List<Clause> old, List<Clause> generated) {
        Set<Atom> o = new HashSet<>();
        Set<Atom> g = new HashSet<>();

        for(Clause c : old) {
            o.addAll(c.getAtoms());
        }

        for(Clause c : generated) {
            g.addAll(c.getAtoms());
        }
        return o.containsAll(g);
    }

    private Clause selectClauses() {

        for(Clause s1 : sos) {
            for(Clause s2 : entry) {
                Map.Entry<Atom, Atom> at = s1.resolveClauses(s2);
                if(at != null) {   //nasli smo klauzule koje se mogu razriješiti
                    sos.remove(s1);
                    entry.remove(s2);
                    return resolve(s1, s2, at);
                }
            }for(Clause s3 : sos) {
                Map.Entry<Atom, Atom> at = s1.resolveClauses(s3);
                if(at != null) {   //nasli smo klauzule koje se mogu razriješiti
                    sos.remove(s1);
                    entry.remove(s3);
                    return resolve(s1, s3, at);
                }
            }
        }
        return null;
    }

    private Clause resolve (Clause first, Clause second, Map.Entry<Atom, Atom> atom) {
        Set<Atom> firstClause = first.getAtoms();
        firstClause.remove(atom.getKey());
        Set<Atom> secondClause = second.getAtoms();
        secondClause.remove(atom.getValue());
        Set<Atom> clause = new HashSet<>();
        clause.addAll(secondClause);
        clause.addAll(firstClause);
        Clause cl1 = new Clause(clause, id++, second.getId(), first.getId());
        Clause cl2 = new Clause(clause, id, second.getId(), first.getId());
        if(! cl2.checkIfTautology()) this.generated.add(cl2);
        return cl1;
    }


    public void print() {
        for(Clause e : entry) {
            System.out.println(e);
        }
        System.out.println("=============");
        for(Clause g : sos) {
            System.out.println(g);
        }
        System.out.println("=============");
    }
}
