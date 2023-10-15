package no.uio.ifi.asp.runtime;

import java.util.HashMap;
import java.util.Map.Entry;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {

    HashMap<RuntimeStringValue, RuntimeValue> dictValue = new HashMap<>();

    public RuntimeDictValue(HashMap<RuntimeStringValue, RuntimeValue> v) {
        dictValue = v;
    }

    @Override
    String typeName() {
        return "Dictionary";
    }

    @Override
    public String showInfo() {
        return toString();
    }

    @Override
    public String toString() {
        String string = "{";
        int counter = 0;

        for (Entry<RuntimeStringValue, RuntimeValue> str : dictValue.entrySet()) {

            if (counter < dictValue.size() - 1) {
                string += str.getKey() + ":" + str.getValue() + ", ";
            } else {
                string += str.getKey() + ":" + str.getValue();
            }
            counter++;
        }
        string += "}";
        return string;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        // any == none
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for ==.", where);
        return null; // Required by the compiler.
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("not operand", where));
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue(dictValue.size());
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (dictValue.containsKey(v) && v instanceof RuntimeStringValue) {
            return dictValue.get(v);
        }
        return null; // Required by the compiler!
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (dictValue.size() != 0);
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return this.toString();
    }
}
