package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RuntimeDictValue extends RuntimeValue {
    HashMap<String, RuntimeValue> dict;

    public RuntimeDictValue(HashMap<String, RuntimeValue> v) {
        dict = v;
    }

    @Override
    protected String typeName() {
        return "dictionary";
    }

    public String showInfo() {
        return toString();
    }

    @Override
    public String toString() {
        String setString = "";
        int counter = 0;
        setString = setString + "{";

        for (Map.Entry<String, RuntimeValue> entry : dict.entrySet()) {
            String keyword = entry.getKey().toString();
            RuntimeValue val = entry.getValue();

            if (counter < dict.size()-1){
                setString = setString + "\'" + keyword + "\'" + ": " + val.showInfo() + ", ";
            } else {
                setString = setString + "\'" + keyword + "\'" + ": " + val.showInfo();
            }
            counter++;
        }
        setString = setString + '}';
        return setString;
        //return String.valueOf(dict);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (dict.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return what;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (dict.size() == 0) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }

    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (dict.containsKey(v.getStringValue(null, where))) {
            RuntimeValue value = dict.get(v.getStringValue(null, where));
            return value;
        } else {
            String errorMsg = "Dictionary key " + v.getStringValue(null, where) + " undefined!";
            runtimeError(errorMsg, where);
            return null;
        }
    }

    @Override
    public void evalAssignElem(RuntimeValue v, RuntimeValue v2, AspSyntax where) {
        String keyword = v.getStringValue("assign dict", where);
        dict.put(keyword, v2);
    }
}
