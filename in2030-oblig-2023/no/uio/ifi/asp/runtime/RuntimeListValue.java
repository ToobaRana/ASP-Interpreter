package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    public ArrayList<RuntimeValue> listValue = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> v) {
        listValue = v;
    }

    @Override
    protected String typeName() {
        return "[]";
    }
    
    @Override
    public String showInfo() {
        return listValue.toString();
    }

    @Override
    public String toString() {
        return listValue.toString();
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return what;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (listValue == null) {
            return false;
        }
        return true;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(listValue.size());
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (listValue == null) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("Error for '==' ", where);
        return null;
    }

    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if(v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        runtimeError("Error for '!=' ", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            ArrayList<RuntimeValue> newList = new ArrayList<>();
            long time = (long)v.getIntValue("*", where);
            
            int counter = 0;
            while (counter < time) {
                newList.addAll(listValue);
                counter++;
            }
            return new RuntimeListValue(newList);
        }
        runtimeError("Type error for *.", where);
        return null;
    }

    public ArrayList<RuntimeValue> getListValue() {
        if (listValue.isEmpty()){
            return null;
        } else {
            return listValue;
        }
    }

    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        RuntimeValue v1 = null;
        if (v instanceof RuntimeIntValue) {
            int i = (int) (v.getIntValue("[]", where));
            v1 = listValue.get(i);
        }
        return v1;
    }

    public int getSize() {
        return listValue.size();
    }

    @Override
    public void evalAssignElem(RuntimeValue v, RuntimeValue v2, AspSyntax where) {
        if (v instanceof RuntimeIntValue){
            long midint = v.getIntValue("assign list", where);
            int position = (int)midint;
            listValue.set(position, v2);
        } else {
            runtimeError("Type error for assignElem.", where);
        }
    }
}
