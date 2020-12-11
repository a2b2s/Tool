package decision_table;

public class Condition implements Comparable<Condition>{

    private int ID;
    private String name;

    public Condition(int ID, String name) {
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
        if (object instanceof Condition){
            Condition condition = (Condition)object;
            return (this.getID() == condition.getID()) && (this.getName().equalsIgnoreCase(condition.getName()));
        }

        return false;
    }
    @Override
    public int hashCode() {
        int result = ID;
        result = 31 * result + name.hashCode();
        return result;
    }
    @Override
    public int compareTo(Condition condition) {
        return this.getID() - condition.getID();
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName().charAt(0) + "[" + ID + "] " + name;
    }
}
