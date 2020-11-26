package decision_table;

public class Action implements Comparable<Action>{

    private int ID;
    private String name;

    public Action(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object == null)
            return false;
        if (object instanceof Action){
            Action action = (Action)object;
            return (this.getID() == action.getID()) && (this.getName().equalsIgnoreCase(action.getName()));
        }

        return false;
    }
    @Override
    public int compareTo(Action action) {
        return this.getID() - action.getID();
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName().charAt(0) + "[" + ID + "] " + name;
    }
}
