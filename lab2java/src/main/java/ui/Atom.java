package ui;

public class Atom {

    private String name;
    private boolean type;

    public Atom(String name, boolean type) {
        this.name = name;
        this.type = type;
    }

    public boolean resolve(Atom a) {
        if(a.getName().equalsIgnoreCase(this.name) && a.getType() != this.getType()) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public boolean getType() {
        return type;
    }


    public boolean equals(Object o) {
        Atom atom = (Atom) o;
        return (this.getType() == atom.getType() && this.name.equals(atom.getName()));
    }

    @Override
    public String toString() {
        String sign = this.type == false ? "~" : "";
        return (sign + name);
    }
}
