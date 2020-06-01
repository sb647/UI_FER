package ui;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class Clause {

    private Set<Atom> atoms;
    private int id;
    private int parent1;
    private int parent2;

    public Clause() {

    }

    public Clause(Set<Atom> atoms, int id, int parent1, int parent2) {
        this.atoms = atoms;
        this.id = id;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public Clause(Clause c) {
        this.atoms = c.getAtoms();
        this.id = c.getId();
        this.parent1 = c.getParent1();
        this.parent2 = c.getParent2();

    }

    public Set<Atom> getAtoms() {
        return atoms;
    }

    public int getId() {
        return id;
    }

    public int getParent1() {
        return parent1;
    }

    public int getParent2() {
        return parent2;
    }

    public boolean checkIfTautology() {
        for(Atom a1 : this.getAtoms()) {
            for(Atom a2 : this.getAtoms()) {
                if (a1.resolve(a2)) return true;
            }
        }
        return false;
    }

    public Clause checkIfSubset(Clause clause) {
        if (clause.getAtoms().containsAll(this.atoms)) {
            return clause;
        }
        else if(this.atoms.containsAll(clause.getAtoms())) {
            return this;
        }
        return null;
    }

    //resolve i vrati po kojem atomu
    public Map.Entry<Atom, Atom> resolveClauses (Clause clause) {
        Map.Entry<Atom, Atom> result;
        for(Atom a1 : this.atoms) {
            for (Atom a2 : clause.getAtoms()) {
                if (a1.resolve(a2)) return new AbstractMap.SimpleEntry<>(a1, a2);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String parents = (parent1 == 0 && parent2 == 0) ? "" : ("("+parent1+", "+parent2+")");
        if(atoms.isEmpty()) return (id +". NIL "+ parents);

        StringBuilder sb = new StringBuilder();
        for(Atom a : atoms) {
            sb.append(a);
            sb.append(" v ");
        }
        String clause = sb.toString().substring(0, sb.length()-3);
        return (id + ". "+clause+" "+parents);
    }
}
