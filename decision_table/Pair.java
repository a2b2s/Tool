package decision_table;

public class Pair implements Comparable<Pair>{

    private Object value;
    private Boolean aBoolean;

    public Pair(Object value, Boolean aBoolean) {
        this.value = value;
        this.aBoolean = aBoolean;
    }

    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public Boolean getaBoolean() {
        return aBoolean;
    }
    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public Pair copyOfPair(){
        return new Pair(value, aBoolean);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (object instanceof Pair){
            Pair pair = (Pair)object;
            return (value.equals(pair.getValue()) && Utility.compareTwoBooleans(aBoolean, pair.getaBoolean()));
        }

        return false;
    }
    @Override
    public int compareTo(Pair pair) {
        if (pair.getValue() instanceof Action)
            return ((Action)this.getValue()).compareTo((Action) pair.getValue());
        else if (pair.getValue() instanceof Condition)
            return ((Condition)this.getValue()).compareTo((Condition) pair.getValue());

        return 0;
    }
    @Override
    public String toString() {
        return value + ": " + aBoolean;
    }
}
