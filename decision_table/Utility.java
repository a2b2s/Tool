package decision_table;

public class Utility {

    public Boolean stringToBoolean(String value){
        if (value.matches("[tTyY1]"))
            return true;
        else if (value.matches("[fFnN0]"))
            return false;
        else
            return null;
    }

    public String[] stringToArray(String string){
        String[] arr = new String[string.length()];

        for (int i = 0; i < arr.length; i++)
            arr[i] = string.substring(i, i+1);

        return arr;
    }

    public boolean compareTwoBooleans(Boolean b1, Boolean b2){
        if (b1 == null || b2 == null)
            return true;
        else
            return b1.equals(b2);
    }
}
